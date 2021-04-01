package ch.dc.shipment_tracking_app.db.async.shipment;

import android.content.Context;
import android.os.AsyncTask;

import ch.dc.shipment_tracking_app.db.AppDatabase;
import ch.dc.shipment_tracking_app.db.entity.Shipment;

/**
 * DeleteShipment AsyncTask.
 */
public class DeleteShipment extends AsyncTask<Shipment, Void, Void> {

    /**
     * The App database
     */
    private AppDatabase database;

    /**
     * DeleteShipment constructor
     *
     * @param context the context
     */
    public DeleteShipment(Context context) {
        database = AppDatabase.getInstance(context);
    }


    @Override
    protected Void doInBackground(Shipment... shipments) {
        try{
            for(Shipment shipment : shipments) {
                database.shipmentDao().delete(shipment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
