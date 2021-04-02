package ch.dc.shipment_tracking_app.db.repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import ch.dc.shipment_tracking_app.db.AppDatabase;
import ch.dc.shipment_tracking_app.db.async.item.DeleteItem;
import ch.dc.shipment_tracking_app.db.async.item.InsertItem;
import ch.dc.shipment_tracking_app.db.async.item.UpdateItem;
import ch.dc.shipment_tracking_app.db.dataAccessObject.ItemDao;
import ch.dc.shipment_tracking_app.db.entity.Item;

/**
 * Item Repository.
 */
public class ItemRepository {

    /**
     * ItemDao
     */
    private ItemDao itemDao;


    /**
     * ItemRepository constructor
     *
     * @param application the application
     */
    public ItemRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        itemDao = database.itemDao();
    }

    /**
     * Method to get an Item by its shippingNumber
     *
     * @param shippingNumber the shipping number
     * @return the Item
     */
    public LiveData<Item> getItemByShippingNumber(int shippingNumber) {
        return itemDao.getItemByShippingNumber(shippingNumber);
    }

    /**
     * Method to insert an Item in the database
     *
     * @param item the Item to insert
     */
    public void insert(Item item) {
        new InsertItem(itemDao).execute(item);
    }

    /**
     * Method to delete an Item
     *
     * @param item the Item to delete
     */
    public void delete(Item item) {
        new DeleteItem(itemDao).execute(item);
    }

    /**
     * Method to update an Item
     *
     * @param item the Item to update
     */
    public void update(Item item) {
        new UpdateItem(itemDao).execute(item);
    }

}
