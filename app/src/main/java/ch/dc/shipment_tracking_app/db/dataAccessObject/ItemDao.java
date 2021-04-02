package ch.dc.shipment_tracking_app.db.dataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import ch.dc.shipment_tracking_app.db.entity.Item;

/**
 * Item DAO.
 */
@Dao
public interface ItemDao {

    /**
     * Method to insert an Item
     * @param item the Item to insert
     */
    @Insert
    void insert(Item item);

    /**
     * Method to delete an Item
     * @param item the Item to delete
     */
    @Delete
    void delete(Item item);

    /**
     * Method to update an Item
     * @param item the Item to update
     */
    @Update
    void update(Item item);

    /**
     * Method to get an Item by its shipping number
     * @param shippingNumber the shipping number
     * @return the Item entity
     */
    @Query("SELECT * FROM items WHERE shipping_number = :shippingNumber")
    LiveData<Item> getItemByShippingNumber(int shippingNumber);
}