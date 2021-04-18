package ch.dc.shipment_tracking_app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import ch.dc.shipment_tracking_app.db.entity.Item;
import ch.dc.shipment_tracking_app.db.entity.Shipment;
import ch.dc.shipment_tracking_app.db.repository.ItemRepository;
import ch.dc.shipment_tracking_app.db.repository.ShipmentRepository;

/**
 * Shipment ViewModel
 */
public class ShipmentViewModel extends AndroidViewModel {

    /**
     * The Shipment Repository
     */
    private final ShipmentRepository shipmentRepository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<Item> observableShipment;


    public ShipmentViewModel(@NonNull Application application, ShipmentRepository shipmentRepository) {
        super(application);

        this.shipmentRepository = shipmentRepository;

        observableShipment = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableShipment.setValue(null);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final ShipmentRepository repository;

        public Factory(@NonNull Application application) {
            this.application = application;
            repository = ShipmentRepository.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ShipmentViewModel(application, repository);
        }
    }

    /**
     * Method to insert a Shipment
     *
     * @param shipment the Shipment to insert
     */
    public void insert(Shipment shipment) {
        shipmentRepository.insert(shipment);
    }

    /**
     * Method to delete a Shipment
     *
     * @param shipment the Shipment to delete
     */
    public void delete(Shipment shipment) {
        shipmentRepository.delete(shipment);
    }

    /**
     * Method to update a Shipment
     *
     * @param shipment the Shipment to update
     */
    public void update(Shipment shipment) {
        shipmentRepository.update(shipment);
    }

    /**
     * Method to get Shipments by a shipping number
     * @param id the id
     * @return a livedata list of Shipments
     */
    public LiveData<List<Shipment>> getShipmentByShippingNumber(String id) {
        return  shipmentRepository.getShipmentByShippingNumber(id);
    }

}
