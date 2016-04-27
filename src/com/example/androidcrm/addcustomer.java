package com.example.androidcrm;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
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

public class addcustomer extends Activity implements OnClickListener,
		OnItemSelectedListener {
	RadioGroup radioleadGroup;
	private static String URL = "http://192.168.164.50:8016/Service1.asmx";// address
	// SOAP Action URI again Namespace + Web method name
	public static final String SOAP_ACTION = "http://tempuri.org/";

	private static String SOAP_ACTION1 = "http://tempuri.org/getinfocustomer";
	String webMethName1 = "getinfocustomer";
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

	List<String> industryname = new ArrayList<String>();
	List<Long> industryid = new ArrayList<Long>();

	List<String> customertype = new ArrayList<String>();
	List<Long> customertypeid = new ArrayList<Long>();
	RadioButton radioleadButton;
	EditText name, website, emailcust, mobilenumber;

	long number1;
	Button save;

	long custypeiid, indusid, custmobileno;
	String custname, custtype, custwebsite, custemail, custindustry, a;
	Spinner industry, customertypedisplay;
	int age1;
	static int error = 0;
	int loginStatus;
	TextView tv;
	String number2;
	ProgressBar webservicePG1;
	static boolean errored = false;
	int uid, id = 1;
	String token;
	public final Pattern EMAIL_ADDRESS_PATTERN = Pattern
			.compile("[a-zA-Z0-9+._%-+]{1,256}" + "@"
					+ "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" + "(" + "."
					+ "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" + ")+");
@Override
protected void onPause() {
	// TODO Auto-generated method stub
finish();
	super.onPause();
}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customer);
		pref = getApplicationContext().getSharedPreferences("MyPref", 0);
		editor = pref.edit();
		save = (Button) findViewById(R.id.btnSubmitcust);
		save.setOnClickListener(this);

		token = pref.getString("token", null);

		industry = (Spinner) findViewById(R.id.Industrycust);
		industry.setOnItemSelectedListener(this);
		customertypedisplay = (Spinner) findViewById(R.id.ctype);
		customertypedisplay.setOnItemSelectedListener(this);
		name = (EditText) findViewById(R.id.custname1);
		website = (EditText) findViewById(R.id.webcuscrm);
		website.addTextChangedListener(new TextWatcher() {
			
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
				if( !Patterns.WEB_URL.matcher(website.getText().toString()).matches())
				{
					Toast.makeText(getApplicationContext(), "Enter a Valid Website", Toast.LENGTH_SHORT).show();
				}
			}
		});
		emailcust =(EditText) findViewById(R.id.emailcust);
		emailcust.addTextChangedListener(new TextWatcher() {
			
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
			if(!checkEmail(emailcust.getText().toString()))
			{
				Toast.makeText(getApplicationContext(), "Enter a Valid Email Address", Toast.LENGTH_SHORT).show();
			}
			}
		});
		mobilenumber = (EditText) findViewById(R.id.mobilecustcrm);
		
