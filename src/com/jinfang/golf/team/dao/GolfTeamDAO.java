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
	
	@SQL(" select "+field+" from " + table_name +" where city=:1")
	public List<GolfTeam> getGolfTeamList(String city);
	
	@SQL(" select "+field+" from " + table_name +" where id=:1")
    public GolfTeam getGolfTeamById(Integer id);
	
	
}
