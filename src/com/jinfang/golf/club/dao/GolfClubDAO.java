package com.jinfang.golf.club.dao;

import java.util.List;
import java.util.Map;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.core.Identity;

import com.jinfang.golf.club.model.GolfClub;

@DAO(catalog = "golf_app")
public interface GolfClubDAO {
	//数据表名
	public static String table_name = "golf_club";
	//数据表字段
	public static String field = "id,name,logo,contacts,phone,city,address,market_price,centify_price,un_centify_price,description,about_club,created_time";

	@SQL(" insert into " + table_name + "( email,password,name,contacts,phone,city,created_time )values(:1.email,:1.password,:1.name,:1.contacts,:1.phone,:1.city,now())")
	public Identity save(GolfClub club);

	
	@SQL(" select "+field+" from " + table_name + " where city=:1 order by created_time desc limit :2,:3 ")
	public List<GolfClub> getGolfClubList(String city,Integer offset,Integer limit);
	
	
	@SQL(" select "+field+" from " + table_name + " where id=:1 ")
	public GolfClub getGolfClub(Integer id);
	
	@SQL(" select email,password,name,contacts,phone,city,created_time from " + table_name + " where email=:1 ")
	public GolfClub getGolfCLubLoginInfo(String email);
	
	@SQL(" select "+field+" from " + table_name + " where id in (:1) ")
	public Map<Integer,GolfClub> getGolfClubMap(List<Integer> clubIds);

}
