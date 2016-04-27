package com.example.androidcrm;

public class WorldPopulation3 {
	private String name;
	private String cust;
	private String mobile;
	private String email;
 
	public WorldPopulation3(String name, String cust, String mobile,String email) {
		this.name = name;
		this.cust=cust;
		
		this.mobile=mobile;
		this.email=email;
	}
 
	public String getname() {
		return this.name;
	}
 
	public String getcust() {
		return this.cust;
	}
 
	public String getmobile() {
		return this.mobile;
	}
	public String getemail() {
		return this.email;
	}
	
}