package com.jinfang.golf.user.model;

import java.io.Serializable;
import java.util.Date;

public class UserTeamRelation implements Serializable{

	private Integer userId;
	
	private Integer teamId;
	
	private Date createdTime;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
	
	
	
	
	
}
