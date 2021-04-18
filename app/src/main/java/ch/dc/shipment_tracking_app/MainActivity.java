package ch.dc.shipment_tracking_app;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import ch.dc.shipment_tracking_app.db.entity.Item;
import ch.dc.shipment_tracking_app.db.entity.Shipment;
import ch.dc.shipment_tracking_app.viewmodel.ItemViewModel;
import ch.dc.shipment_tracking_app.viewmodel.ShipmentViewModel;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        attachNavigationMenu(R.id.main_drawer_layout);

        CardView cardViewClient = findViewById(R.id.main_client_card);
        CardView cardViewPostEmployee = findViewById(R.id.main_post_employee_card);

        cardViewClient.setOnClickListener(v -> redirectActivity(MainActivity.this, ClientMainActivity.class));
        cardViewPostEmployee.setOnClickListener(v -> redirectActivity(MainActivity.this, PostEmployeeShippingNumberActivity.class));

//        populateDatabase();
    }

    /**
     * Method to insert test data on application start
     */
    private void populateDatabase() {
        Item item = new Item("B", 126.0, "Daniel", "Roduit",
                "Rue de l'ile 8", "3979", "Grône", "Cathy",
                "Gay", "Rue du moulin rouge 10", "1920", "Martigny");

        System.out.println("----------------------------------------------------");
        System.out.println("shippinNumber entered : " + item.getShippingNumber());
        System.out.println("----------------------------------------------------");

        // Create the shipment
        Shipment shipment = new Shipment(item.getShippingNumber(),
                TrackingStatus.DEPOSITED.getStatusListPosition(), item.getSenderNpa(), item.getSenderCity());

        ItemViewModel itemViewModel = new ViewModelProvider
                .AndroidViewModelFactory(getApplication())
                .create(ItemViewModel.class);

        ShipmentViewModel shipmentViewModel = new ViewModelProvider
                .AndroidViewModelFactory(getApplication())
                .create(ShipmentViewModel.class);

        itemViewModel.insert(item);
        shipmentViewModel.insert(shipment);
    }
}