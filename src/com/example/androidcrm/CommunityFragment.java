package com.example.androidcrm;

import java.util.ArrayList;
import java.util.Locale;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;


public class CommunityFragment extends Fragment implements OnClickListener{
	
	public CommunityFragment(){}
	ListViewAdapter1 adapter;

	
	ArrayList<WorldPopulation1> arraylist = new ArrayList<WorldPopulation1>();
	int age1;
	TextView tv;
	static int error = 0;
    EditText ed;
	ArrayList<String> phnno = new ArrayList<String>();
	SharedPreferences pref;
	Editor editor;
	ArrayList<String> Customername = new ArrayList<String>();
	ArrayList<String> CustomerType = new ArrayList<String>();
	ArrayList<String> customerWebsite = new ArrayList<String>();
	ArrayList<String> customeremail = new ArrayList<String>();
	
	ArrayList<Long> ID = new ArrayList<Long>();

	boolean loginStatus;
	static boolean errored = false;
	private static String NAMESPACE = "http://tempuri.org/";
	
	// ArrayList<String> list1 = new ArrayList<String>();
	ProgressBar webservicePG;
	SoapObject tablerow = null;
	//ArrayAdapter<String> adapter;
	SoapObject responsebody;
	ListView lv;
	RelativeLayout rl;
	SoapObject table = null;
	String[] values = new String[] {};
	// Webservice URL - WSDL File location
	private static String URL = "http://192.168.164.50:8016/Service1.asmx";// address
	
	// SOAP Action URI again Namespace + Web method name
	private static String SOAP_ACTION = "http://tempuri.org/getinfocustomers";
	
	String webMethName = "getinfocustomers";

	TableLayout tl = null;
	Button b;
  	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_community, container, false);

		return rootView;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		pref = getActivity().getSharedPreferences("MyPref", 0);
		editor = pref.edit();
		lv = (ListView) getActivity().findViewById(R.id.listViewcus);
		// tl = (TableLayout) findViewById(R.id.table);
		// Button b = new Button(this);
		// b.setText("add");
		// lv.addFooterView(b);
		b = (Button) getActivity().findViewById(R.id.buttoncus);
		b.setOnClickListener(this);
		ed=(EditText)getActivity().findViewById(R.id.editcust);
		AsyncCallWS1 task = new AsyncCallWS1();
		// Call execute
		task.execute();
		ed.addTextChangedListener(new TextWatcher() {
			 
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				String text = ed.getText().toString().toLowerCase(Locale.getDefault());
				adapter.filter(text);
			}
 
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub
			}
 
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
			}
		});
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
                        Bundle b = new Bundle();
                        b.putLong("1", ID.get(arg2));
                        Intent i = new Intent(getActivity(),editcustomer.class);
                        i.putExtras(b);
                        startActivity(i);
			}

		});
		

	}
	

	private class AsyncCallWS1 extends AsyncTask<String, Void, Void> {
		private ProgressDialog progress;
		@Override
		protected Void doInBackground(String... params) {
			// Call Web Method
		
			SoapObject request = new SoapObject(NAMESPACE, "getinfocustomers");
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

				for (int i = 0; i < table.getPropertyCount(); i++) {
					tablerow = (SoapObject) table.getProperty(i);
					Customername.add(tablerow.getProperty("AccountName").toString());
					CustomerType.add(tablerow.getProperty("Name").toString());
					customerWebsite.add(tablerow.getProperty("Website").toString());
					customeremail.add(tablerow.getProperty("Email").toString());
					ID.add(Long.parseLong(tablerow.getProperty("ID").toString()));
					
			System.out.println("table:"+table.toString());
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
			progress = new ProgressDialog(getActivity());
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
		for(int i =0 ;i<Customername.size();i++)
		{
			WorldPopulation1 wp = new WorldPopulation1(Customername.get(i), CustomerType.get(i),
				customerWebsite.get(i), customeremail.get(i));
			// Binds all strings into an array
			arraylist.add(wp);
		}
		adapter = new ListViewAdapter1(getActivity(), arraylist);
		 
		// Binds the Adapter to the ListView
		lv.setAdapter(adapter);
		

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.equals(b)) {
			Intent i = new Intent(getActivity(), addcustomer.class);
			startActivity(i);
		}
	}
}
