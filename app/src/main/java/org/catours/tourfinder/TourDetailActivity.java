package org.catours.tourfinder;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;

import org.catours.tourfinder.db.TourItem;
import org.catours.tourfinder.utilities.GooglePlayServiceUtility;

import java.net.URLEncoder;
import java.text.NumberFormat;

public class TourDetailActivity extends Activity {

	TourItem mTour;
    boolean mShowMap;
    GoogleMap mMap;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_tour_detail);

		getActionBar().setDisplayHomeAsUpEnabled(true);

//		Check for map availability
        mShowMap = GooglePlayServiceUtility.isPlayServiceAvailable(this) && initMap();


		Bundle b = getIntent().getExtras();
		mTour = b.getParcelable(".model.Tour");

		displayTourDetails();

	}

    private boolean initMap() {

        if(mMap == null){
            MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
            mMap = mapFragment.getMap();
        }
        return (mMap != null);
    }

    private void displayTourDetails() {

		TextView tv = (TextView) findViewById(R.id.titleText);
		tv.setText(mTour.getTitle());

		NumberFormat nf = NumberFormat.getCurrencyInstance();
		tv = (TextView) findViewById(R.id.priceText);
		tv.setText(nf.format(mTour.getPrice()));

		tv = (TextView) findViewById(R.id.descText);
		tv.setText(mTour.getDescription());

        if(mShowMap){

            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(mTour.getLatLng(),5);
            mMap.moveCamera(update);

            String markerTitle = mTour.getMarkerText().equals("")?
                    mTour.getTitle():
                    mTour.getMarkerText();
            mMap.addMarker(new MarkerOptions()
                .position(mTour.getLatLng())
                .title(markerTitle)
                .anchor(.5f, .5f)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_starmarker))
            );
        }

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_tour_detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_goto_action :
//			Go to external mapping app
			sendToActionIntent();
			break;
		case android.R.id.home:
//			User pressed the 'Up' button on the action bar
			finish();
			break;
		}
		return false;
	}

public void sendToActionIntent() {
//	Send to another mapping app
    StringBuilder uri = new StringBuilder("geo:");
    uri.append(mTour.getLatitude());
    uri.append(",");
    uri.append(mTour.getLongitude());
    uri.append("?z=10");

    if(!mTour.getMarkerText().equals("")){
        uri.append("&q=" + URLEncoder.encode(mTour.getMarkerText()));
    }
    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri.toString()));
    startActivity(intent);
}

}
