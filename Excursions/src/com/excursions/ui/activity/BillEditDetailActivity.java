package com.excursions.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.excursions.R;
import com.excursions.utils.DBAdapter;

public class BillEditDetailActivity extends ActivityBase {
	private EditText edt_bill_title;
	private EditText edt_bill_budget;
	private EditText edt_bill_time;
	private EditText edt_typefund;
	private EditText edt_fund;
	private Spinner sp_type_select;
	private String title;
	private String budget;
	private String time;
	private String fund;
	private String typefund;
	private ArrayAdapter<String> adapter;
	private String[] mItems;
	private DBAdapter db = new DBAdapter(this);
	long id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activty_bill_edit_detail);
		init();

	}

	private void init() {
		// TODO Auto-generated method stub

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

		getMenuInflater().inflate(R.menu.bill_ok_menu, menu);
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
				// createDB();
				insertData();
				if (id > 0) {
					Toast.makeText(getApplicationContext(), "插入成功",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getApplicationContext(), "插入失败",
							Toast.LENGTH_SHORT).show();
				}

				return true;
			}

		}
		return super.onOptionsItemSelected(item);
	}

	private void insertData() {
		// TODO Auto-generated method stub
		db.open();
		id = db.insertData(title, budget, time, sp_type_select
				.getSelectedItem().toString(), typefund, fund);
		db.close();

		startActivity(new Intent(getApplicationContext(), BillActivity.class));
		this.finish();
	}

	// private void clear() {
	// // TODO Auto-generated method stub
	// edt_bill_title.setText("");
	// edt_bill_budget.setText("");
	// edt_bill_time.setText("");
	// edt_typefund.setText("");
	// edt_fund.setText("");
	// }
}