mobilenumber.addTextChangedListener(new TextWatcher() {
	
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
		Toast.makeText(getApplicationContext(), "Enter a Valid Phonenumber", Toast.LENGTH_SHORT).show();
	}
	}
});
		AsyncCallWS2 task1 = new AsyncCallWS2();
		// Call execute
		task1.execute();
		AsyncCallWS3 task2 = new AsyncCallWS3();
		// Call execute
		task2.execute();

	}

	private boolean checkEmail(String email) {
		return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.equals(save)) {

			custname = name.getText().toString();
			custwebsite = website.getText().toString();
			custemail = emailcust.getText().toString();
			custmobileno = Long.parseLong(mobilenumber.getText().toString());
			custemail = emailcust.getText().toString();
			count1 = customertypedisplay.getSelectedItemPosition();
			custypeiid = customertypeid.get(count1);
			count = industry.getSelectedItemPosition();
			indusid = industryid.get(count);
			a = mobilenumber.getText().toString();
			// find the radiobutton by returned id

			if (checkEmail(custemail) && a.length() == 10 &&  Patterns.WEB_URL.matcher(custwebsite).matches()) {
				// Toast.makeText(lead.this,"Valid Email Addresss",
				// Toast.LENGTH_SHORT).show();
				AsyncCallWS1 task = new AsyncCallWS1();
				// Call execute
				task.execute();
			}else if(!Patterns.WEB_URL.matcher(custwebsite).matches())
			{
				Toast.makeText(getApplicationContext(), "Enter a Valid Website", Toast.LENGTH_SHORT).show();
			}
			else if (a.length() != 10) {
				Toast.makeText(getApplicationContext(), "Invalid Phone Number",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(addcustomer.this, "Invalid Email Addresss",
						Toast.LENGTH_SHORT).show();

			}

		}
	}

	// 1,firstnam,(int)raid,(int)leadsrcid,(int)indusid,uid, "lead_new"
	private class AsyncCallWS1 extends AsyncTask<String, Void, Void> {
		SoapObject result;
		@Override
		protected Void doInBackground(String... params) {
			// Call Web Method
			SoapObject request = new SoapObject(NAMESPACE,
					"storecustomers");
			request.addProperty("name", custname);
			request.addProperty("website", custwebsite);
			request.addProperty("phone", custmobileno);
			request.addProperty("db",pref.getString("dbname", ""));
			request.addProperty("email", custemail);

			request.addProperty("custtype", custypeiid);
			request.addProperty("indusid", indusid);
			request.addProperty("token", token);
			SoapSerializationEnvelope mySoapEnvelop = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			mySoapEnvelop.dotNet = true;
			mySoapEnvelop.setOutputSoapObject(request);
			HttpTransportSE myAndroidHttpTransport = null;
			try {
				try {
					myAndroidHttpTransport = new HttpTransportSE(URL);
					myAndroidHttpTransport.call(SOAP_ACTION
							+ "storecustomers", mySoapEnvelop);
				} catch (XmlPullParserException e) {
					// System.out.println(e.getClass());
					e.printStackTrace();
					// System.out.println("XmlPullParserException 0");
				} catch (SocketTimeoutException e) {
					// System.out.println(e.getClass());
					e.printStackTrace();
					// System.out.println("SocketTimeoutException 1");
				} catch (SocketException e) {
					// System.out.println(e.getClass());
					e.printStackTrace();
					// System.out.println("SocketException  2");
				} catch (IOException e) {
					// System.out.println(e.getClass());
					e.printStackTrace();
					System.out.println("IO Exception 3");
					// return objLoginBean;
				}
				//result = (SoapObject) mySoapEnvelop.bodyIn;
				SoapPrimitive response = (SoapPrimitive) mySoapEnvelop.getResponse();
				System.out.println("got response:");
				loginStatus = Integer.parseInt(response.toString());
			}

			catch (Exception e) {
				e.printStackTrace();
			}
			/*
			 * loginStatus = customerwebservice.invokeLoginWS12(custname,
			 * (int)custypeiid, custwebsite, custmobileno, custemail, (int)
			 * indusid,token, "storecustomers");
			 * System.out.println("login status:" + loginStatus);
			
			 */
			
			
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
					Toast.makeText(getApplicationContext(),
							"inserted customer", Toast.LENGTH_SHORT).show();
					Bundle b = new Bundle();
					b.putInt("a", 3);
					Intent i = new Intent(addcustomer.this, MainActivity.class);
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
			Toast.makeText(
					getApplicationContext(),
					"On Button Click : " + "\n"
							+ String.valueOf(industry.getSelectedItem()),
					Toast.LENGTH_SHORT).show();
			count = industry.getSelectedItemPosition();
			indusid = industryid.get(count);

		}
	}

	private class AsyncCallWS3 extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			// Call Web Method

			SoapObject request = new SoapObject(NAMESPACE, "getinfocustomer");
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
					customertype.add(tablerow.getProperty("Name").toString());

					customertypeid.add(Long.parseLong(tablerow
							.getProperty("ID").toString()));
				}
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

			for (int i = 0; i < customertype.size(); i++) {
				lables.add(customertype.get(i));
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
			customertypedisplay.setAdapter(spinnerAdapter);
			Toast.makeText(
					getApplicationContext(),
					"On Button Click : "
							+ "\n"
							+ String.valueOf(customertypedisplay
									.getSelectedItem()), Toast.LENGTH_SHORT)
					.show();

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
