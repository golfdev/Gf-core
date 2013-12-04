package com.jinfang.golf.team.model;

import java.io.Serializable;
import java.util.Date;

public class UserTeamRelation implements Serializable{

	private Integer userId;
	
	private Integer teamId;
	
	private Integer isLeader;
	
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

    public Integer getIsLeader() {
        return isLeader;
    }

    public void setIsLeader(Integer isLeader) {
        this.isLeader = isLeader;
    }
	
	
	
	
	
}
