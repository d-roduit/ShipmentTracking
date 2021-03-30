package ch.dc.shipment_tracking_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ClientTrackPackageActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_track_package);

        setTitle(getString(R.string.client_track_package_activity_title));
    }
}