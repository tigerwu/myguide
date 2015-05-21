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
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);  
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里  
        db = helper.getWritableDatabase();  
    } 
    
    public void addViewspot(Viewspot viewspot) {
    	db.beginTransaction();  //开始事务  
        try {  
            db.execSQL("INSERT INTO [Viewspot] VALUES(null, ?, ?, ?, ?)", 
            		new Object[]{viewspot.getName(), viewspot.getDescript(), viewspot.getParkID(), viewspot.getDescriptSound()});  
             
            db.setTransactionSuccessful();  //设置事务成功完成  
        } finally {  
            db.endTransaction();    //结束事务  
        } 
    	
    }
    
    public ArrayList<Viewspot> searchViewspotByParkId(Integer parkid) {
    	ArrayList<Viewspot> viewspots = null;
    	
    	return viewspots;
    }

}
