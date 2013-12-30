package com.jinfang.golf.club.model;

import java.io.Serializable;
import java.util.Date;

import com.google.gson.annotations.Expose;

public class GolfClubYard implements Serializable{
	
	@Expose
	private Integer clubId;

	@Expose
	private Integer holeNum;
	
	@Expose
	private Integer parScore;
	
	@Expose
	private Date createdTime;

	public Integer getClubId() {
		return clubId;
	}

	public void setClubId(Integer clubId) {
		this.clubId = clubId;
	}


	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
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

	
	
}
