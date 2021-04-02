package ch.dc.shipment_tracking_app.db.async.shipment;

import android.os.AsyncTask;

import ch.dc.shipment_tracking_app.db.dataAccessObject.ShipmentDao;
import ch.dc.shipment_tracking_app.db.entity.Shipment;

/**
 * UpdateShipment AsyncTask.
 */
public class UpdateShipment extends AsyncTask<Shipment, Void, Void> {

    /**
     * ShipmentDao
     */
    private ShipmentDao shipmentDao;

    /**
     * UpdateShipment constructor
     *
     * @param shipmentDao the ShipmentDao
     */
    public UpdateShipment(ShipmentDao shipmentDao) {
        this.shipmentDao = shipmentDao;
    }


    @Override
    protected Void doInBackground(Shipment... shipments) {
        shipmentDao.update(shipments[0]);

        return null;
    }
}
