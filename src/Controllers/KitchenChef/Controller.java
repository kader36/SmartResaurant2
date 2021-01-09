package Controllers.KitchenChef;

import Controllers.stroreKeeper.BillListController;
import Controllers.stroreKeeper.DashboardController;
import Controllers.stroreKeeper.ProductController;
import Controllers.stroreKeeper.ProviderController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private AnchorPane mainPane;

    @FXML
    private ImageView logo,userVector;
    /**
     * load screen controller
     * */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*Image image = new Image("Images/Logo.png");
        logo.setImage(image);
        image = new Image("Images/Vector.png");
        userVector.setImage(image);*/

        //LaunchProviderScreen();
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/foods.fxml"));
            BorderPane temp = loader.load();
            FoodsController foodsController = loader.getController();
            foodsController.Init(temp);
            mainPane.getChildren().setAll(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
