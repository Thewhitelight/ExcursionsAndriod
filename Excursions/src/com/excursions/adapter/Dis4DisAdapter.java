package com.excursions.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.excursions.R;
import com.excursions.ViewHolder.ViewHolder;
import com.excursions.ui.customview.EmoticonsTextView;

public class Dis4DisAdapter extends BaseAdapter {
	private List<Map<String, Object>> list;
	private LayoutInflater inflater;
	// private Context context;
	private EmoticonsTextView emo;
	private TextView tv_dis_name, tv_dis_time;

	public Dis4DisAdapter(Context context, List<Map<String, Object>> list) {
		// TODO Auto-generated constructor stub
		// this.context = context;
		this.list = list;
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
			convertView = inflater.inflate(R.layout.layout_dis4dis, parent,
					false);
		}
		emo = ViewHolder.get(convertView, R.id.dis_comment);
		tv_dis_name = ViewHolder.get(convertView, R.id.tv_name_dis);
		tv_dis_time = ViewHolder.get(convertView, R.id.tv_time_dis);
		emo.setText((String) list.get(position).get("comment0"));
		tv_dis_name.setText((String) list.get(position).get("name0"));
		tv_dis_time.setText((String) list.get(position).get("time0"));
		return convertView;
	}

}
