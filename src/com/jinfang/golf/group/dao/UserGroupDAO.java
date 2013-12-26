package com.jinfang.golf.group.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import com.jinfang.golf.group.model.UserGroup;

@DAO(catalog = "golf_group")
public interface UserGroupDAO {
	//数据表名
	public static String table_name = "user_group";
	//数据表字段
	public static String field = "user_id,group_id,time,status";

	@SQL(" insert ignore into " + table_name + "(" + field + ") values(:1.userId,:1.groupId,:1.time,:1.status)")
	public void save(UserGroup userGroup);
	
	@SQL(" select " +  field + " from " + table_name + " where user_id=:1 and status=0 order by time desc limit :2,:3")
	public List<UserGroup> gets(int userId, int offset, int limit);
	
	@SQL(" delete from " + table_name + " where user_id=:1 and group_id=:2")
	public void delete(int userId, int groupId);
	
	@SQL(" select " +  field + " from " + table_name + " where user_id=:1 and group_id=:2")
	public UserGroup get(int userId, int groupId);
	
	@SQL(" update " + table_name + " set status=:3 where user_id=:1 and group_id=:2")
	public void updateStatus(int userId, int groupId, int status);
}
