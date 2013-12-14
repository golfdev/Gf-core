package com.jinfang.golf.group.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import com.jinfang.golf.group.model.UserGroup;

@DAO(catalog = "golf_group")
public interface GroupUserDAO {
	//数据表名
	public static String table_name = "group_user";
	//数据表字段
	public static String field = "group_id,user_id,time";

	@SQL(" insert ignore into " + table_name + "(" + field + ") values(:1.groupId,:1.userId,:1.time)")
	public void save(UserGroup groupUser);
	
	@SQL(" select " +  field + " from " + table_name + " where group_id=:1 order by time desc limit :2,:3")
	public List<UserGroup> gets(int groupId, int offset, int limit);
	
	@SQL(" delete from " + table_name + " where group_id=:1 and user_id=:2")
	public void delete(int groupId, int userId);
	
	@SQL(" select count(1) from " + table_name + " where group_id=:1")
	public int getCount(int groupId);
}
