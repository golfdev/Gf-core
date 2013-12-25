package com.jinfang.golf.appointment.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.jinfang.golf.user.model.User;

public class GolfAppointment implements Serializable {
	
    @Expose
	private Integer id;
	
    @Expose
	private Integer clubId;
	
    @Expose
	private Integer creatorId;
	
    @Expose
	private Date appointTime;
	
    @Expose
	private Double avgPrice;
	
    @Expose
	private Integer gender;
	
    @Expose
	private Integer minHandicap;
	
    @Expose
	private Integer maxHandicap;
	
	private Integer privateSetting; //0 公开 1 队友可见 2不公开
	
    @Expose
	private String description;
    
    @Expose
    private String city;
	
    @Expose
	private List<User> userList;
	
    @Expose
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

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }


	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	
	
	

}
