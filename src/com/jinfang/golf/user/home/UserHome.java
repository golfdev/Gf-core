package com.jinfang.golf.user.home;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.jinfang.golf.user.dao.UserCentifyDAO;
import com.jinfang.golf.user.dao.UserDAO;
import com.jinfang.golf.user.dao.UserDeviceDAO;
import com.jinfang.golf.user.dao.UserTokenDAO;
import com.jinfang.golf.user.model.User;
import com.jinfang.golf.user.model.UserCentify;
import com.jinfang.golf.user.model.UserToken;

@Component
public class UserHome {
    

//    private MCache<User> userCache = MCache.getCache(MCache.POOL_CORE, "user", User.class);

	@Autowired
	private UserDAO dao;
	
	@Autowired
	private UserDeviceDAO userDeviceDao;
	
	@Autowired
	private UserTokenDAO userTokenDao;
	
	@Autowired
	private UserCentifyDAO userCentifyDAO;
	
	
	
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
	
	public void uploadDeviceToken(Integer userId,String deviceToken){
		userTokenDao.updateDeviceToken(userId, deviceToken);
    }
	
	public void updateTokenAndSource(Integer userId,String token,String source){
		userTokenDao.save(userId,token,source);
//		userCache.delete(userId);

	}
	
	
	public String getDeviceToken(Integer userId){
		return userTokenDao.getDeviceToken(userId);
	}
	
	
	public User getByPhone(String phone){
		return dao.getByPhone(phone);
	}
	
	
	
	public User getById(int id){
//		User user = userCache.get(id);
//		if(user==null){
	        User user =  dao.getById(id); 
			UserToken temp = userTokenDao.getTokenAndSourceByUserId(id);
			if(temp!=null){
			user.setToken(temp.getToken());
			user.setSource(temp.getSource());
			}
//			userCache.set(id, user);
//		}else{
//			return user;
//		}
		return user; 
	}
	
	public Integer getTotalUserCount(){
		return dao.getTotalUserCount();
	}
	
	public void updateUser(User user){
		 dao.updateUser(user);
//		 userCache.delete(user.getId());
	}
	
	public void saveCentifyInfo(UserCentify centify){
		userCentifyDAO.save(centify);
		dao.updateStatus(centify.getUserId(), 1);
//		userCache.delete(centify.getUserId());

	}
	
	public UserCentify getUserCentify(Integer userId){
		return userCentifyDAO.getUserCentify(userId);
	}
	
	public void updateHeadUrl(User user){
		dao.updateHeadUrl(user);
//		userCache.delete(user.getId());
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
	
	public List<User> getAllUserListByCityAndStatus(int offset,int limit,String city,int status){
		return dao.getAllUserListByCity(offset, limit,city);
	}
	
	public List<User> getUserListByIds(List<Integer> userIds){
		if(CollectionUtils.isEmpty(userIds)){
			return null;
		}
		Map<Integer,User> userMap = getUserMapByIds(userIds);
	    List<User> userList = new ArrayList<User>();
	    for(Integer userId:userIds){
	    	if(userMap.containsKey(userId)){
	    		userList.add(userMap.get(userId));
	    	}
	    }
	    return userList;
	}
	public Map<Integer,User> getUserMapByIds(List<Integer> userIds) {
		return dao.getUserMapByIds(userIds);
	}

}
