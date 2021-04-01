package ch.dc.shipment_tracking_app.db.async.item;

import android.content.Context;
import android.os.AsyncTask;

import ch.dc.shipment_tracking_app.db.AppDatabase;
import ch.dc.shipment_tracking_app.db.entity.Item;

/**
 * DeleteItem AsyncTask.
 */
public class DeleteItem extends AsyncTask<Item, Void, Void> {

    /**
     * The App database
     */
    private AppDatabase database;

    /**
     * DeleteItem constructor
     *
     * @param context the context
     */
    public DeleteItem(Context context) {
        database = AppDatabase.getInstance(context);
    }


    @Override
    protected Void doInBackground(Item... items) {
        try{
            for(Item item : items) {
                database.itemDao().delete(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
