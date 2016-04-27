package com.example.androidcrm;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.sql.Date;
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
import android.app.ProgressDialog;
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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class displaynotification extends Activity implements OnClickListener,
		OnItemSelectedListener {
	RadioGroup radioleadGroup;
	private static String URL = "http://192.168.164.50:8016/Service1.asmx";// address
	// SOAP Action URI again Namespace + Web method name

	

	int pos;
	// SOAP Action URI again Namespace + Web method name
	public static final String SOAP_ACTION = "http://tempuri.org/getnotifications";

	
	String webMethName1 = "getnotifications";
	
	int count, count1, count2;
	private static String NAMESPACE = "http://tempuri.org/";
	SoapObject responsebody;
	SoapObject tablerow = null;
	SoapObject table = null;
	List<String> list = new ArrayList<String>();
	List<String> list1 = new ArrayList<String>();
	SharedPreferences pref;
	Editor editor;
	int count5 = 0;
	List<String> industryname = new ArrayList<String>();
	List<Long> industryid = new ArrayList<Long>();
ListView lv ;


	
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
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.displaynoti);
		setTitle("Notifications");
		pref = getApplicationContext().getSharedPreferences("MyPref", 0);
		editor = pref.edit();
		lv = (ListView)findViewById(R.id.listView1);
		token = pref.getString("token", null);
		AsyncCallWS1 task = new AsyncCallWS1();
		// Call execute
		task.execute();
		
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Bundle b = new Bundle();
		b.putInt("a", 0);
		Intent i = new Intent(displaynotification.this, MainActivity.class);
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
		private class AsyncCallWS1 extends AsyncTask<String, Void, Void> {
			private ProgressDialog progress;
			@Override
			protected Void doInBackground(String... params) {
				// Call Web Method
			
				SoapObject request = new SoapObject(NAMESPACE, "getnotifications");
				request.addProperty("db",pref.getString("dbname", ""));
				request.addProperty("id",pref.getString("username", ""));
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

					for (int i = 0; i < table.getPropertyCount(); i++) {
						tablerow = (SoapObject) table.getProperty(i);
						list.add(tablerow.getProperty("Message").toString());
						list1.add(tablerow.getProperty("Date").toString());
						
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
				
				abcd();
				
				progress.dismiss();
				// startActivity(i);

			}

			@Override
			// Make Progress Bar visible
			protected void onPreExecute() {
				progress = new ProgressDialog(displaynotification.this);
				progress.setMessage("Please wait...");
				progress.setCancelable(false);
				progress.show();
			}

			@Override
			protected void onProgressUpdate(Void... values) {

			}
		}

		public void abcd() {
			// TODO Auto-generated method stub
			for(int i =0 ;i<list.size();i++)
			{
				 notifi adapter = new
					        notifi(displaynotification.this,list, list1);
				lv.setAdapter(adapter);
			}
			

		}

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}

			}
