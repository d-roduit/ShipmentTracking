package ch.dc.shipment_tracking_app;

import android.os.Bundle;
import android.view.View;

import androidx.cardview.widget.CardView;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        attachNavigationMenu();

        CardView cardViewClient = findViewById(R.id.main_client_card);
        cardViewClient.setOnClickListener(v -> redirectActivity(this, ClientMainActivity.class));
    }

}