package com.jinfang.golf.cache.redis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.thrift.TBase;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TIOStreamTransport;

/**
 * @author RamosLi
 * 对象和byte数组之间序列化和反序列化的工具
 */
public class Serializer {
	/**
	 * 序列化
	 * @param tBase
	 * @return
	 */
	public static byte[] serialize(TBase tBase) {
		if (tBase == null) {
			return null;
		}
		OutputStream os = null;
		try {
			os = new ByteArrayOutputStream();
	        TProtocol protocol = new TCompactProtocol(new TIOStreamTransport(os));
	        tBase.write(protocol);
	        ByteArrayOutputStream bos = (ByteArrayOutputStream)os;
	        return bos.toByteArray();
		} catch (TException e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null) {
					os.close();
				}
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * 反序列化
	 * @param bytes
	 * @param tBase  返回值的类型
	 * @return
	 */
	public static TBase deserialze(byte[] bytes, TBase tBase) {
		if (bytes == null) {
			return null;
		}
		InputStream is = null;
		try {
			is = new ByteArrayInputStream(bytes);			
	        TProtocol protocol = new TCompactProtocol(new TIOStreamTransport(is));
	        tBase.read(protocol);
	        return tBase;
		} catch (TException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}
		return null;
	}
}
