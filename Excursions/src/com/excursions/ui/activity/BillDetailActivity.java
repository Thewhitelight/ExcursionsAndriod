package com.excursions.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.excursions.R;
import com.excursions.utils.DBAdapter;

public class BillDetailActivity extends BaseActivity {
	private TextView tv_bill_title;
	private TextView tv_bill_budget;
	private TextView tv_bill_time;
	private TextView tv_typefund;
	private TextView tv_fund;
	private Spinner sp_type;
	private DBAdapter db = new DBAdapter(this);
	private String[] item;
	private String att;
	private Button btn_select;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bill_detail);
		att = getIntent().getStringExtra("key");
		init();
		selectData();
		btn_select.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String str = sp_type.getSelectedItem().toString();
			}
		});
	}

	private void init() {
		// TODO Auto-generated method stub
		tv_bill_title = (TextView) findViewById(R.id.tv_bill_title);
		tv_bill_budget = (TextView) findViewById(R.id.tv_bill_budget);
		tv_bill_time = (TextView) findViewById(R.id.tv_bill_time);
		tv_typefund = (TextView) findViewById(R.id.tv_typefund);
		tv_fund = (TextView) findViewById(R.id.tv_bill_fund);
		sp_type = (Spinner) findViewById(R.id.sp_type);
		item = getResources().getStringArray(R.array.spinner);
		sp_type.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, item));
		btn_select = (Button) findViewById(R.id.btn_select);
	}

	private void selectData() {
		// TODO Auto-generated method stub
		db.open();
		Cursor cursor = db.getData(att);
		if (cursor.moveToFirst()) {
			DisplayData(cursor);
		}
		db.close();
	}

	private void DisplayData(Cursor cursor) {
		// TODO Auto-generated method stub
		tv_bill_title.setText(cursor.getString(0));
		tv_bill_budget.setText(cursor.getString(1));
		tv_bill_time.setText(cursor.getString(2));
		item[0] = cursor.getString(3);
		tv_typefund.setText(cursor.getString(4));
		tv_fund.setText(cursor.getString(5));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.bill_delete_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
		if (id == R.id.menu_delete) {
			db.open();
			db.deleteData(att);
			db.close();
			startActivity(new Intent(getApplicationContext(),
					BillActivity.class));
			this.finish();
		}
		return super.onOptionsItemSelected(item);
	}
}
