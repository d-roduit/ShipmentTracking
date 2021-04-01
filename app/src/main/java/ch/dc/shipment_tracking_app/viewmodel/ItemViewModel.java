package ch.dc.shipment_tracking_app.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ch.dc.shipment_tracking_app.db.entity.Item;
import ch.dc.shipment_tracking_app.db.repository.ItemRepository;

/**
 * Item ViewModel.
 */
public class ItemViewModel extends AndroidViewModel {

    /**
     * The Item Repository
     */
    private ItemRepository itemRepository;

    /**
     * The application context
     */
    private final Context applicationContext;


    /**
     * ItemViewModel constructor
     *
     * @param application the application
     * @param itemRepository the itemRepository
     */
    public ItemViewModel(@NonNull Application application, ItemRepository itemRepository) {
        super(application);
        this.itemRepository = itemRepository;
        applicationContext = application.getApplicationContext();
    }


    /**
     * Method to insert an Item
     *
     * @param item the Item to insert
     */
    public void insert(Item item) {
        itemRepository.insert(item, applicationContext);
    }

    /**
     * Method to delete an Item
     *
     * @param item the Item to delete
     */
    public void delete(Item item) {
        itemRepository.delete(item, applicationContext);
    }

    /**
     * Method to update an Item
     *
     * @param item the Item to update
     */
    public void update(Item item) {
        itemRepository.update(item, applicationContext);
    }


    public LiveData<Item> getItemByShippingNumber(int shippingNumber) {
        return itemRepository.getItemByShippingNumber(shippingNumber, applicationContext);
    }

}
