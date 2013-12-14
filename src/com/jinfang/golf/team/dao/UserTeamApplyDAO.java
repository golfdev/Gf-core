package com.jinfang.golf.team.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import com.jinfang.golf.team.model.UserTeamApply;

@DAO(catalog = "golf_app")
public interface UserTeamApplyDAO {
	//数据表名
	public static String table_name = "team_apply";
	//数据表字段
	public static String field = "user_id,team_id,status,created_time";	

	@SQL(" insert into " + table_name + "(user_id,team_id,status,created_time) values(:1.userId,:1.teamId,:1.status,now())")
	public void save(UserTeamApply userTeamApply);
	
	
	@SQL(" select user_id from " + table_name +" where team_id=:1")
	public List<Integer> getUserIdListByTeamId(Integer teamId);
	
	@SQL(" select count(user_id) from " + table_name +" where team_id=:1 and status=0")
	public Integer getApplyCountByTeamId(Integer teamId);
	
	
	@SQL(" update " + table_name + " set status=:3 where user_id=:1 and team_id=:2")
	public void updateStatus(Integer userId,Integer teamId,Integer status);
	
}
