package com.excursions.ui.activity;

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

import com.example.excursions.R;
import com.excursions.ui.fragment.AttractionsInfoMainFragment;
import com.excursions.ui.fragment.ImMainFragment;
import com.excursions.ui.fragment.PersonalCenterFramgent;
import com.excursions.ui.fragment.TouristInfoMainFragment;

public class MainActivity extends ActionBarActivity implements OnClickListener {
	private AttractionsInfoMainFragment attFragment;
	private ImMainFragment imFragment;
	private TouristInfoMainFragment touFragment;
	private PersonalCenterFramgent perFragment;
	private View attlayout;
	private View imlayout;
	private View toulayout;
	private View perlayout;
	private TextView tv_att;
	private TextView tv_im;
	private TextView tv_tou;
	private TextView tv_per;
	private ImageView img_att;
	private ImageView img_im;
	private ImageView img_tou;
	private ImageView img_per;
	private FragmentManager fragmentMangager;
	private long firstime = 0;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTheme(R.style.theme2);
		overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);
		ActionBar actionBar = getSupportActionBar();
		actionBar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.tab_bg));
		setContentView(R.layout.activity_main);
		init();
		fragmentMangager = getSupportFragmentManager();
		setTabSelection(0);
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
}
