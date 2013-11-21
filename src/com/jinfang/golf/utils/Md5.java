package com.jinfang.golf.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Md5 {

	public static String md5s(String plainText) {
		String str = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			str = buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();

		}
		return str;
	}

	public static void main(String[] arg) {
		Set<String> set = new HashSet<String>();
		for(int i=0;i<10000;i++){
		String ran = UUID.randomUUID().toString().substring(0,5);
		if(set.contains(ran)){
		System.out.println(ran);
		}
		}
	}
}
