package com.example.androidcrm;



import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class main extends Activity {
	// Set Error Status
	ConnectionDetector cd;
	Boolean isInternetPresent = false;
	Button b;

	SharedPreferences pref;
	Editor editor;

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checking);
		pref = getApplicationContext().getSharedPreferences("MyPref", 0);
		editor = pref.edit();
		// Intent i = new Intent(main.this,login.class);
		// startActivity(i);
		String token = pref.getString("token", null);
		cd = new ConnectionDetector(main.this);
		isInternetPresent = cd.isConnectingToInternet();
	System.out.println("Token:"+token);
		if (isInternetPresent) {
		
			if (token == null) {
				Intent i = new Intent(main.this, loginmain.class);
				startActivity(i);
			} else {
			
				Bundle b = new Bundle();
				b.putInt("a", 0);
				Intent i = new Intent(main.this, MainActivity.class);
				i.putExtras(b);
				startActivity(i);
			}
		} else {
			if (token == null) {
				Toast.makeText(getApplicationContext(), "NO INTERNET",
						Toast.LENGTH_LONG).show();
				Intent i = new Intent(main.this, loginmain.class);
				startActivity(i);
			} else {
				Intent i = new Intent(main.this, offlinemain.class);
				startActivity(i);
			}
		}
	}

}
