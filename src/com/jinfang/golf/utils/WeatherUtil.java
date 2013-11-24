package com.jinfang.golf.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jinfang.golf.weather.model.Weather;

public class WeatherUtil {

	private static Log logger = LogFactory.getLog(WeatherUtil.class);

	public static final String KEY_GOLF = "gh7adH1NwMCTuhUygNmwGVSH";

	private static Weather getWeatherByCity(String location) throws IOException {

		String url = "http://api.map.baidu.com/telematics/v3/weather?location="
				+ location + "&output=json&ak=" + KEY_GOLF;
		URL jsonUrl = new URL(url);
		URLConnection urlConnection = jsonUrl.openConnection();
		urlConnection.setConnectTimeout(20000);
		urlConnection.setReadTimeout(20000);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				urlConnection.getInputStream()));
		String res = "";
		StringBuilder sb = new StringBuilder("");
		while ((res = in.readLine()) != null) {
			sb.append(res.trim());
		}
		in.close();
		JsonParser jp = new JsonParser();
		JsonObject jo = (JsonObject) jp.parse(sb.toString());
		String status = jo.get("status").getAsString();
		if ("success".equals(status)) {
			String city = jo.get("results").getAsJsonArray().get(0)
					.getAsJsonObject().get("currentCity").getAsString();
			String weather = jo.get("results").getAsJsonArray().get(0)
					.getAsJsonObject().get("weather_data").getAsJsonArray()
					.get(0).getAsJsonObject().get("weather").getAsString();
			String wind = jo.get("results").getAsJsonArray().get(0)
					.getAsJsonObject().get("weather_data").getAsJsonArray()
					.get(0).getAsJsonObject().get("wind").getAsString();
			String temp = jo.get("results").getAsJsonArray().get(0)
					.getAsJsonObject().get("weather_data").getAsJsonArray()
					.get(0).getAsJsonObject().get("temperature").getAsString();
			Weather we = new Weather();
			we.setCity(city);
			we.setTemp(temp);
			we.setWeather(weather);
			we.setWind(wind);
			return we;
		} else {
			return null;
		}
	}

	public static Weather getWeatherByLngLat(String location) {
		String city = null;
		try {
			city = LatitudeUtils.getCityNameByLocation(location);
		} catch (IOException e) {

		}
		
		//TODO cache处理

		Weather we = null;
		try {
			we = getWeatherByCity(city);
		} catch (IOException e) {
			logger.error("获取天气失败！", e);
			return null;
		}

		return we;

	}


}
