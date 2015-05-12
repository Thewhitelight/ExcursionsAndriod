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
import com.excursions.ViewHolder.BillListViewHolder;

/**
 * 账单列表
 * 
 * @ClassName: BillLvAdapter
 * @Description: TODO
 * @author SZQ
 * @date 2015年5月6日 下午8:44:21
 */
public class BillLvAdapter extends BaseAdapter {
	private List<Map<String, Object>> listItem;
	private LayoutInflater inflater;

	public BillLvAdapter(Context context, List<Map<String, Object>> listItem) {
		// TODO Auto-generated constructor stub
		this.listItem = listItem;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listItem.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listItem.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		BillListViewHolder holder;
		if (convertView == null) {
			holder = new BillListViewHolder();
			convertView = inflater.inflate(R.layout.layout_bill_item, parent,
					false);
			holder.tv_budget = (TextView) convertView
					.findViewById(R.id.tv_budget);
			holder.tv_tou_title = (TextView) convertView
					.findViewById(R.id.tv_tou_title);
			holder.tv_cur_funds = (TextView) convertView
					.findViewById(R.id.tv_cur_funds);
			convertView.setTag(holder);
		} else {
			holder = (BillListViewHolder) convertView.getTag();
		}
		holder.tv_budget.setText((String) listItem.get(position).get("budget")
				+ "元");
		holder.tv_tou_title.setText((String) listItem.get(position).get("att"));
		holder.tv_cur_funds.setText((String) listItem.get(position).get("fund")
				+ "元");
		return convertView;
	}

}
