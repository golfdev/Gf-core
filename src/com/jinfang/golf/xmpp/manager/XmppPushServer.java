package com.jinfang.golf.xmpp.manager;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.pool.impl.GenericObjectPool;

import com.jinfang.golf.xmpp.LogUtils;
import com.jinfang.golf.xmpp.model.ServerConnection;
import com.jinfang.golf.xmpp.pool.ServerConnectionPoolFactory;

/**
 * 操作xmpp push server的类
 */
public class XmppPushServer {
	public static final int RET_SUCCESS = 0;
	public static final int RET_NOT_ONLINE = 1;
	public static final int RET_FAIL = 2;
	private static XmppPushServer instance = new XmppPushServer();
	public static XmppPushServer getInstance() {
		return instance;
	}
	
	private Map<String, GenericObjectPool> poolMap = new HashMap<String, GenericObjectPool>();
	/**
	 * 把一个数据包发到指定服务器
	 * @param serverIp
	 * @param port
	 * @param packet
	 */
	public int pushDataToServer(String serverIp, int port, String xml) {
		String key = serverIp + "_" + port;
		GenericObjectPool pool = poolMap.get(key);
		if (pool == null) {
			synchronized (key.intern()) {
				pool = poolMap.get(key);
				if (pool == null) {
					pool = new GenericObjectPool(new ServerConnectionPoolFactory(serverIp, port));
					poolMap.put(key, pool);
				}
			}
		}
		
		boolean isSuccess = false;
		ServerConnection conn = null;
		try {
			conn = (ServerConnection)pool.borrowObject();
			isSuccess = conn.write(xml);
			LogUtils.stdLogger.info(String.format("XmppPush. xmppServer: %s, port: %s, message: %s, isSuccess: %s", serverIp, port, xml, isSuccess));
			if (isSuccess) {
				return RET_SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					if (isSuccess) {
						pool.returnObject(conn);
					} else {
						pool.invalidateObject(conn);
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		LogUtils.stdLogger.info(String.format("Warning! XmppPush. send message fail. xmppServer: %s, port: %s, message: %s", serverIp, port, xml));

		return RET_FAIL;
	}
}
