package com.excursions.data;

import java.util.ArrayList;
import java.util.List;

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
	private static List<Attractions> list = new ArrayList<Attractions>();

	public static List<Attractions> getData(Context context) throws Exception {
		// TODO Auto-generated method stub

		RequestQueue rq = Volley.newRequestQueue(context);
		Listener<String> listener;
		listener = new Listener<String>() {
			@Override
			public void onResponse(String arg0) {
				// TODO Auto-generated method stub
				mList = ParseJson.getAttractionsJson(arg0);
				list = mList;
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
		Log.i("getData", mList.toString());
		return list;
	}

}
