package com.jinfang.golf.team.home;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.jinfang.golf.cache.redis.RedisCacheManager;
import com.jinfang.golf.cache.redis.RedisCachePool;
import com.jinfang.golf.cache.redis.RedisConstants;
import com.jinfang.golf.constants.GolfConstant;
import com.jinfang.golf.group.manager.GroupManager;
import com.jinfang.golf.group.model.Group;
import com.jinfang.golf.team.dao.GolfTeamDAO;
import com.jinfang.golf.team.dao.GolfTeamNoticeDAO;
import com.jinfang.golf.team.dao.UserTeamApplyDAO;
import com.jinfang.golf.team.dao.UserTeamRelationDAO;
import com.jinfang.golf.team.model.GolfTeam;
import com.jinfang.golf.team.model.GolfTeamNotice;
import com.jinfang.golf.team.model.UserTeamApply;
import com.jinfang.golf.team.model.UserTeamRelation;
import com.jinfang.golf.user.home.UserHome;
import com.jinfang.golf.user.model.User;

@Component
public class UserTeamHome {

	@Autowired
	private GolfTeamDAO golfTeamDAO;

	@Autowired
	private UserTeamRelationDAO userTeamRelationDAO;

	@Autowired
	private UserTeamApplyDAO userTeamApplyDAO;

	@Autowired
	private UserHome userHome;

	@Autowired
	private GolfTeamNoticeDAO golfTeamNoticeDAO;

	@Autowired
	private GroupManager groupManager;
	
	private RedisCachePool redisPool = RedisCacheManager.getInstance().getRedisPool(RedisConstants.POOL_COUNT);


	public void removeFromTeam(Integer userId, Integer teamId) {
		GolfTeam team = getGolfTeamById(teamId);
		userTeamRelationDAO.deleteByUserIdAndTeamId(userId, teamId);
		if (teamId != null) {
			groupManager.delUser(team.getGroupId(), userId);
		}
		String key = GolfConstant.TEAM_MEMBER_COUNT_KEY+teamId;
		redisPool.incrby(key, -1);
	}

	public void createGolfTeam(GolfTeam team) {

		String groupName = team.getName() + "群";

		// 创建群聊
		Group group = groupManager.createGroup(team.getCreatorId(), groupName,
				null, Group.TYPE_TEAM);
		team.setGroupId(group.getGroupId());
		int teamId = golfTeamDAO.save(team).intValue();
		UserTeamRelation relation = new UserTeamRelation();
		relation.setUserId(team.getCreatorId());
		relation.setTeamId(teamId);
		relation.setIsLeader(1);
		userTeamRelationDAO.save(relation);
		
		//成员数+1
		String key = GolfConstant.TEAM_MEMBER_COUNT_KEY+teamId;
		redisPool.incrby(key, 1);

	}

	public List<GolfTeam> getGolfTeamList(Integer userId, String city,
			Integer offset, Integer limit) {
		List<GolfTeam> teamList = golfTeamDAO.getGolfTeamList(city, offset,
				limit);
		if (teamList == null) {
			return null;
		} else {
			List<Integer> teamIds = null;
			if (userId != 0) {
				teamIds = getTeamIdListByUid(userId);
			}
			for (GolfTeam team : teamList) {
				if (teamIds != null && teamIds.contains(team.getId())) {
					team.setIsMember(1);
					if (team.getCreatorId().equals(userId)) {
						team.setCanEdit(1);
						String key = GolfConstant.TEAM_APPLY_COUNT_KEY+team.getId();
						team.setApplyCount(NumberUtils.toInt(redisPool.get(key)));
					}
				} else {
					team.setIsMember(0);
				}
				String key = GolfConstant.TEAM_MEMBER_COUNT_KEY+team.getId();
				team.setMemberCount(NumberUtils.toInt(redisPool.get(key)));
                team.setLogo(GolfConstant.IMAGE_DOMAIN + team.getLogo());
			}
		}
		return teamList;
	}

