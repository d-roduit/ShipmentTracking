package ch.dc.shipment_tracking_app.db.async.shipment;

import android.content.Context;
import android.os.AsyncTask;

import ch.dc.shipment_tracking_app.db.AppDatabase;
import ch.dc.shipment_tracking_app.db.entity.Shipment;

/**
 * InsertShipment AsyncTask.
 */
public class InsertShipment extends AsyncTask<Shipment, Void, Void> {

    /**
     * The App database
     */
    private AppDatabase database;

    /**
     * InsertShipment constructor
     *
     * @param context the context
     */
    public InsertShipment(Context context) {
        database = AppDatabase.getInstance(context);
    }


    @Override
    protected Void doInBackground(Shipment... shipments) {
        try{
            for(Shipment shipment : shipments) {
                database.shipmentDao().insert(shipment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
