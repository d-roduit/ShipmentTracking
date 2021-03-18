package ch.dc.shipment_tracking_app.location;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;

public class LocationManager {

    // Constants
    private final Activity activity;
    private String locationPermission;

    // Location classes
    private FusedLocationProviderClient fusedLocationClient;
    private static Geocoder geocoder;
    private LocationCallback locationUpdateCallback;
    private LocationRequest locationRequest;
    private LocationManageable locationManageable;

    // Activity state variables
    private boolean requestingLocationUpdates;

    public LocationManager(@NonNull Activity activity, LocationManageable locationManageable) {
        this.activity = activity;
        this.locationManageable = locationManageable;

        // Initialize locationPermission.
        locationPermission = Manifest.permission.ACCESS_FINE_LOCATION;

        // Initialize requestingLocationUpdates.
        requestingLocationUpdates = false;

        // Initialize the FusedLocationClient.
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);

        // Initialize the Geocoder.
        geocoder = new Geocoder(activity);

        // Initialize the LocationRequest.
        locationRequest = createLocationRequest(1000, 500, LocationRequest.PRIORITY_HIGH_ACCURACY);

        // Initialize the locationUpdateCallback.
        locationUpdateCallback = new LocationCallback() {
            /**
             * This method is called each time the phone updates his location.
             * @param locationResult The updated location of the phone.
             */
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                System.out.println("LocationCallback onLocationResult");
                System.out.println("Nb of Locations found : " + locationResult.getLocations().size());

                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        System.out.println("REVERSE GEOCODING...");
                        reverseGeocode(location);
                        stopLocationUpdates();
                        break;
                    }
                }
            }
        };
    }

    /**
     * Start the location updates.
     * This method will launch a periodic location update.
     */
    public void startLocationUpdates() throws SecurityException {
        System.out.println("StartLocationUpdates method called");

        requestingLocationUpdates = true;

        if (ActivityCompat.checkSelfPermission(activity, locationPermission)
                != PackageManager.PERMISSION_GRANTED) {
            throw new SecurityException("No access permission granted for " + locationPermission);
        }

        locationManageable.onLocationUpdatesStart();

        fusedLocationClient.requestLocationUpdates(locationRequest,
                locationUpdateCallback,
                Looper.getMainLooper());
    }

    /**
     * Stops the location updates.
     */
    public void stopLocationUpdates() {
        requestingLocationUpdates = false;

        fusedLocationClient.removeLocationUpdates(locationUpdateCallback)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("removeLocationUpdates successfull");
                        locationManageable.onLocationUpdatesStopped();
                    }
                });
    }

    /**
     * Sets up the location request.
     *
     * @return The LocationRequest object containing the desired parameters.
     */
    private LocationRequest createLocationRequest(int interval, int fastestInterval, int priority) {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(interval);
        locationRequest.setFastestInterval(fastestInterval);
        locationRequest.setPriority(priority);
        return locationRequest;
    }

    /**
     * Translates a given location (longitude / latitude) into a human readable address.
     * @param location A location.
     */
    private void reverseGeocode(Location location){
        // Got last known location. In some rare situations this can be null.
        if (location == null) {
            locationManageable.onLocationFailed();
            return;
        }

        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    // In this sample, get just a single address
                    1
            );
        } catch (IOException e) {
            locationManageable.onLocationNotFound();
            return;
        }

        // If no addresses found, display location not found message
        if (addresses == null || addresses.size() == 0) {
            locationManageable.onLocationNotFound();
            return;
        }

        // If an address is found, read it into resultMessage
        Address address = addresses.get(0);

        locationManageable.onLocationFound(address);
    }


    public boolean isRequestingLocationUpdates() {
        return requestingLocationUpdates;
    }

    public void setRequestingLocationUpdates(boolean requestingLocationUpdates) {
        this.requestingLocationUpdates = requestingLocationUpdates;
    }

    public String getLocationPermission() {
        return locationPermission;
    }

    public void setLocationPermission(String locationPermission) {
        this.locationPermission = locationPermission;
    }
}
