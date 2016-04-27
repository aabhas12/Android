package com.example.androidcrm;

public class WorldPopulation1 {
	private String name;
	private String type;
	private String website;
	private String email;
 
	public WorldPopulation1(String name, String type, String website,String email) {
		this.name = name;
		this.type=type;
		
		this.website=website;
		this.email=email;
	}
 
	public String getname() {
		return this.name;
	}
 
	public String gettype() {
		return this.type;
	}
 
	public String getwebsite() {
		return this.website;
	}
	public String getemail() {
		return this.email;
	}
	
}