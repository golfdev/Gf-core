package com.jinfang.golf.cache;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import net.spy.memcached.BinaryConnectionFactory;
import net.spy.memcached.MemcachedClient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class MemcachedClientFactory {
	
	private static final Log logger = LogFactory.getLog(MemcachedClientFactory.class);
	
	private static final int POOL_SIZE = 5;
	
	/**
     * 存储{@link MemcachedClient}实例，key为{@link CachePool}的name
     */
    private Map<String, MemcachedClientWrapper> clients = new ConcurrentHashMap<String, MemcachedClientWrapper>();
    
    
    /**
     * 存储{@link MemcachedClient}实例的版本号，每当poolname的配置变更，这个版本号都要自增1。
     * 版本号的价值在于，当{@link CachePool}的配置变更时，需要重新构造一个{@link SockIOPool}，需要 在
     * {@link SockIOPool}的name中加入版本号以区分，同时通过旧的版本号获取老的{@link SockIOPool}实例，
     * 并销毁之。
     */
    private Map<String, AtomicInteger> clientVersions = new ConcurrentHashMap<String, AtomicInteger>();
    
    private static MemcachedClientFactory instance = new MemcachedClientFactory();
    
    public static MemcachedClientFactory getInstance(){
    	return instance;
    }

    private MemcachedClientFactory() {
    }

    /**
     * 按照{@link Poolname}的名字来查找与之对应的{@link MemCachedClient}。
     * 
     * @param poolname
     * @return
     */
    public MemcachedClient getClientByPoolname(String poolname) {
    	//考虑重连
    	MemcachedClientWrapper clientWrapper = clients.get(poolname);
        if (clientWrapper == null) {
            synchronized (this) {
            	clientWrapper = clients.get(poolname);
                if (clientWrapper == null) { //init a client
                	logger.info("getMemCacheConfig : " + poolname);
					Properties conf = new Properties();

					try {
						conf.load(MemcachedClientFactory.class.getResourceAsStream("/server.properties"));
					} catch (IOException e) {
						logger.error("config error : " + poolname);
						e.printStackTrace();
					}
					
    				MemcachedClient client = createMemcachedClient(poolname, conf, POOL_SIZE);
    				
    				clientWrapper = new MemcachedClientWrapper(poolname, client, conf); 
    				clients.put(poolname, clientWrapper);
					AtomicInteger version = clientVersions.get(poolname);
			        if (version == null) {
			            version = new AtomicInteger(-1);
			            clientVersions.put(poolname, version);
			        }
			       
                }
            }
        }
        return clientWrapper != null ? clientWrapper.client : null;
    }
    
    private MemcachedClient createMemcachedClient(String poolname, Properties configuration, int connectionPoolSize){
    	MemcachedClient client = null;
    	List<InetSocketAddress> addressList = new ArrayList<InetSocketAddress>();
		int nb = 1;
		while (configuration.containsKey("host." + nb)) {
			String hostandport = configuration.getProperty("host." + nb);
			String host = StringUtils.substringBefore(hostandport, ":");
			String portStr = StringUtils.substringAfter(hostandport, ":");
			int port = NumberUtils.toInt(portStr, 11211);
			logger.info("memcached init [host:" + host + ",port:" + port + ",portStr:" + portStr + "]");
			addressList.add(new InetSocketAddress(host,port));
			nb++;
		}
		String cacheEnable = configuration.getProperty("cacheEnable");
		

		try {
			if("true".equals(cacheEnable)){
				if(!"counter".equals(poolname)){
					// 支持二进制协议
					client = new MemcachedClient(new BinaryConnectionFactory(),addressList);
				}else{
					client = new MemcachedClient(addressList);
				}
			}
			
		
			return client;
		} catch (IOException e) {
			logger.error("memcached init error",e);
			return null;
		}
    }
    
    
    public void destroyMemcachedClient(String poolname){
    	MemcachedClientWrapper clientWrapper = clients.get(poolname);
    	if(clientWrapper != null){
    		clients.remove(poolname);
    		try {
				clientWrapper.client.shutdown();
			} catch (Exception e) {
				logger.error("MemcachedClient shutdown error",e);
			}
    	}
    }
    
    public void destroyAll(){
    	Collection<MemcachedClientWrapper> list = clients.values();
    	for(MemcachedClientWrapper w : list){
    		destroyMemcachedClient(w.poolname);
    	}
    }
    
    class MemcachedClientWrapper {
    	
    	protected MemcachedClientWrapper(String poolname, MemcachedClient client, Properties conf){
    		this.poolname = poolname;
    		this.client = client;
    		this.conf = conf;
    	}

    	protected String poolname;
    	protected MemcachedClient client;
    	protected Properties conf;
    	
    }
}
