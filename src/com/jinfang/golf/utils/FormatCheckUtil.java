package com.jinfang.golf.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatCheckUtil {

    /**
     * 验证输入的邮箱格式是否符合
     * @param email
     * @return 是否合法
     */
	public static boolean isEmail(String email){
        //final String pattern1 = "^([a-z0-9A-Z]+[-._]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	    final String pattern1 = "^[A-Za-z0-9](([_.-]?[a-zA-Z0-9]+)*)@([A-Za-z0-9]+)(([.-]?[a-zA-Z0-9]+)*)\\.([A-Za-z]{2,})$";
        final Pattern pattern = Pattern.compile(pattern1);
        final Matcher mat = pattern.matcher(email);
        if (mat.find()) {
            return true;
        }
        return false;
    }
	
    public static boolean isMobileNO(String mobiles){     
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");     
        Matcher m = p.matcher(mobiles);     
        return m.matches();     
    } 
}