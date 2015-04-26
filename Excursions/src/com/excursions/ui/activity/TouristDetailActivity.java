package com.excursions.ui.activity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.excursions.R;
import com.excursions.adapter.EmoViewPagerAdapter;
import com.excursions.adapter.EmoteAdapter;
import com.excursions.adapter.TourDetailAdapter;
import com.excursions.bean.FaceText;
import com.excursions.data.TourDetailData;
import com.excursions.ui.customview.EmoticonsEditText;
import com.excursions.utils.FaceTextUtils;
import com.excursions.utils.ReadTextFile;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class TouristDetailActivity extends ActivityBase implements
		OnClickListener {
	private Button btn_chat_add, btn_chat_emo, btn_chat_send;
	private EmoticonsEditText edit_user_comment;
	private LinearLayout layout_more, layout_add, layout_emo, buttom_bar;
	private ViewPager pager_emo;
	private List<Map<String, Object>> list;
	private PullToRefreshListView mPullRefreshListView;
	private TourDetailAdapter adapter;
	private Mode currentMode;
	private static int position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tourist_detail);
		list = new ArrayList<Map<String, Object>>();
		list = TourDetailData.getData(this);
		adapter = new TourDetailAdapter(this, list);
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		initEmoView();

		buttom_bar = (LinearLayout) findViewById(R.id.buttom_bar);
		edit_user_comment = (EmoticonsEditText) findViewById(R.id.edit_user_comment);
		layout_add = (LinearLayout) findViewById(R.id.layout_add);
		layout_emo = (LinearLayout) findViewById(R.id.layout_emo);
		layout_more = (LinearLayout) findViewById(R.id.layout_more);
		btn_chat_add = (Button) findViewById(R.id.btn_chat_add);
		btn_chat_emo = (Button) findViewById(R.id.btn_chat_emo);
		btn_chat_send = (Button) findViewById(R.id.btn_chat_send);
		btn_chat_add.setOnClickListener(this);
		btn_chat_emo.setOnClickListener(this);
		btn_chat_send.setOnClickListener(this);
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
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
						currentMode = refreshView.getCurrentMode();
						if (Mode.PULL_FROM_START == currentMode) {
							// Update the LastUpdatedLabel
							refreshView.getLoadingLayoutProxy()
									.setLastUpdatedLabel(label);

							// D work to refresh the list here.
							new GetDataTask().execute();
						}
						if (Mode.PULL_FROM_END == currentMode) {
							// Update the LastUpdatedLabel
							refreshView.getLoadingLayoutProxy()
									.setLastUpdatedLabel(label);

							// D work to refresh the list here.
							new GetDataTask().execute();
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
				buttom_bar.setVisibility(View.VISIBLE);
				TouristDetailActivity.position = position;
				ShowToast("点击" + position);
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
						"tou_detail.txt");
				String json = ReadTextFile.readTextFile(inputStream);
				JSONArray array = new JSONArray(json);
				int n = array.length();
				for (int i = 0; i < n; i++) {
					map = new HashMap<String, Object>();
					map.put("name", array.getJSONObject(i).getString("name"));
					map.put("layer", array.getJSONObject(i).getString("layer"));
					map.put("time", array.getJSONObject(i).getString("time"));
					map.put("comment",
							array.getJSONObject(i).getString("comment"));
					map.put("name0", array.getJSONObject(i).getString("name0"));
					map.put("time0", array.getJSONObject(i).getString("time0"));
					map.put("comment0",
							array.getJSONObject(i).getString("comment0"));
					map.put("name1", array.getJSONObject(i).getString("name1"));
					map.put("time1", array.getJSONObject(i).getString("time1"));
					map.put("comment1",
							array.getJSONObject(i).getString("comment1"));
					map.put("name2", array.getJSONObject(i).getString("name2"));
					map.put("time2", array.getJSONObject(i).getString("time2"));
					map.put("comment2",
							array.getJSONObject(i).getString("comment2"));
					map.put("name3", array.getJSONObject(i).getString("name3"));
					map.put("time3", array.getJSONObject(i).getString("time3"));
					map.put("comment3",
							array.getJSONObject(i).getString("comment3"));
					map.put("name4", array.getJSONObject(i).getString("name4"));
					map.put("time4", array.getJSONObject(i).getString("time4"));
					map.put("comment4",
							array.getJSONObject(i).getString("comment4"));
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

	List<FaceText> emos;

	/**
	 * 初始化表情布局
	 * 
	 * @Title: initEmoView
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 */
	private void initEmoView() {
		pager_emo = (ViewPager) findViewById(R.id.pager_emo);
		emos = FaceTextUtils.faceTexts;

		List<View> views = new ArrayList<View>();
		for (int i = 0; i < 2; ++i) {
			views.add(getGridView(i));
		}
		pager_emo.setAdapter(new EmoViewPagerAdapter(views));
	}

	private View getGridView(final int i) {
		View view = View.inflate(this, R.layout.include_emo_gridview, null);
		GridView gridview = (GridView) view.findViewById(R.id.gridview);
		List<FaceText> list = new ArrayList<FaceText>();
		if (i == 0) {
			list.addAll(emos.subList(0, 21));
		}
		if (i == 1) {
			list.addAll(emos.subList(21, emos.size()));
		}

		final EmoteAdapter gridAdapter = new EmoteAdapter(
				TouristDetailActivity.this, list);
		gridview.setAdapter(gridAdapter);
		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				FaceText name = (FaceText) gridAdapter.getItem(position);
				String key = name.text.toString();
				try {
					if (edit_user_comment != null && !TextUtils.isEmpty(key)) {
						int start = edit_user_comment.getSelectionStart();
						CharSequence content = edit_user_comment.getText()
								.insert(start, key);
						edit_user_comment.setText(content);
						// 定位光标位置
						CharSequence info = edit_user_comment.getText();
						if (info instanceof Spannable) {
							Spannable spanText = (Spannable) info;
							Selection.setSelection(spanText,
									start + key.length());
						}
					}
				} catch (Exception e) {

				}

			}
		});
		return view;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.edit_user_comment:// 点击文本输入框
			if (layout_more.getVisibility() == View.VISIBLE) {
				layout_add.setVisibility(View.GONE);
				layout_emo.setVisibility(View.GONE);
				layout_more.setVisibility(View.GONE);
			}

			break;
		case R.id.btn_chat_emo:// 点击笑脸图标
			if (layout_more.getVisibility() == View.GONE) {
				showEditState(true);
			} else {
				if (layout_add.getVisibility() == View.VISIBLE) {
					layout_add.setVisibility(View.GONE);
					layout_emo.setVisibility(View.VISIBLE);
				} else {
					layout_more.setVisibility(View.GONE);
				}
			}

			break;
		case R.id.btn_chat_add:// 添加按钮-显示图片、拍照、位置
			if (layout_more.getVisibility() == View.GONE) {
				layout_more.setVisibility(View.VISIBLE);
				layout_add.setVisibility(View.VISIBLE);
				layout_emo.setVisibility(View.GONE);
				hideSoftInputView();
			} else {
				if (layout_emo.getVisibility() == View.VISIBLE) {
					layout_emo.setVisibility(View.GONE);
					layout_add.setVisibility(View.VISIBLE);
				} else {
					layout_more.setVisibility(View.GONE);
				}
			}

			break;
		case R.id.btn_chat_send:
			upListItem(position);
			edit_user_comment.setText("");
			break;
		}
	}

	private void upListItem(int position) {
		// TODO Auto-generated method stub
		Map<String, Object> map = (HashMap<String, Object>) adapter
				.getItem(position - 1);//position-1 否则数组越界
		map.put("comment", edit_user_comment.getText().toString());
		adapter.notifyDataSetChanged();
	}

	/**
	 * 根据是否点击笑脸来显示文本输入框的状态
	 * 
	 * @Title: showEditState
	 * @Description: TODO
	 * @param @param isEmo: 用于区分文字和表情
	 * @return void
	 * @throws
	 */
	private void showEditState(boolean isEmo) {
		edit_user_comment.setVisibility(View.VISIBLE);
		edit_user_comment.requestFocus();
		if (isEmo) {
			layout_more.setVisibility(View.VISIBLE);
			layout_more.setVisibility(View.VISIBLE);
			layout_emo.setVisibility(View.VISIBLE);
			layout_add.setVisibility(View.GONE);
			hideSoftInputView();
		} else {
			layout_more.setVisibility(View.GONE);
			showSoftInputView();
		}
	}

	// 显示软键盘
	public void showSoftInputView() {
		if (getWindow().getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
			if (getCurrentFocus() != null)
				((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
						.showSoftInput(edit_user_comment, 0);
		}
	}
}
