package com.jinfang.golf.utils;

import org.springframework.stereotype.Component;

import com.jinfang.golf.passport.model.PassportTicket;
import com.jinfang.golf.user.model.User;


@Component
public class UserHolder {
	
	/**
	 * 用户在系统的登录状态
	 */
	private ThreadLocal<PassportTicket> passportTicket = new ThreadLocal<PassportTicket>();
	
	private ThreadLocal<User> userInfo = new ThreadLocal<User>();
	

	public PassportTicket getPassportTicket() {
		return passportTicket.get();
	}


	public void setPassportTicket(PassportTicket passportTicket) {
		this.passportTicket.set(passportTicket);
	}

	
	public User getUserInfo(){
		return this.userInfo.get();
	}
	public void setUserInfo(User user){
		this.userInfo.set(user);
	}

	public void clean() {
		userInfo.remove();
		passportTicket.remove();
	}


}
