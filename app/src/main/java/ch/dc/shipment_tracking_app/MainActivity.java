package ch.dc.shipment_tracking_app;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        attachNavigationMenu();
    }

    public void goClientSide (View view) {
        redirectActivity(this, ClientMainActivity.class);
    }
}