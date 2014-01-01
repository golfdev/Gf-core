package com.jinfang.golf.club.home;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jinfang.golf.cache.redis.RedisCacheManager;
import com.jinfang.golf.cache.redis.RedisCachePool;
import com.jinfang.golf.cache.redis.RedisConstants;
import com.jinfang.golf.club.dao.GolfClubDAO;
import com.jinfang.golf.club.dao.GolfClubOrderDAO;
import com.jinfang.golf.club.dao.GolfClubOrderItemDAO;
import com.jinfang.golf.club.dao.GolfClubWayItemDAO;
import com.jinfang.golf.club.dao.GolfClubYardDAO;
import com.jinfang.golf.club.model.GolfClub;
import com.jinfang.golf.club.model.GolfClubOrder;
import com.jinfang.golf.club.model.GolfClubOrderWay;
import com.jinfang.golf.club.model.GolfClubWayItem;
import com.jinfang.golf.club.model.GolfClubYard;

@Component
public class GolfClubHome {
    
	@Autowired
	private GolfClubDAO golfClubDAO;
	
	@Autowired
	private GolfClubWayItemDAO golfClubItemDAO;
	
	@Autowired
	private GolfClubOrderDAO golfClubOrderDAO;
	
	@Autowired
	private GolfClubOrderItemDAO golfClubOrderItemDAO;
	
	@Autowired
	private GolfClubYardDAO golfClubYardDAO;
	
	private RedisCachePool redisPool = RedisCacheManager.getInstance().getRedisPool(RedisConstants.POOL_LOCK);
	
	
	public GolfClub getGolfClubById(Integer id){
		GolfClub club =  golfClubDAO.getGolfClub(id);
		return club;	
    }
	
	public Map<Integer,GolfClub> getGolfClubMap(List<Integer> clubIds){
		return golfClubDAO.getGolfClubMap(clubIds);
	}
	

	public List<GolfClub> getGolfClubList(String city,Integer offset,Integer limit){
		List<GolfClub> clubList = golfClubDAO.getGolfClubList(city, offset, limit);
		return clubList;
	}
	
	
	public List<String> getAvailableDateList(Integer clubId){
		return golfClubItemDAO.getClubAvailableDateList(clubId);
	}
	
	public List<GolfClubWayItem> getAvailableItemList(Integer clubId,String date){
		return golfClubItemDAO.getClubAvailableItemList(clubId, date);
	}
	
	
	public List<Date> getAvailableTimeList(Integer clubId,String date){
		return golfClubItemDAO.getClubAvailableTimeList(clubId, date);
	}
	
	public boolean saveOrder(GolfClubOrder order){
		
		List<Integer> wayIdList = order.getWayIdList();

//		for(Integer wayItemId:wayIdList){
//			try {
//				long lock = redisPool.setnx(GolfConstant.ORDER_WAY_LOCK+wayItemId,String.valueOf(order.getUserId()));
//				if(lock!=1){
//					return false;
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
		
		Integer orderId = golfClubOrderDAO.save(order).intValue();
		
		for(Integer wayItemId:wayIdList){
			GolfClubOrderWay orderWay = new GolfClubOrderWay();
			orderWay.setOrderId(orderId);
			orderWay.setItemId(wayItemId);
			golfClubItemDAO.updateClubItemStatus(wayItemId,1);
			golfClubOrderItemDAO.save(orderWay);
		}
		
		return true;
	}
	
	public void batchSaveOrderItem(List<GolfClubOrderWay> itemList){
		if(CollectionUtils.isNotEmpty(itemList)){
			for(GolfClubOrderWay item:itemList){
				golfClubOrderItemDAO.save(item);
			}
		}
	}
	
	public List<GolfClubYard> getGolfClubYardList(Integer clubId){
		return golfClubYardDAO.getGolfClubYardList(clubId);
	}
	
	
	
}
