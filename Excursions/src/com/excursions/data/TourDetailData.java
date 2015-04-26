package com.excursions.data;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import android.content.Context;

import com.excursions.utils.ReadTextFile;

public class TourDetailData {
	public static List<Map<String, Object>> getData(Context context) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		InputStream inputStream;

		try {
			inputStream = context.getAssets().open("tou_detail.txt");
			String json = ReadTextFile.readTextFile(inputStream);
			JSONArray array = new JSONArray(json);
			int n = array.length();
			for (int i = 0; i < n; i++) {
				map = new HashMap<String, Object>();
				map.put("name", array.getJSONObject(i).getString("name"));
				map.put("layer", array.getJSONObject(i).getString("layer"));
				map.put("time", array.getJSONObject(i).getString("time"));
				map.put("comment", array.getJSONObject(i).getString("comment"));
				map.put("name0", array.getJSONObject(i).getString("name0"));
				map.put("time0", array.getJSONObject(i).getString("time0"));
				map.put("comment0", array.getJSONObject(i)
						.getString("comment0"));
				map.put("name1", array.getJSONObject(i).getString("name1"));
				map.put("time1", array.getJSONObject(i).getString("time1"));
				map.put("comment1", array.getJSONObject(i)
						.getString("comment1"));
				map.put("name2", array.getJSONObject(i).getString("name2"));
				map.put("time2", array.getJSONObject(i).getString("time2"));
				map.put("comment2", array.getJSONObject(i)
						.getString("comment2"));
				map.put("name3", array.getJSONObject(i).getString("name3"));
				map.put("time3", array.getJSONObject(i).getString("time3"));
				map.put("comment3", array.getJSONObject(i)
						.getString("comment3"));
				map.put("name4", array.getJSONObject(i).getString("name4"));
				map.put("time4", array.getJSONObject(i).getString("time4"));
				map.put("comment4", array.getJSONObject(i)
						.getString("comment4"));
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
