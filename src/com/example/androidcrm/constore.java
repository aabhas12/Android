package com.example.androidcrm;

import java.util.ArrayList;
import java.util.HashMap;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

public class constore extends Activity implements OnClickListener,
		OnItemClickListener, OnItemSelectedListener {
	private ArrayAdapter<String> adapter;

	HashMap<String, String> hashMap = new HashMap<String, String>();
	List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();
	private static String URL = "http://192.168.164.50:8016/Service1.asmx";// address
	// SOAP Action URI again Namespace + Web method name
	private static String SOAP_ACTION = "http://tempuri.org/getcustomer";
	String webMethName = "getcustomer";

	int count = -1;
	private static String NAMESPACE = "http://tempuri.org/";
	SoapObject responsebody;
	SoapObject tablerow = null;
	SoapObject table = null;
	AutoCompleteTextView textView = null;
	SharedPreferences pref;
	Editor editor;
	Button save;
	ArrayList<String> concustomername = new ArrayList<String>();
	ArrayList<Long> concustomerid = new ArrayList<Long>();
	ArrayList<Long> concompanyid = new ArrayList<Long>();
	String fname, lname, mobno, emailid, customername1;
	Spinner customername;
	EditText fname1, lname1, mobno1, emailid1;
	static int error = 0;
	int loginStatus;
int position;
	ProgressBar webservicePG1;
	static boolean errored = false;
	int uid;
	String token;
	long id, cid;

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Bundle b = new Bundle();
		b.putInt("a", 2);

		Intent i = new Intent(constore.this, MainActivity.class);
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
		setContentView(R.layout.contact);
		pref = getApplicationContext().getSharedPreferences("MyPref", 0);
		editor = pref.edit();
		save = (Button) findViewById(R.id.btnSubmitcon);
		save.setOnClickListener(this);
		token = pref.getString("token", null);
		// autoComplete= (CustomAutoCompleteTextView)
		// findViewById(R.id.Custnamecon);
		textView = (AutoCompleteTextView) findViewById(R.id.Custnamecon);
		// customername = (Spinner) findViewById(R.id.Custnamecon);
		// customername.setOnItemSelectedListener(this);
		fname1 = (EditText) findViewById(R.id.Firstnamecon);
		lname1 = (EditText) findViewById(R.id.Lastnamecon);
		mobno1 = (EditText) findViewById(R.id.phonecon);
		mobno1.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				if(arg0.length()!=10)
				{
					Toast.makeText(getApplicationContext(), "Enter a Valid PhoneNumber", Toast.LENGTH_LONG).show();
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
					Toast.makeText(getApplicationContext(),"Enter A valid Email Address",Toast.LENGTH_LONG).show();
				}
			}
		});
		AsyncCallWS2 task1 = new AsyncCallWS2();
		// Call execute
		task1.execute();
		// autoComplete.setOnItemClickListener(this);

		/** Setting the adapter to the listView */

		textView.setOnItemSelectedListener(this);
		textView.setOnItemClickListener(this);
		

	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		// TODO Auto-generated method stub
		// Log.d("AutocompleteContacts", "onItemSelected() position " +
		// position);
		System.out.println("position:" + position);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

		InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	
	}

	protected void onResume() {
		super.onResume();
	}

	protected void onDestroy() {
		super.onDestroy();
	}

	public final Pattern EMAIL_ADDRESS_PATTERN = Pattern
			.compile("[a-zA-Z0-9+._%-+]{1,256}" + "@"
					+ "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" + "(" + "."
					+ "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" + ")+");

	private boolean checkEmail(String email) {
		return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.equals(save)) {
			customername1 = textView.getText().toString();
		 int flag=0;
			for(int i = 0;i<concustomername.size();i++)
			{
				if(customername1.equals(concustomername.get(i)))
						{ 
					 flag=1;
					     position =i;
					     break;
						}
				else 
				{ 
					flag=0;
					continue;
				}
			}
			
			fname = fname1.getText().toString();

			lname = lname1.getText().toString();
			mobno = mobno1.getText().toString();
			emailid = emailid1.getText().toString();
if(flag==1)
{
	id = concustomerid.get(position);
}
else
{
	Toast.makeText(getApplicationContext(), "Enter the correct name", Toast.LENGTH_SHORT).show();
}
			
			  if (checkEmail(emailid) && mobno.length() == 10 && flag==1) {
			  
			   Toast.makeText(constore.this,"Valid Email Addresss",Toast.LENGTH_SHORT).show(); 
			  AsyncCallWS1 task = new AsyncCallWS1(); // Call execute 
			  task.execute(); }
			  else if
			  (mobno.length() != 10) { Toast.makeText(getApplicationContext(),
			  "Invalid Phone Number", Toast.LENGTH_SHORT).show(); } else {
			  Toast.makeText(getApplicationContext(), "Invalid Email Addresss",
			  Toast.LENGTH_SHORT).show();
			  
			  }
			 
		}
	}

	private class AsyncCallWS1 extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			// Call Web Method

			loginStatus = storecontacts.invokeLoginWS2(fname, (int) id, lname,
					 mobno, emailid, token,pref.getString("dbname", ""), "storecontact");
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
					b.putInt("a", 2);
					Toast.makeText(getApplicationContext(), "inserted",
							Toast.LENGTH_SHORT).show();

					Intent i = new Intent(constore.this, MainActivity.class);
					i.putExtras(b);
					startActivity(i);

				} else {
					// Set Error message
					System.out.println("error");
				}
				// Error status is true
			} else {
				if (error == 1) {
					Toast.makeText(getApplicationContext(),
							"Error occured in invoking webservice1",
							Toast.LENGTH_SHORT).show();
				} else if (error == 2) {
					Toast.makeText(getApplicationContext(),
							"Error occured in invoking webservice2",
							Toast.LENGTH_SHORT).show();
					// WebService.loginStatus,Toast.LENGTH_LONG).show();
				} else if (error == 3) {
					// Toast.makeText(getApplicationContext(),
					// WebService.loginStatus,Toast.LENGTH_LONG).show();
					// tv.setText("Error occured in invoking webservice3");
				} else {
					// tv.setText("Error occured in invoking webservice");
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
					concustomername.add(tablerow.getProperty("AccountName")
							.toString());
					concustomerid.add(Long.parseLong(tablerow.getProperty("ID")
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
			List<Long> lables1 = new ArrayList<Long>();

			for (int i = 0; i < concustomername.size(); i++) {
				lables.add(concustomername.get(i));
				lables1.add(concustomerid.get(i));
				hashMap.put(lables1.get(i).toString(), lables.get(i));

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
	protected void onSaveInstanceState(Bundle outState) {

		super.onSaveInstanceState(outState);
	}

	/**
	 * A callback method, which is executed when the activity is recreated ( eg
	 * : Configuration changes : portrait -> landscape )
	 */
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {

		super.onRestoreInstanceState(savedInstanceState);
	}
}
