package com.jinfang.golf.user.dao;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import com.jinfang.golf.user.model.UserCentify;

@DAO(catalog = "")
public interface UserCentifyDAO {
	//数据表名
	public static String table_name = "user_device";
	//数据表字段
	public static String field = "user_id,sfz_id,real_name,created_time";	

	@SQL(" replace into " + table_name + "(user_id,sfz_id,real_name,created_time) value(:1.userId,:1.sfzId,:1.realName,now())")
	public void save(UserCentify centify);
	
	
}
