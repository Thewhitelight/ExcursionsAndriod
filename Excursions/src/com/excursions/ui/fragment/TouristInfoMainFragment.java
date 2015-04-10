package com.excursions.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.excursions.R;

public class TouristInfoMainFragment extends BaseFragment {
	private View view;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_tourisinfo_main, container,
				false);

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
		if (id == R.id.menu) {
			return true;
		}
		if (id == R.id.all) {

			return true;
		}
		if (id == R.id.friends) {

			return true;
		}
		if (id == R.id.org) {

			return true;
		}
		if (id == R.id.market) {

			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {

		menu.clear();

		MenuInflater inflater = getActivity().getMenuInflater();
		inflater.inflate(R.menu.tou_menu, menu);
		super.onPrepareOptionsMenu(menu);
	}
}
