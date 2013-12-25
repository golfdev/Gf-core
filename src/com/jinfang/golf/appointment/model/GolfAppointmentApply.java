package com.jinfang.golf.appointment.model;

import java.io.Serializable;
import java.util.Date;

public class GolfAppointmentApply implements Serializable {
    
	private Integer userId;
	
	private Integer appointId;
	
	private Integer status;
	
	private Date createdTime;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAppointId() {
        return appointId;
    }

    public void setAppointId(Integer appointId) {
        this.appointId = appointId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

	
	

}
