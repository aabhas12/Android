package com.example.androidcrm;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class editopp extends Activity implements OnClickListener,
		OnItemClickListener, OnItemSelectedListener {
	private Calendar cal;
	private int day;
	private int month;
	int amount;
	private ArrayAdapter<String> adapter;
	long customerid, stageid, typeid, companyid, ldsrcid;
	private int year;
	private static String URL = "http://192.168.164.50:8016/Service1.asmx";// address
	// SOAP Action URI again Namespace + Web method name
	private static String SOAP_ACTION = "http://tempuri.org/getcustomer";
	String webMethName = "getcustomer";
	private static String SOAP_ACTION1 = "http://tempuri.org/getstage";
	String webMethName1 = "getstage";
	private static String SOAP_ACTION2 = "http://tempuri.org/gettype";
	String webMethName2 = "gettype";
	private static String SOAP_ACTION3 = "http://tempuri.org/get_leadsource";
	String webMethName3 = "get_leadsource";
	private static String SOAP_ACTION4 = "http://tempuri.org/getoppedit";

	int count, count1, count2, count3;
	private static String NAMESPACE = "http://tempuri.org/";
	SoapObject responsebody, responsebody1, responsebody2, responsebody3;
	SoapObject tablerow = null, tablerow1 = null, tablerow2 = null,
			tablerow3 = null;
	SoapObject table = null, table1 = null, table2 = null, table3 = null;
	AutoCompleteTextView textView = null;
	SharedPreferences pref;
	Editor editor;
	Calendar c;

	private DatePickerDialog dialog = null;
	ArrayList<String> OppCustomerName = new ArrayList<String>();
	List<Long> OppCustomerID = new ArrayList<Long>();
	List<String> OppstageName = new ArrayList<String>();
	List<Long> OppStageID = new ArrayList<Long>();
	ArrayList<Long> OppCompanyID = new ArrayList<Long>();
	List<String> OpptypeName = new ArrayList<String>();
	List<Long> OpptypeID = new ArrayList<Long>();
	List<String> OppleadsourceName = new ArrayList<String>();
	List<Long> OppleadsourceID = new ArrayList<Long>();
	int count5 = 0;
	String name, cldate1;
	long amt;
	long lds;
	long t;
	long st;
	long cust;
	Button save, cancel;
	EditText Amount;
	long raid, leadsrcid, indusid;
	EditText oppname;
	TextView closedate;
	int c1, c2, c3, c4;
	long customername12, stage12, type12, leadsource12;
	String oppname12, cldate, Customername;
	Spinner customername, stage, type, Leadsource;
	int age1;

	static int error = 0;
	int loginStatus;
	TextView tv;
	String number2;
	ProgressBar webservicePG1;
	static boolean errored = false;
	int uid, id = 1;
	String token;
	int pos = 0;

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Bundle b = new Bundle();
		b.putInt("a", 4);
		Intent i = new Intent(editopp.this, MainActivity.class);
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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.opportunities);
		ActionBar actionBar = getActionBar();

		// Enabling Up / Back navigation
		actionBar.setDisplayHomeAsUpEnabled(true);
		Bundle extras = getIntent().getExtras();
		id = (int) extras.getLong("a1");
		System.out.println("id:" + id);
		pref = getApplicationContext().getSharedPreferences("MyPref", 0);
		editor = pref.edit();
		save = (Button) findViewById(R.id.btnSubmit);
		save.setOnClickListener(this);
		token = pref.getString("token", null);
		oppname = (EditText) findViewById(R.id.oppname1);
		closedate = (TextView) findViewById(R.id.closedate);
		textView = (AutoCompleteTextView) findViewById(R.id.customername);
		// customername = (Spinner) findViewById(R.id.customername);
		// customername.setOnItemSelectedListener(this);
		stage = (Spinner) findViewById(R.id.stagecrm);
		stage.setOnItemSelectedListener(this);
		type = (Spinner) findViewById(R.id.typecrm);
		type.setOnItemSelectedListener(this);
		Leadsource = (Spinner) findViewById(R.id.ldsrc);
		Leadsource.setOnItemSelectedListener(this);
		Amount = (EditText) findViewById(R.id.amt);

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
		AsyncCallWS6 task5 = new AsyncCallWS6();
		// Call execute
		task5.execute();
		textView.setOnItemSelectedListener(this);
		textView.setOnItemClickListener(this);
	}

	private void abc() {
		// TODO Auto-generated method stub
		System.out.println("inside abc");
		System.out.println(name);
		oppname.setText(name);
		// System.out.println(fname);
		Amount.setText("" + (int) amt);
		if (cldate1.equals("01/01/1900")) {
			closedate.setText("");
			closedate.setOnClickListener(this);
		} else {
			closedate.setText(cldate1);
		}
		// System.out.println(lname);
		int flag = 0;
		for (int i = 0; i < OppCustomerID.size(); i++) {
			if (cust == OppCustomerID.get(i)) {
				flag = 1;
				pos = i;
				break;
			} else {
				flag = 0;
				continue;
			}
		}
		if (flag == 1) {
			textView.setText(OppCustomerName.get(pos));
		} else {
			Toast.makeText(getApplicationContext(),
					"Enter Correct Customer Name", Toast.LENGTH_SHORT).show();
		}

		for (int i = 0; i < OppstageName.size(); i++) {

			if (st == OppStageID.get(i)) {
				pos = i;
			}
		}
		stage.setSelection(pos);
		for (int i = 0; i < OpptypeName.size(); i++) {
			if (t == OpptypeID.get(i)) {
				pos = i;
			}
		}
		type.setSelection(pos);
		for (int i = 0; i < OppleadsourceName.size(); i++) {
			if (lds == OppleadsourceID.get(i)) {
				pos = i;
			}
		}
		Leadsource.setSelection(pos);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.equals(save)) {
			String avc;
			oppname12 = oppname.getText().toString();
			avc = textView.getText().toString();
			System.out.println("sdasd:" + OppCustomerID.size());
			int flag = 0;
			for (int i = 0; i < OppCustomerID.size(); i++) {
				if (avc.equals(OppCustomerName.get(i))) {
					flag = 1;
					pos = i;
					break;
				} else {
					flag = 0;
					continue;
				}
			}

			System.out.println("" + OppCustomerID.get(pos));
			if (flag == 1) {
				customername12 = OppCustomerID.get(pos);
			} else {
				Toast.makeText(getApplicationContext(),
						"enter proper customername", Toast.LENGTH_SHORT).show();
			}
			c2 = stage.getSelectedItemPosition();
			stage12 = OppStageID.get(c2);
			c3 = type.getSelectedItemPosition();
			type12 = OpptypeID.get(c3);
			c4 = Leadsource.getSelectedItemPosition();
			leadsource12 = OppleadsourceID.get(c4);

			amount = Integer.parseInt(Amount.getText().toString());
			cldate = closedate.getText().toString();
			if (cldate.equals("")) {
				cldate = "01/01/1900";
			}
			if (flag == 1) {
				AsyncCallWS1 task = new AsyncCallWS1();
				task.execute();
			}
		}
		// find the radiobutton by returned id
		/*
		 * if (checkEmail(emailid) && number2.length() == 10) { //
		 * Toast.makeText(lead.this,"Valid Email Addresss", //
		 * Toast.LENGTH_SHORT).show(); AsyncCallWS1 task = new AsyncCallWS1();
		 * // Call execute task.execute(); } else if (number2.length() != 10) {
		 * Toast.makeText(getApplicationContext(), "Invalid Phone Number",
		 * Toast.LENGTH_SHORT).show(); } else { Toast.makeText(oppstore.this,
		 * "Invalid Email Addresss", Toast.LENGTH_SHORT).show();
		 * 
		 * }
		 */
		else if (v.equals(closedate)) {
			cal = Calendar.getInstance();
			day = cal.get(Calendar.DAY_OF_MONTH);
			month = cal.get(Calendar.MONTH);
			year = cal.get(Calendar.YEAR);

			showDialog(0);
		}

	}

	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id) {
		return new DatePickerDialog(this, datePickerListener, year, month, day);
	}

	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			closedate.setText(selectedDay + " / " + (selectedMonth + 1) + " / "
					+ selectedYear);
		}
	};

	// 1,firstnam,(int)raid,(int)leadsrcid,(int)indusid,uid, "lead_new"
	private class AsyncCallWS1 extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			// Call Web Method

			loginStatus = oppeditwebservice.invokeLoginWS1(id, oppname12,
					(int) customername12, (int) stage12, (int) type12,
					(int) amount, (int) leadsource12, token, cldate,pref.getString("dbname", ""),
					"storeupdatedopp");
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
					Bundle b = new Bundle();
					b.putInt("a", 4);
					Toast.makeText(getApplicationContext(), "updated opp",
							Toast.LENGTH_SHORT).show();
					Intent i = new Intent(editopp.this, MainActivity.class);
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

		private ProgressDialog progress;

		@Override
		// Make Progress Bar visible
		protected void onPreExecute() {
			progress = new ProgressDialog(editopp.this);

			progress.setMessage("Please wait...");
			progress.setCancelable(false);
			progress.show();
		}

		@Override
		protected void onProgressUpdate(Void... values) {
		}
	}

	private class AsyncCallWS6 extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			// Call Web Method
			System.out.println("inside function");
			SoapObject request = new SoapObject(NAMESPACE, "getoppedit");
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
				name = tablerow.getProperty("OpportunityName").toString();
				System.out.println("inside:" + name);

				if (tablerow.getProperty("CloseDate").toString().equals("")) {
					cldate1 = "";
				} else {
					cldate1 = tablerow.getProperty("CloseDate").toString();
				}
				System.out.println("fname:" + cldate1);
				cust = Long.parseLong(tablerow.getProperty("AccountID")
						.toString());
				/*
				 * emailid1 = tablerow.getProperty("EmailID").toString(); if
				 * (emailid1.equals("anyType{}")) { emailid1 = ""; }
				 */
				// System.out.println("email:" + emailid1);
				st = Long.parseLong(tablerow.getProperty("StageID").toString());
				System.out.println(st);
				t = Long.parseLong(tablerow.getProperty("OpportunityTypeID")
						.toString());
				System.out.println(t);
				lds = Long.parseLong(tablerow.getProperty("LeadSourceID")
						.toString());
				System.out.println(tablerow.getProperty("Amount").toString());
				amt = (long) Double.parseDouble(tablerow.getProperty("Amount")
						.toString());
				System.out.println(amt);
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
			if (count5 == 5) {
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
				System.out.println("table result1:" + table.toString());
				for (int i = 0; i < table.getPropertyCount(); ++i) {
					tablerow = (SoapObject) table.getProperty(i);
					OppCustomerName.add(tablerow.getProperty("AccountName")
							.toString());
					System.out.println("adasdasd:" + OppCustomerName);
					OppCustomerID.add(Long.parseLong(tablerow.getProperty("ID")
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
			if (count5 == 5) {
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
			for (int i = 0; i < OppCustomerName.size(); i++) {
				lables.add(OppCustomerName.get(i));
			}
			adapter = new ArrayAdapter<String>(getApplicationContext(),
					R.layout.spinneritem, lables);
			adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
			adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
			textView.setThreshold(1);

			// Set adapter to AutoCompleteTextView
			textView.setAdapter(adapter);
		}
	}

	private class AsyncCallWS3 extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			// Call Web Method

			SoapObject request1 = new SoapObject(NAMESPACE, "getstage");
			request1.addProperty("db",pref.getString("dbname", ""));
			// Create envelope
			SoapSerializationEnvelope envelope1 = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope1.dotNet = true;
			// Set output SOAP object
			envelope1.implicitTypes = true;
			envelope1.setOutputSoapObject(request1);
			// Create HTTP call object
			envelope1.bodyOut = request1;
			HttpTransportSE androidHttpTransport1 = new HttpTransportSE(URL);

			try {
				// Invoke web service

				androidHttpTransport1.call(SOAP_ACTION1, envelope1);

			} catch (Exception e) {
				String a = e.toString();

				// Toast.makeText(this,a, Toast.LENGTH_SHORT).show();
				// Assign Error Status true in static variable 'errored'

				e.printStackTrace();
			}
			try {
				// Toast.makeText(this,"inside try", Toast.LENGTH_SHORT).show();

				// Invoke web servi
				responsebody1 = (SoapObject) envelope1.getResponse();

				// Toast.makeText(this,""+responsebody,
				// Toast.LENGTH_SHORT).show();
				// Toast.makeText(this,"inside try", Toast.LENGTH_SHORT).show();
				responsebody1 = (SoapObject) responsebody1.getProperty(1);

				table1 = (SoapObject) responsebody1.getProperty(0);
				// tablerow = (SoapObject) table.getProperty(0);
				// Toast.makeText(this,"inside try12",
				// Toast.LENGTH_SHORT).show();
				System.out.println("table result1:" + table1.toString());
				for (int i = 0; i < table1.getPropertyCount(); ++i) {
					tablerow1 = (SoapObject) table1.getProperty(i);
					OppstageName.add(tablerow1.getProperty("Name").toString());
					OppStageID.add(Long.parseLong(tablerow1.getProperty("ID")
							.toString()));
				}

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
			if (count5 == 5) {
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

			List<String> lables1 = new ArrayList<String>();
			for (int i = 0; i < OppstageName.size(); i++) {
				lables1.add(OppstageName.get(i));
			}
			// Creating adapter for spinner
			ArrayAdapter<String> spinnerAdapter1 = new ArrayAdapter<String>(
					getApplicationContext(), R.layout.spinneritem, lables1);
			// Drop down layout style - list view with radio button
			// spinnerAdapter
			// .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinnerAdapter1
					.setDropDownViewResource(R.layout.spinner_dropdown_item);
			// attaching data adapter to spinner
			stage.setAdapter(spinnerAdapter1);
			Toast.makeText(
					getApplicationContext(),
					"On Button Click : " + "\n"
							+ String.valueOf(stage.getSelectedItem()),
					Toast.LENGTH_SHORT).show();
			count1 = stage.getSelectedItemPosition();
			stageid = OppStageID.get(count1);

		}
	}

	private class AsyncCallWS4 extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			// Call Web Method

			SoapObject request2 = new SoapObject(NAMESPACE, "gettype");
			request2.addProperty("db",pref.getString("dbname", ""));
			// Create envelope
			SoapSerializationEnvelope envelope2 = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);

			envelope2.dotNet = true;
			// Set output SOAP object
			envelope2.implicitTypes = true;
			envelope2.setOutputSoapObject(request2);
			// Create HTTP call object
			envelope2.bodyOut = request2;

			HttpTransportSE androidHttpTransport2 = new HttpTransportSE(URL);

			try {
				// Invoke web service

				androidHttpTransport2.call(SOAP_ACTION2, envelope2);

			} catch (Exception e) {
				String a = e.toString();

				// Toast.makeText(this,a, Toast.LENGTH_SHORT).show();
				// Assign Error Status true in static variable 'errored'

				e.printStackTrace();
			}
			try {
				// Toast.makeText(this,"inside try", Toast.LENGTH_SHORT).show();

				// Invoke web servi

				responsebody2 = (SoapObject) envelope2.getResponse();
				// Toast.makeText(this,""+responsebody,
				// Toast.LENGTH_SHORT).show();
				// Toast.makeText(this,"inside try", Toast.LENGTH_SHORT).show();
				// responsebody = (SoapObject) responsebody.getProperty(1);
				responsebody2 = (SoapObject) responsebody2.getProperty(1);
				table2 = (SoapObject) responsebody2.getProperty(0);
				// tablerow = (SoapObject) table.getProperty(0);
				// Toast.makeText(this,"inside try12",
				// Toast.LENGTH_SHORT).show();

				for (int i = 0; i < table2.getPropertyCount(); ++i) {
					tablerow2 = (SoapObject) table2.getProperty(i);
					OpptypeName.add(tablerow2.getProperty("Name").toString());
					OpptypeID.add(Long.parseLong(tablerow2.getProperty("ID")
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
			if (count5 == 5) {
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

			List<String> lables2 = new ArrayList<String>();

			for (int i = 0; i < OpptypeName.size(); i++) {
				lables2.add(OpptypeName.get(i));
			}
			ArrayAdapter<String> spinnerAdapter2 = new ArrayAdapter<String>(
					getApplicationContext(), R.layout.spinneritem, lables2);
			// Drop down layout style - list view with radio button
			// spinnerAdapter
			// .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinnerAdapter2
					.setDropDownViewResource(R.layout.spinner_dropdown_item);
			// attaching data adapter to spinner
			type.setAdapter(spinnerAdapter2);
			Toast.makeText(
					getApplicationContext(),
					"On Button Click : " + "\n"
							+ String.valueOf(type.getSelectedItem()),
					Toast.LENGTH_SHORT).show();
			count2 = type.getSelectedItemPosition();
			typeid = OpptypeID.get(count2);

		}
	}

	private class AsyncCallWS5 extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			// Call Web Method
			SoapObject request3 = new SoapObject(NAMESPACE, "get_leadsource");
			request3.addProperty("db",pref.getString("dbname", ""));
			// Create envelope
			SoapSerializationEnvelope envelope3 = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope3.dotNet = true;
			// Set output SOAP object
			envelope3.implicitTypes = true;
			envelope3.setOutputSoapObject(request3);
			// Create HTTP call object
			envelope3.bodyOut = request3;
			HttpTransportSE androidHttpTransport3 = new HttpTransportSE(URL);

			try {
				// Invoke web service

				androidHttpTransport3.call(SOAP_ACTION3, envelope3);

			} catch (Exception e) {
				String a = e.toString();

				// Toast.makeText(this,a, Toast.LENGTH_SHORT).show();
				// Assign Error Status true in static variable 'errored'

				e.printStackTrace();
			}
			try {
				// Toast.makeText(this,"inside try", Toast.LENGTH_SHORT).show();

				// Invoke web servi
				responsebody3 = (SoapObject) envelope3.getResponse();
				// Toast.makeText(this,""+responsebody,
				// Toast.LENGTH_SHORT).show();
				// Toast.makeText(this,"inside try", Toast.LENGTH_SHORT).show();
				// responsebody = (SoapObject) responsebody.getProperty(1);
				responsebody3 = (SoapObject) responsebody3.getProperty(1);
				table3 = (SoapObject) responsebody3.getProperty(0);
				// tablerow = (SoapObject) table.getProperty(0);
				// Toast.makeText(this,"inside try12",
				// Toast.LENGTH_SHORT).show();
				for (int i = 0; i < table3.getPropertyCount(); ++i) {
					tablerow3 = (SoapObject) table3.getProperty(i);
					OppleadsourceName.add(tablerow3.getProperty("Name")
							.toString());
					OppleadsourceID.add(Long.parseLong(tablerow3.getProperty(
							"ID").toString()));
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
			if (count5 == 5) {
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
			List<String> lables3 = new ArrayList<String>();
			for (int i = 0; i < OppleadsourceName.size(); i++) {
				lables3.add(OppleadsourceName.get(i));
			}
			// Creating adapter for spinner
			ArrayAdapter<String> spinnerAdapter3 = new ArrayAdapter<String>(
					getApplicationContext(), R.layout.spinneritem, lables3);
			// Drop down layout style - list view with radio button
			// spinnerAdapter
			// .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinnerAdapter3
					.setDropDownViewResource(R.layout.spinner_dropdown_item);
			// attaching data adapter to spinner
			Leadsource.setAdapter(spinnerAdapter3);
			Toast.makeText(
					getApplicationContext(),
					"On Button Click : " + "\n"
							+ String.valueOf(Leadsource.getSelectedItem()),
					Toast.LENGTH_SHORT).show();
			count3 = Leadsource.getSelectedItemPosition();
			ldsrcid = OppleadsourceID.get(count3);

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

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}

}
