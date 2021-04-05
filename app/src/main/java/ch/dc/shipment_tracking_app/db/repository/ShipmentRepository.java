package ch.dc.shipment_tracking_app.db.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import ch.dc.shipment_tracking_app.db.AppDatabase;
import ch.dc.shipment_tracking_app.db.async.OnPostAsyncQueryExecuted;
import ch.dc.shipment_tracking_app.db.async.shipment.DeleteShipment;
import ch.dc.shipment_tracking_app.db.async.shipment.InsertShipment;
import ch.dc.shipment_tracking_app.db.async.shipment.UpdateShipment;
import ch.dc.shipment_tracking_app.db.dataAccessObject.ShipmentDao;
import ch.dc.shipment_tracking_app.db.entity.Shipment;

/**
 * Shipment Repository.
 */
public class ShipmentRepository {

    /**
     * ShipmentDao
     */
    private final ShipmentDao shipmentDao;

    /**
     * ShipmentRepository constructor
     *
     * @param application the application
     */
    public ShipmentRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        shipmentDao = database.shipmentDao();
    }

    /**
     * Method to get Shipments by a shipping number
     * @param shippingNumber the shipping number
     * @return a livedata list of Shipments
     */
    public LiveData<List<Shipment>> getShipmentByShippingNumber(int shippingNumber) {
        return shipmentDao.getShipmentByShippingNumber(shippingNumber);
    }

    /**
     * Method to insert a Shipment in the database
     *
     * @param shipment the Shipment to insert
     */
    public void insert(Shipment shipment, OnPostAsyncQueryExecuted<Void> onPostAsyncQueryExecuted) {
        new InsertShipment(shipmentDao, onPostAsyncQueryExecuted).execute(shipment);
    }

    /**
     * Method to delete a Shipment
     *
     * @param shipment the Shipment to delete
     */
    public void delete(Shipment shipment, OnPostAsyncQueryExecuted<Integer> onPostAsyncQueryExecuted) {
        new DeleteShipment(shipmentDao, onPostAsyncQueryExecuted).execute(shipment);
    }

    /**
     * Method to update a Shipment
     *
     * @param shipment the Shipment to update
     */
    public void update(Shipment shipment, OnPostAsyncQueryExecuted<Integer> onPostAsyncQueryExecuted) {
        new UpdateShipment(shipmentDao, onPostAsyncQueryExecuted).execute(shipment);
    }

}
