package com.excursions.ViewHolder;

import android.util.SparseArray;
import android.view.View;

/**
 * viewHolder简化写法
 * 
 * @ClassName: ViewHolder
 * @Description: TODO
 * @author SZQ
 * @date 2015年5月20日 下午9:23:19
 */
public class ViewHolder {
	@SuppressWarnings("unchecked")
	public static <T extends View> T get(View view, int id) {
		SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
		if (viewHolder == null) {
			viewHolder = new SparseArray<View>();
			view.setTag(viewHolder);
		}
		View childView = viewHolder.get(id);
		if (childView == null) {
			childView = view.findViewById(id);
			viewHolder.put(id, childView);
		}
		return (T) childView;
	}
}
