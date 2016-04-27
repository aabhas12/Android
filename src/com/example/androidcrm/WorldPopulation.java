package com.example.androidcrm;

public class WorldPopulation {
	private String name;
	private String companyname;
	private String rating;
	private String phno;
 
	public WorldPopulation(String name, String companyname, String rating,String phone) {
		this.name = name;
		this.companyname= companyname;
		
		this.phno=phone;
		this.rating=rating;
	}
 
	public String getname() {
		return this.name;
	}
 
	public String getcompanyname() {
		return this.companyname;
	}
 
	public String phone() {
		return this.phno;
	}
	public String rating() {
		return this.rating;
	}
	
}