package ch.dc.shipment_tracking_app.db.repository;


import androidx.lifecycle.LiveData;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import ch.dc.shipment_tracking_app.db.entity.Shipment;
import ch.dc.shipment_tracking_app.db.firebase.ShipmentListLiveData;

/**
 * Shipment Repository.
 */
public class ShipmentRepository {

    private static ShipmentRepository instance;

    /**
     * ShipmentRepository constructor*
     */
    public static ShipmentRepository getInstance() {
        if (instance == null) {
            synchronized (ShipmentRepository.class) {
                if (instance == null) {
                    instance = new ShipmentRepository();
                }
            }
        }
        return instance;
    }

    /**
     * Method to get Shipments by a shipping number
     * @return a livedata list of Shipments
     */
    public LiveData<List<Shipment>> getShipmentByShippingNumber(final String id) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("items")
                .child(id)
                .child("shipments");

        return new ShipmentListLiveData(reference);
    }

    /**
     * Method to insert a Shipment in the database
     *
     * @param shipment the Shipment to insert
     */
    public void insert(final Shipment shipment) {
        String id = FirebaseDatabase.getInstance().getReference("shipments").push().getKey();
        FirebaseDatabase.getInstance().getReference("shipments")
                .child(id)
                .setValue(shipment);
    }

    /**
     * Method to delete a Shipment
     *
     * @param shipment the Shipment to delete
     */
    public void delete(final Shipment shipment) {
        FirebaseDatabase.getInstance().getReference("shipments")
                .child(shipment.getId())
                .removeValue();
    }

    /**
     * Method to update a Shipment
     *
     * @param shipment the Shipment to update
     */
    public void update(Shipment shipment) {
        FirebaseDatabase.getInstance().getReference("shipments")
                .child(shipment.getId())
                .updateChildren(shipment.toMap());
    }

}
