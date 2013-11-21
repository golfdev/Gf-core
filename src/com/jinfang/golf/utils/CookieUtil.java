package com.jinfang.golf.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
	
	public static void saveCookie(HttpServletResponse resp, CookieDef cookieDef, String value) {
		saveCookie(resp, cookieDef.name(), value, cookieDef.domain(), cookieDef.path(), cookieDef.maxAge(), cookieDef.isSecure());
	}

	public static String getCookieValue(HttpServletRequest req, CookieDef cookieDef) {
		return getCookieValue(req, cookieDef.name());
	}

	public static void deleteCookie(HttpServletResponse resp, CookieDef cookieDef) {
		deleteCookie(resp, cookieDef.name(), cookieDef.domain(), cookieDef.path());
	}
	
	public static void saveCookie(HttpServletResponse resp, String name,
			String value, String domain, String path, int maxAge, boolean secure) {
		Cookie cookie = new Cookie(name, value);
		cookie.setDomain(domain);
		cookie.setPath(path);
		cookie.setMaxAge(maxAge);
		cookie.setSecure(secure);
		resp.addCookie(cookie);
	}

	public static String getCookieValue(HttpServletRequest req, String name) {
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	public static void deleteCookie(HttpServletResponse resp, String name,
			String domain, String path) {
		Cookie cookie = new Cookie(name, null);
		cookie.setDomain(domain);
		cookie.setPath(path);
		cookie.setMaxAge(0);

		resp.addCookie(cookie);
	}
}
