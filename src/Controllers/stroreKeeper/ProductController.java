package Controllers.stroreKeeper;

import BddPackage.ProductOperation;
import Models.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProductController implements Initializable {

    @FXML
    private BorderPane mainPane;

    @FXML
    private TableView<Product> productTable;

    @FXML
    private TableColumn<Product, String> col_name;

    @FXML
    private TableColumn<Product, String> col_category;

    @FXML
    private TableColumn<Product, String> col_unite;

    @FXML
    private TableColumn<Product, Integer> col_quantity;

    @FXML
    private TableColumn<Product, Integer> col_less_quantity;

    @FXML
    private Label lbl_less_quantity;

    @FXML
    private Label lbl_dead_product;

    @FXML
    private TextField txt_name;

    @FXML
    private ComboBox<String> categoryCombo;

    @FXML
    private TextField txt_unite;

    @FXML
    private TextField txt_tot_quantity;

    @FXML
    private TextField txt_less_quantity;

    @FXML
    private ComboBox<String> comboByCategory;

    @FXML
    private TextField txt_search;

    @FXML
    private VBox vboxOption;

    private boolean visible;
    private ObservableList<Product> dataTable = FXCollections.observableArrayList();
    private ArrayList<Product> list_Products = new ArrayList<>();
    private ProductOperation productOperation = new ProductOperation();
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void Init(BorderPane mainPane) {
        this.mainPane = mainPane;
        hidevbox();
        vboxOption.setVisible(false);
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_category.setCellValueFactory(new PropertyValueFactory<>("category_name"));
        col_unite.setCellValueFactory(new PropertyValueFactory<>("storage_Unit"));
        col_quantity.setCellValueFactory(new PropertyValueFactory<>("tot_quantity"));
        col_less_quantity.setCellValueFactory(new PropertyValueFactory<>("less_quantity"));
        list_Products = productOperation.getAll();
        dataTable.setAll(list_Products);
        productTable.setItems(dataTable);
        categoryCombo.getItems().addAll("fofof","hhh");

    }

    private void hidevbox() {
        mainPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                vboxOption.setVisible(false);
                visible = false;

            }
        });
    }

    @FXML
    void insertProduct(ActionEvent event) {
        String category = categoryCombo.getSelectionModel().getSelectedItem();
        if (!txt_name.getText().isEmpty() && !txt_tot_quantity.getText().isEmpty()
          && !txt_less_quantity.getText().isEmpty() && !txt_unite.getText().isEmpty() && !category.isEmpty()){
         Product product = new Product();
         product.setName(txt_name.getText());
         product.setId_category(idCategory(category));
         product.setTot_quantity(Integer.valueOf(txt_tot_quantity.getText()));
         product.setStorage_Unit(txt_unite.getText());
         product.setLess_quantity(Integer.valueOf(txt_less_quantity.getText()));
         productOperation.insert(product);
         clearTxt();
     }
    }

    private void clearTxt() {
        txt_unite.clear();
        txt_less_quantity.clear();
        txt_tot_quantity.clear();
        txt_name.clear();
    }

    @FXML
    void updateProduct(ActionEvent event) {
        String category = categoryCombo.getSelectionModel().getSelectedItem();
        System.out.println(idCategory(category));
    }

    @FXML
    void deleteProduct(ActionEvent event) {

    }
    private int idCategory(String category){
        int i = 0;
        for (Product list_Product : list_Products) {
            if (list_Product.getCategory_name().equals(category)) {
                System.out.println("op : " +i);
                return list_Product.getId_category();
            }
            i++;
        }
        return -1;
    }
    @FXML
    void showhideVbox(ActionEvent event) {
        visible = showHideListOperation(vboxOption,visible);
    }
    private boolean showHideListOperation(VBox vBox ,boolean visibles){
        if (!visibles){
            vBox.setVisible(true);
            visibles = true;
        }
        else{ vBox.setVisible(false);
            visibles = false;

        }
        return visibles;
    }
}

