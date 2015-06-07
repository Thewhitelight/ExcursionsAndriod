package com.excursions.ui.fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.excursions.R;
import com.excursions.adapter.TourLvAdapter;
import com.excursions.application.BmobConstants;
import com.excursions.bean.TourMain;
import com.excursions.ui.activity.DealActivity;
import com.excursions.ui.activity.NotifyActivity;
import com.excursions.ui.activity.TourIssueActivity;
import com.excursions.ui.activity.TouristDetailActivity;
import com.excursions.utils.ParseJson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class TouristInfoMainFragment extends BaseFragment {
	private View view;
	private List<TourMain> list;
	private PullToRefreshListView mPullRefreshListView;
	private TourLvAdapter adapter;
	private Mode currentMode;
	private int index = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		list = new ArrayList<TourMain>();
		new GetDataTask().execute(BmobConstants.TOURURL);
		// Log.i("oncreate", list.get(16).getUserUserName());
		adapter = new TourLvAdapter(getActivity(), list);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_tourisinfo_main, container,
				false);
		init();
		return view;
	}

	public void init() {
		// TODO Auto-generated method stub
		mPullRefreshListView = (PullToRefreshListView) view
				.findViewById(R.id.fragment_pull_refresh_list);
		// 设定下拉监听函数
		mPullRefreshListView
				.setOnRefreshListener(new OnRefreshListener<ListView>() {
					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						String label = DateUtils.formatDateTime(getActivity(),
								System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);
						currentMode = refreshView.getCurrentMode();
						if (Mode.PULL_FROM_START == currentMode) {
							// Update the LastUpdatedLabel
							refreshView.getLoadingLayoutProxy()
									.setLastUpdatedLabel(label);
							new GetDataTask2().execute();

						}
						if (Mode.PULL_FROM_END == currentMode) {
							// Update the LastUpdatedLabel
							refreshView.getLoadingLayoutProxy()
									.setLastUpdatedLabel(label);

							// D work to refresh the list here.
							new GetDataTask().execute(BmobConstants.TOURMORE
									+ index);
							index++;
						}

					}
				});

		// 设置适配器
		ListView actualListView = mPullRefreshListView.getRefreshableView();
		actualListView.setAdapter(adapter);
		mPullRefreshListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setClass(getActivity(), TouristDetailActivity.class);
				i.putExtra("flag", 1);
				startActivity(i);
			}
		});
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	private class GetDataTask extends AsyncTask<String, Void, List<TourMain>> {
		List<TourMain> lists;

		@Override
		protected List<TourMain> doInBackground(String... params) {
			// Simulates a background job.
			try {
				URL url = new URL(params[0]);
				URLConnection urlcon = url.openConnection();
				InputStream is = urlcon.getInputStream();
				InputStreamReader in = new InputStreamReader(is, "utf-8");
				BufferedReader buffer = new BufferedReader(in);
				String str = null;

				lists = new ArrayList<TourMain>();
				while ((str = buffer.readLine()) != null) {
					lists = ParseJson.getTourJson(str);
				}
				buffer.close();
				in.close();
				is.close();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return lists;
		}

		@Override
		protected void onPostExecute(List<TourMain> result) {
			try {
				list.addAll(result);
				// 通知程序数据集已经改变，如果不做通知，那么将不会刷新mListItems的集合
				adapter.notifyDataSetChanged();
				// Call onRefreshComplete when the list has been refreshed.
				mPullRefreshListView.onRefreshComplete();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

			super.onPostExecute(result);
		}
	}

	private class GetDataTask2 extends AsyncTask<Void, Void, List<TourMain>> {

		@Override
		protected List<TourMain> doInBackground(Void... params) {
			try {
				Thread.sleep(500);
			} catch (Exception e) {
				// TODO: handle exception
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<TourMain> result) {
			mPullRefreshListView.onRefreshComplete();
			super.onPostExecute(result);
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.menu) {
			return true;
		}
		if (id == R.id.all) {

			return true;
		}
		if (id == R.id.friends) {

			return true;
		}
		if (id == R.id.market) {
			startActivity(new Intent(getActivity(), DealActivity.class));
			return true;
		}
		if (id == R.id.notice) {
			startActivity(new Intent(getActivity(), NotifyActivity.class));
			return true;
		}
		if (id == R.id.add_card) {
			startActivity(new Intent(getActivity(), TourIssueActivity.class));
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {

		menu.clear();

		MenuInflater inflater = getActivity().getMenuInflater();
		inflater.inflate(R.menu.tou_menu, menu);
		super.onPrepareOptionsMenu(menu);
	}
}
