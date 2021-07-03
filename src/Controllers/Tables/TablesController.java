package Controllers.Tables;
import BddPackage.TabelsOperation;
import Models.Orders;
import Models.TableGainedMoney;
import Models.Tables;
import com.jfoenix.controls.JFXDrawer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Screen;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;



public class TablesController implements Initializable {

    @FXML
    private BorderPane mainPane;

    // fxml elements.
    @FXML
    private Text totalDayEarnings;

    @FXML
    private ListView<?> tableEarningListView;

    @FXML
    private GridPane tablesGridPane;

    @FXML
    private GridPane tablesGainesGridPan;

    @FXML
    private JFXDrawer drawerSlider;

    @FXML
    private Button goToTableButton;

    @FXML
    private Button goToMoneyWithdrawalButton;

    @FXML
    private Button logOutButton;

    @FXML
    private AnchorPane lifetPane;


    // test data.
    // table order details.
    static ArrayList<Orders> tabelsOrders = new ArrayList<Orders>();
    // tables gaines.
    static ArrayList<TableGainedMoney> tablesGainedMoney = new ArrayList<>();


    // variables.
    public static BooleanProperty drawerOpening = new SimpleBooleanProperty();
    public static BooleanProperty newOrder = new SimpleBooleanProperty();
    public static BooleanProperty orderConfirmed = new SimpleBooleanProperty();
    public static BooleanProperty totalEarningsrefreshed = new SimpleBooleanProperty();
    public static int selectedFoodIndex;
    int tableOrderColumn = 0;
    int tableOrderRow = 1;
    int tableOrderColumnRefreshed = 0;
    int tableOrderRowRefreshed = 1;
    static int numberOfORders = 0;
    static double totalEarnings = 0;
    int removingIndex = 2;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // the variable that we will listen to.
         drawerOpening.setValue(false); // drawer opening/closing.
        // newOrder.setValue(false); // new order coming from the tablet.

        // get the tables list from database.
        TabelsOperation tablesDataBaseConnector = new TabelsOperation();
        ArrayList<Tables> tablesList = new ArrayList<>();
        tablesList = tablesDataBaseConnector.getAll();
        for (int tabelIndex = 0; tabelIndex <tablesList.size() ; tabelIndex++) {
            tablesGainedMoney.add(new TableGainedMoney(
                    tablesList.get(tabelIndex).getNumber(),
                    0
            ));
        }


        // add the listener to teh new orders.
        newOrder.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                //  get the added order. (add the first one that has the value false).

