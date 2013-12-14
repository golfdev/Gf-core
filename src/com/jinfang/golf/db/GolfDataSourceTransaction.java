package com.jinfang.golf.db;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.jinfang.golf.utils.RoseAppContextProvider;

/**
 * 让Jade支持事务操作的管理器(注意：只能支持对单个库的事务支持，不是分布式事务)
 * @author 
 *
 */
public class GolfDataSourceTransaction {
	
	private static GolfDataSourceFactory dataSourceFactory = (GolfDataSourceFactory)RoseAppContextProvider.getInstance().getRoseAppContext().getBean(GolfDataSourceFactory.class);

	private DataSourceTransactionManager transactionManager;
	private TransactionStatus transactionStatus;
	
	/**
	 * 生成一个TransactionManager实例（每个事务开始请就创建一次），
	 * 另外要记得线程结束前一定要记得调用 release()方法释放连接资源，否则资源会浪费
	 * @param bizName dbInstance的bizName，同DAO接口Annotation“@DAO”catalog属性值 
	 */
	public GolfDataSourceTransaction(String bizName){
		DataSource dataSource = dataSourceFactory.getDataSource(bizName);
		transactionManager = new DataSourceTransactionManager(dataSource);
	}
	
	/**
	 * 创建Connection并获取一个事务
	 * @throws SQLException 如创建连接出错，就抛出异常
	 */
	public void begin() throws SQLException{
		ConnectionHolder conHolder = (ConnectionHolder) TransactionSynchronizationManager.getResource(transactionManager.getDataSource());
		
		if(conHolder == null){ 
			conHolder = new ConnectionHolder(transactionManager.getDataSource().getConnection()); 
			
			//这个bindResource的步骤非常关键。ConnectionHolder会在DAO的每个方法执行时，从这里获取Connection的
			TransactionSynchronizationManager.bindResource(transactionManager.getDataSource(), conHolder); 
		}
		if(transactionStatus == null){
			transactionStatus = transactionManager.getTransaction(null);
		}
	}
	
	/** 提交事务 */
	public void commit(){
		if(transactionStatus != null){
			//这里会在spring的内部实现中调用上面给定的connection的commit方法
			transactionManager.commit(transactionStatus);
		} else {
			//throw new RuntimeException("");
		}
	}
	
	/** 回滚事务 */
	public void rollback(){
		if(transactionStatus != null){
			//这里会在spring的内部实现中调用上面给定的connection的rollback方法
			transactionManager.rollback(transactionStatus);
		}
	}
	
	/** 释放连接资源，否则资源会浪费 （在finally内部一定要调用该方法） */
	public void release(){
		//说明：以下没有重用ConnectionHolder对象并关闭了Connection，实现虽然有些粗暴，但很有必要 
		
		ConnectionHolder conHolder = (ConnectionHolder) TransactionSynchronizationManager.getResource(transactionManager.getDataSource());
		if(conHolder != null){
			conHolder.released();
			try {
				if(conHolder.getConnection() != null){
					conHolder.getConnection().close(); //释放连接，还给connection pool
				}
			} catch (SQLException e) {
			}
			conHolder.clear(); //清除不再重用了
		}
		TransactionSynchronizationManager.unbindResource(transactionManager.getDataSource());
	}
}