package ch.dc.shipment_tracking_app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

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

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<Item> observableItem;

    /**
     * ItemViewModel constructor
     *
     * @param application the application
     */
    public ItemViewModel(@NonNull Application application, ItemRepository itemRepository) {
        super(application);

        this.itemRepository = itemRepository;

        observableItem = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableItem.setValue(null);
    }


    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final ItemRepository repository;

        public Factory(@NonNull Application application) {
            this.application = application;
            repository = ItemRepository.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ItemViewModel(application, repository);
        }
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

    public LiveData<Item> getItemByShippingNumber(String id) {
        return itemRepository.getItemByShippingNumber(id);
    }

    /**
     * Method to count the number of occurrences of a shipping number
     * @param shippingNumber A shipping number
     */
    public void countShippingNumber(int shippingNumber) {
        //itemRepository.countShippingNumber(shippingNumber, onPostAsyncQueryExecuted);
    }

}
