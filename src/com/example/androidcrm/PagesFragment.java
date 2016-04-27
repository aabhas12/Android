package com.example.androidcrm;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.annotation.SuppressLint;
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

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("NewApi")
public class PagesFragment extends Fragment implements OnClickListener {
	int age1;
	SharedPreferences pref;
	Editor editor;
	TextView tv;
	static int error = 0;
	ListViewAdapter2 adapter;
	ArrayList<WorldPopulation2> arraylist = new ArrayList<WorldPopulation2>();
 EditText ed;
	ArrayList<String> closeddate = new ArrayList<String>();
	ArrayList<Long> phnno = new ArrayList<Long>();
    ArrayList<Long> id = new ArrayList<Long>();
	boolean loginStatus;
	static boolean errored = false;
	private static String NAMESPACE = "http://tempuri.org/";
	ArrayList<String> name = new ArrayList<String>();
	ArrayList<String> cutomername = new ArrayList<String>();
	ArrayList<String> c = new ArrayList<String>();
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
	private static String SOAP_ACTION = "http://tempuri.org/getoppu";
	String webMethName = "getoppu";
	TableLayout tl = null;
	Button b;

	public PagesFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.showopp, container, false);

		return rootView;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		pref = getActivity().getSharedPreferences("MyPref", 0);
		editor = pref.edit();
		lv = (ListView) getActivity().findViewById(R.id.opplistView1);
		// tl = (TableLayout) findViewById(R.id.table);
		// Button b = new Button(this);
		// b.setText("add");
		// lv.addFooterView(b);
		ed=(EditText)getActivity().findViewById(R.id.editopp);
		b = (Button) getActivity().findViewById(R.id.oppbutton);
		b.setOnClickListener(this);
		
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
                        b.putLong("a1", id.get(arg2));
                        Intent i = new Intent(getActivity(),editopp.class);
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
			List<String> result = new ArrayList<String>();
			SoapObject request = new SoapObject(NAMESPACE, "getoppu");
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
				System.out.println("reslt1212:" + responsebody.toString());
				table = (SoapObject) responsebody.getProperty(0);
				System.out.println("table result:" + table.toString());
				// tablerow = (SoapObject) table.getProperty(0);
				// Toast.makeText(this,"inside try12",
				// Toast.LENGTH_SHORT).show();
				System.out.println(table.getPropertyCount());
				for (int i = 0; i < table.getPropertyCount(); i++) {
					tablerow = (SoapObject) table.getProperty(i);
					name.add(tablerow.getProperty("OpportunityName").toString());
					System.out.println(""+tablerow.getProperty("CloseDate").toString());
					//closeddate.add("not closed");
				  if(tablerow.getProperty("CloseDate").toString().equals("01/01/1900"))
					{
						closeddate.add("Not Closed");
					}
					else
					{
						closeddate.add(tablerow.getProperty("CloseDate").toString());
					}
					
					id.add(Long.parseLong(tablerow.getProperty("ID")
							.toString()));
					//phnno.add(Long.parseLong(tablerow.getProperty("Phone")
						//	.toString()));
					c.add(tablerow.getProperty("Phone")
							.toString());
					cutomername.add(tablerow.getProperty("AccountName")
							.toString());

				}
				System.out.println("tablerow result:" + tablerow.toString());
				System.out.println(name.get(0));
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
		for(int i =0 ;i<name.size();i++)
		{
			WorldPopulation2 wp = new WorldPopulation2(name.get(i), cutomername.get(i),
				c.get(i), closeddate.get(i));
			// Binds all strings into an array
			arraylist.add(wp);
		}
		adapter = new ListViewAdapter2(getActivity(), arraylist);
		lv.setAdapter(adapter);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.equals(b)) {
			Intent i = new Intent(getActivity(), oppstore.class);
			startActivity(i);
		}
	}

}