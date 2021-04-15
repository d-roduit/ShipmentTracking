package ch.dc.shipment_tracking_app;

import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class InputValidator {

    /**
     * Makes the validation of input fields. Check if the inputs are empty or not.
     *
     * @param textInputLayout
     *          The textInputLayout whose EditText input has to be validated.
     * @return
     *          A boolean. True if there are no error, false otherwise.
     */
    private static boolean validateInput(TextInputLayout textInputLayout) throws IllegalArgumentException {
        EditText editText = textInputLayout.getEditText();

        if (editText == null) {
            throw new IllegalArgumentException("The given textInputLayout has no EditText field.");
        }

        String value = editText.getText().toString().trim();
        boolean isInputValid = !value.isEmpty();
        String errorMessage = (isInputValid) ? null : "Mandatory";
        textInputLayout.setErrorEnabled(isInputValid);
        textInputLayout.setError(errorMessage);

        return isInputValid;
    }

    /**
     *
     * @param textInputLayouts The inputs to validate.
     *                         Each input will be pass to the {@code validateInput()} method.
     * @return True if all the inputs are valid. False otherwise.
     */
    public static boolean validateInputs(TextInputLayout... textInputLayouts) {
        boolean areAllInputsOk = true;

        for (TextInputLayout textInputLayout: textInputLayouts) {
            boolean isInputValid = validateInput(textInputLayout);
            if (areAllInputsOk && !isInputValid) {
                areAllInputsOk = false;
            }
        }

        return areAllInputsOk;
    }

    public static boolean validateInputs(List<TextInputLayout> textInputLayouts) {
        boolean areAllInputsOk = true;

        for (TextInputLayout textInputLayout: textInputLayouts) {
            boolean isInputValid = validateInput(textInputLayout);
            if (areAllInputsOk && !isInputValid) {
                areAllInputsOk = false;
            }
        }

        return areAllInputsOk;
    }
}
