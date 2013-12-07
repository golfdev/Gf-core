package com.jinfang.golf.team.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.core.Identity;

import com.jinfang.golf.team.model.GolfTeam;

@DAO(catalog = "")
public interface GolfTeamDAO {
	//数据表名
	public static String table_name = "golf_team";
	//数据表字段
	public static String field = "id,name,logo,creator_id,contacts,phone,city,created_time";	

	@SQL(" insert into " + table_name + "(name,logo,creator_id,contacts,phone,city,created_time) value(:1.name,:1.logo,:1.creatorId,:1.contacts,:1.phone,:1.city,now())")
	public Identity save(GolfTeam team);
	
	@SQL(" select "+field+" from " + table_name +" where city=:1 limit :2,:3")
	public List<GolfTeam> getGolfTeamList(String city,Integer offset,Integer limit);
	
	@SQL(" select "+field+" from " + table_name +" where id=:1")
    public GolfTeam getGolfTeamById(Integer id);
	
	@SQL(" update " + table_name +" set name=:1.name,logo=:1.logo,contacts=:1.contacts,phone=:1.phone,city=:1.city where id=:1.id")
	public void updateGolfTeam(GolfTeam team);
	
	
}
