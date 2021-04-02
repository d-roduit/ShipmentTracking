package ch.dc.shipment_tracking_app.db.async.item;

import android.os.AsyncTask;

import ch.dc.shipment_tracking_app.db.dataAccessObject.ItemDao;
import ch.dc.shipment_tracking_app.db.entity.Item;

/**
 * DeleteItem AsyncTask.
 */
public class DeleteItem extends AsyncTask<Item, Void, Void> {

    /**
     * ItemDao
     */
    private ItemDao itemDao;

    /**
     * DeleteItem constructor
     *
     * @param itemDao the ItemDao
     */
    public DeleteItem(ItemDao itemDao) {
        this.itemDao = itemDao;
    }


    @Override
    protected Void doInBackground(Item... items) {
        itemDao.delete(items[0]);

        return null;
    }

}
