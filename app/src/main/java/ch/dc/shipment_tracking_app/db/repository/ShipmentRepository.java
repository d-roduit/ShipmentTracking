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
    public final static String referenceName = "shipments";

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
     * @return a liveData list of Shipments
     */
    public LiveData<List<Shipment>> getShipmentsByShippingNumber(final int shippingNumber) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference(referenceName)
                .child(String.valueOf(shippingNumber));

        return new ShipmentListLiveData(reference);
    }

    /**
     * Method to insert a Shipment in the database
     *
     * @param shipment the Shipment to insert
     */
    public void insert(final Shipment shipment) {
        String id = FirebaseDatabase.getInstance()
                .getReference(referenceName)
                .child(String.valueOf(shipment.getShippingNumber()))
                .push()
                .getKey();

        if (id != null) {
            FirebaseDatabase.getInstance()
                    .getReference(referenceName)
                    .child(String.valueOf(shipment.getShippingNumber()))
                    .child(id)
                    .setValue(shipment);
        }
    }

    /**
     * Method to delete a Shipment
     *
     * @param shipment the Shipment to delete
     */
    public void delete(final Shipment shipment) {
        FirebaseDatabase.getInstance()
                .getReference(referenceName)
                .child(String.valueOf(shipment.getShippingNumber()))
                .child(shipment.getId())
                .removeValue();
    }

    /**
     * Method to update a Shipment
     *
     * @param shipment the Shipment to update
     */
    public void update(Shipment shipment) {
        FirebaseDatabase.getInstance()
                .getReference(referenceName)
                .child(String.valueOf(shipment.getShippingNumber()))
                .child(shipment.getId())
                .updateChildren(shipment.toMap());
    }
}
