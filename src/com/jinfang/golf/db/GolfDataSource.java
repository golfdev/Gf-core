package com.jinfang.golf.db;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.sql.DataSource;

import net.paoding.rose.jade.annotation.UseMaster;
import net.paoding.rose.jade.core.SQLThreadLocal;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Gummy数据源
 * <h3>特性：</h3>
 * <ul>
 * <li>读写的配置信息分离</li>
 * <li>mysql的host,port,dbname相同的情况，只创建一个连接池</li>
 * <li>对于读写在一台server上的情况，连接池是共享的，避免连接资源浪费</li>
 * <li>对于读写的server分开的情况，分别创建连接池</li>
 * </ul>
 * @author 
 *
 */
public class GolfDataSource implements DataSource{
	
	private static Log logger = LogFactory.getLog(GolfDataSource.class);
	
	/**mysql的host,port,dbname相同的情况，只创建一个连接池 Key:hostportdatabaseId , Value:GummyDataSource */
    private static Map<String,BasicDataSource> cachedDsMap = new HashMap<String,BasicDataSource>();
    private static Object lock = new Object();
    
	private String bizName;
	private GolfDataSourceConfig writeDatasourceConfig;
	private GolfDataSourceConfig readDatasourceConfig;
	private Map<String,GolfTablePartitionConfig> tablePartitionMap;
	
	public GolfDataSource(String bizName, GolfBizNameDBConfig bizAllConfig
			, List<GolfTablePartitionConfig> tablePartitionList){
		this.bizName = bizName;
		writeDatasourceConfig = extractDSConfig(bizAllConfig, true);
		readDatasourceConfig = extractDSConfig(bizAllConfig, false);
		
		tablePartitionMap = new HashMap<String,GolfTablePartitionConfig>();
		if(tablePartitionList != null){
			for(GolfTablePartitionConfig p : tablePartitionList){
				tablePartitionMap.put(p.getTableName(), p);
			}
		}
	}
	
	public void setBizName(String bizName) {
        this.bizName = bizName;
    }

    public String getBizName() {
        return bizName;
    }
    
    /** 返回该数据源对象对应的数据库名 */
    public String getDatabaseName(){
    	return writeDatasourceConfig != null ? writeDatasourceConfig.getDatabase() : (readDatasourceConfig != null ? readDatasourceConfig.getDatabase() : null);
    }
    
	public PrintWriter getLogWriter() throws SQLException {
		return null;
	}

	public void setLogWriter(PrintWriter out) throws SQLException {
	}

	public void setLoginTimeout(int seconds) throws SQLException {
	}

	public int getLoginTimeout() throws SQLException {
		 return 0;
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		return null;
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return false;
	}

	/** 获取连接对象，判断写操作还是读操作*/
	public Connection getConnection() throws SQLException {
		SQLThreadLocal local = SQLThreadLocal.get();
        boolean write = local == null || local.isWriteType() || 
        		(local.getModifier() != null && local.getModifier().getMethod().isAnnotationPresent(UseMaster.class));
        //System.out.println("local.isWriteType():" + (local!=null?local.isWriteType():"null"));
        
        //Lazy创建DBCPDataSource对象
        GolfDataSourceConfig dsConfig = write ? writeDatasourceConfig : readDatasourceConfig;
		String mysqlId = makeMysqlDbId(dsConfig.getHost(), dsConfig.getPort(), dsConfig.getDatabase());
		BasicDataSource dataSource = cachedDsMap.get(mysqlId);
    	if(dataSource == null){
    		synchronized (lock) {
    			dataSource = cachedDsMap.get(mysqlId);
    			if(dataSource == null){
    				dataSource = createDBCPDataSource(dsConfig);
	        		cachedDsMap.put(mysqlId, dataSource);
	        		//System.out.println(cachedDsMap);
	        		
	        		String msg = bizName + " Create DB Pool, connected to " + dsConfig.getHost() + ":" + dsConfig.getPort();
	    			logger.info(msg);
    			}
			}
    	}
        
		return dataSource.getConnection();
	}
	
	public Connection getConnection(String username, String password)
			throws SQLException {
		return getConnection();
	}

