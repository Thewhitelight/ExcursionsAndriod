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
import android.widget.Toast;
import cn.bmob.im.BmobChat;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.SDKInitializer;
import com.example.excursions.R;
import com.excursions.application.BmobConfig;
import com.excursions.application.MyApplication;
import com.excursions.utils.CommonUtils;

public class SplashActivity extends BaseActivity {
	private ImageView img_splash;
	private int currentversioncode;// ��ǰ�汾��
	private SharedPreferences preferences;
	private int count;// ��һ�汾��

	private static final int GO_HOME = 100;
	private static final int GO_LOGIN = 200;

	// ��λ��ȡ��ǰ�û��ĵ���λ��
	private LocationClient mLocationClient;

	private BaiduReceiver mReceiver;// ע��㲥�����������ڼ��������Լ���֤key

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
		// BmobIM SDK��ʼ��--ֻ��Ҫ��һ�δ��뼴����ɳ�ʼ��
		// �뵽Bmob����(http://www.bmob.cn/)����ApplicationId,�����ַ:http://docs.bmob.cn/android/faststart/index.html?menukey=fast_start&key=start_android
		BmobChat.getInstance(this).init(BmobConfig.applicationId);
		// ������λ
		initLocClient();
		// ע���ͼ SDK �㲥������
		IntentFilter iFilter = new IntentFilter();
		iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
		iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
		mReceiver = new BaiduReceiver();
		registerReceiver(mReceiver, iFilter);
	}

	/**
	 * ������λ�����µ�ǰ�û��ľ�γ������
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
		option.setLocationMode(LocationMode.Hight_Accuracy);// ���ö�λģʽ:�߾���ģʽ
		option.setCoorType("bd09ll"); // ������������:�ٶȾ�γ��
		option.setScanSpan(1000);// ���÷���λ����ļ��ʱ��Ϊ1000ms:����1000Ϊ�ֶ���λһ�Σ����ڻ����1000��Ϊ��ʱ��λ
		option.setIsNeedAddress(false);// ����Ҫ������ַ��Ϣ
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
	 * ��ȡ�汾��(�ڲ�ʶ���)
	 * 
	 * @param context
	 * @return �汾��
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
			// ÿ���Զ���½��ʱ�����Ҫ�����µ�ǰλ�úͺ��ѵ����ϣ���Ϊ���ѵ�ͷ���ǳ�ɶ���Ǿ����䶯��
			updateUserInfos();
			mHandler.sendEmptyMessageDelayed(GO_HOME, 2000);
		} else {
			mHandler.sendEmptyMessageDelayed(GO_LOGIN, 2000);
		}
	}

	@Override
	protected void onDestroy() {
		// �˳�ʱ���ٶ�λ
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

	private void startMainActivity() {
		if (CommonUtils.isNetworkAvailable(getApplicationContext())) {
			new Thread() {
				public void run() {
					try {
						Thread.sleep(3000);// ����3����
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
					} catch (InterruptedException e) {
						// TODO: handle exception
						e.printStackTrace();
					}

					SplashActivity.this.finish();
				};
			}.start();
		} else {
			Toast.makeText(getApplicationContext(), "����������ʧ�ܣ�����������Ժ�����",
					Toast.LENGTH_SHORT).show();

			new Thread() {
				public void run() {
					try {
						Thread.sleep(3000);// ����3����
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

					} catch (InterruptedException e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					SplashActivity.this.finish();
				};
			}.start();
		}
	}

	/**
	 * ����㲥�����࣬���� SDK key ��֤�Լ������쳣�㲥
	 */
	public class BaiduReceiver extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {
			String s = intent.getAction();
			if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
				ShowToast("key ��֤����! ���� AndroidManifest.xml �ļ��м�� key ����");
			} else if (s
					.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
				ShowToast("��ǰ�������Ӳ��ȶ�������������������!");
			}
		}
	}
}
