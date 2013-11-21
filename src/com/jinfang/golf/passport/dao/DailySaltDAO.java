package com.jinfang.golf.passport.dao;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import com.jinfang.golf.passport.model.DailySalt;

@DAO(catalog = "")
public interface DailySaltDAO {
	//数据表名
	public static String table_name = "dailySalt";
	//数据表字段
	public static String field = "createTime,salt";

	@SQL(" insert ignore into " + table_name + "( " + field + " )value(:1.createTime,:1.salt)")
	public void save(DailySalt salt);

	
	@SQL(" select * from " + table_name + " where createTime=:1 ")
	public DailySalt get(long createTime);
	

}