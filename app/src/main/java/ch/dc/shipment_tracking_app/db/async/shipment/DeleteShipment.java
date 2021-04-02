package ch.dc.shipment_tracking_app.db.async.shipment;

import android.os.AsyncTask;

import ch.dc.shipment_tracking_app.db.dataAccessObject.ShipmentDao;
import ch.dc.shipment_tracking_app.db.entity.Shipment;

/**
 * DeleteShipment AsyncTask.
 */
public class DeleteShipment extends AsyncTask<Shipment, Void, Void> {

    /**
     * ShipmentDao
     */
    private ShipmentDao shipmentDao;

    /**
     * DeleteShipment constructor
     *
     * @param shipmentDao the ShipmentDao
     */
    public DeleteShipment(ShipmentDao shipmentDao) {
        this.shipmentDao = shipmentDao;
    }


    @Override
    protected Void doInBackground(Shipment... shipments) {
        shipmentDao.delete(shipments[0]);

        return null;
    }
}
