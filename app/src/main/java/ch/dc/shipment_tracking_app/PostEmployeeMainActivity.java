package ch.dc.shipment_tracking_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class PostEmployeeMainActivity extends BaseActivity {

    private TextView packageNumberTextView;

    private int shippingNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_employee_main);

        packageNumberTextView = findViewById(R.id.post_employee_main_title);
        CardView cardViewTrackingUpdate = findViewById(R.id.post_employee_main_tracking_update_card);
        CardView cardViewManagePackage = findViewById(R.id.post_employee_main_manage_package_card);

        // Get shipping number from sharedPreferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        shippingNumber = sharedPreferences.getInt(PostEmployeeShippingNumberActivity.SAVED_SHIPPING_NUMBER, 0);
        System.out.println(getClass().getSimpleName() + " | shippingNumber from Preferences : " + shippingNumber);

        packageNumberTextView.setText(getString(R.string.post_employee_main_title, String.valueOf(shippingNumber)));

        cardViewTrackingUpdate.setOnClickListener(v -> {
            redirectActivity(PostEmployeeMainActivity.this, PostEmployeeUpdateTrackingActivity.class);
        });
        cardViewManagePackage.setOnClickListener(v -> {
            redirectActivity(PostEmployeeMainActivity.this, PostEmployeeManagePackageActivity.class);
        });

        Intent intent = getIntent();

        List<String> snackbarMessages = new ArrayList<>();
        snackbarMessages.add(intent.getStringExtra(PostEmployeeUpdateTrackingActivity.SNACKBAR_TRACKING_UPDATED));
        snackbarMessages.add(intent.getStringExtra(PostEmployeeManagePackageActivity.SNACKBAR_PACKAGE_INFO_SAVED));
        snackbarMessages.add(intent.getStringExtra(PostEmployeeManagePackageActivity.SNACKBAR_PACKAGE_DELETED));

        for (String snackbarMessage: snackbarMessages) {
            if (snackbarMessage != null) {
                Snackbar.make(findViewById(R.id.post_employee_main_coordinatorLayout),
                        snackbarMessage,
                        Snackbar.LENGTH_LONG
                ).show();
                break;
            }
        }
    }
}
