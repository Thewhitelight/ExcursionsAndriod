package com.excursions.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import cn.bmob.v3.listener.UpdateListener;

import com.example.excursions.R;
import com.excursions.bean.User;

public class UpdateInfoActivity extends ActivityBase {

	EditText edit_nick;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_updateinfo);
		initView();
	}

	private void initView() {
		ActionBar ab = getSupportActionBar();
		ab.setTitle("修改昵称");
		edit_nick = (EditText) findViewById(R.id.edit_nick);
	}

	/**
	 * 修改资料 updateInfo
	 * 
	 * @Title: updateInfo
	 * @return void
	 * @throws
	 */
	private void updateInfo(String nick) {
		final User user = userManager.getCurrentUser(User.class);
		User u = new User();
		u.setNick(nick);
		u.setHight(110);
		u.setObjectId(user.getObjectId());
		u.update(this, new UpdateListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				// final User c = userManager.getCurrentUser(User.class);
				// ShowToast("修改成功:" + c.getNick() + ",height = " +
				// c.getHight());
				finish();
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				// ShowToast("onFailure:" + arg1);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.bill_ok_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		if (R.id.ok == item.getItemId()) {

			String nick = edit_nick.getText().toString();
			if (nick.equals("")) {
				ShowToast("请填写昵称!");
			}
			updateInfo(nick);
		}
		return super.onOptionsItemSelected(item);
	}
}
