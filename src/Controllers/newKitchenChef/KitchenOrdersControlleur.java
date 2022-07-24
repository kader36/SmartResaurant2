package Controllers.newKitchenChef;

import BddPackage.FoodOperation;
import BddPackage.TabelsOperation;
import Controllers.Tables.TabelActiveController;
import Models.ClientSocket;
import Models.Food;
import Models.Orders;
import Models.Tables;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.Scanner;

public class KitchenOrdersControlleur implements Initializable {

    // fxml elements.
    @FXML
    private GridPane orderGridView;
    @FXML
    private HBox s;

    @FXML
    private Button confirmOrderButton;

    @FXML
    private Label orerTotalPriceLabel;

    @FXML
    private Label tableNumberLabel;

    // variables.
    public static BooleanProperty newOrder = new SimpleBooleanProperty();
    public  BooleanProperty confirmOrder = new SimpleBooleanProperty();
    public static Queue<String> ordersIdsQueue = new PriorityQueue<String>();
    public Orders curentOrder;
    public static String curentOrderId = "";
    int tableOrderColumn = 0;
    int tableOrderRow = 1;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // set the button action methods.
        confirmOrderButton.setOnMouseClicked(actionEvent -> {

            confirmOrderButton.setDisable(true);
            try {
                if (ordersIdsQueue.isEmpty() == false)
                confirmOrder();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        s.setOnMouseClicked(actionEvent -> {

            confirmOrderButton.setDisable(false);
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
            // load the current order.
            curentOrderId = ordersIdsQueue.element();
            for (int orderIndex = 0; orderIndex < ClientSocket.ordersList.size(); orderIndex++) {
                if (String.valueOf(ClientSocket.ordersList.get(orderIndex).getId()).equals(curentOrderId)){
                    curentOrder = ClientSocket.ordersList.get(orderIndex);
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
                Image image = new Image(file.toURI().toString());

                tableGridItemControlleur.loadData(
                        image,
                        curentFood.getName(),
                        String.valueOf(curentOrder.getFoodsList().get(foodItemsIndex).getQuantity())

                );

                // add to the grid view.
                anchorPane.setEffect(new DropShadow(10, Color.BLACK));
                Rectangle clip = new Rectangle();
                clip.setWidth(160);
                clip.setHeight(180);

                clip.setArcHeight(10);
                clip.setArcWidth(10);
                clip.setStroke(Color.BLACK);
                 anchorPane.setClip(clip);
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
                orderGridView.setEffect(new DropShadow(1, Color.BLACK));

                // snapshot the rounded image.
                SnapshotParameters parameters = new SnapshotParameters();
                parameters.setFill(Color.TRANSPARENT);


                // remove the rounding clip so that our effect can show through.

                GridPane.setMargin(anchorPane,new Insets(10));


            }

    }

    }


    // method to confirm the order.
    void confirmOrder() throws IOException {
        Tables tables = new Tables();
        tables.setId(curentOrder.getId_table());
        TabelsOperation tabelsOperation = new TabelsOperation();
        tabelsOperation.ActivOrderTabel(tables);

        orderGridView.getChildren().clear();
        curentOrderId = ordersIdsQueue.element();
        // do what when the order is confirmed ?????
        // load the next order.
        if (ordersIdsQueue.isEmpty() == false) {
            ordersIdsQueue.poll();
            loadData();
            if (ordersIdsQueue.isEmpty()) {
                // reset the view
                orerTotalPriceLabel.setText("");
                tableNumberLabel.setText("");
            }
        }
        TabelActiveController.newOrder.setValue(!TabelActiveController.newOrder.getValue());

        Socket serverSocket;

        try {
            String port=readfile(new File(System.getProperty("user.dir") + "/BD/port.txt"));
            serverSocket = new Socket("127.0.0.1", Integer.parseInt(port));
            System.out.println("waiting for messages 5000");

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(serverSocket.getOutputStream()));
            DataInputStream in = new DataInputStream(new BufferedInputStream(serverSocket.getInputStream()));

            out.write("confirmOrder");
            out.newLine();
            out.flush();

        } catch (Exception e) {

        }
    }
    private static String readfile(File fileReader){
        String txt="";
        try { Scanner scanner = new Scanner( fileReader);
            String text = scanner.useDelimiter(";").next();
            txt=text;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return txt;
    }
}
