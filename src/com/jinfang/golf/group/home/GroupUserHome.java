package com.jinfang.golf.group.home;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jinfang.golf.group.dao.GroupUserDAO;
import com.jinfang.golf.group.model.UserGroup;

@Component
public class GroupUserHome {

	@Autowired
	private GroupUserDAO dao;
	
	public void save(UserGroup userGroup) {
		try {
			dao.save(userGroup);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<UserGroup> gets(int groupId, int offset, int limit) {
		try {
			return dao.gets(groupId, offset, limit);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public void delete(int groupId, int userId) {
		try {
			dao.delete(groupId, userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public int getCount(int groupId) {
		try {
			return dao.getCount(groupId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}
