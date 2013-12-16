package com.jinfang.golf.team.model;

import java.io.Serializable;
import java.util.Date;

public class GolfTeamNotice implements Serializable{

	private Integer teamId;
	
	private String notice;
	
	private Date createdTime;

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

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}
	
	
	
	
	
}
