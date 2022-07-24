package Controllers.stroreKeeper;

import BddPackage.ProductOperation;
import Models.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LessProductController implements Initializable {
    @FXML
    private TableView<Product> Tableproduct;

    @FXML
    private TableColumn<Product, String> col_Product;

    @FXML
    private TableColumn<Product, String> col_qantity;
    @FXML
    private Button btn_close;
    private ObservableList<Product> dataTable = FXCollections.observableArrayList();
    private ArrayList<Product> list_Products = new ArrayList<>();
    private ProductOperation productOperation = new ProductOperation();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        col_Product.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_qantity.setCellValueFactory(new PropertyValueFactory<>("tot_quantity"));
        list_Products = productOperation.getProductFinished();
        dataTable.setAll(list_Products);
        Tableproduct.setItems(dataTable);
    }


    @FXML
    void close(ActionEvent event) {
        closeDialog(btn_close);
    }
    private void closeDialog(Button btn) {
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }
}
