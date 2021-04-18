package ch.dc.shipment_tracking_app.db.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ch.dc.shipment_tracking_app.db.entity.Shipment;

public class ShipmentListLiveData extends LiveData<List<Shipment>> {

    private static final String TAG = "ClientAccountsLiveData";

    private final DatabaseReference reference;
    private final MyValueEventListener listener = new MyValueEventListener();

    public ShipmentListLiveData(DatabaseReference ref) {
        reference = ref;
    }

    @Override
    protected void onActive() {
        Log.d(TAG, "onActive");
        reference.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        Log.d(TAG, "onInactive");
    }

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            setValue(toShipmentList(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<Shipment> toShipmentList(DataSnapshot snapshot) {
        List<Shipment> shipments = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            Shipment shipment = childSnapshot.getValue(Shipment.class);
            shipment.setId(childSnapshot.getKey());
            shipments.add(shipment);
        }
        return shipments;
    }
}
