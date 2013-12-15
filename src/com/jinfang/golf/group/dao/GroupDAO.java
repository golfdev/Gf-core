package com.jinfang.golf.group.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import com.jinfang.golf.group.model.Group;

@DAO(catalog = "golf_app")
public interface GroupDAO {
	//数据表名
	public static String table_name = "`group`";
	//数据表字段
	public static String field = "group_id,name,time,type,user_count,creater_id,status,last_user_id,last_text,last_time";

	@SQL(" insert into " + table_name + "(" + field + ") values(:1.groupId,:1.name,:1.time,:1.type,:1.userCount,:1.createrId," +
			":1.status,:1.lastUserId,:1.lastText,:1.lastTime)")
	public void save(Group group);
	
	@SQL(" select " + field + " from " + table_name +" where group_id=:1")
	public Group get(int groupId);
	
	@SQL(" select " +  field + " from " + table_name + " where group_id in (:1)")
	public Map<Integer, Group> getByIds(List<Integer> groupIds);
	
	@SQL(" update " + table_name + " set name=:2 where group_id=:1")
	public void updateName(int groupId, String name);
	
	@SQL(" update " + table_name + " set last_user_id=:2,last_text=:3,last_time=:4 where group_id=:1")
	public void updateLastUse(int groupId, int userId, String text, Date time);
	
	@SQL(" update " + table_name + " set user_count=:2 where group_id=:1")
	public void updateCount(int groupId, int count);
}
