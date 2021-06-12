package Controllers.Tables;
import Models.Orders;
import Models.TableGainedMoney;
import com.jfoenix.controls.JFXDrawer;
import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class TablesDetailsController implements Initializable {

    // test data.
    // table order details.
    ArrayList<Orders> tabelsOrders = new ArrayList<Orders>();
    // tables gaines.
    ArrayList<TableGainedMoney> tablesGainedMoney = new ArrayList<>();



    // variables.
    double totalGain = 5233;
    public static BooleanProperty drawerOpening = new SimpleBooleanProperty();

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //totalDayEarnings.setText(String.valueOf(totalGain));

        for (int index = 0; index < 20; index++) {
            tabelsOrders.add(new Orders(
               1,index, null,index*1000,null,null
            ));
            tablesGainedMoney.add(new TableGainedMoney(
               index,index*155
            ));

        }

        // set the tables grid view.(get all the tables with there orders)
        int column = 0;
        int row = 1;
        for (int tabelIndex = 0; tabelIndex < tabelsOrders.size(); tabelIndex++) {


            // tow columns only.
            if (column  == 2){
                column = 0;
                row = row +1;
            }

            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/Views/TablesViews/tableGridViewItem.fxml"));
                AnchorPane anchorPane = loader.load();
                TableGridItemControlleur tableGridItemControlleur = loader.getController();
                tableGridItemControlleur.setGridItemData(
                        String.valueOf(tabelsOrders.get(tabelIndex).getId_table()),
                        tabelsOrders.get(tabelIndex).getPrice()
                );

                tablesGridPane.add(anchorPane,column++,row);
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

        // set th tables gains grid view.
        int tableGainescolumn = 1;
        int tableGainesrow = 1;
        for (int tablesGainesIndex = 0; tablesGainesIndex < 20; tablesGainesIndex++) {


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
                // set the width.
                tablesGainesGridPan.setMinWidth(Region.USE_COMPUTED_SIZE);
                tablesGainesGridPan.setMaxWidth(Region.USE_PREF_SIZE);
                tablesGainesGridPan.setPrefWidth(Region.USE_COMPUTED_SIZE);
                // set the height.
                tablesGainesGridPan.setMinHeight(Region.USE_COMPUTED_SIZE);
                tablesGainesGridPan.setMaxHeight(Region.USE_PREF_SIZE);
                tablesGainesGridPan.setPrefHeight(Region.USE_COMPUTED_SIZE);


                //GridPane.setMargin(anchorPane,new Insets(10)).

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        // set the listner to open the drawer.
        drawerOpening.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (drawerOpening.get() == false){
                    drawerSlider.close();
                    //System.out.println("==========================False=========================");
                }else{
                    FXMLLoader loader = new FXMLLoader();
                    AnchorPane box = null;
                    try {
                        box = loader.load(getClass().getResource("Views/TablesViews/tableDetails.fxml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    TableOrderDetailsControlleur controlleur = loader.getController();
                    // set the data.
                    // show the drawer slider.

                    drawerSlider.setSidePane(box);
                    drawerSlider.open();
                    //System.out.println("==========================True=========================");
                }
            }
        });

    }


    // method to show the table order details.

    // a method to close the drarwer slider.


    // a method to pay the table order.


    // a method to go to other views.


    // a method to change teh color of the selected table based on teh cursor dragging.


}
