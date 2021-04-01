package ch.dc.shipment_tracking_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

public class PostEmployeeUpdateTrackingActivity extends BaseActivity {

    public static final String SNACKBAR_TRACKING_UPDATED = "SNACKBAR_TRACKING_UPDATED";

    private AutoCompleteTextView statusDropDown;
    private TextInputLayout statusDropDownInput;
    private TextInputLayout npaInput;
    private TextInputLayout cityInput;
    private Button updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_employee_update_tracking);

        statusDropDown = findViewById(R.id.post_employee_update_tracking_status_list);
        statusDropDownInput = findViewById(R.id.post_employee_update_tracking_input_status);
        npaInput = findViewById(R.id.post_employee_update_tracking_input_npa);
        cityInput = findViewById(R.id.post_employee_update_tracking_input_city);
        updateButton = findViewById(R.id.post_employee_update_tracking_button_update);

        // Initialize the tracking status dropdown.
        String[] trackingStatusList = getResources().getStringArray(R.array.post_employee_update_tracking_status_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, R.layout.dropdown_item, trackingStatusList
        );
        statusDropDown.setAdapter(adapter);

        updateButton.setOnClickListener(v -> {
            boolean areInputsValid = InputValidator.validateInputs(statusDropDownInput, npaInput, cityInput);

            if (areInputsValid) {
                Intent intent = new Intent(PostEmployeeUpdateTrackingActivity.this, PostEmployeeMainActivity.class);

                intent.putExtra(SNACKBAR_TRACKING_UPDATED,
                        getString(R.string.post_employee_update_tracking_notification_message)
                );

                startActivity(intent);
            }
        });
    }

}
