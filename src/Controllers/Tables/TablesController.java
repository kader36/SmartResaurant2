package Controllers.Tables;
import BddPackage.OrdersOperation;
import BddPackage.TabelsOperation;
import Models.Orders;
import Models.TableGainedMoney;
import Models.Tables;
import com.jfoenix.controls.JFXDrawer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
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
    @FXML
    private ScrollPane ScrollpaneTab;

    // fxml elements.
    @FXML
    private Text totalDayEarnings;
    @FXML
    private Label NumberOrders;

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
    static ArrayList<Orders> listeorder = new ArrayList<Orders>();
    // tables gaines.
    public ArrayList<TableGainedMoney> tablesGainedMoney = new ArrayList<>();


    // variables.
    public ArrayList<Orders> tabelsOrder = new ArrayList<Orders>();
    public static BooleanProperty drawerOpening = new SimpleBooleanProperty();
    public static BooleanProperty newOrder = new SimpleBooleanProperty();
    public static BooleanProperty orderConfirmed = new SimpleBooleanProperty();
    public static BooleanProperty totalEarningsrefreshed = new SimpleBooleanProperty();
    public static int selectedFoodIndex;
    public static Orders CurentOrder;
    int tableOrderColumn = 0;
    int tableOrderRow = 1;
    int tableOrderColumnRefreshed = 0;
    int tableOrderRowRefreshed = 1;
    static int numberOfORders = 0;
    private double totalEarnings = 0;
    int removingIndex = 2;





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lodeData();
        CalculeTotal();
        // the variable that we will listen to.
         drawerOpening.setValue(false); // drawer opening/closing.
        // newOrder.setValue(false); // new order coming from the tablet.

        // get the tables list from database.



        // listener for the confirmation of the orders so it can be removed from the view
        orderConfirmed.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                tablesGridPane.getChildren().clear();
                lodeData();
                CalculeTotal();
            }
        });


        CalculeTotal();
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
                drawerSlider.setVisible(true);
                if (drawerSlider.isShown()){
                    drawerSlider.close();
                    drawerSlider.setVisible(false);
                }else{
                    // set the data.
                    TableOrderDetailsControlleur.curentOrder = CurentOrder;
                    // prepare the drawer slider.
                    FXMLLoader loader = new FXMLLoader();
                    AnchorPane box ;
                    try {
                        // get the slider fxml file.
                        box = loader.load(getClass().getClassLoader().getResource("Views/TablesViews/tableDetails.fxml"));
                        box.setMaxHeight(Screen.getPrimary().getVisualBounds().getHeight());
                        box.setMaxWidth(Screen.getPrimary().getVisualBounds().getWidth());
                        // initialize the controller.
                        //TableOrderDetailsControlleur controlleur = loader.getController();
                        // set the data.
                        //TableOrderDetailsControlleur.curentOrder = tabelsOrder.get(selectedFoodIndex);
                        // show the drawer slider.
                        drawerSlider.setSidePane(box);
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
                CalculeTotal();
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


    }






    // a method to change teh color of the selected table based on teh cursor dragging.
     void lodeData(){

        OrdersOperation ordersOperation=new OrdersOperation();
        tabelsOrder=ordersOperation.getAll();
        listeorder=ordersOperation.getAll();

        for (int orderIndex = listeorder.size()-1; orderIndex >= 0 ; orderIndex--) {

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
                        String.valueOf(listeorder.get(orderIndex).getId_table()),
                        listeorder.get(orderIndex).getPrice(),
                        listeorder.get(orderIndex)
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



        }
         NumberOrders.setText(String.valueOf(listeorder.size()));
    }
    public  void CalculeTotal(){
        totalEarnings=0;
        TabelsOperation tablesDataBaseConnector = new TabelsOperation();
        ArrayList<Tables> tablesList = new ArrayList<>();
        tablesList = tablesDataBaseConnector.getAll();
        tablesGainedMoney.clear();
        for (int tabelIndex = 0; tabelIndex <tablesList.size() ; tabelIndex++) {
            TableGainedMoney tableGainedMoney=new TableGainedMoney();
            OrdersOperation tablegainsOperationsBDD=new OrdersOperation();
            tableGainedMoney=tablegainsOperationsBDD.getElement(tablesList.get(tabelIndex));
            tablesGainedMoney.add(new TableGainedMoney(
                    tableGainedMoney.getTableId(),
                    tableGainedMoney.getGainedMoney()
            ));
            totalEarnings=totalEarnings+tablesGainedMoney.get(tabelIndex).getGainedMoney();
        }
        totalDayEarnings.setText(String.valueOf(totalEarnings));
    }
    @FXML
    void UpdateTables(ActionEvent event){
        tablesGridPane.getChildren().clear();
        lodeData();
        CalculeTotal();
    }
    }




