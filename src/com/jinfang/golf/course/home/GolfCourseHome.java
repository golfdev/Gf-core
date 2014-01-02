package com.jinfang.golf.course.home;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jinfang.golf.cache.redis.RedisCacheManager;
import com.jinfang.golf.cache.redis.RedisCachePool;
import com.jinfang.golf.cache.redis.RedisConstants;
import com.jinfang.golf.club.home.GolfClubHome;
import com.jinfang.golf.club.model.GolfClub;
import com.jinfang.golf.constants.GolfConstant;
import com.jinfang.golf.course.dao.GolfCourseDAO;
import com.jinfang.golf.course.dao.GolfCourseHoleScoreDAO;
import com.jinfang.golf.course.dao.GolfCoursePlayerDAO;
import com.jinfang.golf.course.model.GolfCourse;
import com.jinfang.golf.course.model.GolfCourseHoleScore;
import com.jinfang.golf.course.model.GolfCoursePlayer;
import com.jinfang.golf.user.home.UserHome;
import com.jinfang.golf.user.model.User;

@Component
public class GolfCourseHome {

	@Autowired
	private GolfCourseDAO golfCourseDAO;

	@Autowired
	private GolfCourseHoleScoreDAO golfCourseHoleScoreDAO;

	@Autowired
	private GolfCoursePlayerDAO golfCoursePlayerDAO;

	@Autowired
	private UserHome userHome;

	@Autowired
	private GolfClubHome golfClubHome;
	
	private RedisCachePool redisPool = RedisCacheManager.getInstance().getRedisPool(RedisConstants.POOL_COUNT);


	public void saveCourse(GolfCourse course) {
		Gson gson = new Gson();
		String json = gson.toJson(course.getPlayerList());
		course.setPlayerSetting(json);
		int courseId = golfCourseDAO.save(course).intValue();

		List<GolfCoursePlayer> playerList = course.getPlayerList();

		if (CollectionUtils.isNotEmpty(playerList)) {
			for (GolfCoursePlayer player : playerList) {
				player.setCourseId(courseId);
				golfCoursePlayerDAO.save(player);
			}
		}
	}

	public void saveHoleScore(GolfCourseHoleScore holeScore) {
		golfCourseHoleScoreDAO.save(holeScore);
	}

	public GolfCourse getGolfCourseById(Integer id) {
		GolfCourse course = golfCourseDAO.getGolfCourseById(id);

		if (course == null) {
			return null;
		} else {
			String settings = course.getPlayerSetting();
			Gson gson = new Gson();
			Type type = new TypeToken<List<GolfCoursePlayer>>() {
			}.getType();
			List<GolfCoursePlayer> playerList = gson.fromJson(settings, type);
			course.setPlayerList(playerList);
		}

		return course;
	}

	public List<GolfCourse> getHistoryCourseList(Integer userId,
			Integer offset, Integer limit) {
		List<Integer> courseIdList = golfCoursePlayerDAO.getHistoryCourseList(
				userId, offset, limit);
		List<GolfCourse> courseList = new ArrayList<GolfCourse>();
		if (CollectionUtils.isNotEmpty(courseIdList)) {
			Map<Integer, GolfCourse> courseMap = golfCourseDAO
					.getGolfCourseMapByIds(courseIdList);
			List<Integer> clubIdList = new ArrayList<Integer>();
			for (Integer courseId : courseIdList) {
				if (courseMap.containsKey(courseId)) {
					GolfCourse course = courseMap.get(courseId);
					clubIdList.add(course.getClubId());
				}
			}
			Map<Integer, GolfClub> clubMap = golfClubHome
					.getGolfClubMap(clubIdList);

			for (Integer courseId : courseIdList) {
				if (courseMap.containsKey(courseId)) {
					GolfCourse course = courseMap.get(courseId);

					Integer clubId = course.getClubId();
					if (clubMap.containsKey(clubId)) {
						GolfClub club = clubMap.get(clubId);
						course.setClubName(club.getName());
						if (StringUtils.isBlank(club.getLogo())) {
							course.setClubLogo(GolfConstant.IMAGE_DOMAIN
									+ GolfConstant.DEFAULT_CLUB_LOGO_URL);
						} else {
							course.setClubLogo(GolfConstant.IMAGE_DOMAIN
									+ club.getLogo());
						}
					} else {
						continue;
					}

					String settings = course.getPlayerSetting();
					Gson gson = new Gson();
					Type type = new TypeToken<List<GolfCoursePlayer>>() {
					}.getType();
					List<GolfCoursePlayer> playerList = gson.fromJson(settings,
							type);
					List<Integer> userIdList = new ArrayList<Integer>();
					for (GolfCoursePlayer player : playerList) {
						userIdList.add(player.getPlayerId());
					}

					Map<Integer, User> userMap = userHome
							.getUserMapByIds(userIdList);
					for (GolfCoursePlayer player : playerList) {
						if (userMap.containsKey(player.getPlayerId())) {
							player.setPlayerHead(userMap.get(
									player.getPlayerId()).getHeadUrl());

							if (StringUtils.isNotBlank(userMap.get(
									player.getPlayerId()).getHeadUrl())) {

								player.setPlayerHead(GolfConstant.IMAGE_DOMAIN
										+ userMap.get(player.getPlayerId())
												.getHeadUrl());
							} else {
								player.setPlayerHead(GolfConstant.IMAGE_DOMAIN
										+ GolfConstant.DEFAULT_HEAD_URL);
							}
						}else{
							player.setPlayerHead(GolfConstant.IMAGE_DOMAIN
									+ GolfConstant.DEFAULT_HEAD_URL);
						}
					}
					course.setPlayerList(playerList);
					courseList.add(course);
				}
			}

		}

		return courseList;
	}

