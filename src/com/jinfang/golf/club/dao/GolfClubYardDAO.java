package com.jinfang.golf.club.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import com.jinfang.golf.club.model.GolfClubYard;

@DAO(catalog = "golf_app")
public interface GolfClubYardDAO {
	//数据表名
	public static String table_name = "golf_club_yard";
	//数据表字段
	public static String field = "club_id,hole_num,par_score,created_time";

	@SQL(" insert into " + table_name + "( club_id,hole_num,par_score,created_time )values(:1.clubId,:1.holeNum,:1.parScore,now())")
	public void save(GolfClubYard yard);

	
	@SQL(" select "+field+" from " + table_name + " where club_id=:1 order by hole_num")
	public List<GolfClubYard> getGolfClubYardList(Integer clubId);
	

}
