package com.jinfang.golf.team.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import com.jinfang.golf.team.model.GolfTeam;

@DAO(catalog = "")
public interface GolfTeamDAO {
	//数据表名
	public static String table_name = "golf_team";
	//数据表字段
	public static String field = "id,name,logo,creator_id,contacts,phone,city,club_name,created_time";	

	@SQL(" insert into " + table_name + "(name,logo,creator_id,contacts,phone,city,club_name,created_time) value(:1.name,:1.logo,:1.creatorId,:1.contacts,:1.phone,:1.city,:1.clubName,now())")
	public void save(GolfTeam team);
	
	@SQL(" select team_id from " + table_name +" where user_id=:1")
	public List<Integer> getByUserId(Integer userId);
	
	
	@SQL(" select user_id from " + table_name +" where team_id=:1")
	public List<Integer> getByTeamId(Integer teamId);
	
	
	@SQL(" delete from " + table_name + " where user_id=:1 and team_id=:2")
	public void deleteByUserIdAndTeamId(Integer userId,Integer teamId);
	
}
