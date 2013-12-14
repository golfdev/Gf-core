package com.jinfang.golf.xmpp.model;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

public class Photo implements Serializable {
	private static final long serialVersionUID = -5827601050563420202L;
	@Expose
	private int photoId;
	@Expose
	private String photoUrl;
	@Expose
	private int width;
	@Expose
	private int height;
	@Expose
	private int size;
	public int getPhotoId() {
		return photoId;
	}
	public void setPhotoId(int photoId) {
		this.photoId = photoId;
	}
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
}
