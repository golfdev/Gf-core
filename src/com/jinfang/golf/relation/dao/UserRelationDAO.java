package com.jinfang.golf.relation.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import com.jinfang.golf.relation.model.UserRelation;

@DAO(catalog = "")
public interface UserRelationDAO {
	//数据表名
	public static String table_name = "relation";
	//数据表字段
	public static String field = "from_uid,to_uid,,created_time";	

	@SQL(" insert into " + table_name + "(from_uid,to_uid,created_time) value(:1.fromUid,:1.toUid,now())")
	public void save(UserRelation userRelation);
	
	@SQL(" select to_uid from " + table_name +" where from_uid=:1")
	public List<Integer> getByUserId(Integer userId);
	
	
	@SQL(" select from_uid from " + table_name +" where to_uid=:1")
	public List<Integer> getByTeamId(Integer teamId);
	
	
	@SQL(" delete from " + table_name + " where from_uid=:1 and to_uid=:2")
	public void deleteRelation(Integer fromUid,Integer toUid);
	
}
