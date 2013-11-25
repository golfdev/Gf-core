package com.jinfang.golf.user.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import com.jinfang.golf.user.model.UserClubRelation;

@DAO(catalog = "")
public interface UserClubRelationDAO {
	//数据表名
	public static String table_name = "user_club";
	//数据表字段
	public static String field = "user_id,club_id,created_time";	

	@SQL(" insert into " + table_name + "(user_id,club_id,created_time) value(:1.userId,:1.clubId,now())")
	public void save(UserClubRelation userClubRelation);
	
	@SQL(" select club_id from " + table_name +" where user_id=:1")
	public List<Integer> getByUserId(Integer userId);
	
	
	@SQL(" select user_id from " + table_name +" where club_id=:1")
	public List<Integer> getByClubId(String phone);
	
	
	@SQL(" delete from " + table_name + " where user_id=:1 and club_id=:2")
	public void deleteByUserIdAndClubId(Integer userId,Integer clubId);
	
}
