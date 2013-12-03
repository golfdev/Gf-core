package com.jinfang.golf.user.dao;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.core.Identity;

@DAO(catalog = "")
public interface UserDeviceDAO {
	//数据表名
	public static String table_name = "user_device";
	//数据表字段
	public static String field = "user_id,device,created_time";	

	@SQL(" insert into " + table_name + "(user_id,device,created_time) value(:1,:2,now())")
	public Identity save(Integer userId,String device);
	
	
}
