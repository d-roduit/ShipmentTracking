package ch.dc.shipment_tracking_app.db.async.item;

import android.os.AsyncTask;

import ch.dc.shipment_tracking_app.db.async.OnPostAsyncQueryExecuted;
import ch.dc.shipment_tracking_app.db.dataAccessObject.ItemDao;
import ch.dc.shipment_tracking_app.db.entity.Item;

/**
 * InsertItem AsyncTask.
 */
public class InsertItem extends AsyncTask<Item, Void, Void> {

    /**
     * ItemDao
     */
    private final ItemDao itemDao;

    /**
     * A functional interface that provides a callback method for the DAO query.
     * Its only method, called {@code onPostExecute()}, is executed
     * in the onPostExecute() method of the AsyncTask.
     */
    private final OnPostAsyncQueryExecuted<Void> onPostAsyncQueryExecuted;

    /**
     * InsertItem constructor
     *
     * @param itemDao the ItemDao
     */
    public InsertItem(ItemDao itemDao, OnPostAsyncQueryExecuted<Void> onPostAsyncQueryExecuted) {
        this.itemDao = itemDao;
        this.onPostAsyncQueryExecuted = onPostAsyncQueryExecuted;
    }


    @Override
    protected Void doInBackground(Item... items) {
        itemDao.insert(items[0]);

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
