package ch.dc.shipment_tracking_app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import ch.dc.shipment_tracking_app.db.async.OnPostAsyncQueryExecuted;
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
     */
    public void insert(Item item, OnPostAsyncQueryExecuted<Void> onPostAsyncQueryExecuted) {
        itemRepository.insert(item, onPostAsyncQueryExecuted);
    }

    /**
     * Method to delete an Item
     *
     * @param item the Item to delete
     */
    public void delete(Item item, OnPostAsyncQueryExecuted<Integer> onPostAsyncQueryExecuted) {
        itemRepository.delete(item, onPostAsyncQueryExecuted);
    }

    /**
     * Method to update an Item
     *
     * @param item the Item to update
     */
    public void update(Item item, OnPostAsyncQueryExecuted<Integer> onPostAsyncQueryExecuted) {
        itemRepository.update(item, onPostAsyncQueryExecuted);
    }


    public LiveData<Item> getItemByShippingNumber(int shippingNumber) {
        return itemRepository.getItemByShippingNumber(shippingNumber);
    }

}
