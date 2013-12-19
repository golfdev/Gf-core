package com.jinfang.golf.user.dao;

import java.util.List;
import java.util.Map;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.core.Identity;

import com.jinfang.golf.user.model.User;

@DAO(catalog = "golf_app")
public interface UserDAO {
	//数据表名
	public static String table_name = "user";
	//数据表字段
	public static String field = "id,user_name,password,email,phone,gender,head_url,play_age,handicap,description,status,created_time";	

	public static String field_private = "id,user_name,gender,head_url,play_age,handicap,description,status,created_time";	

	@SQL(" insert into " + table_name + "(user_name,password,phone,status,created_time) values(:1.userName,:1.passWord,:1.phone,:1.status,now())")
	public Identity save(User user);
	
	@SQL(" select " + field + " from " + table_name +" where email=:1")
	public User getByEmail(String email);
	
	
	@SQL(" select " + field + " from " + table_name +" where phone=:1")
	public User getByPhone(String phone);
	
	@SQL(" select " + field_private + " from " + table_name +" where user_name like ':1%'")
	public List<User> searchByName(String name);
	
	
	@SQL(" select " +  field + " from " + table_name + " where id=:1")
	public User getById(int id);
	
	@SQL(" select " +  field_private + " from " + table_name + " order by created_time desc limit :1,:2")
	public List<User> getAllUserList(int offset,int limit);
	
	@SQL(" select " +  field_private + " from " + table_name + " where id in (:1)")
	public Map<Integer,User> getUserMapByIds(List<Integer> userIds);
	
	@SQL(" select " +  field_private + " from " + table_name + " where status = :3 order by created_time desc limit :1,:2")
	public List<User> getAllUserListByStatus(int offset,int limit,int status);
	
	@SQL(" select " +  field_private + " from " + table_name + " where city = :3 order by created_time desc limit :1,:2")
	public List<User> getAllUserListByCity(int offset,int limit,String city);
	
	@SQL(" select " +  field_private + " from " + table_name + " where city = :3 and status=:4 order by created_time desc limit :1,:2")
	public List<User> getAllUserListByCity(int offset,int limit,String city,int status);
	
	@SQL(" update " + table_name + " set user_name=:1.userName,gender=:1.gender,city=:1.city,description=:1.description where id=:1.id")
	public void updateUser(User user);
	
	@SQL(" update " + table_name + " set head_url=:1.headUrl where id=:1.id")
	public void updateHeadUrl(User user);
	
	@SQL(" update " + table_name + " set token=:1.token where id=:1.id")
	public void updateToken(User user);
	
	@SQL(" update " + table_name + " set status=:2 where id=:1")
	public void updateStatus(int userId,int status);
	
}
