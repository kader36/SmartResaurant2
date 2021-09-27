package Controllers.Tables;

import Models.CurrentUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private AnchorPane mainPane;
    @FXML
    private Label userName;
    @FXML
    private Label Type;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userName.setText(CurrentUser.getEmloyer_name());
        Type.setText(CurrentUser.getType());
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../Views/TablesViews/Dashboard.fxml"));
            BorderPane temp = loader.load();
            mainPane.getChildren().setAll(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    void showDashborde(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../Views/TablesViews/Dashboard.fxml"));
            BorderPane temp = loader.load();
            mainPane.getChildren().setAll(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
     void showTables(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../Views/TablesViews/9aimatDaf3.fxml"));
            BorderPane temp = loader.load();
            mainPane.getChildren().setAll(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    void showMoneyWithdrawl(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../Views/TablesViews/moneyWithdrawl.fxml"));
            BorderPane temp = loader.load();
            mainPane.getChildren().setAll(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    void showListeTable(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../Views/TablesViews/tablesList.fxml"));
            BorderPane temp = loader.load();
            mainPane.getChildren().setAll(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    void showBuy(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../Views/TablesViews/FoodBuy.fxml"));
            BorderPane temp = loader.load();
            mainPane.getChildren().setAll(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
