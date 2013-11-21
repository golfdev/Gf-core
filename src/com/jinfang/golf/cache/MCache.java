package com.jinfang.golf.cache;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import net.spy.memcached.CASResponse;
import net.spy.memcached.CASValue;
import net.spy.memcached.MemcachedClient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MCache<T> {

	private static Log logger = LogFactory.getLog(MCache.class);
			
	public static final int EXPIRE_SECOND = 1;

	public static final int EXPIRE_MINUTE = 60;

	public static final int EXPIRE_HOUR = 60 * EXPIRE_MINUTE;

	public static final int EXPIRE_DAY = 24 * EXPIRE_HOUR;

	public static final char NT_SEPARATOR = '.';

	public static final char TI_SEPARATOR = ':';

	public static final int MAX_EXPIRE = EXPIRE_DAY * 30;
	
	
	public static final String POOL_CORE = "golf_core";
	
	
	
	/** Cache池名，相当于大类 */
	private final String poolname;

	/** keyPrefix = poolname + NT_SEPARATOR + type */
	private final String keyPrefix;

	private final Class<T> valueClass;

	private boolean isCounter;
	
	private static ValueCaster valueCaster = new ValueCaster();


	/**
	 * 构造MCache实例。
	 * <p>
	 * 
	 * 参数prefix表示该cache的使用策略，由两部分组成poolname和type，并通过点号连接，通过这个prefix，
	 * MCache将从配置服务器中获取相关的使用策略。
	 * <p>
	 * 合法的prefix，非空且只能是由数字、字母、下划线、连接线以及点号组成，如：home.username、app.type<br>
	 * 非法的prefix，将抛出 {@link IllegalArgumentException}异常，并在异常信息中进行说明
	 * <p>
	 * 
	 * 您在使用MCache前，必须确认prefix已经在登记在wiki中，如果还未登录，请参考wiki:
	 * http://wiki.rnrn.jp/index.php?title=%E5%AF%B9%E4%BA%8EMemcached%E7%9A%84Key%E7%BA%A6%E5%AE%9A[wiki地址]
	 * 
	 * @throws {@link NullPointerException}
	 * @throws {@link IllegalArgumentException}
	 * @param prefix
	 *            key prefix
	 * @param cacheFactory
	 *            xcache factory
	 * @param valueClass
	 *            value的类型
	 * @param isCounter
	 *            当前MCache实例是否用作Counter
	 */
	public MCache(String prefix, Class<T> valueClass, boolean isCounter) {
		// not null
		if (prefix == null) {
			throw new NullPointerException("prefix");
		}
		prefix = prefix.trim();
		// ':' not allowed
		if (prefix.indexOf(TI_SEPARATOR) != -1) {
			throw new IllegalArgumentException("wrong prefix '" + prefix
					+ "': char '" + TI_SEPARATOR + "' is not allowed ");
		}
		// '.' 在其中
		int index = prefix.indexOf(NT_SEPARATOR);
		if (index <= 0 || index == prefix.length() - 1) {
			throw new IllegalArgumentException("wrong prefix '" + prefix
					+ "': char '" + NT_SEPARATOR + "' is needed");
		}
		// not duplicated
		if (prefix.indexOf(NT_SEPARATOR, index + 1) != -1) {
			throw new IllegalArgumentException("wrong prefix '" + prefix
					+ "': char '" + NT_SEPARATOR + "' duplicated");
		}
		// legal chars
		for (int i = 0; i < prefix.length(); i++) {
			char ch = prefix.charAt(i);
			if (ch >= '0' && ch <= '9') {
				continue;
			}
			if (ch >= 'a' && ch <= 'z') {
				continue;
			}
			if (ch == NT_SEPARATOR || ch == '-' || ch == '_') {
				continue;
			}
			if (ch >= 'A' && ch <= 'Z') {
				continue;
			}

			throw new IllegalArgumentException("wrong prefix '" + prefix
					+ "': char '" + ch + "' is not allowed");
		}
		// ok
		this.poolname = prefix.substring(0, index); // poolname is the first
														// part of prefix
		this.keyPrefix = prefix;
		this.valueClass = valueClass;
		this.isCounter = isCounter;

		// 类型判断
		if (isCounter && !isCounterType(valueClass)) {
			throw new IllegalArgumentException(
					"For a counter, the value type must be Long or Integer, but yours is: "
							+ valueClass);
		}

		//client = MemcachedClientFactory.getInstance().getClientByPoolname(poolname);
	}
	
	private MemcachedClient getClient(){
		return MemcachedClientFactory.getInstance().getClientByPoolname(poolname);
	}

	private static <T> MCache<T> getCache(String poolname, String cacheType,
			Class<T> clazz, boolean isCounter) {
		String prefix = poolname + NT_SEPARATOR + cacheType;
		return new MCache<T>(prefix, clazz, isCounter);
	}

	public static <T> MCache<T> getCache(String poolname, String cacheType,
			Class<T> clazz) {
		return getCache(poolname, cacheType, clazz, false);
	}
	
	/**
	 * 判断类型是否可以用作counter，只支持Integer和Long
	 * 
	 * @param type
	 * @return
	 */
	private boolean isCounterType(Class<?> type) {
		return type.equals(Long.class) || type.equals(Integer.class) || type.equals(String.class);
	}

	private void ensureOpen() throws IOException {
		if (getClient() == null)
			throw new IOException("Client is closed!");
	}
	
	private void checkValueType(Object value) {
		if (value != null && !valueClass.isAssignableFrom(value.getClass())) {
			throw new IllegalArgumentException("Value type must be '"
					+ valueClass.getName() + "' or its subclass, but it's '"
					+ value.getClass().getName() + "'");
		}
	}
	
	public boolean add(long id, T value){
		return add(String.valueOf(id), value, MAX_EXPIRE);//最大存30天
	}
	
	public boolean add(String id, T value){
		return add(id, value, MAX_EXPIRE);//最大存30天
	}

	public boolean add(long id, T value, int expire){
		return add(String.valueOf(id), value, expire);
	}
	
	public boolean add(String id, Object value, int expire) {
		boolean suc = false;
		try {
			ensureOpen();
			checkValueType(value);
			String key = generateKey(id);
			Object finalValue = value;
			if (this.isCounter()) {
				// 因为在构造函数里已经判断值的类型为Integer或Long了，所以这里可以放心地转为String
				finalValue = "" + value;
			}
			
			Future<Boolean> f = getClient().add(key, expire, finalValue);
			
			try {
				suc = f.get(5, TimeUnit.SECONDS);
				
				if (!suc) { //一般不发生
					logger.error(String.format("memcacheFail(add),key=%s", key));
					
				}
			} catch (TimeoutException e) {
				logger.error(String.format("memcacheOpTimeout(add),key=%s", key));
				f.cancel(false);
			}
		} catch (Exception e) {
			logger.error("memcached op error", e);
			
		}
		return suc;
	}
	
	public boolean set(long id, T value){
		return set(String.valueOf(id), value, MAX_EXPIRE);
	}
	
	public boolean set(String id, T value){
		return set(id, value, MAX_EXPIRE);
	}
	
	public boolean set(long id, T value, int expire){
		return set(String.valueOf(id), value, expire);
	}

	public boolean set(String id, T value, int expire)  {
		boolean suc = false;
		try {
			ensureOpen();
			checkValueType(value);
			String key = generateKey(id);
			Object finalValue = value;
			if (this.isCounter()) {
				// 因为在构造函数里已经判断值的类型为Integer或Long了，所以这里可以放心地转为String
				finalValue = "" + value;
			}
			
			Future<Boolean> f = getClient().set(key, expire, finalValue);
			
			try {
				suc = f.get(5, TimeUnit.SECONDS);
				
				if (!suc) { //一般不发生
					logger.error(String.format("memcacheFail(set),key=%s", key));
					
				}
			} catch (TimeoutException e) {
				logger.error(String.format("memcacheOpTimeout(set),key=%s", key));
				f.cancel(false);
			}
		} catch (Exception e) {
			logger.error("memcached op error", e);
		}
		return suc;
	}
	
	public boolean replace(long id, T value, int expire) {
		return replace(String.valueOf(id), value, expire);
	}
	
	public boolean replace(String id, T value, int expire) {
		boolean suc = false;
		try {
			ensureOpen();
			checkValueType(value);
			String key = generateKey(id);
			Object finalValue = value;
			if (this.isCounter()) {
				// 因为在构造函数里已经判断值的类型为Integer或Long了，所以这里可以放心地转为String
				finalValue = "" + value;
			}
			Future<Boolean> f = getClient().replace(key, expire, finalValue);
			
			try {
				suc = f.get(5, TimeUnit.SECONDS);
				
				if (!suc) { //一般不发生
					logger.error(String.format("memcacheFail(replace),key=%s", key));
				}
			} catch (TimeoutException e) {
				logger.error(String.format("memcacheOpTimeout(replace),key=%s", key));
				f.cancel(false);
			}
		} catch (Exception e) {
			logger.error("memcached op error", e);
			
		}
		return suc;
	}

	public T get(long id){
		return get(String.valueOf(id));
	}

	public T get(String id) {
		T val = null;
		try {
			ensureOpen();
			String key = generateKey(id);
			Object obj = getClient().get(key);
			
			if(obj!=null && this.isCounter){
				val = valueCaster.cast(valueClass, obj);
				return val;
			}
			
			if(obj != null && valueClass.isInstance(obj)){
				
				val = valueCaster.cast(valueClass, obj);
				return val;
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error("memcached op error", e);
			
		}
		return val;
	}
	
	public boolean delete(long id){
		return delete(String.valueOf(id));
	}

	public boolean delete(String id) {
		String key = generateKey(id);
		try {
			Future<Boolean> f = getClient().delete(key);
			try {
				boolean suc = f.get(5, TimeUnit.SECONDS);
				
				if (!suc) { //一般不发生
					logger.error(String.format("memcacheFail(delete),key=%s", key));
				}
				return suc;
			} catch (TimeoutException e) {
				logger.error(String.format("memcacheOpTimeout(delete),key=%s", key));
				f.cancel(false);
				return false;
			}
		} catch (Exception e) {
			throw new RuntimeException("Error while operating on key-prefix: "
					+ keyPrefix, e);
		}
	}

	public long incr(String id, long inc) {
		String key = generateKey(id);
		try {
			ensureOpen();
			return getClient().incr(key, inc);
		} catch (Exception e) {
			throw new RuntimeException("Error while operating on key-prefix: "
					+ keyPrefix, e);
		}
	}

	public long decr(String id, long inc) {
		String key = generateKey(id);
		try {
			ensureOpen();
			return getClient().decr(key, inc);
		} catch (Exception e) {
			throw new RuntimeException("Error while operating on key-prefix: "
					+ keyPrefix, e);
		}
	}
	

	public Map<String, T> getMulti(Collection<String> idList) {
		List<String> keyList = new ArrayList<String>(idList.size());
		for (String id : idList) {
			keyList.add(generateKey(id));
		}

		try {
			ensureOpen();
			Map<String, Object> originalRet = getClient().getBulk(keyList);
			if (originalRet == null) { // 正常情况下是不会为null的，这里以防万一
				return Collections.emptyMap();
			}
			Map<String, T> map = new HashMap<String, T>(originalRet.size());

			for (Entry<String, Object> entry : originalRet.entrySet()) {
				String id = resolveIdFromKey(entry.getKey());
				
				if(entry.getValue() != null && this.isCounter) {
					T val = valueCaster.cast(valueClass, entry.getValue());

					map.put(id, val);
					continue;
				}
				
				if(entry.getValue() != null && valueClass.isInstance(entry.getValue())){
					T val = valueCaster.cast(valueClass, entry.getValue());
					map.put(id, val);
				} else {
					//对象类型有误
					logger.warn("memcached obj is not '" + valueClass.getName() + "' instance. cached object classname:" + entry.getValue().getClass().getName());
				}
			}
			return map;
		} catch (Exception e) {
			throw new RuntimeException("Error while operating on key-prefix: "
					+ keyPrefix, e);
		}
	}
	
	public List<T> getMultiAsList(Collection<String> idList) {
		Map<String, T> map = getMulti(idList);
		List<T> list = new ArrayList<T>(idList.size());
		for (String id : idList) {
			T value = map.get(id);
			if (value != null) {
				list.add(value);
			}
		}
		return list;
	}
	
	
	
	public boolean exists(String id) {
		return get(id) != null;
	}
	
	public boolean exists(long id) {
		return get(id) != null;
	}
	
	/**
	 * 不要使用
	 * @param id
	 * @param value
	 * @param expiry
	 * @return
	 */
	@Deprecated
	public boolean cas(long id, final T value, int expiry){
		return cas(String.valueOf(id), value, expiry);
	}
	
	/**
	 * 不要使用
	 * @param id
	 * @param value
	 * @param expiry
	 * @return
	 */
	@Deprecated
	public boolean cas(String id, final T value, int expiry) {
		boolean suc = false;
		try {
			ensureOpen();
			String key = generateKey(id);
			CASValue<Object> casValue = getClient().gets(key);
			//TODO 实现Cas功能
			CASResponse casResponse = getClient().cas(key, casValue.getCas(), value);
			//TODO 想想怎么实现？
			
			//GetsResponse<T> result = getClient().gets(key);
//			boolean flag = false;
//			for (int i = 0; i < CAS_MAX_TRIS; i++) {
//				if (result != null) {
//					long cas = result.getCas();
//					// 尝试将a的值更新为2
//					flag = getClient().cas(key, expiry, value, cas);
//				} else {
//					flag = getClient().set(key, expiry, value);
//				}
//				if(flag){
//					break;
//				}
//			}
		} catch (Exception e) {
			logger.error("memcached op error", e);
		}
		return suc;
	}

	/**
	 * 给据指定的id拼出一个用于memcached存储的key来
	 * 
	 * @param id
	 * @return
	 */
	private String generateKey(String id) {
		return keyPrefix + TI_SEPARATOR + id;
	}

	/**
	 * 从key中解析出id
	 * 
	 * @param key
	 * @return
	 */
	private String resolveIdFromKey(String key) {
		return key.substring(keyPrefix.length() + 1); // +1是因为还有个冒号
	}

	public boolean isCounter() {
		return this.isCounter;
	}
	
	public static void main(String[] args) {
//		MCache<String> c = MCache.getCache(POOL_SITE, "test", String.class,true);
//		c.set("test", "test");
//		
//		System.out.println(c.get("test"));
//		System.exit(0);
	}
}
