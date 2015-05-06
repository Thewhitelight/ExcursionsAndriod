package com.excursions.ui.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.excursions.R;
import com.excursions.adapter.NotifyAdapter;
import com.excursions.utils.DBNotifyAdapter;

public class NotifyActivity extends ActivityBase {
	private ListView lv_notify;
	private NotifyAdapter adapter;
	private List<Map<String, Object>> list;
	private Map<String, Object> map;
	private DBNotifyAdapter db;
	private String title, content, time;
	private static final String KEY = "key";
	private Intent i = new Intent();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		list = new ArrayList<Map<String, Object>>();
		try {
			list = getData(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		adapter = new NotifyAdapter(this, list);
		setContentView(R.layout.activity_notify_main);
		lv_notify = (ListView) findViewById(R.id.lv_notify);
		lv_notify.setAdapter(adapter);
		lv_notify.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				i.setClass(getApplicationContext(), NotifyDetailActivity.class);
				i.putExtra(KEY, (String) list.get(position).get("time"));
				startActivity(i);
			}
		});
	}

	private List<Map<String, Object>> getData(Context context) throws Exception {
		// TODO Auto-generated method stub
		list = new ArrayList<Map<String, Object>>();
		db = new DBNotifyAdapter(this);
		db.open();
		Cursor cursor = db.selectAllNotify();
		if (cursor.moveToFirst()) {
			do {
				map = new HashMap<String, Object>();
				title = cursor.getString(cursor.getColumnIndex("title"));
				content = cursor.getString(cursor.getColumnIndex("content"));
				time = cursor.getString(cursor.getColumnIndex("time"));
				map.put("title", title);
				map.put("content", content.substring(0, 10));
				map.put("time", time);
				list.add(map);

			} while (cursor.moveToNext());

		}

		db.close();
		return list;
	}
}
