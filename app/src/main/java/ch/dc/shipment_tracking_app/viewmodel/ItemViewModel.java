package ch.dc.shipment_tracking_app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import ch.dc.shipment_tracking_app.db.entity.Item;
import ch.dc.shipment_tracking_app.db.repository.ItemRepository;

/**
 * Item ViewModel
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

        this.itemRepository = ItemRepository.getInstance();
    }

    /**
     * Method to get an Item by its shipping number
     *
     * @param shippingNumber the shipping number
     * @return the LiveData object for the Item
     */
    public LiveData<Item> getItemByShippingNumber(int shippingNumber) {
        return itemRepository.getItemByShippingNumber(shippingNumber);
    }

    /**
     * Method to insert an Item
     *
     * @param item the Item to insert
     */
    public void insert(Item item) {
        itemRepository.insert(item);
    }

    /**
     * Method to delete an Item
     *
     * @param item the Item to delete
     */
    public void delete(Item item) {
        itemRepository.delete(item);
    }

    /**
     * Method to update an Item
     *
     * @param item the Item to update
     */
    public void update(Item item) {
        itemRepository.update(item);
    }

    /**
     * Utility method to check if an item exist for the given shipping number
     * @param shippingNumber A shipping number
     * @param itemExistCallback A itemExistsCallback interface
     */
    public void itemExist(int shippingNumber, ItemRepository.ItemExistCallback itemExistCallback) {
        itemRepository.itemExist(shippingNumber, itemExistCallback);
    }
}
