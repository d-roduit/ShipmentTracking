package ch.dc.shipment_tracking_app;


import androidx.cardview.widget.CardView;
import androidx.preference.PreferenceManager;

import android.os.Bundle;


public class ClientMainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_main);

        CardView cardViewSendPackage = findViewById(R.id.client_main_sendPackage_card);
        CardView cardViewTrackPackage = findViewById(R.id.client_main_trackPackage_card);

        cardViewSendPackage.setOnClickListener(v -> redirectActivity(ClientMainActivity.this, ClientSendPackageActivity.class));
        cardViewTrackPackage.setOnClickListener(v -> redirectActivity(ClientMainActivity.this, ClientTrackPackageActivity.class));

        ClientPackageConfirmationActivity.removeDataFromSharedPreferences(PreferenceManager.getDefaultSharedPreferences(this));
    }

}