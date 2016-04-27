package com.example.androidcrm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class third extends Activity {
	TextView name;
	TextView companyname;
	TextView phone;
	ImageView img;
	String name1;
	String comanme;
	String phon;
	int flag;
 String rating;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fifth);
		// Get the intent from ListViewAdapter
		Intent i = getIntent();
		// Get the results of rank
		name1 = i.getStringExtra("name");
		// Get the results of country
		comanme = i.getStringExtra("comname");
		// Get the results of population
		phon = i.getStringExtra("phone");
		// Get the results of flag
		rating = i.getStringExtra("rating");
 
		// Locate the TextViews in singleitemview.xml
		name = (TextView) findViewById(R.id.txt);
		companyname = (TextView) findViewById(R.id.phon);
		phone = (TextView) findViewById(R.id.age);
 
		// Locate the ImageView in singleitemview.xml
		img = (ImageView) findViewById(R.id.imageView1);
 
		// Load the results into the TextViews
		name.setText(name1);
		companyname.setText(comanme);
		phone.setText(phon);
		if (rating.equals("Hot (Probable conversion within 1 month)")) {
			CharacterDrawable drawable = new CharacterDrawable(Character.toUpperCase(name1.charAt(0)), 0xFFff4411);
			img.setImageDrawable(drawable); 
		} else if (rating.equals("Warm (Positive Interest)")) {
			CharacterDrawable drawable = new CharacterDrawable(Character.toUpperCase(name1.charAt(0)), 0xFFff9933);
			img.setImageDrawable(drawable); 
		} else if (rating.equals("Cold")) {
			CharacterDrawable drawable = new CharacterDrawable(Character.toUpperCase(name1.charAt(0)), 0xff0099FF);
			img.setImageDrawable(drawable); 
		}
		// Load the image into the ImageView
		
	}
}



