package ch.dc.shipment_tracking_app.db.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import ch.dc.shipment_tracking_app.db.AppDatabase;
import ch.dc.shipment_tracking_app.db.async.item.DeleteItem;
import ch.dc.shipment_tracking_app.db.async.item.InsertItem;
import ch.dc.shipment_tracking_app.db.async.item.UpdateItem;
import ch.dc.shipment_tracking_app.db.entity.Item;

/**
 * Item Repository.
 */
public class ItemRepository {

    /**
     * ItemRepository static instance
     */
    private static ItemRepository instance;

    /**
     * ItemRepository private constructor
     */
    private ItemRepository() {}

    /**
     * Method to get an instance of ItemRepository
     * @return the ItemRepository instance
     */
    public static ItemRepository getInstance() {
        if(instance == null) {
            synchronized (ItemRepository.class) {
                if(instance == null) {
                    instance = new ItemRepository();
                }
            }
        }
        return instance;
    }

    /**
     * Method to get an Item by its shippingNumber
     *
     * @param shippingNumber the shipping number
     * @param context the context
     * @return the Item
     */
    public LiveData<Item> getItemByShippingNumber(int shippingNumber, Context context) {
        return AppDatabase.getInstance(context).itemDao().getItemByShippingNumber(shippingNumber);
    }

    /**
     * Method to insert an Item in the database
     *
     * @param item the Item to insert
     * @param context the context
     */
    public void insert(Item item, Context context) {
        new InsertItem(context).execute(item);
    }

    /**
     * Method to delete an Item
     *
     * @param item the Item to delete
     * @param context the context
     */
    public void delete(Item item, Context context) {
        new DeleteItem(context).execute(item);
    }

    /**
     * Method to update an Item
     *
     * @param item the Item to update
     * @param context the context
     */
    public void update(Item item, Context context) {
        new UpdateItem(context).execute(item);
    }

}
