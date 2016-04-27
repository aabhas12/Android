package com.example.androidcrm;



import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class loginmain extends Activity implements OnClickListener, OnItemSelectedListener {
	// Set Error Status
	private static String URL = "http://192.168.164.50:8016/Service1.asmx";
	private static String SOAP_ACTION = "http://tempuri.org/getdbname";
	String webMethName = "getdbname";
	private static String NAMESPACE = "http://tempuri.org/";
	static boolean errored = false;
	List<String> dbname= new ArrayList<String>();
	protected LocationManager locationManager;
	protected LocationListener locationListener;
	float lati,longi;
	Button b;
	SoapObject responsebody;
	SoapObject tablerow = null;
	SoapObject table = null;
	static int error = 0;
	TextView statusTV;
	EditText userNameET, passWordET;
	ProgressBar webservicePG;
	String editTextUsername;
	String ma_a;
	Spinner s;
	SharedPreferences pref;
	Editor editor;
	int ip;
	String loginStatus;
	String editTextPassword;
   Spinner sp;
   String abc;
   String dbname1;
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
	finish();
		super.onPause();
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
	System.exit(0);
		super.onBackPressed();
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main);
		
		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		LocationListener ll = new mylocationlistener();
		lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, ll);
		
		pref = getApplicationContext().getSharedPreferences("MyPref", 0);
		editor = pref.edit();
		// Name Text control
		s = (Spinner)findViewById(R.id.dbname);
		s.setOnItemSelectedListener(this);
		userNameET = (EditText) findViewById(R.id.usname1);
		passWordET = (EditText) findViewById(R.id.passwordq);
		
		// Display Text control
		//String mac = pref.getString("MAC", null);

		userNameET.addTextChangedListener(new TextWatcher() {
			
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
				abc = s.toString();
				AsyncCallWS4 task1 = new AsyncCallWS4();
				// Call execute
				task1.execute();
			}
		});
		statusTV = (TextView) findViewById(R.id.tv_result);
		// Button to trigger web service invocation
		b = (Button) findViewById(R.id.button1);
		// Display progress bar until web service invocation completes
		webservicePG = (ProgressBar) findViewById(R.id.progressBar1);
		// Button Click Listener
		WifiManager wiman = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		ma_a = wiman.getConnectionInfo().getMacAddress();
		ip= wiman.getConnectionInfo().getIpAddress();
		
		
		b.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// Check if text controls are not empty
				if (userNameET.getText().length() != 0
						&& userNameET.getText().toString() != "") {
					if (passWordET.getText().length() != 0
							&& passWordET.getText().toString() != "") {
						editTextUsername = userNameET.getText().toString();
						editTextPassword = passWordET.getText().toString();
						int count = s.getSelectedItemPosition();
						dbname1=dbname.get(count);
						
						statusTV.setText("");
						// Create instance for AsyncCallWS
						AsyncCallWS task = new AsyncCallWS();
						// Call execute
						task.execute();
					}
					// If Password text control is empty
					else {
						statusTV.setText("Please enter Password");
					}
					// If Username text control is empty
				} else {
					statusTV.setText("Please enter Username");
				}
			}
		});
	}
	private class mylocationlistener implements LocationListener {
		private double temp;

		@SuppressLint("NewApi")
		public void onLocationChanged(Location location) {
			if (location != null) {
				lati= (float) (location.getLatitude());
				longi = (float) (location.getLongitude());
			

			}

		}

		@Override
		public void onProviderDisabled(String provider) {
			Toast.makeText(loginmain.this, "Error onProviderDisabled",
					Toast.LENGTH_LONG).show();

		}

		@Override
		public void onProviderEnabled(String provider) {
			Toast.makeText(loginmain.this, "onProviderEnabled",
					Toast.LENGTH_LONG).show();
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			Toast.makeText(loginmain.this, "onStatusChanged",
					Toast.LENGTH_LONG).show();
		}
	}

	private class AsyncCallWS extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			// Call Web Method
			loginStatus = WebServicelogin.invokeLoginWS12(editTextUsername,
					editTextPassword,dbname1,ma_a,ip,(long)lati,(long)longi, "login");
			return null;//lati,longi,
		}

		@Override
		// Once WebService returns response
		protected void onPostExecute(Void result) {
			// Make Progress Bar invisible
			webservicePG.setVisibility(View.INVISIBLE);
			Bundle b = new Bundle();
			b.putInt("a", 0);
		Intent intObj = new Intent(loginmain.this, MainActivity.class);
			
		// Error status is false
			if (!errored) {
				// Based on Boolean value returned from WebService
				if (loginStatus!=null) {
					// Navigate to Home Screen
					Toast.makeText(getApplicationContext(), ""+loginStatus,
							Toast.LENGTH_LONG).show();
					editor.putString("token",loginStatus);
					editor.putString("dbname", dbname1);
                   editor.putString("username", userNameET.getText().toString());
					editor.commit();
		intObj.putExtras(b);
					startActivity(intObj);
				} else {
					// Set Error message
					Toast.makeText(getApplicationContext(),
							"" + WebService.loginStatus, Toast.LENGTH_LONG)
							.show();
					statusTV.setText("Login Failed, try again");
				}
				// Error status is true
			} else {
				if (error == 1) {
					statusTV.setText("Error occured in invoking webservice1");
				} else if (error == 2) {
					statusTV.setText("Error occured in invoking webservice2");
					// Toast.makeText(getApplicationContext(),
					// WebService.loginStatus,Toast.LENGTH_LONG).show();
				} else if (error == 3) {
					// Toast.makeText(getApplicationContext(),
					// WebService.loginStatus,Toast.LENGTH_LONG).show();
					statusTV.setText("Error occured in invoking webservice3");
				} else {
					statusTV.setText("Error occured in invoking webservice");
				}
			}
			// Re-initialize Error Status to False
			errored = false;
			error = 0;
		}
		private ProgressDialog progress;
		@Override
		// Make Progress Bar visible
		protected void onPreExecute() {
		/*	progress = new ProgressDialog(loginmain.this);

			progress.setMessage("Please wait...");
			progress.setCancelable(false);
			progress.show();
	*/
		}

		@Override
		protected void onProgressUpdate(Void... values) {
		}
	}
	private class AsyncCallWS4 extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			// Call Web Method
