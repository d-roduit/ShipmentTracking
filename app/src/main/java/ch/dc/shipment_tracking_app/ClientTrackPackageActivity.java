package ch.dc.shipment_tracking_app;

import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;

import ch.dc.shipment_tracking_app.db.entity.Item;
import ch.dc.shipment_tracking_app.viewmodel.ItemViewModel;

public class ClientTrackPackageActivity extends BaseActivity {

    public static final String SEND_SHIPPING_NUMBER = "SEND_SHIPPING_NUMBER";

    private ItemViewModel itemViewModel;

    private TextInputLayout inputShippingNumber;
    private TextView noShippingNumberFoundTextView;
    private Button trackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_track_package);

        setTitle(getString(R.string.client_track_package_activity_title));

        itemViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(ItemViewModel.class);

        // Views
        inputShippingNumber = findViewById(R.id.input_shipping_number);
        noShippingNumberFoundTextView = findViewById(R.id.client_track_package_no_shipping_number_found_error_text_view);
        trackButton = findViewById(R.id.button_track);

        inputShippingNumber.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (noShippingNumberFoundTextView.getVisibility() == View.VISIBLE) {
                    noShippingNumberFoundTextView.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        trackButton.setOnClickListener(v -> {
            boolean isInputValid = InputValidator.validateInputs(inputShippingNumber);
            if (isInputValid) {
                int shippingNumber = Integer.parseInt(inputShippingNumber.getEditText().getText().toString());
                trackPackageIfExists(shippingNumber);
            }
        });
    }

    private void trackPackageIfExists(int shippingNumber) {
        itemViewModel.countShippingNumber(shippingNumber, numberOfOccurrences -> {
            if (numberOfOccurrences == 0) {
                noShippingNumberFoundTextView.setVisibility(View.VISIBLE);
                return;
            }
            redirectToTrackingInformation(shippingNumber);
        });
    }

    private void redirectToTrackingInformation(int shippingNumber) {
        Intent intent = new Intent(ClientTrackPackageActivity.this, ClientTrackPackageInformationActivity.class);
        intent.putExtra(SEND_SHIPPING_NUMBER, shippingNumber);
        startActivity(intent);
    }
}