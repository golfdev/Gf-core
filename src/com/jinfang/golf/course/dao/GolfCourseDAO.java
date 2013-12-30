package com.jinfang.golf.course.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import com.jinfang.golf.club.model.GolfClubYard;
import com.jinfang.golf.course.model.GolfCourse;

@DAO(catalog = "golf_app")
public interface GolfCourseDAO {
	//数据表名
	public static String table_name = "golf_course";
	//数据表字段
	public static String field = "id,club_id,player_id,player_name,tee_num,serial_num,is_live,created_time";

	@SQL(" insert into " + table_name + "(club_id,player_id,player_name,tee_num,serial_num,is_live,created_time )values(:1.clubId,:1.playerId,:1.playerName,:1.teeNum,:1.serialNum,:1.isLive,now())")
	public void save(GolfCourse course);

	
	@SQL(" select "+field+" from " + table_name + " where club_id=:1 order by hole_num")
	public List<GolfClubYard> getGolfClubYardList(Integer clubId);
	

}
