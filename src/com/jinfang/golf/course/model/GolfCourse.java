package com.jinfang.golf.course.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.google.gson.annotations.Expose;

public class GolfCourse implements Serializable{
	
	@Expose
	private Integer id;
	
	@Expose
	private Integer clubId;
	
	@Expose
	private String clubName;
	
	@Expose
	private String clubLogo;
	
	private String playerSetting;
	
	@Expose
	private List<GolfCoursePlayer> playerList;
	
	@Expose
	private Integer isLive;
	
	@Expose
	private Integer creatorId;
	
	@Expose
	private Date createdTime;
	
	@Expose
	private Integer viewCount = 0;

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



	public Integer getIsLive() {
		return isLive;
	}

	public void setIsLive(Integer isLive) {
		this.isLive = isLive;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getPlayerSetting() {
		return playerSetting;
	}

	public void setPlayerSetting(String playerSetting) {
		this.playerSetting = playerSetting;
	}

	public List<GolfCoursePlayer> getPlayerList() {
		return playerList;
	}

	public void setPlayerList(List<GolfCoursePlayer> playerList) {
		this.playerList = playerList;
	}

	public Integer getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	public String getClubName() {
		return clubName;
	}

	public void setClubName(String clubName) {
		this.clubName = clubName;
	}

	public String getClubLogo() {
		return clubLogo;
	}

	public void setClubLogo(String clubLogo) {
		this.clubLogo = clubLogo;
	}

    	
	
}
