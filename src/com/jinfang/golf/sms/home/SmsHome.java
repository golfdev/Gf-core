package com.jinfang.golf.sms.home;

import org.springframework.stereotype.Component;

@Component
public class SmsHome {
	private static final String SMS_KEY = "6d0cd5b2d3b27c5371be658df25ca41b";
	private static final String SMS_URL = "http://www.tui3.com/api/send";
	private static final String SMS_LAYOUT = "[hunbola提醒]:";
	
	
	/**
	 * 发送短信
	 * @param mobile
	 * @param content
	 * @return
	 */
	public void sendVerifyCodeSms(String mobile, String code){
		
	}
	
	
}
