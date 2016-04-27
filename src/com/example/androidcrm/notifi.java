package com.example.androidcrm;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class notifi extends ArrayAdapter<String> {
	private final Activity context;
	private final List<String> firname;
	private final List<String> lasname;
	

	public notifi(Activity context2, List<String> list,
			List<String> list12) {
		// TODO Auto-generated constructor stub
		super(context2, R.layout.fifth, list);
		this.context = context2;
		this.firname = list;
		this.lasname = list12;
		
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.abcd12, null, true);
		TextView txtTitle = (TextView) rowView.findViewById(R.id.date);
		
		TextView txtTitle2 = (TextView) rowView.findViewById(R.id.msg);
		

		txtTitle.setText("   "+firname.get(position));
		
		txtTitle2.setText("   "+lasname.get(position));
		
		return rowView;
	}
}
