package com.jinfang.golf.group.home;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jinfang.golf.group.dao.GroupDAO;
import com.jinfang.golf.group.model.Group;

@Component
public class GroupHome {

	@Autowired
	private GroupDAO dao;
	
	public void save(Group group) {
		try {
			dao.save(group);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    public Group get(int groupId){
    	try {
    		return dao.get(groupId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
	/**
	 * 按照群组最后更新时间的倒叙排列
	 * @param groupIds
	 * @return
	 */
	public Map<Integer, Group> getByIds(List<Integer> groupIds) {
		try {
			return dao.getByIds(groupIds);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public void updateName(int groupId, String name) {
		try {
			dao.updateName(groupId, name);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void updateLastUse(int groupId, int userId, String text, Date time) {
		try {
			dao.updateLastUse(groupId, userId, text, time);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void updateCount(int groupId, int count) {
		try {
			dao.updateCount(groupId, count);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
