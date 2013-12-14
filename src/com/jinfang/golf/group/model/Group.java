package com.jinfang.golf.group.model;

import java.io.Serializable;
import java.util.Date;

import com.google.gson.annotations.Expose;
import com.jinfang.golf.constants.GolfConstant;

public class Group implements Serializable {
	public static final int TYPE_TEAM = 0; // 球队的微群
	public static final int TYPE_NORMAL = 1; // 普通的微群
	public static final String DEFAULT_LOGO = GolfConstant.HEAD_DOMAIN + "/chat/default_group_logo.jpg";
	private static final long serialVersionUID = -8192799575587504840L;
	@Expose
	private int groupId;
	@Expose
	private String name = "";
	@Expose
	private Date time;
	private int type;
	@Expose
	private int userCount;
	private int createrId;
	private int status;
	// 最后说话的用户ID
	@Expose
	private int lastUserId;
	// 最后说话的内容
	@Expose
	private String lastText = "";
	// 最后说话的时间
	@Expose
	private Date lastTime;
	
	// 注意，以下的变量在group表里没有，需要后来set进去
	@Expose
	private String logoUrl;
	@Expose
	private String lastUserName;
	
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public int getUserCount() {
		return userCount;
	}
	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}
	public int getCreaterId() {
		return createrId;
	}
	public void setCreaterId(int createrId) {
		this.createrId = createrId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getLastUserId() {
		return lastUserId;
	}
	public void setLastUserId(int lastUserId) {
		this.lastUserId = lastUserId;
	}
	public String getLastText() {
		return lastText;
	}
	public void setLastText(String lastText) {
		this.lastText = lastText;
	}
	public Date getLastTime() {
		return lastTime;
	}
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	public String getLogoUrl() {
		return logoUrl;
	}
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}
	public String getLastUserName() {
		return lastUserName;
	}
	public void setLastUserName(String lastUserName) {
		this.lastUserName = lastUserName;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
}
