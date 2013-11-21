package com.jinfang.golf.passport.model;

import java.io.Serializable;

public class DailySalt implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long createTime;

	private String salt;
	
	public DailySalt(){
		
	}
	
	public DailySalt(Long createTime, String salt){
		this.createTime = createTime;
		this.salt = salt;
	}
	
	public Long getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	
	public String getSalt() {
		return salt;
	}
	
	public void setSalt(String salt) {
		this.salt = salt;
	}
	
}
