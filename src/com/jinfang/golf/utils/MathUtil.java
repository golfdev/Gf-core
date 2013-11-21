package com.jinfang.golf.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 算法工具类
 * 
 * @author ly.jiao
 * 
 */
public class MathUtil {
	/**
	 * 生成图片文件名(20位)
	 * 
	 * @return
	 */
	public static synchronized String getImgFileName() {
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmsss");
		return format.format(new Date()) + getCharAndNumr(5);
	}
	
	public static String getImgFileDirectory(){
		DateFormat format = new SimpleDateFormat("yyyyMMdd");

	    return format.format(new Date());
	}

	public static String getImgFileDirectoryWithHour(){
		DateFormat format = new SimpleDateFormat("yyyyMMdd/HH");

	    return format.format(new Date());
	}
	
	/**
	 * 取得随机字母和数字组合
	 * 
	 * @param length
	 * @return
	 */
	public static String getCharAndNumr(int length) {
		String val = "";

		Random random = new Random();
		for (int i = 0; i < length; i++) {
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字

			if ("char".equalsIgnoreCase(charOrNum)) // 字符串
			{
				int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; // 取得大写字母还是小写字母
				val += (char) (choice + random.nextInt(26));
			} else if ("num".equalsIgnoreCase(charOrNum)) // 数字
			{
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val;
	}

	/**
	 * 得到n天前的日期
	 * 
	 * @param n
	 * @return
	 */
	public static Date getDate(int n) {
		return new Date(System.currentTimeMillis() - 1000L * 60L * 60L * 24L * n);
	}
}
