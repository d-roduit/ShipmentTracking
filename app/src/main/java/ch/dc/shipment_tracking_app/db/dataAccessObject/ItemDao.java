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
     * @return The number of rows affected by this query
     */
    @Delete
    int delete(Item item);

    /**
     * Method to update an Item
     * @param item the Item to update
     * @return The number of rows affected by this query
     */
    @Update
    int update(Item item);

    /**
     * Method to get an Item by its shipping number
     * @param shippingNumber the shipping number
     * @return the Item entity
     */
    @Query("SELECT * FROM items WHERE shipping_number = :shippingNumber")
    LiveData<Item> getItemByShippingNumber(int shippingNumber);

    /**
     * Method to count the number of occurrences of a shipping number
     * @param shippingNumber A shipping number
     * @return The number of occurrences
     */
    @Query("SELECT COUNT(*) FROM items WHERE shipping_number = :shippingNumber")
    int countShippingNumber(int shippingNumber);
}