	public List<GolfTeam> getJoinedGolfTeamList(Integer userId, Integer offset,
			Integer limit) {
		List<Integer> teamIds = getJoinedTeamIdListByUidAndPage(userId, offset,
				limit);
		if (CollectionUtils.isEmpty(teamIds)) {
			return null;
		}

		Map<Integer, GolfTeam> teamMap = golfTeamDAO
				.getGolfTeamListByIds(teamIds);
		List<GolfTeam> result = new ArrayList<GolfTeam>();
		for (Integer id : teamIds) {
			if (teamMap.containsKey(id)) {
				GolfTeam team = teamMap.get(id);
				String memberKey = GolfConstant.TEAM_MEMBER_COUNT_KEY+team.getId();
				team.setMemberCount(NumberUtils.toInt(redisPool.get(memberKey)));
				result.add(team);
			}
		}
		return result;

	}

	public List<GolfTeam> getMyCreatedGolfTeamList(Integer userId,
			Integer offset, Integer limit) {
		List<Integer> teamIds = getCreatedTeamIdListByUidAndPage(userId,
				offset, limit);
		if (CollectionUtils.isEmpty(teamIds)) {
			return null;
		}

		Map<Integer, GolfTeam> teamMap = golfTeamDAO
				.getGolfTeamListByIds(teamIds);
		List<GolfTeam> result = new ArrayList<GolfTeam>();
		for (Integer id : teamIds) {
			if (teamMap.containsKey(id)) {
				GolfTeam team = teamMap.get(id);
				String key = GolfConstant.TEAM_APPLY_COUNT_KEY+team.getId();
				team.setApplyCount(NumberUtils.toInt(redisPool.get(key)));
				String memberKey = GolfConstant.TEAM_MEMBER_COUNT_KEY+team.getId();
				team.setMemberCount(NumberUtils.toInt(redisPool.get(memberKey)));
				result.add(team);
			}
		}
		return result;

	}

	public List<GolfTeam> getTeamListByIds(List<Integer> teamIds) {
		if (CollectionUtils.isEmpty(teamIds)) {
			return null;
		}
		Map<Integer, GolfTeam> teamMap = getTeamMapByIds(teamIds);
		List<GolfTeam> teamList = new ArrayList<GolfTeam>();
		for (Integer teamId : teamIds) {
			if (teamMap.containsKey(teamId)) {
				teamList.add(teamMap.get(teamId));
			}
		}
		return teamList;
	}

	public Map<Integer, GolfTeam> getTeamMapByIds(List<Integer> teamIds) {
		return golfTeamDAO.getGolfTeamListByIds(teamIds);
	}

	public GolfTeam getGolfTeamById(Integer id) {
		GolfTeam team = golfTeamDAO.getGolfTeamById(id);
		return team;
	}

	public GolfTeam getGolfTeamByIdAndUserId(Integer id, Integer userId) {
		GolfTeam team = golfTeamDAO.getGolfTeamById(id);

		List<Integer> teamIds = null;
		if (userId != 0) {
			teamIds = getTeamIdListByUid(userId);
		}

		if (teamIds != null && teamIds.contains(team.getId())) {
			team.setIsMember(1);

			if (team.getCreatorId().equals(userId)) {
				team.setCanEdit(1);
			}

		} else {
			team.setIsMember(0);
//			team.setNotice("");
		}

		return team;

	}

	public List<User> getMemberListByTeamId(Integer teamId, Integer offset,
			Integer limit) {
		List<Integer> userIdList = userTeamRelationDAO.getMemberIdListByTeamId(
				teamId, offset, limit);
		return userHome.getUserListByIds(userIdList);
	}

	public List<Integer> getMemberListByTeamIds(List<Integer> teamIds) {
		List<Integer> userIdList = userTeamRelationDAO
				.getMemberIdListByTeamIdList(teamIds);
		return userIdList;
	}

	public List<Integer> getTeamIdListByUid(Integer userId) {
		return userTeamRelationDAO.getByUserId(userId);
	}

	public List<Integer> getTeamIdListByUidAndPage(Integer userId,
			Integer offset, Integer limit) {
		return userTeamRelationDAO.getByUserIdAndPage(userId, offset, limit);
	}

	public List<GolfTeam> getTeamListByUid(Integer userId, Integer offset,
			Integer limit) {
		List<Integer> teamIdList = getTeamIdListByUidAndPage(userId, offset,
				limit);
		if (CollectionUtils.isEmpty(teamIdList)) {
			return null;
		} else {
			List<GolfTeam> teamList = getTeamListByIds(teamIdList);
			return teamList;
		}

	}

