package ch.dc.shipment_tracking_app;

import android.os.Bundle;

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setTitle(getString(R.string.settings_nav_title));

        attachNavigationMenu(R.id.settings_drawer_layout);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.preference_fragment_container, new PreferenceFragment())
                .commit();
    }
}