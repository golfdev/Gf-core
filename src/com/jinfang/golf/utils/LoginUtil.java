package com.jinfang.golf.utils;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取用户真实的ip和refer
 * @author lvlin.zeng
 *
 */
public class LoginUtil {
	
	private static final Pattern ipPattern = Pattern.compile("([0-9]{1,3}\\.){3}[0-9]{1,3}");


	/**
	 * 
	 * 取客户端的真实IP，考虑了反向代理等因素的干扰
	 * +使用X-Real-IP，处理ngix的干扰
	 * @param request
	 * @return
	 */
	public static String getFromIp(HttpServletRequest request){
		String ip = request.getHeader("X-Real-IP");
		if (isValidIP(ip)) {
			return ip;
		}
		return request.getRemoteAddr();
	}
	
	/**
	 * 获取http的refer
	 * @param request
	 * @return
	 */
	public static String getHttpRefer(HttpServletRequest request){
		return request.getHeader("referer");
	}
	
	private static boolean isValidIP(String ip) {
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			return false;
		}
		return ipPattern.matcher(ip).matches();
	}
	
	public static void main(String[] args) {
       
	}

}
