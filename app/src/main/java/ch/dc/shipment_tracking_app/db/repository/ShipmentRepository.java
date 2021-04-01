package ch.dc.shipment_tracking_app.db.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

import ch.dc.shipment_tracking_app.db.AppDatabase;
import ch.dc.shipment_tracking_app.db.async.shipment.DeleteShipment;
import ch.dc.shipment_tracking_app.db.async.shipment.InsertShipment;
import ch.dc.shipment_tracking_app.db.async.shipment.UpdateShipment;
import ch.dc.shipment_tracking_app.db.entity.Shipment;

/**
 * Shipment Repository.
 */
public class ShipmentRepository {

    /**
     * ShipmentRepository static instance
     */
    private static ShipmentRepository instance;

    /**
     * ShipmentRepository private constructor
     */
    private ShipmentRepository() {}

    /**
     * Method to get an instance of ShipmentRepository
     * @return the ShipmentRepository instance
     */
    public static ShipmentRepository getInstance() {
        if(instance == null) {
            synchronized (ShipmentRepository.class) {
                if(instance == null) {
                    instance = new ShipmentRepository();
                }
            }
        }
        return instance;
    }

    /**
     * Method to get all Shipments
     *
     * @param context the context
     * @return a LiveData list of Shipment
     */
    public LiveData<List<Shipment>> getAllShipments(Context context) {
        return AppDatabase.getInstance(context).shipmentDao().getAllShipments();
    }

    /**
     * Method to insert a Shipment in the database
     *
     * @param shipment the Shipment to insert
     * @param context the context
     */
    public void insert(Shipment shipment, Context context) {
        new InsertShipment(context).execute(shipment);
    }

    /**
     * Method to delete a Shipment
     *
     * @param shipment the Shipment to delete
     * @param context the context
     */
    public void delete(Shipment shipment, Context context) {
        new DeleteShipment(context).execute(shipment);
    }

    /**
     * Method to update a Shipment
     *
     * @param shipment the Shipment to update
     * @param context the context
     */
    public void update(Shipment shipment, Context context) {
        new UpdateShipment(context).execute(shipment);
    }

}
