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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.example.excursions.R;
import com.excursions.adapter.GridViewAdapter;
import com.excursions.application.BmobConstants;
import com.excursions.bean.Attractions;
import com.excursions.data.GridViewData;
import com.excursions.ui.activity.AttractionDetailActivity;
import com.excursions.ui.activity.BillActivity;
import com.excursions.utils.ParseJson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;

public class AttractionsInfoMainFragment extends BaseFragment {
	private View view;
	private List<Attractions> mGridItems;
	private PullToRefreshGridView mPullRefreshGridView;
	private GridView mGridView;
	private GridViewAdapter adapter;
	private int index = 1;

	// private String url =
	// "http://10.64.130.129:10240/Attraction/AttractionList?flag=0";
	// private String moreUrl =
	// "http://10.64.130.129:10240/Attraction/AttractionList?flag=";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		new GetDataTask().execute(BmobConstants.INDEXURL);
		mGridItems = new ArrayList<Attractions>();
		try {
			mGridItems = GridViewData.getData(getActivity());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		adapter = new GridViewAdapter(getActivity(), mGridItems);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_attractions_main, container,
				false);
		init();
		return view;
	}

	public void init() {
		// TODO Auto-generated method stub
		mPullRefreshGridView = (PullToRefreshGridView) view
				.findViewById(R.id.pull_refresh_grid);
		mGridView = mPullRefreshGridView.getRefreshableView();
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setClass(getActivity(), AttractionDetailActivity.class);
				i.putExtra(BmobConstants.ATTPOSITION, position);
				startActivity(i);

			}
		});
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

						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);
						if (BmobConstants.INDEXURL == "http://10.64.130.129:10240/Attraction/AttractionList?flag=0") {
							Toast.makeText(getActivity(), "已经是最新内容了",
									Toast.LENGTH_SHORT).show();
							refreshView.onRefreshComplete();
						} else {
							new GetDataTask().execute(BmobConstants.INDEXURL);
						}
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<GridView> refreshView) {
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);
						new GetDataTask()
								.execute(BmobConstants.MOREURL + index);
						index++;
					}

				});
		mGridView.setAdapter(adapter);

	}

	public class GetDataTask extends AsyncTask<String, Void, List<Attractions>> {
		List<Attractions> list;

		@Override
		protected List<Attractions> doInBackground(String... params) {
			// Simulates a background job.
			try {
				URL url = new URL(params[0]);
				URLConnection urlcon = url.openConnection();
				InputStream is = urlcon.getInputStream();
				InputStreamReader in = new InputStreamReader(is, "utf-8");
				BufferedReader buffer = new BufferedReader(in);
				String str = null;

				list = new ArrayList<Attractions>();
				while ((str = buffer.readLine()) != null) {
					list = ParseJson.getAttractionsJson(str);
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
			return list;
		}

		@Override
		protected void onPostExecute(List<Attractions> result) {
			// 在头部增加新添内容

			try {
				mGridItems.addAll(result);
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
