package ch.dc.shipment_tracking_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class PostEmployeeMainActivity extends BaseActivity {

    public static final String SEND_SHIPPING_NUMBER = "SEND_SHIPPING_NUMBER";

    private TextView packageNumberTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_employee_main);

        Intent intent = getIntent();

        packageNumberTextView = findViewById(R.id.post_employee_main_title);
        CardView cardViewTrackingUpdate = findViewById(R.id.post_employee_main_tracking_update_card);
        CardView cardViewManagePackage = findViewById(R.id.post_employee_main_manage_package_card);


        int shippingNumber = intent.getIntExtra(PostEmployeeShippingNumberActivity.SEND_SHIPPING_NUMBER, 0);


        packageNumberTextView.setText(getString(R.string.post_employee_main_title, String.valueOf(shippingNumber)));
        cardViewTrackingUpdate.setOnClickListener(v -> {
            Intent updateIntent = new Intent(this, PostEmployeeUpdateTrackingActivity.class);
            updateIntent.putExtra(SEND_SHIPPING_NUMBER, shippingNumber);
            startActivity(updateIntent);
        });
        cardViewManagePackage.setOnClickListener(v -> redirectActivity(this, PostEmployeeManagePackageActivity.class));


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
