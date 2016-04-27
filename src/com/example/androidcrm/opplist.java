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

public class opplist extends ArrayAdapter<String> {
	private final Activity context;
	private final ArrayList<String> names;
	private final ArrayList<String> abc;
	private final ArrayList<Long> phnno;

	private final ArrayList<String> closeddate;

	public opplist(Activity context2, ArrayList<String> list,
			ArrayList<String> list12, ArrayList<Long> phone,
			ArrayList<String> closeddate) {
		// TODO Auto-generated constructor stub
		super(context2, R.layout.dispopp, list);
		this.context = context2;
		this.names = list;
		this.abc = list12;
		this.phnno = phone;
		this.closeddate = closeddate;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.dispopp, null, true);
		TextView txtTitle = (TextView) rowView.findViewById(R.id.oppname);
		TextView txtTitle4 = (TextView) rowView.findViewById(R.id.oppcld);
		TextView txtTitle2 = (TextView) rowView
				.findViewById(R.id.oppcustomername);
		TextView txtTitle3 = (TextView) rowView.findViewById(R.id.oppmobile);
ImageView img= (ImageView)rowView.findViewById(R.id.imageView3);
String a;
a=  names.get(position);
Random rand = new Random();
int r = rand.nextInt(6);

int[] colo= {0xff009900,0xff3366CC,0xff990000,0xffFF6600,0xff9900FF,0xffCC33CC};
	CharacterDrawable drawable = new CharacterDrawable(Character.toUpperCase(a.charAt(0)), colo[r]);
	img.setImageDrawable(drawable); 
		txtTitle.setText("  " + names.get(position));
		txtTitle2.setText("  " + abc.get(position));
		txtTitle3.setText("  " + phnno.get(position));
		txtTitle4.setText("  " + closeddate.get(position));
		return rowView;
	}
}