	/**
	 * 获取表的散列信息
	 * @param table 表名
	 * @return 如果没有散列，就返回null
	 */
	public GolfTablePartitionConfig getPartitionInfoByTable(String tableName){
		return tablePartitionMap != null ? tablePartitionMap.get(tableName) : null;
	}
	
	/** 散表信息有变化时，触发该方法（如ZooKeeper的配置有变化时） */
	protected void onTablePartitionConfigModified(List<GolfTablePartitionConfig> tablePartitionList){
		tablePartitionMap = new HashMap<String,GolfTablePartitionConfig>();
		if(tablePartitionList != null){
			for(GolfTablePartitionConfig p : tablePartitionList){
				tablePartitionMap.put(p.getTableName(), p);
			}
		}
	}
	
	/** 连接池配置有变化时，触发该方法（如ZooKeeper的配置有变化时） */
	protected void onConfigModified(GolfBizNameDBConfig biznameDBCfg){
		onDataSourceConfigModified(biznameDBCfg, true); //用于写操作的数据源
		onDataSourceConfigModified(biznameDBCfg, false); //用于读操作的数据源
	}
	
    /**
     * 当数据源配置可能有变化时，重新载入和创建数据源
     * 注意：这里可能包含读的数据源和写的数据源 
     */
    private void onDataSourceConfigModified(GolfBizNameDBConfig biznameDBCfg, boolean write) {
    	GolfDataSourceConfig newDsConfig = extractDSConfig(biznameDBCfg, write);
    	GolfDataSourceConfig oldDsConfig = write ? writeDatasourceConfig : readDatasourceConfig;
    	
    	if(write){
    		writeDatasourceConfig = newDsConfig;
    	} else {
    		readDatasourceConfig = newDsConfig;
    	}
		String newMysqlId = makeMysqlDbId(newDsConfig.getHost(), newDsConfig.getPort(), newDsConfig.getDatabase());
		String oldMysqlId = makeMysqlDbId(oldDsConfig.getHost(), oldDsConfig.getPort(), oldDsConfig.getDatabase());
		BasicDataSource oldDbcpDS = cachedDsMap.get(newMysqlId);
		if(oldDbcpDS != null){
			if(newMysqlId.equals(oldMysqlId) && !newDsConfig.equals(oldDsConfig)){
				//host和port没变，只是配置有修改， 才重新重建连接池
				BasicDataSource newDbcpDs = createDBCPDataSource(newDsConfig);
				cachedDsMap.put(newMysqlId, newDbcpDs);
				closeDBCPDataSource(oldDbcpDS);//释放连接池资源
			}
			
			//如host和port有变化，测试一下old连接池，如无效就关闭，并从缓存Map中删除
			if(!newMysqlId.equals(oldMysqlId)){
				Connection con = null;
				Statement stat = null;
				ResultSet rs = null;
				try {
					con = oldDbcpDS.getConnection();
					stat = con.createStatement();
					rs = stat.executeQuery("select 1");
				} catch (Exception e) {
					//有问题，就关闭
					closeDBCPDataSource(oldDbcpDS);//释放无效的连接池资源
					oldDbcpDS = null;
					
					cachedDsMap.remove(oldMysqlId);//删除无效的连接池
				} finally {
					if(rs != null) try {rs.close();} catch (SQLException e) { }
					if(stat != null) try {stat.close();} catch (SQLException e) { }
					if(con != null) try {con.close();} catch (SQLException e) { }
				}
			}
		}
		
		//说明：对于host或port变了的情况，不用创建连接池，因为会根据实际需要创建 (因为调用getConnection()方法时，就Lazy创建连接池)
    }
    
