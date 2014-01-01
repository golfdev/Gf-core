package com.jinfang.golf.course.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import com.jinfang.golf.course.model.GolfCoursePlayer;

@DAO(catalog = "golf_app")
public interface GolfCoursePlayerDAO {
	//数据表名
	public static String table_name = "golf_course_player";
	//数据表字段
	public static String field = "course_id,player_id,player_name,tee_num,serial_num,created_time";

	@SQL(" insert into " + table_name + "(course_id,player_id,player_name,tee_num,serial_num,created_time)values(:1.courseId,:1.playerId,:1.playerName,:1.teeNum,:1.serialNum,now())")
	public void save(GolfCoursePlayer coursePlayer);
	
	@SQL(" select course_id from " + table_name + " where player_id=:1 order by created_time desc limit :2,:3")
	public List<Integer> getHistoryCourseList(Integer userId,Integer offset,Integer limit);



}
