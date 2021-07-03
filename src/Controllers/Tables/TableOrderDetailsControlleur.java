package Controllers.Tables;

import BddPackage.DrinkOrderOperation;
import BddPackage.FoodOrderOperation;
import BddPackage.OrdersOperation;
import BddPackage.TablegainsOperationsBDD;
import Models.Orders;
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
        // add the foods.
        int column = 1;
        int row = 1;
        for (int foodIndex = 0; foodIndex < curentOrder.getFoodsList().size(); foodIndex++) {

            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/Views/TablesViews/tabelDetailsItem.fxml"));
                AnchorPane anchorPane = loader.load();
                tableDetailsItemcontrolleur tableGridItemControlleur = loader.getController();
                // search for the price and title and change them here.
                tableGridItemControlleur.setDetailsItemdData(
                        String.valueOf(curentOrder.getFoodsList().get(foodIndex).getId_food()),
                        String.valueOf(curentOrder.getFoodsList().get(foodIndex).getId_food()),
                        String.valueOf(curentOrder.getFoodsList().get(foodIndex).getQuantity())
                );
                itemsGridView.add(anchorPane,column,row);
                row++;
                itemsGridView.setMargin(anchorPane,new Insets(5));

            }catch (IOException e){
                e.printStackTrace();
            }
            
        }

        // add the drinks.
        for (int drinksList = 0; drinksList < curentOrder.getDrinksList().size(); drinksList++) {

            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/Views/TablesViews/tabelDetailsItem.fxml"));
                AnchorPane anchorPane = loader.load();
                tableDetailsItemcontrolleur tableGridItemControlleur = loader.getController();
                // search for the price and title and change them here.
                tableGridItemControlleur.setDetailsItemdData(
                        String.valueOf(curentOrder.getDrinksList().get(drinksList).getId_drink()),
                        String.valueOf(curentOrder.getDrinksList().get(drinksList).getId_drink()),
                        String.valueOf(curentOrder.getDrinksList().get(drinksList).getQuantity())
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
        orderDatabaseConnector.insert(curentOrder);
        // add the food order.
        FoodOrderOperation foodDatabaseConnector = new FoodOrderOperation();
        for (int foodIndex = 0; foodIndex < curentOrder.getFoodsList().size(); foodIndex++) {
            foodDatabaseConnector.insert(curentOrder.getFoodsList().get(foodIndex));
        }
        // add the drink order.
        DrinkOrderOperation drinksDataBAseConnector = new DrinkOrderOperation();
        for (int foodIndex = 0; foodIndex < curentOrder.getDrinksList().size(); foodIndex++) {
            drinksDataBAseConnector.insert(curentOrder.getDrinksList().get(foodIndex));
        }
        // delete the order from teh view.
        TablesController.tabelsOrders.removeIf(orders -> orders.getId() == curentOrder.getId());
        // delet from the server orders list.
        for (int index = 0; index <  OrdersServer.ordersList.size() ; index++) {
            if (OrdersServer.ordersList.get(index).getId() == curentOrder.getId()){
                OrdersServer.orderSeenState.remove(index);
                OrdersServer.ordersList.remove(index);
            }

        }



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
        // refresh in the list.
        for (int tableIndex = 0; tableIndex < TablesController.tablesGainedMoney.size(); tableIndex++) {
            if (TablesController.tablesGainedMoney.get(tableIndex).getTableId() == tableNumber){
                TablesController.tablesGainedMoney.get(tableIndex).setGainedMoney(
                        TablesController.tablesGainedMoney.get(tableIndex).getGainedMoney() + money
                );
                // refresh in the database. // add anew column for the new operation.
                TablegainsOperationsBDD databaseConnector = new TablegainsOperationsBDD();
                databaseConnector.insertNewGain(TablesController.tablesGainedMoney.get(tableIndex),money);
            }
        }
        // refresh the total earnings.
        TablesController.totalEarnings = TablesController.totalEarnings + money;
        // notify the view of the changes so the total earnings is up to date.
        TablesController.totalEarningsrefreshed.setValue(! TablesController.totalEarningsrefreshed.getValue());
        // refresh the exact table earnings.



    }

}
