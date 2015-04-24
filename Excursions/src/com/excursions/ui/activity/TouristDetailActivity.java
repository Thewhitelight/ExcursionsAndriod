package com.excursions.ui.activity;

import com.example.excursions.R;
import com.excursions.ui.customview.EmoticonsEditText;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TouristDetailActivity extends ActivityBase implements
		OnClickListener {
	private Button btn_chat_add, btn_chat_emo, btn_chat_send;
	private EmoticonsEditText edit_user_comment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_touristdetail);
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		btn_chat_add = (Button) findViewById(R.id.btn_chat_add);
		btn_chat_emo = (Button) findViewById(R.id.btn_chat_emo);
		btn_chat_send = (Button) findViewById(R.id.btn_chat_send);
		btn_chat_add.setOnClickListener(this);
		btn_chat_emo.setOnClickListener(this);
		btn_chat_send.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
}
