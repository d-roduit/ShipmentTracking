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
    private ShipmentRepository shipmentRepository;

    /**
     * LiveData list of all Shipments
     */
    private LiveData<List<Shipment>> allShipments;


    public ShipmentViewModel(@NonNull Application application) {
        super(application);
        shipmentRepository = new ShipmentRepository(application);
        allShipments = shipmentRepository.getAllShipments();
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
     * Method to get all Shipments
     *
     * @return the LiveData list of all Shipments
     */
    public LiveData<List<Shipment>> getAllShipments() {
        return  allShipments;
    }

}
