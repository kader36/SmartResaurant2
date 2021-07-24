package Controllers.Tables;

import BddPackage.FoodOperation;
import BddPackage.FoodOrderOperation;
import BddPackage.OrdersOperation;
import BddPackage.TablegainsOperationsBDD;
import Models.Food;
import Models.FoodOrder;
import Models.Orders;
import Models.TableGainedMoney;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TableOrderDetailsControlleur implements Initializable {

    // fxml elements .
    @FXML
    private Label tableNumber;

    @FXML
    private GridPane itemsGridView;

    @FXML
    private TextField discountTextField;

    @FXML
    private Button cancelButotn;

    @FXML
    private Button payMoneyButton;

    @FXML
    private Text orderPrice;


    // variables.

    // test data.
    public static Orders curentOrder;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // initialize the buttons work.
        payMoneyButton.setOnAction(actionEvent -> {
            confirmOrder();
        });

        cancelButotn.setOnAction(actionEvent -> {
            cancelView();
        });

        // set the data .
        tableNumber.setText(String.valueOf(curentOrder.getId_table()));
        orderPrice.setText(String.valueOf(curentOrder.getPrice()));

        // set the list of orders.
        ArrayList<FoodOrder> list = new ArrayList<>();
        FoodOrderOperation foodOrderOperation=new FoodOrderOperation();
        list=foodOrderOperation.getElement(curentOrder);
        // add the foods.
        int column = 1;
        int row = 1;
        for (int foodIndex = 0; foodIndex < list.size(); foodIndex++) {
            Food food=new Food();
            FoodOperation foodOperation=new FoodOperation();
            food=foodOperation.getFoodByID(list.get(foodIndex).getId_food());

            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/Views/TablesViews/tabelDetailsItem.fxml"));
                AnchorPane anchorPane = loader.load();
                tableDetailsItemcontrolleur tableGridItemControlleur = loader.getController();
                // search for the price and title and change them here.
                tableGridItemControlleur.setDetailsItemdData(
                        String.valueOf(food.getName()),
                        String.valueOf(food.getPrice()),
                        String.valueOf(list.get(foodIndex).getQuantity())
                );
                itemsGridView.add(anchorPane,column,row);
                row++;
                itemsGridView.setMargin(anchorPane,new Insets(5));

            }catch (IOException e){
                e.printStackTrace();
            }
            
        }



    }


    // method to confirm the order purshase.
    void confirmOrder(){

        // add the order to the database.
        // add the order to the table order.
        OrdersOperation orderDatabaseConnector = new OrdersOperation();
        orderDatabaseConnector.update(curentOrder);
        System.out.println(curentOrder.getId());

        // delete the order from teh view.
        TablesController.tabelsOrders.removeIf(orders -> orders.getId() == curentOrder.getId());
        // delet from the server orders list.


        // refresh the view.
        TablesController.orderConfirmed.setValue(! TablesController.orderConfirmed.getValue());


        // refresh teh table gains.
        refrechTableGains(curentOrder.getId_table(),curentOrder.getPrice());

        // close the drawer.
        TablesController.drawerOpening.setValue(! TablesController.drawerOpening.getValue());


    }

    // method to cancle.
    void cancelView(){
        //just close the drawer.
        TablesController.drawerOpening.setValue(! TablesController.drawerOpening.getValue());
    }



    // this method i think it should be in the slider details when the user confirm the purchase.
    // a method to refresh the gained money for each table.
    void refrechTableGains(int tableNumber, double money){
        TableGainedMoney tableGainedMoney=new TableGainedMoney();
        tableGainedMoney.setTableId(tableNumber);
        TablegainsOperationsBDD tablegainsOperationsBDD=new TablegainsOperationsBDD();
        tablegainsOperationsBDD.insertNewGain(tableGainedMoney,money);


    }

}
