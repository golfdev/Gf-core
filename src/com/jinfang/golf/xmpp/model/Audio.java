package com.jinfang.golf.xmpp.model;

import java.io.Serializable;

public class Audio implements Serializable {
	private static final long serialVersionUID = -9000326310700431419L;
	private int audioId;
	private String audioUrl;
	private double duration;
	private int size;
	public int getAudioId() {
		return audioId;
	}
	public void setAudioId(int audioId) {
		this.audioId = audioId;
	}
	public String getAudioUrl() {
		return audioUrl;
	}
	public void setAudioUrl(String audioUrl) {
		this.audioUrl = audioUrl;
	}
	public double getDuration() {
		return duration;
	}
	public void setDuration(double duration) {
		this.duration = duration;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
}
