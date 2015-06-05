package com.excursions.ui.activity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

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
import com.excursions.data.TouListViewData;
import com.excursions.utils.ReadTextFile;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class DealActivity extends ActivityBase {
	private List<Map<String, Object>> list;
	private PullToRefreshListView mPullRefreshListView;
	private TourLvAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_deal);
		initView();
		initData();
	}

	private void initView() {
		// TODO Auto-generated method stub
		list = new ArrayList<Map<String, Object>>();
		list = TouListViewData.getData(this);
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
						new GetDataTask().execute();

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
				startActivity(new Intent(DealActivity.this,
						TouristDetailActivity.class));
			}
		});
	}

	private class GetDataTask extends
			AsyncTask<Void, Void, List<Map<String, Object>>> {

		@Override
		protected List<Map<String, Object>> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			Map<String, Object> map = new HashMap<String, Object>();
			InputStream inputStream;
			try {
				inputStream = getApplicationContext().getAssets().open(
						"tou_main.txt");
				String json = ReadTextFile.readTextFile(inputStream);
				JSONArray array = new JSONArray(json);
				int n = array.length();
				for (int i = 0; i < n; i++) {
					map = new HashMap<String, Object>();
					map.put("title", array.getJSONObject(i).getString("title"));
					map.put("content",
							array.getJSONObject(i).getString("content"));
					map.put("man", array.getJSONObject(i).getString("man"));
					map.put("time", array.getJSONObject(i).getString("time"));
					map.put("number", array.getJSONObject(i)
							.getString("number"));
					list.add(map);
				}
				return list;

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return null;
			}

		}

		@Override
		protected void onPostExecute(List<Map<String, Object>> result) {
			// TODO Auto-generated method stub
			try {
				list.addAll(0, result);

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
