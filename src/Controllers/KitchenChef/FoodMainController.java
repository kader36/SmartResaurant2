package Controllers.KitchenChef;

import BddPackage.FoodOperation;
import Models.Food;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FoodMainController implements Initializable {
    @FXML
    private AnchorPane mainPain;
    @FXML
    private FlowPane flowPane;

    private ArrayList<Food> listFood;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FoodOperation foodOperation = new FoodOperation();
        listFood = foodOperation.getAll();
        flowPane.setHgap(10);
        flowPane.setVgap(10);
        ChargeFlowPane();
    }

    private void ChargeFlowPane() {
        VBox vBox ;
        for (Food food : listFood) {
            vBox = new VBox();
            ImageView imageView = new ImageView();
            File file = new File(food.getImage_path());
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
            imageView.setFitHeight(170);
            imageView.setFitWidth(200);
            Label foodName = new Label(food.getName());
            Label price = new Label("Price : " +food.getPrice());
            foodName.setPrefHeight(40);
            foodName.setPrefWidth(200);
            foodName.setStyle("-fx-alignment: center;");
            price.setPrefHeight(40);
            price.setPrefWidth(200);
            price.setStyle("-fx-alignment: center;");
            vBox.getChildren().addAll(imageView,foodName,price);
            flowPane.getChildren().add(vBox);
        }
    }


    public void Init(AnchorPane pane){
        this.mainPain = pane;
    }

    @FXML
    void addFoodPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/KitchenChef/InsertFoodView.fxml"));
            AnchorPane temp = loader.load();
            InsertFoodController controller = loader.getController();
            controller.Init(mainPain);
            mainPain.getChildren().setAll(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void homePage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/KitchenChef/FoodMainController.fxml"));
            AnchorPane temp = loader.load();
            FoodMainController controller = loader.getController();
            controller.Init(mainPain);
            mainPain.getChildren().setAll(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void CategoryFoodPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/KitchenChef/CategoryFoodMangment.fxml"));
            AnchorPane temp = loader.load();
            CategoryFoodController controller = loader.getController();
            controller.Init(mainPain);
            mainPain.getChildren().setAll(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
