package com.excursions.ui.activity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.excursions.R;

public class BillDetailActivity extends BaseActivity {
	private TextView tv_bill_title;
	private TextView tv_bill_budget;
	private TextView tv_bill_time;
	private TextView tv_typefund;
	private TextView tv_fund;
	private Spinner sp_type;
	private EditText edt_bill_title;
	private EditText edt_bill_budget;
	private EditText edt_bill_time;
	private EditText edt_typefund;
	private EditText edt_fund;
	private Spinner sp_type_select;
	private SQLiteDatabase DB;
	private String title;
	private String budget;
	private String time;
	private String fund;
	private String typefund;
	private ArrayAdapter<String> adapter;
	private String[] mItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activty_bill_detail);
		init();

	}

	private void init() {
		// TODO Auto-generated method stub
		tv_bill_title = (TextView) findViewById(R.id.tv_bill_title);
		tv_bill_budget = (TextView) findViewById(R.id.tv_bill_budget);
		tv_bill_time = (TextView) findViewById(R.id.tv_bill_time);
		tv_typefund = (TextView) findViewById(R.id.tv_typefund);
		tv_fund = (TextView) findViewById(R.id.tv_bill_fund);
		sp_type = (Spinner) findViewById(R.id.sp_type);
		edt_bill_title = (EditText) findViewById(R.id.edt_bill_title);
		edt_bill_budget = (EditText) findViewById(R.id.edt_bill_budget);
		edt_bill_time = (EditText) findViewById(R.id.edt_bill_time);
		edt_typefund = (EditText) findViewById(R.id.edt_typefund);
		edt_fund = (EditText) findViewById(R.id.edt_bill_fund);
		sp_type_select = (Spinner) findViewById(R.id.sp_type_select);
		mItems = getResources().getStringArray(R.array.spinner);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, mItems);
		sp_type_select.setAdapter(adapter);
		sp_type.setAdapter(adapter);
	}

	private void getContent() {
		// TODO Auto-generated method stub
		title = edt_bill_title.getText().toString();
		budget = edt_bill_budget.getText().toString();
		time = edt_bill_time.getText().toString();
		typefund = edt_typefund.getText().toString();
		fund = edt_fund.getText().toString();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.ok_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.ok) {
			// 写入数据库
			getContent();
			if (TextUtils.isEmpty(title)) {

				Toast.makeText(getApplicationContext(), "景点不能为空",
						Toast.LENGTH_SHORT).show();
				return false;
			} else {
				createDB();
				insertData();
				Toast.makeText(getApplicationContext(), "插入成功",
						Toast.LENGTH_SHORT).show();
				return true;
			}

		}
		return super.onOptionsItemSelected(item);
	}

	private void createDB() {
		// TODO Auto-generated method stub
		DB = openOrCreateDatabase(title, MODE_PRIVATE, null);
		DB.execSQL("create table if not exists "
				+ title
				+ "(id integer primary key autoincrement,title varchar ,budget integer,time integer,type varchar,typefund integer,fund integer)");
	}

	private void insertData() {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put("title", title);
		cv.put("budget", budget);
		cv.put("time", time);
		cv.put("type", sp_type_select.getSelectedItem().toString());
		cv.put("typefund", typefund);
		cv.put("fund", fund);
		DB.insert(title, null, cv);
		clear();
	}

	private void clear() {
		// TODO Auto-generated method stub
		edt_bill_title.setText("");
		edt_bill_budget.setText("");
		edt_bill_time.setText("");
		edt_typefund.setText("");
		edt_fund.setText("");
	}
}
