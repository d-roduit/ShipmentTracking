package ch.dc.shipment_tracking_app;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ch.dc.shipment_tracking_app.internationalization.LocaleHelper;

public abstract class BaseActivity extends AppCompatActivity {

    private String initialLocale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialLocale = LocaleHelper.getLocaleFromSharedPreference(getBaseContext());
    }


    @Override
    protected void attachBaseContext(Context baseContext) {
        String locale = LocaleHelper.getLocaleFromSharedPreference(baseContext);
        Context newContext = LocaleHelper.changeLocale(baseContext, locale);
        super.attachBaseContext(newContext);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String localeFromSharedPreference = LocaleHelper.getLocaleFromSharedPreference(getBaseContext());

        if (initialLocale != null && !initialLocale.equals(localeFromSharedPreference)) {
            recreate();
        }
    }
}