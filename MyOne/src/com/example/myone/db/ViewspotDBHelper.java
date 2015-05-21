package com.example.myone.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class ViewspotDBHelper extends SQLiteOpenHelper {
	private static final String DB_NAME = "myone.db"; //数据库名称
    private static final int version = 1; //数据库版本
    
    private static final String DB_CREATE_SQL = "CREATE TABLE  IF NOT EXISTS [Park] ([Id] INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL,[parkname] NVARCHAR(200)  UNIQUE NOT NULL,[descript] NVARCHAR(300)  NULL,[city] NVARCHAR(50)  NOT NULL,[citycode] NVARCHAR(30)  NOT NULL,[descriptsound] NVARCHAR(300)  NULL);" +
    		"CREATE TABLE IF NOT EXISTS [Viewspot] ([Id] INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL,[viewspotname] NVARCHAR(200)  UNIQUE NOT NULL,[descript] NVARCHAR(500)  NULL,[parkid] INTEGER  NOT NULL,[descriptsound] NVARCHAR(300)  NULL);";
	
	public ViewspotDBHelper(Context context) {
		super(context, DB_NAME, null, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(DB_CREATE_SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
