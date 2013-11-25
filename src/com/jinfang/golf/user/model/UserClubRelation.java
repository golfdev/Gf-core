package com.jinfang.golf.user.model;

import java.io.Serializable;
import java.util.Date;

public class UserClubRelation implements Serializable{

	private Integer userId;
	
	private Integer clubId;
	
	private Date createdTime;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Integer getClubId() {
        return clubId;
    }

    public void setClubId(Integer clubId) {
        this.clubId = clubId;
    }
	
	
	
	
	
}
