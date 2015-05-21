package com.example.myone.service.implement;

import java.util.ArrayList;

import com.example.myone.db.ViewspotDBManager;
import com.example.myone.model.Viewspot;
import com.example.myone.service.IViewspotService;

public class ViewspotService implements IViewspotService {
	ViewspotDBManager dbmgr;
	public ViewspotService(ViewspotDBManager mgr) {
		dbmgr = mgr; 
	}
	
	@Override
	public void saveViewspot(Viewspot viewspot) {
		// TODO Auto-generated method stub
		dbmgr.addViewspot(viewspot);
	}

	@Override
	public ArrayList<Viewspot> getViewspotsByPark(Long ParkID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Viewspot> getViewspotByName(String ViewspotName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Viewspot getViewspotByID(Long ViewspotID) {
		// TODO Auto-generated method stub
		return null;
	}

}
