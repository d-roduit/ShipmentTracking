package ch.dc.shipment_tracking_app;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import ch.dc.shipment_tracking_app.db.entity.Item;
import ch.dc.shipment_tracking_app.db.entity.Shipment;
import ch.dc.shipment_tracking_app.viewmodel.ItemViewModel;
import ch.dc.shipment_tracking_app.viewmodel.ShipmentViewModel;

public class ClientPackageConfirmationActivity extends BaseActivity {

    public static final String SEND_SHIPPING_NUMBER = "SEND_SHIPPING_NUMBER";

    private ItemViewModel itemViewModel;
    private ShipmentViewModel shipmentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_package_confirmation);

        setTitle(getString(R.string.client_confirmation_package_activity_title));

        //ViewModels
//        itemViewModel = new ViewModelProvider
//                .AndroidViewModelFactory(getApplication())
//                .create(ItemViewModel.class);
//
//        shipmentViewModel = new ViewModelProvider
//                .AndroidViewModelFactory(getApplication())
//                .create(ShipmentViewModel.class);

        ItemViewModel.Factory itemFactory = new ItemViewModel.Factory(getApplication());
        itemViewModel = new ViewModelProvider(this, itemFactory).get(ItemViewModel.class);

        ShipmentViewModel.Factory shipmentFactory = new ShipmentViewModel.Factory(getApplication());
        shipmentViewModel = new ViewModelProvider(this, shipmentFactory).get(ShipmentViewModel.class);

        //Initialize the views.
        TextView textViewWeight = findViewById(R.id.package_confirmation_weight);
        TextView textViewShippingPriority = findViewById(R.id.package_confirmation_shipping_priority);
        TextView textViewSenderFirstname = findViewById(R.id.package_confirmation_sender_firstname);
        TextView textViewSenderLastname = findViewById(R.id.package_confirmation_sender_lastname);
        TextView textViewSenderAddress = findViewById(R.id.package_confirmation_sender_address);
        TextView textViewSenderNpa = findViewById(R.id.package_confirmation_sender_npa);
        TextView textViewSenderCity = findViewById(R.id.package_confirmation_sender_city);
        TextView textViewRecipientFirstname = findViewById(R.id.package_confirmation_recipient_firstname);
        TextView textViewRecipientLastname = findViewById(R.id.package_confirmation_recipient_lastname);
        TextView textViewRecipientAddress = findViewById(R.id.package_confirmation_recipient_address);
        TextView textViewRecipientNpa = findViewById(R.id.package_confirmation_recipient_npa);
        TextView textViewRecipientCity = findViewById(R.id.package_confirmation_recipient_city);
        Button sendPackageButton = findViewById(R.id.button_send_package);


        //Get the value of each input field.
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        double weight = Double.longBitsToDouble(sharedPreferences.getLong(ClientSendPackageActivity.SEND_WEIGHT, Double.doubleToLongBits(0.00)));
        char shippingPriority = sharedPreferences.getString(ClientSendPackageActivity.SEND_SHIPPING_PRIORITY, "B").charAt(0);
        String senderFirstname = sharedPreferences.getString(ClientSendPackageActivity.SEND_SENDER_FIRSTNAME, "");
        String senderLastname = sharedPreferences.getString(ClientSendPackageActivity.SEND_SENDER_LASTNAME, "");
        String senderAddress = sharedPreferences.getString(ClientSendPackageActivity.SEND_SENDER_ADDRESS, "");
        String senderNpa = sharedPreferences.getString(ClientSendPackageActivity.SEND_SENDER_NPA, "");
        String senderCity = sharedPreferences.getString(ClientSendPackageActivity.SEND_SENDER_CITY, "");
        String recipientFirstname = sharedPreferences.getString(ClientSendPackageActivity.SEND_RECIPIENT_FIRSTNAME, "");
        String recipientLastname = sharedPreferences.getString(ClientSendPackageActivity.SEND_RECIPIENT_LASTNAME, "");
        String recipientAddress = sharedPreferences.getString(ClientSendPackageActivity.SEND_RECIPIENT_ADDRESS, "");
        String recipientNpa = sharedPreferences.getString(ClientSendPackageActivity.SEND_RECIPIENT_NPA, "");
        String recipientCity = sharedPreferences.getString(ClientSendPackageActivity.SEND_RECIPIENT_CITY, "");


        //Set the TextViews text with the corresponding value.
        textViewWeight.setText("" + weight);
        textViewShippingPriority.setText("" + shippingPriority);
        textViewSenderFirstname.setText(senderFirstname);
        textViewSenderLastname.setText(senderLastname);
        textViewSenderAddress.setText(senderAddress);
        textViewSenderNpa.setText(senderNpa);
        textViewSenderCity.setText(senderCity);
        textViewRecipientFirstname.setText(recipientFirstname);
        textViewRecipientLastname.setText(recipientLastname);
        textViewRecipientAddress.setText(recipientAddress);
        textViewRecipientNpa.setText(recipientNpa);
        textViewRecipientCity.setText(recipientCity);

        // Create the item
