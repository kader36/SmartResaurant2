package Controllers.Tables;

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
import javafx.scene.text.Text;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class FoodItemController implements Initializable {

    @FXML
    private ImageView itemImage;

    @FXML
    private Label itemName;

    @FXML
    private Button itemToggoleButton;

    @FXML
    private Button btnplus;

    @FXML
    private Button btn;

    @FXML
    private Text txt_qentity;
    public boolean bool=false;


    public  int Id;
    public  int qentity=1;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txt_qentity.setText(String.valueOf(qentity));
        itemToggoleButton.setOnAction(actionEvent -> {
            if (bool==false){
                FoodBuyController.idfood=Id;
                FoodBuyController.qentity=qentity;
                FoodBuyController.addfood.setValue(!FoodBuyController.addfood.getValue());
            }
            this.bool=true;
            });
        btnplus.setOnAction(actionEvent -> {
            qentity++;
            txt_qentity.setText(String.valueOf(qentity));
        });
        btn.setOnAction(actionEvent -> {
            if(qentity!=1)
            qentity--;
            txt_qentity.setText(String.valueOf(qentity));
        });
    }

   public  void setItemData(String name, String imagePAth,int id){
       this.Id=id;

        this.itemName.setText(name);
        File file = new File(imagePAth);
        Image image = new Image(file.toURI().toString());
        this.itemImage.setImage(image);
        itemImage.setFitWidth(100);
        itemImage.setFitHeight(75);
        itemImage.setPreserveRatio(false);
        Rectangle clip = new Rectangle();
        clip.setWidth(100);
        clip.setHeight(75);
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

    }
}
