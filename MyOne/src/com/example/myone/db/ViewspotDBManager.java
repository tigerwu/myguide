package com.example.myone.db;

import java.util.ArrayList;

import com.example.myone.model.Viewspot;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class ViewspotDBManager {
	private ViewspotDBHelper helper;  
    private SQLiteDatabase db;  
      
    public ViewspotDBManager(Context context) {  
        helper = new ViewspotDBHelper(context);  
        //��ΪgetWritableDatabase�ڲ�������mContext.openOrCreateDatabase(mName, 0, mFactory);  
        //����Ҫȷ��context�ѳ�ʼ��,���ǿ��԰�ʵ����DBManager�Ĳ������Activity��onCreate��  
        db = helper.getWritableDatabase();  
    } 
    
    public void addViewspot(Viewspot viewspot) {
    	db.beginTransaction();  //��ʼ����  
        try {  
            db.execSQL("INSERT INTO [Viewspot] VALUES(null, ?, ?, ?, ?)", 
            		new Object[]{viewspot.getName(), viewspot.getDescript(), viewspot.getParkID(), viewspot.getDescriptSound()});  
             
            db.setTransactionSuccessful();  //��������ɹ����  
        } finally {  
            db.endTransaction();    //��������  
        } 
    	
    }
    
    public ArrayList<Viewspot> searchViewspotByParkId(Integer parkid) {
    	ArrayList<Viewspot> viewspots = null;
    	
    	return viewspots;
    }

}
