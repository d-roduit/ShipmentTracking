package ch.dc.shipment_tracking_app;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.textfield.TextInputLayout;

import ch.dc.shipment_tracking_app.location.LocationManageable;
import ch.dc.shipment_tracking_app.location.LocationManager;

public class ClientSendPackageActivity extends BaseActivity implements LocationManageable {

    // Constants
    private static final String REQUESTING_LOCATION_UPDATES_KEY = "REQUESTING_LOCATION_UPDATES_KEY";

    // Views
    private ProgressBar progressbarLocation;
    private TextInputLayout inputSenderAddress;
    private TextInputLayout inputSenderCity;
    private TextInputLayout inputSenderNpa;
    private Button useLocationButton;
    private TextView textviewUnableRetrieveLocation;
    private AutoCompleteTextView dropDownText;

    // Request permission classes
    private ActivityResultLauncher<String> requestPermissionLauncher;

    // Location classes
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_send_package);

        // Initialize XML views.
        progressbarLocation = findViewById(R.id.progressbar_location);
        inputSenderAddress = findViewById(R.id.input_sender_address);
        inputSenderCity = findViewById(R.id.input_sender_city);
        inputSenderNpa = findViewById(R.id.input_sender_npa);
        useLocationButton = findViewById(R.id.button_use_location);
        textviewUnableRetrieveLocation = findViewById(R.id.textview_unable_retrieve_location);
        dropDownText = findViewById(R.id.shipping_priority_list);

        // Initialize the shipping priority dropdown.
        String[] shipping_priority_items = getResources().getStringArray(R.array.shipping_priority_items);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, R.layout.dropdown_item, shipping_priority_items
        );
        dropDownText.setAdapter(adapter);

        // Initialize the location manager.
        locationManager = new LocationManager(this, this);

        // Initialize the permission launcher.
        requestPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(), isGranted -> {
                    if (isGranted) {
                        System.out.println("PERMISSION WAS GRANTED !");
                        locationManager.startLocationUpdates();
                    } else {
                        System.out.println("PERMISSION WAS **NOT** GRANTED !");
                        Toast.makeText(
                                this,
                                R.string.location_permission_denied,
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });

        // Initialize views eventListeners.
        useLocationButton.setOnClickListener(v -> useLocationButtonAction());

        // Restore state if app was resumed.
        updateValuesFromBundle(savedInstanceState);
    }

    //Method to close the keyboard when we click outside of an input field.
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * This method is called when the useLocationButton is clicked.
     */
    private void useLocationButtonAction() {
        String locationPermission = locationManager.getLocationPermission();

        if (ActivityCompat.checkSelfPermission(this, locationPermission)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(locationPermission);
            return;
        }

        locationManager.startLocationUpdates();
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.stopLocationUpdates();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (locationManager.isRequestingLocationUpdates()) {
            locationManager.startLocationUpdates();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(REQUESTING_LOCATION_UPDATES_KEY,
                locationManager.isRequestingLocationUpdates());
        super.onSaveInstanceState(outState);
    }

    /**
     * Restore the activity values if app was resumed.
     *
     * @param savedInstanceState The saved instance state.
     */
    private void updateValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            return;
        }

        // Update the value of requestingLocationUpdates from the Bundle.
        if (savedInstanceState.keySet().contains(REQUESTING_LOCATION_UPDATES_KEY)) {
            locationManager.setRequestingLocationUpdates(
                    savedInstanceState.getBoolean(REQUESTING_LOCATION_UPDATES_KEY)
            );
        }
    }

    @Override
    public void onLocationUpdatesStart() {
        System.out.println("LocationStart");
        progressbarLocation.setVisibility(View.VISIBLE);
        textviewUnableRetrieveLocation.setVisibility(View.GONE);
    }

    @Override
    public void onLocationFailed() {
        System.out.println("LocationFailed");
        progressbarLocation.setVisibility(View.GONE);
        textviewUnableRetrieveLocation.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLocationNotFound() {
        System.out.println("LocationNotFound");
        progressbarLocation.setVisibility(View.GONE);
        textviewUnableRetrieveLocation.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLocationFound(Address address) {
        System.out.println("LocationFound");

        progressbarLocation.setVisibility(View.GONE);
        textviewUnableRetrieveLocation.setVisibility(View.GONE);

        // Fetch the postal code and the city name
        String street = address.getThoroughfare();
        String houseNumber = address.getSubThoroughfare();
        String postalCode = address.getPostalCode();
        String city = address.getLocality();
        String streetHouseAddress = street + " " + houseNumber;

        System.out.println("getAddressLine : " + address.getAddressLine(0));
        System.out.println("getAdminArea : " + address.getAdminArea());
        System.out.println("getCountryCode : " + address.getCountryCode());
        System.out.println("getCountryName : " + address.getCountryName());
        System.out.println("getFeatureName : " + address.getFeatureName());
        System.out.println("getPremises : " + address.getPremises());
        System.out.println("getSubAdminArea : " + address.getSubAdminArea());
        System.out.println("getSubLocality : " + address.getSubLocality());

        if (street != null && houseNumber!= null) {
            inputSenderAddress.getEditText().setText(streetHouseAddress);
        }
        inputSenderCity.getEditText().setText(city);
        inputSenderNpa.getEditText().setText(postalCode);
    }

    @Override
    public void onLocationUpdatesStopped() {
        System.out.println("LocationStopped");
        progressbarLocation.setVisibility(View.GONE);
    }
}