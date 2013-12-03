package com.jinfang.golf.user.model;

import java.io.Serializable;
import java.util.Date;

public class UserCentify implements Serializable {
	
	private Integer userId;
	
	private String sfzId;
	
	private String realName;
	
	private Date createdTime;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getSfzId() {
		return sfzId;
	}

	public void setSfzId(String sfzId) {
		this.sfzId = sfzId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	
	

}
