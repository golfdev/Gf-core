package com.jinfang.golf.xmpp;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LogUtils {
	public static final Log logger = LogFactory.getLog(LogUtils.class);
	
	// 标准输出
	public static final Log stdLogger = LogFactory.getLog("stdout_logger");
	private static String[] searchArray = {" ", "\r", "\n"};
	private static String[] replaceArray = {"&nbsp;", "\\r", "\\n"};
	public static String format(Object obj) {
		if (obj == null) {
			return "-";
		} else {
			if (obj instanceof String) {
				String str = (String)obj;
				if (StringUtils.isNotBlank(str)) {
					str = StringUtils.replaceEach(str, searchArray, replaceArray);
					return str;
				} else {
					return "-";
				}
			} else {
				return obj.toString();
			}
		}
	}
	// 给我转回去
	public static String unformat(String content) {
		return StringUtils.replaceEach(content, replaceArray, searchArray);
	}
	public static void print(String log) {
		stdLogger.info(log);
	}
}
