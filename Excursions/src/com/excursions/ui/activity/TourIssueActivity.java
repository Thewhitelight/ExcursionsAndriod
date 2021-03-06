package com.excursions.ui.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;

import com.example.excursions.R;
import com.excursions.adapter.EmoViewPagerAdapter;
import com.excursions.adapter.EmoteAdapter;
import com.excursions.bean.FaceText;
import com.excursions.ui.customview.EmoticonsEditText;
import com.excursions.utils.FaceTextUtils;

public class TourIssueActivity extends ActivityBase implements OnClickListener {
	private EmoticonsEditText edit_user_content;
	private Button btn_emo, btn_add;
	private ViewPager pager_emo;
	private LinearLayout layout_more, layout_add, layout_emo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tourissue);
		init();
		actionBar.setTitle("发帖");
	}

	private void init() {
		// TODO Auto-generated method stub
		layout_add = (LinearLayout) findViewById(R.id.layout_add);
		layout_emo = (LinearLayout) findViewById(R.id.layout_emo);
		layout_more = (LinearLayout) findViewById(R.id.layout_more);
		edit_user_content = (EmoticonsEditText) findViewById(R.id.edit_user_comment);
		btn_add = (Button) findViewById(R.id.btn_issue_add);
		btn_emo = (Button) findViewById(R.id.btn_issue_emo);
		btn_add.setOnClickListener(this);
		btn_emo.setOnClickListener(this);
		initEmoView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.bill_ok_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
		if (id == R.id.ok) {
			TourIssueActivity.this.finish();
		}
		return super.onOptionsItemSelected(item);
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
		pager_emo = (ViewPager) findViewById(R.id.pager_issue_emo);
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
				TourIssueActivity.this, list);
		gridview.setAdapter(gridAdapter);
		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				FaceText name = (FaceText) gridAdapter.getItem(position);
				String key = name.text.toString();
				try {
					if (edit_user_content != null && !TextUtils.isEmpty(key)) {
						int start = edit_user_content.getSelectionStart();
						CharSequence content = edit_user_content.getText()
								.insert(start, key);
						edit_user_content.setText(content);
						// 定位光标位置
						CharSequence info = edit_user_content.getText();
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
		case R.id.btn_issue_add:
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
		case R.id.btn_issue_emo:
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
		default:
			break;
		}
	}

	private void showEditState(boolean isEmo) {
		edit_user_content.setVisibility(View.VISIBLE);
		edit_user_content.requestFocus();
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
						.showSoftInput(edit_user_content, 0);
		}
	}
}
