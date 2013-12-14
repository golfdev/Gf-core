package com.jinfang.golf.db;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.datasource.DataSourceFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.jinfang.golf.db.exception.ConfiguartionException;

/**
 * 实现DataSourceFactory, 使用Jade时需配置到applicationContext-jade.xml中
 * @author 
 *
 */
public class GolfDataSourceFactory implements DataSourceFactory {
	
	private static int instanceCount = 0;//for test
	
	//ZooKeeper维护的数据路径前缀
	private static final String dbPathPrefix = "/configuration/dbInstance/";
	private static final String tpPathPrefix = "/configuration/dbTablePartitions/";
	
    private static Log logger = LogFactory.getLog(GolfDataSourceFactory.class);
    private static Gson gson = new Gson();
    private static JsonParser parser = new JsonParser();
    private static Object lock = new Object();
    
    /**Key:bizName , Value:GummyDataSource */
    private static Map<String,GolfDataSource> gummyDsMap = new HashMap<String,GolfDataSource>();

    public GolfDataSourceFactory(){
    	instanceCount++;
    	if(instanceCount > 3){
    		logger.warn("ERROR ERROR ERROR: too many GummyDataSourceFactory, count:" + instanceCount);
    	}
    }
    
    public DataSource getDataSource(Class<?> daoClass) {
        String catalog = daoClass.getAnnotation(DAO.class).catalog();
        if (StringUtils.isBlank(catalog)) {
        	logger.error(" ~~~~~~~~~~~~~~~ERROR: not found 'catalog' Annotation in : " + daoClass.getName());
            catalog = "notfound";
        }
        return getDataSource(catalog);
    }

    /**
     * 根据bizName获取数据源对象，
     * 先从本地缓存获取，不存在就创建
     * @param bizName
     * @return 数据源对象
     */
    public GolfDataSource getDataSource(String bizName) {
    	if(bizName.indexOf("db_") == 0){ //为兼容有些以“db_”为前缀的bizName
    		bizName = bizName.substring(3);
    	}
    	GolfDataSource gummyDS = gummyDsMap.get(bizName);
    	if(gummyDS == null){
    		synchronized (lock) {
    			gummyDS = gummyDsMap.get(bizName);
    			if(gummyDS == null){
    				GolfBizNameDBConfig dbCfg = getBizNameDBConfig(bizName);
    				String databaseName = dbCfg.getDatabaseName();
    				List<GolfTablePartitionConfig> tablePartitionList = getTablePartitionConfigFromZookeeper(databaseName);
    				gummyDS = new GolfDataSource(bizName, dbCfg, tablePartitionList);
    				gummyDsMap.put(bizName, gummyDS);
    			}
    		}
    	}

        return gummyDS;
    }
    
    private static GolfBizNameDBConfig getBizNameDBConfig(String bizName){
    	String path = dbPathPrefix + bizName;
		//先从ZooKeeper获取配置信息
		String json = "{\"mode\":1,\"servers\":[{\"type\":\"mysql\",\"database\":\"golf_app\",\"host\":\"127.0.0.1\",\"port\":3306,\"user\":\"root\",\"password\":\"\",\"wrflag\":\"w\",\"token\":\"Y\",\"status\":\"enabled\",\"initialSize\":1,\"maxActive\":16,\"maxWait\":10000}]}";
		if(json == null){
			//ZooKeeper没有配置
			logger.error("not found Database Configuration, bizName: " + bizName);
			throw new ConfiguartionException("Error occurs while getData from ZooKeeper(not found configration) ,path:" + path);
		}
		//logger.info(json);
		
		try {
			GolfBizNameDBConfig biznameDBCfg = gson.fromJson(json, GolfBizNameDBConfig.class);
			return biznameDBCfg;
		} catch (JsonSyntaxException e) {
			throw new ConfiguartionException("Error occurs while getData from ZooKeeper(Json Format Error) ,path:" + path, e);
		}
    }
    
    /**从ZooKeeper获取散表信息*/
    private static List<GolfTablePartitionConfig> getTablePartitionConfigFromZookeeper(String databaseName){
    	String tablePartitionPath = tpPathPrefix + databaseName;
		String json = "[{\"tableName\":\"topic_follower\",\"byColumn\":\"topic_id\",\"partitions\":100,\"targetPattern\":\"topic_follower_{0}\"}]";

		if(json == null){
			//不存在这个节点，表示没有散表信息
			return Collections.emptyList();
		}
		
		try {
			JsonArray jarray = parser.parse(json).getAsJsonArray();
			int size = jarray.size();
			List<GolfTablePartitionConfig> tablePartitionList = new ArrayList<GolfTablePartitionConfig>(size);
			for (int i = 0; i < size; i++) {
				GolfTablePartitionConfig tp = gson.fromJson(jarray.get(i), GolfTablePartitionConfig.class);
				if(!tp.isValid()){
					logger.error("database '" + databaseName + "' , invalid TablePartition: " + jarray.get(i).toString());
				}
				tablePartitionList.add(tp);
			}
			return tablePartitionList;
		} catch (JsonSyntaxException e) {
			//logger.error(json);
			throw new ConfiguartionException("Error occurs while getData from ZooKeeper(Json Format Error) ,path:" + tablePartitionPath, e);
		}
    }
    
  
    
    /*测试方法*/
	public static void main(String[] args) {
		GolfDataSourceFactory f = new GolfDataSourceFactory();
		
		DataSource ds = f.getDataSource("bsns");
		for(int i = 0;i < 500;i++){
			long t1 = System.currentTimeMillis();
			try {
				Connection c = ds.getConnection();
				Statement st = c.createStatement();
//				st.executeUpdate("insert into test(name) values ('t" + i + "')");
//				System.out.println(i);
				
				java.sql.ResultSet rs = st.executeQuery("select now()");
				if(rs.next()){
					System.out.println("db time " + rs.getTimestamp(1)); 
				}
				rs.close();
				st.close();
				c.close();
			} catch (Exception e) {
				System.out.println("ex times : " + (System.currentTimeMillis() - t1));
				e.printStackTrace();
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.exit(0);
	}
}