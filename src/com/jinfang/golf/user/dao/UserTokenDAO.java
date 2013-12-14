package com.jinfang.golf.user.dao;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import com.jinfang.golf.user.model.UserToken;

@DAO(catalog = "golf_app")
public interface UserTokenDAO {
	//数据表名
	public static String table_name = "user_token";
	//数据表字段
	public static String field = "user_id,token,source,created_time";	

	@SQL(" replace into " + table_name + "(user_id,token,source,created_time) values(:1,:2,:3,now())")
	public void save(Integer userId,String token,String source);
	
	
	@SQL(" select user_id,token,source from user_token where user_id=:1")
	public UserToken getTokenAndSourceByUserId(Integer userId);
	
	@SQL(" update user_token set device_token=:2 where user_id=:1")
	public void updateDeviceToken(Integer userId,String deviceToken);
	
	@SQL(" select device_token where user_id=:1")
	public String getDeviceToken(Integer userId);
	
	
}
