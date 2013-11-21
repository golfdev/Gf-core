package com.jinfang.golf.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NotesEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7005683298502515320L;
	private int albumID = 0;
	private String date = "";
	private List<String> notes = new ArrayList<String>();
	private List<String> photos = new ArrayList<String>();
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public List<String> getNotes() {
		return notes;
	}
	public void setNotes(List<String> notes) {
		this.notes = notes;
	}
	public List<String> getPhotos() {
		return photos;
	}
	public void setPhotos(List<String> photos) {
		this.photos = photos;
	}
	public int getAlbumID() {
		return albumID;
	}
	public void setAlbumID(int albumID) {
		this.albumID = albumID;
	}
	

}
