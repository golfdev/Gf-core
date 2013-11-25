package com.jinfang.golf.user.model;

import java.io.Serializable;
import java.util.Date;

public class UserRelation implements Serializable{

	private Integer fromUserId;
	
	private Integer toUserId;
	
	private Date createdTime;


    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

	public Integer getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(Integer fromUserId) {
		this.fromUserId = fromUserId;
	}

	public Integer getToUserId() {
		return toUserId;
	}

	public void setToUserId(Integer toUserId) {
		this.toUserId = toUserId;
	}

	
	
}
