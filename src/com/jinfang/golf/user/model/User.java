package com.jinfang.golf.user.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.google.gson.annotations.Expose;
import com.jinfang.golf.constants.GolfConstant;

public class User implements Serializable{

	@Expose
	private Integer id;
	
	@Expose
	private String userName="";
	
	@Expose
	private String passWord="";
	
	@Expose
	private String email="";
	
	@Expose
	private String phone="";
	
	@Expose
	private Integer gender=0;
	
	@Expose
	private String headUrl;
	
	@Expose
	private Integer playAge=0;
	
	@Expose
	private String description="";
	
	@Expose
	private Double handicap=0.0;
	
	@Expose
	private Date createdTime;
	
	@Expose
	private String token="";
	
	@Expose
	private String city="";
	
	@Expose
	private Integer status=0;
	
	@Expose
	private Integer friendCount = 0;
	
	@Expose
	private Integer followCount = 0;
	
	@Expose
	private Integer fansCount = 0;
	
	@Expose
	private String realName="";
	
	@Expose
	private String sfzId="";
	
	@Expose
	private Integer isFollowed = 0;
	
	
	private String source=""; //0表示android 1表示ios

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Integer getFriendCount() {
		return friendCount;
	}

	public void setFriendCount(Integer friendCount) {
		this.friendCount = friendCount;
	}

	public Integer getFollowCount() {
		return followCount;
	}

	public void setFollowCount(Integer followCount) {
		this.followCount = followCount;
	}

	public Integer getFansCount() {
		return fansCount;
	}

	public void setFansCount(Integer fansCount) {
		this.fansCount = fansCount;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getSfzId() {
		return sfzId;
	}

	public void setSfzId(String sfzId) {
		this.sfzId = sfzId;
	}

	public Integer getIsFollowed() {
		return isFollowed;
	}

	public void setIsFollowed(Integer isFollowed) {
		this.isFollowed = isFollowed;
	}
	
	
	
	
	
	
}
