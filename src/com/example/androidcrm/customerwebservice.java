package com.example.androidcrm;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.R.integer;

public class customerwebservice {
	// Namespace of the Webservice - can be found in WSDL
	private static String NAMESPACE = "http://tempuri.org/";

	// Webservice URL - WSDL File location
	private static String URL = "http://192.168.164.50:8016/Service1.asmx";// ip address
	// SOAP Action URI again Namespace + Web method name
	private static String SOAP_ACTION = "http://tempuri.org/";
	static int loginStatus;

	
	public static int invokeLoginWS12(String name,
			int custotype, String website, long mobile, String email,
			 int indusid,String token, String webMethName) {
		loginStatus = 1;
		// Create request
		System.out.println("entered webservice");
		SoapObject request = new SoapObject(NAMESPACE, webMethName);
		// Property which holds input parameters
		PropertyInfo name1 = new PropertyInfo();
		PropertyInfo website1 = new PropertyInfo();
		PropertyInfo phone = new PropertyInfo();
		PropertyInfo emailid = new PropertyInfo();
		PropertyInfo customertype = new PropertyInfo();
		PropertyInfo indusid1 = new PropertyInfo();
		PropertyInfo token1 = new PropertyInfo();
		System.out.println("made the property info");
		
		name1.setName("name");
		name1.setValue(name);
		name1.setType(String.class);
		request.addProperty(name1);
		website1.setName("website");
		website1.setValue(website);
		website1.setType(String.class);
		request.addProperty(website1);
		
		phone.setName("phone");
		// Set Value
		phone.setValue(mobile);
		// Set dataType
		phone.setType(Long.class);
		// Add the property to request object
		request.addProperty(phone);
      
	
		
		emailid.setName("email");
		// Set Value
		emailid.setValue(email);
		// Set dataType
		emailid.setType(String.class);
		// Add the property to request object
		request.addProperty(emailid);
	
		customertype.setName("custtype");
		customertype.setValue(customertype);
		customertype.setType(integer.class);
		request.addProperty(customertype);
		indusid1.setName("indusid");
		indusid1.setValue(indusid);
		indusid1.setType(integer.class);
		request.addProperty(indusid1);
		
		 token1.setName("token"); 
		 token1.setValue(token);
		 token1.setType(String.class); 
		 request.addProperty(token1);
		
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
