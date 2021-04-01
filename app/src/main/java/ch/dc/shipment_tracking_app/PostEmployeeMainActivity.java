package ch.dc.shipment_tracking_app;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;

import com.google.android.material.snackbar.Snackbar;

public class PostEmployeeMainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_employee_main);

        CardView cardViewTrackingUpdate = findViewById(R.id.post_employee_main_tracking_update_card);
        CardView cardViewManagePackages = findViewById(R.id.post_employee_main_manage_packages_card);

        cardViewTrackingUpdate.setOnClickListener(v -> redirectActivity(this, PostEmployeeUpdateTrackingActivity.class));
        cardViewManagePackages.setOnClickListener(v -> redirectActivity(this, PostEmployeeManagePackagesActivity.class));

        Intent intent = getIntent();
        String notificationMessage = intent.getStringExtra(PostEmployeeUpdateTrackingActivity.SNACKBAR_TRACKING_UPDATED);

        if (notificationMessage != null) {
            Snackbar.make(findViewById(R.id.post_employee_main_coordinatorLayout),
                    notificationMessage,
                    Snackbar.LENGTH_LONG
            ).show();
        }
    }

}
