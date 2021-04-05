package ch.dc.shipment_tracking_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;

import ch.dc.shipment_tracking_app.viewmodel.ShipmentViewModel;

public class PostEmployeeManagePackageActivity extends BaseActivity {

    public static final String SNACKBAR_PACKAGE_INFO_SAVED = "SNACKBAR_PACKAGE_INFO_SAVED";
    public static final String SNACKBAR_PACKAGE_DELETED = "SNACKBAR_PACKAGE_DELETED";
    private Intent snackbarIntent;

    private TextInputLayout packageWeightTextInputLayout;
    private TextInputLayout shippingPriorityTextInputLayout;
    private AutoCompleteTextView shippingPriorityDropDown;
    private TextInputLayout senderLastnameTextInputLayout;
    private TextInputLayout senderFirstnameTextInputLayout;
    private TextInputLayout senderAddressTextInputLayout;
    private TextInputLayout senderNpaTextInputLayout;
    private TextInputLayout senderCityTextInputLayout;
    private TextInputLayout recipientLastnameTextInputLayout;
    private TextInputLayout recipientFirstnameTextInputLayout;
    private TextInputLayout recipientAddressTextInputLayout;
    private TextInputLayout recipientNpaTextInputLayout;
    private TextInputLayout recipientCityTextInputLayout;
    private TextInputLayout status1TextInputLayout;
    private AutoCompleteTextView statusDropDown1;
    private TextInputLayout npa1TextInputLayout;
    private TextInputLayout city1TextInputLayout;
    private TextInputLayout status2TextInputLayout;
    private AutoCompleteTextView statusDropDown2;
    private TextInputLayout npa2TextInputLayout;
    private TextInputLayout city2TextInputLayout;
    private Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_employee_manage_package);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        snackbarIntent = new Intent(this, PostEmployeeMainActivity.class);

        //Views
        packageWeightTextInputLayout = findViewById(R.id.post_employee_manage_package_input_weight);
        shippingPriorityTextInputLayout = findViewById(R.id.post_employee_manage_package_input_shipping_priority);
        shippingPriorityDropDown = findViewById(R.id.post_employee_manage_package_shipping_priority_list);
        senderLastnameTextInputLayout = findViewById(R.id.post_employee_manage_package_input_sender_lastname);
        senderFirstnameTextInputLayout = findViewById(R.id.post_employee_manage_package_input_sender_firstname);
        senderAddressTextInputLayout = findViewById(R.id.post_employee_manage_package_input_sender_address);
        senderNpaTextInputLayout = findViewById(R.id.post_employee_manage_package_input_sender_npa);
        senderCityTextInputLayout = findViewById(R.id.post_employee_manage_package_input_sender_city);
        recipientLastnameTextInputLayout = findViewById(R.id.post_employee_manage_package_input_recipient_lastname);
        recipientFirstnameTextInputLayout = findViewById(R.id.post_employee_manage_package_input_recipient_firstname);
        recipientAddressTextInputLayout = findViewById(R.id.post_employee_manage_package_input_recipient_address);
        recipientNpaTextInputLayout = findViewById(R.id.post_employee_manage_package_input_recipient_npa);
        recipientCityTextInputLayout = findViewById(R.id.post_employee_manage_package_input_recipient_city);
        status1TextInputLayout = findViewById(R.id.post_employee_manage_package_input_status_1);
        statusDropDown1 = findViewById(R.id.post_employee_manage_package_status_list_1);
        npa1TextInputLayout = findViewById(R.id.post_employee_manage_package_input_npa_1);
        city1TextInputLayout = findViewById(R.id.post_employee_manage_package_input_city_1);
        status2TextInputLayout = findViewById(R.id.post_employee_manage_package_input_status_2);
        statusDropDown2 = findViewById(R.id.post_employee_manage_package_status_list_2);
        npa2TextInputLayout = findViewById(R.id.post_employee_manage_package_input_npa_2);
        city2TextInputLayout = findViewById(R.id.post_employee_manage_package_input_city_2);
        deleteButton = findViewById(R.id.post_employee_manage_package_button_delete);

        // Initialize the shipping priority dropdown.
        String[] shipping_priority_items = getResources().getStringArray(R.array.shipping_priority_items);
        ArrayAdapter<String> shippingPriorityAdapter = new ArrayAdapter<>(
                this, R.layout.dropdown_item, shipping_priority_items
        );
        shippingPriorityDropDown.setAdapter(shippingPriorityAdapter);

        // Initialize the tracking status dropdowns.
        String[] trackingStatusList = getResources().getStringArray(R.array.post_employee_update_tracking_status_list);
        ArrayAdapter<String> trackingStatusAdapter1 = new ArrayAdapter<>(
                this, R.layout.dropdown_item, trackingStatusList
        );
        ArrayAdapter<String> trackingStatusAdapter2 = new ArrayAdapter<>(
                this, R.layout.dropdown_item, trackingStatusList
        );
        statusDropDown1.setAdapter(trackingStatusAdapter1);
        statusDropDown2.setAdapter(trackingStatusAdapter2);


        MaterialAlertDialogBuilder deletePackageDialog = new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.post_employee_manage_package_dialog_title)
                .setMessage(R.string.post_employee_manage_package_dialog_message)
                .setPositiveButton(R.string.post_employee_manage_package_dialog_button_ok, (dialog, which) -> deletePackageFromDB())
                .setNegativeButton(R.string.post_employee_manage_package_dialog_button_cancel, null);

        deleteButton.setOnClickListener(v -> deletePackageDialog.show());
    }

    private void updatePackageInfo() {
        boolean areInputsValid = InputValidator.validateInputs(
                packageWeightTextInputLayout, shippingPriorityTextInputLayout,
                senderLastnameTextInputLayout, senderFirstnameTextInputLayout,
                senderAddressTextInputLayout, senderNpaTextInputLayout,
                senderCityTextInputLayout, recipientLastnameTextInputLayout,
                recipientFirstnameTextInputLayout, recipientAddressTextInputLayout,
                recipientNpaTextInputLayout, recipientCityTextInputLayout,
                status1TextInputLayout, npa1TextInputLayout,
                city1TextInputLayout, status2TextInputLayout,
                npa2TextInputLayout, city2TextInputLayout
        );

        if (areInputsValid) {
            System.out.println("Package info saved !");

            snackbarIntent.putExtra(SNACKBAR_PACKAGE_INFO_SAVED,
                    getString(R.string.post_employee_manage_package_snackbar_message_package_info_saved)
            );
            startActivity(snackbarIntent);
        }
    }

    private void deletePackageFromDB() {
        System.out.println("Remove package from DB");

        snackbarIntent.putExtra(SNACKBAR_PACKAGE_DELETED,
                getString(R.string.post_employee_manage_package_snackbar_message_package_deleted)
        );
        startActivity(snackbarIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.update_package_info_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.update_package_info_menu_save_icon) {
            updatePackageInfo();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