//        Item item = new Item(shippingPriority, weight, senderFirstname, senderLastname,
//                senderAddress, senderNpa, senderCity, recipientFirstname,
//                recipientLastname, recipientAddress, recipientNpa, recipientCity);
        Item item = new Item();
        item.setShippingPriority(shippingPriority);
        item.setWeight(weight);
        item.setSenderFirstname(senderFirstname);
        item.setSenderLastname(senderLastname);
        item.setSenderAddress(senderAddress);
        item.setSenderNpa(senderNpa);
        item.setSenderCity(senderCity);
        item.setRecipientFirstname(recipientFirstname);
        item.setRecipientLastname(recipientLastname);
        item.setRecipientAddress(recipientAddress);
        item.setRecipientNPA(recipientNpa);
        item.setRecipientCity(recipientCity);


        // Create the shipment
//        Shipment shipment = new Shipment(item.getShippingNumber(),
//                TrackingStatus.DEPOSITED.getStatusListPosition(), item.getSenderNpa(), item.getSenderCity());
        Shipment shipment = new Shipment();
        shipment.setShippingNumber(item.getShippingNumber());
        shipment.setStatus(TrackingStatus.DEPOSITED.getStatusListPosition());
        shipment.setNpa(item.getSenderNpa());
        shipment.setCity(item.getSenderCity());

        sendPackageButton.setOnClickListener(v -> sendPackage(item, shipment));
    }

    private void sendPackage(Item item, Shipment shipment) {
        Intent intent = new Intent(this, ClientPackageSentActivity.class);

        itemViewModel.insert(item);

        shipmentViewModel.insert(shipment);

        intent.putExtra(SEND_SHIPPING_NUMBER, item.getShippingNumber());

        removeDataFromSharedPreferences(PreferenceManager.getDefaultSharedPreferences(this));

        startActivity(intent);
    }

    public static void removeDataFromSharedPreferences(SharedPreferences sharedPreferences) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(ClientSendPackageActivity.SEND_WEIGHT);
        editor.remove(ClientSendPackageActivity.SEND_SHIPPING_PRIORITY);
        editor.remove(ClientSendPackageActivity.SEND_SENDER_FIRSTNAME);
        editor.remove(ClientSendPackageActivity.SEND_SENDER_LASTNAME);
        editor.remove(ClientSendPackageActivity.SEND_SENDER_ADDRESS);
        editor.remove(ClientSendPackageActivity.SEND_SENDER_NPA);
        editor.remove(ClientSendPackageActivity.SEND_SENDER_CITY);
        editor.remove(ClientSendPackageActivity.SEND_RECIPIENT_FIRSTNAME);
        editor.remove(ClientSendPackageActivity.SEND_RECIPIENT_LASTNAME);
        editor.remove(ClientSendPackageActivity.SEND_RECIPIENT_ADDRESS);
        editor.remove(ClientSendPackageActivity.SEND_RECIPIENT_NPA);
        editor.remove(ClientSendPackageActivity.SEND_RECIPIENT_CITY);
        editor.apply();
    }
}