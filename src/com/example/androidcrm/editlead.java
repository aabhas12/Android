package com.example.androidcrm;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class editlead extends Activity implements OnClickListener,
		OnItemSelectedListener {
	RadioGroup radioleadGroup;
	private static String URL = "http://192.168.164.50:8016/Service1.asmx";// address
	// SOAP Action URI again Namespace + Web method name
	private static String SOAP_ACTION = "http://tempuri.org/get_ratings";
	private static String SOAP_ACTION4 = "http://tempuri.org/getforeditlead";
	String webMethName = "get_ratings";
	private static String SOAP_ACTION1 = "http://tempuri.org/get_leadsource";
	String webMethName1 = "get_leadsource";
	private static String SOAP_ACTION2 = "http://tempuri.org/get_industry";
	String webMethName2 = "get_industry";
	int count, count1, count2;
	private static String NAMESPACE = "http://tempuri.org/";
	SoapObject responsebody;
	SoapObject tablerow = null;
	SoapObject table = null;
	List<String> list = new ArrayList<String>();
	SharedPreferences pref;
	Editor editor;
	int count5 = 0;
	String a, b, c, comname121, fname, lname, m, emailid1;
	List<String> industryname = new ArrayList<String>();
	List<Long> industryid = new ArrayList<Long>();
	List<String> leadsource = new ArrayList<String>();
	List<Long> ldsourceid = new ArrayList<Long>();
	List<String> ratings = new ArrayList<String>();
	List<Long> ratingsid = new ArrayList<Long>();
	RadioButton radioleadButton;
	EditText name, city, age;
	EditText companyname, firstname, lastname, email, phonenumber;
	long number1;
	Button save, cancel;
	String name1, city1;
	long raid, leadsrcid, indusid;
	String comname, firstnam, lastnam, emailid, rating;
	Spinner rating1, ldsource, industry;
	int age1;
	int pos;
	static int error = 0;
	int loginStatus;
	TextView tv;
	String number2;
	ProgressBar webservicePG1;
	static boolean errored = false;
	int uid, id;
  String token;
	public final Pattern EMAIL_ADDRESS_PATTERN = Pattern
			.compile("[a-zA-Z0-9+._%-+]{1,256}" + "@"
					+ "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" + "(" + "."
					+ "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" + ")+");

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editlead);
		System.out.println("here");
		setTitle("LEAD");
		Bundle extras = getIntent().getExtras();
		id = (int) extras.getLong("1");
		pref = getApplicationContext().getSharedPreferences("MyPref", 0);
		editor = pref.edit();
		save = (Button) findViewById(R.id.btnSubmit1);
		save.setOnClickListener(this);

		token = pref.getString("token", null);

		rating1 = (Spinner) findViewById(R.id.Rating);
		rating1.setOnItemSelectedListener(this);
		ldsource = (Spinner) findViewById(R.id.Leadsrc);
		ldsource.setOnItemSelectedListener(this);
		industry = (Spinner) findViewById(R.id.Industry);
		industry.setOnItemSelectedListener(this);
		companyname = (EditText) findViewById(R.id.comname1);
		firstname = (EditText) findViewById(R.id.Firstname);
		lastname = (EditText) findViewById(R.id.Lastname);
		phonenumber = (EditText) findViewById(R.id.phone);
		email = (EditText) findViewById(R.id.Email);
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
		Toast.makeText(getApplicationContext(), "Enter a Valid Phone Number", Toast.LENGTH_SHORT).show();
	}
	
	}
});
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
		if(!checkEmail(email.getText().toString()))
		{
			Toast.makeText(getApplicationContext(), "Enter a valid Email Address", Toast.LENGTH_SHORT).show();
		}
	}
});
		AsyncCallWS2 task1 = new AsyncCallWS2();
		// Call execute
		task1.execute();

		AsyncCallWS3 task2 = new AsyncCallWS3();
		// Call execute
		task2.execute();
		AsyncCallWS4 task3 = new AsyncCallWS4();
		// Call execute
		task3.execute();
		AsyncCallWS5 task4 = new AsyncCallWS5();
		// Call execute
		task4.execute();

	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Bundle b = new Bundle();
		b.putInt("a", 1);
		Intent i = new Intent(editlead.this, MainActivity.class);
		i.putExtras(b);
		startActivity(i);
		super.onBackPressed();
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		finish();
		super.onPause();
	}
	private void abc() {
		// TODO Auto-generated method stub
		System.out.println(comname121);
		companyname.setText(comname121);
		System.out.println(fname);

		firstname.setText(fname);
		System.out.println(lname);
		lastname.setText(lname);
		phonenumber.setText(m);
		email.setText(emailid1);
		for (int i = 0; i < ratings.size(); i++) {
			if (a.equals(ratings.get(i))) {
				pos = i;
			}
		}
		rating1.setSelection(pos);
		for (int i = 0; i < industryname.size(); i++) {
			if (c.equals(industryname.get(i))) {
				pos = i;
			}
		}
		industry.setSelection(pos);
		for (int i = 0; i < leadsource.size(); i++) {
			if (b.equals(leadsource.get(i))) {
				pos = i;
			}
		}
		ldsource.setSelection(pos);
	}

	private boolean checkEmail(String email) {
		return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.equals(save)) {

		
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
			number2 = phonenumber.getText().toString();
			// raid=(ratingsid.get(count2));

			raid = ratingsid.get(count2);
			// find the radiobutton by returned id

			if (checkEmail(emailid) && number2.length() == 10) {
				// Toast.makeText(lead.this,"Valid Email Addresss",
				// Toast.LENGTH_SHORT).show();
				AsyncCallWS1 task = new AsyncCallWS1();
				// Call execute
				task.execute();
			} else if (number2.length() != 10) {
				Toast.makeText(getApplicationContext(), "Invalid Phone Number",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(editlead.this, "Invalid Email Addresss",
						Toast.LENGTH_SHORT).show();

			}

			Toast.makeText(getApplicationContext(),
					firstnam + "" + lastnam + "" + indusid, Toast.LENGTH_SHORT)
					.show();

		} else if (v.equals(cancel)) {
			companyname.setText("");
			firstname.setText("");
			lastname.setText("");
			phonenumber.setText("");
			email.setText("");
		}
	}

	// 1,firstnam,(int)raid,(int)leadsrcid,(int)indusid,uid, "lead_new"
	private class AsyncCallWS1 extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			// Call Web Method
			loginStatus = WebService1.invokeLoginWS1(pref.getString("dbname", ""),id, comname, firstnam,
					lastnam, number2, emailid, (int) raid, (int) leadsrcid,
					(int) indusid,token, "updatelead");
			System.out.println("login status:" + loginStatus);
			return null;
		}

		@Override
		// Once WebService returns response
		protected void onPostExecute(Void result) {
			// Make Progress Bar invisible

			// Error status is false
			if (!errored) {
				// Based on Boolean value returned from WebService
				if (loginStatus == 1) {
					// Navigate to Home Screen
					
					Toast.makeText(getApplicationContext(), "Record Updated",
							Toast.LENGTH_SHORT).show();
					Bundle b = new Bundle();
					b.putInt("a", 1);
					Intent i = new Intent(editlead.this,MainActivity.class);
					i.putExtras(b);
					startActivity(i);

				} else {
					// Set Error message
					System.out.println("error");
				}
				// Error status is true
			} else {
				if (error == 1) {
					tv.setText("Error occured in invoking webservice1");
				} else if (error == 2) {
					tv.setText("Error occured in invoking webservice2");
					// Toast.makeText(getApplicationContext(),
					// WebService.loginStatus,Toast.LENGTH_SHORT).show();
				} else if (error == 3) {
					// Toast.makeText(getApplicationContext(),
					// WebService.loginStatus,Toast.LENGTH_SHORT).show();
					tv.setText("Error occured in invoking webservice3");
				} else {
					tv.setText("Error occured in invoking webservice");
				}
			}
			// Re-initialize Error Status to False
			errored = false;
			error = 0;
		}

		@Override
		// Make Progress Bar visible
		protected void onPreExecute() {

		}

		@Override
		protected void onProgressUpdate(Void... values) {
		}
	}

	private class AsyncCallWS5 extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			// Call Web Method
			System.out.println("inside function");
			SoapObject request = new SoapObject(NAMESPACE, "getforeditlead");
			request.addProperty("a", id);
			request.addProperty("db",pref.getString("dbname", ""));
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

				androidHttpTransport.call(SOAP_ACTION4, envelope);

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

				tablerow = (SoapObject) table.getProperty(0);
				comname121 = tablerow.getProperty("CompanyName").toString();
				System.out.println("inside:" + comname121);
				fname = tablerow.getProperty("FirstName").toString();
				System.out.println("fname:" + fname);
				lname = tablerow.getProperty("LastName").toString();
				emailid1 = tablerow.getProperty("EmailID").toString();
				if (emailid1.equals("anyType{}")) {
					emailid1 = "";
				}
				System.out.println("email:" + emailid1);
				m = tablerow.getProperty("Phone").toString();
				System.out.println(m);
				a = tablerow.getProperty("ratingName").toString();
				System.out.println(a);
				b = tablerow.getProperty("LeadSourceName").toString();
				c = tablerow.getProperty("IndustryName").toString();

				Toast.makeText(getApplicationContext(),
						"" + table.getPropertyCount(), Toast.LENGTH_SHORT)
						.show();
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
			count5++;
			// startActivity(i);
			if (count5 == 4) {
				abc();
			}
		}

		@Override
		// Make Progress Bar visible
		protected void onPreExecute() {

		}

		@Override
		protected void onProgressUpdate(Void... values) {

		}
	}

	private class AsyncCallWS2 extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			// Call Web Method

			SoapObject request = new SoapObject(NAMESPACE, "get_industry");
			request.addProperty("db",pref.getString("dbname", ""));
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

				androidHttpTransport.call(SOAP_ACTION2, envelope);

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
					industryname.add(tablerow.getProperty("Name").toString());
					industryid.add(Long.parseLong(tablerow.getProperty("ID")
							.toString()));
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
			count5++;
			if (count5 == 4) {
				abc();
			}
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

			for (int i = 0; i < industryname.size(); i++) {
				lables.add(industryname.get(i));
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
			industry.setAdapter(spinnerAdapter);
			
			count = industry.getSelectedItemPosition();
			indusid = industryid.get(count);

		}
	}

	private class AsyncCallWS3 extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			// Call Web Method

			SoapObject request = new SoapObject(NAMESPACE, "get_leadsource");
			request.addProperty("db",pref.getString("dbname", ""));
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

				androidHttpTransport.call(SOAP_ACTION1, envelope);

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
					leadsource.add(tablerow.getProperty("Name").toString());

					ldsourceid.add(Long.parseLong(tablerow.getProperty("ID")
							.toString()));
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
			count5++;
			if(count5 ==4)
			{
				abc();
			}
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

			for (int i = 0; i < leadsource.size(); i++) {
				lables.add(leadsource.get(i));
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
			ldsource.setAdapter(spinnerAdapter);
			Toast.makeText(
					getApplicationContext(),
					"On Button Click : " + "\n"
							+ String.valueOf(ldsource.getSelectedItem()),
					Toast.LENGTH_SHORT).show();
			count1 = ldsource.getSelectedItemPosition();
			leadsrcid = ldsourceid.get(count1);

		}
	}

	private class AsyncCallWS4 extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			// Call Web Method

			SoapObject request = new SoapObject(NAMESPACE, "get_ratings");
			request.addProperty("db",pref.getString("dbname", ""));
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
					ratings.add(tablerow.getProperty("Name").toString());
					ratingsid.add(Long.parseLong(tablerow.getProperty("ID")
							.toString()));
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
			count5++;
			if(count5 ==4)
			{
				abc();
			}
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

			for (int i = 0; i < ratings.size(); i++) {
				lables.add(ratings.get(i));
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
			rating1.setAdapter(spinnerAdapter);
			
			count2 = rating1.getSelectedItemPosition();

			// raid=(ratingsid.get(count2));

			raid = ratingsid.get(count2);

		}
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

}
