package Controllers.Tables;

import BddPackage.FoodOperation;
import BddPackage.TabelsOperation;
import Models.Food;
import Models.Tables;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.SnapshotParameters;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TabelActiveController  implements Initializable {
    @FXML
    private GridPane goodItemsGrid;
    ArrayList<Tables> TabelsList = new ArrayList<>();
    ArrayList<Food> temporaryFoodsList = new ArrayList<>();
    public static BooleanProperty newOrder = new SimpleBooleanProperty();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loaddefaultDataFood();
        newOrder.addListener((observableValue, aBoolean, t1) -> {
            loaddefaultDataFood();
        });
    }
    void loaddefaultDataFood(){
        goodItemsGrid.getChildren().clear();
        // get food data from database.
        TabelsOperation tabelsOperation=new TabelsOperation();
        FoodOperation databaseConnector = new FoodOperation();
        TabelsList = tabelsOperation.getAllActive();
        temporaryFoodsList = databaseConnector.getAll();

        // set the gridItems.
        int tableOrderColumn = 0;
        int tableOrderRow = 1;
        String num;

        for (int foodIndex = 0; foodIndex < TabelsList.size(); foodIndex++) {
            num= String.valueOf(TabelsList.get(foodIndex).getNumber());


            if (tableOrderColumn == 8) {
                tableOrderColumn = 0;
                tableOrderRow = tableOrderRow + 1;
            }

            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/Views/TablesViews/TabelItem.fxml"));
                AnchorPane anchorPane2 = loader.load();
                TabelItemController tabelItemController=loader.getController();
                if (TabelsList.get(foodIndex).getOrderActive().equals("true"))
                tabelItemController.color("GREEN",num);
                else
                    tabelItemController.color("RED",num);

                goodItemsGrid.add(anchorPane2, tableOrderColumn++, tableOrderRow);
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
                clip.setWidth(135);
                clip.setHeight(180);

                clip.setArcHeight(25);
                clip.setArcWidth(25);
                clip.setStroke(Color.GREEN);
                goodItemsGrid.setClip(null);

                // snapshot the rounded image.
                SnapshotParameters parameters = new SnapshotParameters();
                parameters.setFill(Color.TRANSPARENT);


                // remove the rounding clip so that our effect can show through.



                GridPane.setMargin(anchorPane2, new Insets(13));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