	public List<GolfCourseHoleScore> getHoleScoreList(Integer courseId) {
		List<GolfCourseHoleScore> holeScoreList = golfCourseHoleScoreDAO
				.getGolfCourseHoleScoreList(courseId);
		return holeScoreList;
	}

	public List<GolfCourse> getLiveCourseList(Integer offset, Integer limit) {
		List<GolfCourse> courseList = golfCourseDAO.getLiveCourseList(offset,
				limit);
		if (CollectionUtils.isNotEmpty(courseList)) {
			List<Integer> clubIdList = new ArrayList<Integer>();
			for (GolfCourse course : courseList) {
				clubIdList.add(course.getClubId());
			}
			Map<Integer, GolfClub> clubMap = golfClubHome
					.getGolfClubMap(clubIdList);

			for (GolfCourse course : courseList) {
				Integer clubId = course.getClubId();
				if (clubMap.containsKey(clubId)) {
					GolfClub club = clubMap.get(clubId);
					course.setClubName(club.getName());
					if (StringUtils.isBlank(club.getLogo())) {
						course.setClubLogo(GolfConstant.IMAGE_DOMAIN
								+ GolfConstant.DEFAULT_CLUB_LOGO_URL);
					} else {
						course.setClubLogo(GolfConstant.IMAGE_DOMAIN
								+ club.getLogo());
					}
				} else {
					continue;
				}
				String settings = course.getPlayerSetting();
				Gson gson = new Gson();
				Type type = new TypeToken<List<GolfCoursePlayer>>() {
				}.getType();
				List<GolfCoursePlayer> playerList = gson.fromJson(settings,
						type);
				List<Integer> userIdList = new ArrayList<Integer>();
				for (GolfCoursePlayer player : playerList) {
					userIdList.add(player.getPlayerId());
				}

				Map<Integer, User> userMap = userHome
						.getUserMapByIds(userIdList);
				for (GolfCoursePlayer player : playerList) {
					if (userMap.containsKey(player.getPlayerId())) {
						player.setPlayerHead(userMap.get(player.getPlayerId())
								.getHeadUrl());

						if (StringUtils.isNotBlank(userMap.get(
								player.getPlayerId()).getHeadUrl())) {

							player.setPlayerHead(GolfConstant.IMAGE_DOMAIN
									+ userMap.get(player.getPlayerId())
											.getHeadUrl());
						} else {
							player.setPlayerHead(GolfConstant.IMAGE_DOMAIN
									+ GolfConstant.DEFAULT_HEAD_URL);
						}
					}else{
						player.setPlayerHead(GolfConstant.IMAGE_DOMAIN
								+ GolfConstant.DEFAULT_HEAD_URL);
					}
				}
				course.setPlayerList(playerList);
				course.setViewCount(getViewCount(course.getId()));
			}

		}

		return courseList;
	}
	
	public Integer incViewCount(Integer courseId){
		String key = GolfConstant.LIVE_VIEW_COUNT_KEY+courseId;
		return ((Long) redisPool.incrby(key, 1)).intValue();
	}
	
	public Integer getViewCount(Integer courseId){
		String key = GolfConstant.LIVE_VIEW_COUNT_KEY+courseId;
		return  NumberUtils.toInt(redisPool.get(key),0);
	}

}
