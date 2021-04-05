package ch.dc.shipment_tracking_app.db.async.shipment;

import android.os.AsyncTask;

import ch.dc.shipment_tracking_app.db.async.OnPostAsyncQueryExecuted;
import ch.dc.shipment_tracking_app.db.dataAccessObject.ShipmentDao;
import ch.dc.shipment_tracking_app.db.entity.Shipment;

/**
 * InsertShipment AsyncTask.
 */
public class InsertShipment extends AsyncTask<Shipment, Void, Void> {

    /**
     * ShipmentDao
     */
    private final ShipmentDao shipmentDao;

    /**
     * A functional interface that provides a callback method for the DAO query.
     * Its only method, called {@code onPostExecute()}, is executed
     * in the onPostExecute() method of the AsyncTask.
     */
    private final OnPostAsyncQueryExecuted<Void> onPostAsyncQueryExecuted;


    /**
     * InsertShipment constructor
     *
     * @param shipmentDao the ShipmentDao
     */
    public InsertShipment(ShipmentDao shipmentDao, OnPostAsyncQueryExecuted<Void> onPostAsyncQueryExecuted) {
        this.shipmentDao = shipmentDao;
        this.onPostAsyncQueryExecuted = onPostAsyncQueryExecuted;
    }


    @Override
    protected Void doInBackground(Shipment... shipments) {
        shipmentDao.insert(shipments[0]);

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (onPostAsyncQueryExecuted != null) {
            onPostAsyncQueryExecuted.onPostExecute(aVoid);
        }
    }
}
