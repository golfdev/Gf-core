package com.jinfang.golf.cache.redis;

import java.util.HashMap;
import java.util.Map;

public class RedisConstants {
	public static final String POOL_XMPP = "xmpp";
	public static final String POOL_COUNT = "count";
	public static final String POOL_LOCK = "lock";
	public static final String POOL_MESSAGE_QUEUE = "message_queue";
	public static final String POOL_GROUP = "group";
	public static final int DEFAULT_PORT = 6379; 
	private static Map<String, Integer> DB_MAPS = new HashMap<String, Integer>();
	static {
		DB_MAPS.put(POOL_XMPP, 0);
		DB_MAPS.put(POOL_COUNT, 1);
		DB_MAPS.put(POOL_MESSAGE_QUEUE, 2);
		DB_MAPS.put(POOL_GROUP, 3);
		DB_MAPS.put(POOL_LOCK, 4);


	}
	
	public static String getDefaultHost() {
		if (System.getProperties().getProperty("os.name").toLowerCase().contains("windows")) {
			return "127.0.0.1";
		} else {
			return "127.0.0.1";
		}
	}
	public static int getDatabase(String pool) {
		Integer db = DB_MAPS.get(pool);
		if (db == null) {
			return 0;
		} else {
			return db.intValue();
		}
	}
}
