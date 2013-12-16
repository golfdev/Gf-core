package com.jinfang.golf.team.dao;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.core.Identity;

import com.jinfang.golf.team.model.GolfTeamNotice;

@DAO(catalog = "golf_app")
public interface GolfTeamNoticeDAO {
	//数据表名
	public static String table_name = "golf_team_notice";
	//数据表字段
	public static String field = "team_id,notice,created_time";	

	@SQL(" insert into " + table_name + "(team_id,notice,created_time) values(:1.teamId,:1.notice,now())")
	public Identity save(GolfTeamNotice notice);
	
}
