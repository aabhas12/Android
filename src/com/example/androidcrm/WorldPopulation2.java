package com.example.androidcrm;

public class WorldPopulation2 {
	private String name;
	private String custtype;
	private String mobile;
	private String cldate;
 
	public WorldPopulation2(String name, String cust, String mobile,String cldate) {
		this.name = name;
		this.custtype=cust;
		
		this.mobile=mobile;
		this.cldate=cldate;
	}
 
	public String getname() {
		return this.name;
	}
 
	public String getcust() {
		return this.custtype;
	}
 
	public String getmobile() {
		return this.mobile;
	}
	public String getcldate() {
		return this.cldate;
	}
	
}