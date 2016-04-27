package com.example.androidcrm;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@SuppressLint("NewApi")
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class WhatsHotFragment extends Fragment {
	@SuppressLint("NewApi")


	public WhatsHotFragment() {
	}

	// Webservice URL - WSDL File location

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_whats_hot,
				container, false);

		return rootView;
	}


}
