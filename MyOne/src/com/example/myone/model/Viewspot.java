package com.example.myone.model;

import java.util.ArrayList;


public class Viewspot {
	private Long ID;
	private String Name;
	private String Descript;
	private String DescriptSound;
	private Long ParkID;
	private ArrayList<VLocation> Locations;
	
	
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
	
	public String getDescriptSound() {
		return DescriptSound;
	}
	public void setDescriptSound(String descriptSound) {
		DescriptSound = descriptSound;
	}
	public Long getParkID() {
		return ParkID;
	}
	public void setParkID(Long ParkID) {
		ParkID = ParkID;
	}
	public ArrayList<VLocation> getLocations() {
		return Locations;
	}
	public void setLocations(ArrayList<VLocation> locations) {
		Locations = locations;
	}

	
	
}
