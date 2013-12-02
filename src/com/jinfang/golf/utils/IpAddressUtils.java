package com.jinfang.golf.utils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

/**
 * IP地址工具
 * @author RamosLi
 *
 */
public class IpAddressUtils {
	private static final Pattern ipPattern = Pattern.compile("([0-9]{1,3}\\.){3}[0-9]{1,3}");
	// 本机IP
	private static String LOCALE_IP = null;
	/**
	 * 获取客户端的真实IP，考虑到反响代理等因素
	 * @param request
	 * @return
	 */
	public static String getClientIp(HttpServletRequest request) {
		String ip;
        @SuppressWarnings("unchecked")
        Enumeration<String> xffs = request.getHeaders("X-Forwarded-For");
        if (xffs.hasMoreElements()) {
            String xff = xffs.nextElement();
            ip = resolveClientIPFromXFF(xff);
            if (isValidIP(ip)) {
                return ip;
            }
        }
        ip = request.getHeader("Proxy-Client-IP");
        if (isValidIP(ip)) {
            return ip;
        }
        ip = request.getHeader("WL-Proxy-Client-IP");
        if (isValidIP(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
	}
	/**
     * 从X-Forwarded-For头部中获取客户端的真实IP。
     * X-Forwarded-For并不是RFC定义的标准HTTP请求Header
     * ，可以参考http://en.wikipedia.org/wiki/X-Forwarded-For
     * 
     * @param xff X-Forwarded-For头部的值
     * @return 如果能够解析到client IP，则返回表示该IP的字符串，否则返回null
     */
    private static String resolveClientIPFromXFF(String xff) {
        if (xff == null || xff.length() == 0) {
            return null;
        }
        String[] ss = xff.split(",");
//        for (int i = ss.length - 1; i >= 0; i--) {//x-forward-for链反向遍历
        String trueIp = null;
		for (int i = 0; i < ss.length; i++) { //x-forward-for链反向遍历是不对的，其实我们想取真实client的地址，所以得正向遍历
            String ip = ss[i].trim();
        	if (isValidIP(ip)) { //判断ip是否合法
        		trueIp = ip;
        		if (!isLanIp(ip)) {
        			// 如果不是局域网IP，就说明找到了，停止遍历
        			break;
        		}
            }
        }
        return trueIp;
    }
    private static boolean isValidIP(String ip) {
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            return false;
        }
        return ipPattern.matcher(ip).matches();
    }
    // 是否是局域网IP
    public static boolean isLanIp(String ip) {
		if (ip.startsWith("10.") || ip.startsWith("192.")) {
			return true;
		}
		return false;
	}
    public static final String getLocalAddress() {
    	if (LOCALE_IP != null) {
    		return LOCALE_IP;
    	}
		try {
			for (Enumeration<NetworkInterface> ni = NetworkInterface.getNetworkInterfaces(); ni.hasMoreElements();) {
				NetworkInterface eth = ni.nextElement();
				for (Enumeration<InetAddress> add = eth.getInetAddresses(); add.hasMoreElements();) {
					InetAddress i = add.nextElement();
					if (i instanceof Inet4Address) {
						if (i.isSiteLocalAddress()) {
							LOCALE_IP = i.getHostAddress();
							return LOCALE_IP;
						}
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return "";
	}
    /**
     * 将ip地址转换成long型
     * 此接口故意不做ip地址合法校验。如果传入的ip地址为非法，则直接抛异常，谁传谁活该！
     * @param ip
     * @return
     */
    public static long convert2Long(String ip) {
    	String[] array = ip.split("\\.");
    	return (Long.parseLong(array[0]) << 24) + (Long.parseLong(array[1]) << 16) + (Long.parseLong(array[2]) << 8) + Long.parseLong(array[3]);
    }
    public static void main(String[] args) {
		System.out.println(resolveClientIPFromXFF("10.167.117.219, 211.140.18.99"));
		System.out.println(convert2Long("117.79.229.18"));
	}
}
