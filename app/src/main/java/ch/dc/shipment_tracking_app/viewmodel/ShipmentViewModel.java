package ch.dc.shipment_tracking_app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ch.dc.shipment_tracking_app.db.entity.Shipment;
import ch.dc.shipment_tracking_app.db.repository.ShipmentRepository;

/**
 * Shipment ViewModel
 */
public class ShipmentViewModel extends AndroidViewModel {

    /**
     * The Shipment Repository
     */
    private final ShipmentRepository shipmentRepository;

    public ShipmentViewModel(@NonNull Application application) {
        super(application);

        this.shipmentRepository = ShipmentRepository.getInstance();
    }

    /**
     * Method to get Shipments by a shipping number
     * @param shippingNumber the shipping number
     * @return a LiveData list of Shipments
     */
    public LiveData<List<Shipment>> getShipmentsByShippingNumber(int shippingNumber) {
        return shipmentRepository.getShipmentsByShippingNumber(shippingNumber);
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
}
