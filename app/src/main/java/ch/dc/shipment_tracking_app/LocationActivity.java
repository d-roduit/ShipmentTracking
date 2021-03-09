package ch.dc.shipment_tracking_app;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class LocationActivity extends AppCompatActivity {

    // Constants
    private static final String LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION;

    // Views
    private Button useLocationButton;
    private TextView locationLabelTextView;
    private TextView locationTextView;

    // Location classes
    private FusedLocationProviderClient fusedLocationProviderClient;
    private ActivityResultLauncher<String> requestPermissionLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        useLocationButton = findViewById(R.id.button_use_location);
        locationLabelTextView = findViewById(R.id.textview_location_label);
        locationTextView = findViewById(R.id.textview_location);

        // Initialize the FusedLocationClient.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Initialize the permission launcher.
        requestPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(), isGranted -> {
                    if (isGranted) {
                        System.out.println("PERMISSION WAS GRANTED !");
//                startTrackingLocation();
                    } else {
                        System.out.println("PERMISSION WAS **NOT** GRANTED !");
                        Toast.makeText(
                                LocationActivity.this,
                                R.string.location_permission_denied,
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });

//        ActivityResultLauncher<String> requestPermissionLauncher = requestPermission(LOCATION_PERMISSION, isGranted -> {
//            if (isGranted) {
//                System.out.println("PERMISSION WAS GRANTED !");
////                startTrackingLocation();
//            } else {
//                System.out.println("PERMISSION WAS **NOT** GRANTED !");
//                Toast.makeText(
//                        LocationActivity.this,
//                        R.string.location_permission_denied,
//                        Toast.LENGTH_SHORT
//                ).show();
//            }
//        });


        useLocationButton.setOnClickListener(v -> {
            if (hasPermission(LOCATION_PERMISSION)) {
                System.out.println("has permission");
//                startTrackingLocation();
            } else {
                System.out.println("has not permission. Requesting permission...");
                requestPermissionLauncher.launch(LOCATION_PERMISSION);
            }
        });
    }

    private boolean hasPermission(String permission) {
        return ActivityCompat.checkSelfPermission(this, permission)
                == PackageManager.PERMISSION_GRANTED;
    }

//    private ActivityResultLauncher<String> requestPermission(String permission,
//                                   ActivityResultCallback<Boolean> activityResultCallback) {
//        ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(
//                new ActivityResultContracts.RequestPermission(), activityResultCallback);
//
//        return requestPermissionLauncher;
//        requestPermissionLauncher.launch(permission);
//    }
//    private void startTrackingLocation() {
//        // Initialize the FusedLocationClient.
//        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//
//        try {
//            fusedLocationProviderClient.getLastLocation()
//                    .addOnSuccessListener(this, location -> {
//                        // Set up the geocoder
//                        Geocoder geocoder = new Geocoder(LocationActivity.this);
//
//                        List<Address> addresses = null;
//                        boolean hasExceptionOccurred = false;
//
//                        try {
//                            addresses = geocoder.getFromLocation(
//                                    location.getLatitude(),
//                                    location.getLongitude(),
//                                    // In this sample, get just a single address
//                                    1
//                            );
//                        } catch (IOException ioException) {
//                            // Catch network or other I/O problems
//                            hasExceptionOccurred = true;
//                            System.out.println("ioException");
//                            locationLabelTextView.setText(getString(R.string.service_not_available));
//                            ioException.printStackTrace();
//                        } catch (IllegalArgumentException illegalArgumentException) {
//                            // Catch invalid latitude or longitude values
//                            hasExceptionOccurred = true;
//                            System.out.println("illegalArgumentException");
//                            locationLabelTextView.setText(getString(R.string.invalid_lat_long_used));
//                            illegalArgumentException.printStackTrace();
//                        } catch (NullPointerException nullPointerException) {
//                            // Catch no last location exception
//                            hasExceptionOccurred = true;
//                            System.out.println("nullPointerException");
//                            locationLabelTextView.setText(getString(R.string.location_not_found));
//                            nullPointerException.printStackTrace();
//                        }
//
//                        // If no addresses found, display location not found message
//                        if (addresses == null || addresses.size() == 0) {
//                            if (!hasExceptionOccurred) {
//                                locationLabelTextView.setText(getString(R.string.location_not_found));
//                            }
//                        } else {
//                            // If an address is found, read it into resultMessage
//                            Address address = addresses.get(0);
//
//                            // Fetch the postal code and the city name
//                            String postalCode = address.getPostalCode();
//                            String city = address.getLocality();
//
//                            locationTextView.setText(postalCode + ", " + city);
//                            locationTextView.setVisibility(View.VISIBLE);
//                            useLocationButton.setVisibility(View.INVISIBLE);
//                        }
//                    })
//                    .addOnFailureListener(this, exception -> {
//                        exception.printStackTrace();
//                        System.out.println("on failure listener");
//                        locationLabelTextView.setText(getString(R.string.tracking_error));
//                    });
//        } catch (SecurityException securityException) {
//            locationLabelTextView.setText(getString(R.string.tracking_error));
//        }
//    }

    private void startTrackingLocation() {
        System.out.println("startTrackingLocation ...");

        try {
            fusedLocationProviderClient.getCurrentLocation(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY, null)
                    .addOnSuccessListener(LocationActivity.this, location -> {
                        System.out.println("ON SUCCESS LISTENER");

                        if (location != null) {
                            System.out.println("-------- LOCATION IS NOT NULL --------");
                        } else {
                            System.err.println("-------- LOCATION IS NULL --------");
                        }
                    })
                    .addOnCompleteListener(LocationActivity.this, task -> {
                        System.out.println("ON COMPLETE LISTENER");
                        Location location = task.getResult();

                        if (location != null) {
                            System.out.println("-------- LOCATION IS NOT NULL --------");
                        } else {
                            System.err.println("-------- LOCATION IS NULL --------");
                        }
                    })
                    .addOnFailureListener(this, exception -> {
                        exception.printStackTrace();
                        System.err.println("ON FAILURE LISTENER");
                        locationLabelTextView.setText(getString(R.string.tracking_error));
                    });
        } catch (SecurityException securityException) {
            securityException.printStackTrace();
        }
    }

    /**
     * Sets up the location request.
     *
     * @return The LocationRequest object containing the desired parameters.
     */
    private LocationRequest getLocationRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }
}
