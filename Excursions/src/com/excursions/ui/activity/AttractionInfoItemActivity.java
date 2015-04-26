package com.excursions.ui.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.example.excursions.R;
import com.excursions.adapter.AttractionDetailAdapter;
import com.excursions.data.TouListViewData;

public class AttractionInfoItemActivity extends ActivityBase implements
		OnPageChangeListener {
	private MapView mapView;
	private BaiduMap baiduMap;
	private View view;
	private ScrollView scrollView;
	private ListView listView;
	private AttractionDetailAdapter adapter;
	private List<Map<String, Object>> list;
	/**
	 * ViewPager
	 */
	private ViewPager viewPager;

	/**
	 * װ����ImageView����
	 */
	private ImageView[] tips;

	/**
	 * װImageView����
	 */
	private ImageView[] mImageViews;

	/**
	 * ͼƬ��Դid
	 */
	private int[] imgIdArray;

	Timer mTimer;
	TimerTask mTask;
	int pageIndex = 1;
	boolean isTaskRun;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_attractioninfo_item);
		ViewGroup group = (ViewGroup) findViewById(R.id.viewGroup);
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		viewPager.findFocus();
		mapView = (MapView) findViewById(R.id.bmapView);
		scrollView = (ScrollView) findViewById(R.id.sv);
		listView = (ListView) findViewById(R.id.listView1);
		list = new ArrayList<Map<String, Object>>();
		list = TouListViewData.getData(this);
		adapter = new AttractionDetailAdapter(this, list);
		baiduMap = mapView.getMap();
		/**
		 * ���Ƕ����scrollview�е�mapview�޷���������
		 */
		view = mapView.getChildAt(0);
		view.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_UP) {
					scrollView.requestDisallowInterceptTouchEvent(false);
				} else {
					scrollView.requestDisallowInterceptTouchEvent(true);
				}
				return false;
			}
		});
		baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
		// �趨���ĵ�����
		LatLng point = new LatLng(28.883358, 121.174273);
		// �����ͼ״̬
		MapStatus mMapStatus = new MapStatus.Builder().target(point).zoom(19)
				.build();
		// ����MapStatusUpdate�����Ա�������ͼ״̬��Ҫ�����ı仯
		MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
				.newMapStatus(mMapStatus);
		// �ı��ͼ״̬
		baiduMap.setMapStatus(mMapStatusUpdate);
		listView.setAdapter(adapter);
		setListViewHeightBasedOnChildren(listView);
		// ����ͼƬ��ԴID
		imgIdArray = new int[] { R.drawable.item0, R.drawable.item1,
				R.drawable.item2, R.drawable.item3, R.drawable.item4,
				R.drawable.item5, R.drawable.item6, R.drawable.item7,
				R.drawable.item8 };

		// �������뵽ViewGroup��
		tips = new ImageView[imgIdArray.length];
		for (int i = 0; i < tips.length; i++) {
			ImageView imageView = new ImageView(this);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					15, 15);
			params.setMargins(3, 0, 3, 0);// ���ϣ��ң���
			imageView.setLayoutParams(params);
			imageView.setScaleType(ScaleType.FIT_XY);

			tips[i] = imageView;
			if (i == 0) {
				tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
			} else {
				tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
			}

			group.addView(imageView);
		}

		// ��ͼƬװ�ص�������
		mImageViews = new ImageView[imgIdArray.length];
		for (int i = 0; i < mImageViews.length; i++) {
			ImageView imageView = new ImageView(this);
			mImageViews[i] = imageView;
			imageView.setBackgroundResource(imgIdArray[i]);
		}

		// ����Adapter
		viewPager.setAdapter(new MyAdapter());
		// ���ü�������Ҫ�����õ��ı���
		viewPager.setOnPageChangeListener(this);
		// ����ViewPager��Ĭ����, ����Ϊ���ȵ�100���������ӿ�ʼ�������󻬶�
		viewPager.setCurrentItem((mImageViews.length) * 100);
	}

	/**
	 * ���scrollview��Ƕ��listviewֻ��ʾһ��item����
	 * 
	 * @param listView
	 */
	private void setListViewHeightBasedOnChildren(ListView listView) { // ��ȡListView��Ӧ��Adapter
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()�������������Ŀ
			View listItem = listAdapter.getView(i, null, listView);
			// ��������View �Ŀ��
			listItem.measure(0, 0);
			// ͳ������������ܸ߶�
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		// listView.getDividerHeight()��ȡ�����ָ���ռ�õĸ߶�
		// params.height���õ�����ListView������ʾ��Ҫ�ĸ߶�
		listView.setLayoutParams(params);
	}

	/**
	 * ������ʱ����
	 */
	private void startTask() {
		// TODO Auto-generated method stub
		isTaskRun = true;
		mTimer = new Timer();
		mTask = new TimerTask() {
			@Override
			public void run() {
				pageIndex++;
				mHandler.sendEmptyMessage(0);
			}
		};
		mTimer.schedule(mTask, 2 * 1000, 2 * 1000);// ���������Զ��л���ʱ�䣬��λ�Ǻ��룬2*1000��ʾ2��
	}

	// ����EmptyMessage(0)
	@SuppressLint("HandlerLeak")
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			setCurrentItem();
		}
	};

	/**
	 * ����Page���л��߼�
	 */
	private void setCurrentItem() {
		if (pageIndex == 0) {
			pageIndex = 1;
		} else if (pageIndex == imgIdArray.length) {
			pageIndex = 1;
		}
		viewPager.setCurrentItem(pageIndex, false);// ȡ������
	}

	/**
	 * ֹͣ��ʱ����
	 */
	private void stopTask() {
		// TODO Auto-generated method stub
		isTaskRun = false;
		mTimer.cancel();
	}

	@Override
	public void onResume() {
		super.onResume();
		setCurrentItem();
		startTask();
		mapView.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		stopTask();
		mapView.onPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mapView.onDestroy();
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

		// TODO Auto-generated method stub
		// System.out.println("state:" + state);
		if (arg0 == 0 && !isTaskRun) {
			setCurrentItem();
			startTask();
		} else if (arg0 == 1 && isTaskRun)
			stopTask();

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		setImageBackground(arg0 % mImageViews.length);
		pageIndex = arg0;
	}

	/**
	 * ����ѡ�е�tip�ı���
	 * 
	 * @param selectItems
	 */
	private void setImageBackground(int selectItems) {
		for (int i = 0; i < tips.length; i++) {
			if (i == selectItems) {
				tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
			} else {
				tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
			}
		}
	}

	public class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return Integer.MAX_VALUE;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager) container).removeView(mImageViews[position
					% mImageViews.length]);

		}

		/**
		 * ����ͼƬ��ȥ���õ�ǰ��position ���� ͼƬ���鳤��ȡ�����ǹؼ�
		 */
		@Override
		public Object instantiateItem(View container, int position) {
			((ViewPager) container).addView(mImageViews[position
					% mImageViews.length], 0);
			return mImageViews[position % mImageViews.length];
		}

	}
}
