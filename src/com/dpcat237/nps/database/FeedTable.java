package com.dpcat237.nps.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class FeedTable {

	// Database table
	public static final String TABLE_FEED = "feed";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_API_ID = "api_id";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_WEBSITE = "website";
	public static final String COLUMN_FAVICON = "favicon";

	// Database creation SQL statement
	private static final String DATABASE_CREATE = "create table " 
			+ TABLE_FEED
			+ "(" + COLUMN_ID + " integer primary key autoincrement, " 
			+ COLUMN_API_ID + " integer not null ,"
			+ COLUMN_TITLE + " text not null ,"
			+ COLUMN_WEBSITE + " text not null,"
			+ COLUMN_FAVICON + " text not null"
			+ ");";

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}
	
	public static void onDelete(SQLiteDatabase database) {
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_FEED);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		Log.w(FeedTable.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_FEED);
		onCreate(database);
	}
}
