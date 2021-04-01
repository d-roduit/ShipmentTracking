package ch.dc.shipment_tracking_app;


import androidx.cardview.widget.CardView;
import android.os.Bundle;


public class ClientMainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_main);

        CardView cardViewSendPackage = findViewById(R.id.client_main_sendPackage_card);
        CardView cardViewTrackPackage = findViewById(R.id.client_main_trackPackage_card);

        cardViewSendPackage.setOnClickListener(v -> redirectActivity(this, ClientSendPackageActivity.class));
        cardViewTrackPackage.setOnClickListener(v -> redirectActivity(this, ClientTrackPackageActivity.class));
    }

}