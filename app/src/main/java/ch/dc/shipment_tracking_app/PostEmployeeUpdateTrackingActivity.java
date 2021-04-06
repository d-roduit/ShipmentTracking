package ch.dc.shipment_tracking_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;

import ch.dc.shipment_tracking_app.db.entity.Shipment;
import ch.dc.shipment_tracking_app.viewmodel.ShipmentViewModel;

public class PostEmployeeUpdateTrackingActivity extends BaseActivity {

    public static final String SNACKBAR_TRACKING_UPDATED = "SNACKBAR_TRACKING_UPDATED";

    private ShipmentViewModel shipmentViewModel;
    private AutoCompleteTextView statusDropDown;
    private TextInputLayout statusDropDownInput;
    private TextInputLayout npaInput;
    private TextInputLayout cityInput;
    private Button updateButton;
    private int status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_employee_update_tracking);

        // ViewModels
        shipmentViewModel = new ViewModelProvider
                .AndroidViewModelFactory(getApplication())
                .create(ShipmentViewModel.class);

        // Views
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

        // Get shipping number from sharedPreferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int shippingNumber = sharedPreferences.getInt(PostEmployeeShippingNumberActivity.SAVED_SHIPPING_NUMBER, 0);
        System.out.println(getClass().getSimpleName() + " | shippingNumber from Preferences : " + shippingNumber);

        // Get the status position
        statusDropDown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                status = position;
            }
        });

        updateButton.setOnClickListener(v -> {
            boolean areInputsValid = InputValidator.validateInputs(statusDropDownInput, npaInput, cityInput);

            if (areInputsValid) {
                Intent intent = new Intent(PostEmployeeUpdateTrackingActivity.this, PostEmployeeMainActivity.class);

                intent.putExtra(SNACKBAR_TRACKING_UPDATED,
                        getString(R.string.post_employee_update_tracking_notification_message)
                );

                String npa = npaInput.getEditText().getText().toString();
                String city = cityInput.getEditText().getText().toString();

                //Insert the shipment
                Shipment shipment = new Shipment(shippingNumber, status, npa, city);
                shipmentViewModel.insert(shipment, null);

                startActivity(intent);
            }
        });
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            System.out.println("-------------------------- HOME PRESSED");
//            onBackPressed();
//            return true;
//        }
//        System.out.println("-------------------------- AUTRE PRESSED");
//        return super.onOptionsItemSelected(item);
//    }
}
