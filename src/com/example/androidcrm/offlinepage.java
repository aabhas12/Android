package com.example.androidcrm;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class offlinepage extends Activity {
	// Set Error Status

	Button b;
	SQLiteDatabase db;
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		System.exit(0);
		super.onBackPressed();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.offline);
		db= openOrCreateDatabase("Lead", Context.MODE_PRIVATE, null);
		db.execSQL("CREATE TABLE IF NOT EXISTS lead1(id INTEGER PRIMARY KEY   AUTOINCREMENT,companyname VARCHAR,firstname VARCHAR,lastname Varchar,mobilenumber VARCHAR,email Varchar,Ratingid integer,ldsourceid integer,industrid integer);");
		Cursor c=db.rawQuery("SELECT * FROM lead1", null);
		if(c.getCount()==0)
		{
			showMessage("Error", "No records found");
			return;
		}
		StringBuffer buffer=new StringBuffer();
		while(c.moveToNext())
		{
			buffer.append("id: "+c.getInt(0)+"\n");
			buffer.append("cName: "+c.getString(1)+"\n");
			buffer.append("fName: "+c.getString(2)+"\n");
			buffer.append("lName: "+c.getString(3)+"\n");
			buffer.append("pName: "+c.getString(4)+"\n");
			buffer.append("eName: "+c.getString(5)+"\n");
			buffer.append("rName: "+c.getInt(6)+"\n");
			buffer.append("lName: "+c.getInt(7)+"\n");
			buffer.append("iName: "+c.getInt(8)+"\n");
		
		}
		showMessage("Student Details", buffer.toString());
	}
	  public void showMessage(String title,String message)
	    {
	    	Builder builder=new Builder(this);
	    	builder.setCancelable(true);
	    	builder.setTitle(title);
	    	builder.setMessage(message);
	    	builder.show();
		}
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
