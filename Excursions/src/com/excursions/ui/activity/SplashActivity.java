package com.excursions.ui.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import cn.bmob.im.BmobChat;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.SDKInitializer;
import com.example.excursions.R;
import com.excursions.application.BmobConfig;
import com.excursions.application.MyApplication;

public class SplashActivity extends BaseActivity {
	private ImageView img_splash;
	private int currentversioncode;// 当前版本号
	private SharedPreferences preferences;
	private int count;// 上一版本号

	private static final int GO_HOME = 100;
	private static final int GO_LOGIN = 200;

	// 定位获取当前用户的地理位置
	private LocationClient mLocationClient;

	private BaiduReceiver mReceiver;// 注册广播接收器，用于监听网络以及验证key

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_splash);
		img_splash = (ImageView) findViewById(R.id.img_splash);
		img_splash.setBackgroundResource(R.drawable.splash_list);
		final AnimationDrawable drawable = (AnimationDrawable) img_splash
				.getBackground();
		img_splash.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				drawable.start();
			}
		});
		// startMainActivity();
		readShare();
		BmobChat.DEBUG_MODE = true;
		// BmobIM SDK初始化--只需要这一段代码即可完成初始化
		// 请到Bmob官网(http://www.bmob.cn/)申请ApplicationId,具体地址:http://docs.bmob.cn/android/faststart/index.html?menukey=fast_start&key=start_android
		BmobChat.getInstance(this).init(BmobConfig.applicationId);
		// 开启定位
		initLocClient();
		// 注册地图 SDK 广播监听者
		IntentFilter iFilter = new IntentFilter();
		iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
		iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
		mReceiver = new BaiduReceiver();
		registerReceiver(mReceiver, iFilter);
	}

	/**
	 * 开启定位，更新当前用户的经纬度坐标
	 * 
	 * @Title: initLocClient
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 */
	private void initLocClient() {
		mLocationClient = MyApplication.getInstance().mLocationClient;
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式:高精度模式
		option.setCoorType("bd09ll"); // 设置坐标类型:百度经纬度
		option.setScanSpan(1000);// 设置发起定位请求的间隔时间为1000ms:低于1000为手动定位一次，大于或等于1000则为定时定位
		option.setIsNeedAddress(false);// 不需要包含地址信息
		mLocationClient.setLocOption(option);
		mLocationClient.start();
	}

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GO_HOME:

				if (count == currentversioncode) {
					Intent intent = new Intent(
							com.excursions.ui.activity.SplashActivity.this,
							com.excursions.ui.activity.MainActivity.class);
					startActivity(intent);
				} else {

					Intent intent = new Intent(
							com.excursions.ui.activity.SplashActivity.this,
							com.excursions.ui.activity.GuideActivity.class);
					startActivity(intent);
				}
				finish();
				break;
			case GO_LOGIN:
				if (count == currentversioncode) {
					Intent intent = new Intent(
							com.excursions.ui.activity.SplashActivity.this,
							com.excursions.ui.activity.SignInActivity.class);
					startActivity(intent);
				} else {

					Intent intent = new Intent(
							com.excursions.ui.activity.SplashActivity.this,
							com.excursions.ui.activity.GuideActivity.class);
					startActivity(intent);
				}
				finish();
				break;
			}
		}
	};

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		writeShare();
	}

	/**
	 * 获取版本号(内部识别号)
	 * 
	 * @param context
	 * @return 版本号
	 */
	public static int getVersionCode(Context context) {
		try {
			PackageInfo pi = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			return pi.versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (userManager.getCurrentUser() != null) {
			// 每次自动登陆的时候就需要更新下当前位置和好友的资料，因为好友的头像，昵称啥的是经常变动的
			updateUserInfos();
			mHandler.sendEmptyMessageDelayed(GO_HOME, 2000);
		} else {
			mHandler.sendEmptyMessageDelayed(GO_LOGIN, 2000);
		}
	}

	@Override
	protected void onDestroy() {
		// 退出时销毁定位
		if (mLocationClient != null && mLocationClient.isStarted()) {
			mLocationClient.stop();
		}
		unregisterReceiver(mReceiver);
		super.onDestroy();
	}

	public void writeShare() {
		preferences = getSharedPreferences("versionCode", MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt("versionCode", currentversioncode);
		editor.commit();
	}

	public void readShare() {
		preferences = getSharedPreferences("versionCode", MODE_PRIVATE);
		count = preferences.getInt("versionCode", 2);
	}

	/**
	 * 构造广播监听类，监听 SDK key 验证以及网络异常广播
	 */
	public class BaiduReceiver extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {
			String s = intent.getAction();
			if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
				ShowToast("当前网络连接不稳定，请检查您的网络设置!");
			} else if (s
					.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
				ShowToast("当前网络连接不稳定，请检查您的网络设置!");
			}
		}
	}
}
