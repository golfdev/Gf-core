package com.jinfang.golf.relation.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import com.jinfang.golf.relation.model.UserFollowRelation;

@DAO(catalog = "golf_app")
public interface UserFollowedRelationDAO {
	//数据表名
	public static String table_name = "followed_relation";
	//数据表字段
	public static String field = "host,guest,created_time";	

	@SQL(" replace into " + table_name + "(host,guest,created_time) values(:1.guest,:1.host,now())")
	public void save(UserFollowRelation userRelation);
	
	
	@SQL(" select guest from " + table_name +" where host=:1 limit :2,:3")
	public List<Integer> getFansListByToUid(Integer userId,Integer offset,Integer limit);
	
	@SQL(" select count(guest) from " + table_name +" where host=:1")
	public Integer getFansCountByToUid(Integer toUid);
	
	@SQL(" delete from " + table_name + " where host=:1 and guest=:2")
	public void deleteRelation(Integer fromUid,Integer toUid);
	
	
}
