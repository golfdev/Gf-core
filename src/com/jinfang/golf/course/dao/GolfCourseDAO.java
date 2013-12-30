package com.jinfang.golf.course.dao;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.core.Identity;

import com.jinfang.golf.course.model.GolfCourse;

@DAO(catalog = "golf_app")
public interface GolfCourseDAO {
	//数据表名
	public static String table_name = "golf_course";
	//数据表字段
	public static String field = "id,club_id,player_setting,is_live,created_time";

	@SQL(" insert into " + table_name + "(club_id,player_setting,is_live,created_time)values(:1.clubId,:1.playerSetting,:1.isLive,now())")
	public Identity save(GolfCourse course);



}
