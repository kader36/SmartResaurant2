package Controllers;

import javafx.scene.control.TextField;

public class ValidateController {
    public void inputTextValueType(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 100) {
                textField.setText(oldValue);
                return;
            }
            if (newValue.matches("[ ا-يa-z]*")) return;
            textField.setText(oldValue);
        });
    }

    public void inputNumberValueType(TextField txt_number) {
        txt_number.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 13) {
                txt_number.setText(oldValue.replaceAll("[^\\d]", ""));
                return;
            }
            if (newValue.matches("[0-9]*")) return;
            txt_number.setText(oldValue);
        });
    }

    public void inputNumberValue(TextField txt_number) {
        txt_number.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                int i = Integer.parseInt(txt_number.getText());
                txt_number.setText(String.valueOf(i));
            }catch (Exception e){
                System.out.println("err");
                txt_number.setText("");
            }
        });

    }
    public void inputNumberphone(TextField txt_number) {
        txt_number.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 12) {
                txt_number.setText(oldValue.replaceAll("[^\\d]", ""));
                return;
            }
            if (newValue.matches("[0]{1}[0-9]{10}")) return;
            txt_number.setText(oldValue);
        });
    }
}
