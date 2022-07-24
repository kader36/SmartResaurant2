package Controllers.newKitchenChef;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class AvailabaleItemControlleur implements Initializable {


    // fxml elements.

    @FXML
    private ImageView itemImage;

    @FXML
    private Label itemName;

    @FXML
    private Button itemToggoleButton;

    // variables.
    int itemId;
    boolean isFood;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //set the butons action methods.
        itemToggoleButton.setOnAction(actionEvent -> {
            activeteDeactivateItem();
        });


        // intilize the item elements.
    }

    // method to set teh item data.
    void setItemData(String name, boolean avilabale, String imagePAth){

        this.itemName.setText(name);
        File file = new File(imagePAth);
        Image image = new Image(file.toURI().toString());
        this.itemImage.setImage(image);
        itemImage.setFitWidth(130);
        itemImage.setFitHeight(90);
        itemImage.setPreserveRatio(false);
        Rectangle clip = new Rectangle();
        clip.setWidth(130);
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
        itemImage.setClip(null);
        itemImage.setEffect(new DropShadow(1, Color.BLACK));
        itemImage.setImage(myimage);

        //this.itemToggoleButton.setSelected(! avilabale);
        // set the button state based on teh boolean variable
        if (avilabale == true){
            itemToggoleButton.setText("متوفر");
            itemToggoleButton.setStyle("-fx-background-color: green;-fx-border-radius:1em;-fx-background-radius: 1em;");

        }else{
            itemToggoleButton.setText("غير متوفر");
            itemToggoleButton.setStyle("-fx-background-color:#EE3D48;\n" +
                    "\t-fx-font-family: 'HSDream-Regular';-fx-border-radius:1em;-fx-background-radius: 1em;-fx-background-insets: 0;-fx-border-width: 1 1 1 1;");

        }
    }

    // method to activate / deactivate the item .
    void activeteDeactivateItem(){

        if (itemToggoleButton.getText().equals("غير متوفر")){
            itemToggoleButton.setText("متوفر");
            itemToggoleButton.setStyle("-fx-background-color: green;-fx-border-radius:1em;-fx-background-radius: 1em;");
            //itemToggoleButton.setStyle();

        }else{
            itemToggoleButton.setText("غير متوفر");
            itemToggoleButton.setStyle("-fx-background-color:#EE3D48;\n" +
                    "\t-fx-font-family: 'HSDream-Regular';-fx-border-radius:1em;-fx-background-radius: 1em;-fx-background-insets: 0;-fx-border-width: 1 1 1 1;");
        }
        // change the data in the temporary list of the grid view based on the item type.
        if(isFood == true){
            for (int itemIndex = 0; itemIndex < AvailabaleFoodsControlleur.temporaryFoodsList.size(); itemIndex++) {
                if (AvailabaleFoodsControlleur.temporaryFoodsList.get(itemIndex).getId() == itemId){
                    if (itemToggoleButton.getText().equals("متوفر")){
                        AvailabaleFoodsControlleur.temporaryFoodsList.get(itemIndex).setAvailabale(true);
                    }else{
                        AvailabaleFoodsControlleur.temporaryFoodsList.get(itemIndex).setAvailabale(false);
                    }
                    break;

                }
            }

            for (int index = 0; index < AvailabaleFoodsControlleur.temporaryFoodsList.size(); index++) {
                System.out.println(AvailabaleFoodsControlleur.temporaryFoodsList.get(index).getName() + " : " +
                        AvailabaleFoodsControlleur.temporaryFoodsList.get(index).isAvailabale()
                +"//" + AvailabaleFoodsControlleur.foodsList.get(index).getName() + " : " +
                        AvailabaleFoodsControlleur.foodsList.get(index).isAvailabale());
            }
            System.out.println("---------------------------------------------");

        }else{
            // in case we are dealing with drinks .
        }

    }

    //  method to set the item id.
    void setItemIdentifiers(int itemId, boolean isFood){
        this.itemId = itemId;
        this.isFood = isFood;
    }
}
