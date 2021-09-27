package Controllers.Tables;
import Models.Orders;
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


    public Orders curentItemIndex;
    @FXML
    private Text txt_order;



    public void setGridItemData(String table, double Price, Orders curentItemIndex){

        orderPrice.setText(String.valueOf(Price));
        tableNumber.setText(String.valueOf(table));
        this.curentItemIndex = curentItemIndex;
        if(curentItemIndex.getId_table()==99){
            txt_order.setText("طلب خارجي");
            tableNumber.setText("");
        }

    }


    public void openDrawer(){

        // set the selected food data.
        TablesController.CurentOrder = curentItemIndex;
        // open the drawer.
        TablesController.drawerOpening.setValue(! TablesController.drawerOpening.get());


    }



}
