package com.jinfang.golf.team.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import com.jinfang.golf.team.model.UserTeamRelation;

@DAO(catalog = "golf_app")
public interface UserTeamRelationDAO {
	//数据表名
	public static String table_name = "user_team";
	//数据表字段
	public static String field = "user_id,team_id,is_leader,created_time";	

	@SQL(" insert into " + table_name + "(user_id,team_id,is_leader,created_time) values(:1.userId,:1.teamId,:1.isLeader,now())")
	public void save(UserTeamRelation userTeamRelation);
	
	@SQL(" select team_id from " + table_name +" where user_id=:1")
	public List<Integer> getByUserId(Integer userId);
	
	@SQL(" select team_id from " + table_name +" where user_id=:1 and is_leader=0 order by created_time desc limit :2,:3")
	public List<Integer> getJoinedByUserIdAndPage(Integer userId,Integer offset,Integer limit);
	
	@SQL(" select team_id from " + table_name +" where user_id=:1 and is_leader=1 order by created_time desc limit :2,:3")
	public List<Integer> getOwnedTeamIdByUserIdAndPage(Integer userId,Integer offset,Integer limit);
	
	
	@SQL(" select user_id from " + table_name +" where team_id=:1 order by created_time limit :2,:3")
	public List<Integer> getMemberIdListByTeamId(Integer teamId,Integer offset,Integer limit);
	
	@SQL(" select count(user_id) from " + table_name +" where team_id=:1")
	public Integer getMemberCountByTeamId(Integer teamId);
	
	
	
	
	@SQL(" delete from " + table_name + " where user_id=:1 and team_id=:2")
	public void deleteByUserIdAndTeamId(Integer userId,Integer teamId);
	
}
