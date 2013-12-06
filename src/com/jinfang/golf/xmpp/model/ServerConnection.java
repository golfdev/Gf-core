package com.jinfang.golf.xmpp.model;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.jinfang.golf.constants.GolfConstant;
import com.jinfang.golf.xmpp.LogUtils;

public class ServerConnection {
	private static Charset UTF8 = Charset.forName("UTF-8");
	private static byte[] INIT_BYTE = ("<stream:stream version=\"1.0\" to=\"" + 
			GolfConstant.ROOT_DOMAIN + "\" xmlns=\"jabber:server\" " +
			"xmlns:stream=\"http://etherx.jabber.org/streams\">").getBytes(UTF8);
	private static int connId = 1;
	private String host;
	private int port;
	private Socket socket;
	private int maxRetries = 2;
	private int readTimeOut = 3000; // 3秒
	private int connectionId;
	private ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
	public ServerConnection(String serverIp, int serverPort) {
		this.host = serverIp;
		this.port = serverPort;
		this.connectionId = connId++;
		LogUtils.stdLogger.info(String.format("create new ServerConnection %s %s %s", host, port, connectionId));
		service.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				try {
					write("<k/>");
					LogUtils.stdLogger.info(String.format("Write keep alive.<k/> host: %s, port: %s, connectionId: %s", host, port, connectionId));
					// TODO 每次读一下，读出来的数据忽略吧，为的就是不让把buffer写满
					InputStream is = socket.getInputStream();
					byte[] bytes = new byte[1024];
					is.read(bytes);
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}, 1, 2, TimeUnit.MINUTES); // 两分钟发个keep alive
	}
	public boolean createNewSocket() {
		try {
			socket = new Socket(host, port);
			socket.setSoTimeout(readTimeOut);
			socket.setTcpNoDelay(true);
			OutputStream os = socket.getOutputStream();
			os.write(INIT_BYTE);
			os.flush();
			InputStream is = socket.getInputStream();
			byte[] inByte = new byte[256];
			int size = is.read(inByte);
			LogUtils.stdLogger.info(String.format("connect to %s:%s, init read size: %s", host, port, size));
			if (size > 0) {
				String str = new String(inByte);
				if (str.contains("stream:stream")) {
					// 说明验证通过
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		close();
		return false;
	}
	public void close() {
		try {
			socket.close();
			LogUtils.stdLogger.info("close this socket. connId: " + connectionId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		socket = null;
	}
	public void shutdown() {
		try {
			service.shutdown();
			service = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		close();
	}
	public synchronized boolean write(String xml) {
		boolean isSuccessful = false;
		byte[] bytes = xml.getBytes(UTF8);
		for (int i = 1; i <= maxRetries; i++) {
			try {
				if (socket == null || socket.isClosed()) {
					createNewSocket();
				}
				OutputStream os = socket.getOutputStream();
				os.write(bytes);
				os.flush();
				isSuccessful = true;
				break;
			} catch (Exception e) {
				e.printStackTrace();
				LogUtils.stdLogger.error(String.format("Send xml failed. The %s retry. %s", i, xml));
				close();
			}
		}
		return isSuccessful;
	}
}
