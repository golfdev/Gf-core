package com.jinfang.golf.group.manager;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jinfang.golf.cache.redis.RedisCacheManager;
import com.jinfang.golf.cache.redis.RedisCachePool;
import com.jinfang.golf.cache.redis.RedisConstants;
import com.jinfang.golf.group.dao.GroupDAO;
import com.jinfang.golf.group.home.GroupUserHome;
import com.jinfang.golf.group.home.UserGroupHome;
import com.jinfang.golf.group.model.Group;
import com.jinfang.golf.group.model.UserGroup;
import com.jinfang.golf.utils.IdSeqUtils;

@Component
public class GroupManager {
	@Autowired
	private GroupDAO groupDAO;
	@Autowired
	private GroupUserHome groupUserHome;
	@Autowired
	private UserGroupHome userGroupHome;
	/**
	 * 创建群组
	 * @param createrId 
	 * @param name
	 * @param set 群组成员的ID列表，可以不包含创建者的ID
	 * @param type Group.TYPE_TEAM or Group.TYPE_NORMAL
	 * @return
	 */
	public Group createGroup(int createrId, String name, Set<Integer> set, int type) {
		if (set == null) {
			set = new HashSet<Integer>();
		}
		set.add(createrId);
		// TODO 两人的群组，如果重复创建，应该返回同个groupId
		Group group = new Group();
		group.setGroupId(IdSeqUtils.getNextGroupId());
		group.setName(name);
		group.setTime(new Date());
		group.setType(type);
		group.setCreaterId(createrId);
		group.setUserCount(set.size());
		groupDAO.save(group);
		addUser(group.getGroupId(), set);
		return group;
	}
	public void addUser(int groupId, Set<Integer> set) {
		if (set != null && set.size() > 0) {
			for (int userId : set) {
				addUser(groupId, userId);
			}
		}
	}
	public UserGroup addUser(int groupId, int userId) {
		UserGroup userGroup = new UserGroup();
		userGroup.setGroupId(groupId);
		userGroup.setUserId(userId);
		userGroup.setTime(new Date());
		userGroupHome.save(userGroup);
		groupUserHome.save(userGroup);
		// TODO 这样每次都去算，能保证user_count的准确性，但是缺点是比较慢，以后需要优化。
		updateGroupUserCount(groupId);
		return userGroup;
	}
	private void updateGroupUserCount(int groupId) {
		int count = groupUserHome.getCount(groupId);
		if (count > 0) {
			groupDAO.updateCount(groupId, count);
		}
	}
	// TODO 是否只有管理员才有权限删除？
	public void delUser(int groupId, int userId) {
		userGroupHome.delete(userId, groupId);
		groupUserHome.delete(groupId, userId);
		updateGroupUserCount(groupId);
	}
	public List<UserGroup> getUserIds(int groupId) {
		// TODO
		int offset = 0, limit = 1000;
		return groupUserHome.gets(groupId, offset, limit);
	}
	
	public void updateUserGroupStatus(int userId, int groupId, int status) {
		String key = userId + "_" + groupId;
		RedisCachePool pool = RedisCacheManager.getInstance().getRedisPool(RedisConstants.POOL_GROUP);
		try {
			// 删除的就先改数据库，然后存缓存
			if (status == UserGroup.STATUS_HIDE) {
				userGroupHome.updateStatus(userId, groupId, status);
				pool.set(key, String.valueOf(status));
			} else {
				// 如果是改成normal，就读缓存来判断
				Object obj = pool.get(key);
				if (obj != null && Integer.parseInt(obj.toString()) == UserGroup.STATUS_HIDE) {
					pool.del(key);
					userGroupHome.updateStatus(userId, groupId, status);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取两人群的ID
	 * @param uid1
	 * @param uid2
	 * @return
	 */
//	private int getOnlyTwoUserGroup(int uid1, int uid2) {
//		String key = generateKey(uid1, uid2);
//		try {
//			RedisCachePool pool = RedisCacheManager.getInstance().getRedisPool(RedisConstants.POOL_GROUP);
//			String obj = pool.get(key);
//			if (StringUtils.isNotBlank(obj)) {
//				return Integer.parseInt(obj);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return 0;
//	}
//	private void saveTwoUserGroup(int uid1, int uid2, int groupId) {
//		String key = generateKey(uid1, uid2);
//		try {
//			RedisCachePool pool = RedisCacheManager.getInstance().getRedisPool(RedisConstants.POOL_GROUP);
//			pool.set(key, String.valueOf(groupId));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	private void delTwoUserGroup(int uid1, int uid2) {
//		String key = generateKey(uid1, uid2);
//		try {
//			RedisCachePool pool = RedisCacheManager.getInstance().getRedisPool(RedisConstants.POOL_GROUP);
//			pool.del(key);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	private String generateKey(int uid1, int uid2) {
//		return (uid1 < uid2) ? uid1 + "_" + uid2 : uid2 + "_" + uid1;
//	}
}
