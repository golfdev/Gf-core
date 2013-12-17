package com.jinfang.golf.relation.dao;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import com.jinfang.golf.relation.model.UserFollowRelation;

@DAO(catalog = "golf_app")
public interface UserFriendRelationDAO {
	//数据表名
	public static String table_name = "friend_relation";
	//数据表字段
	public static String field = "host,guest,created_time";	

	@SQL(" replace into " + table_name + "(host,guest,created_time) values(:1.host,:1.guest,now())")
	public void save(UserFollowRelation userRelation);
	
	
	@SQL(" select count(guest) from " + table_name +" where host=:1")
	public Integer getFriendCountByFromUid(Integer userId);
	
	@SQL(" delete from " + table_name + " where host=:1 and guest=:2")
	public void deleteRelation(Integer fromUid,Integer toUid);
	
	
}
