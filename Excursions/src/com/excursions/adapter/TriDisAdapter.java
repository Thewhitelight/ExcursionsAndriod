package com.excursions.adapter;

import java.util.List;
import java.util.Map;

import com.example.excursions.R;
import com.excursions.ViewHolder.TouListViewHolder;
import com.excursions.ui.customview.EmoticonsTextView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class TriDisAdapter extends BaseAdapter {
	private List<Map<String, Object>> list;
	private LayoutInflater inflater;

	public TriDisAdapter(Context context, List<Map<String, Object>> list) {
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
		TouListViewHolder holder;
		if (convertView == null) {
			holder = new TouListViewHolder();
			convertView = inflater.inflate(R.layout.layout_tridis_item, parent,
					false);
			holder.tv_title = (TextView) convertView
					.findViewById(R.id.tv_tou_title);
			holder.tv_content = (EmoticonsTextView) convertView
					.findViewById(R.id.tv_tou_content);
			holder.tv_var_man = (TextView) convertView
					.findViewById(R.id.tv_var_man);
			holder.tv_var_time = (TextView) convertView
					.findViewById(R.id.tv_var_time);
			holder.ck_good = (CheckBox) convertView.findViewById(R.id.ck_good);
			holder.tv_comment_num = (TextView) convertView
					.findViewById(R.id.tv_comment_num);
			convertView.setTag(holder);
		} else {
			holder = (TouListViewHolder) convertView.getTag();
		}
		holder.tv_title.setText((String) list.get(position).get("title"));
		holder.tv_content.setText((String) list.get(position).get("content"));
		holder.tv_var_man.setText((String) list.get(position).get("man"));
		holder.tv_var_time.setText((String) list.get(position).get("time"));
		holder.tv_comment_num
				.setText((String) list.get(position).get("number"));
		return convertView;
	}

}
