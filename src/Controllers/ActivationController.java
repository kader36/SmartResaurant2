package Controllers;

import BddPackage.SerialOperation;
import Models.Serial;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ActivationController {
    @FXML
    private JFXTextField code;

    @FXML
    private JFXButton btnclose;

    @FXML
    private Label error;
    @FXML
    private Label error1;

    @FXML
    void Active(ActionEvent event) {
        Key  key=new Key();
        String macAdress= key.macAdress();
        if(code.getText().equals(macAdress)){
            SerialOperation serialOperation=new SerialOperation();
            Serial serial=new Serial();
            serial.setSerial(macAdress);
            serialOperation.update(serial);
            error.setVisible(true);
        }else {
            error1.setVisible(true);
        }

    }

    @FXML
    void close(ActionEvent event){
        Stage stage = (Stage) btnclose.getScene().getWindow();
        stage.close();

    }
}


