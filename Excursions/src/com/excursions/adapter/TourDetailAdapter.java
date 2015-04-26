package com.excursions.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.excursions.R;
import com.excursions.ViewHolder.ViewHolder;
import com.excursions.ui.customview.EmoticonsTextView;
import com.excursions.ui.customview.RoundImageView;

public class TourDetailAdapter extends BaseAdapter {
	private List<Map<String, Object>> list;
	private LayoutInflater inflater;
	private RoundImageView roundimg;
	private EmoticonsTextView comment, comment0, comment1, comment2, comment3,
			comment4;
	private TextView layer, name, name0, name1, name2, name3, name4, time,
			time0, time1, time2, time3, time4;
	private ImageView imageSingle;

	public TourDetailAdapter(Context context, List<Map<String, Object>> list) {
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
			convertView = inflater.inflate(R.layout.layout_tour_detail_item,
					parent, false);
		}
		comment = ViewHolder.get(convertView, R.id.comment);
		comment0 = ViewHolder.get(convertView, R.id.comment0);
		comment1 = ViewHolder.get(convertView, R.id.comment1);
		comment2 = ViewHolder.get(convertView, R.id.comment2);
		comment3 = ViewHolder.get(convertView, R.id.comment3);
		comment4 = ViewHolder.get(convertView, R.id.comment4);
		layer = ViewHolder.get(convertView, R.id.layer);
		name = ViewHolder.get(convertView, R.id.name);
		name0 = ViewHolder.get(convertView, R.id.name0);
		name1 = ViewHolder.get(convertView, R.id.name1);
		name2 = ViewHolder.get(convertView, R.id.name2);
		name3 = ViewHolder.get(convertView, R.id.name3);
		name4 = ViewHolder.get(convertView, R.id.name4);
		time = ViewHolder.get(convertView, R.id.time);
		time0 = ViewHolder.get(convertView, R.id.time0);
		time1 = ViewHolder.get(convertView, R.id.time1);
		time2 = ViewHolder.get(convertView, R.id.time2);
		time3 = ViewHolder.get(convertView, R.id.time3);
		time4 = ViewHolder.get(convertView, R.id.time4);
		imageSingle = ViewHolder.get(convertView, R.id.imageSingle);
		roundimg = ViewHolder.get(convertView, R.id.roundimg);
		roundimg.setBackgroundResource(R.drawable.default_head);
		name.setText((String) list.get(position).get("name"));
		layer.setText((String) list.get(position).get("layer"));
		time.setText((String) list.get(position).get("time"));
		name0.setText((String) list.get(position).get("name0"));
		name1.setText((String) list.get(position).get("name1"));
		name2.setText((String) list.get(position).get("name2"));
		name3.setText((String) list.get(position).get("name3"));
		name4.setText((String) list.get(position).get("name4"));
		time0.setText((String) list.get(position).get("time0"));
		time1.setText((String) list.get(position).get("time1"));
		time2.setText((String) list.get(position).get("time2"));
		time3.setText((String) list.get(position).get("time3"));
		time4.setText((String) list.get(position).get("time4"));
		imageSingle.setImageResource(R.drawable.item0);
		comment.setText((String) list.get(position).get("comment"));
		comment0.setText((String) list.get(position).get("comment0"));
		comment1.setText((String) list.get(position).get("comment1"));
		comment2.setText((String) list.get(position).get("comment2"));
		comment3.setText((String) list.get(position).get("comment3"));
		comment4.setText((String) list.get(position).get("comment4"));
		return convertView;
	}

}
