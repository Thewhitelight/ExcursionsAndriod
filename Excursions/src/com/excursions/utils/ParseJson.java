package com.excursions.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.excursions.bean.Attractions;
import com.excursions.bean.TourMain;

public class ParseJson {
	public static List<Attractions> getAttractionsJson(String value) {
		// TODO Auto-generated method stub
		List<Attractions> list = null;
		try {
			JSONArray jsonArray = new JSONArray(value);
			list = new ArrayList<Attractions>();
			int length = jsonArray.length();
			for (int i = 0; i < length; i++) {
				Attractions attractions = new Attractions();
				attractions.setId(jsonArray.getJSONObject(i).getString("Id"));
				attractions.setName(jsonArray.getJSONObject(i)
						.getString("Name"));
				attractions.setDescribe(jsonArray.getJSONObject(i).getString(
						"Describe"));
				attractions.setSsImage(jsonArray.getJSONObject(i).getString(
						"SsImage"));
				attractions.setAddress(jsonArray.getJSONObject(i).getString(
						"Address"));
				attractions.setLongitude(jsonArray.getJSONObject(i).getDouble(
						"Longitude"));
				attractions.setLatitude(jsonArray.getJSONObject(i).getDouble(
						"Latitude"));
				list.add(attractions);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}

	public static Attractions getAttractionsDetailJson(String value) {
		// TODO Auto-generated method stub
		Attractions attractions = null;
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(value);
			attractions = new Attractions();
			attractions.setId(jsonObject.getString("Id"));
			attractions.setName(jsonObject.getString("Name"));
			attractions.setDescribe(jsonObject.getString("Describe"));
			attractions.setSsImage(jsonObject.getString("SsImage"));
			attractions.setAddress(jsonObject.getString("Address"));
			attractions.setLongitude(jsonObject.getDouble("Longitude"));
			attractions.setLatitude(jsonObject.getDouble("Latitude"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return attractions;
	}

	public static List<TourMain> getTourJson(String value) {
		// TODO Auto-generated method stub
		List<TourMain> list = null;
		try {
			JSONArray jsonArray = new JSONArray(value);
			list = new ArrayList<TourMain>();
			int length = jsonArray.length();
			for (int i = 0; i < length; i++) {
				TourMain tourMain = new TourMain();
				tourMain.setId(jsonArray.getJSONObject(i).getString("Id"));
				tourMain.setUserId(jsonArray.getJSONObject(i).getString(
						"UserId"));
				tourMain.setTitle(jsonArray.getJSONObject(i).getString("Title"));
				tourMain.setContent(jsonArray.getJSONObject(i).getString(
						"Content"));
				tourMain.setCreateTime(jsonArray.getJSONObject(i).getString(
						"CreateTime"));
				tourMain.setPraiseNum(jsonArray.getJSONObject(i).getString(
						"PraiseNum"));
				list.add(tourMain);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}

}
