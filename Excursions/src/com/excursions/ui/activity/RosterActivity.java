package com.excursions.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import cn.bmob.im.BmobUserManager;
import cn.bmob.im.util.BmobLog;
import cn.bmob.v3.listener.UpdateListener;

import com.example.excursions.R;
import com.excursions.bean.User;

public class RosterActivity extends ActivityBase {
	private Spinner sp_q1, sp_q2, sp_q3, sp_q4;
	private Button btn_start;
	private User user = new User();
	private BmobUserManager userManager = BmobUserManager.getInstance(this);
	private SharedPreferences preferences;
	private int theme1, theme2, theme3, theme4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_roster);
		init();
		theme1 = R.style.theme1;
		theme2 = R.style.theme2;
		theme3 = R.style.theme3;
		theme4 = R.style.theme4;
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
				// saveRoster();
				user.setRoster("master");
				writeSharePerfreences(theme1);
				updateUserData(user, new UpdateListener() {

					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						BmobLog.i("修改成功");

					}

					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub
						BmobLog.i("修改失败");
					}
				});
				startActivity(new Intent(RosterActivity.this,
						MainActivity.class));
				RosterActivity.this.finish();
			}
		});
	}

	private void updateUserData(User user, UpdateListener listener) {
		User current = (User) userManager.getCurrentUser(User.class);
		user.setObjectId(current.getObjectId());
		user.update(this, listener);
	}

	private void writeSharePerfreences(int theme) {
		// TODO Auto-generated method stub
		preferences = getSharedPreferences("theme", MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt("theme", theme);
		editor.commit();
	}
}
