package com.excursions.ui.activity;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.excursions.R;
import com.excursions.adapter.TourLvAdapter;
import com.excursions.application.BmobConstants;
import com.excursions.bean.TourMain;
import com.excursions.utils.ParseJson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class DealActivity extends ActivityBase {
	private List<TourMain> list;
	private PullToRefreshListView mPullRefreshListView;
	private TourLvAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_deal);
		actionBar.setTitle("交易社区");
		initView();
		initData();
	}

	private void initView() {
		// TODO Auto-generated method stub
		list = new ArrayList<TourMain>();
		new GetDataTask().execute(BmobConstants.DEALURL);
		adapter = new TourLvAdapter(this, list);
		mPullRefreshListView = (PullToRefreshListView) this
				.findViewById(R.id.deal_pull_refresh_list);
	}

	private void initData() {
		// TODO Auto-generated method stub

		// 设定下拉监听函数
		mPullRefreshListView
				.setOnRefreshListener(new OnRefreshListener<ListView>() {
					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						String label = DateUtils.formatDateTime(
								getApplicationContext(),
								System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);

						// Update the LastUpdatedLabel
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);
						new GetDataTask().execute(BmobConstants.DEALURL);

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
				i.setClass(getApplicationContext(), TouristDetailActivity.class);
				i.putExtra("flag", 2);
				startActivity(i);
			}
		});
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
			// TODO Auto-generated method stub
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.discuss_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
		if (id == R.id.discuss) {
			startActivity(new Intent(getApplicationContext(),
					TourIssueActivity.class));
			this.finish();
		}
		return super.onOptionsItemSelected(item);
	}
}
