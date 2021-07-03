package Controllers.newKitchenChef;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class KitchenOrderItemControlleur implements Initializable {

    // fxml elements.
    @FXML
    private ImageView itemImage;

    @FXML
    private Label itemTitle;

    @FXML
    private Label qan;
    @FXML
    private Label itemQuantity;


    // variables.

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    // method to load the item data.
    void loadData(Image image, String itemTitle, String quantity){

        this.itemImage.setImage(image);
        itemImage.setFitWidth(150);
        itemImage.setFitHeight(85);
        itemImage.setPreserveRatio(false);
        this.itemTitle.setText(itemTitle);
        this.itemTitle.setStyle("-fx-font-size: 16");
        this.itemQuantity.setText(quantity);
        this.itemQuantity.setStyle("-fx-font-size: 14");
        this.qan.setStyle("-fx-font-size: 14±±±±±±±");


    }
}
