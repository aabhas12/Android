package com.example.androidcrm;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class first extends Activity {
	int age1;
	TextView tv;
	static int error = 0;
	
	ArrayList<String> city = new ArrayList<String>();
	ArrayList<Long> phnno = new ArrayList<Long>();
	ArrayList<Long> age = new ArrayList<Long>();
	
	boolean loginStatus;
	static boolean errored = false;
	private static String NAMESPACE = "http://tempuri.org/";
	ArrayList<String> name = new ArrayList<String>();
	//ArrayList<String> list1 = new ArrayList<String>();
	ProgressBar webservicePG;
	SoapObject tablerow = null;
	ArrayAdapter<String> adapter;
	SoapObject responsebody;
	ListView lv;
	RelativeLayout rl;
	SoapObject table = null;
	String[] values = new String[] {};
	// Webservice URL - WSDL File location
	private static String URL = "http://192.168.1.128:8010/Service1.asmx";// address
	// SOAP Action URI again Namespace + Web method name
	private static String SOAP_ACTION = "http://tempuri.org/view_leads";
	String webMethName = "view_leads";
	TableLayout tl = null;

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showleads);
	
		lv = (ListView)findViewById(R.id.listView1);
		// tl = (TableLayout) findViewById(R.id.table);
		Button b = new Button(this);
		b.setText("add");
		lv.addFooterView(b);
		AsyncCallWS1 task = new AsyncCallWS1();
		// Call execute
		task.execute();

	}

	private class AsyncCallWS1 extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			// Call Web Method
			List<String> result = new ArrayList<String>();
			SoapObject request = new SoapObject(NAMESPACE,
					"view_leads");

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
				
				 for (int i = 0; i < table.getPropertyCount(); ++i) {
				tablerow = (SoapObject) table.getProperty(i);
				name.add(tablerow.getProperty("name").toString());
				city.add(tablerow.getProperty("city").toString());
				phnno.add(Integer.valueOf(tablerow.getProperty("phnno").toString()), null);
				age.add(Integer.valueOf(tablerow.getProperty("age").toString()),null);
				 }System.out.println("tablerow result:" + tablerow.toString());
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
			
			//startActivity(i);
			
		}

		@Override
		// Make Progress Bar visible
		protected void onPreExecute() {
		
		}

		@Override
		protected void onProgressUpdate(Void... values) {

		}
	}

	public void abcd() {
		// TODO Auto-generated method stub
		//third adapter = new third(first.this,name,city,phnno,age);
		//lv.setAdapter(adapter);
		
	}
}
