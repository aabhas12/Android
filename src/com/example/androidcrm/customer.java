package com.example.androidcrm;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class customer extends ArrayAdapter<String> {
	private final Activity context;
	private final ArrayList<String> name;
	private final ArrayList<String> type;
	private final ArrayList<String> website;
	private final ArrayList<String> email;


	public customer(Activity context2, ArrayList<String> list,
			ArrayList<String> list12, ArrayList<String> companyname,
			ArrayList<String> phone) {
		// TODO Auto-generated constructor stub
		super(context2, R.layout.sixth, list);
		this.context = context2;
		this.name = list;
		this.type = list12;
		this.website= companyname;
		this.email = phone;
		
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.sixth, null, true);
		TextView txtTitle = (TextView) rowView.findViewById(R.id.cutomername1);
		System.out.println(name.get(position));
		TextView txtTitle2 = (TextView) rowView.findViewById(R.id.customertype);
		ImageView img = (ImageView) rowView.findViewById(R.id.imageView2);
		String a;
		 a=  name.get(position);
		 Random rand = new Random();
		 int r = rand.nextInt(6);
		
		 int[] colo= {0xff009900,0xff3366CC,0xff990000,0xffFF6600,0xff9900FF,0xffCC33CC};
			CharacterDrawable drawable = new CharacterDrawable(Character.toUpperCase(a.charAt(0)), colo[r]);
			img.setImageDrawable(drawable); 
			
			
		TextView txtTitle3 = (TextView) rowView.findViewById(R.id.customerwebsite);
		TextView txtTitle4 = (TextView) rowView.findViewById(R.id.customeremail);
			
		
		txtTitle.setText("   " + name.get(position));

		txtTitle2.setText("   " + type.get(position));
		txtTitle3.setText("   " + website.get(position));
		txtTitle4.setText("   " + email.get(position));
		return rowView;
	}
}
