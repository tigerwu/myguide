package com.example.myone.model;

import java.util.ArrayList;

public class Park {
	private Long ID;
	private String Name;
	private String Descript;
	private String DescriptSound;
	private String City;
	private String CityCode;
	private ArrayList<Viewspot> Viewspots;
	
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
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	public String getCityCode() {
		return CityCode;
	}
	public void setCityCode(String cityCode) {
		CityCode = cityCode;
	}
	public ArrayList<Viewspot> getViewspots() {
		return Viewspots;
	}
	public void setViewspots(ArrayList<Viewspot> viewspots) {
		Viewspots = viewspots;
	}
	
	
}
