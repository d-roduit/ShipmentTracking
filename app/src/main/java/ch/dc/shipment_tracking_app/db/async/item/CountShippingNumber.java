package ch.dc.shipment_tracking_app.db.async.item;

import android.os.AsyncTask;

import ch.dc.shipment_tracking_app.db.async.OnPostAsyncQueryExecuted;
import ch.dc.shipment_tracking_app.db.dataAccessObject.ItemDao;
import ch.dc.shipment_tracking_app.db.entity.Item;

/**
 * DeleteItem AsyncTask.
 */
public class CountShippingNumber extends AsyncTask<Integer, Void, Integer> {

    /**
     * ItemDao
     */
    private final ItemDao itemDao;

    /**
     * A functional interface that provides a callback method for the DAO query.
     * Its only method, called {@code onPostExecute()}, is executed
     * in the onPostExecute() method of the AsyncTask.
     */
    private final OnPostAsyncQueryExecuted<Integer> onPostAsyncQueryExecuted;

    /**
     * CountShippingNumber constructor
     *
     * @param itemDao the ItemDao
     * @param onPostAsyncQueryExecuted The OnPostAsyncQueryExecuted functional interface
     */
    public CountShippingNumber(ItemDao itemDao, OnPostAsyncQueryExecuted<Integer> onPostAsyncQueryExecuted) {
        this.itemDao = itemDao;
        this.onPostAsyncQueryExecuted = onPostAsyncQueryExecuted;
    }

    @Override
    protected Integer doInBackground(Integer... integers) {
        return itemDao.countShippingNumber(integers[0]);
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if (onPostAsyncQueryExecuted != null) {
            onPostAsyncQueryExecuted.onPostExecute(integer);
        }
    }
}
