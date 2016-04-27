package com.example.androidcrm;

import java.util.ArrayList;
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
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TableLayout;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("NewApi")
public class FindPeopleFragment extends Fragment implements OnClickListener,OnQueryTextListener {
	int age1;
EditText ed;
	static int error = 0;
	ListViewAdapter adapter;
	ArrayList<WorldPopulation> arraylist = new ArrayList<WorldPopulation>();
	
	ArrayList<String> phnno = new ArrayList<String>();
	SharedPreferences pref;
	Editor editor;
	ArrayList<String> companyname = new ArrayList<String>();
	ArrayList<String> Firstname = new ArrayList<String>();
	ArrayList<String> Lastname = new ArrayList<String>();
	ArrayList<String> ratingname = new ArrayList<String>();
	ArrayList<String> email = new ArrayList<String>();
	ArrayList<Long> ID = new ArrayList<Long>();

	boolean loginStatus;
	static boolean errored = false;
	private static String NAMESPACE = "http://tempuri.org/";
	ArrayList<String> name = new ArrayList<String>();
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
	private static String SOAP_ACTION = "http://tempuri.org/getleads";
	private static String SOAP_ACTION1 = "http://tempuri.org/getcompanyname";
	String webMethName = "getleads";
	String webMethName1 = "getcompanyname";
	TableLayout tl = null;
	Button b;
  
	public FindPeopleFragment() {
	}
     
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.showleads, container, false);

		return rootView;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		pref = getActivity().getSharedPreferences("MyPref", 0);
		editor = pref.edit();
		lv = (ListView) getActivity().findViewById(R.id.listView1);
		// tl = (TableLayout) findViewById(R.id.table);
		// Button b = new Button(this);
		// b.setText("add");
		// lv.addFooterView(b);
		ed = (EditText)getActivity().findViewById(R.id.edit);
         
		b = (Button) getActivity().findViewById(R.id.button12);
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
	   // getActionBar().setIcon(new BitmapDrawable(getResources(), letterTile));
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
                        Bundle b = new Bundle();
                        b.putLong("1", ID.get(arg2));
                        Intent i = new Intent(getActivity(),editlead.class);
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
		
			SoapObject request = new SoapObject(NAMESPACE, "getleads");
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
					Firstname.add(tablerow.getProperty("FirstName").toString());
					Lastname.add(tablerow.getProperty("LastName").toString());
					companyname.add(tablerow.getProperty("Comp").toString());
					phnno.add(tablerow.getProperty("Phone").toString());
					ID.add(Long.parseLong(tablerow.getProperty("ID").toString()));
					ratingname.add(tablerow.getProperty("RatingName").toString());
					
				System.out.println("rating:"+tablerow.getProperty("RatingName").toString());
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
		for(int i =0 ;i<Firstname.size();i++)
		{
			WorldPopulation wp = new WorldPopulation(Firstname.get(i)+" "+Lastname.get(i), companyname.get(i),
					ratingname.get(i), phnno.get(i));
			// Binds all strings into an array
			arraylist.add(wp);
		}
		adapter = new ListViewAdapter(getActivity(), arraylist);
		lv.setAdapter(adapter);
		

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.equals(b)) {
			Intent i = new Intent(getActivity(), lead1.class);
			startActivity(i);
		}
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		// TODO Auto-generated method stub
		return false;
	}

}
