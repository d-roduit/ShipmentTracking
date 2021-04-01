package ch.dc.shipment_tracking_app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

public class PostEmployeeShippingNumberActivity extends BaseActivity {

    private TextInputLayout shippingNumberTextInputLayout;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_employee_shipping_number);

        shippingNumberTextInputLayout = findViewById(R.id.post_employee_shipping_number_textInputLayout);
        nextButton = findViewById(R.id.post_employee_shipping_number_next_button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isShippingNumberValid = InputValidator.validateInputs(shippingNumberTextInputLayout);

                if (isShippingNumberValid) {
                    redirectActivity(PostEmployeeShippingNumberActivity.this, PostEmployeeMainActivity.class);
                }
            }
        });
    }
}
