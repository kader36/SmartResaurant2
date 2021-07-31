package Controllers.newKitchenChef;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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
        itemImage.setFitWidth(140);
        itemImage.setFitHeight(80);
        itemImage.setPreserveRatio(false);
        Rectangle clip = new Rectangle();
        clip.setWidth(140);
        clip.setHeight(80);

        clip.setArcHeight(25);
        clip.setArcWidth(25);
        clip.setStroke(Color.BLACK);
        itemImage.setClip(clip);

        // snapshot the rounded image.
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        WritableImage myimage = itemImage.snapshot(parameters, null);

        // remove the rounding clip so that our effect can show through.

        itemImage.setEffect(new DropShadow(5, Color.BLACK));
        itemImage.setImage(myimage);

        this.itemTitle.setText(itemTitle);
        this.itemTitle.setStyle("-fx-font-size: 16");
        this.itemQuantity.setText(quantity);
        this.itemQuantity.setStyle("-fx-font-size: 14");
        this.qan.setStyle("-fx-font-size: 14±±±±±±±");


    }
}
