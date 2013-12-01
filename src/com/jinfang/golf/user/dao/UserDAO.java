package com.jinfang.golf.user.dao;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.core.Identity;

import com.jinfang.golf.user.model.User;

@DAO(catalog = "")
public interface UserDAO {
	//数据表名
	public static String table_name = "user";
	//数据表字段
	public static String field = "id,user_name,password,email,phone,gender,head_url,play_age,handicap,description,created_time";	

	@SQL(" insert into " + table_name + "(user_name,password,phone,status,created_time) value(:1.userName,:1.passWord,:1.phone,:1.status,now())")
	public Identity save(User user);
	
	@SQL(" select " + field + " from " + table_name +" where email=:1")
	public User getByEmail(String email);
	
	
	@SQL(" select " + field + " from " + table_name +" where phone=:1")
	public User getByPhone(String phone);
	
	
	@SQL(" select " +  field + " from " + table_name + " where id=:1")
	public User getById(int id);
	
	@SQL(" update " + table_name + " set user_name=:1.userName,gender=:1.gender,city=:1.city,description=:1.description where id=:1.id")
	public void updateUser(User user);
	
	@SQL(" update " + table_name + " set head_url=:1.headUrl where id=:1.id")
	public void updateHeadUrl(User user);
	
}
