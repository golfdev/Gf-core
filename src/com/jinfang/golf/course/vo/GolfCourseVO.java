package com.jinfang.golf.course.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.jinfang.golf.course.model.GolfCoursePlayer;

public class GolfCourseVO implements Serializable{
	
	private Integer id;
	
	private Integer clubId;
	
	private String playerSetting;
	
	private List<GolfCoursePlayer> playerList;
	
	private Integer isLive;
	
	private Date createdTime;

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

    	
	
}
