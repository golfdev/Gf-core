package com.jinfang.golf.passport.model;

import java.io.Serializable;

public class DailySalt implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long createdTime;

	private String salt;
	
	public DailySalt(){
		
	}
	
	public DailySalt(Long createdTime, String salt){
		this.createdTime = createdTime;
		this.salt = salt;
	}
	
	public Long getCreatedTime() {
		return createdTime;
	}
	
	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}
	
	public String getSalt() {
		return salt;
	}
	
	public void setSalt(String salt) {
		this.salt = salt;
	}
	
}
