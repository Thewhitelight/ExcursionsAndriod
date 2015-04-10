package com.excursions.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetWorkContent {

	/**
	 * ÅÐ¶ÏÍøÂç
	 * 
	 * @return boolean ÊÇ·ñÒÑÁªÍø
	 */
	public static boolean isNetworkConnected(Context context) {

		// TODO Auto-generated method stub
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Activity.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		if (info != null && info.isConnected()) {
			return true;
		} else {
			return false;
		}
	}
}