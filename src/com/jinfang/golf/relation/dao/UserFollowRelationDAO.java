package com.jinfang.golf.relation.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import com.jinfang.golf.relation.model.UserFollowRelation;

@DAO(catalog = "golf_app")
public interface UserFollowRelationDAO {
	//数据表名
	public static String table_name = "follow_relation";
	//数据表字段
	public static String field = "host,guest,status,created_time";	

	@SQL(" replace into " + table_name + "(host,guest,status,created_time) values(:1.host,:1.guest,:1.status,now())")
	public void save(UserFollowRelation userRelation);
	
	@SQL(" select guest from " + table_name +" where host=:1 limit :2,:3")
	public List<Integer> getFollowListByFromUid(Integer userId,Integer offset,Integer limit);
	
	@SQL(" select count(guest) from " + table_name +" where host=:1")
	public Integer getFollowCountByFromUid(Integer userId);
	
	@SQL(" select count(1) from " + table_name +" where host=:1 and guest=:2")
	public Integer getRelationCount(Integer host,Integer guest);
	
	@SQL(" select guest from " + table_name +" where host=:1 and guest in (:2)")
	public List<Integer> getFollowListBatch(Integer host,List<Integer> guestList);
	
	@SQL(" update " + table_name +" set status = :3 where host=:1 and guest=:2")
	public void updateRelation(Integer host,Integer guest,Integer status);
	
	@SQL(" select count(guest) from " + table_name +" where host=:1 and status=1")
	public Integer getFriendCountByFromUid(Integer userId);
	
	@SQL(" select guest from " + table_name +" where host=:1 and status=1 limit :2,:3")
	public List<Integer> getFriendListByFromUid(Integer userId,Integer offset,Integer limit);
	
	@SQL(" delete from " + table_name + " where host=:1 and guest=:2")
	public void deleteRelation(Integer fromUid,Integer toUid);
	
	
}
