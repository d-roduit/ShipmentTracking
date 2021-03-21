package ch.dc.shipment_tracking_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class ClientMainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_main);
    }

    public void sendPackage(View view) {
        MainActivity.redirectActivity(this, ClientSendPackageActivity.class);
    }
}