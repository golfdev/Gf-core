package com.jinfang.golf.appointment.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import com.jinfang.golf.appointment.model.GolfAppointment;

@DAO(catalog = "golf_app")
public interface GolfAppointmentDAO {
	//数据表名
	public static String table_name = "golf_appointment";
	//数据表字段
	public static String field = "id,club_id,creator_id,count,appoint_time,avg_price,gender,min_handicap,max_handicap,private_setting,description,created_time";

	@SQL(" insert into " + table_name + "( club_id,creator_id,appoint_time,avg_price,gender,min_handicap,max_handicap,private_setting,description,created_time )values(:1.clubId,:1.creatorId,:1.appointTime,:1.avgPrice,:1.gender,:1.minHandicap,:1.maxHandicap,:1.privateSetting,:1.description,now())")
	public void save(GolfAppointment appointMent);

	@SQL(" select "+field+" from " + table_name + " where appoint_time>now() and ((creator_id in (:1) and private_setting=1) or (private_setting=0))  order by appoint_time desc limit :2,:3 ")
	public List<GolfAppointment> getGolfAppointmentListWithTeam(List<Integer> userIdList,Integer offset,Integer limit);
	
	@SQL(" select "+field+" from " + table_name + " where appoint_time>now() and private_setting=0 order by appoint_time desc limit :2,:3 ")
    public List<GolfAppointment> getGolfAppointmentList(Integer offset,Integer limit);
	
	@SQL(" select "+field+" from " + table_name + " where id=:1 ")
	public GolfAppointment getGolfAppointment(Integer id);
	
}
