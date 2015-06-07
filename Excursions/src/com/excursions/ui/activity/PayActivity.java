package com.excursions.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.excursions.R;

public class PayActivity extends ActivityBase {
	private Button buy_btn_buy;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay);
		initView();
		actionBar.setTitle("÷ß∏∂ΩÁ√Ê");
	}

	private void initView() {
		// TODO Auto-generated method stub
		buy_btn_buy = (Button) findViewById(R.id.buy_btn);
		buy_btn_buy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
}