    private GolfDataSourceConfig extractDSConfig(GolfBizNameDBConfig biznameDBCfg, boolean write) {
		int mode = biznameDBCfg.getMode();
		if (mode == -1){ //不可用
			throw new RuntimeException("Error occurs while getData from ZooKeeper , disabled mode, DBInstance:" + bizName);
		}
		
		GolfDataSourceConfig retDataSourceConfig = null;
		List<GolfDataSourceConfig> dsCfgList = biznameDBCfg.getServers();
		for(GolfDataSourceConfig dsCfg : dsCfgList){
			if(!dsCfg.hasToken(mode) || !"enabled".equals(dsCfg.getStatus())){
				//没有令牌的结点 和 不可用的结点的配置 算作无效
				//System.out.println("token:" + dsCfg.getToken());
				//System.out.println("status:" + dsCfg.getStatus());
				continue;
			}
			
			if(mode == 1){ //读写在一台服务器
				retDataSourceConfig = dsCfg;
				break;
			} else if (mode == 0){ //该结点为只读，不能写
				if(write){
					throw new RuntimeException("Error occurs while getData from ZooKeeper , not support 'write' mode, DBInstance:" + bizName);
				} else {
					retDataSourceConfig = dsCfg;
					break;
				}
			} else if (mode == 2) { //读写根据wrflag来指定（一读操作和写操作各一般个server；如其中一个down了，剩下的一台被monitor改为'wr'才承担读和写操作）
				if("wr".equals(dsCfg.getWrflag()) //读写在一起
						|| ( write && "w".equals(dsCfg.getWrflag()) )
						|| (!write && "r".equals(dsCfg.getWrflag()))){
					retDataSourceConfig = dsCfg;
					break;
				}
			}
		}
		
		if(retDataSourceConfig == null){
			throw new RuntimeException("Error occurs while getData from ZooKeeper , no DBInstance:" + bizName);
		}

		return retDataSourceConfig;
    }
    
    private String makeMysqlDbId(String host,int port, String databaseName){
    	return host + ":" + port + "/" + databaseName;
    }
    
    private void closeDBCPDataSource(DataSource dbcpDs){
    	if(dbcpDs != null && dbcpDs instanceof BasicDataSource) {
    		BasicDataSource ds = (BasicDataSource)dbcpDs;
    		try {
				ds.close();
			} catch (Exception e) {
				//not todo 
			}
    	}
    }
	
    /** 根据配置创建数据源对象  */
    private BasicDataSource createDBCPDataSource(GolfDataSourceConfig dsConfig){
    	//boolean notfound = "notfound".equals(dsConfig.getDatabase());
    	//if(notfound){
    	//	System.err.println(" ~~~~~~~~~~~~~~~Config ERROR: Not Found DB Configuration of " + dsConfig.getDatabase() + ", Please tell Administrator");
    	//}
    	int initialSize = dsConfig.getInitialSize();
   		int maxActive = dsConfig.getMaxActive();
   		int maxWait = dsConfig.getMaxWait();//建议10000毫秒
   		int maxIdle = maxActive/10 + 1;
   		int minIdle = maxActive/25 + 1;
   		
   		//timeBetweenEvictionRunsMillis毫秒秒检查一次连接池中空闲的连接,
   		//把空闲时间超过minEvictableIdleTimeMillis毫秒的连接断开,直到连接池中的连接数到minIdle为止
   		long timeBetweenEvictionRunsMillis = 1000*30;
   		long minEvictableIdleTimeMillis = 1000*60*5;
   		
    	//创建数据源
    	BasicDataSource ds = new BasicDataSource();
    	if(StringUtils.isEmpty(dsConfig.getType()) || "mysql".equals(dsConfig.getType())){
        	ds.setDriverClassName("com.mysql.jdbc.Driver");
        	String url = String.format("jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=utf8&connectTimeout=10000&socketTimeout=5000"
        			, dsConfig.getHost(), dsConfig.getPort(), dsConfig.getDatabase());
        	logger.info("connect pool, url:[" + url + "] user:" + dsConfig.getUser());
        	ds.setUrl(url);
    	}
    	ds.setUsername(dsConfig.getUser());
   		ds.setPassword(dsConfig.getPassword());
   		
   		ds.setInitialSize(initialSize);
   		ds.setMaxActive(maxActive);
   		ds.setMinIdle(minIdle);
   		ds.setMaxIdle(maxIdle);
   		ds.setMaxWait(maxWait);
   		
   		ds.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		ds.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		ds.setNumTestsPerEvictionRun(maxActive); //每次检查链接的数量，设置和maxActive一样大，这样每次可以有效检查所有的链接
		ds.setValidationQuery("SELECT 1");

		return ds;
    }

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}
}