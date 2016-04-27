package com.example.androidcrm;

import java.io.ObjectOutputStream.PutField;
import java.util.ArrayList;
import java.util.List;

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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

public class funnel extends Activity implements OnClickListener {
	Button barGraph, b2, b3;
	private static String URL = "http://192.168.164.50:8016/Service1.asmx";// address
	private static String SOAP_ACTION = "http://tempuri.org/getopp";
	private static String SOAP_ACTION2 = "http://tempuri.org/getopenleadscount";
	private static String NAMESPACE = "http://tempuri.org/";
	private static String SOAP_ACTION3 = "http://tempuri.org/getoppcount1";
	private static String SOAP_ACTION4 = "http://tempuri.org/getleadstid";
	ArrayList<Long> ldid = new ArrayList<Long>();
	ArrayList<Long> ldmon = new ArrayList<Long>();
	SoapObject responsebody;
	SoapObject tablerow = null;
	SoapObject table = null;
	SharedPreferences pref;
	Editor editor;
	static int error = 0;
	int count = 0, count1 = 0;
	int loginStatus;
	Bundle b = new Bundle();
	Bundle b1 = new Bundle();
	static long wonopp, opp, leads;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("CHARTS");
		setContentView(R.layout.charts1);
		pref = getApplicationContext().getSharedPreferences("MyPref", 0);
		editor = pref.edit();
		b3 = (Button) findViewById(R.id.Linegraph);
		
		b2 = (Button) findViewById(R.id.funnel1);
		
	
			System.out.println("Entered");
			
			b2.setOnClickListener(this);
			
			b3.setOnClickListener(this);
		
		AsyncCallWS1 task1 = new AsyncCallWS1();
		// Call execute
		task1.execute();

		AsyncCallWS2 task2 = new AsyncCallWS2();
		// Call execute
		task2.execute();
		AsyncCallWS3 task3 = new AsyncCallWS3();
		// Call execute
		task3.execute();
		AsyncCallWS4 task4 = new AsyncCallWS4();
		// Call execute
		task4.execute();
		
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		

		case R.id.funnel1:
			Intent i = new Intent(funnel.this, funnel2.class);
			i.putExtras(b);
			startActivity(i);
			break;

		case R.id.Linegraph:
			Intent i1 = new Intent(funnel.this, line1.class);
			i1.putExtras(b1);
			startActivity(i1);
			break;
		}
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
	finish();
		super.onPause();
	}
@Override
public void onBackPressed() {
	// TODO Auto-generated method stub
Bundle b = new Bundle();
b.putInt("a", 0);
	Intent i = new Intent(funnel.this,MainActivity.class);
i.putExtras(b);
startActivity(i);
	super.onBackPressed();
}
	private class AsyncCallWS1 extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			// Call Web Method
			System.out.println("async1");
			SoapObject request = new SoapObject(NAMESPACE, "getopp");
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
				System.out.println("entered try");
				// Invoke web servi
				responsebody = (SoapObject) envelope.getResponse();
				System.out.println("fist");
				// Toast.makeText(this,""+responsebody,
				// Toast.LENGTH_SHORT).show();
				// Toast.makeText(this,"inside try", Toast.LENGTH_SHORT).show();
				responsebody = (SoapObject) responsebody.getProperty(1);

				table = (SoapObject) responsebody.getProperty(0);

				tablerow = (SoapObject) table.getProperty(0);
				wonopp = (Long.parseLong(tablerow.getProperty("OppStage")
						.toString()));

				System.out.println("OPP:" + wonopp);
				abc();
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

			// startActivity(i);

		}

		@Override
		// Make Progress Bar visible
		protected void onPreExecute() {

		}

		@Override
		protected void onProgressUpdate(Void... values) {

		}

		void abc() {
			count++;
			if (count == 3) {
				b.putLong("10", wonopp);
				b.putLong("11", opp);
				b.putLong("12", leads);

			}
		}

	}

	private class AsyncCallWS2 extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			// Call Web Method

			SoapObject request = new SoapObject(NAMESPACE, "getopenleadscount");
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

				tablerow = (SoapObject) table.getProperty(0);

				leads = (Long.parseLong(tablerow.getProperty("OpenLeads")
						.toString()));

				System.out.println("leads:" + leads);
				// loginStatus =Boolean.parseBoolean(response.toString());
				abc();
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

			// startActivity(i);

		}

		@Override
		// Make Progress Bar visible
		protected void onPreExecute() {

		}

		@Override
		protected void onProgressUpdate(Void... values) {

		}

		void abc() {
			count++;
			if (count == 3) {
				b.putLong("10", wonopp);
				b.putLong("11", opp);
				b.putLong("12", leads);
			}
		}

	}

	private class AsyncCallWS3 extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			// Call Web Method

			SoapObject request = new SoapObject(NAMESPACE, "getoppcount1");
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

				androidHttpTransport.call(SOAP_ACTION3, envelope);

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

				opp = (Long.parseLong(tablerow.getProperty("Openopp")
						.toString()));

				System.out.println("oppxsx:" + opp);
				abc();
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

			// startActivity(i);

		}

		@Override
		// Make Progress Bar visible
		protected void onPreExecute() {

		}

		@Override
		protected void onProgressUpdate(Void... values) {

		}

		void abc() {
			count++;
			if (count == 3) {
				b.putLong("10", wonopp);
				b.putLong("11", opp);
				b.putLong("12", leads);
			}
		}

	}

	private class AsyncCallWS4 extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			// Call Web Method

			SoapObject request = new SoapObject(NAMESPACE, "getleadstid");
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
				for (int i = 0; i < table.getPropertyCount(); ++i) {
					tablerow = (SoapObject) table.getProperty(i);

					ldid.add(Long.parseLong(tablerow
							.getProperty("LeadStatusID").toString()));
					ldmon.add(Long.parseLong(tablerow.getProperty("Moddate")
							.toString()));

					// loginStatus =Boolean.parseBoolean(response.toString());
				}
				abc();
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

			// startActivity(i);

		}

		@Override
		// Make Progress Bar visible
		protected void onPreExecute() {

		}

		@Override
		protected void onProgressUpdate(Void... values) {

		}

		void abc() {

			count1++;
			long[] stockArr = new long[ldid.size()];
			for (int i = 0; i < ldid.size(); i++) {
				stockArr[i] = ldid.get(i);

			}
			long[] stockArr1 = new long[ldmon.size()];
			for (int i = 0; i < ldmon.size(); i++) {
				stockArr1[i] = ldmon.get(i);
				System.out.println("saas:" + stockArr1[i]);
			}

			b1.putLongArray("10", stockArr);
			b1.putLongArray("11", stockArr1);

		}

	}
}