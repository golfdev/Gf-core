package com.jinfang.golf.relation.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jinfang.golf.relation.dao.UserRelationDAO;

@Component
public class UserRelationHome {
    


	@Autowired
	private UserRelationDAO userRelationDAO;
	
	
	public void removeRelation(Integer fromUid,Integer toUid){
	    userRelationDAO.deleteRelation(fromUid, toUid);
	}
	
	

}
