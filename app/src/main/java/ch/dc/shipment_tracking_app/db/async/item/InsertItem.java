package ch.dc.shipment_tracking_app.db.async.item;

import android.os.AsyncTask;

import ch.dc.shipment_tracking_app.db.dataAccessObject.ItemDao;
import ch.dc.shipment_tracking_app.db.entity.Item;

/**
 * InsertItem AsyncTask.
 */
public class InsertItem extends AsyncTask<Item, Void, Void> {

    /**
     * ItemDao
     */
    private ItemDao itemDao;

    /**
     * InsertItem constructor
     *
     * @param itemDao the ItemDao
     */
    public InsertItem(ItemDao itemDao) {
        this.itemDao = itemDao;
    }


    @Override
    protected Void doInBackground(Item... items) {
        itemDao.insert(items[0]);

        return null;
    }
}
