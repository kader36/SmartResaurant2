package Controllers.Tables;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class CategoryItemController implements Initializable {
    @FXML
    private HBox pane;
    @FXML
    private Text txtname;
    private int id;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pane.setOnMouseClicked(mouseEvent -> {
           FoodBuyController.ID=id;
            FoodBuyController.loadDataFood.setValue(!FoodBuyController.loadDataFood.getValue());
        });
    }


    public void setDetailsItemdData(int id, String name) {
        this.id=id;
        txtname.setText(name);
    }
}
