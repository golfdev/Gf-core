package com.jinfang.golf.club.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.google.gson.annotations.Expose;

public class GolfClub implements Serializable{

	@Expose
	private Integer id;
	
	@Expose
	private Integer creatorId;
	
	@Expose
	private String contacts;
	
	@Expose
	private String phone;
	
	@Expose
	private String address;
	
	@Expose
	private String name;
	
	@Expose
	private String logo;
	
	@Expose
	private List<String> imgList;
	
	@Expose
	private Double marketPrice;
	
	@Expose
	private Double centifyPrice;
	
	@Expose
	private Double unCentifyPrice;
	
	
	@Expose
	private String city;
	
	@Expose
	private String description;
	
	@Expose
	private String aboutClub;
	
	@Expose
	private String notice;
	
	private Date createdTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getAboutClub() {
		return aboutClub;
	}

	public void setAboutClub(String aboutClub) {
		this.aboutClub = aboutClub;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<String> getImgList() {
		return imgList;
	}

	public void setImgList(List<String> imgList) {
		this.imgList = imgList;
	}

	public Double getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}

	public Double getCentifyPrice() {
		return centifyPrice;
	}

	public void setCentifyPrice(Double centifyPrice) {
		this.centifyPrice = centifyPrice;
	}

	public Double getUnCentifyPrice() {
		return unCentifyPrice;
	}

	public void setUnCentifyPrice(Double unCentifyPrice) {
		this.unCentifyPrice = unCentifyPrice;
	}

	
}
