package com.excursions.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.excursions.bean.Attractions;

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
				list.add(attractions);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}

	public static List<Attractions> getAttractionsDetailJson(String value) {
		// TODO Auto-generated method stub
		List<Attractions> list = null;

		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(value);
			list = new ArrayList<Attractions>();

			Attractions attractions = new Attractions();
			attractions.setId(jsonObject.getString("Id"));
			attractions.setName(jsonObject.getString("Name"));
			attractions.setDescribe(jsonObject.getString("Describe"));
			attractions.setSsImage(jsonObject.getString("SsImage"));
			attractions.setAddress(jsonObject.getString("Address"));
			list.add(attractions);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}
}
