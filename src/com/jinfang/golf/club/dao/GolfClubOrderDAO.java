package com.jinfang.golf.club.dao;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.core.Identity;

import com.jinfang.golf.club.model.GolfClubOrder;

@DAO(catalog = "golf_app")
public interface GolfClubOrderDAO {
	//数据表名
	public static String table_name = "golf_club_order";
	//数据表字段
	public static String field = "id,club_id,tee_time,user_id,player_num,player_names,total_price,status,created_time";

	@SQL(" insert into " + table_name + "(club_id,tee_time,user_id,player_num,player_names,total_price,status,created_time)values(:1.clubId,:1.teeTime,:1.userId,:1.playerNum,:1.playerNames,:1.totalPrice,:1.status,now())")
	public Identity save(GolfClubOrder order);

}
