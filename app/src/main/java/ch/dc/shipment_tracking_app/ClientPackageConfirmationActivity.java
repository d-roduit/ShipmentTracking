package ch.dc.shipment_tracking_app;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import ch.dc.shipment_tracking_app.db.entity.Item;
import ch.dc.shipment_tracking_app.viewmodel.ItemViewModel;

public class ClientPackageConfirmationActivity extends BaseActivity {

    public static final String SEND_SHIPPING_NUMBER = "SEND_SHIPPING_NUMBER";

    private ItemViewModel itemViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_package_confirmation);

        setTitle(getString(R.string.client_confirmation_package_activity_title));

        Intent intent = getIntent();

        //ViewModel
        itemViewModel = new ViewModelProvider
                .AndroidViewModelFactory(getApplication())
                .create(ItemViewModel.class);

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
        double weight = intent.getDoubleExtra(ClientSendPackageActivity.SEND_WEIGHT, 0.00);
        char shippingPriority = intent.getCharExtra(ClientSendPackageActivity.SEND_SHIPPING_PRIORITY, 'B');
        String senderFirstname = intent.getStringExtra(ClientSendPackageActivity.SEND_SENDER_FIRSTNAME);
        String senderLastname = intent.getStringExtra(ClientSendPackageActivity.SEND_SENDER_LASTNAME);
        String senderAddress = intent.getStringExtra(ClientSendPackageActivity.SEND_SENDER_ADDRESS);
        String senderNpa = intent.getStringExtra(ClientSendPackageActivity.SEND_SENDER_NPA);
        String senderCity = intent.getStringExtra(ClientSendPackageActivity.SEND_SENDER_CITY);
        String recipientFirstname = intent.getStringExtra(ClientSendPackageActivity.SEND_RECIPIENT_FIRSTNAME);
        String recipientLastname = intent.getStringExtra(ClientSendPackageActivity.SEND_RECIPIENT_LASTNAME);
        String recipientAddress = intent.getStringExtra(ClientSendPackageActivity.SEND_RECIPIENT_ADDRESS);
        String recipientNpa = intent.getStringExtra(ClientSendPackageActivity.SEND_RECIPIENT_NPA);
        String recipientCity = intent.getStringExtra(ClientSendPackageActivity.SEND_RECIPIENT_CITY);

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

        //sendPackageButton.setOnClickListener(v -> redirectActivity(this, ClientPackageSentActivity.class));
        sendPackageButton.setOnClickListener(v -> {
            Intent intent1 = new Intent(this, ClientPackageSentActivity.class);

            Item item = new Item(shippingPriority, weight, senderFirstname, senderLastname,
                    senderAddress, senderNpa, senderCity, recipientFirstname,
                    recipientLastname, recipientAddress, recipientNpa, recipientCity);
            itemViewModel.insert(item);

            intent1.putExtra(SEND_SHIPPING_NUMBER, item.getShippingNumber());

            startActivity(intent1);
        });

    }

}