package ch.dc.shipment_tracking_app.db.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ch.dc.shipment_tracking_app.db.entity.Item;
import ch.dc.shipment_tracking_app.db.firebase.ItemLiveData;

/**
 * Item Repository.
 */
public class ItemRepository {

    private static ItemRepository instance;
    public final static String referenceName = "items";

    /**
     * ItemRepository constructor
     */
    public static ItemRepository getInstance() {
        if (instance == null) {
            synchronized (ItemRepository.class) {
                if (instance == null) {
                    instance = new ItemRepository();
                }
            }
        }
        return instance;
    }

    /**
     * Method to get an Item by its shipping number
     *
     * @param shippingNumber the shipping number
     * @return the Item
     */
    public LiveData<Item> getItemByShippingNumber(final int shippingNumber) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference(referenceName)
                .child(String.valueOf(shippingNumber));

        return new ItemLiveData(reference);
    }

    /**
     * Method to insert an Item in the database
     *
     * @param item the Item to insert
     */
    public void insert(final Item item) {
        FirebaseDatabase.getInstance()
                .getReference(referenceName)
                .child(String.valueOf(item.getShippingNumber()))
                .setValue(item);
    }

    /**
     * Method to delete an Item
     *
     * @param item the Item to delete
     */
    public void delete(final Item item) {
        String shippingNumberKey = String.valueOf(item.getShippingNumber());

        FirebaseDatabase.getInstance()
                .getReference(referenceName)
                .child(shippingNumberKey)
                .removeValue();

        FirebaseDatabase.getInstance()
                .getReference(ShipmentRepository.referenceName)
                .child(shippingNumberKey)
                .removeValue();
    }

    /**
     * Method to update an Item
     *
     * @param item the Item to update
     */
    public void update(final Item item) {
        FirebaseDatabase.getInstance()
                .getReference(referenceName)
                .child(String.valueOf(item.getShippingNumber()))
                .updateChildren(item.toMap());
    }

    /**
     * Utility method to check if an item exist for the given shipping number
     * @param shippingNumber A shipping number
     * @param itemExistCallback A itemExistsCallback interface
     */
    public void itemExist(final int shippingNumber, ItemExistCallback itemExistCallback) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference(referenceName)
                .child(String.valueOf(shippingNumber));

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                itemExistCallback.onDataChange(snapshot.exists());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("itemExists query was cancelled : " + error.getMessage());
            }
        });
    }

    /**
     * Utility interface that provides a callback to the {@link #itemExist(int, ItemExistCallback)} method
     */
    public interface ItemExistCallback {
        void onDataChange(boolean itemExist);
    }
}
