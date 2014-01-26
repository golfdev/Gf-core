package com.jinfang.golf.appointment.home;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jinfang.golf.appointment.dao.GolfAppointmentDAO;
import com.jinfang.golf.appointment.dao.GolfAppointmentMemberDAO;
import com.jinfang.golf.appointment.model.GolfAppointment;
import com.jinfang.golf.appointment.model.GolfAppointmentMember;
import com.jinfang.golf.club.home.GolfClubHome;
import com.jinfang.golf.club.model.GolfClub;
import com.jinfang.golf.constants.GolfConstant;
import com.jinfang.golf.team.home.UserTeamHome;
import com.jinfang.golf.user.home.UserHome;
import com.jinfang.golf.user.model.User;

@Component
public class GolfAppointmentHome {

	@Autowired
	private GolfAppointmentDAO golfAppointmentDAO;

	@Autowired
	private UserTeamHome userTeamHome;

	@Autowired
	private GolfAppointmentMemberDAO golfAppointmentMemberDAO;

	@Autowired
	private UserHome userHome;
	
	@Autowired
	private GolfClubHome golfClubHome;

	public void createApponint(GolfAppointment appoint) {
		int appointId = golfAppointmentDAO.save(appoint).intValue();
		GolfAppointmentMember member = new GolfAppointmentMember();
		member.setAppointId(appointId);
		member.setUserId(appoint.getCreatorId());
		golfAppointmentMemberDAO.save(member);
	}

	public List<GolfAppointment> getAppointmentList(Integer userId,
			String city, Integer offset, Integer limit) {
		List<Integer> teamIdList = userTeamHome.getTeamIdListByUid(userId);
		if (CollectionUtils.isNotEmpty(teamIdList)) {
			List<Integer> userIdList = userTeamHome
					.getMemberListByTeamIds(teamIdList);
			List<GolfAppointment> appointList = golfAppointmentDAO
					.getGolfAppointmentListWithTeam(userIdList, city, offset,
							limit);
			for (GolfAppointment appoint : appointList) {
				List<Integer> uidList = golfAppointmentMemberDAO
						.getUserIdListByAppointId(appoint.getId());
				List<User> userList = userHome.getUserListByIds(uidList);
				for(User user:userList){
					if (StringUtils.isNotBlank(user.getHeadUrl())) {
						user.setHeadUrl(GolfConstant.IMAGE_DOMAIN
								+ user.getHeadUrl());
					} else {
						user.setHeadUrl(GolfConstant.IMAGE_DOMAIN
								+ GolfConstant.DEFAULT_HEAD_URL);
					}
				}
				appoint.setUserList(userList);
				GolfClub club = golfClubHome.getGolfClubById(appoint.getClubId());
				appoint.setClubName(club.getName());
				if (StringUtils.isBlank(club.getLogo())) {
					appoint.setClubLogo(GolfConstant.IMAGE_DOMAIN
							+ GolfConstant.DEFAULT_CLUB_LOGO_URL);
				} else {
					appoint.setClubLogo(GolfConstant.IMAGE_DOMAIN
							+ club.getLogo());
				}
			}
			return appointList;
		} else {
			List<GolfAppointment> appointList = golfAppointmentDAO
					.getGolfAppointmentList(city, offset, limit);
			for (GolfAppointment appoint : appointList) {
				List<Integer> uidList = golfAppointmentMemberDAO
						.getUserIdListByAppointId(appoint.getId());
				List<User> userList = userHome.getUserListByIds(uidList);
				for(User user:userList){
					if (StringUtils.isNotBlank(user.getHeadUrl())) {
						user.setHeadUrl(GolfConstant.IMAGE_DOMAIN
								+ user.getHeadUrl());
					} else {
						user.setHeadUrl(GolfConstant.IMAGE_DOMAIN
								+ GolfConstant.DEFAULT_HEAD_URL);
					}
				}
				appoint.setUserList(userList);
				GolfClub club = golfClubHome.getGolfClubById(appoint.getClubId());
				appoint.setClubName(club.getName());
				if (StringUtils.isBlank(club.getLogo())) {
					appoint.setClubLogo(GolfConstant.IMAGE_DOMAIN
							+ GolfConstant.DEFAULT_CLUB_LOGO_URL);
				} else {
					appoint.setClubLogo(GolfConstant.IMAGE_DOMAIN
							+ club.getLogo());
				}
			}
			return appointList;

		}
	}

	public GolfAppointment getGolfAppointment(Integer id) {
		GolfAppointment appoint = golfAppointmentDAO.getGolfAppointment(id);
		GolfClub club = golfClubHome.getGolfClubById(appoint.getClubId());
		appoint.setClubName(club.getName());
		appoint.setClubLogo(club.getLogo());
		List<Integer> uidList = golfAppointmentMemberDAO
				.getUserIdListByAppointId(appoint.getId());
		List<User> userList = userHome.getUserListByIds(uidList);
		appoint.setUserList(userList);
		return appoint;
	}

	public void addParter(GolfAppointmentMember appointMember) {
		golfAppointmentMemberDAO.save(appointMember);
	}

	public void removeParter(Integer appointId, Integer userId) {
		golfAppointmentMemberDAO.removeMember(appointId, userId);
	}

}
