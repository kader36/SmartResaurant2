package Controllers.KitchenChef;

import Controllers.stroreKeeper.BillListController;
import Models.CurrentUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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
    @FXML
    private Label userName;
    @FXML
    private Label Type;
    /**
     * load screen controller
     * */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userName.setText(CurrentUser.getEmloyer_name());
        Type.setText(CurrentUser.getType());
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
    void loadAvailableItemScreen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/KitchenChef/availableFoods.fxml"));

            BorderPane temp = loader.load();

            mainPane.getChildren().setAll(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void loadFoodScreen(ActionEvent event) {
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
    void ProductCompositeScrenn(ActionEvent event){
            try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/KitchenChef/ProductComposite.fxml"));
        BorderPane temp = loader.load();
        mainPane.getChildren().setAll(temp);
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    @FXML
    void OrderScreen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/KitchenChef/kitchenOrdersView.fxml"));
            BorderPane temp = loader.load();
            mainPane.getChildren().setAll(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
