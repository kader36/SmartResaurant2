package Controllers.stroreKeeper;

import BddPackage.ProductCategoryOperation;
import BddPackage.ProductOperation;
import Controllers.ValidateController;
import Models.Product;
import Models.ProductCategory;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

public class ProductOperationController implements Initializable {

    @FXML
    private AnchorPane mainePane;

    @FXML
    private Pane operation_Pane;

    @FXML
    private TableView<Product> tableProduct;

    @FXML
    private TableColumn<Product, Integer> col_quantity;

    @FXML
    private TableColumn<Product,Integer> col_recipe_unite;

    @FXML
    private TableColumn<Product, String> col_purshase_unit;

    @FXML
    private TableColumn<Product, String> col_category_name;

    @FXML
    private TableColumn<Product, String> col_Product_name;

    @FXML
    private TableColumn<Product, Integer> col_idProduct;

    @FXML
    private TableColumn<Product, String> col_storage_Unite;

    @FXML
    private JFXButton insertbutton;

    @FXML
    private JFXButton updatebutton;

    @FXML
    private JFXButton deletebutton;

    @FXML
    private JFXTextField txt_Product_name;

    @FXML
    private JFXTextField txt_Purchase_Unite;

    @FXML
    private JFXTextField txt_Quantity;

    @FXML
    private JFXComboBox<String> categoryCombo;

    @FXML
    private JFXTextField txt_Recipe_Unite;

    @FXML
    private JFXTextField txt_Storage_Unite;

    @FXML
    private JFXButton AddProductButton;

    @FXML
    private JFXButton updateProductButton;

    private ArrayList<Product> listProduct ;
    private ArrayList<ProductCategory> listCategory ;
    private ObservableList<Product> dataTable = FXCollections.observableArrayList();
    private ObservableList<String> dataCombo = FXCollections.observableArrayList();
    private Product product;
    private ProductOperation productOperation;
    private ProductCategoryOperation productCategoryOperation;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ValidateController validateController = new ValidateController();
        validateController.inputNumberValueType(txt_Quantity);
        validateController.inputTextValueType(txt_Product_name);
        validateController.inputTextValueType(txt_Purchase_Unite);
        validateController.inputTextValueType(txt_Recipe_Unite);
        validateController.inputTextValueType(txt_Storage_Unite);
        productOperation = new ProductOperation();
        productCategoryOperation = new ProductCategoryOperation();
        listProduct = productOperation.getAll();
        listCategory = productCategoryOperation.getAll();
        col_idProduct.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_category_name.setCellValueFactory(new PropertyValueFactory<>("category_name"));
        col_Product_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_purshase_unit.setCellValueFactory(new PropertyValueFactory<>("purchase_Unit"));
        col_recipe_unite.setCellValueFactory(new PropertyValueFactory<>("Recipe_Unit"));
        col_storage_Unite.setCellValueFactory(new PropertyValueFactory<>("storage_Unit"));
        col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        dataTable.setAll(listProduct);
        tableProduct.setItems(dataTable);
        ChargelistCombo();
        categoryCombo.setItems(dataCombo);
        operation_Pane.setVisible(false);

    }
    public void Init(AnchorPane mainPane) {
        this.mainePane = mainPane;
    }

    @FXML
    void insertProductAction(ActionEvent event) {
            disable(true);
            updateProductButton.setVisible(false);
            operation_Pane.setVisible(true);

    }

    @FXML
    void updateProductAction(ActionEvent event) {

        product = tableProduct.getSelectionModel().getSelectedItem();
        if (product != null){
            categoryCombo.setValue(product.getCategory_name());
            txt_Product_name.setText(product.getName());
            txt_Storage_Unite.setText(product.getStorage_Unit());
            txt_Purchase_Unite.setText(product.getPurchase_Unit());
            txt_Recipe_Unite.setText(""+product.getRecipe_Unit());
            txt_Quantity.setText(String.valueOf(product.getQuantity()));
            disable(true);
            AddProductButton.setVisible(false);
            operation_Pane.setVisible(true);
        }


    }
    @FXML
    void deleteProductAction(ActionEvent event) {
        if (product != null){
            product = tableProduct.getSelectionModel().getSelectedItem();
            productOperation.delete(product);
            refresh();

        }
    }

    public void AddProduct(ActionEvent event) {
        if (testVide()) {
            Product newProduct = new Product();
            newProduct.setId_category(SearchIdCategory(categoryCombo.getValue()));
            newProduct.setName(txt_Product_name.getText());
            newProduct.setPurchase_Unit(txt_Purchase_Unite.getText());
            newProduct.setStorage_Unit(txt_Storage_Unite.getText());
            newProduct.setRecipe_Unit(txt_Recipe_Unite.getText());
            newProduct.setQuantity(Integer.valueOf(txt_Quantity.getText()));
            productOperation.insert(newProduct);
            updateProductButton.setVisible(true);
            txtVide();
            refresh();
        }
    }
    @FXML
    void UpdateProduct(ActionEvent event) {
        if (testVide()) {

            Product newProduct = new Product();
            newProduct.setId_category(SearchIdCategory(categoryCombo.getValue()));
            newProduct.setName(txt_Product_name.getText());
            newProduct.setPurchase_Unit(txt_Purchase_Unite.getText());
            newProduct.setStorage_Unit(txt_Storage_Unite.getText());
            newProduct.setRecipe_Unit((txt_Recipe_Unite.getText()));
            newProduct.setQuantity(Integer.valueOf(txt_Quantity.getText()));
            productOperation.update(newProduct, product);
            refresh();
            disable(false);
            operation_Pane.setVisible(false);
            txtVide();
        }

    }



    @FXML
    void HidePane() {
      operation_Pane.setVisible(false);
      disable(false);
    }
    private  void disable(boolean status){
         insertbutton.setDisable(status);
         updatebutton.setDisable(status);
         deletebutton.setDisable(status);
    }
    private void txtVide(){
        Vide(txt_Product_name, txt_Purchase_Unite, txt_Recipe_Unite, txt_Quantity, txt_Storage_Unite);
    }

    static void Vide(JFXTextField txt_product_name, JFXTextField txt_purchase_unite, JFXTextField txt_recipe_unite, JFXTextField txt_quantity, JFXTextField txt_storage_unite) {
        txt_product_name.setText("");
        txt_purchase_unite.setText("");
        txt_recipe_unite.setText("");
        txt_quantity.setText("");
        txt_storage_unite.setText("");
    }
    private void ChargelistCombo()
    {
        IntStream.range(0, listCategory.size()).forEach(i -> dataCombo.add(listCategory.get(i).getName()));
    }
    private int SearchIdCategory(String categoryName){
        int index = 0;
        for (int i = 0; i < listCategory.size(); i++) {
            if (listCategory.get(i).getName()==categoryName){
                index = i;
                break;
            }
        }

        return listCategory.get(index).getId();
    }
    private void refresh(){
        listProduct = productOperation.getAll();
        dataTable.setAll(listProduct);
        tableProduct.setItems(dataTable);
    }
    private boolean testVide() {
        return (!txt_Product_name.getText().equals("") && !txt_Quantity.getText().equals("")
                && !txt_Recipe_Unite.getText().equals("") && !txt_Purchase_Unite.getText().equals("")
                && !txt_Storage_Unite.getText().equals(""));

    }


}
