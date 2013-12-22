package com.jinfang.golf.club.model;

import java.io.Serializable;
import java.util.Date;

import com.google.gson.annotations.Expose;

public class GolfClubWayItem implements Serializable{

	@Expose
	private Integer id;
	
	@Expose
	private Integer clubId;
	
	@Expose
	private String itemName;
	
	@Expose
	private Date availableTime;
	
	@Expose
	private Date availableDate;
	
	@Expose
	private Integer status;
	
	
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


	public Date getAvailableTime() {
		return availableTime;
	}

	public void setAvailableTime(Date availableTime) {
		this.availableTime = availableTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getAvailableDate() {
		return availableDate;
	}

	public void setAvailableDate(Date availableDate) {
		this.availableDate = availableDate;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	
	
}
