package com.jinfang.golf.team.model;

import java.io.Serializable;
import java.util.Date;

public class UserTeamApply implements Serializable{

	private Integer userId;
	
	private Integer teamId;
	
	private Integer status;//0 未处理 1同意加入 2 删除
	
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	
	
}
