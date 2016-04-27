package com.example.androidcrm;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Offlinelead extends Activity implements OnClickListener {
	// Set Error Status
	public final Pattern EMAIL_ADDRESS_PATTERN = Pattern
			.compile("[a-zA-Z0-9+._%-+]{1,256}" + "@"
					+ "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" + "(" + "."
					+ "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" + ")+");
	Button b;
	int count, count1, count2;
	long number1;
	SQLiteDatabase db, db1, db2, db4;
	List<String> industryname = new ArrayList<String>();
	List<Long> industryid = new ArrayList<Long>();
	List<String> leadsource = new ArrayList<String>();
	List<Long> ldsourceid = new ArrayList<Long>();
	List<String> ratings = new ArrayList<String>();
	List<Long> ratingsid = new ArrayList<Long>();
	EditText companyname, firstname, lastname, email, phonenumber;
	String comname, firstnam, lastnam, emailid, rating;
	Spinner rating1, ldsource, industry;
	Button save;
	long raid, leadsrcid, indusid;

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent i = new Intent(Offlinelead.this, offlinemain.class);
		startActivity(i);
		super.onBackPressed();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		finish();
		super.onPause();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editlead);
		companyname = (EditText) findViewById(R.id.comname1);
		firstname = (EditText) findViewById(R.id.Firstname);
		lastname = (EditText) findViewById(R.id.Lastname);
		phonenumber = (EditText) findViewById(R.id.phone);
		phonenumber.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
			if(s.length()!=10)
			{
				Toast.makeText(getApplicationContext(), "Enter a Valid Phone Number", Toast.LENGTH_LONG).show();
			}
			}
		});
		email = (EditText) findViewById(R.id.Email);
		email.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
			if(checkEmail(email.getText().toString()))
			{
				Toast.makeText(getApplicationContext(), "Enter a Valid Email Address", Toast.LENGTH_LONG).show();
			}
			}
		});
		rating1 = (Spinner) findViewById(R.id.Rating);
		ldsource = (Spinner) findViewById(R.id.Leadsrc);
		industry = (Spinner) findViewById(R.id.Industry);
		b = (Button) findViewById(R.id.btnSubmit1);
		b.setOnClickListener(this);
		db = openOrCreateDatabase("Industry", Context.MODE_PRIVATE, null);
		db.execSQL("CREATE TABLE IF NOT EXISTS industry(id Integer,name VARCHAR);");
		Cursor c = db.rawQuery("SELECT * FROM industry", null);
		while (c.moveToNext()) {
			industryname.add(c.getString(1));
			industryid.add((long) c.getInt(0));

		}
		db1 = openOrCreateDatabase("Leadsource", Context.MODE_PRIVATE, null);
		db1.execSQL("CREATE TABLE IF NOT EXISTS leadsource(id Integer,name VARCHAR);");
		Cursor c1 = db1.rawQuery("SELECT * FROM leadsource", null);
		while (c1.moveToNext()) {
			leadsource.add(c1.getString(1));
			ldsourceid.add((long) c1.getInt(0));

		}
		db2 = openOrCreateDatabase("Rating", Context.MODE_PRIVATE, null);
		db2.execSQL("CREATE TABLE IF NOT EXISTS rating4(id Integer,name VARCHAR);");
		Cursor c2 = db2.rawQuery("SELECT * FROM rating4", null);
		while (c2.moveToNext()) {
			ratings.add(c2.getString(1));
			ratingsid.add((long) c2.getInt(0));

		}
		populatespinner();
	}

	private boolean checkEmail(String email) {
		return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.equals(b)) {
			comname = companyname.getText().toString();
			firstnam = firstname.getText().toString();
			lastnam = lastname.getText().toString();
			number1 = Long.parseLong(phonenumber.getText().toString());
			emailid = email.getText().toString();
			count1 = ldsource.getSelectedItemPosition();
			leadsrcid = ldsourceid.get(count1);
			count = industry.getSelectedItemPosition();
			indusid = industryid.get(count);
			count2 = rating1.getSelectedItemPosition();
			String number2 = phonenumber.getText().toString();
			// raid=(ratingsid.get(count2));

			raid = ratingsid.get(count2);
			// find the radiobutton by returned id

			if (checkEmail(emailid) && number2.length() == 10) {
				db4 = openOrCreateDatabase("Lead", Context.MODE_PRIVATE, null);
				db4.execSQL("CREATE TABLE IF NOT EXISTS lead1(id INTEGER PRIMARY KEY AUTOINCREMENT,companyname VARCHAR,firstname VARCHAR,lastname Varchar,mobilenumber VARCHAR,"
						+ "email Varchar,Ratingid integer,ldsourceid integer,industrid integer);");
				db4.execSQL("INSERT INTO lead1(companyname,firstname,lastname,mobilenumber,email,Ratingid,ldsourceid ,industrid) VALUES('"
						+ comname
						+ "','"
						+ firstnam
						+ "','"
						+ lastnam
						+ "','"
						+ number1
						+ "','"
						+ emailid
						+ "',"
						+ raid
						+ ","
						+ leadsrcid + "," + indusid + ");");
				Intent i = new Intent(Offlinelead.this, main.class);
				startActivity(i);
			} else if (number2.length() != 10) {
				Toast.makeText(getApplicationContext(), "Invalid Phone Number",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getApplicationContext(),
						"Invalid Email Addresss", Toast.LENGTH_SHORT).show();

			}
		}

	}

	void populatespinner() {
		List<String> lables = new ArrayList<String>();
		List<String> lables1 = new ArrayList<String>();
		List<String> lables2 = new ArrayList<String>();
		for (int i = 0; i < ratings.size(); i++) {
			lables.add(ratings.get(i));
		}
		for (int i = 0; i < industryname.size(); i++) {
			lables1.add(industryname.get(i));
		}
		for (int i = 0; i < leadsource.size(); i++) {
			lables2.add(leadsource.get(i));
		}

		// Creating adapter for spinner
		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(
				getApplicationContext(), R.layout.spinneritem, lables);
		ArrayAdapter<String> spinnerAdapter1 = new ArrayAdapter<String>(
				getApplicationContext(), R.layout.spinneritem, lables1);
		ArrayAdapter<String> spinnerAdapter2 = new ArrayAdapter<String>(
				getApplicationContext(), R.layout.spinneritem, lables2);
		// Drop down layout style - list view with radio button
		// spinnerAdapter
		// .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
		spinnerAdapter1.setDropDownViewResource(R.layout.spinner_dropdown_item);
		spinnerAdapter2.setDropDownViewResource(R.layout.spinner_dropdown_item);
		// attaching data adapter to spinner
		rating1.setAdapter(spinnerAdapter);
		industry.setAdapter(spinnerAdapter1);
		ldsource.setAdapter(spinnerAdapter2);
	}
}
