package com.excursions.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.excursions.R;
import com.excursions.ViewHolder.ViewHolder;
import com.excursions.data.TourDetailData;
import com.excursions.ui.customview.EmoticonsTextView;
import com.excursions.ui.customview.RoundImageView;

public class TourDetailAdapter extends BaseAdapter {
	private List<Map<String, Object>> list, dislist;
	private LayoutInflater inflater;
	private RoundImageView roundimg;
	private ImageView imageSingle;
	private ListView lv_dis4dis;
	private Context context;
	private Dis4DisAdapter adapter;
	private TextView name, time, layer, commentMoreCount;
	private EmoticonsTextView emo;

	public TourDetailAdapter(Context context, List<Map<String, Object>> list) {
		// TODO Auto-generated constructor stub
		this.list = list;
		this.context = context;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.layout_tour_detail_item,
					parent, false);
		}
		name = ViewHolder.get(convertView, R.id.tour_name);
		time = ViewHolder.get(convertView, R.id.time);
		emo = ViewHolder.get(convertView, R.id.comment);
		layer = ViewHolder.get(convertView, R.id.layer);
		imageSingle = ViewHolder.get(convertView, R.id.imageSingle);
		commentMoreCount = ViewHolder.get(convertView, R.id.commentMoreCount);
		roundimg = ViewHolder.get(convertView, R.id.roundimg);
		roundimg.setBackgroundResource(R.drawable.default_head);
		layer.setText((String) list.get(position).get("layer"));
		name.setText((String) list.get(position).get("name"));
		time.setText((String) list.get(position).get("time"));
		emo.setText((String) list.get(position).get("comment"));
		imageSingle.setBackgroundResource(R.drawable.item0);
		String str = context.getResources().getString(R.string.morediscuss);
		String discussnum = String.format(str, 20);
		commentMoreCount.setText(discussnum);
		lv_dis4dis = ViewHolder.get(convertView, R.id.lv_dis4dis);
		dislist = new ArrayList<Map<String, Object>>();
		dislist = TourDetailData.getData(context);
		adapter = new Dis4DisAdapter(context, dislist);
		lv_dis4dis.setAdapter(adapter);
		setListViewHeightBasedOnChildren(lv_dis4dis);
		return convertView;
	}

	public void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

}
