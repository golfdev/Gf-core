package com.jinfang.golf.user.model;

import java.io.Serializable;
import java.util.Date;

public class UserToken implements Serializable {
	
	private Integer userId;
	
	private String token;
	
	private String source;
	
	private Date createdTime;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	
	

}
