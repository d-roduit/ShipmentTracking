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
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import ch.dc.shipment_tracking_app.adapter.ManageRecyclerAdapter;
import ch.dc.shipment_tracking_app.adapter.onDeleteShipmentClickListener;
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

    private Item item;

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
    private ImageView deleteShipmentButton;

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
        deleteButton = findViewById(R.id.post_employee_manage_package_button_delete);
        deleteShipmentButton = findViewById(R.id.post_employee_manage_package_delete_shipment_button);

        // Initialize the shipping priority string array
        String[] shipping_priority_items = getResources().getStringArray(R.array.shipping_priority_items);
        ArrayAdapter<String> shippingPriorityAdapter = new ArrayAdapter<>(
                this, R.layout.dropdown_item, shipping_priority_items
        );
        shippingPriorityDropDown.setAdapter(shippingPriorityAdapter);

        // Initialize the viewModels
        itemViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(ItemViewModel.class);
        shipmentViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(ShipmentViewModel.class);

        // Get shipping number from sharedPreferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int shippingNumber = sharedPreferences.getInt(PostEmployeeShippingNumberActivity.SAVED_SHIPPING_NUMBER, 0);

        // RecyclerView
        RecyclerView manageRecyclerView = findViewById(R.id.post_employee_manage_package_recycler_view);
        manageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        manageRecyclerView.setHasFixedSize(true);

        onDeleteShipmentClickListener onDeleteShipmentClickListener = new onDeleteShipmentClickListener() {
            @Override
            public void onItemClick(Shipment shipment) {
                shipmentViewModel.delete(shipment, null);
            }
        };

        ManageRecyclerAdapter manageRecyclerAdapter = new ManageRecyclerAdapter(this, onDeleteShipmentClickListener);
        manageRecyclerView.setAdapter(manageRecyclerAdapter);

        LiveData<List<Shipment>> shipmentsListLiveData = shipmentViewModel.getShipmentByShippingNumber(shippingNumber);
        shipmentsListLiveData.observe(this, new Observer<List<Shipment>>() {
            @Override
            public void onChanged(List<Shipment> shipments) {
                manageRecyclerAdapter.setShipments(shipments);
            }
        });

        // Fetch database data
        LiveData<Item> itemLiveData = itemViewModel.getItemByShippingNumber(shippingNumber);
        LiveData<List<Shipment>> itemShipmentsLiveData = shipmentViewModel.getShipmentByShippingNumber(shippingNumber);

        // Fill in the views with the database data
        itemLiveData.observe(this, new Observer<Item>() {
            @Override
            public void onChanged(Item item) {
                setItem(item);
                updateInputsValues(item);
            }
        });

        itemShipmentsLiveData.observe(this, new Observer<List<Shipment>>() {
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
                recipientNpaTextInputLayout, recipientCityTextInputLayout
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
        System.out.println("deletePackageFromDB called");

        if (item != null) {
            System.out.println("Removing package from DB...");
            itemViewModel.delete(item, nbOfRowsAffected -> {
                System.out.println("Package removed from DB");
                snackbarIntent.putExtra(SNACKBAR_PACKAGE_DELETED,
                        getString(R.string.post_employee_manage_package_snackbar_message_package_deleted)
                );
                startActivity(snackbarIntent);
            });
        }
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
            recipientNpaTextInputLayout.getEditText().setText(item.getRecipientNPA());
            recipientCityTextInputLayout.getEditText().setText(item.getRecipientCity());
        }
    }

    private void setItem(Item item) {
        this.item = item;
    }
}
