package com.example.myone.service;

import java.util.ArrayList;

import com.example.myone.model.Viewspot;

public interface IViewspotService {
	public void saveViewspot(Viewspot viewspot);
	public ArrayList<Viewspot> getViewspotsByPark(Long ParkID);
	public ArrayList<Viewspot> getViewspotByName(String ViewspotName);
	public Viewspot getViewspotByID(Long ViewspotID);
	
}
