package com.jinfang.golf.appointment.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import com.jinfang.golf.appointment.model.GolfAppointmentMember;

@DAO(catalog = "golf_app")
public interface GolfAppointmentMemberDAO {
    
	//数据表名
	public static String table_name = "golf_appointment_member";
	//数据表字段
	public static String field = "appoint_id,user_id,created_time";

	@SQL(" replace into " + table_name + "(appoint_id,user_id,created_time)values(:1.appointId,:1.userId,now())")
	public void save(GolfAppointmentMember appointMember);

	
	@SQL(" select user_id from " + table_name + " where appoint_id=:1")
	public List<Integer> getUserIdListByAppointId(Integer appointId);
	
	@SQL(" delete from " + table_name + " where appoint_id=:1 and user_id=:2")
	public void removeMember(Integer appointId,Integer userId);
	

}
