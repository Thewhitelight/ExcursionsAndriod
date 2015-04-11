package com.excursions.ui.activity;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.example.excursions.R;
import com.excursions.ui.fragment.GuideFragment01;
import com.excursions.ui.fragment.GuideFragment02;
import com.excursions.ui.fragment.GuideFragment03;

public class GuideActivity extends FragmentActivity {
	private ViewPager viewPager;
	private GuideFragment01 guide01;
	private GuideFragment02 guide02;
	private GuideFragment03 guide03;
	private List<Fragment> fragmentList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);
		viewPager = (ViewPager) findViewById(R.id.guide_pager);
		fragmentList = new ArrayList<Fragment>();
		guide01 = new GuideFragment01();
		guide02 = new GuideFragment02();
		guide03 = new GuideFragment03();
		fragmentList.add(guide01);
		fragmentList.add(guide02);
		fragmentList.add(guide03);
		viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
		viewPager.setOffscreenPageLimit(2);
		viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {

			@SuppressLint("NewApi")
			@Override
			public void transformPage(View page, float position) {
				// TODO Auto-generated method stub
				final float normalizedposition = Math.abs(Math.abs(position) - 1);
				page.setAlpha(normalizedposition);// ·­Ò³¶¯»­1
				// final float normalizedposition = Math.abs(Math.abs(position)
				// - 1);//·­Ò³¶¯»­2
				// page.setScaleX(normalizedposition / 2 + 0.5f);
				// page.setScaleY(normalizedposition / 2 + 0.5f);
				// page.setRotationY(position * -30);·­Ò³¶¯»­3
			}
		});
	}

	public class MyPagerAdapter extends FragmentPagerAdapter {

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			return fragmentList.get(arg0);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return fragmentList.size();
		}
	}
}
