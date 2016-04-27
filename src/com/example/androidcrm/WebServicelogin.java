package com.example.androidcrm;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


public class WebServicelogin {
	//Namespace of the Webservice - can be found in WSDL
	private static String NAMESPACE = "http://tempuri.org/";
	
	
	//Webservice URL - WSDL File location	
	private static String URL = "http://192.168.164.50:8016/Service1.asmx";//Make sure you changed IP address
	//SOAP Action URI again Namespace + Web method name
	private static String SOAP_ACTION = "http://tempuri.org/";
	static String loginStatus;
	public static String invokeLoginWS12(String userName,String passWord,String dbname,String mac,int ip,long lati1,long long2, String webMethName) {
		loginStatus = null;
		// Create request
		//float lati1,float longi1,
		SoapObject request = new SoapObject(NAMESPACE, webMethName);
		// Property which holds input parameters
		PropertyInfo unamePI = new PropertyInfo();
		PropertyInfo dbname1 = new PropertyInfo();
		PropertyInfo passPI = new PropertyInfo();
		PropertyInfo macad = new PropertyInfo();
		PropertyInfo ipadd = new PropertyInfo();
		PropertyInfo lati = new PropertyInfo();
		PropertyInfo longi = new PropertyInfo();
		//PropertyInfo macPI = new PropertyInfo();
		// Set Username
		System.out.println("latit:"+lati1);
System.out.println("long1:"+long2);
		unamePI.setName("userName");
		// Set Value
		unamePI.setValue(userName);
		// Set dataType
		unamePI.setType(String.class);
		// Add the property to request object
		request.addProperty(unamePI);
		//Set Password
		passPI.setName("password");
		//Set dataType
		passPI.setValue(passWord);
		//Set dataType
		passPI.setType(String.class);
		//Add the property to request object
		request.addProperty(passPI);
		macad.setName("macaddr");
		//Set dataType
		macad.setValue(mac);
		//Set dataType
		macad.setType(String.class);
		//Add the property to request object
		request.addProperty(macad);
		dbname1.setName("dbname");
		//Set dataType
		dbname1.setValue(dbname);
		//Set dataType
		dbname1.setType(String.class);
		//Add the property to request object
		request.addProperty(dbname1);
		ipadd.setName("ipaddr");
		//Set dataType
		ipadd.setValue(ip);
		//Set dataType
		ipadd.setType(String.class);
		//Add the property to request object
		request.addProperty(ipadd);
	lati.setName("lati");
		//Set dataType
		lati.setValue(lati1);
		//Set dataType
		lati.setType(float.class);
		//Add the property to request object
		request.addProperty(lati);
		longi.setName("longi");
		//Set dataType
		longi.setValue(long2);
		//Set dataType
		longi.setType(float.class);
		//Add the property to request object
		request.addProperty(longi);
	/// Create envelope
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		// Set output SOAP object
		envelope.setOutputSoapObject(request);
		// Create HTTP call object
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		
		try {
			// Invoke web service
			androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);

		} catch (Exception e) {
		
			//Assign Error Status true in static variable 'errored'
			
			e.printStackTrace();
		}
		try {
			// Invoke web servi
		SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
		 loginStatus =response.toString();
		 System.out.println(loginStatus);
		} catch (Exception e) {
			//Assign Error Status true in static variable 'errored'
			
			e.printStackTrace();
		}
	
			

/*
		try {
			// Invoke web service
			androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
			// Get the response
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			// Assign it to  boolean variable variable
			loginStatus =response.toString();

		} catch (Exception e) {
			//Assign Error Status true in static variable 'errored'
			CheckDNLoginActivity.errored = true;
			e.printStackTrace();
		}*/ 
		//Return booleam to calling object
		return loginStatus;
	}
}
