package com.example.androidcrm;


public class Industry {
	
	//private variables
	int id;
	String industryname;

	
	// Empty constructor
	public Industry(){
		
	}
	// constructor
	public Industry(int id, String name){
		this.id = id;
		this.industryname = name;
	
	}
	
	
	// getting ID
	public int getID(){
		return this.id;
	}
	
	// setting id
	public void setID(int id){
		this.id = id;
	}
	
	// getting name
	public String getName(){
		return this.industryname;
	}
	
	// setting name
	public void setName(String name){
		this.industryname = name;
	}
	
	
}
