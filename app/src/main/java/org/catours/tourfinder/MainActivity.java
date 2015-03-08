package org.catours.tourfinder;

import java.util.List;

import org.catours.tourfinder.db.TourItem;
import org.catours.tourfinder.db.ToursDataSource;
import org.catours.tourfinder.utilities.AppConstants;
import org.catours.tourfinder.xml.ToursPullParser;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity {

	public static final String LOGTAG="EXPLORECA";

	private List<TourItem> mTours;

	ToursDataSource mDataSource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mDataSource = new ToursDataSource(this);
		mDataSource.open();

		mTours = mDataSource.findAll();
		if (mTours.size() == 0) {
			mTours = createData();
		}

		refreshDisplay();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.menu_all:
			mTours = mDataSource.findAll();
			refreshDisplay();
			break;

		case R.id.menu_cheap:
			mTours = mDataSource.findFiltered("price <= 300", "price ASC");
			refreshDisplay();
			break;

		case R.id.menu_fancy:
			mTours = mDataSource.findFiltered("price >= 1000", "price DESC");
			refreshDisplay();
			break;

 		case R.id.menu_gp_license_dialog:
 			Intent intent = new Intent(this, GPSLicenseActivity.class);
 			startActivity(intent);
 			break;
			
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void refreshDisplay() {
		ArrayAdapter<TourItem> adapter = new TourListAdapter(this, mTours);
		setListAdapter(adapter);
	}

	@Override
	protected void onResume() {
		super.onResume();
		mDataSource.open();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mDataSource.close();
	}

	private List<TourItem> createData() {
		ToursPullParser parser = new ToursPullParser();
		List<TourItem> tours = parser.parseXML(this);

		for (TourItem tour : tours) {
			mDataSource.create(tour);
		}
		
		return mDataSource.findAll();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		TourItem tour = mTours.get(position);

		Intent intent = new Intent(this, TourDetailActivity.class);

		intent.putExtra(".model.Tour", tour);
		startActivityForResult(intent, AppConstants.TOUR_DETAIL_ACTIVITY);

	}

}
