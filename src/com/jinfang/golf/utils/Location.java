package com.jinfang.golf.utils;

import java.io.Serializable;

public class Location implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 经度
	 */
	private float lng;
	/**
	 * 纬度
	 */
	private float lat;

	public float getLng() {
		return lng;
	}

	public void setLng(float lng) {
		this.lng = lng;
	}

	public float getLat() {
		return lat;
	}

	public void setLat(float lat) {
		this.lat = lat;
	}
}