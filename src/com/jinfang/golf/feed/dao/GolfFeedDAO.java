package com.jinfang.golf.feed.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import com.jinfang.golf.feed.model.GolfFeed;

@DAO(catalog = "golf_app")
public interface GolfFeedDAO {
	//数据表名
	public static String table_name = "golf_feed";
	//数据表字段
	public static String field = "user_id,user_head,content,img_url,audio_url,source,created_time";

	@SQL(" insert into " + table_name + "( " + field + " )value(:1.userId,:1.userHead,:1.content,:1.imgUrl,:1.audioUrl,:1.source,now())")
	public void save(GolfFeed feed);

	
	@SQL(" select "+field+" from " + table_name + " where user_id in (:1) ")
	public List<GolfFeed> getUserFeedList(List<Integer> feedList);
	

}