                for (int orderIndex = OrdersServer.ordersList.size()-1; orderIndex >= 0 ; orderIndex--) {
                    // check if the order has been added or not.
                    if (OrdersServer.orderSeenState.get(orderIndex) == false){

                        // add the order.
                        // first to the list of orders.
                        tabelsOrders.add(OrdersServer.ordersList.get(orderIndex));
                        // then to the interface.
                        // tow columns only.
                        if (tableOrderColumn == 2){
                            tableOrderColumn = 0;
                            tableOrderRow = tableOrderRow +1;
                        }

                        try {
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("/Views/TablesViews/tableGridViewItem.fxml"));
                            AnchorPane anchorPane = loader.load();
                            TableGridItemControlleur tableGridItemControlleur = loader.getController();
                            tableGridItemControlleur.setGridItemData(
                                    String.valueOf(OrdersServer.ordersList.get(orderIndex).getId_table()),
                                    OrdersServer.ordersList.get(orderIndex).getPrice(),
                                    orderIndex
                            );

                            tablesGridPane.add(anchorPane, tableOrderColumn++, tableOrderRow);
                            //column = column + 1;
                            // set the width.
                            tablesGridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
                            tablesGridPane.setMaxWidth(Region.USE_PREF_SIZE);
                            tablesGridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
                            // set the height.
                            tablesGridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
                            tablesGridPane.setMaxHeight(Region.USE_PREF_SIZE);
                            tablesGridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);


                            GridPane.setMargin(anchorPane,new Insets(10));

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        // change the state of the order.
                        OrdersServer.orderSeenState.set(orderIndex,true);

                    }
                }

                //  add the order to the page.
            }
        });

        // listener for the confirmation of the orders so it can be removed from the view
        orderConfirmed.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {


                 tableOrderColumnRefreshed = 0;
                 tableOrderRowRefreshed = 1;

                // refresh the view.
                tablesGridPane.getChildren().clear();
                for (int orderIndex = 0; orderIndex < tabelsOrders.size(); orderIndex++) {
                    if (tableOrderColumnRefreshed == 2){
                        tableOrderColumnRefreshed = 0;
                        tableOrderRowRefreshed = tableOrderRowRefreshed + 1;
                    }

                    try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/Views/TablesViews/tableGridViewItem.fxml"));
                        AnchorPane anchorPane = loader.load();
                        TableGridItemControlleur tableGridItemControlleur = loader.getController();
                        tableGridItemControlleur.setGridItemData(
                                String.valueOf(tabelsOrders.get(orderIndex).getId_table()),
                                tabelsOrders.get(orderIndex).getPrice(),
                                orderIndex
                        );

                        tablesGridPane.add(anchorPane, tableOrderColumnRefreshed++, tableOrderRowRefreshed);
                        //column = column + 1;
                        // set the width.
                        tablesGridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
                        tablesGridPane.setMaxWidth(Region.USE_PREF_SIZE);
                        tablesGridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
                        // set the height.
                        tablesGridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
                        tablesGridPane.setMaxHeight(Region.USE_PREF_SIZE);
                        tablesGridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);


                        GridPane.setMargin(anchorPane,new Insets(10));

                        // synchronizing the add order columns and rows.
                        if (removingIndex == 0){
                            tableOrderColumn --;
                            tableOrderRow = 0;
                        }else{
                            tableOrderRow --;
                            removingIndex --;
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });



        // set th tables gains grid view.
        int tableGainescolumn = 1;
        int tableGainesrow = 1;
        for (int tablesGainesIndex = 0; tablesGainesIndex < tablesGainedMoney.size(); tablesGainesIndex++) {


            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/Views/TablesViews/tableGainesGridItem.fxml"));
                AnchorPane anchorPane = loader.load();
                TableGainesGridItemControlleur tableGridItemControlleur = loader.getController();
                tableGridItemControlleur.setTableGainItemData(
                        tablesGainedMoney.get(tablesGainesIndex).getTableId(),
                        tablesGainedMoney.get(tablesGainesIndex).getGainedMoney()
                );

                tablesGainesGridPan.add(anchorPane,tableGainescolumn,tableGainesrow);
                tableGainesrow++;

                tablesGainesGridPan.setMargin(anchorPane,new Insets(5));

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        // set the listener to open the drawer.
        drawerOpening.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (drawerSlider.isShown()){
                    drawerSlider.close();
                }else{
                    // set the data.
                    TableOrderDetailsControlleur.curentOrder = tabelsOrders.get(selectedFoodIndex);
                    // prepare the drawer slider.
                    FXMLLoader loader = new FXMLLoader();
                    AnchorPane box ;
                    try {
                        // get the slider fxml file.
                        box = loader.load(getClass().getClassLoader().getResource("Views/TablesViews/tableDetails.fxml"));
                        box.setMinHeight(Screen.getPrimary().getVisualBounds().getHeight());
                        //box.setMaxHeight(Screen.getPrimary().getVisualBounds().getHeight());
                        // initialize the controller.
                        //TableOrderDetailsControlleur controlleur = loader.getController();
                        // set the data.
                        TableOrderDetailsControlleur.curentOrder = tabelsOrders.get(selectedFoodIndex);
                        // show the drawer slider.
                        drawerSlider.setSidePane(box);
                        drawerSlider.setMaxHeight(box.getMaxHeight()-10);
                        drawerSlider.open();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        // add teh listener of the total earnings .
        totalEarningsrefreshed.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                // refresh teh total.
                totalDayEarnings.setText(String.valueOf(totalEarnings));
                // refresh the exact table.
                // set th tables gains grid view.
                int tableGainescolumn = 1;
                int tableGainesrow = 1;
                for (int tablesGainesIndex = 0; tablesGainesIndex < tablesGainedMoney.size(); tablesGainesIndex++) {


                    try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/Views/TablesViews/tableGainesGridItem.fxml"));
                        AnchorPane anchorPane = loader.load();
                        TableGainesGridItemControlleur tableGridItemControlleur = loader.getController();
                        tableGridItemControlleur.setTableGainItemData(
                                tablesGainedMoney.get(tablesGainesIndex).getTableId(),
                                tablesGainedMoney.get(tablesGainesIndex).getGainedMoney()
                        );

                        tablesGainesGridPan.add(anchorPane, tableGainescolumn, tableGainesrow);
                        tableGainesrow++;

                        tablesGainesGridPan.setMargin(anchorPane, new Insets(5));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        /*logOutButton.setOnAction(e ->{

            OrdersServer.addOrder("3@3500@1=2,2=1@+5=6,3=5");
        });*/

    }


    // a method to go to other views.
    void goTo(){

    }




    // a method to change teh color of the selected table based on teh cursor dragging.
    void changeColorOfHover(){
        // maybe just do it in CSS it's better just a :hover code
    }


}
