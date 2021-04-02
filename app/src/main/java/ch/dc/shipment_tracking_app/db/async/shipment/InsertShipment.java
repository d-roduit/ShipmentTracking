package ch.dc.shipment_tracking_app.db.async.shipment;

import android.os.AsyncTask;

import ch.dc.shipment_tracking_app.db.dataAccessObject.ShipmentDao;
import ch.dc.shipment_tracking_app.db.entity.Shipment;

/**
 * InsertShipment AsyncTask.
 */
public class InsertShipment extends AsyncTask<Shipment, Void, Void> {

    /**
     * ShipmentDao
     */
    private ShipmentDao shipmentDao;

    /**
     * InsertShipment constructor
     *
     * @param shipmentDao the ShipmentDao
     */
    public InsertShipment(ShipmentDao shipmentDao) {
        this.shipmentDao = shipmentDao;
    }


    @Override
    protected Void doInBackground(Shipment... shipments) {
        shipmentDao.insert(shipments[0]);

        return null;
    }
}
