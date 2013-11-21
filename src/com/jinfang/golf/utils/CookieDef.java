package com.jinfang.golf.utils;

public interface CookieDef {

	String domain();
	String path();
	String name();
	
	int maxAge();
	boolean isSecure();
	
}
