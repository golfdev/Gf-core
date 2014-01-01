package com.jinfang.golf.course.model;

import java.io.Serializable;
import java.util.Date;

import com.google.gson.annotations.Expose;

public class GolfCoursePlayer implements Serializable {
	
	
	private Integer courseId;
	
	@Expose
	private Integer playerId;
	
	@Expose
	private String playerName;
	
	@Expose
	private String playerHead;
	
	@Expose
	private Integer teeNum;
	
	@Expose
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

	public String getPlayerHead() {
		return playerHead;
	}

	public void setPlayerHead(String playerHead) {
		this.playerHead = playerHead;
	}
	
	

}
