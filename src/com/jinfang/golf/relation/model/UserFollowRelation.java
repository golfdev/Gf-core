package com.jinfang.golf.relation.model;

import java.io.Serializable;
import java.util.Date;

public class UserFollowRelation implements Serializable{

	private Integer host;
	
	private Integer guest;
	
	private Integer status;
	
	private Date createdTime;


    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

	public Integer getHost() {
		return host;
	}

	public void setHost(Integer host) {
		this.host = host;
	}

	public Integer getGuest() {
		return guest;
	}

	public void setGuest(Integer guest) {
		this.guest = guest;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

    
	

	
	
}
