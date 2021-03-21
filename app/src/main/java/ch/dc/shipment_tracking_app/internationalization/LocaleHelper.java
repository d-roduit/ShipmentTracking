package ch.dc.shipment_tracking_app.internationalization;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.LocaleList;

import androidx.preference.PreferenceManager;

import java.util.Locale;

import ch.dc.shipment_tracking_app.R;

public class LocaleHelper {

    public static ContextWrapper changeLocale(Context context, String languageCode) {
        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        Context newContext = context;

        if (!languageCode.equals("")) {
            Locale locale = new Locale(languageCode);
            LocaleList localeList = new LocaleList(locale);
            Locale.setDefault(locale);
            config.setLocale(locale);
            config.setLocales(localeList);
            config.setLayoutDirection(locale);
            newContext = context.createConfigurationContext(config);
        }

        return new ContextWrapper(newContext);
    }

    public static String getLocaleFromSharedPreference(Context context) {
        String languagePreferenceDefaultValue = context.getString(R.string.preference_language_english_value);
        String languagePreferenceKey = context.getString(R.string.preference_language_key);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(languagePreferenceKey, languagePreferenceDefaultValue);
    }
}