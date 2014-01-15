package com.digby.test.jpushtest.preference;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.digby.test.jpushtest.R;
import com.urbanairship.AirshipConfigOptions;
import com.urbanairship.UAirship;
import com.urbanairship.preference.UAPreferenceAdapter;

public class PreferencesActivity extends PreferenceActivity {

    private UAPreferenceAdapter preferenceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AirshipConfigOptions options = UAirship.shared().getAirshipConfigOptions();

        // Only add the push preferences if the pushServiceEnabled is true
        if (options.pushServiceEnabled) {
            this.addPreferencesFromResource(R.xml.push_preferences);
        }

        // Only add the location preferences if the locationServiceEnabled is true
        if (options.locationOptions.locationServiceEnabled) {
            this.addPreferencesFromResource(R.xml.location_preferences);
        }

        // Display the advanced settings
        if (options.pushServiceEnabled) {
            this.addPreferencesFromResource(R.xml.advanced_preferences);
        }

        // Creates the UAPreferenceAdapter with the entire preference screen
        preferenceAdapter = new UAPreferenceAdapter(getPreferenceScreen());
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Activity instrumentation for analytic tracking
        UAirship.shared().getAnalytics().activityStarted(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Activity instrumentation for analytic tracking
        UAirship.shared().getAnalytics().activityStopped(this);

        // Apply any changed UA preferences from the preference screen
        preferenceAdapter.applyUrbanAirshipPreferences();
    }
}
