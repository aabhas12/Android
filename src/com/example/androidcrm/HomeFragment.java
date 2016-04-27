package com.example.androidcrm;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("NewApi")
public class HomeFragment extends Fragment implements OnClickListener {
	private static String URL = "http://192.168.164.50:8016/Service1.asmx";// address

	// SOAP Action URI again Namespace + Web method name
	private static String SOAP_ACTION = "http://tempuri.org/getaccountcount";
	private static String SOAP_ACTION1 = "http://tempuri.org/getcontactscount";
	private static String SOAP_ACTION2 = "http://tempuri.org/getleadcount";
	private static String SOAP_ACTION3 = "http://tempuri.org/getoppcount";
	private static String SOAP_ACTION5 = "http://tempuri.org/getwonoppcount";
	private static String SOAP_ACTION4 = "http://tempuri.org/getleadstid";
	private static String SOAP_ACTION6 = "http://tempuri.org/getlostoppcount";
	private static String NAMESPACE = "http://tempuri.org/";
	Boolean isInternetPresent = false;
	long raid, leadsrcid, indusid;
	Button b;
	String comname, firstnam, lastnam, emailid, rating;
	// Connection detector class
	int accountcount = 0, leadcount = 0, contactscount = 0, oppcount = 0,
			offlineleadcount = 0, wonopp = 0, lostopp = 0,openlead=0,unqualfiedlead=0;
	ConnectionDetector cd;
	TextView wonopp1, lostopp1;
	SoapObject tablerow = null;
	ArrayAdapter<String> adapter;
	SoapObject responsebody;
	private ProgressDialog progress1;
	ListView lv;
	RelativeLayout rl;
	SoapObject table = null;
	SharedPreferences pref;
	Editor editor;
	int ac = 0;
	String token;
	static boolean errored = false;
	static int error = 0;
	int loginStatus;
	long number2;
	int id, count, count1 = 0;
	SQLiteDatabase db, db1;
	private ProgressDialog progress;
	TextView tv, tv1, tv2, tv3, tv4,openle,unquali;

