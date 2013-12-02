package com.jinfang.golf.cache.redis;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

/**
 * @author RamosLi
 */
public class RedisCacheManager {
	// 现在就一个池，直接写在这儿。等多个时就用map管理
//	private static JedisPool jedisPool = null;
	private static Map<String, JedisPool> POOL_MAPS = new HashMap<String, JedisPool>(10);
	private static RedisCacheManager instance = new RedisCacheManager();
	public static RedisCacheManager getInstance() {
		return instance;
	}
	private RedisCacheManager() {
	}
	private JedisPool createJedis(String poolName) {
		int db = RedisConstants.getDatabase(poolName);
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxActive(50);
		config.setMaxIdle(10 * 1);
//		config.setMaxWait(100);
		config.setTestOnBorrow(false);
		config.setTestOnReturn(false);
		JedisPool jedis = new JedisPool(config, RedisConstants.getDefaultHost(), RedisConstants.DEFAULT_PORT,
				Protocol.DEFAULT_TIMEOUT, null, db);
		return jedis;
	}
	
	public RedisCachePool getRedisPool(String poolName) {
		JedisPool jedis = POOL_MAPS.get(poolName);
		if (jedis == null) {
			synchronized (poolName.intern()) {
				jedis = POOL_MAPS.get(poolName);
				if (jedis == null) {
					jedis = createJedis(poolName);
					POOL_MAPS.put(poolName, jedis);
				}
			}
		}
		return new RedisCachePool(jedis);
	}
	
	public static void main(String[] args) throws Exception {
		Executor executor = Executors.newFixedThreadPool(10);
		RedisCachePool pool = getInstance().getRedisPool("default");
		final String key = "redis_key_test";
		
		System.out.println(pool.set(key, "一二三四"));
		System.exit(0);
		for (int i = 0; i < 1000; i++) {
			final int j = i;
			executor.execute(new Runnable() {
				@Override
				public void run() {
					try {
						getInstance().getRedisPool("").get(key);
						System.out.println(j);
						Thread.sleep(100);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
		System.out.println(pool.get(key));
	}
}
