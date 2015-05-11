package com.excursions.ui.activity;

import com.example.excursions.R;
import com.excursions.utils.DBNotifyAdapter;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class NotifyDetailActivity extends ActivityBase {
	private TextView tv_title, tv_time, tv_content;
	private String key;
	private DBNotifyAdapter db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		actionBar.setTitle("Í¨ÖªÏêÇé");
		setContentView(R.layout.activity_notify_detail);
		db = new DBNotifyAdapter(this);
		key = getIntent().getExtras().getString("key");
		tv_title = (TextView) findViewById(R.id.notify_title);
		tv_time = (TextView) findViewById(R.id.notify_time);
		tv_content = (TextView) findViewById(R.id.notify_content);
		selectData();
	}

	private void selectData() {
		// TODO Auto-generated method stub
		try {
			db.open();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Cursor cursor = null;
		try {
			cursor = db.selectNotify(key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (cursor.moveToFirst()) {
			DisplayData(cursor);
		}
		db.close();
	}

	private void DisplayData(Cursor cursor) {
		// TODO Auto-generated method stub
		tv_title.setText(cursor.getString(0));
		tv_time.setText(cursor.getString(2));
		tv_content.setText(cursor.getString(1));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == android.R.id.home) {
			startActivity(new Intent(this, MainActivity.class));
		}
		return super.onOptionsItemSelected(item);
	}
}
