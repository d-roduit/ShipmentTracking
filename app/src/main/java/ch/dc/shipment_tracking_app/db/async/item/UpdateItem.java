package ch.dc.shipment_tracking_app.db.async.item;

import android.os.AsyncTask;

import ch.dc.shipment_tracking_app.db.dataAccessObject.ItemDao;
import ch.dc.shipment_tracking_app.db.entity.Item;

/**
 * UpdateItem AsyncTask.
 */
public class UpdateItem extends AsyncTask<Item, Void, Void> {

    /**
     * ItemDao
     */
    private ItemDao itemDao;

    /**
     * UpdateItem constructor
     *
     * @param itemDao the ItemDao
     */
    public UpdateItem(ItemDao itemDao) {
        this.itemDao = itemDao;
    }


    @Override
    protected Void doInBackground(Item... items) {
        itemDao.update(items[0]);

        return null;
    }
}
