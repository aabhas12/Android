package com.example.androidcrm;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.R.integer;
import android.R.string;

public class notiwebservice {
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
	public static int invokeLoginWS1(String to,
			String from, String message, String webMethName) {
		loginStatus = 1;
		// Create request
		System.out.println("entered webservice");
		SoapObject request = new SoapObject(NAMESPACE, webMethName);
		// Property which holds input parameters
		PropertyInfo to1 = new PropertyInfo();
	//	PropertyInfo comapnyname = new PropertyInfo();
		PropertyInfo from1 = new PropertyInfo();
		PropertyInfo message1 = new PropertyInfo();
		
		System.out.println("made the property info");
		// Set Username
		/*
		 * companyid1.setName("CompanyID"); companyid1.setValue(companyid1);
		 * companyid1.setType(integer.class); request.addProperty(companyid1);
		 */
		to1.setName("to");
		to1.setValue(to);
		to1.setType(string.class);
		request.addProperty(to1);
		/*comapnyname.setName("CompanyName");
		comapnyname.setValue(companyname);
		comapnyname.setType(String.class);
		request.addProperty(comapnyname);
		*/
		from1.setName("from");
		// Set Value
		from1.setValue(from);
		// Set dataType
		from1.setType(String.class);
		// Add the property to request object
		request.addProperty(from1);
      
		message1.setName("message");
		// Set Value
		message1.setValue(message);
		// Set dataType
		message1.setType(String.class);
		// Add the property to request object
		request.addProperty(message1);
		
		
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
			// Assign Error Statuslogin.error = 2;
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
