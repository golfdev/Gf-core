package com.jinfang.golf.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.commons.lang.StringUtils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class LatitudeUtils {

	public static final String KEY_1 = "327db7009617d6806b9c38e819ea06ac";
	public static final String KEY_GOLF = "gh7adH1NwMCTuhUygNmwGVSH";

	
	public static final String[] keys = new String[] {
			"034d5a77f6343f68accda4e451a74bb9",
			"327db7009617d6806b9c38e819ea06ac",
			"0c38d8dbb7f4dedf089cd62102afcb3c",
			"ca75482a0d0141607ce019145389ddfd",
			"bf56d712c7cf68fd08e509528180c918" };
	private static final double PI = 3.14159265;
	private static final double EARTH_RADIUS = 6378137;
	private static final double RAD = Math.PI / 180.0;

	public static Location getLocation(String address, String city) {
		Location location = null;
		// location = getLocationByBaiduGeocoder(address);
		if (address == null || city == null) {
			return location;
		}
		if (location == null) {
			location = getLocationByBaiduPlace(address, city, 0);
		}

		if (location == null) {
			address = city + "市" + address;
			location = getLocationByBaiduGeocoder(address);
		}
		return location;
	}

	/**
	 * 返回输入地址的经纬度坐标 key lng(经度),lat(纬度),以此为标准
	 */
	private static Location getLocationByBaiduGeocoder(String address) {
		try {
			// 将地址转换成utf-8的16进制
			address = URLEncoder.encode(address, "UTF-8");
			String url = "http://api.map.baidu.com/geocoder?address=" + address
					+ "&output=json&key=" + KEY_1;
			Location location = getLocationByServiceUrl(url);
			return location;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/***
	 * 返回输入地址的经纬度坐标 key lng(经度),lat(纬度)，此为替补方案
	 * 
	 * @param address
	 * @param city
	 * @param index
	 * @return
	 */
	private static Location getLocationByBaiduPlace(String address,
			String city, int index) {
		try {
			if (index >= keys.length) {
				return null;
			}
			String tmpAddress = URLEncoder.encode(address.trim(), "UTF-8");
			String tmpCity = URLEncoder.encode(city.trim(), "UTF-8");
			String url = "http://api.map.baidu.com/place/search?&query="
					+ tmpAddress + "&region=" + tmpCity + "&output=json&key="
					+ keys[index];

			Location location = getLocationByServiceUrl(url);
			if (location == null && index < keys.length) {
				index++;
				return getLocationByBaiduPlace(address, city, index);
			}
			return location;
		} catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
		}
		return null;
	}

	/***
	 * 根据服务连接获取Location对象
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	private static Location getLocationByServiceUrl(String url)
			throws Exception {
		URL jsonUrl = new URL(url);
		URLConnection urlConnection = jsonUrl.openConnection();
		urlConnection.setConnectTimeout(20000);
		urlConnection.setReadTimeout(20000);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				urlConnection.getInputStream()));
		String res;
		StringBuilder sb = new StringBuilder("");
		while ((res = in.readLine()) != null) {
			sb.append(res.trim());
		}
		in.close();
		String str = sb.toString();
		String status = MatcherUtil.matcher("\"status\":\"[\\w]+\"", str)
				.replaceAll("\"status\":|\"", "");
		if ("OVER_MAX_LIMIT".equals(status)) {
			return null;
		}
		String lng = MatcherUtil.matcher("\"lng\":[\\d|.]+", str).replace(
				"\"lng\":", "");
		String lat = MatcherUtil.matcher("\"lat\":[\\d|.]+", str).replace(
				"\"lat\":", "");
		Location location = null;
		if (StringUtils.isNotEmpty(lat)) {
			location = new Location();
			location.setLat(Float.parseFloat(lat));
		}
		if (StringUtils.isNotEmpty(lng)) {
			location.setLng(Float.parseFloat(lng));
		}
		return location;
	}

	/**
	 * @param raidus
	 *            单位米 return minLat,minLng,maxLat,maxLng
	 */
	public static float[] getAround(float lat, float lon, int raidus) {

		Float latitude = lat;
		Float longitude = lon;

		Float degree = (float) ((24901 * 1609) / 360.0);
		double raidusMile = raidus;

		Float dpmLat = 1 / degree;
		Float radiusLat = (float) (dpmLat * raidusMile);
		Float minLat = latitude - radiusLat;
		Float maxLat = latitude + radiusLat;

		Float mpdLng = (float) (degree * Math.cos(latitude * (PI / 180)));
		Float dpmLng = 1 / mpdLng;
		Float radiusLng = (float) (dpmLng * raidusMile);
		Float minLng = longitude - radiusLng;
		Float maxLng = longitude + radiusLng;
		return new float[] { minLat, minLng, maxLat, maxLng };
	}

	/**
	 * 根据两点间经纬度坐标（double值），计算两点间距离，单位为米
	 * 
	 * @param lng1
	 * @param lat1
	 * @param lng2
	 * @param lat2
	 * @return
	 */
	public static double getDistance(double lng1, double lat1, double lng2,
			double lat2) {
		double radLat1 = lat1 * RAD;
		double radLat2 = lat2 * RAD;
		double a = radLat1 - radLat2;
		double b = (lng1 - lng2) * RAD;
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}

	/**
	 * 根据经纬度获取城市
	 * @param location
	 * @return
	 * @throws IOException
	 */
	public static String getCityNameByLocation(String location)
			throws IOException {
		String url = "http://api.map.baidu.com/geocoder/v2/?ak="+KEY_GOLF+"&location="+location+"&output=json&pois=0";
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
		JsonElement ele = jo.get("result").getAsJsonObject()
				.get("addressComponent");
		String city = ele.getAsJsonObject().get("city").getAsString();
		return city;

	}

	public static void main(String[] args) throws IOException {
		System.out.println(getCityNameByLocation("39.983424,112.322987"));

	}

}
