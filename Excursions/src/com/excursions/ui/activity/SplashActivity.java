package com.excursions.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.excursions.R;
import com.excursions.utils.NetWorkContent;

public class SplashActivity extends Activity {
	private ImageView img_splash;
	private int currentversioncode;// ��ǰ�汾��
	private SharedPreferences preferences;
	private int count;// ��һ�汾��

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
		startMainActivity();
		readShare();
	}

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
		if (NetWorkContent.isNetworkConnected(getApplicationContext())) {
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
}
