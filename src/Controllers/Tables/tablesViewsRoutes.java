package Controllers.Tables;

import Controllers.stroreKeeper.BillListController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class tablesViewsRoutes {


    // go to the first view of the order views (the view with the orders list).
    void loadOrdersScreen(ActionEvent event, AnchorPane mainPane) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/TablesViews/9aimatDaf3.fxml"));

            BorderPane temp = loader.load();
            BillListController billController = loader.getController();
            billController.Init(temp);
            mainPane.getChildren().setAll(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // go to the view of the tables managment.
    void loadTablesScreen(ActionEvent event, AnchorPane mainPane) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/TablesViews/tablesList.fxml"));
            BorderPane temp = loader.load();
            BillListController billController = loader.getController();
            billController.Init(temp);
            mainPane.getChildren().setAll(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // go to the money withdrawal management view.
    void loadMoneyWithdrawalScreen(ActionEvent event, AnchorPane mainPane) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/TablesViews/moneyWithdrawl.fxml"));

            BorderPane temp = loader.load();
            BillListController billController = loader.getController();
            billController.Init(temp);
            mainPane.getChildren().setAll(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
