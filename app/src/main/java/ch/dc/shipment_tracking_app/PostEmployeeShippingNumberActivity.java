package ch.dc.shipment_tracking_app;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;

import ch.dc.shipment_tracking_app.viewmodel.ItemViewModel;

public class PostEmployeeShippingNumberActivity extends BaseActivity {

    public static final String SEND_SHIPPING_NUMBER = "SEND_SHIPPING_NUMBER";

    private ItemViewModel itemViewModel;

    private TextInputLayout shippingNumberTextInputLayout;
    private TextView noShippingNumberFoundTextView;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_employee_shipping_number);

        itemViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(ItemViewModel.class);

        shippingNumberTextInputLayout = findViewById(R.id.post_employee_shipping_number_textInputLayout);
        noShippingNumberFoundTextView = findViewById(R.id.post_employee_shipping_number_no_shipping_number_found_error_text_view);
        nextButton = findViewById(R.id.post_employee_shipping_number_next_button);

        shippingNumberTextInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
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


        nextButton.setOnClickListener(v -> {
            boolean isShippingNumberValid = InputValidator.validateInputs(shippingNumberTextInputLayout);

            if (isShippingNumberValid) {
                int shippingNumber = Integer.parseInt(shippingNumberTextInputLayout.getEditText().getText().toString());
                managePackageIfExists(shippingNumber);
            }
        });
    }

    private void managePackageIfExists(int shippingNumber) {
        itemViewModel.countShippingNumber(shippingNumber, numberOfOccurrences -> {
            if (numberOfOccurrences == 0) {
                noShippingNumberFoundTextView.setVisibility(View.VISIBLE);
                return;
            }
            redirectToPostEmployeeMainActivity(shippingNumber);
        });
    }

    private void redirectToPostEmployeeMainActivity(int shippingNumber) {
        Intent intent = new Intent(PostEmployeeShippingNumberActivity.this, PostEmployeeMainActivity.class);
        intent.putExtra(SEND_SHIPPING_NUMBER, shippingNumber);
        startActivity(intent);
    }
}
