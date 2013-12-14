package com.jinfang.golf.utils;

import com.jinfang.golf.cache.redis.RedisCacheManager;
import com.jinfang.golf.cache.redis.RedisCachePool;
import com.jinfang.golf.cache.redis.RedisConstants;

public class IdSeqUtils {
	
	public static String getNextMessageId() {
		String key = "audio_id_seq";
		return String.valueOf(getNextId(key));
	}
	
	public static int getNextAudioId() {
		String key = "audio_id_seq";
		return getNextId(key);
	}

	public static int getNextPhotoId() {
		String key = "photo_id_seq";
		return getNextId(key);
	}

	public static int getNextGroupId() {
		String key = "group_id_seq";
		return getNextId(key);
	}

	private static int getNextId(String key) {
		try {
			RedisCachePool pool = RedisCacheManager.getInstance().getRedisPool(
					RedisConstants.POOL_COUNT);
			return (int) pool.incrby(key, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}
