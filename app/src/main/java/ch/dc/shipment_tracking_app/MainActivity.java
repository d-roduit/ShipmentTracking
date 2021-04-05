package ch.dc.shipment_tracking_app;

import android.os.Bundle;

import androidx.cardview.widget.CardView;

import ch.dc.shipment_tracking_app.db.AppDatabase;

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

        AppDatabase.getInstance(getApplication());
    }
}