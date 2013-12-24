package com.jinfang.golf.appointment.model;

import java.io.Serializable;
import java.util.Date;

public class GolfAppointment implements Serializable {
	
	private Integer id;
	
	private Integer clubId;
	
	private Date appointTime;
	
	private Double avgPrice;
	
	private Integer gender;
	
	private Integer minHandicap;
	
	private Integer maxHandicap;
	
	private Integer privateSetting;
	
	private String description;
	
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

	public Date getAppointTime() {
		return appointTime;
	}

	public void setAppointTime(Date appointTime) {
		this.appointTime = appointTime;
	}

	public Double getAvgPrice() {
		return avgPrice;
	}

	public void setAvgPrice(Double avgPrice) {
		this.avgPrice = avgPrice;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Integer getMinHandicap() {
		return minHandicap;
	}

	public void setMinHandicap(Integer minHandicap) {
		this.minHandicap = minHandicap;
	}

	public Integer getMaxHandicap() {
		return maxHandicap;
	}

	public void setMaxHandicap(Integer maxHandicap) {
		this.maxHandicap = maxHandicap;
	}

	public Integer getPrivateSetting() {
		return privateSetting;
	}

	public void setPrivateSetting(Integer privateSetting) {
		this.privateSetting = privateSetting;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	
	
	
	

}
