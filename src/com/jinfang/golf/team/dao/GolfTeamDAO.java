package com.jinfang.golf.team.dao;

import java.util.List;
import java.util.Map;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.core.Identity;

import com.jinfang.golf.team.model.GolfTeam;

@DAO(catalog = "golf_app")
public interface GolfTeamDAO {
	//数据表名
	public static String table_name = "golf_team";
	//数据表字段
	public static String field = "id,name,logo,creator_id,contacts,phone,city,purpose,description,notice,created_date,created_time";	

	@SQL(" insert into " + table_name + "(name,logo,creator_id,contacts,phone,city,purpose,description,notice,created_date,created_time) values(:1.name,:1.logo,:1.creatorId,:1.contacts,:1.phone,:1.city,:1.purpose,:1.description,:1.notice,:1.createdDate,now())")
	public Identity save(GolfTeam team);
	
	@SQL(" select "+field+" from " + table_name +" where city=:1 order by created_time desc limit :2,:3")
	public List<GolfTeam> getGolfTeamList(String city,Integer offset,Integer limit);
	
	@SQL(" select "+field+" from " + table_name +" where id in (:1) ")
	public Map<Integer,GolfTeam> getGolfTeamListByIds(List<Integer> ids);
	
	@SQL(" select "+field+" from " + table_name +" where id=:1")
    public GolfTeam getGolfTeamById(Integer id);
	
	@SQL(" update " + table_name +" set name=:1.name,logo=:1.logo,contacts=:1.contacts,phone=:1.phone,city=:1.city, purpose=:1.purpose,description=:1.description where id=:1.id")
	public void updateGolfTeam(GolfTeam team);
	
	@SQL(" update " + table_name +" set notice=:1.notice where id=:1.id")
	public void updateNotice(GolfTeam team);
	
	@SQL(" select count(1) from " + table_name)
	public Integer getTotalTeamCount();
	
	@SQL(" select "+field+" from " + table_name +" order by created_time desc limit :2,:3")
	public List<GolfTeam> getAllGolfTeamList(Integer offset,Integer limit);
	
	
	
	
}
