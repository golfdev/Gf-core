package com.jinfang.golf.relation.home;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jinfang.golf.relation.dao.UserRelationDAO;
import com.jinfang.golf.relation.model.UserRelation;

@Component
public class UserRelationHome {
    
	@Autowired
	private UserRelationDAO userRelationDAO;
	
	
	public void removeRelation(Integer fromUid,Integer toUid){
	    userRelationDAO.deleteRelation(fromUid, toUid);
		int temp = userRelationDAO.getUserRelation(toUid,fromUid);
		if(temp>0){
			userRelationDAO.updateStatus(toUid, fromUid, 0);
		}

	}
	
	public void addRelation(UserRelation userRelation){
		
		int count = userRelationDAO.getUserRelation(userRelation.getFromUid(),userRelation.getToUid());
		if(count==0){
			int temp = userRelationDAO.getUserRelation(userRelation.getToUid(),userRelation.getFromUid());
			if(temp==0){
				userRelation.setStatus(0);
			}else{
				userRelation.setStatus(1);
				userRelationDAO.updateStatus(userRelation.getToUid(), userRelation.getFromUid(), 1);
			}
			userRelationDAO.save(userRelation);
		}
		
	}
	
	public Integer getFollowCount(Integer userId){
		return userRelationDAO.getFollowCountByFromUid(userId);
	}
	
	public Integer getFansCount(Integer userId){
		return userRelationDAO.getFansCountByToUid(userId);
	}
	
	public Integer getFriendCount(Integer userId){
		return userRelationDAO.getFansCountByToUid(userId);
	}
	
	public Integer getFriendCountByFromUid(Integer userId){
		return userRelationDAO.getFriendCountByFromUid(userId);
	}
	
	public List<Integer> getFollowList(Integer userId){
		return userRelationDAO.getFollowListByFromUid(userId);
	}
	
	
	

}
