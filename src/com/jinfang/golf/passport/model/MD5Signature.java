package com.jinfang.golf.passport.model;

import org.springframework.stereotype.Component;

import com.jinfang.golf.utils.Md5;

@Component("md5Signature")
public class MD5Signature extends Signature {

    /**
     * md5签名算法。
     *
     * @param text 需要md5字符串。
     * @return 字段传内容的md5值。
     */
	@Override
	String hash(String text) {
		return Md5.md5s(text);
	}

}
