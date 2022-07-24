package Controllers.Tables;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class TabelItemController {
    @FXML
    private AnchorPane anchor;

    @FXML
    private ImageView itemImage;

    @FXML
    private Label itemName;

    public void color(String color,String Num){
        if(color.equals("GREEN")){
            anchor.setStyle("-fx-background-color:GREEN;");
        }else {
            anchor.setStyle("-fx-background-color:RED;");
        }
        itemName.setText(Num);
    }


}
