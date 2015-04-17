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
 * @Description: ����ӭ����½�������̳еĻ���
 * @author SZQ
 * @date 2015��4��16�� ����11:10:56
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
		// �Զ���½״̬�¼���Ƿ��������豸��½
		checkLogin();
		userManager = BmobUserManager.getInstance(this);
		manager = BmobChatManager.getInstance(this);
		myApplication = MyApplication.getInstance();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// ����״̬�µļ��
		checkLogin();
	}

	public void checkLogin() {
		BmobUserManager userManager = BmobUserManager.getInstance(this);
		if (userManager.getCurrentUser() == null) {
			Toast.makeText(getBaseContext(), "�����˺����������豸�ϵ�¼!",
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
	 * ��Log ShowLog
	 * 
	 * @return void
	 * @throws
	 */
	public void ShowLog(String msg) {
		Log.i("life", msg);
	}

	/**
	 * ��ʾ���ߵĶԻ��� showOfflineDialog
	 * 
	 * @return void
	 * @throws
	 */
	public void showOfflineDialog(final Context context) {
		DialogTips dialog = new DialogTips(this, "�����˺����������豸�ϵ�¼!", "���µ�¼");
		// ���óɹ��¼�
		dialog.SetOnSuccessListener(new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialogInterface, int userId) {
				MyApplication.getInstance().logout();
				startActivity(new Intent(context, SignInActivity.class));
				finish();
				dialogInterface.dismiss();
			}
		});
		// ��ʾȷ�϶Ի���
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
	 * ���ڵ�½�����Զ���½����µ��û����ϼ��������ϵļ�����
	 * 
	 * @Title: updateUserInfos
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 */
	public void updateUserInfos() {
		// ���µ���λ����Ϣ
		updateUserLocation();
		// ��ѯ���û��ĺ����б�(��������б���ȥ���������û���Ŷ),Ŀǰ֧�ֵĲ�ѯ���Ѹ���Ϊ100�������޸����ڵ����������ǰ����BmobConfig.LIMIT_CONTACTS���ɡ�
		// ����Ĭ�ϲ�ȡ���ǵ�½�ɹ�֮�󼴽������б�洢�����ݿ��У������µ���ǰ�ڴ���,
		userManager.queryCurrentContactList(new FindListener<BmobChatUser>() {

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				if (arg0 == BmobConfig.CODE_COMMON_NONE) {
					ShowLog(arg1);
				} else {
					ShowLog("��ѯ�����б�ʧ�ܣ�" + arg1);
				}
			}

			@Override
			public void onSuccess(List<BmobChatUser> arg0) {
				// TODO Auto-generated method stub
				// ���浽application�з���Ƚ�
				MyApplication.getInstance().setContactList(
						CollectionUtils.list2map(arg0));
			}
		});
	}

	/**
	 * �����û��ľ�γ����Ϣ
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
			if (!saveLatitude.equals(newLat) || !saveLongtitude.equals(newLong)) {// ֻ��λ���б仯�͸��µ�ǰλ�ã��ﵽʵʱ���µ�Ŀ��
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
						// ShowLog("��γ�ȸ��³ɹ�");
					}

					@Override
					public void onFailure(int code, String msg) {
						// TODO Auto-generated method stub
						// ShowLog("��γ�ȸ��� ʧ��:"+msg);
					}
				});
			} else {
				// ShowLog("�û�λ��δ�������仯");
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
		// �ز����٣��������е������������TouchEvent��
		if (getWindow().superDispatchTouchEvent(ev)) {
			return true;
		}
		return onTouchEvent(ev);

	}

	public boolean isShouldHideInput(View v, MotionEvent event) {
		if (v != null && (v instanceof EditText)) {
			int[] leftTop = { 0, 0 };
			// ��ȡ�����ǰ��locationλ��
			v.getLocationInWindow(leftTop);
			int left = leftTop[0];
			int top = leftTop[1];
			int bottom = top + v.getHeight();
			int right = left + v.getWidth();
			if (event.getX() > left && event.getX() < right
					&& event.getY() > top && event.getY() < bottom) {
				// ���������������򣬱������EditText���¼�
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
