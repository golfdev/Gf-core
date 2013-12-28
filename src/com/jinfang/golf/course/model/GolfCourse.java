package com.jinfang.golf.course.model;

import java.io.Serializable;
import java.util.Date;

import com.google.gson.annotations.Expose;

public class GolfCourse implements Serializable{
	
	private Integer id;
	
	private Integer clubId;
	
	private Integer playerId;
	
	private Integer playerName;
	
	private Integer teeNum;
	
	private Integer serialNum;
	
	private Integer isLive;
	
	private Date createdTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getClubId() {
		return clubId;
	}

	public void setClubId(Integer clubId) {
		this.clubId = clubId;
	}



	public Integer getIsLive() {
		return isLive;
	}

	public void setIsLive(Integer isLive) {
		this.isLive = isLive;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Integer getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
	}

	public Integer getPlayerName() {
		return playerName;
	}

	public void setPlayerName(Integer playerName) {
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
	
	
}
