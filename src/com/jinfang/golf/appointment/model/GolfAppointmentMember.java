package com.jinfang.golf.appointment.model;

import java.io.Serializable;
import java.util.Date;

public class GolfAppointmentMember implements Serializable {

    private Integer appointId;

    private Integer userId;

    private Date createdTime;

    public Integer getAppointId() {
        return appointId;
    }

    public void setAppointId(Integer appointId) {
        this.appointId = appointId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

}
