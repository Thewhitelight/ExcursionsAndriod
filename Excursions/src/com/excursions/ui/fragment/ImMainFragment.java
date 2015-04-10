package com.excursions.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.excursions.R;

public class ImMainFragment extends BaseFragment {
	private View view;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_im_main, container, false);
		setHasOptionsMenu(true);
		init();
		return view;
	}

	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.add_user) {

			return true;
		}
		if (id == R.id.location) {

			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {

		menu.clear();

		MenuInflater inflater = getActivity().getMenuInflater();
		inflater.inflate(R.menu.im_menu, menu);
		super.onPrepareOptionsMenu(menu);
	}
}
