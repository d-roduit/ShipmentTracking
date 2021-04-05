package ch.dc.shipment_tracking_app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import ch.dc.shipment_tracking_app.db.async.OnPostAsyncQueryExecuted;
import ch.dc.shipment_tracking_app.db.async.item.CountShippingNumber;
import ch.dc.shipment_tracking_app.db.entity.Item;
import ch.dc.shipment_tracking_app.db.repository.ItemRepository;

/**
 * Item ViewModel.
 */
public class ItemViewModel extends AndroidViewModel {

    /**
     * The Item Repository
     */
    private final ItemRepository itemRepository;

    /**
     * ItemViewModel constructor
     *
     * @param application the application
     */
    public ItemViewModel(@NonNull Application application) {
        super(application);
        itemRepository = new ItemRepository(application);
    }

    /**
     * Method to insert an Item
     *
     * @param item the Item to insert
     * @param onPostAsyncQueryExecuted The callback interface
     */
    public void insert(Item item, OnPostAsyncQueryExecuted<Void> onPostAsyncQueryExecuted) {
        itemRepository.insert(item, onPostAsyncQueryExecuted);
    }

    /**
     * Method to delete an Item
     *
     * @param item the Item to delete
     * @param onPostAsyncQueryExecuted The callback interface
     */
    public void delete(Item item, OnPostAsyncQueryExecuted<Integer> onPostAsyncQueryExecuted) {
        itemRepository.delete(item, onPostAsyncQueryExecuted);
    }

    /**
     * Method to update an Item
     *
     * @param item the Item to update
     * @param onPostAsyncQueryExecuted The callback interface
     */
    public void update(Item item, OnPostAsyncQueryExecuted<Integer> onPostAsyncQueryExecuted) {
        itemRepository.update(item, onPostAsyncQueryExecuted);
    }

    public LiveData<Item> getItemByShippingNumber(int shippingNumber) {
        return itemRepository.getItemByShippingNumber(shippingNumber);
    }

    /**
     * Method to count the number of occurrences of a shipping number
     * @param shippingNumber A shipping number
     * @param onPostAsyncQueryExecuted The callback interface
     */
    public void countShippingNumber(int shippingNumber, OnPostAsyncQueryExecuted<Integer> onPostAsyncQueryExecuted) {
        itemRepository.countShippingNumber(shippingNumber, onPostAsyncQueryExecuted);
    }

}
