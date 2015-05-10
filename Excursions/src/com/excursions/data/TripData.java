package com.excursions.data;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import android.content.Context;

import com.excursions.utils.ReadTextFile;

public class TripData {

	public static List<Map<String, Object>> getData(Context context) {
		// TODO Auto-generated method stub

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		InputStream inputStream;

		try {
			inputStream = context.getAssets().open("trip.txt");
			String json = ReadTextFile.readTextFile(inputStream);
			JSONArray array = new JSONArray(json);
			int n = array.length();
			for (int i = 0; i < n; i++) {
				map = new HashMap<String, Object>();
				map.put("title", array.getJSONObject(i).getString("title"));
				map.put("content", array.getJSONObject(i).getString("content"));
				map.put("man", array.getJSONObject(i).getString("man"));
				map.put("time", array.getJSONObject(i).getString("time"));
				map.put("number", array.getJSONObject(i).getString("number"));
				list.add(map);
			}
			return list;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return list;

	}

}
