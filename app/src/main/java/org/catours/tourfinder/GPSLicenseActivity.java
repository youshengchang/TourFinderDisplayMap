package org.catours.tourfinder;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesUtil;

public class GPSLicenseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gps_license);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
//		display license terms here
        String licence = GooglePlayServicesUtil.getOpenSourceSoftwareLicenseInfo(this);
        TextView tv = (TextView) findViewById(R.id.gps_license_text);
        if(licence != null){
            tv.setText(licence);
        }else{
            tv.setText("Google Play Services is not installed in this device.");
        }
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
	
}
