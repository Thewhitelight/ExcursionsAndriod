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
				android.R.layout.simple_spinner_item, new String[] { "A����",
						"B����" }));
		sp_q2 = (Spinner) findViewById(R.id.sp_q2);
		sp_q2.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, new String[] {
						"A������ֱ�ӡ����۳嶯 ", "B�������Ͻ�����������", "C���Ծ��Ͻ�����Į����",
						" D��������ԥ���׽��Ѻ�" }));
		sp_q3 = (Spinner) findViewById(R.id.sp_q3);
		sp_q3.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, new String[] { "A����ʷ���� ",
						"B������С˵ ", "C����������С˵ ", "D����ͨ����" }));
		sp_q4 = (Spinner) findViewById(R.id.sp_q4);
		sp_q4.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, new String[] {
						"A�������ճ��ŵ���", " B���Ϸ���ů�Ļ� ", "C�������ã��Į��", " D�����������ʱ��" }));
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
						BmobLog.i("�޸ĳɹ�");

					}

					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub
						BmobLog.i("�޸�ʧ��");
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
