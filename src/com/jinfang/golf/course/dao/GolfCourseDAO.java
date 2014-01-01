package com.jinfang.golf.course.dao;

import java.util.List;
import java.util.Map;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.core.Identity;

import com.jinfang.golf.course.model.GolfCourse;

@DAO(catalog = "golf_app")
public interface GolfCourseDAO {
	//数据表名
	public static String table_name = "golf_course";
	//数据表字段
	public static String field = "id,club_id,player_setting,is_live,creator_id,created_time";

	@SQL(" insert into " + table_name + "(club_id,player_setting,is_live,creator_id,created_time)values(:1.clubId,:1.playerSetting,:1.isLive,:1.creatorId,now())")
	public Identity save(GolfCourse course);


	@SQL(" select " +  field + " from " + table_name + " where id in (:1)")
	public Map<Integer,GolfCourse> getGolfCourseMapByIds(List<Integer> courseIds);
	
	@SQL(" select " +  field + " from " + table_name + " where id = :1")
	public GolfCourse getGolfCourseById(Integer courseId);
	
	@SQL(" select " +  field + " from " + table_name + " where is_live=1 limit :1,:2")
	public List<GolfCourse> getLiveCourseList(Integer offset,Integer limit);
}
