package ch.dc.shipment_tracking_app;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.preference.PreferenceManager;

import com.google.android.material.textfield.TextInputLayout;

import ch.dc.shipment_tracking_app.location.LocationManageable;
import ch.dc.shipment_tracking_app.location.LocationManager;

public class ClientSendPackageActivity extends BaseActivity implements LocationManageable {

    // Constants
    private static final String REQUESTING_LOCATION_UPDATES_KEY = "REQUESTING_LOCATION_UPDATES_KEY";
    public static final String SEND_WEIGHT = "SEND_WEIGHT";
    public static final String SEND_SHIPPING_PRIORITY = "SEND_SHIPPING_PRIORITY";
    public static final String SEND_SENDER_FIRSTNAME = "SEND_SENDER_FIRSTNAME";
    public static final String SEND_SENDER_LASTNAME = "SEND_SENDER_LASTNAME";
    public static final String SEND_SENDER_ADDRESS = "SEND_SENDER_ADDRESS";
    public static final String SEND_SENDER_NPA = "SEND_SENDER_NPA";
    public static final String SEND_SENDER_CITY = "SEND_SENDER_CITY";
    public static final String SEND_RECIPIENT_FIRSTNAME = "SEND_RECIPIENT_FIRSTNAME";
    public static final String SEND_RECIPIENT_LASTNAME = "SEND_RECIPIENT_LASTNAME";
    public static final String SEND_RECIPIENT_ADDRESS = "SEND_RECIPIENT_ADDRESS";
    public static final String SEND_RECIPIENT_NPA = "SEND_RECIPIENT_NPA";
    public static final String SEND_RECIPIENT_CITY = "SEND_RECIPIENT_CITY";

    // Views
    private ProgressBar progressbarLocation;
    private TextInputLayout inputWeight;
    private TextInputLayout inputShippingPriority;
    private TextInputLayout inputSenderFirstname;
    private TextInputLayout inputSenderLastname;
    private TextInputLayout inputSenderAddress;
    private TextInputLayout inputSenderCity;
    private TextInputLayout inputSenderNpa;
    private TextInputLayout inputRecipientFirstname;
    private TextInputLayout inputRecipientLastname;
    private TextInputLayout inputRecipientAddress;
    private TextInputLayout inputRecipientCity;
    private TextInputLayout inputRecipientNpa;
    private Button useLocationButton;
    private Button nextButton;
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

        setTitle(getString(R.string.client_send_package_activity_title));

