/**
 * -----------------------------------------------------------------------
 *     Copyright © 2015 ShepHertz Technologies Pvt Ltd. All rights reserved.
 * -----------------------------------------------------------------------
 */
package com.shephertz.app42.push.plugin;

import com.example.androidcrm.Leadwebservice;
import com.example.androidcrm.MainActivity;
import com.example.androidcrm.lead1;
import com.example.androidcrm.notiwebservice;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.widget.Toast;
/**
 * @author Vishnu Garg
 *
 */
public class App42GCMReceiver  extends WakefulBroadcastReceiver {
	
	static boolean errored = false;
	static int error = 0;
	int loginStatus;
	 String message;
	 /* (non-Javadoc)
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
	 */
	@Override
	    public void onReceive(Context context, Intent intent) {
		
		    System.out.println( "TESTING:"+App42GCMService.class.getName());
	        ComponentName comp = new ComponentName(context.getPackageName(),
	        		App42GCMService.class.getName());
	        message = intent
					.getStringExtra(App42GCMService.ExtraMessage);
	        startWakefulService(context, (intent.setComponent(comp)));
	        AsyncCallWS1 task = new AsyncCallWS1();
			// Call execute
			task.execute();
	        setResultCode(Activity.RESULT_OK);
	}
	private class AsyncCallWS1 extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			// Call Web Method
			loginStatus = notiwebservice.invokeLoginWS1("abcd12", null,
					message, "Insertnotify");
			System.out.println("login status:" + loginStatus);
			return null;
		}

		@Override
		// Once WebService returns response
		protected void onPostExecute(Void result) {
			// Make Progress Bar invisible

			// Error status is false
			if (!errored) {
				// Based on Boolean value returned from WebService
				if (loginStatus == 1) {
					// Navigate to Home Screen
					System.out.println("Success");

				} else {
					// Set Error message
					System.out.println("error");
				}
				// Error status is true
			} else {
				if (error == 1) {
					System.out.println("Error occured in invoking webservice1");
				} else if (error == 2) {
					System.out.println("Error occured in invoking webservice2");
					// Toast.makeText(getApplicationContext(),
					// WebService.loginStatus,Toast.LENGTH_SHORT).show();
				} else if (error == 3) {
					// Toast.makeText(getApplicationContext(),
					// WebService.loginStatus,Toast.LENGTH_SHORT).show();
					System.out.println("Error occured in invoking webservice3");
				} else {
					System.out.println("Error occured in invoking webservice");
				}
			}
			// Re-initialize Error Status to False
			errored = false;
			error = 0;
		}

		@Override
		// Make Progress Bar visible
		protected void onPreExecute() {

		}

		@Override
		protected void onProgressUpdate(Void... values) {
		}
	}

}