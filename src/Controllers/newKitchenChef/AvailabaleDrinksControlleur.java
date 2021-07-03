package Controllers.newKitchenChef;

import BddPackage.DrinksOperation;
import Models.Drinks;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AvailabaleDrinksControlleur implements Initializable {

    // fxml elements.
    @FXML
    private Button closeButton;

    @FXML
    private Button saveButton;

    @FXML
    private GridPane drinksItemsGrid;

    // variables.
    static ArrayList<Drinks> drinksList = new ArrayList<>();
    static ArrayList<Drinks> temporaryDrinksList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // set the buttons action methods.
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
        DrinksOperation databaseConnector = new DrinksOperation();
        drinksList = databaseConnector.getAll();
        temporaryDrinksList = databaseConnector.getAll();

        // set the gridItems.
        int tableOrderColumn = 0;
        int tableOrderRow = 1;

        for (int drinkIndex = 0; drinkIndex < drinksList.size(); drinkIndex++) {

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
                        drinksList.get(drinkIndex).getName(),
                        drinksList.get(drinkIndex).isAvailable(),
                        drinksList.get(drinkIndex).getImage_path()
                );
                // set the id;
                availableItemControlleur.setItemIdentifiers(
                        drinksList.get(drinkIndex).getId(),
                        true
                );

                drinksItemsGrid.add(anchorPane, tableOrderColumn++, tableOrderRow);
                //column = column + 1;
                // set the width.
                drinksItemsGrid.setMinWidth(Region.USE_COMPUTED_SIZE);
                drinksItemsGrid.setMaxWidth(Region.USE_PREF_SIZE);
                drinksItemsGrid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                // set the height.
                drinksItemsGrid.setMinHeight(Region.USE_COMPUTED_SIZE);
                drinksItemsGrid.setMaxHeight(Region.USE_PREF_SIZE);
                drinksItemsGrid.setPrefHeight(Region.USE_COMPUTED_SIZE);

                GridPane.setMargin(anchorPane,new Insets(20));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // method to save the changes.
    void saveChanges(){

        // save the changes to database.
        DrinksOperation databaseConnector =  new DrinksOperation();
        for (int drinkIndex = 0; drinkIndex < temporaryDrinksList.size(); drinkIndex++) {
            databaseConnector.changeAvailability(temporaryDrinksList.get(drinkIndex));
        }
        // synchronize the data.
        drinksList.clear();
        for (int drinkIndex = 0; drinkIndex < temporaryDrinksList.size(); drinkIndex++) {
            drinksList.add(new Drinks(
                    temporaryDrinksList.get(drinkIndex).getId(),
                    temporaryDrinksList.get(drinkIndex).getId_category(),
                    temporaryDrinksList.get(drinkIndex).getName(),
                    temporaryDrinksList.get(drinkIndex).getDescription(),
                    temporaryDrinksList.get(drinkIndex).getPrice(),
                    temporaryDrinksList.get(drinkIndex).getImage_path(),
                    temporaryDrinksList.get(drinkIndex).getRating(),
                    temporaryDrinksList.get(drinkIndex).isAvailable()
            ));
        }

    }

    // method to discard the changes.
    void cancelChanges(){

        // revert to the original data.
        drinksItemsGrid.getChildren().clear();
        temporaryDrinksList.clear();
        for (int drinkIndex = 0; drinkIndex < drinksList.size(); drinkIndex++) {
            temporaryDrinksList.add(new Drinks(
                    drinksList.get(drinkIndex).getId(),
                    drinksList.get(drinkIndex).getId_category(),
                    drinksList.get(drinkIndex).getName(),
                    drinksList.get(drinkIndex).getDescription(),
                    drinksList.get(drinkIndex).getPrice(),
                    drinksList.get(drinkIndex).getImage_path(),
                    drinksList.get(drinkIndex).getRating(),
                    drinksList.get(drinkIndex).isAvailable()
            ));
        }
        //temporaryFoodsList.addAll(foodsList);

        // refresh the view.
        // set the gridItems.
        int tableOrderColumn = 0;
        int tableOrderRow = 1;

        for (int drinkIndex = 0; drinkIndex < temporaryDrinksList.size(); drinkIndex++) {

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
                        temporaryDrinksList.get(drinkIndex).getName(),
                        temporaryDrinksList.get(drinkIndex).isAvailable(),
                        temporaryDrinksList.get(drinkIndex).getImage_path()
                );
                // set the id;
                availableItemControlleur.setItemIdentifiers(
                        temporaryDrinksList.get(drinkIndex).getId(),
                        true
                );

                drinksItemsGrid.add(anchorPane, tableOrderColumn++, tableOrderRow);
                //column = column + 1;
                // set the width.
                drinksItemsGrid.setMinWidth(Region.USE_COMPUTED_SIZE);
                drinksItemsGrid.setMaxWidth(Region.USE_PREF_SIZE);
                drinksItemsGrid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                // set the height.
                drinksItemsGrid.setMinHeight(Region.USE_COMPUTED_SIZE);
                drinksItemsGrid.setMaxHeight(Region.USE_PREF_SIZE);
                drinksItemsGrid.setPrefHeight(Region.USE_COMPUTED_SIZE);

                GridPane.setMargin(anchorPane,new Insets(20));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