	public HomeFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_home, container,
				false);

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		pref = getActivity().getSharedPreferences("MyPref", 0);
		editor = pref.edit();

		tv = (TextView) getActivity().findViewById(R.id.onlinelead);
		tv1 = (TextView) getActivity().findViewById(R.id.onlineopp3);
		wonopp1 = (TextView) getActivity().findViewById(R.id.wonopp1);
		lostopp1 = (TextView) getActivity().findViewById(R.id.lostopp1);
		openle=(TextView)getActivity().findViewById(R.id.Openlead);
		unquali=(TextView)getActivity().findViewById(R.id.unqualifiedlead);
		b = (Button) getActivity().findViewById(R.id.b);
		b.setOnClickListener(this);

		token = pref.getString("token", null);

		Toast.makeText(getActivity(), "internet present", Toast.LENGTH_LONG)
				.show();
		db = getActivity().openOrCreateDatabase("Lead", Context.MODE_PRIVATE,
				null);
		db.execSQL("CREATE TABLE IF NOT EXISTS lead1(id INTEGER PRIMARY KEY   AUTOINCREMENT,companyname VARCHAR,firstname VARCHAR,lastname Varchar,mobilenumber VARCHAR,email Varchar,Ratingid integer,ldsourceid integer,industrid integer);");

		Cursor c = db.rawQuery("SELECT * FROM lead1", null);
		count = c.getCount();

		if (c.getCount() == 0) {
			System.out.println("no records");

		
			AsyncCallWS4 task3 = new AsyncCallWS4();
			// Call execute
			task3.execute();
			AsyncCallWS5 task4 = new AsyncCallWS5();
			// Call execute
			task4.execute();
			AsyncCallWS10 task10 = new AsyncCallWS10();
			// Call execute
			task10.execute();
			AsyncCallWS6 task6 = new AsyncCallWS6();
			// Call execute
			task6.execute();
			AsyncCallWS11 task11 = new AsyncCallWS11();
			// Call execute
			task11.execute();
		} else {
			while (c.moveToNext()) {

				id = c.getInt(0);

				comname = c.getString(1);
				firstnam = c.getString(2);
				lastnam = c.getString(3);
				number2 = Long.parseLong(c.getString(4));
				emailid = c.getString(5);
				raid = (long) c.getInt(6);
				leadsrcid = (long) c.getInt(7);
				indusid = (long) c.getInt(8);
				count1++;
				AsyncCallWS1 task = new AsyncCallWS1();
				// Call execute
				task.execute();
			}
		
			AsyncCallWS6 task6 = new AsyncCallWS6();
			// Call execute
			task6.execute();
			AsyncCallWS4 task3 = new AsyncCallWS4();
			// Call execute
			task3.execute();
			AsyncCallWS5 task4 = new AsyncCallWS5();
			// Call execute
			task4.execute();
			
		}

	}

	private class AsyncCallWS4 extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			// Call Web Method

			SoapObject request = new SoapObject(NAMESPACE, "getleadcount");
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

				for (int i = 0; i < table.getPropertyCount(); i++) {
					tablerow = (SoapObject) table.getProperty(i);
					leadcount = Integer.parseInt(tablerow.getProperty("count")
							.toString());

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

			ac++;
			if (ac == 5) {
				tv.setText("Leads:" + leadcount);
				tv1.setText("Opportunity:" + oppcount);
				wonopp1.setText("" + wonopp);
				lostopp1.setText("" + lostopp);
				openle.setText("OPEN LEAD:"+openlead);
				unquali.setText("UNQUALIFIED LEAD:"+unqualfiedlead);
				progress1.dismiss();
			}

		}

		@Override
		// Make Progress Bar visible
		protected void onPreExecute() {
			progress1 = new ProgressDialog(getActivity());
			progress1.setMessage("WAIT ...");
			progress1.setCancelable(false);
			progress1.show();
		}
	}
	private class AsyncCallWS6 extends AsyncTask<String, Void, Void> {
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

				for (int i = 0; i < table.getPropertyCount(); i++) {
					tablerow = (SoapObject) table.getProperty(i);
					if(Integer.parseInt(tablerow.getProperty("LeadStatusID").toString())==12)
							{
						openlead++;
							}
					else if(Integer.parseInt(tablerow.getProperty("LeadStatusID")
							.toString())==14)
					{
						unqualfiedlead++;
					}

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

			ac++;
			if (ac == 5) {
				tv.setText("Leads:" + leadcount);
				tv1.setText("Opportunity:" + oppcount);
				wonopp1.setText("" + wonopp);
				lostopp1.setText("" + lostopp);
				openle.setText("OPEN LEAD:"+openlead);
				unquali.setText("UNQUALIFIED LEAD:"+unqualfiedlead);
				progress1.dismiss();
			}
			// startActivity(i);

		}

		@Override
		// Make Progress Bar visible
		protected void onPreExecute() {
		
		}
	}
	private class AsyncCallWS5 extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			// Call Web Method

			SoapObject request = new SoapObject(NAMESPACE, "getoppcount");
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

				for (int i = 0; i < table.getPropertyCount(); i++) {
					tablerow = (SoapObject) table.getProperty(i);
					oppcount = Integer.parseInt(tablerow.getProperty("count1")
							.toString());

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
			ac++;
			if (ac == 5) {
				tv.setText("Leads:" + leadcount);
				tv1.setText("Opportunity:" + oppcount);
				wonopp1.setText("" + wonopp);
				lostopp1.setText("" + lostopp);
				openle.setText("OPEN LEAD:"+openlead);
				unquali.setText("UNQUALIFIED LEAD:"+unqualfiedlead);
				progress1.dismiss();
			}
			// startActivity(i);

		}

		@Override
		// Make Progress Bar visible
		protected void onPreExecute() {

		}
	}

	private class AsyncCallWS10 extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			// Call Web Method

			SoapObject request = new SoapObject(NAMESPACE, "getwonoppcount");
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
				androidHttpTransport.call(SOAP_ACTION5, envelope);

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

				for (int i = 0; i < table.getPropertyCount(); i++) {
					tablerow = (SoapObject) table.getProperty(i);
					wonopp = Integer.parseInt(tablerow.getProperty("woncount")
							.toString());

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
			ac++;
			if (ac == 5) {
				tv.setText("Leads:" + leadcount);
				tv1.setText("Opportunity:" + oppcount);
				wonopp1.setText("" + wonopp);
				lostopp1.setText("" + lostopp);
				openle.setText("OPEN LEAD:"+openlead);
				unquali.setText("UNQUALIFIED LEAD:"+unqualfiedlead);
				progress1.dismiss();
			}

			// startActivity(i);

		}

		@Override
		// Make Progress Bar visible
		protected void onPreExecute() {

		}
	}

	private class AsyncCallWS11 extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			// Call Web Method

			SoapObject request = new SoapObject(NAMESPACE, "getlostoppcount");
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
				androidHttpTransport.call(SOAP_ACTION6, envelope);

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

				for (int i = 0; i < table.getPropertyCount(); i++) {
					tablerow = (SoapObject) table.getProperty(i);
					lostopp = Integer.parseInt(tablerow
							.getProperty("lostcount").toString());

				}
				System.out.println("lostopp:" + lostopp);
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
			ac++;
			if (ac == 5) {
				tv.setText("Leads:" + leadcount);
				tv1.setText("Opportunity:" + oppcount);
				wonopp1.setText("" + wonopp);
				lostopp1.setText("" + lostopp);
				openle.setText("OPEN LEAD:"+openlead);
				unquali.setText("UNQUALIFIED LEAD:"+unqualfiedlead);
				progress1.dismiss();
			}
			// startActivity(i);

		}

		@Override
		// Make Progress Bar visible
		protected void onPreExecute() {

		}
	}

	private class AsyncCallWS1 extends AsyncTask<String, Void, Void> {
		private ProgressDialog progress;

		@Override
		protected Void doInBackground(String... params) {
			// Call Web Method
			loginStatus = Leadwebservice.invokeLoginWS1(comname, firstnam,
					lastnam, number2, emailid, (int) raid, (int) leadsrcid,
					(int) indusid, token,pref.getString("dbname", ""), "lead_new");
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
					if (count == count1) {
						progress.dismiss();

					}
					// Navigate to Home Screen
					Toast.makeText(getActivity(), "inserted", Toast.LENGTH_LONG)
							.show();
					db.execSQL("DELETE FROM lead1 WHERE id=" + id + "");

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
			super.onPreExecute();
			progress = new ProgressDialog(getActivity());
			progress.setMessage("WAIT SYNC...");
			progress.setCancelable(false);
			progress.show();
		}

		@Override
		protected void onProgressUpdate(Void... values) {
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.equals(b)) {
			Intent i = new Intent(getActivity(), funnel.class);
			startActivity(i);
		}
	}

}