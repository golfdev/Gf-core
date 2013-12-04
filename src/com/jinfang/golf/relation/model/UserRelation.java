package com.jinfang.golf.relation.model;

import java.io.Serializable;
import java.util.Date;

public class UserRelation implements Serializable{

	private Integer fromUid;
	
	private Integer toUid;
	
	private Integer status;
	
	private Date createdTime;


    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Integer getFromUid() {
        return fromUid;
    }

    public void setFromUid(Integer fromUid) {
        this.fromUid = fromUid;
    }

    public Integer getToUid() {
        return toUid;
    }

    public void setToUid(Integer toUid) {
        this.toUid = toUid;
    }

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	

	
	
}
