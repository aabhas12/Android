package com.example.androidcrm;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.R.integer;
import android.R.string;

public class oppeditwebservice {
	// Namespace of the Webservice - can be found in WSDL
	private static String NAMESPACE = "http://tempuri.org/";

	// Webservice URL - WSDL File location
	private static String URL = "http://192.168.164.50:8016/Service1.asmx";// Make
																			// sure
																			// you
																			// change
																			// the
																			// IP
																			// address

	// SOAP Action URI again Namespace + Web method name
	private static String SOAP_ACTION = "http://tempuri.org/";
	static int loginStatus;

	// int companyid, String userName,
	// int rateid,
	// int ldsrcid, int indusid, int uid, String webMethName
	public static int invokeLoginWS1(int id,String oppname, int accountid,
			int stageid, int typeid, int amtD, int LeadSourceID,String token,String cldate,String dbname,
			String webMethName) {
		loginStatus = 1;
		// Create request
		System.out.println("entered webservice");
		SoapObject request = new SoapObject(NAMESPACE, webMethName);
		// Property which holds input parameters
		PropertyInfo opname = new PropertyInfo();
		// PropertyInfo comapnyname = new PropertyInfo();
		PropertyInfo acid = new PropertyInfo();
		
		PropertyInfo sid = new PropertyInfo();
		PropertyInfo tid = new PropertyInfo();
		PropertyInfo amt = new PropertyInfo();
		PropertyInfo lid = new PropertyInfo();
		PropertyInfo token1 = new PropertyInfo();
		PropertyInfo cld = new PropertyInfo();
		PropertyInfo dbname1 = new PropertyInfo();
		PropertyInfo oppid = new PropertyInfo();
		System.out.println("made the property info");
		// Set Username
		/*
		 * companyid1.setName("CompanyID"); companyid1.setValue(companyid1);
		 * companyid1.setType(integer.class); request.addProperty(companyid1);
		 */
		dbname1.setName("db");
		//Set dataType
		dbname1.setValue(dbname);
		//Set dataType
		dbname1.setType(String.class);
		//Add the property to request object
		request.addProperty(dbname1);
		opname.setName("name");
		opname.setValue(oppname);
		opname.setType(string.class);
		request.addProperty(opname);
		oppid.setName("id");
		oppid.setValue(id);
		oppid.setType(Integer.class);
		request.addProperty(oppid);
		
		/*
		 * comapnyname.setName("CompanyName");
		 * comapnyname.setValue(companyname); comapnyname.setType(String.class);
		 * request.addProperty(comapnyname);
		 */
		
		acid.setName("accountid");
		// Set Value
		acid.setValue(accountid);
		// Set dataType
		acid.setType(Integer.class);
		// Add the property to request object
		request.addProperty(acid);

		
		tid.setName("typeid");
		// Set Value
		tid.setValue(typeid);
		// Set dataType
		tid.setType(Integer.class);
		// Add the property to request object
		request.addProperty(tid);

		sid.setName("stageid");
		// Set Value
		sid.setValue(stageid);
		// Set dataType
		sid.setType(Integer.class);
		// Add the property to request object
		request.addProperty(sid);
         
		amt.setName("amtD");
		amt.setValue(amtD);
		amt.setType(integer.class);
		request.addProperty(amt);
		lid.setName("LeadSourceID");
		lid.setValue(LeadSourceID);
		lid.setType(integer.class);
		request.addProperty(lid);
		token1.setName("token");
		token1.setValue(token);
		token1.setType(integer.class);
		request.addProperty(token1);

		cld.setName("closedate");
		cld.setValue(cldate);
		cld.setType(String.class);
		request.addProperty(cld);

		// Create envelope
		System.out.println("before call");
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		// Set output SOAP object
		envelope.setOutputSoapObject(request);
		// Create HTTP call object
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		System.out.println("before try");
		try {
			// Invoke web service
			androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);

		} catch (Exception e) {

			System.out.println("entered catch of webservice");
			// Assign Error Status true in static variable 'errored'
			
			e.printStackTrace();
		}
		try {
			// Invoke web servi
			System.out.println("entered try");
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			System.out.println("got response:");
			loginStatus = Integer.parseInt(response.toString());
			System.out.println("entered try");
		} catch (Exception e) {
			// Assign Error Status true in static variable 'errored'
			
			System.out.println("entered ctch");
			
			e.printStackTrace();
		}

		/*
		 * try { // Invoke web service
		 * androidHttpTransport.call(SOAP_ACTION+webMethName, envelope); // Get
		 * the response SoapPrimitive response = (SoapPrimitive)
		 * envelope.getResponse(); // Assign it to boolean variable variable
		 * loginStatus =response.toString();
		 * 
		 * } catch (Exception e) { //Assign Error Status true in static variable
		 * 'errored' CheckDNLoginActivity.errored = true; e.printStackTrace(); }
		 */
		// Return booleam to calling object
		return loginStatus;
	}
}
