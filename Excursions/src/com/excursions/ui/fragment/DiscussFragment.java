package com.excursions.ui.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.excursions.R;
import com.excursions.adapter.TriDisAdapter;
import com.excursions.data.DiscussData;

public class DiscussFragment extends BaseFragment {
	private View view;
	private ListView listView;
	private TriDisAdapter adapter;
	private List<Map<String, Object>> list;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_discuss, container, false);
		init();
		return view;
	}

	private void init() {
		// TODO Auto-generated method stub
		listView = (ListView) view.findViewById(R.id.lv_discuss);
		list = new ArrayList<Map<String, Object>>();
		list = DiscussData.getData(getActivity());
		adapter = new TriDisAdapter(getActivity(), list);
		listView.setAdapter(adapter);
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
}