dbname.clear();
			SoapObject request = new SoapObject(NAMESPACE, "getdbname");
			request.addProperty("username", abc);
			// Create envelope
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.dotNet = true;
			// Set output SOAP object
			envelope.implicitTypes = true;
			envelope.setOutputSoapObject(request);
			// Create HTTP call object
			envelope.bodyOut = request;

			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

			try {
				// Invoke web service

				androidHttpTransport.call(SOAP_ACTION, envelope);

			} catch (Exception e) {
				String a = e.toString();

				// Toast.makeText(this,a, Toast.LENGTH_SHORT).show();
				// Assign Error Status true in static variable 'errored'
				
				e.printStackTrace();
			}
			try {
				// Toast.makeText(this,"inside try", Toast.LENGTH_SHORT).show();

				// Invoke web servi
				responsebody = (SoapObject) envelope.getResponse();
				// Toast.makeText(this,""+responsebody,
				// Toast.LENGTH_SHORT).show();
				// Toast.makeText(this,"inside try", Toast.LENGTH_SHORT).show();
				responsebody = (SoapObject) responsebody.getProperty(1);

				table = (SoapObject) responsebody.getProperty(0);

				// tablerow = (SoapObject) table.getProperty(0);
				// Toast.makeText(this,"inside try12",
				// Toast.LENGTH_SHORT).show();

				for (int i = 0; i < table.getPropertyCount(); ++i) {
					tablerow = (SoapObject) table.getProperty(i);
					dbname.add(tablerow.getProperty("DBName").toString());
				
				}
				
				// loginStatus =Boolean.parseBoolean(response.toString());

			} catch (Exception e) {
				String a = e.toString();

				// Toast.makeText(this,a, Toast.LENGTH_SHORT).show();
				// Assign Error Status true in static variable 'errored'
				
				e.printStackTrace();
			}
			return null;
		}

		// Once WebService returns response
		@Override
		protected void onPostExecute(Void result) {
			populateSpinner();
			// startActivity(i);
			
		}

		@Override
		// Make Progress Bar visible
		protected void onPreExecute() {

		}

		@Override
		protected void onProgressUpdate(Void... values) {

		}

		private void populateSpinner() {
			List<String> lables = new ArrayList<String>();

			for (int i = 0; i < dbname.size(); i++) {
				lables.add(dbname.get(i));
			}

			// Creating adapter for spinner
			ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(
					getApplicationContext(), R.layout.spinneritem, lables);

			// Drop down layout style - list view with radio button
			// spinnerAdapter
			// .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinnerAdapter
					.setDropDownViewResource(R.layout.spinner_dropdown_item);
			// attaching data adapter to spinner
			s.setAdapter(spinnerAdapter);
			
			

		}


	

}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}

}