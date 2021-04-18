package ch.dc.shipment_tracking_app;

import android.content.Intent;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ch.dc.shipment_tracking_app.adapter.RecyclerAdapter;
import ch.dc.shipment_tracking_app.db.entity.Shipment;
import ch.dc.shipment_tracking_app.viewmodel.ItemViewModel;
import ch.dc.shipment_tracking_app.viewmodel.ShipmentViewModel;

public class ClientTrackPackageInformationActivity extends BaseActivity {

    private ItemViewModel itemViewModel;
    private ShipmentViewModel shipmentViewModel;

    private LinearLayout expandableViewPackage;
    private LinearLayout expandableViewSender;
    private LinearLayout expandableViewRecipient;
    private LinearLayout expandableViewStatus;
    private Button arrowButtonPackage;
    private Button arrowButtonSender;
    private Button arrowButtonRecipient;
    private Button arrowButtonStatus;
    private Button finishButton;
    private CardView packageCardview;
    private CardView senderCardview;
    private CardView recipientCardview;
    private CardView statusCardview;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_track_package_information);

        setTitle(R.string.client_track_package_information_activity_title);

        Intent intent = getIntent();

        // ViewModels
        itemViewModel = new ViewModelProvider
                .AndroidViewModelFactory(getApplication())
                .create(ItemViewModel.class);

        shipmentViewModel = new ViewModelProvider
                .AndroidViewModelFactory(getApplication())
                .create(ShipmentViewModel.class);

        //RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recycler_view_track_status);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        RecyclerAdapter adapter = new RecyclerAdapter(this);
        recyclerView.setAdapter(adapter);

        //Views
        expandableViewPackage = findViewById(R.id.track_package_expandableView_package);
        expandableViewSender = findViewById(R.id.track_package_expandableView_sender);
        expandableViewRecipient = findViewById(R.id.track_package_expandableView_recipient);
        expandableViewStatus = findViewById(R.id.track_package_expandableView_status);
        arrowButtonPackage = findViewById(R.id.button_expandCard_arrow_down_package);
        arrowButtonSender = findViewById(R.id.button_expandCard_arrow_down_sender);
        arrowButtonRecipient = findViewById(R.id.button_expandCard_arrow_down_recipient);
        arrowButtonStatus = findViewById(R.id.button_expandCard_arrow_down_status);
        finishButton = findViewById(R.id.track_package_button_finish);
        packageCardview = findViewById(R.id.cardview_package_information);
        senderCardview = findViewById(R.id.cardview_sender_information);
        recipientCardview = findViewById(R.id.cardview_recipient_information);
        statusCardview = findViewById(R.id.cardview_status);
        textViewWeight = findViewById(R.id.track_package_weight);
        textViewShippingPriority = findViewById(R.id.track_package_shipping_priority);
        textViewSenderFirstname = findViewById(R.id.track_package_sender_firstname);
        textViewSenderLastname = findViewById(R.id.track_package_sender_lastname);
        textViewSenderAddress = findViewById(R.id.track_package_sender_address);
        textViewSenderNpa = findViewById(R.id.track_package_sender_npa);
        textViewSenderCity = findViewById(R.id.track_package_sender_city);
        textViewRecipientFirstname = findViewById(R.id.track_package_recipient_firstname);
        textViewRecipientLastname = findViewById(R.id.track_package_recipient_lastname);
        textViewRecipientAddress = findViewById(R.id.track_package_recipient_address);
        textViewRecipientNpa = findViewById(R.id.track_package_recipient_npa);
        textViewRecipientCity = findViewById(R.id.track_package_recipient_city);

        int shippingNumber = intent.getIntExtra(ClientTrackPackageActivity.SEND_SHIPPING_NUMBER, 1);

        itemViewModel.getItemByShippingNumber(shippingNumber).observe(this, item -> {
            //Set the TextViews text with the corresponding value.
            textViewWeight.setText(String.valueOf(item.getWeight()));
            textViewShippingPriority.setText(item.getShippingPriority());
            textViewSenderFirstname.setText(item.getSenderFirstname());
            textViewSenderLastname.setText(item.getSenderLastname());
            textViewSenderAddress.setText(item.getSenderAddress());
            textViewSenderNpa.setText(item.getSenderNpa());
            textViewSenderCity.setText(item.getSenderCity());
            textViewRecipientFirstname.setText(item.getRecipientFirstname());
            textViewRecipientLastname.setText(item.getRecipientLastname());
            textViewRecipientAddress.setText(item.getRecipientAddress());
            textViewRecipientNpa.setText(item.getRecipientNPA());
            textViewRecipientCity.setText(item.getRecipientCity());
        });


        shipmentViewModel.getShipmentsByShippingNumber(shippingNumber).observe(this, new Observer<List<Shipment>>() {
            @Override
            public void onChanged(List<Shipment> shipments) {
                adapter.setShipments(shipments);
            }
        });

        arrowButtonPackage.setOnClickListener(v ->
                expandCard(packageCardview, expandableViewPackage, arrowButtonPackage));
        arrowButtonSender.setOnClickListener(v ->
                expandCard(senderCardview, expandableViewSender, arrowButtonSender));
        arrowButtonRecipient.setOnClickListener(v ->
                expandCard(recipientCardview, expandableViewRecipient, arrowButtonRecipient));
        arrowButtonStatus.setOnClickListener(v ->
                expandCard(statusCardview, expandableViewStatus, arrowButtonStatus));

        finishButton.setOnClickListener(v -> redirectActivity(this, ClientMainActivity.class));
    }

    private void expandCard(CardView cardView, View expandableView, Button button) {
        if(expandableView.getVisibility() == View.GONE) {
            TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
            expandableView.setVisibility(View.VISIBLE);
            button.setBackgroundResource(R.drawable.ic_arrow_up);
        } else {
            TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
            expandableView.setVisibility(View.GONE);
            button.setBackgroundResource(R.drawable.ic_arrow_down);
        }
    }
}