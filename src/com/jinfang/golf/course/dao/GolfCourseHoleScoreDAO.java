package com.jinfang.golf.course.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import com.jinfang.golf.course.model.GolfCourseHoleScore;

@DAO(catalog = "golf_app")
public interface GolfCourseHoleScoreDAO {
	//数据表名
	public static String table_name = "golf_course_hole_score";
	//数据表字段
	public static String field = "course_id,hole_num,player_id,player_name,serial_num,par_score,total_score,putter_score,sub_score,point,created_time";

	@SQL(" replace into " + table_name + "(course_id,hole_num,player_id,player_name,serial_num,par_score,total_score,putter_score,sub_score,point,created_time)values(:1.courseId,:1.holeNum,:1.playerId,:1.playerName,:1.serialNum,:1.parScore,:1.totalScore,:1.putterScore,:1.subScore,:1.point,now())")
	public void save(GolfCourseHoleScore holeScore);

	
	@SQL(" select "+field+" from " + table_name + " where course_id=:1 order by hole_num,serial_num")
	public List<GolfCourseHoleScore> getGolfCourseHoleScoreList(Integer courseId);
	

}
