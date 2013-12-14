package com.jinfang.golf.relation.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import com.jinfang.golf.relation.model.UserRelation;

@DAO(catalog = "golf_app")
public interface UserRelationDAO {
	//数据表名
	public static String table_name = "relation";
	//数据表字段
	public static String field = "from_uid,to_uid,status,created_time";	

	@SQL(" insert into " + table_name + "(from_uid,to_uid,status,created_time) values(:1.fromUid,:1.toUid,:1.status,now())")
	public void save(UserRelation userRelation);
	
	@SQL(" select to_uid from " + table_name +" where from_uid=:1")
	public List<Integer> getFollowListByFromUid(Integer userId);
	
	@SQL(" select count(to_uid) from " + table_name +" where from_uid=:1")
	public Integer getFollowCountByFromUid(Integer userId);
	
	
	@SQL(" select from_uid from " + table_name +" where to_uid=:1")
	public List<Integer> getFansListByToUid(Integer teamId);
	
	@SQL(" select count(from_uid) from " + table_name +" where to_uid=:1")
	public Integer getFansCountByToUid(Integer toUid);
	
	@SQL(" select count(to_uid) from " + table_name +" where from_uid=:1 ans status=1")
	public Integer getFriendCountByFromUid(Integer userId);
	
	
	@SQL(" delete from " + table_name + " where from_uid=:1 and to_uid=:2")
	public void deleteRelation(Integer fromUid,Integer toUid);
	
	@SQL(" select count(1) from " + table_name +" where from_uid =:1 and to_uid=:2")
	public int getUserRelation(Integer fromUid,Integer toUid);
	
	@SQL(" update " + table_name +" set status=:3 where from_uid =:1 and to_uid=:2")
	public void updateStatus(Integer fromUid,Integer toUid,Integer status);
	
}
