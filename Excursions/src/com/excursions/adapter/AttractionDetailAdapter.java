package com.excursions.adapter;

import java.util.List;
import java.util.Map;

import com.example.excursions.R;
import com.excursions.ViewHolder.ViewHolder;
import com.excursions.ui.customview.EmoticonsTextView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AttractionDetailAdapter extends BaseAdapter {
	private List<Map<String, Object>> list;
	private LayoutInflater inflater;
	private TextView tv_title, tv_var_man, tv_var_time, tv_var_num;
	private EmoticonsTextView etv_comment;

	public AttractionDetailAdapter(Context context,
			List<Map<String, Object>> list) {
		// TODO Auto-generated constructor stub
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
			convertView = inflater.inflate(R.layout.layout_tou_item, parent,
					false);
		}
		tv_title = ViewHolder.get(convertView, R.id.tv_tou_title);
		etv_comment = ViewHolder.get(convertView, R.id.tv_tou_content);
		tv_var_man = ViewHolder.get(convertView, R.id.tv_var_man);
		tv_var_time = ViewHolder.get(convertView, R.id.tv_var_time);
		tv_var_num = ViewHolder.get(convertView, R.id.tv_comment_num);
		tv_title.setText((String) list.get(position).get("title"));
		etv_comment.setText((String) list.get(position).get("content"));
		tv_var_man.setText((String) list.get(position).get("man"));
		tv_var_time.setText((String) list.get(position).get("time"));
		tv_var_num.setText((String) list.get(position).get("number"));
		return convertView;
	}

}
