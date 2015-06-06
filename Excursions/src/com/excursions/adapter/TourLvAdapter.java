package com.excursions.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.excursions.R;
import com.excursions.ViewHolder.TouListViewHolder;
import com.excursions.bean.TourMain;
import com.excursions.ui.customview.EmoticonsTextView;

/**
 * 社区主页列表
 * 
 * @ClassName: TourLvAdapter
 * @Description: TODO
 * @author SZQ
 * @date 2015年5月6日 下午8:50:36
 */
public class TourLvAdapter extends BaseAdapter {
	private List<TourMain> list;
	private LayoutInflater inflater;

	public TourLvAdapter(Context context, List<TourMain> list) {
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
			convertView = inflater.inflate(R.layout.layout_tour_item, parent,
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
		holder.tv_title.setText(list.get(position).getTitle());
		holder.tv_content.setText(list.get(position).getContent());
		holder.tv_var_man.setText(list.get(position).getUserId());
		holder.tv_var_time.setText(list.get(position).getCreateTime());
		holder.tv_comment_num.setText(list.get(position).getPraiseNum());
		return convertView;
	}

}
