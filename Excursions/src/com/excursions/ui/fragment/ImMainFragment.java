package com.excursions.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.excursions.R;
import com.excursions.ui.activity.AddFriendActivity;
import com.excursions.ui.activity.NearPeopleActivity;
import com.excursions.ui.customview.PagerSlidingTabStrip;

public class ImMainFragment extends BaseFragment {
	private View view;
	private PagerSlidingTabStrip tabs;
	private ViewPager viewPager;
	private SessionFragment session;
	private LinkmanFragment linkman;
	private String[] titles = { "会话", "联系人" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_im_main, container, false);
		setHasOptionsMenu(true);
		init();
		return view;
	}

	public void init() {
		// TODO Auto-generated method stub
		viewPager = (ViewPager) view.findViewById(R.id.viewPager);
		tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
		tabs.setTextSize(40);
		viewPager
				.setAdapter(new ViewPagerAdapater(getFragmentManager(), titles));
		tabs.setViewPager(viewPager);
		viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {

			@SuppressLint("NewApi")
			@Override
			public void transformPage(View page, float position) {
				// TODO Auto-generated method stub
				final float normalizedposition = Math.abs(Math.abs(position) - 1);
				page.setAlpha(normalizedposition);// 翻页动画1
				// final float normalizedposition = Math.abs(Math.abs(position)
				// - 1);//翻页动画2
				// page.setScaleX(normalizedposition / 2 + 0.5f);
				// page.setScaleY(normalizedposition / 2 + 0.5f);
				// page.setRotationY(position * -30);翻页动画3
			}
		});
	}

	public class ViewPagerAdapater extends FragmentStatePagerAdapter {
		String[] titles;

		public ViewPagerAdapater(FragmentManager fm, String[] titles) {
			super(fm);
			// TODO Auto-generated constructor stub
			this.titles = titles;
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			switch (arg0) {
			case 0:
				session = new SessionFragment();
				return session;
			case 1:
				linkman = new LinkmanFragment();
				return linkman;
			default:
				break;
			}
			return null;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return titles.length;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			return titles[position];
		}
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.add_user) {
			Intent intent = new Intent(getActivity(), AddFriendActivity.class);
			intent.putExtra("from", "contact");
			startAnimActivity(intent);
			return true;
		}
		if (id == R.id.location) {
			Intent intent = new Intent(getActivity(), NearPeopleActivity.class);
			startAnimActivity(intent);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		menu.clear();
		MenuInflater inflater = getActivity().getMenuInflater();
		inflater.inflate(R.menu.im_menu, menu);
		super.onPrepareOptionsMenu(menu);
	}
}
