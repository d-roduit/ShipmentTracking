package ch.dc.shipment_tracking_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ClientPackageConfirmationActivity extends BaseActivity {

    //Views
    private TextView textViewWeight;
    private TextView textViewShippingPriority;
    private TextView textViewSenderFirstname;
    private TextView textViewSenderLastname;
    private TextView textViewSenderAddress;
    private TextView textViewSenderNpa;
    private TextView textViewSenderCity;
    private TextView textViewRecipientFirstname;
    private TextView textViewRecipientLastname;
    private TextView textViewRecipientAddress;
    private TextView textViewRecipientNpa;
    private TextView textViewRecipientCity;
    private Button sendPackageButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_package_confirmation);

        setTitle(getString(R.string.client_confirmation_package_activity_title));

        Intent intent = getIntent();

        //Initialize the views.
        textViewWeight = findViewById(R.id.package_confirmation_weight);
        textViewShippingPriority = findViewById(R.id.package_confirmation_shipping_priority);
        textViewSenderFirstname = findViewById(R.id.package_confirmation_sender_firstname);
        textViewSenderLastname = findViewById(R.id.package_confirmation_sender_lastname);
        textViewSenderAddress = findViewById(R.id.package_confirmation_sender_address);
        textViewSenderNpa = findViewById(R.id.package_confirmation_sender_npa);
        textViewSenderCity = findViewById(R.id.package_confirmation_sender_city);
        textViewRecipientFirstname = findViewById(R.id.package_confirmation_recipient_firstname);
        textViewRecipientLastname = findViewById(R.id.package_confirmation_recipient_lastname);
        textViewRecipientAddress = findViewById(R.id.package_confirmation_recipient_address);
        textViewRecipientNpa = findViewById(R.id.package_confirmation_recipient_npa);
        textViewRecipientCity = findViewById(R.id.package_confirmation_recipient_city);
        sendPackageButton = findViewById(R.id.button_send_package);

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

        sendPackageButton.setOnClickListener(v -> redirectActivity(this, ClientPackageSentActivity.class));

    }

}