        // Initialize XML views.
        progressbarLocation = findViewById(R.id.progressbar_location);
        inputWeight = findViewById(R.id.input_weight);
        inputShippingPriority = findViewById(R.id.input_shippingPriority);
        inputSenderFirstname = findViewById(R.id.input_sender_firstname);
        inputSenderLastname = findViewById(R.id.input_sender_lastname);
        inputSenderAddress = findViewById(R.id.input_sender_address);
        inputSenderCity = findViewById(R.id.input_sender_city);
        inputSenderNpa = findViewById(R.id.input_sender_npa);
        inputRecipientFirstname = findViewById(R.id.input_recipient_firstname);
        inputRecipientLastname = findViewById(R.id.input_recipient_lastname);
        inputRecipientAddress = findViewById(R.id.input_recipient_address);
        inputRecipientCity = findViewById(R.id.input_recipient_city);
        inputRecipientNpa = findViewById(R.id.input_recipient_npa);
        useLocationButton = findViewById(R.id.button_use_location);
        nextButton = findViewById(R.id.button_next);
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
                        locationManager.startLocationUpdates();
                    } else {
                        Toast.makeText(
                                this,
                                R.string.location_permission_denied,
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });

        // Initialize views eventListeners.
        useLocationButton.setOnClickListener(v -> useLocationButtonAction());

        // Restore state if app was resumed.
        updateValuesFromBundle(savedInstanceState);

        //Button next listener
        nextButton.setOnClickListener(v -> {
            boolean areInputsValid = InputValidator.validateInputs(
                    inputWeight, inputShippingPriority, inputSenderFirstname, inputSenderLastname,
                    inputSenderAddress, inputSenderNpa, inputSenderCity, inputRecipientFirstname,
                    inputRecipientLastname, inputRecipientAddress, inputRecipientNpa,
                    inputRecipientCity
            );

            if (areInputsValid) {
                sendPackageInformation();
            }
        });


        //Get the value of each input field.
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        double weight = Double.longBitsToDouble(sharedPreferences.getLong(ClientSendPackageActivity.SEND_WEIGHT, Double.doubleToLongBits(0.0)));
        char shippingPriority = sharedPreferences.getString(ClientSendPackageActivity.SEND_SHIPPING_PRIORITY, " ").charAt(0);
        String senderFirstname = sharedPreferences.getString(ClientSendPackageActivity.SEND_SENDER_FIRSTNAME, "");
        String senderLastname = sharedPreferences.getString(ClientSendPackageActivity.SEND_SENDER_LASTNAME, "");
        String senderAddress = sharedPreferences.getString(ClientSendPackageActivity.SEND_SENDER_ADDRESS, "");
        String senderNpa = sharedPreferences.getString(ClientSendPackageActivity.SEND_SENDER_NPA, "");
        String senderCity = sharedPreferences.getString(ClientSendPackageActivity.SEND_SENDER_CITY, "");
        String recipientFirstname = sharedPreferences.getString(ClientSendPackageActivity.SEND_RECIPIENT_FIRSTNAME, "");
        String recipientLastname = sharedPreferences.getString(ClientSendPackageActivity.SEND_RECIPIENT_LASTNAME, "");
        String recipientAddress = sharedPreferences.getString(ClientSendPackageActivity.SEND_RECIPIENT_ADDRESS, "");
        String recipientNpa = sharedPreferences.getString(ClientSendPackageActivity.SEND_RECIPIENT_NPA, "");
        String recipientCity = sharedPreferences.getString(ClientSendPackageActivity.SEND_RECIPIENT_CITY, "");

        if(weight != 0.0) {
            inputWeight.getEditText().setText("" + weight);
        }
        if(shippingPriority != ' ') {
            dropDownText.setText("" + shippingPriority, false);
        }
        inputSenderFirstname.getEditText().setText(senderFirstname);
        inputSenderLastname.getEditText().setText(senderLastname);
        inputSenderAddress.getEditText().setText(senderAddress);
        inputSenderCity.getEditText().setText(senderCity);
        inputSenderNpa.getEditText().setText(senderNpa);
        inputRecipientFirstname.getEditText().setText(recipientFirstname);
        inputRecipientLastname.getEditText().setText(recipientLastname);
        inputRecipientAddress.getEditText().setText(recipientAddress);
        inputRecipientNpa.getEditText().setText(recipientNpa);
        inputRecipientCity.getEditText().setText(recipientCity);
    }

    /**
     * Sends the package information through an Intent.
     */
    private void sendPackageInformation() {
        double textWeight = Double.parseDouble(inputWeight.getEditText().getText().toString());
        char textShippingPriority = dropDownText.getText().toString().charAt(0);
        String textSenderFirstname = inputSenderFirstname.getEditText().getText().toString();
        String textSenderLastname = inputSenderLastname.getEditText().getText().toString();
        String textSenderAddress = inputSenderAddress.getEditText().getText().toString();
        String textSenderNpa = inputSenderNpa.getEditText().getText().toString();
        String textSenderCity = inputSenderCity.getEditText().getText().toString();
        String textRecipientFirstname = inputRecipientFirstname.getEditText().getText().toString();
        String textRecipientLastname = inputRecipientLastname.getEditText().getText().toString();
        String textRecipientAddress = inputRecipientAddress.getEditText().getText().toString();
        String textRecipientNpa = inputRecipientNpa.getEditText().getText().toString();
        String textRecipientCity = inputRecipientCity.getEditText().getText().toString();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ClientSendPackageActivity.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(SEND_WEIGHT, Double.doubleToLongBits(textWeight));
        editor.putString(SEND_SHIPPING_PRIORITY, String.valueOf(textShippingPriority));
        editor.putString(SEND_SENDER_FIRSTNAME, textSenderFirstname);
        editor.putString(SEND_SENDER_LASTNAME, textSenderLastname);
        editor.putString(SEND_SENDER_ADDRESS, textSenderAddress);
        editor.putString(SEND_SENDER_NPA, textSenderNpa);
        editor.putString(SEND_SENDER_CITY, textSenderCity);
        editor.putString(SEND_RECIPIENT_FIRSTNAME, textRecipientFirstname);
        editor.putString(SEND_RECIPIENT_LASTNAME, textRecipientLastname);
        editor.putString(SEND_RECIPIENT_ADDRESS, textRecipientAddress);
        editor.putString(SEND_RECIPIENT_NPA, textRecipientNpa);
        editor.putString(SEND_RECIPIENT_CITY, textRecipientCity);
        editor.apply();

//        intent.putExtra(SEND_WEIGHT, textWeight);
//        intent.putExtra(SEND_SHIPPING_PRIORITY, textShippingPriority);
//        intent.putExtra(SEND_SENDER_FIRSTNAME, textSenderFirstname);
//        intent.putExtra(SEND_SENDER_LASTNAME, textSenderLastname);
//        intent.putExtra(SEND_SENDER_ADDRESS, textSenderAddress);
//        intent.putExtra(SEND_SENDER_NPA, textSenderNpa);
//        intent.putExtra(SEND_SENDER_CITY, textSenderCity);
//        intent.putExtra(SEND_RECIPIENT_FIRSTNAME, textRecipientFirstname);
//        intent.putExtra(SEND_RECIPIENT_LASTNAME, textRecipientLastname);
//        intent.putExtra(SEND_RECIPIENT_ADDRESS, textRecipientAddress);
//        intent.putExtra(SEND_RECIPIENT_NPA, textRecipientNpa);
//        intent.putExtra(SEND_RECIPIENT_CITY, textRecipientCity);

        redirectActivity(this, ClientPackageConfirmationActivity.class);
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