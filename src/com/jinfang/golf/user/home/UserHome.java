package com.jinfang.golf.user.home;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jinfang.golf.cache.MCache;
import com.jinfang.golf.user.dao.UserDAO;
import com.jinfang.golf.user.dao.UserDeviceDAO;
import com.jinfang.golf.user.model.User;

@Component
public class UserHome {
    
    private MCache<User> phoneCache = MCache.getCache(MCache.POOL_CORE, "user_phone", User.class);

    private MCache<User> userCache = MCache.getCache(MCache.POOL_CORE, "user", User.class);

	@Autowired
	private UserDAO dao;
	
	@Autowired
	private UserDeviceDAO userDeviceDao;
	
	/**
	 * 保存用户信息
	 * @param info
	 * @return
	 */
	public int save(User user){
		return dao.save(user).intValue();
	}
	
	/**
     * 保存用户设备信息
     * @param info
     * @return
     */
    public void saveUserDevice(Integer userId,String device){
         userDeviceDao.save(userId,device);
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
	
	
	public User getByDevice(String device){
	    
	    Integer userId = userDeviceDao.getByDevice(device);
	    
	    if(userId==null||userId==0){
	        return null;
	    }else{
	        return getById(userId);
	    }
        
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
	
	public void updateUser(User user){
		 dao.updateUser(user);
	}
	
	public void updateHeadUrl(User user){
		dao.updateHeadUrl(user);
		userCache.delete(user.getId());
		phoneCache.delete(user.getId());
	}
	
	public List<User> getAllUserList(int offset,int limit){
		return dao.getAllUserList(offset, limit);
	}
	
	public List<User> getAllUserListByStatus(int offset,int limit,int status){
		return dao.getAllUserListByStatus(offset, limit,status);
	}
	
	public List<User> getAllUserListByCity(int offset,int limit,String city){
		return dao.getAllUserListByCity(offset, limit,city);
	}

}
