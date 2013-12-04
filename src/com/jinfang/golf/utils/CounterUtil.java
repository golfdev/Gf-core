package com.jinfang.golf.utils;

import org.apache.commons.lang.math.NumberUtils;

import com.jinfang.golf.cache.redis.RedisCacheManager;
import com.jinfang.golf.cache.redis.RedisCachePool;
import com.jinfang.golf.cache.redis.RedisConstants;


public class CounterUtil {
	
	private static RedisCachePool jedisPool = RedisCacheManager.getInstance().getRedisPool(RedisConstants.POOL_COUNT);

    /**
     * 取得计数器的值  
     * @param key
     * @return
     */
    public static int getValue(String key) {
        return NumberUtils.toInt(jedisPool.get(key));
    }
    
  
	/**
	 * 自增
	 * @param key
	 * @return
	 */
	public static int inc(String key) {
        return (int) jedisPool.incrby(key, 1);
	}
	
	/**
	 * 自减
	 * @param key
	 * @return
	 */
	public static int dec(String key) {
        return (int) jedisPool.incrby(key, -1);
	}
	
	/**
	 * 重置
	 * @param key
	 */
	public void reset(String key) {
          jedisPool.set(key, "0");
	}
	
    
}
