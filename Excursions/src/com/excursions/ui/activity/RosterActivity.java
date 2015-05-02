package com.excursions.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.excursions.R;

public class RosterActivity extends Activity {
	private Spinner sp_q1, sp_q2, sp_q3, sp_q4;
	private Button btn_start;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_roster);
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		sp_q1 = (Spinner) findViewById(R.id.sp_q1);
		sp_q1.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, new String[] { "A：是",
						"B：否" }));
		sp_q2 = (Spinner) findViewById(R.id.sp_q2);
		sp_q2.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, new String[] {
						"A：开放直接、主观冲动 ", "B：谨慎严谨、严肃挑剔", "C：自觉严谨、冷漠果断",
						" D：开放犹豫、亲近友好" }));
		sp_q3 = (Spinner) findViewById(R.id.sp_q3);
		sp_q3.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, new String[] { "A：历史人物 ",
						"B：武侠小说 ", "C：网络流行小说 ", "D：卡通动漫" }));
		sp_q4 = (Spinner) findViewById(R.id.sp_q4);
		sp_q4.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, new String[] {
						"A：东海日出古到今", " B：南方地暖夏或春 ", "C：西域苍茫大漠在", " D：北国风光四时分" }));
		btn_start = (Button) findViewById(R.id.start_exc);
		btn_start.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),
						MainActivity.class));

			}
		});
	}
}
