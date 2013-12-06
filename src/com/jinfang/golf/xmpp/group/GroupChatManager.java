package com.jinfang.golf.xmpp.group;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jinfang.golf.cache.redis.RedisCacheManager;
import com.jinfang.golf.cache.redis.RedisCachePool;
import com.jinfang.golf.cache.redis.RedisConstants;

public class GroupChatManager {
	private static GroupChatManager instance = new GroupChatManager();
	public static GroupChatManager getInstance() {
		return instance;
	}
	public List<Integer> getGroupUsers(int groupId) {
		try {
			RedisCachePool pool = RedisCacheManager.getInstance().getRedisPool(RedisConstants.POOL_GROUP);
			String key = "group_" + groupId;
			String userIds = pool.get(key);
			if (StringUtils.isNotBlank(userIds)) {
				List<Integer> list = new LinkedList<Integer>();
				for (String id : userIds.split(",")) {
					list.add(Integer.parseInt(id));
				}
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
