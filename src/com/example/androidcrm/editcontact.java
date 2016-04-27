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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class editcontact extends Activity implements OnClickListener,
		OnItemSelectedListener {
	RadioGroup radioleadGroup;
	private ArrayAdapter<String> adapter;
	private static String URL = "http://192.168.164.50:8016/Service1.asmx";// address
	// SOAP Action URI again Namespace + Web method name
	private static String SOAP_ACTION = "http://tempuri.org/getinfocontactsforedit";

	String webMethName = "getinfocontactsforedit";
	private static String SOAP_ACTION1 = "http://tempuri.org/getcustomer";
	String webMethName1 = "getcustomer";
	ArrayList<String> concustomername = new ArrayList<String>();
	ArrayList<Long> concustomerid = new ArrayList<Long>();
	ArrayList<Long> concompanyid = new ArrayList<Long>();
	int count, count1, count2;
	private static String NAMESPACE = "http://tempuri.org/";
	SoapObject responsebody;
	SoapObject tablerow = null;
	SoapObject table = null;
	List<String> list = new ArrayList<String>();
	SharedPreferences pref;
	Editor editor;
	long webid;
	int count5 = 0;
	int pos = 0;
	long id, cid, id1;
	AutoCompleteTextView textView = null;
	String finame, laname, cusname, mobno3, email3;
	Button save, cancel;
	String name1, city1;
	static int error = 0;
	int loginStatus;
	TextView tv;
	String number2;
	ProgressBar webservicePG1;
	static boolean errored = false;
	int uid;

	String token;
	String fname, lname, mobno, emailid, customername1;
	Spinner customername;
	EditText fname1, lname1, mobno1, emailid1;
	public final Pattern EMAIL_ADDRESS_PATTERN = Pattern
			.compile("[a-zA-Z0-9+._%-+]{1,256}" + "@"
					+ "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" + "(" + "."
					+ "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" + ")+");

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact);
		System.out.println("here");
		Bundle extras = getIntent().getExtras();
		id1 = (int) extras.getLong("1");
		pref = getApplicationContext().getSharedPreferences("MyPref", 0);
		editor = pref.edit();

		save = (Button) findViewById(R.id.btnSubmitcon);
		save.setOnClickListener(this);
		token = pref.getString("token", null);
		textView = (AutoCompleteTextView) findViewById(R.id.Custnamecon);
		// customername = (Spinner) findViewById(R.id.Custnamecon);
		// customername.setOnItemSelectedListener(this);
		fname1 = (EditText) findViewById(R.id.Firstnamecon);
		lname1 = (EditText) findViewById(R.id.Lastnamecon);
		mobno1 = (EditText) findViewById(R.id.phonecon);
		mobno1.addTextChangedListener(new TextWatcher() {
			
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
				Toast.makeText(getApplicationContext(), "Enter a Valid PhoneNumber", Toast.LENGTH_SHORT).show();
			}
			}
		});
		emailid1 = (EditText) findViewById(R.id.Emailcon);
		emailid1.addTextChangedListener(new TextWatcher() {
			
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
			if(!checkEmail(emailid1.getText().toString()))
			{
				Toast.makeText(getApplicationContext(), "Enter a Valid Email Address",Toast.LENGTH_SHORT).show();
			}
			}
		});
		AsyncCallWS2 task1 = new AsyncCallWS2();
		// Call execute
		task1.execute();
		AsyncCallWS5 task2 = new AsyncCallWS5();
		// Call execute
		task2.execute();

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Bundle b = new Bundle();
		b.putInt("a", 2);

		Intent i = new Intent(editcontact.this, MainActivity.class);
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

		fname1.setText(finame);
		System.out.println("name:" + finame);
		lname1.setText(laname);
		mobno1.setText(mobno3);
		emailid1.setText(email3);

		for (int i = 0; i < concustomerid.size(); i++) {
			if (webid == concustomerid.get(i)) {
				pos = i;
			}
		}

		textView.setText(concustomername.get(pos));
	}

	private boolean checkEmail(String email) {
		return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.equals(save)) {

			fname = fname1.getText().toString();

			lname = lname1.getText().toString();
			mobno = mobno1.getText().toString();
			emailid = emailid1.getText().toString();

			customername1 = textView.getText().toString();
			int flag=0;
			for (int i = 0; i < concustomername.size(); i++) {
				if (customername1.equals(concustomername.get(i))) {
					count = i;
					flag=1;
					break;
				} else {
					flag=0;
					continue;
				}
			}
			if(flag==1)
			{
			id = concustomerid.get(count);
			}
			else
			{
				Toast.makeText(getApplicationContext(), "Enter proper customer name", Toast.LENGTH_SHORT).show();
			}
			if (checkEmail(emailid) && mobno.length() == 10 && flag==1) {
				// Toast.makeText(lead.this,"Valid Email Addresss",
				// Toast.LENGTH_SHORT).show()
				AsyncCallWS1 task = new AsyncCallWS1();
				// Call execute
				task.execute();
			} else if (mobno.length() != 10) {
				Toast.makeText(getApplicationContext(), "Invalid Phone Number",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getApplicationContext(),
						"Invalid Email Addresss", Toast.LENGTH_SHORT).show();

			}

		}
	}

	// 1,firstnam,(int)raid,(int)leadsrcid,(int)indusid,uid, "lead_new"
	private class AsyncCallWS1 extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			// Call Web Method
			loginStatus = storeconWebService.invokeLoginWS1((int) id1,
					(int) id, fname, lname, mobno, emailid, token,	pref.getString("dbname", ""),
					"storeupdatedcontacts");
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
					b.putInt("a", 2);
					Intent i = new Intent(editcontact.this, MainActivity.class);
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
			SoapObject request = new SoapObject(NAMESPACE,
					"getinfocontactsforedit");
			request.addProperty("a", id1);
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

				tablerow = (SoapObject) table.getProperty(0);

				finame = tablerow.getProperty("FirstName").toString();
				System.out.println("firstname:" + finame);
				laname = tablerow.getProperty("LastName").toString();
				cusname = tablerow.getProperty("AccountName").toString();
				mobno3 = tablerow.getProperty("Phone").toString();
				email3 = tablerow.getProperty("Email").toString();
				webid = Long.parseLong(tablerow.getProperty("AccountID")
						.toString());
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
			if (count5 == 2) {
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

			SoapObject request = new SoapObject(NAMESPACE, "getcustomer");
			// request.addProperty("a", id);
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
					concustomername.add(tablerow.getProperty("AccountName")
							.toString());
					concustomerid.add(Long.parseLong(tablerow.getProperty("ID")
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
			count5++;
			if (count5 == 2) {
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
			List<Long> lables1 = new ArrayList<Long>();

			for (int i = 0; i < concustomername.size(); i++) {
				lables.add(concustomername.get(i));
				lables1.add(concustomerid.get(i));

			}

			/** Setting the itemclick event listener */
			// autoComplete.setOnItemClickListener(itemClickListener);

			/** Setting the adapter to the listView */
			adapter = new ArrayAdapter<String>(getApplicationContext(),
					R.layout.spinneritem, lables);
			adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
			adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
			textView.setThreshold(1);

			// Set adapter to AutoCompleteTextView
			textView.setAdapter(adapter);
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
