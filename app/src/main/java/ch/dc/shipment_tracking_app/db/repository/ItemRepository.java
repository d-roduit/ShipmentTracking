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
     * Method to get an Item by its id
     *
     * @param shippingNumber the string id
     * @return the Item
     */
    public LiveData<Item> getItemByShippingNumber(final int shippingNumber) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("items")
                .child(String.valueOf(shippingNumber));

        return new ItemLiveData(reference);
    }

    /**
     * Method to insert an Item in the database
     *
     * @param item the Item to insert
     */
    public void insert(final Item item) {
        FirebaseDatabase.getInstance().getReference("items")
                .child(String.valueOf(item.getShippingNumber()))
                .setValue(item);
    }

    /**
     * Method to delete an Item
     *
     * @param item the Item to delete
     */
    public void delete(final Item item) {
        FirebaseDatabase.getInstance().getReference("items")
                .child(item.getId())
                .removeValue();
    }

    /**
     * Method to update an Item
     *
     * @param item the Item to update
     */
    public void update(final Item item) {
        FirebaseDatabase.getInstance().getReference("items")
                .child(item.getId())
                .updateChildren(item.toMap());
    }

    /**
     * Method to count the number of occurrences of a shipping number
     */
    public void countShippingNumber(final Item item) {
        //new CountShippingNumber(itemDao, onPostAsyncQueryExecuted).execute(shippingNumber);
        DatabaseReference root = FirebaseDatabase.getInstance().getReference();
        DatabaseReference items = root.child("items");
        items.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(item.getId()).exists()) {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
