package ch.dc.shipment_tracking_app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;
import java.util.Map;

import ch.dc.shipment_tracking_app.internationalization.LocaleHelper;

public abstract class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static Map<Integer, Class<? extends BaseActivity>> navigationDrawerRoutes;

    private DrawerLayout drawer;

    private String initialLocale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialLocale = LocaleHelper.getLocaleFromSharedPreference(getBaseContext());

        // Set a default activity title. Any activity can define its proper title by calling this method.
        setTitle(getString(R.string.app_name));

        // Define the navigation drawer routes.
        if (navigationDrawerRoutes == null) {
            navigationDrawerRoutes = new HashMap<>();
            navigationDrawerRoutes.put(R.id.nav_home, MainActivity.class);
            navigationDrawerRoutes.put(R.id.nav_settings, SettingsActivity.class);
            navigationDrawerRoutes.put(R.id.nav_about_us, AboutUsActivity.class);
            navigationDrawerRoutes.put(R.id.nav_questions_answers, QuestionsAnswersActivity.class);
        }
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Class<? extends BaseActivity> callingClass = this.getClass();
        Class<? extends BaseActivity> destinationClass = navigationDrawerRoutes.get(item.getItemId());

        if (destinationClass != null) {
            if (callingClass != destinationClass) {
                redirectActivity(this, destinationClass);
            }
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * When the drawer is open, pressing on the back button will close the drawer
     * instead of going back.
     */
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Enable the navigation drawer on an activity.
     * Call this method in the {@code onCreate()} method of your activity if you want to use
     * the navigation drawer.
     *
     */
    public void attachNavigationMenu() {
        //Make our toolbar as the action bar.
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Add the menu button on the app bar and moves it dynamically when the drawer opens or closes.
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    /**
     * Convient method to redirect to an activity
     * @param context The context calling the method.
     * @param aClass The class to go to.
     */
    public final void redirectActivity(Activity context, Class<?> aClass) {
        Intent intent = new Intent(context, aClass);
        context.startActivity(intent);
    }
}