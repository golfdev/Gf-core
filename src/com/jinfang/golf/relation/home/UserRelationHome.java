package com.jinfang.golf.relation.home;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jinfang.golf.relation.dao.UserFollowRelationDAO;
import com.jinfang.golf.relation.dao.UserFollowedRelationDAO;
import com.jinfang.golf.relation.dao.UserFriendRelationDAO;
import com.jinfang.golf.relation.model.UserFollowRelation;

@Component
public class UserRelationHome {
    
	@Autowired
	private UserFollowRelationDAO userFollowRelationDAO;
	
	@Autowired
	private UserFollowedRelationDAO userFollowedRelationDAO;
	
	@Autowired
	private UserFriendRelationDAO userFriendRelationDAO;
	
	
	public void removeRelation(Integer fromUid,Integer toUid){
		userFollowRelationDAO.deleteRelation(fromUid, toUid);
		int temp = userFollowRelationDAO.getRelationCount(toUid,fromUid);
		if(temp>0){
			userFollowRelationDAO.updateRelation(toUid, fromUid, 0);
		}
		
		userFollowedRelationDAO.deleteRelation(toUid, fromUid);

	}
	
	public void addRelation(UserFollowRelation userRelation){
		
			Integer temp = userFollowRelationDAO.getRelationCount(userRelation.getGuest(),userRelation.getHost());
			if(temp==0){
				userRelation.setStatus(0);
			}else{
				userRelation.setStatus(1);
				userFollowRelationDAO.updateRelation(userRelation.getGuest(), userRelation.getHost(), 1);
			}
			userFollowRelationDAO.save(userRelation);
			userFollowedRelationDAO.save(userRelation);
	}
	
	public Integer isFollow(Integer host,Integer guest){
		Integer count = userFollowRelationDAO.getRelationCount(host,guest);
		
		if(count!=null&&count>0){
			return 1;
		}else{
			return 0;
		}

	}
	
	public Map<Integer,Integer> isFollowBatch(Integer host,List<Integer> guestList){
		List<Integer> followList = userFollowRelationDAO.getFollowListBatch(host,guestList);
		Map<Integer,Integer> isFollowMap = new HashMap<Integer,Integer>();
		
		for(Integer guest:guestList){
			if(followList.contains(guest)){
				isFollowMap.put(guest, 1);
			}else{
				isFollowMap.put(guest, 0);

			}
		}
		
		return isFollowMap;
	}
	
	public Integer getFollowCount(Integer userId){
		return userFollowRelationDAO.getFollowCountByFromUid(userId);
	}
	
	public Integer getFansCount(Integer userId){
		return userFollowedRelationDAO.getFansCountByToUid(userId);
	}
	
	public Integer getFriendCount(Integer userId){
		return userFollowRelationDAO.getFriendCountByFromUid(userId);
	}
	
	public List<Integer> getFollowList(Integer userId,Integer offset,Integer limit){
		return userFollowRelationDAO.getFollowListByFromUid(userId,offset,limit);
	}
	
	public List<Integer> getFansList(Integer userId,Integer offset,Integer limit){
		return userFollowedRelationDAO.getFansListByToUid(userId,offset,limit);
	}
	
	public List<Integer> getFriendList(Integer userId,Integer offset,Integer limit){
		return userFollowRelationDAO.getFriendListByFromUid(userId,offset,limit);

	}
	
	
	

}
