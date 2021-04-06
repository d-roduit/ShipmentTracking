package ch.dc.shipment_tracking_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import ch.dc.shipment_tracking_app.db.entity.Item;
import ch.dc.shipment_tracking_app.db.entity.Shipment;
import ch.dc.shipment_tracking_app.viewmodel.ItemViewModel;
import ch.dc.shipment_tracking_app.viewmodel.ShipmentViewModel;

public class PostEmployeeManagePackageActivity extends BaseActivity {

    public static final String SNACKBAR_PACKAGE_INFO_SAVED = "SNACKBAR_PACKAGE_INFO_SAVED";
    public static final String SNACKBAR_PACKAGE_DELETED = "SNACKBAR_PACKAGE_DELETED";

    private Intent snackbarIntent;

    private ItemViewModel itemViewModel;
    private ShipmentViewModel shipmentViewModel;

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

        // Initialize the outgoing intent
        snackbarIntent = new Intent(this, PostEmployeeMainActivity.class);

        //Initialize the views
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
//        String[] trackingStatusList = getResources().getStringArray(R.array.post_employee_update_tracking_status_list);
//        ArrayAdapter<String> trackingStatusAdapter1 = new ArrayAdapter<>(
//                this, R.layout.dropdown_item, trackingStatusList
//        );
//        ArrayAdapter<String> trackingStatusAdapter2 = new ArrayAdapter<>(
//                this, R.layout.dropdown_item, trackingStatusList
//        );
//        statusDropDown1.setAdapter(trackingStatusAdapter1);
//        statusDropDown2.setAdapter(trackingStatusAdapter2);


        // Initialize the viewModels
        itemViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(ItemViewModel.class);
        shipmentViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(ShipmentViewModel.class);

        // Get shipping number from sharedPreferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int shippingNumber = sharedPreferences.getInt(PostEmployeeShippingNumberActivity.SAVED_SHIPPING_NUMBER, 0);

        // Fetch database data
        LiveData<Item> item = itemViewModel.getItemByShippingNumber(shippingNumber);
        LiveData<List<Shipment>> itemShipments = shipmentViewModel.getShipmentByShippingNumber(shippingNumber);

        // Fill in the views with the database data
        item.observe(this, new Observer<Item>() {
            @Override
            public void onChanged(Item item) {
                updateInputsValues(item);
            }
        });

        itemShipments.observe(this, new Observer<List<Shipment>>() {
            @Override
            public void onChanged(List<Shipment> shipments) {

            }
        });

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

    private void updateInputsValues(Item item) {
        packageWeightTextInputLayout.getEditText().setText(Double.toString(item.getWeight()));
//        shippingPriorityTextInputLayout.getEditText().setText();
//        shippingPriorityDropDown.getEditText().setText();
        senderLastnameTextInputLayout.getEditText().setText(item.getSenderLastname());
        senderFirstnameTextInputLayout.getEditText().setText(item.getSenderFirstname());
        senderAddressTextInputLayout.getEditText().setText(item.getSenderAddress());
        senderNpaTextInputLayout.getEditText().setText(item.getSenderNpa());
        senderCityTextInputLayout.getEditText().setText(item.getSenderCity());
        recipientLastnameTextInputLayout.getEditText().setText(item.getRecipientLastname());
        recipientFirstnameTextInputLayout.getEditText().setText(item.getRecipientFirstname());
        recipientAddressTextInputLayout.getEditText().setText(item.getRecipientAddress());
        recipientNpaTextInputLayout.getEditText().setText(item.getRecipientNPA());
        recipientCityTextInputLayout.getEditText().setText(item.getRecipientCity());
//        status1TextInputLayout.getEditText().setText();
//        statusDropDown1.getEditText().setText();
//        npa1TextInputLayout.getEditText().setText();
//        city1TextInputLayout.getEditText().setText();
//        status2TextInputLayout.getEditText().setText();
//        statusDropDown2.getEditText().setText();
//        npa2TextInputLayout.getEditText().setText();
//        city2TextInputLayout.getEditText().setText();
    }
}
