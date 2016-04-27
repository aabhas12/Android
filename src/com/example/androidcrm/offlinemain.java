package com.example.androidcrm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class offlinemain extends Activity implements OnClickListener {
	// Set Error Status
	Boolean isInternetPresent = false;
	long raid, leadsrcid, indusid;
	String comname, firstnam, lastnam, emailid, rating;
	// Connection detector class
	ConnectionDetector cd;
	Button b, b2;
	TextView tv, tv1, tv2, tv3, tv4;
	SharedPreferences pref;
	Editor editor;

	String token;
	static boolean errored = false;
	static int error = 0;
	int loginStatus;
	long number2;
	String status;
	int id, count, count1 = 0;
	SQLiteDatabase db, db1;
	int accountcount = 0, leadcount = 0, contactscount = 0, oppcount = 0,
			offlineleadcount = 0;

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		System.exit(0);
		super.onBackPressed();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.offlinelead);

		setTitle("OFFLINE MODE");

		tv4 = (TextView) findViewById(R.id.offlead1);
		pref = getApplicationContext().getSharedPreferences("MyPref", 0);
		editor = pref.edit();

		b = (Button) findViewById(R.id.button11223);
		// b2 = (Button) findViewById(R.id.refresh1);
		token = pref.getString("token", null);
		cd = new ConnectionDetector(this);
		isInternetPresent = cd.isConnectingToInternet();
		if (!isInternetPresent) {
			// Internet Connection is Present
			// make HTTP requests
			
			b.setOnClickListener(this);
		
			Toast.makeText(getApplicationContext(), "internet not present",
					Toast.LENGTH_LONG).show();
			db = openOrCreateDatabase("Lead", Context.MODE_PRIVATE, null);
			db.execSQL("CREATE TABLE IF NOT EXISTS lead1(id INTEGER PRIMARY KEY   AUTOINCREMENT,companyname VARCHAR,firstname VARCHAR,lastname Varchar,mobilenumber VARCHAR,email Varchar,Ratingid integer,ldsourceid integer,industrid integer);");
			Cursor c = db.rawQuery("SELECT * FROM lead1", null);
			offlineleadcount = c.getCount();

			tv4.setText("Offline Leads:" + offlineleadcount);

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main1, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title

		switch (item.getItemId()) {
		case R.id.refresh:
			Intent i = new Intent(offlinemain.this, main.class);
			startActivity(i);
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if (arg0.equals(b) && !isInternetPresent) {
			Intent i = new Intent(offlinemain.this, Offlinelead.class);
			startActivity(i);
		}

	}

}