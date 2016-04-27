package com.example.androidcrm;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.R.integer;

public class WebService1 {
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
	public static int invokeLoginWS1(String dbname,int id, String companyname,
			String firname, String lastname, String mobno, String email,
			int ratid, int leadid, int indusid,String token, String webMethName) {
		loginStatus = 0;
		// Create request
		System.out.println("entered webservice");
		SoapObject request = new SoapObject(NAMESPACE, webMethName);
		// Property which holds input parameters
		PropertyInfo ID = new PropertyInfo();
		PropertyInfo comapnyname = new PropertyInfo();
		PropertyInfo firstname = new PropertyInfo();
		PropertyInfo lastname1 = new PropertyInfo();
		PropertyInfo phone = new PropertyInfo();
		PropertyInfo emailid = new PropertyInfo();
		PropertyInfo ratingid = new PropertyInfo();
		PropertyInfo leadsrcid = new PropertyInfo();
		PropertyInfo indusid1 = new PropertyInfo();
		PropertyInfo token1 = new PropertyInfo();
		PropertyInfo dbname1 = new PropertyInfo();
		System.out.println("made the property info");
		// Set Username
		/*
		 * companyid1.setName("CompanyID"); companyid1.setValue(companyid1);
		 * companyid1.setType(integer.class); request.addProperty(companyid1);
		 */
		dbname1.setName("dbname");
		//Set dataType
		dbname1.setValue(dbname);
		//Set dataType
		dbname1.setType(String.class);
		//Add the property to request object
		request.addProperty(dbname1);
		ID.setName("id");
		ID.setValue(id);
		ID.setType(integer.class);
		request.addProperty(ID);
		comapnyname.setName("companyname");
		comapnyname.setValue(companyname);
		comapnyname.setType(String.class);
		request.addProperty(comapnyname);
		firstname.setName("FirstName");
		// Set Value
		firstname.setValue(firname);
		// Set dataType
		firstname.setType(String.class);
		// Add the property to request object
		request.addProperty(firstname);
      
		lastname1.setName("LastName");
		// Set Value
		lastname1.setValue(lastname);
		// Set dataType
		lastname1.setType(String.class);
		// Add the property to request object
		request.addProperty(lastname1);
		phone.setName("Phone");
		// Set Value
		phone.setValue(mobno);
		// Set dataType
		phone.setType(String.class);
		// Add the property to request object
		request.addProperty(phone);
		
		emailid.setName("Email");
		// Set Value
		emailid.setValue(email);
		// Set dataType
		emailid.setType(String.class);
		// Add the property to request object
		request.addProperty(emailid);
	
		ratingid.setName("RatingID");
		ratingid.setValue(ratid);
		ratingid.setType(integer.class);
		request.addProperty(ratingid);
		leadsrcid.setName("LeadSourceID");
		leadsrcid.setValue(leadid);
		leadsrcid.setType(integer.class);
		request.addProperty(leadsrcid);
		indusid1.setName("IndustryID");
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
