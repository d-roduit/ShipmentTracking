package ch.dc.shipment_tracking_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

public class ClientTrackPackageActivity extends BaseActivity {

    public static final String SEND_SHIPPING_NUMBER = "SEND_SHIPPING_NUMBER";

    private Button buttonTrack;
    private TextInputLayout inputShippingNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_track_package);

        setTitle(getString(R.string.client_track_package_activity_title));

        //Views
        buttonTrack = findViewById(R.id.button_track);
        inputShippingNumber = findViewById(R.id.input_shipping_number);

        buttonTrack.setOnClickListener(v -> {
                Intent intent = new Intent(this, ClientTrackPackageInformationActivity.class);

                int textShippingNumber = Integer.parseInt(inputShippingNumber.getEditText().getText().toString());

                intent.putExtra(SEND_SHIPPING_NUMBER, textShippingNumber);

                startActivity(intent);
        });
    }
}