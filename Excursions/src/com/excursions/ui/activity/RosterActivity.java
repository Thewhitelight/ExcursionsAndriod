package com.excursions.ui.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import cn.bmob.im.BmobUserManager;
import cn.bmob.im.util.BmobLog;
import cn.bmob.v3.listener.UpdateListener;

import com.example.excursions.R;
import com.excursions.bean.User;

public class RosterActivity extends BaseActivity {
	private Spinner sp_q1, sp_q2, sp_q3, sp_q4;
	private Button btn_start;
	private User user = new User();
	private BmobUserManager userManager = BmobUserManager.getInstance(this);
	private SharedPreferences preferences;
	private int theme1, theme2, theme3, theme4;
	private Intent intent;
	private String theme;
	private List<String> sp1, sp2, sp3, sp4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_roster);
		init();
		intent = getIntent();
		theme = intent.getStringExtra("theme");
		theme1 = R.style.theme1;
		theme2 = R.style.theme2;
		theme3 = R.style.theme3;
		theme4 = R.style.theme4;
	}

	private void init() {
		sp1 = new ArrayList<String>();
		sp1.add("A:��");
		sp1.add("B:��");
		sp2 = new ArrayList<String>();
		sp2.add("A������ֱ�ӡ����۳嶯 ");
		sp2.add("B�������Ͻ�����������");
		sp2.add("C���Ծ��Ͻ�����Į����");
		sp2.add("D��������ԥ���׽��Ѻ�");
		sp3 = new ArrayList<String>();
		sp3.add("A����ʷ���� ");
		sp3.add("B������С˵ ");
		sp3.add("C����������С˵ ");
		sp3.add("D����ͨ����");
		sp4 = new ArrayList<String>();
		sp4.add("A�������ճ��ŵ��� ");
		sp4.add("B���Ϸ���ů�Ļ� ");
		sp4.add("C�������ã��Į��");
		sp4.add("D�����������ʱ��");
		// TODO Auto-generated method stub
		sp_q1 = (Spinner) findViewById(R.id.sp_q1);
		sp_q1.setAdapter(new MyAdapter(this, sp1));
		sp_q2 = (Spinner) findViewById(R.id.sp_q2);
		sp_q2.setAdapter(new MyAdapter(this, sp2));
		sp_q3 = (Spinner) findViewById(R.id.sp_q3);
		sp_q3.setAdapter(new MyAdapter(this, sp3));
		sp_q4 = (Spinner) findViewById(R.id.sp_q4);
		sp_q4.setAdapter(new MyAdapter(this, sp4));
		btn_start = (Button) findViewById(R.id.start_exc);
		btn_start.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// saveRoster();
				user.setRoster("master");
				if (theme.equals("theme1")) {
					writeSharePerfreences(theme1);
				} else {
					writeSharePerfreences(theme2);
				}

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

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent i = new Intent();
		i.setClass(this, MainActivity.class);
		startActivity(i);
		this.finish();
	}

	public class MyAdapter extends BaseAdapter {
		private List<String> list;
		private LayoutInflater inflater;

		public MyAdapter(Context context, List<String> list) {
			// TODO Auto-generated constructor stub
			this.list = list;
			this.inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.roster_spinner_item,
						parent, false);
			}
			TextView tv = (TextView) convertView.findViewById(R.id.tv_spinner);
			tv.setText(list.get(position).toString());
			return convertView;
		}

	}
}