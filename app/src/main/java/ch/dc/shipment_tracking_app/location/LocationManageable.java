package ch.dc.shipment_tracking_app.location;

import android.location.Address;

public interface LocationManageable {
    void onLocationUpdatesStart();
    void onLocationFailed();
    void onLocationNotFound();
    void onLocationFound(Address address);
    void onLocationUpdatesStopped();
}
