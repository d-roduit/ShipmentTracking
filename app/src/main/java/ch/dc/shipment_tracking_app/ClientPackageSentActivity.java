package ch.dc.shipment_tracking_app;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ClientPackageSentActivity extends BaseActivity {

    private ClipboardManager clipboardManager;
    private ClipData clipData;
    private TextView shippingNumberToCopy;
    private ImageView copyButton;
    private Button finishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_package_sent);

        setTitle(getString(R.string.client_package_sent_activity_title));

        finishButton = findViewById(R.id.button_finish);
        shippingNumberToCopy = findViewById(R.id.shipping_number_to_copy);
        copyButton = findViewById(R.id.button_copy);
        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        //Copy the shipping number in clipboard
        copyButton.setOnClickListener(v -> {
            String shippingNumberText = shippingNumberToCopy.getText().toString();
            clipData = ClipData.newPlainText("text", shippingNumberText);
            clipboardManager.setPrimaryClip(clipData);
            String toastText = getString(R.string.shipping_number_copied_toast);
            Toast.makeText(getApplicationContext(), toastText,
                    Toast.LENGTH_SHORT).show();
        });

        finishButton.setOnClickListener(v -> redirectActivity(this, ClientMainActivity.class));
    }
}