package Controllers.newKitchenChef;

import BddPackage.FoodOperation;
import Models.Food;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AvailabaleFoodsControlleur implements Initializable {

    // fxml elements.
    @FXML
    private Button closeButton;

    @FXML
    private Button saveButton;

    @FXML
    private GridPane goodItemsGrid;

    //varaiables.
    static ArrayList<Food> foodsList = new ArrayList<>();
    static ArrayList<Food> temporaryFoodsList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // set the button action methods.
        saveButton.setOnAction(actionEvent -> {
            saveChanges();
            PDFReportGenerators.showNotification(
                    " تحديث المعلومات",
                    "تم تحديث المعلومات بنجاح",
                    1,
                    false);
        });

        closeButton.setOnAction(actionEvent -> {
            cancelChanges();
        });

        // load the data from database.
        loadData();

    }

    // method to load the data .
    void loadData(){
        // get food data from database.
        FoodOperation databaseConnector = new FoodOperation();
        foodsList = databaseConnector.getAll();
        temporaryFoodsList = databaseConnector.getAll();

        // set the gridItems.
        int tableOrderColumn = 0;
        int tableOrderRow = 1;

        for (int foodIndex = 0; foodIndex < foodsList.size(); foodIndex++) {

            if (tableOrderColumn == 4){
                tableOrderColumn = 0;
                tableOrderRow = tableOrderRow +1;
            }

            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/Views/KitchenChef/availabaleItem.fxml"));
                AnchorPane anchorPane = loader.load();
                AvailabaleItemControlleur availableItemControlleur = loader.getController();
                availableItemControlleur.setItemData(
                        foodsList.get(foodIndex).getName(),
                        foodsList.get(foodIndex).isAvailabale(),
                        foodsList.get(foodIndex).getImage_path()
                );
                // set the id;
                availableItemControlleur.setItemIdentifiers(
                        foodsList.get(foodIndex).getId(),
                        true
                );

                goodItemsGrid.add(anchorPane, tableOrderColumn++, tableOrderRow);
                //column = column + 1;
                // set the width.
               goodItemsGrid.setMinWidth(Region.USE_COMPUTED_SIZE);
                goodItemsGrid.setMaxWidth(Region.USE_PREF_SIZE);
                goodItemsGrid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                // set the height.
                goodItemsGrid.setMinHeight(Region.USE_COMPUTED_SIZE);
                goodItemsGrid.setMaxHeight(Region.USE_PREF_SIZE);
                goodItemsGrid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                Rectangle clip = new Rectangle();
                clip.setWidth(155);
                clip.setHeight(190);

                clip.setArcHeight(25);
                clip.setArcWidth(25);
                clip.setStroke(Color.BLACK);
                goodItemsGrid.setClip(clip);

                // snapshot the rounded image.
                SnapshotParameters parameters = new SnapshotParameters();
                parameters.setFill(Color.TRANSPARENT);


                // remove the rounding clip so that our effect can show through.
                goodItemsGrid.setClip(null);
                goodItemsGrid.setEffect(new DropShadow(1, Color.BLACK));


                GridPane.setMargin(anchorPane,new Insets(20));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    // method to save the changes.
    void saveChanges(){

        // save the changes to database.
        FoodOperation databaseConnector =  new FoodOperation();
        for (int foodIndex = 0; foodIndex < temporaryFoodsList.size(); foodIndex++) {
            databaseConnector.changeAvailability(temporaryFoodsList.get(foodIndex));
        }
        // synchronize the data.
        foodsList.clear();
        for (int foodIndex = 0; foodIndex < temporaryFoodsList.size(); foodIndex++) {
            foodsList.add(new Food(
                    temporaryFoodsList.get(foodIndex).getId(),
                    temporaryFoodsList.get(foodIndex).getId_category(),
                    temporaryFoodsList.get(foodIndex).getName(),
                    temporaryFoodsList.get(foodIndex).getDescription(),
                    temporaryFoodsList.get(foodIndex).getPrice(),
                    temporaryFoodsList.get(foodIndex).getImage_path(),
                    temporaryFoodsList.get(foodIndex).getRating(),
                    temporaryFoodsList.get(foodIndex).isAvailabale()
            ));
        }
        //foodsList.addAll(temporaryFoodsList);
    }



    // method to discard the changes.
    void cancelChanges(){
        // revert to the original data.
        goodItemsGrid.getChildren().clear();
        temporaryFoodsList.clear();
        for (int foodIndex = 0; foodIndex < foodsList.size(); foodIndex++) {
            temporaryFoodsList.add(new Food(
                    foodsList.get(foodIndex).getId(),
                    foodsList.get(foodIndex).getId_category(),
                    foodsList.get(foodIndex).getName(),
                    foodsList.get(foodIndex).getDescription(),
                    foodsList.get(foodIndex).getPrice(),
                    foodsList.get(foodIndex).getImage_path(),
                    foodsList.get(foodIndex).getRating(),
                    foodsList.get(foodIndex).isAvailabale()
            ));
        }
        //temporaryFoodsList.addAll(foodsList);

        // refresh the view.
        // set the gridItems.
        int tableOrderColumn = 0;
        int tableOrderRow = 1;

        for (int foodIndex = 0; foodIndex < temporaryFoodsList.size(); foodIndex++) {

            if (tableOrderColumn == 4){
                tableOrderColumn = 0;
                tableOrderRow = tableOrderRow +1;
            }

            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/Views/KitchenChef/availabaleItem.fxml"));
                AnchorPane anchorPane = loader.load();
                AvailabaleItemControlleur availableItemControlleur = loader.getController();
                availableItemControlleur.setItemData(
                        temporaryFoodsList.get(foodIndex).getName(),
                        temporaryFoodsList.get(foodIndex).isAvailabale(),
                        temporaryFoodsList.get(foodIndex).getImage_path()
                );
                // set the id;
                availableItemControlleur.setItemIdentifiers(
                        temporaryFoodsList.get(foodIndex).getId(),
                        true
                );

                goodItemsGrid.add(anchorPane, tableOrderColumn++, tableOrderRow);
                //column = column + 1;
                // set the width.
                goodItemsGrid.setMinWidth(Region.USE_COMPUTED_SIZE);
                goodItemsGrid.setMaxWidth(Region.USE_PREF_SIZE);
                goodItemsGrid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                // set the height.
                goodItemsGrid.setMinHeight(Region.USE_COMPUTED_SIZE);
                goodItemsGrid.setMaxHeight(Region.USE_PREF_SIZE);
                goodItemsGrid.setPrefHeight(Region.USE_COMPUTED_SIZE);

                GridPane.setMargin(anchorPane,new Insets(20));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
