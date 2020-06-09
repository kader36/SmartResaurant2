package Controllers;

import com.jfoenix.controls.JFXTextField;

public class ValidateController {
    public void inputTextValueType(JFXTextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length()>10){ textField.setText(oldValue);
                return;
            }
            if (newValue.matches("[ا-يa-z]*")) return;
            textField.setText(oldValue);
        });

    }
    public void inputNumberValueType(JFXTextField txt_number) {
        txt_number.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 10) {
                txt_number.setText(oldValue.replaceAll("[^\\d]", ""));
                return;
            }
            if (newValue.matches("[0-9]*")) return;
            txt_number.setText(oldValue);
        });
    }
}
