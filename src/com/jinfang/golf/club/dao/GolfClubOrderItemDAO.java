package com.jinfang.golf.club.dao;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import com.jinfang.golf.club.model.GolfClubOrderWay;

@DAO(catalog = "golf_app")
public interface GolfClubOrderItemDAO {
	//数据表名
	public static String table_name = "golf_club_order_item";
	//数据表字段
	public static String field = "order_id,item_id,created_time";

	@SQL(" insert into " + table_name + "( order_id,item_id,created_time) values (:1.orderId,:1.itemId,now())")
	public void save(GolfClubOrderWay orderWay);

}
