package com.example.myone.model;

import java.util.ArrayList;


public class Viewspot {
	private Long ID;
	private String Name;
	private String Descript;
	private Long ParkID;
	private ArrayList<Location> Locations;
	
	
	public Long getID() {
		return ID;
	}
	public void setID(Long iD) {
		ID = iD;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getDescript() {
		return Descript;
	}
	public void setDescript(String descript) {
		Descript = descript;
	}
	
	public Long getParkID() {
		return ParkID;
	}
	public void setParkID(Long ParkID) {
		ParkID = ParkID;
	}
	public ArrayList<Location> getLocations() {
		return Locations;
	}
	public void setLocations(ArrayList<Location> locations) {
		Locations = locations;
	}

	
	
}
