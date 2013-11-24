package com.jinfang.golf.weather.model;

import java.io.Serializable;

public class Weather implements Serializable {
	
	private String city;
	
	private String temp;
	
	private String weather;
	
	private String wind;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}


	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public String getWind() {
		return wind;
	}

	public void setWind(String wind) {
		this.wind = wind;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}
	
	

}
