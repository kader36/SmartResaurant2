package Controllers.KitchenChef;

import BddPackage.FoodCategoryOperation;
import Controllers.ValidateController;
import Models.FoodCategory;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CategoryFoodController implements Initializable {

    @FXML
    private AnchorPane mainPane;
    @FXML
    private TableView<FoodCategory> tableCatrgoryFood;

    @FXML
    private TableColumn<FoodCategory, String> col_category_name;

    @FXML
    private TableColumn<FoodCategory, Integer> col_idcategory;

    @FXML
    private JFXButton insertbutton;

    @FXML
    private JFXButton updatebutton;

    @FXML
    private JFXButton deletebutton;

    @FXML
    private JFXTextField txt_food_name;

    @FXML
    private JFXButton updatecategoryButton;

    private ArrayList<FoodCategory> listCategory;
    private ObservableList<FoodCategory> data_Table= FXCollections.observableArrayList();
    private FoodCategory foodCategory;
    private FoodCategoryOperation foodCategoryOperation;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ValidateController validateController = new ValidateController();
        validateController.inputTextValueType(txt_food_name);
        foodCategoryOperation = new FoodCategoryOperation();
        listCategory = foodCategoryOperation.getAll();
        data_Table.setAll(listCategory);
        col_idcategory.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_category_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableCatrgoryFood.setItems(data_Table);
        updatecategoryButton.setDisable(true);
    }
    public void Init(AnchorPane mainPain) {
        this.mainPane = mainPain;
    }

    @FXML
    void deleteCategoryFood(ActionEvent event) {
       FoodCategory foodCategory = tableCatrgoryFood.getSelectionModel().getSelectedItem();
        if(foodCategory != null){
            foodCategoryOperation.delete(foodCategory);
            refresh();
        }
    }

    @FXML
    void insertCategoryFood(ActionEvent event) {
        if (!txt_food_name.equals("")){
            FoodCategory foodCategory = new FoodCategory();
            foodCategory.setName(txt_food_name.getText());
            foodCategoryOperation.insert(foodCategory);
            refresh();
        }
    }

    @FXML
    void updateAction(ActionEvent event) {
         foodCategory = tableCatrgoryFood.getSelectionModel().getSelectedItem();
        if(foodCategory != null){
            updatecategoryButton.setDisable(false);
            txt_food_name.setText(foodCategory.getName());
        }
    }

    @FXML
    void updateCategoryFood(ActionEvent event) {
        if (!txt_food_name.equals("")){
            FoodCategory newprodCategory = new FoodCategory();
            newprodCategory.setName(txt_food_name.getText());
            foodCategoryOperation.update(newprodCategory,foodCategory);
            updatecategoryButton.setDisable(true);
            txt_food_name.setText("");
            refresh();
        }
    }

    private void refresh() {
        listCategory = foodCategoryOperation.getAll();
        data_Table.setAll(listCategory);
        tableCatrgoryFood.setItems(data_Table);
    }



}
