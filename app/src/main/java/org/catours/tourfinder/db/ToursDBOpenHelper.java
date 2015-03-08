package org.catours.tourfinder.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ToursDBOpenHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "tours.db";
	private static final int DATABASE_VERSION = 1;

	public static final String TABLE_TOURS = "tours";
	public static final String COLUMN_ID = "tourId";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_DESC = "description";
	public static final String COLUMN_PRICE = "price";
	public static final String COLUMN_IMAGE = "image";
	public static final String COLUMN_LATITUDE = "latitude";
	public static final String COLUMN_LONGITUDE = "longitude";
	public static final String COLUMN_MARKERTEXT = "markerText";

	private static final String TABLE_CREATE = 
			"CREATE TABLE " + TABLE_TOURS + " (" +
					COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					COLUMN_TITLE + " TEXT, " +
					COLUMN_DESC + " TEXT, " +
					COLUMN_IMAGE + " TEXT, " +
					COLUMN_PRICE + " NUMERIC, " +
					COLUMN_LATITUDE + " NUMERIC, " +
					COLUMN_LONGITUDE + " NUMERIC, " +
					COLUMN_MARKERTEXT + " TEXT" +
					")";

	public ToursDBOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOURS);
		onCreate(db);

		Log.i("ExploreCA", "Database has been upgraded from " + 
				oldVersion + " to " + newVersion);
	}

}
