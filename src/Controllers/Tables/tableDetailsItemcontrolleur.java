package Controllers.Tables;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class tableDetailsItemcontrolleur implements Initializable {

    // fxml elements.
    @FXML
    private Label foodtitle;

    @FXML
    private Label foodPrice;

    @FXML
    private Label foodQuantity;

    @FXML
    private ImageView foodImage;


    // variables.



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    // method to set the view data.

     void setDetailsItemdData(String title, String price, String quantity){

        this.foodtitle.setText(title);
        this.foodPrice.setText(price);
        this.foodQuantity.setText(quantity);

        // set the image also

    }
}
