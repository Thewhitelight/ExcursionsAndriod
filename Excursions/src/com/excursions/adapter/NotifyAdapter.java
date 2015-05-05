package com.excursions.adapter;

import java.util.List;
import java.util.Map;

import com.example.excursions.R;
import com.excursions.ViewHolder.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NotifyAdapter extends BaseAdapter {
	private List<Map<String, Object>> list;
	private LayoutInflater inflater;
	private TextView tv_title, tv_content, tv_time;

	public NotifyAdapter(Context context, List<Map<String, Object>> list) {
		// TODO Auto-generated constructor stub
		this.list = list;
		this.inflater = LayoutInflater.from(context);
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
			convertView = inflater.inflate(R.layout.layout_notify_item, parent,
					false);
		}
		tv_title = ViewHolder.get(convertView, R.id.tv_notify_title);
		tv_content = ViewHolder.get(convertView, R.id.tv_notify_content);
		tv_time = ViewHolder.get(convertView, R.id.tv_notify_time);
		tv_title.setText((String) list.get(position).get("title"));
		tv_content.setText((String) list.get(position).get("content"));
		tv_time.setText((String) list.get(position).get("time"));
		return convertView;
	}
}
