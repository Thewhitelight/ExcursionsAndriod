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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.example.excursions.R;
import com.excursions.adapter.BillLvAdapter;
import com.excursions.utils.DBAdapter;

public class BillActivity extends BaseActivity {
	private Button btn;
	private ListView lv;
	private List<Map<String, Object>> list;
	private Map<String, Object> map;
	private BillLvAdapter adapter;
	private String att;
	private String budget;
	private String time;
	private String type;
	private String tcost;
	private String fund;
	private DBAdapter db;
	private final static String KEY = "key";
	private Intent i = new Intent();;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bill_main);
		list = new ArrayList<Map<String, Object>>();
		map = new HashMap<String, Object>();
		list = getData(this);
		adapter = new BillLvAdapter(this, list);
		btn = (Button) findViewById(R.id.btn);
		lv = (ListView) findViewById(R.id.lv_bill);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				i.setClass(getApplicationContext(), BillDetailActivity.class);
				i.putExtra(KEY, (String) list.get(position).get("att"));
				startActivity(i);
			}
		});
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),
						BillEditDetailActivity.class));
			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		list = getData(this);
		adapter = new BillLvAdapter(this, list);
		lv.setAdapter(adapter);
	}

	public List<Map<String, Object>> getData(Context context) {
		// TODO Auto-generated method stub
		list = new ArrayList<Map<String, Object>>();
		db = new DBAdapter(this);
		db.open();
		Cursor cursor = db.getAllData();
		if (cursor.moveToFirst()) {
			do {
				map = new HashMap<String, Object>();
				att = cursor.getString(cursor.getColumnIndex("att"));
				budget = cursor.getString(cursor.getColumnIndex("budget"));
				time = cursor.getString(cursor.getColumnIndex("time"));
				type = cursor.getString(cursor.getColumnIndex("type"));
				tcost = cursor.getString(cursor.getColumnIndex("tcost"));
				fund = cursor.getString(cursor.getColumnIndex("fund"));
				map.put("att", att);
				map.put("budget", budget);
				map.put("time", time);
				map.put("type", type);
				map.put("tcost", tcost);
				map.put("fund", fund);
				list.add(map);

			} while (cursor.moveToNext());

		}

		db.close();
		return list;
	}

}
