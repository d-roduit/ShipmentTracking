package ch.dc.shipment_tracking_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class ClientSendPackageActivity extends AppCompatActivity {

    private AutoCompleteTextView dropDownText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_send_package);

        dropDownText = findViewById(R.id.shipping_priority_list);

        String[] shipping_priority_items = getResources().getStringArray(R.array.shipping_priority_items);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, R.layout.dropdown_item, shipping_priority_items
        );

        dropDownText.setAdapter(adapter);

    }

    //Method to close the keyboard when we click outside of an input field.
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}