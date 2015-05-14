package com.excursions.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.im.BmobChatManager;
import cn.bmob.im.BmobNotifyManager;
import cn.bmob.im.bean.BmobInvitation;
import cn.bmob.im.bean.BmobMsg;
import cn.bmob.im.config.BmobConfig;
import cn.bmob.im.db.BmobDB;
import cn.bmob.im.inteface.EventListener;

import com.example.excursions.R;
import com.excursions.application.MyApplication;
import com.excursions.application.MyMessageReceiver;
import com.excursions.ui.customview.DialogTips;
import com.excursions.ui.fragment.AttractionsInfoMainFragment;
import com.excursions.ui.fragment.ImMainFragment;
import com.excursions.ui.fragment.PersonalCenterFramgent;
import com.excursions.ui.fragment.TouristInfoMainFragment;

public class MainActivity extends ActionBarActivity implements OnClickListener,
		EventListener {
	private AttractionsInfoMainFragment attFragment;
	private ImMainFragment imFragment;
	private TouristInfoMainFragment touFragment;
	private PersonalCenterFramgent perFragment;
	private View attlayout, imlayout, toulayout, perlayout;
	private TextView tv_att, tv_im, tv_tou, tv_per;
	private ImageView img_att, img_im, img_tou, img_per, iv_recent_tips;
	private FragmentManager fragmentMangager;
	private long firstime = 0;
	private SharedPreferences preferences;
	private int theme;
	private ActionBar actionBar;
	public static MainActivity instance = null;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);
		actionBar = getSupportActionBar();
		readSharePreferences();
		switch (theme) {
		case R.style.theme1:
			setTheme(R.style.theme1);
			actionBar.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.tab_bg));
			break;
		case R.style.theme2:
			setTheme(R.style.theme2);
			actionBar.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.themetwo));
			break;
		case R.style.theme3:
			setTheme(R.style.theme3);
			break;
		case R.style.theme4:
			setTheme(R.style.theme4);
			break;
		}
		// setTheme(R.style.theme1);
		setContentView(R.layout.activity_main);
		init();
		fragmentMangager = getSupportFragmentManager();
		setTabSelection(0);
		instance = this;
		initNewMessageBroadCast();
		initTagMessageBroadCast();
	}

	private void initTagMessageBroadCast() {
		// 注册接收消息广播
		userReceiver = new TagBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter(
				BmobConfig.BROADCAST_ADD_USER_MESSAGE);
		// 优先级要低于ChatActivity
		intentFilter.setPriority(3);
		registerReceiver(userReceiver, intentFilter);
	}

	public void init() {
		// TODO Auto-generated method stub
		attlayout = this.findViewById(R.id.attractionsinfo_layout);
		imlayout = this.findViewById(R.id.im_layout);
		toulayout = this.findViewById(R.id.touristinfo_layout);
		perlayout = this.findViewById(R.id.personal_layout);
		tv_att = (TextView) this.findViewById(R.id.attinfo_text);
		tv_im = (TextView) this.findViewById(R.id.im_text);
		tv_tou = (TextView) this.findViewById(R.id.touinfo_text);
		tv_per = (TextView) this.findViewById(R.id.personal_text);
		img_att = (ImageView) this.findViewById(R.id.attinfo_image);
		img_im = (ImageView) this.findViewById(R.id.im_image);
		img_tou = (ImageView) this.findViewById(R.id.touinfo_image);
		img_per = (ImageView) this.findViewById(R.id.personal_image);
		iv_recent_tips = (ImageView) findViewById(R.id.iv_recent_tip);
		attlayout.setOnClickListener(this);
		imlayout.setOnClickListener(this);
		toulayout.setOnClickListener(this);
		perlayout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.attractionsinfo_layout:
			setTabSelection(0);
			break;
		case R.id.im_layout:
			setTabSelection(1);
			break;
		case R.id.touristinfo_layout:
			setTabSelection(2);
			break;
		case R.id.personal_layout:
			setTabSelection(3);
			break;

		default:
			break;
		}
	}

	private void setTabSelection(int index) {
		// TODO Auto-generated method stub
		clearSelection();
		FragmentTransaction fragmentTransaction = fragmentMangager
				.beginTransaction();
		hideFragment(fragmentTransaction);
		int text_selectcolor = Color.WHITE;
		switch (index) {
		case 0:
			tv_att.setTextColor(text_selectcolor);
			img_att.setImageResource(R.drawable.message_selected);
			if (attFragment == null) {
				attFragment = new AttractionsInfoMainFragment();
				fragmentTransaction.add(R.id.content, attFragment);
			} else {
				fragmentTransaction.show(attFragment);
			}
			break;
		case 1:
			tv_im.setTextColor(text_selectcolor);
			img_im.setImageResource(R.drawable.contacts_selected);
			if (imFragment == null) {
				imFragment = new ImMainFragment();
				fragmentTransaction.add(R.id.content, imFragment);
			} else {
				fragmentTransaction.show(imFragment);
			}
			break;
		case 2:
			tv_tou.setTextColor(text_selectcolor);
			img_tou.setImageResource(R.drawable.news_selected);
			if (touFragment == null) {
				touFragment = new TouristInfoMainFragment();
				fragmentTransaction.add(R.id.content, touFragment);
			} else {
				fragmentTransaction.show(touFragment);
			}
			break;
		case 3:
			tv_per.setTextColor(text_selectcolor);
			img_per.setImageResource(R.drawable.setting_selected);
			if (perFragment == null) {
				perFragment = new PersonalCenterFramgent();
				fragmentTransaction.add(R.id.content, perFragment);
			} else {
				fragmentTransaction.show(perFragment);
			}
			break;

		default:
			break;
		}
		fragmentTransaction.commit();
	}

	private void hideFragment(FragmentTransaction ft) {
		// TODO Auto-generated method stub
		if (attFragment != null) {
			ft.hide(attFragment);
		}
		if (imFragment != null) {
			ft.hide(imFragment);
		}
		if (touFragment != null) {
			ft.hide(touFragment);
		}
		if (perFragment != null) {
			ft.hide(perFragment);
		}
	}

	private void clearSelection() {
		// TODO Auto-generated method stub
		int text_unselectcolor = getResources().getColor(R.color.text_unselect);
		tv_att.setTextColor(text_unselectcolor);
		img_att.setImageResource(R.drawable.message_unselected);
		tv_im.setTextColor(text_unselectcolor);
		img_im.setImageResource(R.drawable.contacts_unselected);
		tv_tou.setTextColor(text_unselectcolor);
		img_tou.setImageResource(R.drawable.news_unselected);
		tv_per.setTextColor(text_unselectcolor);
		img_per.setImageResource(R.drawable.setting_unselected);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			long secondtime = System.currentTimeMillis();
			if (secondtime - firstime > 3000) {
				firstime = System.currentTimeMillis();
				Toast.makeText(getApplicationContext(), "再次点击返回键退出",
						Toast.LENGTH_SHORT).show();
				return true;
			} else {
				MainActivity.this.finish();
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	private void readSharePreferences() {
		// TODO Auto-generated method stub
		preferences = getSharedPreferences("theme", MODE_PRIVATE);
		theme = preferences.getInt("theme", R.style.theme1);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// 小圆点提示
		if (BmobDB.create(this).hasUnReadMsg()) {
			iv_recent_tips.setVisibility(View.VISIBLE);
		} else {
			iv_recent_tips.setVisibility(View.GONE);
		}
		if (BmobDB.create(this).hasNewInvite()) {
			iv_recent_tips.setVisibility(View.VISIBLE);
		} else {
			iv_recent_tips.setVisibility(View.GONE);
		}
		MyMessageReceiver.ehList.add(this);// 监听推送的消息
		// 清空
		MyMessageReceiver.mNewNum = 0;

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MyMessageReceiver.ehList.remove(this);// 取消监听推送的消息
	}

	@Override
	public void onMessage(BmobMsg message) {
		// TODO Auto-generated method stub
		refreshNewMsg(message);
	}

	/**
	 * 刷新界面
	 * 
	 * @Title: refreshNewMsg
	 * @Description: TODO
	 * @param @param message
	 * @return void
	 * @throws
	 */
	private void refreshNewMsg(BmobMsg message) {
		// 声音提示
		boolean isAllow = MyApplication.getInstance().getSpUtil()
				.isAllowVoice();
		if (isAllow) {
			MyApplication.getInstance().getMediaPlayer().start();
		}
		iv_recent_tips.setVisibility(View.VISIBLE);
		// 也要存储起来
		if (message != null) {
			BmobChatManager.getInstance(MainActivity.this).saveReceiveMessage(
					true, message);
		}

	}

	NewBroadcastReceiver newReceiver;

	private void initNewMessageBroadCast() {
		// 注册接收消息广播
		newReceiver = new NewBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter(
				BmobConfig.BROADCAST_NEW_MESSAGE);
		// 优先级要低于ChatActivity
		intentFilter.setPriority(3);
		registerReceiver(newReceiver, intentFilter);
	}

	/**
	 * 新消息广播接收者
	 * 
	 */
	private class NewBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			// 刷新界面
			refreshNewMsg(null);
			// 记得把广播给终结掉
			abortBroadcast();
		}
	}

	TagBroadcastReceiver userReceiver;

	/**
	 * 标签消息广播接收者
	 */
	private class TagBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			BmobInvitation message = (BmobInvitation) intent
					.getSerializableExtra("invite");
			refreshInvite(message);
			// 记得把广播给终结掉
			abortBroadcast();
		}
	}

	@Override
	public void onNetChange(boolean isNetConnected) {
		// TODO Auto-generated method stub
		if (isNetConnected) {

			Toast.makeText(getApplicationContext(), R.string.network_tips,
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onAddUser(BmobInvitation message) {
		// TODO Auto-generated method stub
		refreshInvite(message);
	}

	/**
	 * 刷新好友请求
	 * 
	 * @Title: notifyAddUser
	 * @Description: TODO
	 * @param @param message
	 * @return void
	 * @throws
	 */
	private void refreshInvite(BmobInvitation message) {
		boolean isAllow = MyApplication.getInstance().getSpUtil()
				.isAllowVoice();
		if (isAllow) {
			MyApplication.getInstance().getMediaPlayer().start();
		}
		iv_recent_tips.setVisibility(View.VISIBLE);

		// 同时提醒通知
		String tickerText = message.getFromname() + "请求添加好友";
		boolean isAllowVibrate = MyApplication.getInstance().getSpUtil()
				.isAllowVibrate();
		BmobNotifyManager.getInstance(this).showNotify(isAllow, isAllowVibrate,
				R.drawable.ic_launcher, tickerText, message.getFromname(),
				tickerText.toString(), NewFriendActivity.class);

	}

	@Override
	public void onOffline() {
		// TODO Auto-generated method stub

		DialogTips dialog = new DialogTips(this, "您的账号已在其他设备上登录!", "重新登录");
		// 设置成功事件
		dialog.SetOnSuccessListener(new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialogInterface, int userId) {
				MyApplication.getInstance().logout();
				startActivity(new Intent(MainActivity.this,
						SignInActivity.class));
				finish();
				dialogInterface.dismiss();
			}
		});
		// 显示确认对话框
		dialog.show();
		dialog = null;

	}

	@Override
	public void onReaded(String conversionId, String msgTime) {
		// TODO Auto-generated method stub
	}
}
