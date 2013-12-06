package com.jinfang.golf.xmpp.pool;

import org.apache.commons.pool.BasePoolableObjectFactory;

import com.jinfang.golf.xmpp.model.ServerConnection;

public class ServerConnectionPoolFactory extends BasePoolableObjectFactory {
	private String host;
	private int port;
	
	public ServerConnectionPoolFactory(String host, int port) {
		this.host = host;
		this.port = port;
	}
	@Override
	public Object makeObject() throws Exception {
		return new ServerConnection(host, port);
	}
	
	@Override
	public void destroyObject(Object obj) throws Exception {
		ServerConnection conn = (ServerConnection)obj;
		conn.shutdown();
	}
}
