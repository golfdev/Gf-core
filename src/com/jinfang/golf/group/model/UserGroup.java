package com.jinfang.golf.group.model;

import java.io.Serializable;
import java.util.Date;

public class UserGroup implements Serializable {
	private static final long serialVersionUID = 5658936810963790871L;
	private int groupId;
	private int userId;
	private Date time;
	// 注意：当通过群组取用户时，下面字段为空
	private int status;
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
