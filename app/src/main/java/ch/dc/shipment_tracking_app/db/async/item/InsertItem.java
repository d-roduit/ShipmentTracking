package ch.dc.shipment_tracking_app.db.async.item;

import android.content.Context;
import android.os.AsyncTask;

import ch.dc.shipment_tracking_app.db.AppDatabase;
import ch.dc.shipment_tracking_app.db.entity.Item;

/**
 * InsertItem AsyncTask.
 */
public class InsertItem extends AsyncTask<Item, Void, Void> {

    /**
     * The App database
     */
    private AppDatabase database;

    /**
     * InsertItem constructor
     *
     * @param context the context
     */
    public InsertItem(Context context) {
        database = AppDatabase.getInstance(context);
    }


    @Override
    protected Void doInBackground(Item... items) {
        try{
            for(Item item : items) {
                database.itemDao().insert(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
