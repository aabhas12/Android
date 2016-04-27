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

public class discontacts extends ArrayAdapter<String> {
	private final Activity context;
	private final ArrayList<String> firname;
	private final ArrayList<String> lasname;
	private final ArrayList<String> companyname;
	private final ArrayList<String> phone;
    private final ArrayList<String> email;
	public discontacts(Activity context2, ArrayList<String> list,
			ArrayList<String> list12, ArrayList<String> companyname,
			ArrayList<String> phone,ArrayList<String> email) {
		// TODO Auto-generated constructor stub
		super(context2, R.layout.conta, list);
		this.context = context2;
		this.firname = list;
		this.lasname = list12;
		this.companyname =companyname ;
		this.phone =phone;
		this.email=email;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.conta, null, true);
		TextView txtTitle = (TextView) rowView.findViewById(R.id.name21);
	ImageView img = (ImageView)rowView.findViewById(R.id.imageView4);
		TextView txtTitle2 = (TextView) rowView.findViewById(R.id.cname);
		TextView txtTitle3 = (TextView) rowView.findViewById(R.id.mob1);
		TextView txtTitle4 = (TextView) rowView.findViewById(R.id.email);
		txtTitle.setText("   "+firname.get(position)+" "+lasname.get(position));
		String a;
		 a=  firname.get(position);
		 Random rand = new Random();
		 int r = rand.nextInt(6);
		
		 int[] colo= {0xff009900,0xff3366CC,0xff990000,0xffFF6600,0xff9900FF,0xffCC33CC};
			CharacterDrawable drawable = new CharacterDrawable(Character.toUpperCase(a.charAt(0)), colo[r]);
			img.setImageDrawable(drawable); 
		txtTitle2.setText("   "+companyname.get(position));
		txtTitle3.setText("   "+phone.get(position));
		txtTitle4.setText("   "+email.get(position));
		return rowView;
	}
}
