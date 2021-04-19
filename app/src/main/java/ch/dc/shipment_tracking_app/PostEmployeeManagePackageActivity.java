package ch.dc.shipment_tracking_app;

import android.content.DialogInterface;
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
import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import ch.dc.shipment_tracking_app.adapter.ManageRecyclerAdapter;
import ch.dc.shipment_tracking_app.db.entity.Item;
import ch.dc.shipment_tracking_app.db.entity.Shipment;
import ch.dc.shipment_tracking_app.viewmodel.ItemViewModel;
import ch.dc.shipment_tracking_app.viewmodel.ShipmentViewModel;

public class PostEmployeeManagePackageActivity extends BaseActivity {

    // Intent
    private Intent snackbarIntent;
    public static final String SNACKBAR_PACKAGE_INFO_SAVED = "SNACKBAR_PACKAGE_INFO_SAVED";
    public static final String SNACKBAR_PACKAGE_DELETED = "SNACKBAR_PACKAGE_DELETED";

    // Shipping number
    private int shippingNumber;

    // ViewModels
    private ItemViewModel itemViewModel;
    private ShipmentViewModel shipmentViewModel;

    // Entities
    private Item item;

    // RecyclerAdapter
    private ManageRecyclerAdapter manageRecyclerAdapter;

    // Views
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
    private Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_employee_manage_package);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_close);
        }

        // Initialize the views
        initializeViews();

        // Fill the shipping priority dropdown with data
        String[] shipping_priority_items = getResources().getStringArray(R.array.shipping_priority_items);
        ArrayAdapter<String> shippingPriorityAdapter = new ArrayAdapter<>(
                this, R.layout.dropdown_item, shipping_priority_items
        );
        shippingPriorityDropDown.setAdapter(shippingPriorityAdapter);

        // Get shipping number from sharedPreferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        shippingNumber = sharedPreferences.getInt(PostEmployeeShippingNumberActivity.SAVED_SHIPPING_NUMBER, 0);

        // Initialize the RecyclerAdapter
        manageRecyclerAdapter = new ManageRecyclerAdapter(this);
        manageRecyclerAdapter.setOnDeleteShipmentClickListener(shipment -> deleteShipmentFromDB(shipment));

        // Initialize the RecyclerView
        RecyclerView manageRecyclerView = findViewById(R.id.post_employee_manage_package_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        manageRecyclerView.setLayoutManager(linearLayoutManager);
        manageRecyclerView.setHasFixedSize(false);
        manageRecyclerView.setAdapter(manageRecyclerAdapter);

        // Initialize the viewModels
        itemViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(ItemViewModel.class);
        shipmentViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(ShipmentViewModel.class);

        // Fetch database data and fill in the views with the data
        LiveData<Item> itemLiveData = itemViewModel.getItemByShippingNumber(shippingNumber);
        if (itemLiveData != null) {
            itemLiveData.observe(this, item -> {
                setItem(item);
                updateInputsValues(item);
            });
        }

        LiveData<List<Shipment>> itemShipmentsLiveData = shipmentViewModel.getShipmentsByShippingNumber(shippingNumber);
        if (itemShipmentsLiveData != null) {
            itemShipmentsLiveData.observe(this, shipments -> manageRecyclerAdapter.setShipments(shipments));
        }

        // Set the delete item button click listener
        deleteButton.setOnClickListener(v -> deletePackageFromDB(item));
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
            savePackageInformation();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeViews() {
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
        deleteButton = findViewById(R.id.post_employee_manage_package_button_delete);
    }

    private void deletePackageFromDB(Item item) {
        if (item != null) {
            DialogInterface.OnClickListener onPositiveButtonClickListener = (dialog, which) -> {
                itemViewModel.delete(item);

                // Remove shipping number from SharedPreferences
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(PostEmployeeManagePackageActivity.this);
                sharedPreferences.edit().remove(PostEmployeeShippingNumberActivity.SAVED_SHIPPING_NUMBER).apply();

                // Launch intent to redirect
                snackbarIntent = new Intent(this, PostEmployeeShippingNumberActivity.class);
                snackbarIntent.putExtra(SNACKBAR_PACKAGE_DELETED,
                        getString(R.string.post_employee_manage_package_snackbar_message_package_deleted)
                );

                startActivity(snackbarIntent);
            };

            new MaterialAlertDialogBuilder(this)
                    .setTitle(R.string.post_employee_manage_package_delete_package_dialog_title)
                    .setMessage(R.string.post_employee_manage_package_delete_package_dialog_message)
                    .setPositiveButton(R.string.post_employee_manage_package_dialog_button_ok, onPositiveButtonClickListener)
                    .setNegativeButton(R.string.post_employee_manage_package_dialog_button_cancel, null)
                    .show();
        }
    }

    private void deleteShipmentFromDB(Shipment shipment) {
        if (shipment != null) {
            DialogInterface.OnClickListener onPositiveButtonClickListener = (dialog, which) -> {
                shipmentViewModel.delete(shipment);
                manageRecyclerAdapter.removeFromChangedShipment(shipment);
            };

            String status = TrackingStatus.fromStatusListPosition(shipment.getStatus()).getStringStatus(this);

            new MaterialAlertDialogBuilder(this)
                    .setTitle(getString(R.string.post_employee_manage_package_delete_tracking_status_dialog_title, status))
                    .setMessage(getString(R.string.post_employee_manage_package_delete_tracking_status_dialog_message, status))
                    .setPositiveButton(R.string.post_employee_manage_package_dialog_button_ok, onPositiveButtonClickListener)
                    .setNegativeButton(R.string.post_employee_manage_package_dialog_button_cancel, null)
                    .show();
        }
    }

    private void savePackageInformation() {
        List<Shipment> updatedShipments = manageRecyclerAdapter.getChangedShipments();

        boolean arePackageInputsValid = InputValidator.validateInputs(
                packageWeightTextInputLayout, shippingPriorityTextInputLayout,
                senderLastnameTextInputLayout, senderFirstnameTextInputLayout,
                senderAddressTextInputLayout, senderNpaTextInputLayout,
                senderCityTextInputLayout, recipientLastnameTextInputLayout,
                recipientFirstnameTextInputLayout, recipientAddressTextInputLayout,
                recipientNpaTextInputLayout, recipientCityTextInputLayout
        );

        if (arePackageInputsValid) {
            double packageWeight = Double.parseDouble(packageWeightTextInputLayout.getEditText().getText().toString());
            String shippingPriority = shippingPriorityDropDown.getText().toString();
            String senderLastname = senderLastnameTextInputLayout.getEditText().getText().toString();
            String senderFirstname = senderFirstnameTextInputLayout.getEditText().getText().toString();
            String senderAddress = senderAddressTextInputLayout.getEditText().getText().toString();
            String senderNpa = senderNpaTextInputLayout.getEditText().getText().toString();
            String senderCity = senderCityTextInputLayout.getEditText().getText().toString();
            String recipientLastname = recipientLastnameTextInputLayout.getEditText().getText().toString();
            String recipientFirstname = recipientFirstnameTextInputLayout.getEditText().getText().toString();
            String recipientAddress = recipientAddressTextInputLayout.getEditText().getText().toString();
            String recipientNpa = recipientNpaTextInputLayout.getEditText().getText().toString();
            String recipientCity = recipientCityTextInputLayout.getEditText().getText().toString();

            Item updatedItem = new Item(shippingPriority, packageWeight, senderLastname,
                    senderFirstname, senderAddress, senderNpa, senderCity, recipientLastname,
                    recipientFirstname, recipientAddress, recipientNpa, recipientCity
            );

            updatedItem.setShippingNumber(item.getShippingNumber());

            itemViewModel.update(updatedItem);

            for (Shipment updatedShipment: updatedShipments) {
                System.out.println(updatedShipment);
                shipmentViewModel.update(updatedShipment);
            }

            snackbarIntent = new Intent(this, PostEmployeeMainActivity.class);

            snackbarIntent.putExtra(SNACKBAR_PACKAGE_INFO_SAVED,
                    getString(R.string.post_employee_manage_package_snackbar_message_package_info_saved)
            );
            startActivity(snackbarIntent);
        }
    }

    private void updateInputsValues(Item item) {
        if (item != null) {
            shippingPriorityDropDown.setText(String.valueOf(item.getShippingPriority()), false);
            packageWeightTextInputLayout.getEditText().setText(Double.toString(item.getWeight()));
            senderLastnameTextInputLayout.getEditText().setText(item.getSenderLastname());
            senderFirstnameTextInputLayout.getEditText().setText(item.getSenderFirstname());
            senderAddressTextInputLayout.getEditText().setText(item.getSenderAddress());
            senderNpaTextInputLayout.getEditText().setText(item.getSenderNpa());
            senderCityTextInputLayout.getEditText().setText(item.getSenderCity());
            recipientLastnameTextInputLayout.getEditText().setText(item.getRecipientLastname());
            recipientFirstnameTextInputLayout.getEditText().setText(item.getRecipientFirstname());
            recipientAddressTextInputLayout.getEditText().setText(item.getRecipientAddress());
            recipientNpaTextInputLayout.getEditText().setText(item.getRecipientNpa());
            recipientCityTextInputLayout.getEditText().setText(item.getRecipientCity());
        }
    }

    private void setItem(Item item) {
        this.item = item;
    }
}
