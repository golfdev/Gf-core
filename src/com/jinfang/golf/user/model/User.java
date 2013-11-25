package com.jinfang.golf.user.model;

import java.io.Serializable;
import java.util.Date;

import com.google.gson.annotations.Expose;

public class User implements Serializable{

	private Integer id;
	
	private String userName;
	
	private String passWord;
	
	private String email;
	
	private String phone;
	
	private Integer gender;
	
	private String headUrl;
	
	private Integer playAge;
	
	private String description;
	
	private Double handicap;
	
	private Date createdTime;
	
	private String token;
	
	private String city;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public Integer getPlayAge() {
        return playAge;
    }

    public void setPlayAge(Integer playAge) {
        this.playAge = playAge;
    }


    public Double getHandicap() {
        return handicap;
    }

    public void setHandicap(Double handicap) {
        this.handicap = handicap;
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
	
	
	
	
	
	
}
