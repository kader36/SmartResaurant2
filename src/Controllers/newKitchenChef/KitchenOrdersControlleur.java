package Controllers.newKitchenChef;

import BddPackage.DrinksOperation;
import BddPackage.FoodOperation;
import Controllers.Tables.OrdersServer;
import Models.Drinks;
import Models.Food;
import Models.Orders;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.ResourceBundle;

public class KitchenOrdersControlleur implements Initializable {

    // fxml elements.
    @FXML
    private GridPane orderGridView;

    @FXML
    private Button confirmOrderButton;

    @FXML
    private Label orerTotalPriceLabel;

    @FXML
    private Label tableNumberLabel;

    // variables.
    public static BooleanProperty newOrder = new SimpleBooleanProperty();
    //public static Queue<Orders> ordersQueue = new PriorityQueue<Orders>();
    public static Queue<String> ordersIdsQueue = new PriorityQueue<String>();
    public static Orders curentOrder;
    public static String curentOrderId = "";
    int tableOrderColumn = 0;
    int tableOrderRow = 1;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // set the button action methods.
        confirmOrderButton.setOnAction(actionEvent -> {
            try {
                confirmOrder();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // set teh new order refresh listener.
        newOrder.addListener((observableValue, aBoolean, t1) -> {
            try {
                // only if no order is shown in the interface
                if (orderGridView.getChildren().size() == 0){
                    loadData();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // set the current order data.
        try {
            loadData();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // method to load the current order data.
    void loadData() throws IOException {

        // only if the queue of orders is not mepty.
        if (ordersIdsQueue.isEmpty() == false){
            //orderGridView.getChildren().clear();
            // load the current order.
            curentOrderId = ordersIdsQueue.poll();
            for (int orderIndex = 0; orderIndex < OrdersServer.ordersList.size(); orderIndex++) {
                if (String.valueOf(OrdersServer.ordersList.get(orderIndex).getId()).equals(curentOrderId)){
                    curentOrder = OrdersServer.ordersList.get(orderIndex);
                }
            }

            // reset the column and row counters.
            tableOrderColumn = 0;
            tableOrderRow = 1;

            tableNumberLabel.setText(String.valueOf(curentOrder.getId_table()));
            orerTotalPriceLabel.setText(String.valueOf(curentOrder.getPrice()));

            // set the grid items.
            // add the food items.
            FoodOperation foodsDataBaseConnector = new FoodOperation();
            for (int foodItemsIndex = 0; foodItemsIndex < curentOrder.getFoodsList().size(); foodItemsIndex++) {
                if (tableOrderColumn == 5){
                    tableOrderColumn = 0;
                    tableOrderRow = tableOrderRow +1;
                }
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/Views/KitchenChef/kitchenOrderItem.fxml"));



                AnchorPane anchorPane = loader.load();
                KitchenOrderItemControlleur tableGridItemControlleur = loader.getController();

                // get the food data from database.
                Food curentFood = foodsDataBaseConnector.getFoodByID(
                        curentOrder.getFoodsList().get(foodItemsIndex).getId_food()
                );
                // create the image.
                File file = new File(curentFood.getImage_path());
                Image image = new Image(curentFood.getImage_path());

                tableGridItemControlleur.loadData(
                        image,
                        curentFood.getName(),
                        String.valueOf(curentOrder.getFoodsList().get(foodItemsIndex).getQuantity())

                );

                // add to the grid view.
                orderGridView.add(anchorPane, tableOrderColumn++, tableOrderRow);
                //column = column + 1;
                // set the width.
                orderGridView.setMinWidth(Region.USE_COMPUTED_SIZE);
                orderGridView.setMaxWidth(Region.USE_PREF_SIZE);
                orderGridView.setPrefWidth(160);
                // set the height.
                orderGridView.setMinHeight(180);
                orderGridView.setMaxHeight(Region.USE_PREF_SIZE);
                orderGridView.setPrefHeight(Region.USE_COMPUTED_SIZE);

                GridPane.setMargin(anchorPane,new Insets(10));

            }
            // add the drinks items.
            DrinksOperation drinksDataBaseConnector = new DrinksOperation();
            for (int drinkItemsIndex = 0; drinkItemsIndex < curentOrder.getDrinksList().size(); drinkItemsIndex++) {
                if (tableOrderColumn == 2){
                    tableOrderColumn = 0;
                    tableOrderRow = tableOrderRow +1;
                }

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/Views/KitchenChef/kitchenOrderItem.fxml"));
                AnchorPane anchorPane = loader.load();
                KitchenOrderItemControlleur tableGridItemControlleur = loader.getController();
                // get the drink data from database.
                Drinks curentDrink = drinksDataBaseConnector.getDrinkByID(
                        curentOrder.getFoodsList().get(drinkItemsIndex).getId_food()
                );
                // create the image.
                File file = new File(curentDrink.getImage_path());
                Image image = new Image(file.toURI().toString());
                tableGridItemControlleur.loadData(
                        image,
                        curentDrink.getName(),
                        String.valueOf(curentOrder.getFoodsList().get(drinkItemsIndex).getQuantity())

                );

                // add to the grid view.
                orderGridView.add(anchorPane, tableOrderColumn++, tableOrderRow);
                //column = column + 1;
                // set the width.
                orderGridView.setMinWidth(Region.USE_COMPUTED_SIZE);
                orderGridView.setMaxWidth(Region.USE_PREF_SIZE);
                orderGridView.setPrefWidth(Region.USE_COMPUTED_SIZE);
                // set the height.
                orderGridView.setMinHeight(Region.USE_COMPUTED_SIZE);
                orderGridView.setMaxHeight(Region.USE_PREF_SIZE);
                orderGridView.setPrefHeight(Region.USE_COMPUTED_SIZE);

                GridPane.setMargin(anchorPane,new Insets(10));
            }
        }

    }


    // method to confirm the order.
    void confirmOrder() throws IOException {
        // do what when the order is confirmed ?????
        // load the next order.
        if (ordersIdsQueue.isEmpty() == false){
            loadData();
        }
        if (ordersIdsQueue.isEmpty() ==  true){
            // reset the view
            orderGridView.getChildren().clear();
            tableNumberLabel.setText("");
            orerTotalPriceLabel.setText("");
        }
    }
}
