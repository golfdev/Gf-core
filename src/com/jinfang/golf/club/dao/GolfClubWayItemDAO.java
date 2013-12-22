package com.jinfang.golf.club.dao;

import java.util.Date;
import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import com.jinfang.golf.club.model.GolfClubWayItem;

@DAO(catalog = "golf_app")
public interface GolfClubWayItemDAO {
	//数据表名
	public static String table_name = "golf_club_way_item";
	//数据表字段
	public static String field = "id,club_id,item_name,available_time,available_date,status,created_time";

	@SQL(" insert into " + table_name + "( club_id,item_name,available_time,available_date,status,created_time)values(:1.clubId,:1.itemName,:1.availableTime,:1.availableDate,:1.status,now())")
	public void save(GolfClubWayItem item);

	
	@SQL(" select distinct available_date from " + table_name + " where club_id=:1 and status<>1 and available_date>=curdate() order by available_date limit 20 ")
	public List<String> getClubAvailableDateList(Integer clubId);
	
	
	@SQL(" select "+field+" from " + table_name + " where club_id=:1 and available_time=:2 and status<>1  order by available_time")
	public List<GolfClubWayItem> getClubAvailableItemList(Integer clubId,String time);
	
	
	@SQL(" select distinct available_time from " + table_name + " where club_id=:1 and available_date=:2 and status<>1  order by available_time")
	public List<Date> getClubAvailableTimeList(Integer clubId,String date);
	
	@SQL(" update " + table_name + " set status=:2 where id=:1")
	public void updateClubItemStatus(Integer itemId,Integer statys); 

}
