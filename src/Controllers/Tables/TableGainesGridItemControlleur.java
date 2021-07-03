package Controllers.Tables;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TableGainesGridItemControlleur {


    // fxml elements.
    @FXML
    private Label tableNumber;

    @FXML
    private Label tableGain;


    // methods.
    public void setTableGainItemData(double tableNumber, double tableGains){

        this.tableNumber.setText(String.valueOf(tableNumber));
        this.tableGain.setText(String.valueOf(tableGains));
    }


}
