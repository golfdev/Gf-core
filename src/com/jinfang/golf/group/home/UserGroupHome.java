package com.jinfang.golf.group.home;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jinfang.golf.group.dao.UserGroupDAO;
import com.jinfang.golf.group.model.UserGroup;

@Component
public class UserGroupHome {

	@Autowired
	private UserGroupDAO dao;
	
	public void save(UserGroup userGroup) {
		try {
			dao.save(userGroup);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<UserGroup> gets(int userId, int offset, int limit) {
		try {
			return dao.gets(userId, offset, limit);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public void delete(int userId, int groupId) {
		try {
			dao.delete(userId, groupId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public UserGroup get(int userId, int groupId) {
		try {
			return dao.get(userId, groupId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