	public List<Integer> getJoinedTeamIdListByUidAndPage(Integer userId,
			Integer offset, Integer limit) {
		return userTeamRelationDAO.getJoinedByUserIdAndPage(userId, offset,
				limit);
	}

	public List<Integer> getCreatedTeamIdListByUidAndPage(Integer userId,
			Integer offset, Integer limit) {
		return userTeamRelationDAO.getOwnedTeamIdByUserIdAndPage(userId,
				offset, limit);
	}

	public void updateGolfTeam(GolfTeam team) {
		golfTeamDAO.updateGolfTeam(team);
	}

	public void updateGolfTeamNotice(GolfTeam team) {
		golfTeamDAO.updateNotice(team);
		GolfTeamNotice notice = new GolfTeamNotice();
		notice.setTeamId(team.getId());
		notice.setNotice(team.getNotice());
		golfTeamNoticeDAO.save(notice);
	}

	public Integer getTeamMemberCount(Integer id) {
		Integer count = userTeamRelationDAO.getMemberCountByTeamId(id);
		if (count == null) {
			return 0;
		} else {
			return count;
		}

	}

	public Integer getTotalTeamCount() {
		return golfTeamDAO.getTotalTeamCount();
	}

	public List<GolfTeam> getAllGolfTeamList(Integer offset, Integer limit) {
		return golfTeamDAO.getAllGolfTeamList(offset, limit);
	}

	/**
	 * 保存入队申请
	 * 
	 * @param apply
	 */
	public void addApply(UserTeamApply apply) {
		userTeamApplyDAO.save(apply);
		String key = GolfConstant.TEAM_APPLY_COUNT_KEY+apply.getTeamId();
		redisPool.incrby(key, 1);
	}

	public List<UserTeamApply> getTeamApplyList(Integer teamId, Integer offset,
			Integer limit) {
		List<UserTeamApply> applyList = userTeamApplyDAO.getApplyListByTeamId(
				teamId, offset, limit);
		List<Integer> userIdList = new ArrayList<Integer>();
		for (UserTeamApply apply : applyList) {
			userIdList.add(apply.getUserId());
		}
		Map<Integer, User> userMap = userHome.getUserMapByIds(userIdList);

		for (UserTeamApply apply : applyList) {
			if (userMap.containsKey(apply.getUserId())) {
				apply.setUserName(userMap.get(apply.getUserId()).getUserName());
				if (StringUtils.isNotBlank(userMap.get(apply.getUserId())
						.getHeadUrl())) {
					apply.setUserHead(GolfConstant.IMAGE_DOMAIN
							+ userMap.get(apply.getUserId()).getHeadUrl());
				} else {
					apply.setUserHead(GolfConstant.IMAGE_DOMAIN
							+ GolfConstant.DEFAULT_HEAD_URL);
				}
			} else {
				apply.setUserName("Golf球手");
			}
		}
		return applyList;
	}

	public void updateApplyStatus(Integer teamId, Integer userId, Integer status) {
		userTeamApplyDAO.updateStatus(userId, teamId, status);
		if (status == 1) {
			UserTeamRelation relation = new UserTeamRelation();
			relation.setUserId(userId);
			relation.setTeamId(teamId);
			userTeamRelationDAO.save(relation);
			GolfTeam team = getGolfTeamById(teamId);
			groupManager.addUser(team.getGroupId(), userId);
			
			//更新成员数量
			String memberKey = GolfConstant.TEAM_MEMBER_COUNT_KEY+teamId;
			redisPool.incrby(memberKey, 1);
		}
		
		String key = GolfConstant.TEAM_APPLY_COUNT_KEY+teamId;
		long value = redisPool.incrby(key, -1);
		if(value<=0){
			redisPool.set(key, "0");
		}

	}

	public void addMember(Integer teamId, Integer userId) {
		UserTeamRelation relation = new UserTeamRelation();
		relation.setUserId(userId);
		relation.setTeamId(teamId);
		relation.setIsLeader(0);
		userTeamRelationDAO.save(relation);
		GolfTeam team = getGolfTeamById(teamId);
		if (team != null && team.getGroupId() != null) {
			groupManager.addUser(team.getGroupId(), userId);
		}
		String key = GolfConstant.TEAM_MEMBER_COUNT_KEY+teamId;
		redisPool.incrby(key, 1);
	}

}
