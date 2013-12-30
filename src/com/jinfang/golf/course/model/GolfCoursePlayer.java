package com.jinfang.golf.course.model;

import java.io.Serializable;
import java.util.Date;

public class GolfCoursePlayer implements Serializable {
	
	private Integer courseId;
	
	private Integer playerId;
	
	private String playerName;
	
	private Integer teeNum;
	
	private Integer serialNum;
	
	private Date createdTime;

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public Integer getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public Integer getTeeNum() {
		return teeNum;
	}

	public void setTeeNum(Integer teeNum) {
		this.teeNum = teeNum;
	}

	public Integer getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(Integer serialNum) {
		this.serialNum = serialNum;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	
	

}
