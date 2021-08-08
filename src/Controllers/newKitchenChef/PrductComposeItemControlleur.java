package Controllers.newKitchenChef;

import BddPackage.ProductCompositeOperation;
import Controllers.KitchenChef.AddFoodController;
import Controllers.KitchenChef.EditFoodController;
import Models.IngredientsFood;
import Models.ProductComposite;
import com.jfoenix.controls.JFXListView;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
        if (listViewProductCompose.getSelectionModel().getSelectedItem() == null) {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("تحذير ");
            alertWarning.setContentText("يرجى اختيار نوع المنتوج");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("حسنا");
            alertWarning.showAndWait();
            return;
        }else {
        if (!inpQountity.getText().matches("[0-9]+")||inpQountity.getText().isEmpty()){
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("تحذير ");
            alertWarning.setContentText("يرجى ملئ خانة الكمية برقم صحيح");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("حسنا");
            alertWarning.showAndWait();
        }else {
            ObservableList<IngredientsFood> ProductListe = FXCollections.observableArrayList();
            String product = String.valueOf(listViewProductCompose.getSelectionModel().getSelectedItems());
            IngredientsFood ingredientsFood = new IngredientsFood();
            product = product.replace("[", "");
            product = product.replace("]", "");
            ingredientsFood.setProduct_name(product);
            ingredientsFood.setUnity(getUnitProductComposeByCobo(product));
            ingredientsFood.setQuantity(Integer.valueOf(inpQountity.getText()));
            ingredientsFood.setType(1);
            AddFoodController.listProducts.add(ingredientsFood);
            EditFoodController.list_ingredientsFoods.add(ingredientsFood);
            inpQountity.setText("");
            addProduct.setValue(!addProduct.getValue());
        }}
    }
    String getUnitProductComposeByCobo(String comboChose) {
        ProductCompositeOperation productCompositeOperation=new ProductCompositeOperation();
        ProductComposite productComposite=productCompositeOperation.GetProductComposite(comboChose);
        return productComposite.getUnity_Food();
    }

}
