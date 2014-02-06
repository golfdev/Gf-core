package com.jinfang.golf.team.model;

import java.io.Serializable;
import java.util.Date;

import com.google.gson.annotations.Expose;

public class UserTeamApply implements Serializable{

	@Expose
	private Integer userId;
	
	@Expose
	private String userName;
	
	@Expose
	private String userHead;
	
	@Expose
	private Integer teamId;
	
	@Expose
	private Integer status;//0 未处理 1同意加入 2 拒绝
	
	@Expose
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserHead() {
		return userHead;
	}

	public void setUserHead(String userHead) {
		this.userHead = userHead;
	}

	
	
}
