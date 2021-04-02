package ch.dc.shipment_tracking_app;

import android.os.Bundle;
import android.view.View;

import androidx.cardview.widget.CardView;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        attachNavigationMenu();

        CardView cardViewClient = findViewById(R.id.main_client_card);
        CardView cardViewPostEmployee = findViewById(R.id.main_post_employee_card);

        cardViewClient.setOnClickListener(v -> redirectActivity(this, ClientMainActivity.class));
        cardViewPostEmployee.setOnClickListener(v -> redirectActivity(this, PostEmployeeShippingNumberActivity.class));
    }
}