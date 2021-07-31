package Controllers.Tables;

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

     void setDetailsItemdData(String title, String price, String quantity, Image foodimage){
         foodImage.setFitHeight(70);
         foodImage.setFitWidth(85);
         Rectangle clip = new Rectangle();
         clip.setWidth(85);
         clip.setHeight(70);

         clip.setArcHeight(10);
         clip.setArcWidth(10);
         clip.setStroke(Color.BLACK);
         foodImage.setClip(clip);

         // snapshot the rounded image.
         SnapshotParameters parameters = new SnapshotParameters();
         parameters.setFill(Color.TRANSPARENT);
         WritableImage myimage = foodImage.snapshot(parameters, null);

         // remove the rounding clip so that our effect can show through.
         foodImage.setEffect(new DropShadow(5, Color.BLACK));
         foodImage.setImage(myimage);
        this.foodtitle.setText(title);
        this.foodPrice.setText(price);
        this.foodQuantity.setText(quantity);
        this.foodImage.setImage(foodimage);

        // set the image also

    }
}
