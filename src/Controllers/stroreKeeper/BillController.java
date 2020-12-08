package Controllers.stroreKeeper;

import Models.Bill;
import com.jfoenix.controls.JFXListView;
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
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BillController implements Initializable {


    @FXML
    private BorderPane mainPane;
    @FXML
    private TextField txt_Provider_Name;

    @FXML
    private TextField txt_Paid;

    @FXML
    private Label lbl_bill_total;

    @FXML
    private TableView<Bill> table_Bill;

    @FXML
    private TableColumn<Bill, String> col_Product_Name;

    @FXML
    private TableColumn<Bill, String> col_Unit;

    @FXML
    private TableColumn<Bill, Integer> col_Quantity;

    @FXML
    private TableColumn<Bill, Integer> col_Price;

    @FXML
    private TableColumn<Bill, Integer> col_Total_Price;

    @FXML
    private JFXListView<String> listViewProduct;

    @FXML
    private TextField txt_price;

    @FXML
    private TextField txt_quantity;

    @FXML
    private ComboBox<String> comboProvider;

    @FXML
    private Label lbl_date;

    @FXML
    private Button closeButton;

    @FXML
    private VBox vboxBillOption;

    private ObservableList<Bill> dataTable = FXCollections.observableArrayList();
    private ObservableList<String> dataTableView = FXCollections.observableArrayList();
    private ArrayList<Bill> list_Bill = new ArrayList<>();
    private ArrayList<String> list_Product = new ArrayList<>();
    private int total_bill_Price = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // txt_Provider_Name.setText("naily maz");
    }

    public void Init(BorderPane mainPane) {
        this.mainPane = mainPane;
        chargeListProduct();
        col_Product_Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_Unit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        col_Quantity.setCellValueFactory(new PropertyValueFactory<>("quant"));
        col_Price.setCellValueFactory(new PropertyValueFactory<>("price"));
        col_Total_Price.setCellValueFactory(new PropertyValueFactory<>("total"));
        dataTableView.setAll(list_Product);
        listViewProduct.setItems(dataTableView);
        lbl_bill_total.setText(total_bill_Price + ".00");
        lbl_date.setText(LocalDate.now().toString());
    }

    private void chargeListProduct() {
        for (int i = 0; i < 2; i++) {
            list_Product.add("طماطم معلبة");
            list_Product.add("بطاطا");
            list_Product.add("فلفل");
            list_Product.add("طماطم");
            list_Product.add("مايوناز");
            list_Product.add("هريسة");
            list_Product.add("زيت");
            list_Product.add("كاتشاب");
            list_Product.add("زيتون");
            list_Product.add("طماطم");
        }
    }
    @FXML
    void showProductList(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/Products.fxml"));
            DialogPane temp = loader.load();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(temp);
            dialog.initStyle(StageStyle.UNDECORATED);
            dialog.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void close(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void insertProductBill(ActionEvent event) {
        if (listViewProduct.getSelectionModel().getSelectedItems() != null && !txt_price.getText().isEmpty() && !txt_quantity.getText().isEmpty()) {
            String product = String.valueOf(listViewProduct.getSelectionModel().getSelectedItems());
            Bill bill = new Bill();
            bill.setName(product);
            bill.setUnit("kg");
            bill.setQuant(Integer.valueOf(txt_quantity.getText()));
            bill.setPrice(Integer.valueOf(txt_price.getText()));
            bill.setTotal(bill.getPrice() * bill.getQuant());
            list_Bill.add(bill);
            txt_price.setText("");
            txt_quantity.setText("");
            total_bill_Price += bill.getTotal();
            refresh();



        }
    }

    @FXML
    void deleteProductBill(ActionEvent event) {
       Bill bill = table_Bill.getSelectionModel().getSelectedItem();
       if (bill != null){
        getIndex(bill.getName());
        refresh();
       }
    }

    private void getIndex(String productName){
        for (int i = 0; i < list_Bill.size(); i++) {
            if (list_Bill.get(i).getName().equals(productName)){
                total_bill_Price = total_bill_Price-list_Bill.get(i).getTotal();
                list_Bill.remove(i);
                break;
            }
        }
    }
    private void refresh(){
        dataTable.setAll(list_Bill);
        table_Bill.setItems(dataTable);
        lbl_bill_total.setText(total_bill_Price + ".00");

    }

}
