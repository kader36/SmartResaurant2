package Controllers.stroreKeeper;

import BddPackage.ProductCategoryOperation;
import Controllers.ValidateController;
import Models.ProductCategory;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CategoryProductController implements Initializable {

    @FXML
    private AnchorPane mainPane;

    @FXML
    private TableView<ProductCategory> tableCatrgory;

    @FXML
    private TableColumn<ProductCategory, String> col_category_name;

    @FXML
    private TableColumn<ProductCategory, Integer> col_idcategory;

    @FXML
    private JFXButton insertbutton;

    @FXML
    private JFXButton updatebutton;

    @FXML
    private JFXButton deletebutton;

    @FXML
    private JFXTextField txt_name;

    @FXML
    private JFXButton updatecategoryButton;


    private ArrayList<ProductCategory> listCategory;
    private ObservableList<ProductCategory> data_Table= FXCollections.observableArrayList();
    private ProductCategory productCategory;
    private ProductCategoryOperation productCategoryOperation;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ValidateController validateController = new ValidateController();
        validateController.inputTextValueType(txt_name);

        productCategoryOperation = new ProductCategoryOperation();
        listCategory = productCategoryOperation.getAll();
        data_Table.setAll(listCategory);
        col_idcategory.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_category_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableCatrgory.setItems(data_Table);
        updatecategoryButton.setDisable(true);

    }
    public void Init(AnchorPane mainPane){
        this.mainPane = mainPane;
    }


    private void refresh() {
        listCategory = productCategoryOperation.getAll();
        data_Table.setAll(listCategory);
        tableCatrgory.setItems(data_Table);
    }



   public void updateCategory(javafx.event.ActionEvent event) {
        if (!txt_name.equals("")){
            ProductCategory newprodCategory = new ProductCategory();
            newprodCategory.setName(txt_name.getText());
            productCategoryOperation.update(newprodCategory,productCategory);
            updatecategoryButton.setDisable(true);
            txt_name.setText("");
            refresh();
        }
    }
    public void deleteCategory(javafx.event.ActionEvent event) {
        ProductCategory productCategory = tableCatrgory.getSelectionModel().getSelectedItem();
        if(productCategory != null){
            productCategoryOperation.delete(productCategory);
        }
        refresh();
    }



    public void insertCategory(javafx.event.ActionEvent actionEvent) {
        if (!txt_name.equals("")){
           ProductCategory productCategory = new ProductCategory();
            productCategory.setName(txt_name.getText());
            productCategoryOperation.insert(productCategory);
            refresh();
        }
    }
    public void updateAction(javafx.event.ActionEvent actionEvent) {
       ProductCategory productCategory = tableCatrgory.getSelectionModel().getSelectedItem();
        if(productCategory != null){
            updatecategoryButton.setDisable(false);
            txt_name.setText(productCategory.getName());
        }
    }
}
