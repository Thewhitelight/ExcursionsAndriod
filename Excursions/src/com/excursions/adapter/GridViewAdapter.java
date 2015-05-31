package com.excursions.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.excursions.R;
import com.excursions.ViewHolder.GridViewHolder;
import com.excursions.bean.Attractions;
import com.excursions.utils.ImageLoadOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class GridViewAdapter extends BaseAdapter {
	private List<Attractions> list;
	private LayoutInflater inflater;

	public GridViewAdapter(Context context, List<Attractions> list) {
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
		GridViewHolder holder = null;
		if (convertView == null) {
			holder = new GridViewHolder();
			convertView = inflater.inflate(R.layout.layout_grid_item, parent,
					false);
			holder.img_gd_item = (ImageView) convertView
					.findViewById(R.id.img_grid);
			holder.tv_gd_item = (TextView) convertView
					.findViewById(R.id.tv_grid);
			convertView.setTag(holder);
		} else {
			holder = (GridViewHolder) convertView.getTag();
		}
		ImageLoader.getInstance().displayImage(list.get(position).getSsImage(),
				holder.img_gd_item, ImageLoadOptions.getOptions());
		holder.tv_gd_item.setText(list.get(position).getName());
		return convertView;
	}

}
