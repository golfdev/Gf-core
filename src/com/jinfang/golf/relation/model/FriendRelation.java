package com.jinfang.golf.relation.model;

import java.io.Serializable;
import java.util.Date;

public class FriendRelation implements Serializable{

	private Integer host;
	
	private Integer guest;
	
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

   
	
}
