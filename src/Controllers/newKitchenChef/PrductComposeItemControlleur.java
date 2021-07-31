package Controllers.newKitchenChef;

import Controllers.KitchenChef.AddFoodController;
import Models.IngredientsFood;
import com.jfoenix.controls.JFXListView;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PrductComposeItemControlleur implements Initializable {
    @FXML
    private JFXListView<String> listViewProductCompose;
    @FXML
    private Button addProducte;
    @FXML
    private Button deleteButotn;
    @FXML
    private TextField inpQountity;
    public static BooleanProperty addProduct = new SimpleBooleanProperty();
    public static ArrayList<String>  listProduct;
    private ObservableList<String> dataTableView = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dataTableView.setAll(listProduct);
        this.listViewProductCompose.setItems(dataTableView);
        addProducte.setOnAction(actionEvent -> {
            addProducte();
        });
    }
    void addProducte(){
        ObservableList<IngredientsFood> ProductListe = FXCollections.observableArrayList();
        String product = String.valueOf(listViewProductCompose.getSelectionModel().getSelectedItems());
        IngredientsFood ingredientsFood = new IngredientsFood();
        product = product.replace("[", "");
        product = product.replace("]", "");
        ingredientsFood.setProduct_name(product);
        ingredientsFood.setUnity("G");
        ingredientsFood.setQuantity(Integer.valueOf(inpQountity.getText()));
        ingredientsFood.setType(1);
        AddFoodController.listProducts.add(ingredientsFood);
        inpQountity.setText("");
        addProduct.setValue(!addProduct.getValue());

    }

}
