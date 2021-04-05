package ch.dc.shipment_tracking_app.db.dataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ch.dc.shipment_tracking_app.db.entity.Shipment;

/**
 * Shipment DAO.
 */
@Dao
public interface ShipmentDao {

    /**
     * Method to insert a Shipment
     * @param shipment the Shipment to insert
     */
    @Insert
    void insert(Shipment shipment);

    /**
     * Method to delete a Shipment
     * @param shipment the Shipment to delete
     */
    @Delete
    void delete(Shipment shipment);

    /**
     * Method to update a Shipment
     * @param shipment the Shipment to update
     */
    @Update
    void update(Shipment shipment);

    /**
     * Method to get Shipments by a shipping number
     * @param shippingNumber the shipping number
     * @return a livedata list of Shipments
     */
    @Query("SELECT * FROM shipments WHERE shipping_number = :shippingNumber ORDER BY date DESC")
    LiveData<List<Shipment>> getShipmentByShippingNumber(int shippingNumber);


}
