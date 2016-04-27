/**
 * -----------------------------------------------------------------------
 *     Copyright © 2015 ShepHertz Technologies Pvt Ltd. All rights reserved.
 * -----------------------------------------------------------------------
 */
package com.example.androidcrm;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.shephertz.app42.paas.sdk.android.App42API;
import com.shephertz.app42.paas.sdk.android.App42Log;
import com.shephertz.app42.push.plugin.App42GCMController;
import com.shephertz.app42.push.plugin.App42GCMController.App42GCMListener;
import com.shephertz.app42.push.plugin.App42GCMService;
/**
 * @author Vishnu Garg
 *
 */
public class MainActivity12 extends Activity implements App42GCMListener {
	
	private static final String GoogleProjectNo = "509080200440";
	private TextView responseTv;
	private EditText edUserName, edMessage;
	SharedPreferences pref;
	Editor editor;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notification);
		setTitle("Send Notification");
		pref = getApplicationContext().getSharedPreferences("MyPref", 0);
		editor = pref.edit();
		responseTv = ((TextView) findViewById(R.id.response_msg));
		edUserName = ((EditText) findViewById(R.id.uname));
		edMessage = ((EditText) findViewById(R.id.message));
		 App42API.initialize(
				 this,
				 "daf94af6cbc92a5a0ecc86638aa641ec773d105499c1aa163384330689d2ae09",
				 "07bc3ebcc07bf753db2e94049490aacb3ef6044bd531c970ed2bb28775eed4f3");
				 App42Log.setDebug(true);
				 App42API.setLoggedInUser(pref.getString("username", "")) ;
	}

	public void onStart() {
		super.onStart();
		if (App42GCMController.isPlayServiceAvailable(this)) {
			App42GCMController.getRegistrationId(MainActivity12.this,
					GoogleProjectNo, this);
		} else {
			Log.i("App42PushNotification",
					"No valid Google Play Services APK found.");
		}
	}

	/*
	 * * This method is called when a Activty is stop disable all the events if
	 * occuring (non-Javadoc)
	 * 
	 * @see android.app.Activity#onStop()
	 */
	public void onStop() {
		super.onStop();

	}

	/*
	 * This method is called when a Activty is finished or user press the back
	 * button (non-Javadoc)
	 * 
	 * @override method of superclass
	 * 
	 * @see android.app.Activity#onDestroy()
	 */
	public void onDestroy() {
		super.onDestroy();

	}

	/*
	 * called when this activity is restart again
	 * 
	 * @override method of superclass
	 */
	public void onReStart() {
		super.onRestart();

	}

	/*
	 * called when activity is paused
	 * 
	 * @override method of superclass (non-Javadoc)
	 * 
	 * @see android.app.Activity#onPause()
	 */
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Bundle b = new Bundle();
		b.putInt("a", 0);
		Intent i = new Intent(MainActivity12.this, MainActivity.class);
		i.putExtras(b);
		startActivity(i);
		super.onBackPressed();
		
	}
	
	public void onPause() {
		super.onPause();
		unregisterReceiver(mBroadcastReceiver);
		finish();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main3, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title

		switch (item.getItemId()) {
		case R.id.notification:
			Intent i = new Intent(MainActivity12.this, displaynotification.class);
			startActivity(i);
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}
	/*
	 * called when activity is resume
	 * 
	 * @override method of superclass (non-Javadoc)
	 * 
	 * @see android.app.Activity#onResume()
	 */
	public void onResume() {
		super.onResume();
		String message = getIntent().getStringExtra(
				App42GCMService.ExtraMessage);
		if (message != null)
			Log.d("MainActivity-onResume", "Message Recieved :" + message);
		IntentFilter filter = new IntentFilter(
				App42GCMService.DisplayMessageAction);
		filter.setPriority(2);
		registerReceiver(mBroadcastReceiver, filter);
	}

	final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String message = intent
					.getStringExtra(App42GCMService.ExtraMessage);
			Log.i("MainActivity-BroadcastReceiver", "Message Recieved " + " : "
					+ message);
			responseTv.setText(message);

		}
	};

	public void onSendPushClicked(View view) {
		responseTv.setText("Sending Push to User ");
		App42GCMController.sendPushToUser(edUserName.getText().toString(),
				edMessage.getText().toString(), this);

	}

	@Override
	public void onError(String errorMsg) {
		// TODO Auto-generated method stub
		responseTv.setText("Error -" + errorMsg);
	}

	@Override
	public void onGCMRegistrationId(String gcmRegId) {
		// TODO Auto-generated method stub
		responseTv.setText("Registration Id on GCM--" + gcmRegId);
		App42GCMController.storeRegistrationId(this, gcmRegId);
		//if(!App42GCMController.isApp42Registerd(MainActivity12.this))
		App42GCMController.registerOnApp42(App42API.getLoggedInUser(), gcmRegId, this);
	}

	@Override
	public void onApp42Response(final String responseMessage) {
		// TODO Auto-generated method stub
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				responseTv.setText(responseMessage);
			}
		});
	}

	@Override
	public void onRegisterApp42(final String responseMessage) {
		// TODO Auto-generated method stub
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				responseTv.setText(responseMessage);
				App42GCMController.storeApp42Success(MainActivity12.this);
			}
		});
	}
}
