package Controllers.KitchenChef;

import BddPackage.ProductCompositeOperation;
import Controllers.ValidateController;
import Models.Product;
import Models.ProductComposite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProductCompositeController implements Initializable {
    @FXML
    private VBox vboxOption;
    private boolean visible;
    @FXML
    private BorderPane mainPane;
    @FXML
    private TextField txt_name;
    @FXML
    private TextField txt_tot_quantity;
    @FXML
    private TextField txt_LESS_QUANTITY;
    @FXML
    private ComboBox<String> Unity_Food;
    @FXML
    private ComboBox<String> STORAGE_UNIT;
    @FXML
    private TextField  txt_Coefficient;
    @FXML
    private TableView<ProductComposite> productTable;
    @FXML
    private TableColumn<ProductComposite, String> col_name;
    @FXML
    private TableColumn<ProductComposite, Integer> col_quantity;
    @FXML
    private TableColumn<Product, String> col_unite;
    @FXML
    private TableColumn<Product, String> col_UnityFood;
    @FXML
    private TableColumn<Product, String> col_Coefficient;
    @FXML
    private TableColumn<Product, String> col_LESS_QUANTITY;

    private ProductCompositeOperation  productCompositeOperation=new ProductCompositeOperation();
    private ValidateController validateController = new ValidateController();
    private ObservableList<ProductComposite> dataTable = FXCollections.observableArrayList();
    private ArrayList<ProductComposite> list_Products = new ArrayList<>();
    private ProductCompositeOperation productOperation = new ProductCompositeOperation();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtValidate();
        vboxOption.setVisible(false);
        STORAGE_UNIT.getItems().addAll("كيلوغرام","غرام","لتر","وحدة");
        Unity_Food.getItems().addAll("غرام","لتر","ميللتر","وحدة");
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        col_unite.setCellValueFactory(new PropertyValueFactory<>("storage_Unit"));
        col_UnityFood.setCellValueFactory(new PropertyValueFactory<>("Unity_Food"));
        col_Coefficient.setCellValueFactory(new PropertyValueFactory<>("Coefficient"));
        col_LESS_QUANTITY.setCellValueFactory(new PropertyValueFactory<>("LESS_QUANTITY"));
        list_Products = productOperation.getAll();
        dataTable.setAll(list_Products);
        productTable.setItems(dataTable);
    }
    @FXML
    void showhideVbox(ActionEvent event) {
        visible = showHideListOperation(vboxOption, visible);
    }
    private boolean showHideListOperation(VBox vBox, boolean visibles) {
        if (!visibles) {
            vBox.setVisible(true);
            visibles = true;
        } else {
            vBox.setVisible(false);
            visibles = false;

        }
        return visibles;
    }
    @FXML
    void insertProduct(ActionEvent event) {
        if (!txt_name.getText().isEmpty() && !STORAGE_UNIT.getSelectionModel().isEmpty()&&!Unity_Food.getSelectionModel().isEmpty()&&!txt_Coefficient.getText().isEmpty()) {
            ProductComposite product = new ProductComposite();
            product.setName(txt_name.getText());
            product.setQuantity(Float.valueOf(txt_tot_quantity.getText()));
            product.setLESS_QUANTITY(Integer.valueOf(txt_LESS_QUANTITY.getText()));
            product.setStorage_Unit(STORAGE_UNIT.getSelectionModel().getSelectedItem());
            product.setUnity_Food(Unity_Food.getSelectionModel().getSelectedItem());
            product.setCoefficient(Integer.valueOf(txt_Coefficient.getText()));

            productCompositeOperation.insert(product);
            Alert alertWarning = new Alert(Alert.AlertType.INFORMATION);
            alertWarning.setHeaderText("تأكيد ");
            alertWarning.setContentText("تم اضافة السلعة بنجاح");
            Button OKButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            OKButton.setText("حسنا");
            alertWarning.showAndWait();


            refresh();
            clearTxt();
        } else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("تحذير ");
            alertWarning.setContentText("يرجى ملأ جميع الحقول");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("حسنا");
            alertWarning.showAndWait();
        }
    }
    private void clearTxt() {
        txt_LESS_QUANTITY.clear();
        txt_tot_quantity.clear();
        txt_name.clear();
        txt_Coefficient.clear();
        STORAGE_UNIT.getSelectionModel().clearSelection();
        Unity_Food.getSelectionModel().clearSelection();
    }
    private void txtValidate() {
        validateController.inputTextValueType(txt_name);
        validateController.inputNumberValue(txt_tot_quantity);
        validateController.inputNumberValue(txt_Coefficient);
        validateController.inputNumberValue(txt_LESS_QUANTITY);
    }
    private void refresh() {
        list_Products = productOperation.getAll();
        dataTable.setAll(list_Products);
        productTable.setItems(dataTable);
    }
    @FXML
    void addddProductComposite(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/KitchenChef/addProductComposite.fxml"));
            BorderPane temp = loader.load();

           mainPane.getChildren().setAll(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
