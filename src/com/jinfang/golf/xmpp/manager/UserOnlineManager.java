package com.jinfang.golf.xmpp.manager;

import com.jinfang.golf.cache.redis.RedisCacheManager;
import com.jinfang.golf.cache.redis.RedisCachePool;
import com.jinfang.golf.cache.redis.RedisConstants;
import com.jinfang.golf.utils.IpAddressUtils;
import com.jinfang.golf.xmpp.model.UserOnline;

/**
 * 用户在线状态的管理器
 * @author RamosLi
 *
 */
public class UserOnlineManager {
	private static UserOnlineManager instance = new UserOnlineManager();
	public static UserOnlineManager getInstance() {
		return instance;
	}
	/**
	 * 设置用户在线
	 * @param userId
	 */
	public boolean setOnline(long userId, long appId) {
		UserOnline online = new UserOnline();
		online.setUserId(userId);
		online.setAppId(appId);
		online.setOnlineTime(System.currentTimeMillis());
		String ip = IpAddressUtils.getLocalAddress();
		online.setServerIp(ip);
		int port = 5222;
		online.setServerPort(port);
		int status = 1;
		online.setStatus(status);
		try {
			RedisCachePool pool = RedisCacheManager.getInstance().getRedisPool(RedisConstants.POOL_XMPP);
			String key = generateKey(userId);
			pool.setObject(key, online);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 用户下线了
	 * @param userId
	 */
	public boolean offLine(long userId) {
		try {
			RedisCachePool pool = RedisCacheManager.getInstance().getRedisPool(RedisConstants.POOL_XMPP);
			String key = generateKey(userId);
			pool.del(key);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 获取用户的在线信息
	 * @param userId
	 * @return
	 */
	public UserOnline get(long userId) {
		try {
			RedisCachePool pool = RedisCacheManager.getInstance().getRedisPool(RedisConstants.POOL_XMPP);
			String key = generateKey(userId);
			UserOnline online = new UserOnline();
			return (UserOnline)pool.getObject(key, online);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 判断是否在线
	 * @param userId
	 * @return
	 */
	public boolean isOnline(long userId) {
		return get(userId) != null;
	}
	
	private String generateKey(long userId) {
		return "xmpp_" + String.valueOf(userId);
	}
	public static void main(String[] args) {
		long userId = 562949953487639L;
		UserOnline online = UserOnlineManager.getInstance().get(userId);
		System.out.println("===========");
		System.out.println(online.getServerIp());
	}
}
