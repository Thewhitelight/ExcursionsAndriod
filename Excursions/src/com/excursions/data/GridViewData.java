package com.excursions.data;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.excursions.application.BmobConstants;
import com.excursions.bean.Attractions;
import com.excursions.utils.ParseJson;

public class GridViewData {
	private static List<Attractions> mList = new ArrayList<Attractions>();
	private static Attractions attraction;

	public static List<Attractions> getDataAll(Context context)
			throws Exception {
		// TODO Auto-generated method stub

		RequestQueue rq = Volley.newRequestQueue(context);
		Listener<String> listener;
		listener = new Listener<String>() {
			@Override
			public void onResponse(String arg0) {
				// TODO Auto-generated method stub
				mList = ParseJson.getAttractionsJson(arg0);
				Log.i("onResponse", mList.toString());
			}
		};
		StringRequest sr = new StringRequest(BmobConstants.INDEXURL, listener,
				new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError arg0) {
						// TODO Auto-generated method stub
						arg0.printStackTrace();
					}
				});
		rq.add(sr);
		return mList;
	}

	public static Attractions getData(Context context, final int index)
			throws Exception {
		// TODO Auto-generated method stub
		attraction = new Attractions();
		RequestQueue rq = Volley.newRequestQueue(context);
		Listener<String> listener;
		listener = new Listener<String>() {
			@Override
			public void onResponse(String arg0) {
				// TODO Auto-generated method stub
				JSONObject jsonObject;
				try {
					jsonObject = new JSONObject(arg0);
					attraction.setId(jsonObject.getString("Id"));
					attraction.setName(jsonObject.getString("Name"));
					attraction.setDescribe(jsonObject.getString("Describe"));
					attraction.setSsImage(jsonObject.getString("SsImage"));
					attraction.setAddress(jsonObject.getString("Address"));
					Log.i("onResponse", BmobConstants.ATTDETAILURL + index
							+ " " + attraction.toString());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		};
		StringRequest sr = new StringRequest(
				BmobConstants.ATTDETAILURL + index, listener,
				new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError arg0) {
						// TODO Auto-generated method stub
						arg0.printStackTrace();
					}
				});
		rq.add(sr);
		Log.i("getData", attraction.getAddress() + " ");
		return attraction;
	}
}
