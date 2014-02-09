package com.jinfang.golf.course.model;

import java.io.Serializable;
import java.util.Date;

import com.google.gson.annotations.Expose;

public class GolfCourseHoleScore implements Serializable{
	
	private Integer courseId;
	
    private Integer holeNum;
    
    private Integer playerId;
    
    private String playerName;
    
    private Integer serialNum;
    
    private Integer parScore;
    
    private Integer totalScore;
    
    private Integer putterScore;
    
    private Integer point;
    
    private Integer subScore;
    
    private Date createdTime;

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public Integer getHoleNum() {
		return holeNum;
	}

	public void setHoleNum(Integer holeNum) {
		this.holeNum = holeNum;
	}

	public Integer getParScore() {
		return parScore;
	}

	public void setParScore(Integer parScore) {
		this.parScore = parScore;
	}

	public Integer getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}


	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Integer getSubScore() {
		return subScore;
	}

	public void setSubScore(Integer subScore) {
		this.subScore = subScore;
	}

	public Integer getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
	}

	public Integer getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(Integer serialNum) {
		this.serialNum = serialNum;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public Integer getPutterScore() {
		return putterScore;
	}

	public void setPutterScore(Integer putterScore) {
		this.putterScore = putterScore;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}
    
    
}
