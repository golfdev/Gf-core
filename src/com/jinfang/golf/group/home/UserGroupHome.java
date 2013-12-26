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
	
	/**
	 * 获取用户加入的群组列表
	 * @param userId
	 * @param offset
	 * @param limit
	 * @return 对于用户已经隐藏的则不返回
	 */
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
	public void updateStatus(int userId, int groupId, int status) {
		try {
			dao.updateStatus(userId, groupId, status);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
