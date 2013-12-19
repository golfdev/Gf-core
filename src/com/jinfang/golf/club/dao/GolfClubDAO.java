package com.jinfang.golf.club.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import com.jinfang.golf.club.model.GolfClub;

@DAO(catalog = "golf_app")
public interface GolfClubDAO {
	//数据表名
	public static String table_name = "golf_club";
	//数据表字段
	public static String field = "id,name,logo,contacts,phone,city,address,market_price,centify_price,un_centify_price,description,about_club,created_time";

	@SQL(" insert into " + table_name + "( name,logo,contacts,phone,city,address,market_price,centify_price,uncentify_price,description,about_club,created_time )values(:1name,:1:logo,:1.contacts,:1.phone,:1.city,:1.address,:1.marketPrice,:1:centifyPrice,:1.unCentifyPrice,:1.description,:1.aboutClub,now())")
	public void save(GolfClub club);

	
	@SQL(" select "+field+" from " + table_name + " where city=:1 order by created_time desc limit :2,:3 ")
	public List<GolfClub> getGolfClubList(String city,Integer offset,Integer limit);
	
	
	@SQL(" select "+field+" from " + table_name + " where id=:1 ")
	public GolfClub getGolfClub(Integer id);

}
