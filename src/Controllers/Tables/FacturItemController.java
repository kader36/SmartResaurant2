package Controllers.Tables;

import Models.Food;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class FacturItemController implements Initializable {
    @FXML
    private Text name;

    @FXML
    private Text txt_qentity;

    @FXML
    private Button btn_delete;

    private Food food;



    private  int cont=1;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_delete.setOnAction(actionEvent -> {
            FoodBuyController.idfood=food.getId();
            FoodBuyController.deletFood.setValue(!FoodBuyController.deletFood.getValue());

        });
    }
    public  void setItemData(int Cont,Food food){
        this.name.setText(food.getName());
        txt_qentity.setText(String.valueOf(Cont));
        this.food=food;
    }


}
