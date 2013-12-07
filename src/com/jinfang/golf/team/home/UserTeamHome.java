package com.jinfang.golf.team.home;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jinfang.golf.team.dao.GolfTeamDAO;
import com.jinfang.golf.team.dao.UserTeamRelationDAO;
import com.jinfang.golf.team.model.GolfTeam;

@Component
public class UserTeamHome {
    

    @Autowired
    private GolfTeamDAO golfTeamDAO;

	@Autowired
	private UserTeamRelationDAO userTeamRelationDAO;
	
	
	public void removeFromTeam(Integer userId,Integer teamId){
	    userTeamRelationDAO.deleteByUserIdAndTeamId(userId, teamId);
	}
	
	public void createGolfTeam(GolfTeam team){
	    golfTeamDAO.save(team);
	}
	
	public List<GolfTeam> getGolfTeamList(String city){
	    return golfTeamDAO.getGolfTeamList(city);
	}
	
	public GolfTeam getGolfTeamById(Integer id){
	    return golfTeamDAO.getGolfTeamById(id);
	}
	
	public void updateGolfTeam(GolfTeam team){
		golfTeamDAO.updateGolfTeam(team);
	}
	
//	public List<User> getTeamMemberListById(Integer id){
//	    
//	}
	

}
