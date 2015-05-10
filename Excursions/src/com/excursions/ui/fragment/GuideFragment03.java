package com.excursions.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.excursions.R;
import com.excursions.ui.activity.MainActivity;

public class GuideFragment03 extends BaseFragment {
	private View view;
	private ImageView img_guide;
	private Button btn_start;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_guide, container, false);
		img_guide = (ImageView) view.findViewById(R.id.img_guide);
		img_guide.setBackgroundResource(R.drawable.guide3);
		btn_start = (Button) view.findViewById(R.id.btn_start);
		btn_start.setText("进入应用");
		btn_start.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getActivity(), MainActivity.class));
				getActivity().finish();
			}
		});
		return view;
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
}
