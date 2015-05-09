package com.excursions.ui.activity;

import com.example.excursions.R;

import android.os.Bundle;

public class AboutActivity extends ActivityBase {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		actionBar.setTitle("关于我们");
		setContentView(R.layout.activity_about);
	}
}
