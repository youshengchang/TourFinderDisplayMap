package org.catours.tourfinder.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ToursDataSource {

	public static final String LOGTAG="EXPLORECA";

	SQLiteOpenHelper dbhelper;
	SQLiteDatabase database;

	private static final String[] allColumns = {
		ToursDBOpenHelper.COLUMN_ID,
		ToursDBOpenHelper.COLUMN_TITLE,
		ToursDBOpenHelper.COLUMN_DESC,
		ToursDBOpenHelper.COLUMN_PRICE,
		ToursDBOpenHelper.COLUMN_IMAGE,
		ToursDBOpenHelper.COLUMN_LATITUDE,
		ToursDBOpenHelper.COLUMN_LONGITUDE,
		ToursDBOpenHelper.COLUMN_MARKERTEXT
	};

	public ToursDataSource(Context context) {
		dbhelper = new ToursDBOpenHelper(context);
	}

	public void open() {
		database = dbhelper.getWritableDatabase();
	}

	public void close() {
		dbhelper.close();
	}

	public TourItem create(TourItem tour) {
		ContentValues values = new ContentValues();
		values.put(ToursDBOpenHelper.COLUMN_TITLE, tour.getTitle());
		values.put(ToursDBOpenHelper.COLUMN_DESC, tour.getDescription());
		values.put(ToursDBOpenHelper.COLUMN_PRICE, tour.getPrice());
		values.put(ToursDBOpenHelper.COLUMN_IMAGE, tour.getImage());
		values.put(ToursDBOpenHelper.COLUMN_LATITUDE, tour.getLatitude());
		values.put(ToursDBOpenHelper.COLUMN_LONGITUDE, tour.getLongitude());
		values.put(ToursDBOpenHelper.COLUMN_MARKERTEXT, tour.getMarkerText());
		long insertid = database.insert(ToursDBOpenHelper.TABLE_TOURS, null, values);
		tour.setId(insertid);
		return tour;
	}

	public List<TourItem> findAll() {

		Cursor cursor = database.query(ToursDBOpenHelper.TABLE_TOURS, allColumns, 
				null, null, null, null, null);

		List<TourItem> tours = cursorToList(cursor);
		return tours;
	}

	public List<TourItem> findFiltered(String selection, String orderBy) {

		Cursor cursor = database.query(ToursDBOpenHelper.TABLE_TOURS, allColumns, 
				selection, null, null, null, orderBy);

		List<TourItem> tours = cursorToList(cursor);
		return tours;
	}

	private List<TourItem> cursorToList(Cursor cursor) {
		List<TourItem> tours = new ArrayList<TourItem>();
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				TourItem tour = new TourItem();
				tour.setId(cursor.getLong(cursor.getColumnIndex(ToursDBOpenHelper.COLUMN_ID)));
				tour.setTitle(cursor.getString(cursor.getColumnIndex(ToursDBOpenHelper.COLUMN_TITLE)));
				tour.setDescription(cursor.getString(cursor.getColumnIndex(ToursDBOpenHelper.COLUMN_DESC)));
				tour.setImage(cursor.getString(cursor.getColumnIndex(ToursDBOpenHelper.COLUMN_IMAGE)));
				tour.setPrice(cursor.getDouble(cursor.getColumnIndex(ToursDBOpenHelper.COLUMN_PRICE)));
				tour.setLatitude(cursor.getDouble(cursor.getColumnIndex(ToursDBOpenHelper.COLUMN_LATITUDE)));
				tour.setLongitude(cursor.getDouble(cursor.getColumnIndex(ToursDBOpenHelper.COLUMN_LONGITUDE)));
				tour.setMarkerText(cursor.getString(cursor.getColumnIndex(ToursDBOpenHelper.COLUMN_MARKERTEXT)));
				tours.add(tour);
			}
		}
		return tours;
	}
	
}
