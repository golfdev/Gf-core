package com.jinfang.golf.appointment.home;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jinfang.golf.appointment.dao.GolfAppointmentDAO;
import com.jinfang.golf.appointment.model.GolfAppointment;
import com.jinfang.golf.team.home.UserTeamHome;

@Component
public class GolfAppointmentHome {
    
    @Autowired
	private GolfAppointmentDAO golfAppointmentDAO;
    
    @Autowired
    private UserTeamHome userTeamHome;
    
    
    public void createApponint(GolfAppointment appoint){
        golfAppointmentDAO.save(appoint);
    }
    
    public List<GolfAppointment> getAppointmentList(Integer userId,Integer offset,Integer limit){
        
        return golfAppointmentDAO.getGolfAppointmentList(offset, limit);
    }
    
    public GolfAppointment getGolfAppointment(Integer id){
        return golfAppointmentDAO.getGolfAppointment(id);
    }
	
	
}
