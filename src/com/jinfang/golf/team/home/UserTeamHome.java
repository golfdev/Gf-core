package com.jinfang.golf.team.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jinfang.golf.team.dao.UserTeamRelationDAO;

@Component
public class UserTeamHome {
    


	@Autowired
	private UserTeamRelationDAO userTeamRelationDAO;
	
	
	public void removeFromTeam(Integer userId,Integer teamId){
	    userTeamRelationDAO.deleteByUserIdAndTeamId(userId, teamId);
	}
	

}
