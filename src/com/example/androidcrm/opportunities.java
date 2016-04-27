package com.example.androidcrm;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("NewApi")
public class opportunities extends Activity implements OnClickListener,
		OnItemSelectedListener {
	EditText name, city, phonenumber, age;
	Button save, cancel;
	private static String NAMESPACE = "http://tempuri.org/";
	SoapObject responsebody;
	private TextView Output;
	String name1, city1;
	int age1;
	int year;
	long number1;
	int month;

	int day;

	static int error = 0;
	boolean loginStatus;
	private static String URL = "http://192.168.164.50:8016/Service1.asmx";// address
	// SOAP Action URI again Namespace + Web method name
	private static String SOAP_ACTION = "http://tempuri.org/view_leads";
	String webMethName = "view_leads";
	Spinner sp1, sp2, sp3, sp4, sp5;
	TextView tv;
	final Calendar calendar = Calendar.getInstance();
	SoapObject tablerow = null;
	SoapObject table = null;
	List<String> list = new ArrayList<String>();
	ProgressBar webservicePG1;
	static boolean errored = false;
	DatePicker date_picker;
	EditText namexml;
	String name2, closeddt, custoname, stage, type, leadsource, amount;

	public opportunities() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.opportunities);
		date_picker = (DatePicker) findViewById(R.id.datePicker1);
		sp1 = (Spinner) findViewById(R.id.spinner3);
		sp1.setOnItemSelectedListener(this);
		namexml = (EditText) findViewById(R.id.name);
		sp2 = (Spinner) findViewById(R.id.spinner4);
		sp2.setOnItemSelectedListener(this);
		sp3 = (Spinner) findViewById(R.id.spinner5);
		sp3.setOnItemSelectedListener(this);
		sp4 = (Spinner) findViewById(R.id.spinner6);
		sp4.setOnItemSelectedListener(this);
		sp5 = (Spinner) findViewById(R.id.spinner7);
		sp5.setOnItemSelectedListener(this);
		year = calendar.get(Calendar.YEAR);

		month = calendar.get(Calendar.MONTH);

		day = calendar.get(Calendar.DAY_OF_MONTH);
		Toast.makeText(
				getApplicationContext(),
				new StringBuilder().append(month + 1).append("-").append(day)
						.append("-").append(year).append(" "),
				Toast.LENGTH_SHORT).show();
		date_picker.init(year, month, day, null);

		AsyncCallWS2 task1 = new AsyncCallWS2();
		// Call execute
		task1.execute();
		save = (Button) findViewById(R.id.add_opp);
		save.setOnClickListener(this);
		cancel = (Button) findViewById(R.id.cancel_opp);
		cancel.setOnClickListener(this);
		// Button click Listener
		// addListenerOnButton();

		/*
		 * name = (EditText) getActivity().findViewById(R.id.editText1);
		 * webservicePG1 =
		 * (ProgressBar)getActivity().findViewById(R.id.progressBar1);
		 * phonenumber = (EditText) getActivity().findViewById(R.id.editText4);
		 * age = (EditText)getActivity(). findViewById(R.id.editText3); b =
		 * (Button) getActivity().findViewById(R.id.button1); tv = (TextView)
		 * getActivity().findViewById(R.id.textView5);
		 * b.setOnClickListener(this);
		 * city=(EditText)getActivity().findViewById(R.id.editText2);
		 */
	}

	public void addListenerOnSpinnerItemSelection() {

		sp1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
	}

	// get the selected dropdown list value

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.equals(save)) {
			if ((name.getText().length() != 0 && name.getText().toString() != "")
					&& (city.getText().length() != 0 && city.getText()
							.toString() != "")
					&& (phonenumber.getText().length() != 0 && phonenumber
							.getText().toString() != "")
					&& (age.getText().length() != 0 && age.getText().toString() != "")) {
				name1 = name.getText().toString();
				city1 = city.getText().toString();
				number1 = Long.parseLong(phonenumber.getText().toString());
				age1 = Integer.parseInt(age.getText().toString());
				name2 = namexml.getText().toString();
				closeddt = (new StringBuilder().append(month + 1).append("-")
						.append(day).append("-").append(year).append(" "))
						.toString();
				custoname = String.valueOf(sp1.getSelectedItem());
				stage = String.valueOf(sp2.getSelectedItem());
				type = String.valueOf(sp3.getSelectedItem());
				leadsource = String.valueOf(sp4.getSelectedItem());
				amount = String.valueOf(sp5.getSelectedItem());
				AsyncCallWS1 task = new AsyncCallWS1();
				// Call execute
				task.execute();
			}

		} else if (v.equals(cancel)) {
			namexml.setText("");
		}
	}

	private class AsyncCallWS1 extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			// Call Web Method
			//loginStatus = WebService1.invokeLoginWS1(name1, city1, number1,
				//	age1, "new_usr");
			return null;
		}

		@Override
		// Once WebService returns response
		protected void onPostExecute(Void result) {
			// Make Progress Bar invisible
			webservicePG1.setVisibility(View.INVISIBLE);
			// Intent intObj = new Intent(HomeActivity.this, abcd.class);
			// Error status is false
			if (!errored) {
				// Based on Boolean value returned from WebService
				if (loginStatus == true) {
					// Navigate to Home Screen
					// startActivity(intObj);
				} else {
					// Set Error message

				}
				// Error status is true
			} else {
				if (error == 1) {
					tv.setText("Error occured in invoking webservice1");
				} else if (error == 2) {
					tv.setText("Error occured in invoking webservice2");
					// Toast.makeText(getApplicationContext(),
					// WebService.loginStatus,Toast.LENGTH_LONG).show();
				} else if (error == 3) {
					// Toast.makeText(getApplicationContext(),
					// WebService.loginStatus,Toast.LENGTH_LONG).show();
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
			webservicePG1.setVisibility(View.VISIBLE);

		}

		@Override
		protected void onProgressUpdate(Void... values) {
		}
	}

	private class AsyncCallWS2 extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			// Call Web Method
			System.out.println("entering async");

			SoapObject request = new SoapObject(NAMESPACE, "view_leads");

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
			System.out.println("before try");
			try {
				// Invoke web service
				System.out.println("entered try");
				androidHttpTransport.call(SOAP_ACTION, envelope);

			} catch (Exception e) {
				String a = e.toString();
				System.out.println("entered catch");
				System.out.println(a);
				// Toast.makeText(this,a, Toast.LENGTH_SHORT).show();
				// Assign Error Status true in static variable 'errored'
				
				e.printStackTrace();
			}
			try {
				// Toast.makeText(this,"inside try", Toast.LENGTH_SHORT).show();
				System.out.println("entered second try");
				// Invoke web servi
				responsebody = (SoapObject) envelope.getResponse();
				// Toast.makeText(this,""+responsebody,
				// Toast.LENGTH_SHORT).show();
				// Toast.makeText(this,"inside try", Toast.LENGTH_SHORT).show();
				responsebody = (SoapObject) responsebody.getProperty(1);
				System.out.println("reslt1212:" + responsebody.toString());
				table = (SoapObject) responsebody.getProperty(0);
				System.out.println("table result:" + table.toString());
				// tablerow = (SoapObject) table.getProperty(0);
				// Toast.makeText(this,"inside try12",
				// Toast.LENGTH_SHORT).show();

				for (int i = 0; i < table.getPropertyCount(); ++i) {
					tablerow = (SoapObject) table.getProperty(i);
					list.add(tablerow.getProperty("name").toString());

				}
				System.out.println("tablerow result:" + tablerow.toString());
				System.out.println(list.get(0));
				// loginStatus =Boolean.parseBoolean(response.toString());

			} catch (Exception e) {
				String a = e.toString();
				System.out.println("result:" + responsebody.toString());
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

			for (int i = 0; i < list.size(); i++) {
				lables.add(list.get(i));
			}

			// Creating adapter for spinner
			
			ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(
					getApplicationContext(), R.layout.spinneritem, lables);
			// Drop down layout style - list view with radio button
			spinnerAdapter
					.setDropDownViewResource(R.layout.spinner_dropdown_item);

			// attaching data adapter to spinner
			sp1.setAdapter(spinnerAdapter);
			Toast.makeText(
					getApplicationContext(),
					"On Button Click : " + "\n"
							+ String.valueOf(sp1.getSelectedItem()),
					Toast.LENGTH_LONG).show();
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
