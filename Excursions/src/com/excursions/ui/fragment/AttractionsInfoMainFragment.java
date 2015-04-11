package com.excursions.ui.fragment;

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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.example.excursions.R;
import com.excursions.adapter.GridViewAdapter;
import com.excursions.data.GridViewData;
import com.excursions.ui.activity.BillActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;

public class AttractionsInfoMainFragment extends BaseFragment {
	private View view;

	private List<Map<String, Object>> mGridItems;
	private PullToRefreshGridView mPullRefreshGridView;
	private GridView mGridView;
	private GridViewAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mGridItems = new ArrayList<Map<String, Object>>();
		mGridItems = GridViewData.getData(getActivity());
		adapter = new GridViewAdapter(getActivity(), mGridItems);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_attractionsinfo_main,
				container, false);
		init();
		return view;
	}

	public void init() {
		// TODO Auto-generated method stub
		mPullRefreshGridView = (PullToRefreshGridView) view
				.findViewById(R.id.pull_refresh_grid);
		mGridView = mPullRefreshGridView.getRefreshableView();

		// Set a listener to be invoked when the list should be refreshed.
		mPullRefreshGridView
				.setOnRefreshListener(new OnRefreshListener2<GridView>() {
					String label = DateUtils.formatDateTime(getActivity(),
							System.currentTimeMillis(),
							DateUtils.FORMAT_SHOW_TIME
									| DateUtils.FORMAT_SHOW_DATE
									| DateUtils.FORMAT_ABBREV_ALL);

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<GridView> refreshView) {
						Toast.makeText(getActivity(), "Pull Down!",
								Toast.LENGTH_SHORT).show();
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);
						new GetDataTask().execute();
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<GridView> refreshView) {
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);
						Toast.makeText(getActivity(), "Pull Up!",
								Toast.LENGTH_SHORT).show();
						new GetDataTask().execute();
					}

				});

		mGridView.setAdapter(adapter);

	}

	public class GetDataTask extends
			AsyncTask<Void, Void, List<Map<String, Object>>> {

		@Override
		protected List<Map<String, Object>> doInBackground(Void... params) {
			// Simulates a background job.
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			HashMap<String, Object> map = new HashMap<String, Object>();
			InputStream inputStream;
			try {
				inputStream = getActivity().getAssets().open("gridview.txt");
				String json = com.excursions.utils.ReadTextFile
						.readTextFile(inputStream);
				JSONArray array = new JSONArray(json);
				int n = array.length();
				for (int i = 0; i < n; i++) {
					map = new HashMap<String, Object>();
					map.put("gd_img", array.getJSONObject(i)
							.getString("gd_img"));
					map.put("gd_tv", array.getJSONObject(i).getString("gd_tv"));
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
			// 在头部增加新添内容

			try {
				mGridItems.addAll(0, result);

				// 通知程序数据集已经改变，如果不做通知，那么将不会刷新mListItems的集合
				adapter.notifyDataSetChanged();
				// Call onRefreshComplete when the list has been refreshed.
				mPullRefreshGridView.onRefreshComplete();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

			super.onPostExecute(result);
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
		if (id == R.id.bill) {
			startActivity(new Intent(getActivity(), BillActivity.class));
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		menu.clear();
		MenuInflater inflater = getActivity().getMenuInflater();
		inflater.inflate(R.menu.att_menu, menu);
		super.onPrepareOptionsMenu(menu);
	}

}
