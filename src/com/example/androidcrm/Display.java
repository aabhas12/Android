package com.example.androidcrm;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Display extends ArrayAdapter<String> {
	private final Activity context;
	private final ArrayList<String> firname;
	private final ArrayList<String> lasname;
	private final ArrayList<String> companyname;
	private final ArrayList<String> phone;

	public Display(Activity context2, ArrayList<String> list,
			ArrayList<String> list12, ArrayList<String> companyname,
			ArrayList<String> phone) {
		// TODO Auto-generated constructor stub
		super(context2, R.layout.fifth, list);
		this.context = context2;
		this.firname = list;
		this.lasname = list12;
		this.companyname =companyname ;
		this.phone =phone;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.fifth, null, true);
		TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
		
		TextView txtTitle2 = (TextView) rowView.findViewById(R.id.phon);
		TextView txtTitle3 = (TextView) rowView.findViewById(R.id.age);

		txtTitle.setText("   "+firname.get(position)+" "+lasname.get(position));
		
		txtTitle2.setText("   "+companyname.get(position));
		txtTitle3.setText("   "+phone.get(position));
		return rowView;
	}
}
