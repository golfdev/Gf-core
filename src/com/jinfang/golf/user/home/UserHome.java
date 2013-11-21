package com.jinfang.golf.user.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jinfang.golf.user.dao.UserDAO;
import com.jinfang.golf.user.model.User;

@Component
public class UserHome {

	@Autowired
	private UserDAO dao;
	
	/**
	 * 保存用户信息
	 * @param info
	 * @return
	 */
	public int save(User user){
		return dao.save(user).intValue();
	}
	
	public User getByEmail(String email){
        return dao.getByEmail(email); 
    }
	
	
	
	public User getByPhone(String phone){
		return dao.getByPhone(phone); 
	}
	
	
	public User getById(int id){
		return dao.getById(id);
	}
	
	public User updateInfo(User user){
		return dao.updateInfo(user);
	}

}
