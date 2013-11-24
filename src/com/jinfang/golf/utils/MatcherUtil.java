package com.jinfang.golf.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherUtil {
    /**
     * 正则匹配字符串
     * 
     * @param regex
     * @param str
     * @return
     */
    public static String matcher(String regex, String str) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        if (m.find()) {
            return m.group();
        }
        return "";
    }
}
