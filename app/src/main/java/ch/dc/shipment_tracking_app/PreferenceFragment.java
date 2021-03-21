package ch.dc.shipment_tracking_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import ch.dc.shipment_tracking_app.internationalization.LocaleHelper;

public class PreferenceFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.activity_preference, rootKey);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.preference_language_key))) {
            Context activityContext = getActivity().getBaseContext();
            String localeFromSharedPreference = LocaleHelper.getLocaleFromSharedPreference(activityContext);
            LocaleHelper.changeLocale(activityContext, localeFromSharedPreference);
            getActivity().recreate(); // Necessary here because this Activity is currently running and thus a recreate() in onResume() would be too late
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}
