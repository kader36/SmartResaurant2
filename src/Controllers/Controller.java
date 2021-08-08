package Controllers;

import Controllers.KitchenChef.FoodsController;
import Controllers.Tables.OrdersServer;
import Controllers.stroreKeeper.BillListController;
import Controllers.stroreKeeper.DashboardController;
import Controllers.stroreKeeper.ProductController;
import Controllers.stroreKeeper.ProviderController;
import Models.CurrentUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private AnchorPane mainPane;
    @FXML
    private Label employ;
    @FXML
    private Label Type;

    @FXML
    private ImageView logo,userVector;
/**
 * load screen controller
 * */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       this.Type.setText(CurrentUser.getType());
       this.employ.setText(CurrentUser.getEmloyer_name());
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Dashboard.fxml"));

            BorderPane temp = loader.load();
            DashboardController dashboardController = loader.getController();
            dashboardController.Init(temp);
            mainPane.getChildren().setAll(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void LaunchProviderScreen(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/AddFactoryBuy.fxml"));
            BorderPane temp = loader.load();
            mainPane.getChildren().setAll(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void goToFirst(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/FirstScreen.fxml"));
            AnchorPane temp = loader.load();
            mainPane.getChildren().setAll(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void loadBillScreen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Factories.fxml"));

            BorderPane temp = loader.load();
            BillListController billController = loader.getController();
            billController.Init(temp);
            mainPane.getChildren().setAll(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void loadDashboardScreen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Dashboard.fxml"));

            BorderPane temp = loader.load();
            DashboardController dashboardController = loader.getController();
            dashboardController.Init(temp);
            mainPane.getChildren().setAll(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void loadProductScreen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/ProductsScreen.fxml"));
            BorderPane temp = loader.load();
            ProductController productController = loader.getController();
            productController.Init(temp);
            mainPane.getChildren().setAll(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void loadProviderScreen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/ProviderScreen.fxml"));
            BorderPane temp = loader.load();
            ProviderController providerController = loader.getController();
            providerController.Init(temp);
            mainPane.getChildren().setAll(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void loadChefScreen(ActionEvent event) throws IOException {
        Thread serverThread = new Thread(() -> {
            OrdersServer.startListeningToOrders();
        });
        serverThread.start();
        Stage primaryStage =new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../Views/MainScreenKitchenChef.fxml"));
        primaryStage.setTitle(" ادارة الطبخ");
        root.getStylesheets().add("Style.css");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
    @FXML
    void loadFoodScreenx(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/foods.fxml"));
            BorderPane temp = loader.load();
            FoodsController foodsController = loader.getController();
            foodsController.Init(temp);
            mainPane.getChildren().setAll(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void loadUsersScreen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/UsersScreen.fxml"));
            BorderPane temp = loader.load();
            mainPane.getChildren().setAll(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void loadSettings(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/settings.fxml"));
            BorderPane temp = loader.load();
            mainPane.getChildren().setAll(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void loadAccountantScreen(ActionEvent event) throws IOException {
        Stage primaryStage =new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../Views/MainScreenAccountant.fxml"));
        primaryStage.setTitle(" ادارة المحاسبة");
        root.getStylesheets().add("Style.css");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }




}
