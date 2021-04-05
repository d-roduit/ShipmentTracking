package ch.dc.shipment_tracking_app.db.async.shipment;

import android.os.AsyncTask;

import ch.dc.shipment_tracking_app.db.async.OnPostAsyncQueryExecuted;
import ch.dc.shipment_tracking_app.db.dataAccessObject.ShipmentDao;
import ch.dc.shipment_tracking_app.db.entity.Shipment;

/**
 * DeleteShipment AsyncTask.
 */
public class DeleteShipment extends AsyncTask<Shipment, Void, Integer> {

    /**
     * ShipmentDao
     */
    private final ShipmentDao shipmentDao;

    /**
     * A functional interface that provides a callback method for the DAO query.
     * Its only method, called {@code onPostExecute()}, is executed
     * in the onPostExecute() method of the AsyncTask.
     */
    private final OnPostAsyncQueryExecuted<Integer> onPostAsyncQueryExecuted;

    /**
     * DeleteShipment constructor
     *
     * @param shipmentDao the ShipmentDao
     */
    public DeleteShipment(ShipmentDao shipmentDao, OnPostAsyncQueryExecuted<Integer> onPostAsyncQueryExecuted) {
        this.shipmentDao = shipmentDao;
        this.onPostAsyncQueryExecuted = onPostAsyncQueryExecuted;
    }


    @Override
    protected Integer doInBackground(Shipment... shipments) {
        return shipmentDao.delete(shipments[0]);
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if (onPostAsyncQueryExecuted != null) {
            onPostAsyncQueryExecuted.onPostExecute(integer);
        }
    }
}
