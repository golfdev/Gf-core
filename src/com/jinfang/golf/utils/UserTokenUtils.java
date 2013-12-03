package com.jinfang.golf.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jinfang.golf.passport.model.Passport;
import com.jinfang.golf.passport.model.PassportTicket;
import com.jinfang.golf.user.home.UserHome;
import com.jinfang.golf.user.model.User;

@Component
public class UserTokenUtils {

	@Autowired
	private Passport passport;

	@Autowired
	private UserHome userHome;

	/**
	 * 校验用户token的合法性
	 * @param token
	 * @return
	 */
	public boolean checkToken(String token) {

		PassportTicket passportTicket = passport.readInToken(token);

		if (passportTicket != null) {
			User user = userHome.getById(passportTicket.getUserId());
			if (token.equals(user.getToken())) {
				return true;
			}
		} else {
			return false;
		}

		return false;

	}

	/**
	 * 根据token返回user对象，token非法返回为空
	 * @param token
	 * @return
	 */
	public User getUserByToken(String token) {
		PassportTicket passportTicket = passport.readInToken(token);

		if (passportTicket != null) {
			User user = userHome.getById(passportTicket.getUserId());
			if (token.equals(user.getToken())) {
				return user;
			}
		}
		return null;
	}

}
