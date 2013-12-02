package com.jinfang.golf.cache.redis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.thrift.TBase;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author RamosLi
 *
 */
public class RedisCachePool<T extends TBase> {
	private static Log logger = LogFactory.getLog(RedisCachePool.class);
	private JedisPool pool;
	public RedisCachePool(JedisPool pool) {
		this.pool = pool;
	}
	public String get(String key) {
		checkNotNull(key);
		boolean broken = false;
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.get(key);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			if (jedis != null) {
				if (broken) {
					pool.returnBrokenResource(jedis);
				} else {
					pool.returnResource(jedis);
				}
			}
		}
		return null;
	}
	public List<String> getList(List<String> keys) {
		checkNotNull(keys);
		String[] array = new String[keys.size()];
		int i = 0;
		for (String key : keys) {
			array[i] = key;
			i++;
		}
		return getList(array);
	}
	public List<String> getList(String[] keys) {
		checkNotNull(keys);
		boolean broken = false;
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.mget(keys);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			if (jedis != null) {
				if (broken) {
					pool.returnBrokenResource(jedis);
				} else {
					pool.returnResource(jedis);
				}
			}
		}
		return null;
	}
	public boolean set(String key, String value) {
		return set(key, value, -1);
	}
	public boolean set(String key, String value, int expire) {
		checkNotNull(key);
		checkNotNull(value);
		boolean broken = false;
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			String ret = null;
			if (expire > 0) {
				ret = jedis.setex(key, expire, value);
			} else {
				ret = jedis.set(key, value);
			}
			return isSuccess(ret);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			if (jedis != null) {
				if (broken) {
					pool.returnBrokenResource(jedis);
				} else {
					pool.returnResource(jedis);
				}
			}
		}
		return false;
	}
	public boolean setObject(String key, T value) {
		return setObject(key, value, -1);
	}
	public boolean setObject(String key, T value, int expire) {
		checkNotNull(key);
		checkNotNull(value);
		boolean broken = false;
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			byte[] bytes = Serializer.serialize(value);
			String ret = null;
			if (expire > 0) {
				ret = jedis.setex(getBytes(key), expire, bytes);
			} else {
				ret = jedis.set(getBytes(key), bytes);
			}
			return isSuccess(ret);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			if (jedis != null) {
				if (broken) {
					pool.returnBrokenResource(jedis);
				} else {
					pool.returnResource(jedis);
				}
			}
		}
		return false;
	}
	public TBase getObject(String key, T object) {
		checkNotNull(key);
		checkNotNull(object);
		boolean broken = false;
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			Object obj = jedis.get(getBytes(key));
			if (obj != null) {
				byte[] value = (byte[])obj;
				return Serializer.deserialze(value, object);
			}
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			if (jedis != null) {
				if (broken) {
					pool.returnBrokenResource(jedis);
				} else {
					pool.returnResource(jedis);
				}
			}
		}
		return null;
	}
	public List<T> getObjectList(String[] keys, T object) {
		checkNotNull(keys);
		checkNotNull(object);
		byte[][] keysByte = new byte[keys.length][];
		int i = 0;
		for (String key : keys) {
			keysByte[i] = getBytes(key);
			i++;
		}
		return getObjectList(keysByte, object);
	}
	public List<T> getObjectList(List<String> keys, T object) {
		checkNotNull(keys);
		checkNotNull(object);
		byte[][] keysByte = new byte[keys.size()][];
		int i = 0;
		for (String key : keys) {
			keysByte[i] = getBytes(key);
			i++;
		}
		return getObjectList(keysByte, object);
	}
	public Map<String, TBase> getObjectMap(List<String> keys, T object) {
		checkNotNull(keys);
		checkNotNull(object);
		byte[][] keysByte = new byte[keys.size()][];
		int i = 0;
		for (String key : keys) {
			keysByte[i] = getBytes(key);
			i++;
		}
		List<T> list = getObjectList(keysByte, object);
		if (list != null) {
			if (keys.size() == list.size()) {
				Map<String, TBase> map = new HashMap<String, TBase>();
				for (int j = 0; j < keys.size(); j++) {
					map.put(keys.get(j), list.get(j));
				}
				return map;
			} else {
				logger.error("Query object from redis. keys' lenght is different from values' length. keys: " + keys);
			}
		} else {
			logger.error("Query object from redis. values is NULL! keys: " + keys);
		}
		return null;
	}
	public List<T> getObjectList(byte[][] keys, T object) {
		checkNotNull(keys);
		checkNotNull(object);
		boolean broken = false;
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			List<byte[]> values = jedis.mget(keys);
			return byteList2ObjectList(values, object);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			if (jedis != null) {
				if (broken) {
					pool.returnBrokenResource(jedis);
				} else {
					pool.returnResource(jedis);
				}
			}
		}
		return null;
	}
	public List<String> lrange(String key, int start, int end) {
		checkNotNull(key);
		boolean broken = false;
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.lrange(key, start, end);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			if (jedis != null) {
				if (broken) {
					pool.returnBrokenResource(jedis);
				} else {
					pool.returnResource(jedis);
				}
			}
		}
		return null;
	}
	public long rpush(String key, String value) {
		checkNotNull(key);
		checkNotNull(value);
		boolean broken = false;
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.rpush(key, value);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			if (jedis != null) {
				if (broken) {
					pool.returnBrokenResource(jedis);
				} else {
					pool.returnResource(jedis);
				}
			}
		}
		return 0;
	}
	public List<T> lrange(String key, int start, int end, T object) {
		checkNotNull(key);
		boolean broken = false;
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			List<byte[]> list = jedis.lrange(getBytes(key), start, end);
			return byteList2ObjectList(list, object);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			if (jedis != null) {
				if (broken) {
					pool.returnBrokenResource(jedis);
				} else {
					pool.returnResource(jedis);
				}
			}
		}
		return null;
	}
	public long rpush(String key, T object) {
		checkNotNull(key);
		checkNotNull(object);
		boolean broken = false;
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			byte[] bytes = Serializer.serialize(object);
			return jedis.rpush(getBytes(key), bytes);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			if (jedis != null) {
				if (broken) {
					pool.returnBrokenResource(jedis);
				} else {
					pool.returnResource(jedis);
				}
			}
		}
		return 0;
	}
	public long lpush(String key, T object) {
		checkNotNull(key);
		checkNotNull(object);
		boolean broken = false;
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			byte[] bytes = Serializer.serialize(object);
			return jedis.lpush(getBytes(key), bytes);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			if (jedis != null) {
				if (broken) {
					pool.returnBrokenResource(jedis);
				} else {
					pool.returnResource(jedis);
				}
			}
		}
		return 0;
	}
	public void lpop(String key) {
		checkNotNull(key);
		boolean broken = false;
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.lpop(key);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			if (jedis != null) {
				if (broken) {
					pool.returnBrokenResource(jedis);
				} else {
					pool.returnResource(jedis);
				}
			}
		}
	}
	public void rpop(String key) {
		checkNotNull(key);
		boolean broken = false;
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.rpop(key);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			if (jedis != null) {
				if (broken) {
					pool.returnBrokenResource(jedis);
				} else {
					pool.returnResource(jedis);
				}
			}
		}
	}
	public long incrby(String key, long count) {
		checkNotNull(key);
		checkNotNull(count);
		boolean broken = false;
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.incrBy(key, count);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			if (jedis != null) {
				if (broken) {
					pool.returnBrokenResource(jedis);
				} else {
					pool.returnResource(jedis);
				}
			}
		}
		return 0;
	}
	public void del(String... key) {
		checkNotNull(key);
		boolean broken = false;
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.del(key);
		} catch (Exception e) {
			broken = true;
			e.printStackTrace();
		} finally {
			if (jedis != null) {
				if (broken) {
					pool.returnBrokenResource(jedis);
				} else {
					pool.returnResource(jedis);
				}
			}
		}
	}
	private byte[] getBytes(String content) {
		return StringUtils.getBytesUtf8(content);
	}
	private String bytesToString(byte[] bytes) {
		return StringUtils.newStringUtf8(bytes);
	}
	private boolean isSuccess(String ret) {
		if ("OK".equalsIgnoreCase(ret)) {
			return true;
		}
		return false;
	}
	private void checkNotNull(Object object) {
		if (object == null) {
			throw new IllegalArgumentException("Parameter can't be NULL!");
		}
	}
	private List<T> byteList2ObjectList(List<byte[]> values, T object) {
		List list = null;
		if (values != null && values.size() > 0) {
			list = new ArrayList(values.size());
			for (byte[] bytes : values) {
				TBase tBase = object.deepCopy();
				tBase.clear();
				list.add(Serializer.deserialze(bytes, tBase));
			}
		}
		return list;
	}
	public static void main(String[] args) throws Exception {
		String key = "comment";
//		RedisCachePool pool = RedisCacheManager.getInstance().getRedisPool("");
//		Comment comment = new Comment();
//		comment.setSubjectId(12312312);
//		comment.setCommentId(1111111111);
//		comment.setUserId(222222222222L);
//		comment.setTime(System.currentTimeMillis());
//		comment.setContent("1asfsdfasdfasdf");
//		pool.setObject(key, comment);
//		Comment comment = (Comment)pool.getObject(key, new Comment());
		
//		System.out.println(comment.getContent());
		byte[] bs = new byte[1];
		Byte[] bs1 = new Byte[1];
		System.out.println(key.getClass().getName());
		System.out.println(bs);
		System.out.println(bs1);
		System.out.println("OK!!");
	}
}
