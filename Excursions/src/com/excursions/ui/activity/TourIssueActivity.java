package com.excursions.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.excursions.R;
import com.excursions.ui.customview.EmoticonsEditText;

public class TourIssueActivity extends ActivityBase {
	private EmoticonsEditText emo;
	private TextView tv_emo, tv_add;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tourissue);
		init();
	}

	private void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.bill_ok_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
		if (id == R.id.ok) {
			this.finish();
		}
		return super.onOptionsItemSelected(item);
	}
}
