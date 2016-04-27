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

public class oppstore extends Activity implements OnClickListener,
		OnItemClickListener, OnItemSelectedListener {
	private Calendar cal;
	private int day;
	private int month;
	int amount;
	long customerid, stageid, typeid, companyid, ldsrcid;
	private int year;
	private ArrayAdapter<String> adapter;
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
	int count, count1, count2, count3;
	AutoCompleteTextView textView = null;
	private static String NAMESPACE = "http://tempuri.org/";
	SoapObject responsebody, responsebody1, responsebody2, responsebody3;
	SoapObject tablerow = null, tablerow1 = null, tablerow2 = null,
			tablerow3 = null;
	SoapObject table = null, table1 = null, table2 = null, table3 = null;

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

	Button save, cancel;
	EditText Amount;
	long raid, leadsrcid, indusid;
	EditText oppname;
	TextView closedate;
	TextView temp;
	String oppname12, customername12, stage12, type12, leadsource12, cldate;
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

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Bundle b = new Bundle();
		b.putInt("a", 4);
		Intent i = new Intent(oppstore.this, MainActivity.class);
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
		pref = getApplicationContext().getSharedPreferences("MyPref", 0);
		editor = pref.edit();
		save = (Button) findViewById(R.id.btnSubmit);
		save.setOnClickListener(this);
		token = pref.getString("token", null);
		oppname = (EditText) findViewById(R.id.oppname1);
		closedate = (TextView) findViewById(R.id.closedate);
		closedate.setOnClickListener(this);
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
		textView.setOnItemSelectedListener(this);
		textView.setOnItemClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.equals(save)) {

			oppname12 = oppname.getText().toString();
			customername12 = textView.getText().toString();
			int flag = 0;
			for (int i = 0; i < OppCustomerName.size(); i++) {
				if (customername12.equals(OppCustomerName.get(i))) {
					flag = 1;
					count1 = i;
					break;
				} else {
					flag = 0;
					continue;
				}
			}

			if (flag == 1) {
				customerid = OppCustomerID.get(count1);
			} else {
				Toast.makeText(getApplicationContext(),
						"Enter the Proper CustomerName", Toast.LENGTH_SHORT)
						.show();
			}
			count = stage.getSelectedItemPosition();
			stage12 = stage.getSelectedItem().toString();
			stageid = OppStageID.get(count);
			count2 = type.getSelectedItemPosition();
			type12 = type.getSelectedItem().toString();
			typeid = OpptypeID.get(count2);
			count3 = Leadsource.getSelectedItemPosition();
			leadsource12 = Leadsource.getSelectedItem().toString();
			leadsrcid = OppleadsourceID.get(count3);
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

			loginStatus = oppwebservice.invokeLoginWS1(oppname12,
					(int) customerid, (int) stageid, (int) typeid,
					(int) amount, (int) ldsrcid, token, cldate,	pref.getString("dbname", ""), "storeopp");
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
					Intent i = new Intent(oppstore.this, MainActivity.class);
					i.putExtras(b);
					startActivity(i);
					Toast.makeText(getApplicationContext(), "inserted",
							Toast.LENGTH_SHORT).show();

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
