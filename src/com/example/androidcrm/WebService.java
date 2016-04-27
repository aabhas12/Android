package com.example.androidcrm;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


public class WebService {
	//Namespace of the Webservice - can be found in WSDL
	private static String NAMESPACE = "http://tempuri.org/";
	
	
	//Webservice URL - WSDL File location	
	private static String URL = "http://192.168.164.50:8016/Service1.asmx";//Make sure you changed IP address
	//SOAP Action URI again Namespace + Web method name
	private static String SOAP_ACTION = "http://tempuri.org/";
	static boolean loginStatus;
	public static boolean invokeLoginWS12(String token,String dbname, String webMethName) {
		loginStatus = false;
		// Create request
		SoapObject request = new SoapObject(NAMESPACE, webMethName);
		// Property which holds input parameters
		PropertyInfo token1 = new PropertyInfo();
		PropertyInfo dbname1 = new PropertyInfo();
		
		token1.setName("token");
		// Set Value
		token1.setValue(token);
		// Set dataType
		token1.setType(String.class);
		// Add the property to request object
		request.addProperty(token1);
		dbname1.setName("db");
		// Set Value
		dbname1.setValue(dbname);
		// Set dataType
		dbname1.setType(String.class);
		// Add the property to request object
		request.addProperty(dbname1);
		//Set Password
		// Create envelope
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
		 loginStatus =Boolean.getBoolean(response.toString());
		 System.out.println(loginStatus);
		} catch (Exception e) {
			//Assign Error Status true in static variable 'errored'
			
			e.printStackTrace();
		}
	
	
		return loginStatus;
	}
}
