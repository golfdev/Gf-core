package com.jinfang.golf.feed.home;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jinfang.golf.feed.dao.GolfFeedDAO;
import com.jinfang.golf.feed.model.GolfFeed;
import com.jinfang.golf.relation.home.UserRelationHome;

@Component
public class GolfFeedHome {
    
	@Autowired
	private GolfFeedDAO golfFeedDAO;
	
	@Autowired
	private UserRelationHome userRelationHome;
	
	
	public void publish(GolfFeed feed){
		golfFeedDAO.save(feed);
	}
	

	public List<GolfFeed> getGolfFeedList(Integer userId){
		List<Integer> followList = userRelationHome.getFollowList(userId,0,1000);
		return golfFeedDAO.getUserFeedList(followList);
	}
	
	
	
}
