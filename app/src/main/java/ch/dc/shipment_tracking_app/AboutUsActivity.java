package ch.dc.shipment_tracking_app;

import android.os.Bundle;

public class AboutUsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        attachNavigationMenu();
    }
}