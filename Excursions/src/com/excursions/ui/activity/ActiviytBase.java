package com.excursions.ui.activity;

import java.util.List;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;
import cn.bmob.im.BmobChatManager;
import cn.bmob.im.BmobUserManager;
import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.im.config.BmobConfig;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

import com.example.excursions.R;
import com.excursions.application.MyApplication;
import com.excursions.bean.User;
import com.excursions.ui.customview.DialogTips;
import com.excursions.utils.CollectionUtils;

/**
 * @ClassName: ActiviytBase
 * @Description: 除欢迎、登陆界面所继承的基类
 * @author SZQ
 * @date 2015年4月16日 上午11:10:56
 */
public class ActiviytBase extends ActionBarActivity {
	BmobUserManager userManager;
	BmobChatManager manager;
	MyApplication myApplication;
	ActionBar actionBar;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);
		actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.tab_bg));
		actionBar.setDisplayShowTitleEnabled(true);
		// 自动登陆状态下检测是否在其他设备登陆
		checkLogin();
		userManager = BmobUserManager.getInstance(this);
		manager = BmobChatManager.getInstance(this);
		myApplication = MyApplication.getInstance();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// 锁屏状态下的检测
		checkLogin();
	}

	public void checkLogin() {
		BmobUserManager userManager = BmobUserManager.getInstance(this);
		if (userManager.getCurrentUser() == null) {
			Toast.makeText(getBaseContext(), "您的账号已在其他设备上登录!",
					Toast.LENGTH_SHORT).show();
			startActivity(new Intent(this, SignInActivity.class));
			finish();
		}
	}

	Toast mToast;

	public void ShowToast(final String text) {
		if (!TextUtils.isEmpty(text)) {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (mToast == null) {
						mToast = Toast.makeText(getApplicationContext(), text,
								Toast.LENGTH_LONG);
					} else {
						mToast.setText(text);
					}
					mToast.show();
				}
			});

		}
	}

	public void ShowToast(final int resId) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (mToast == null) {
					mToast = Toast.makeText(
							ActiviytBase.this.getApplicationContext(), resId,
							Toast.LENGTH_LONG);
				} else {
					mToast.setText(resId);
				}
				mToast.show();
			}
		});
	}

	/**
	 * 打Log ShowLog
	 * 
	 * @return void
	 * @throws
	 */
	public void ShowLog(String msg) {
		Log.i("life", msg);
	}

	/**
	 * 显示下线的对话框 showOfflineDialog
	 * 
	 * @return void
	 * @throws
	 */
	public void showOfflineDialog(final Context context) {
		DialogTips dialog = new DialogTips(this, "您的账号已在其他设备上登录!", "重新登录");
		// 设置成功事件
		dialog.SetOnSuccessListener(new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialogInterface, int userId) {
				MyApplication.getInstance().logout();
				startActivity(new Intent(context, SignInActivity.class));
				finish();
				dialogInterface.dismiss();
			}
		});
		// 显示确认对话框
		dialog.show();
		dialog = null;
	}

	public void startAnimActivity(Class<?> cla) {
		this.startActivity(new Intent(this, cla));
	}

	public void startAnimActivity(Intent intent) {
		this.startActivity(intent);
	}

	/**
	 * 用于登陆或者自动登陆情况下的用户资料及好友资料的检测更新
	 * 
	 * @Title: updateUserInfos
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 */
	public void updateUserInfos() {
		// 更新地理位置信息
		updateUserLocation();
		// 查询该用户的好友列表(这个好友列表是去除黑名单用户的哦),目前支持的查询好友个数为100，如需修改请在调用这个方法前设置BmobConfig.LIMIT_CONTACTS即可。
		// 这里默认采取的是登陆成功之后即将好于列表存储到数据库中，并更新到当前内存中,
		userManager.queryCurrentContactList(new FindListener<BmobChatUser>() {

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				if (arg0 == BmobConfig.CODE_COMMON_NONE) {
					ShowLog(arg1);
				} else {
					ShowLog("查询好友列表失败：" + arg1);
				}
			}

			@Override
			public void onSuccess(List<BmobChatUser> arg0) {
				// TODO Auto-generated method stub
				// 保存到application中方便比较
				MyApplication.getInstance().setContactList(
						CollectionUtils.list2map(arg0));
			}
		});
	}

	/**
	 * 更新用户的经纬度信息
	 * 
	 * @Title: uploadLocation
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 */
	public void updateUserLocation() {
		if (MyApplication.lastPoint != null) {
			String saveLatitude = myApplication.getLatitude();
			String saveLongtitude = myApplication.getLongtitude();
			String newLat = String.valueOf(MyApplication.lastPoint
					.getLatitude());
			String newLong = String.valueOf(MyApplication.lastPoint
					.getLongitude());
			// ShowLog("saveLatitude ="+saveLatitude+",saveLongtitude = "+saveLongtitude);
			// ShowLog("newLat ="+newLat+",newLong = "+newLong);
			if (!saveLatitude.equals(newLat) || !saveLongtitude.equals(newLong)) {// 只有位置有变化就更新当前位置，达到实时更新的目的
				User u = (User) userManager.getCurrentUser(User.class);
				final User user = new User();
				user.setLocation(MyApplication.lastPoint);
				user.setObjectId(u.getObjectId());
				user.update(this, new UpdateListener() {
					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						MyApplication.getInstance()
								.setLatitude(
										String.valueOf(user.getLocation()
												.getLatitude()));
						MyApplication.getInstance().setLongtitude(
								String.valueOf(user.getLocation()
										.getLongitude()));
						// ShowLog("经纬度更新成功");
					}

					@Override
					public void onFailure(int code, String msg) {
						// TODO Auto-generated method stub
						// ShowLog("经纬度更新 失败:"+msg);
					}
				});
			} else {
				// ShowLog("用户位置未发生过变化");
			}
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			View v = getCurrentFocus();
			if (isShouldHideInput(v, ev)) {

				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				if (imm != null) {
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				}
			}
			return super.dispatchTouchEvent(ev);
		}
		// 必不可少，否则所有的组件都不会有TouchEvent了
		if (getWindow().superDispatchTouchEvent(ev)) {
			return true;
		}
		return onTouchEvent(ev);

	}

	public boolean isShouldHideInput(View v, MotionEvent event) {
		if (v != null && (v instanceof EditText)) {
			int[] leftTop = { 0, 0 };
			// 获取输入框当前的location位置
			v.getLocationInWindow(leftTop);
			int left = leftTop[0];
			int top = leftTop[1];
			int bottom = top + v.getHeight();
			int right = left + v.getWidth();
			if (event.getX() > left && event.getX() < right
					&& event.getY() > top && event.getY() < bottom) {
				// 点击的是输入框区域，保留点击EditText的事件
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
