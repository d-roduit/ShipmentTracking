package ch.dc.shipment_tracking_app.db.dataAccessObject;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ch.dc.shipment_tracking_app.db.entity.Item;

@Dao
public interface ItemDao {
    @Query("SELECT * FROM items")
    List<Item> getAll();

    @Query("SELECT * FROM items WHERE shipping_number = :shippingNumber")
    List<Item> getByShippingNumber(int shippingNumber);

    @Insert
    void insert(Item item);

    @Query("DELETE FROM items WHERE id = :itemId")
    void delete(int itemId);

    @Update
    void update(Item item);
}