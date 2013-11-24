package com.jinfang.golf.user.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jinfang.golf.cache.MCache;
import com.jinfang.golf.user.dao.UserDAO;
import com.jinfang.golf.user.model.User;

@Component
public class UserHome {
    
    private MCache<User> phoneCache = MCache.getCache(MCache.POOL_CORE, "user_phone", User.class);

    private MCache<User> userCache = MCache.getCache(MCache.POOL_CORE, "user", User.class);

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
		
		User user = phoneCache.get(phone);
		if(user==null){
			user =  dao.getByPhone(phone); 
			phoneCache.set(phone, user);
		}else{
			return user;
		}
		return user; 
	}
	
	
	public User getById(int id){
		User user = userCache.get(id);
		if(user==null){
			user =  dao.getById(id); 
			userCache.set(id, user);
		}else{
			return user;
		}
		return user; 
	}
	
	public User updateInfo(User user){
		return dao.updateInfo(user);
	}

}
