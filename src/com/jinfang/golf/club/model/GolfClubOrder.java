package com.jinfang.golf.club.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.google.gson.annotations.Expose;

public class GolfClubOrder implements Serializable {

	@Expose
	private Integer id;

	@Expose
	private Integer clubId;

	@Expose
	private Date teeTime;

	private List<Integer> wayIdList;

	private Integer userId;

	private String playerNames;

	private Integer playerNum;

	private Double totalPrice;

	private Integer status;

	private Date createdTime;

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

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

	public Date getTeeTime() {
		return teeTime;
	}

	public void setTeeTime(Date teeTime) {
		this.teeTime = teeTime;
	}


	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getPlayerNames() {
		return playerNames;
	}

	public void setPlayerNames(String playerNames) {
		this.playerNames = playerNames;
	}

	public Integer getPlayerNum() {
		return playerNum;
	}

	public void setPlayerNum(Integer playerNum) {
		this.playerNum = playerNum;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<Integer> getWayIdList() {
		return wayIdList;
	}

	public void setWayIdList(List<Integer> wayIdList) {
		this.wayIdList = wayIdList;
	}

    
	
}
