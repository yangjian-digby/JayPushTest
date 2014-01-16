package com.digby.test.jpushtest;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;

import com.digby.localpoint.sdk.core.impl.LPLocalpointService;
import com.digby.mm.android.library.utils.Logger;

public class Splash extends Activity {

	private boolean mLocationServicesEnabled = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Logger.Debug("on create for splash", this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		new StartMainActivityTask().execute();
		Intent i = new Intent("com.digby.lptester.LocationUpdate");
		PendingIntent pendingIntent = PendingIntent.getBroadcast(
				getApplicationContext(), 1000, i, PendingIntent.FLAG_NO_CREATE);
		if (pendingIntent != null) {
			LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			locationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER, 1, 1, pendingIntent);
		} else {
			Logger.Debug("Pending Intent is null ", this);

		}
	}

	private class StartMainActivityTask extends AsyncTask<String, Integer, Integer> {

		protected Integer doInBackground(String... strings) {
			try {
				LPLocalpointService service = LPLocalpointService.getInstance(getApplicationContext());
				service.start();
				Thread.sleep(1500); // Hold the splash screen for a bit
			} catch (Exception error) {
				Logger.Error("StartMainActivityTask_doInBackground", error);
			}
			return 1;
		}

		protected void onProgressUpdate(Integer... progress) {
			try {
				// Don't need this callback
			} catch (Exception error) {
				Logger.Error("StartMainActivityTask_onProgressUpdate", error);
			}
		}

		protected void onPostExecute(Integer result) {
			try {
				Logger.Debug("onPostExecute", Splash.this);
				Intent i = new Intent(Splash.this, MainActivity.class);
				i.putExtra("LocationServicesEnabled", mLocationServicesEnabled);
				startActivity(i);
				finish();
			} catch (Exception error) {
				Logger.Error("SearchForWifiNetworkTask_onPostExecute", error);
			}
		}
	}

}