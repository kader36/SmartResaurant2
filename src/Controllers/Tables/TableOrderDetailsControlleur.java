package Controllers.Tables;

import BddPackage.*;
import Models.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
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
            File file = new File(food.getImage_path());
            Image image = new Image(file.toURI().toString());




            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/Views/TablesViews/tabelDetailsItem.fxml"));
                AnchorPane anchorPane = loader.load();
                tableDetailsItemcontrolleur tableGridItemControlleur = loader.getController();
                // search for the price and title and change them here.
                tableGridItemControlleur.setDetailsItemdData(
                        String.valueOf(food.getName()),
                        String.valueOf(food.getPrice()),
                        String.valueOf(list.get(foodIndex).getQuantity()),
                        image

                );
                anchorPane.setEffect(new DropShadow(20, Color.BLACK));
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

        // delete the order from teh view.
        TablesController.tabelsOrders.removeIf(orders -> orders.getId() == curentOrder.getId());
        // delet from the server orders list.

        // refresh the view.
        TablesController.orderConfirmed.setValue(! TablesController.orderConfirmed.getValue());

        // refresh teh table gains.
        refrechTableGains(curentOrder.getId_table(),curentOrder.getPrice());

        // close the drawer.
        TablesController.drawerOpening.setValue(! TablesController.drawerOpening.getValue());


        TabelsOperation tabelsOperation=new TabelsOperation();
        Tables table=new Tables();
        table.setId(curentOrder.getId_table());
        tabelsOperation.ActivTabel(table);
        tabelsOperation.desActivOrderTabel(table);
        //refresh tapor tabels
        TabelActiveController.newOrder.setValue(!TabelActiveController.newOrder.getValue());
    }

    // method to cancle.
    void cancelView(){
        //just close the drawer.
        TablesController.drawerOpening.setValue(! TablesController.drawerOpening.getValue());
    }

    @FXML
    void print(ActionEvent event) {

        Connet connet = new Connet();
             Connection con = connet.connection();
             try {
                 String report = System.getProperty("user.dir") + "/Report/print.jrxml";
                 JasperDesign jasperDesign = JRXmlLoader.load(report);
                 String sqlCmd = "SELECT orders.ORDER_TIME,orders.ORDER_PRICE,food_order.ORDER_QUANTITY,food.FOOD_NAME,food.FOOD_PRICE,orders.ID_TABLE_ORDER ,settings.Name,settings.PhoneNambe1,settings.PhoneNambe2,settings.Adress\n" +
                         "FROM `orders`,food_order,food,settings\n" +
                         "WHERE orders.ID_ORDER=food_order.ID_ORDER and food_order.ID_FOOD=food.ID_FOOD and orders.ID_ORDER="+curentOrder.getId();
                 JRDesignQuery jrDesignQuery = new JRDesignQuery();
                 jrDesignQuery.setText(sqlCmd);
                 jasperDesign.setQuery(jrDesignQuery);
                 JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
                 Connection connection = null;

                 JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, con);
                 JasperViewer.viewReport(jasperPrint,false);
                // JasperPrintManager.printReport(jasperPrint,false);
                 //JasperExportManager.exportReportToPdfFile(jasperPrint,System.getProperty("user.dir") + "/SmartResaurant/simpel.pdf");
             } catch (JRException e) {
                 e.printStackTrace();
             }
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
