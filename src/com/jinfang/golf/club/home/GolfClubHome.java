package com.jinfang.golf.club.home;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jinfang.golf.club.dao.GolfClubDAO;
import com.jinfang.golf.club.model.GolfClub;
import com.jinfang.golf.feed.model.GolfFeed;

@Component
public class GolfClubHome {
    
	@Autowired
	private GolfClubDAO golfClubDAO;
	
	
	
	public GolfClub getGolfClubById(Integer id){
		return golfClubDAO.getGolfClub(id);
	}
	

	public List<GolfClub> getGolfClubList(String city,Integer offset,Integer limit){
		List<GolfClub> clubList = golfClubDAO.getGolfClubList(city, offset, limit);
		return clubList;
	}
	
	
	
}
