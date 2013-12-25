package com.jinfang.golf.appointment.home;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jinfang.golf.appointment.dao.GolfAppointmentDAO;
import com.jinfang.golf.appointment.dao.GolfAppointmentMemberDAO;
import com.jinfang.golf.appointment.model.GolfAppointment;
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
    
    
    public void createApponint(GolfAppointment appoint){
        golfAppointmentDAO.save(appoint);
    }
    
    public List<GolfAppointment> getAppointmentList(Integer userId,Integer offset,Integer limit){
        List<Integer> teamIdList = userTeamHome.getTeamIdListByUid(userId);
        if(CollectionUtils.isNotEmpty(teamIdList)){
            List<Integer> userIdList = userTeamHome.getMemberListByTeamIds(teamIdList);
            List<GolfAppointment> appointList = golfAppointmentDAO.getGolfAppointmentListWithTeam(userIdList,offset, limit);
            for(GolfAppointment appoint:appointList){
                List<Integer> uidList = golfAppointmentMemberDAO.getUserIdListByAppointId(appoint.getId());
                List<User> userList = userHome.getUserListByIds(uidList);
                appoint.setUserList(userList);
            }
            return appointList;
        }else{
            List<GolfAppointment> appointList =  golfAppointmentDAO.getGolfAppointmentList(offset,limit);
            for(GolfAppointment appoint:appointList){
                List<Integer> uidList = golfAppointmentMemberDAO.getUserIdListByAppointId(appoint.getId());
                List<User> userList = userHome.getUserListByIds(uidList);
                appoint.setUserList(userList);
            }
            return appointList;

        }
    }
    
    public GolfAppointment getGolfAppointment(Integer id){
        return golfAppointmentDAO.getGolfAppointment(id);
    }
	
	
}
