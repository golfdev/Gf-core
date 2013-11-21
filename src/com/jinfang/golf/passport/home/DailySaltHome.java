package com.jinfang.golf.passport.home;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jinfang.golf.passport.dao.DailySaltDAO;
import com.jinfang.golf.passport.model.DailySalt;
import com.jinfang.golf.utils.DateUtil;

@Component
public class DailySaltHome {

	@Autowired
	private DailySaltDAO dailySaltDAO;
	
	private Map<Long, String> dailySaltMap = new ConcurrentHashMap<Long, String>();

	public String getOrCreateSalt(long time) {
		Long createTime = DateUtil.timeIgnoreHMS(time);
		
		String salt = dailySaltMap.get(createTime);
		if (salt != null) {
			return salt;
		}

		DailySalt newDailySalt = new DailySalt(createTime, genSalt());
		synchronized (DailySaltHome.class) {
			if (dailySaltMap.get(createTime) == null) {
				dailySaltDAO.save(newDailySalt);
			}
		}
		return getSalt(time);
	}

	public String getSalt(long time) {
		Long createTime = DateUtil.timeIgnoreHMS(time);

		String salt = dailySaltMap.get(createTime);
		if (salt != null) {
			return salt;
		}
//		return "d7ffe531a2e14f74a887bf7160efa337";
		DailySalt dailySalt = dailySaltDAO.get(createTime);
		if (dailySalt != null) {
			dailySaltMap.put(createTime, dailySalt.getSalt());
			return dailySalt.getSalt();
		}else{
			return null;
		}
	}
	
    private static String genSalt(){
    	return UUID.randomUUID().toString().replaceAll("-", "");
    }
    
}
