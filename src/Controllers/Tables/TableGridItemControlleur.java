package Controllers.Tables;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class TableGridItemControlleur {

    @FXML
    private Text orderPrice;

    @FXML
    private Text tableNumber;

    @FXML
    private Button detailsButton;


    public int curentItemIndex;



    public void setGridItemData(String table, double Price, int curentItemIndex){

        orderPrice.setText(String.valueOf(Price));
        tableNumber.setText(String.valueOf(table));
        this.curentItemIndex = curentItemIndex;
    }


    public void openDrawer(){

        // set the selected food data.
        TablesController.selectedFoodIndex = curentItemIndex;
        // open the drawer.
        TablesController.drawerOpening.setValue(! TablesController.drawerOpening.get());


    }



}
