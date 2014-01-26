package com.jinfang.golf.course.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.core.Identity;

import com.jinfang.golf.course.model.GolfCourseComment;

@DAO(catalog = "golf_app")
public interface GolfCourseCommentDAO {
	//数据表名
	public static String table_name = "golf_course_comment";
	//数据表字段
	public static String field = "id,user_id,course_id,content,type,created_time";

	@SQL(" insert into " + table_name + "(course_id,user_id,content,type,created_time)values(:1.courseId,:1.userId,:1.content,:1.type,now())")
	public Identity save(GolfCourseComment comment);
	
	@SQL(" select "+field+" from " + table_name + " where course_id=:1 order by created_time desc limit :2,:3")
	public List<GolfCourseComment> getGolfCourseCommentList(Integer courseId,Integer offset,Integer limit);



}
