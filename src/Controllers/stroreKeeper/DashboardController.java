package Controllers.stroreKeeper;


import BddPackage.ProductOperation;
import BddPackage.ProviderOperation;
import BddPackage.StorBilleOperation;
import BddPackage.StoreBillProductOperation;
import Controllers.ValuesStatic;
import Models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private BorderPane mainPane;

    @FXML
    private Label factToday;

    @FXML
    private Label factWeek;

    @FXML
    private Label factMonth;

    @FXML
    private Label credetorToday;

    @FXML
    private Label credetorWeek;

    @FXML
    private Label CredetorMonth;

    @FXML
    private Label totFact;

    @FXML
    private Label totProvider;

    @FXML
    private Label totCreditor;

    @FXML
    private TableView<Product> productFinishedTable;

    @FXML
    private TableColumn<?, ?> name;

    @FXML
    private TableColumn<?, ?> unit;

    @FXML
    private TableView<Provider> providerTable;

    @FXML
    private TableColumn<?, ?> col_last_name;

    @FXML
    private TableColumn<?, ?> col_job;

    @FXML
    private TableColumn<?, ?> col_phone_number;

    @FXML
    private TableColumn<?, ?> col_creditor;

    @FXML
    private TableView<BillList> factTable;

    @FXML
    private TableColumn<?, ?> col_Bill_number;

    @FXML
    private TableColumn<?, ?> col_Date;

    @FXML
    private TableColumn<?, ?> col_Provider;

    @FXML
    private TableColumn<?, ?> col_Paid;

    @FXML
    private TableColumn<?, ?> col_Total;


    StorBilleOperation storBilleOperation = new StorBilleOperation();
    ProviderOperation providerOperation = new ProviderOperation();
    ProductOperation productOperation = new ProductOperation();
    private ArrayList<Product> list_Products;
    private ArrayList<Provider> list_Provider;
    private ArrayList<BillList> list_Fact;
    private ObservableList<Product> dataTableProduct = FXCollections.observableArrayList();
    private ObservableList<Provider> dataTableProvider = FXCollections.observableArrayList();
    private ObservableList<BillList> dataTableFact = FXCollections.observableArrayList();
    private StoreBillProductOperation storeBillProductOperation = new StoreBillProductOperation();
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void Init(BorderPane mainPane){
        this.mainPane = mainPane;
        storBilleOperation.getAll();
        totFact.setText(String.valueOf(storBilleOperation.getCountStoreBill()));
        totProvider.setText(String.valueOf(providerOperation.getCountProvider()));
        totCreditor.setText(ValuesStatic.totCreditor + ".00");
        factToday.setText(String.valueOf(ValuesStatic.factToday));
        credetorToday.setText( "00");
        factMonth.setText(String.valueOf(ValuesStatic.factMonth));
        CredetorMonth.setText(ValuesStatic.credetorMonth + ".00");
        factWeek.setText(String.valueOf(ValuesStatic.factWeek));
        credetorWeek.setText(ValuesStatic.credetorWeek + ".00");

        //chargeProductFinishedTable();
        chargeCredtorProviderTable();
        chargeFactTable();
    }

    private void chargeFactTable() {
        col_Bill_number.setCellValueFactory(new PropertyValueFactory<>("number"));
        col_Date.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_Provider.setCellValueFactory(new PropertyValueFactory<>("provider_name"));
        col_Paid.setCellValueFactory(new PropertyValueFactory<>("Paid_up"));
        col_Total.setCellValueFactory(new PropertyValueFactory<>("total"));
        list_Fact = chargeListBill();
        dataTableFact.setAll(list_Fact);
        factTable.setItems(dataTableFact);
    }

    private ArrayList<BillList> chargeListBill() {
        ArrayList<BillList> list = new ArrayList<>();
        ArrayList<StoreBill> storBillLists = storBilleOperation.getAll();
        //ArrayList<Provider> providerList = providerOperation.getAll();
        ArrayList<StoreBillProduct> billListProduct = storeBillProductOperation.getAll();


        for (StoreBill storeBill : storBillLists) {
            BillList billList = new BillList();
            int total = 0;
            billList.setNumber(storeBill.getId());
            billList.setDate(storeBill.getDate().toString());
            billList.setPaid_up(storeBill.getPaid_up());
            billList.setProvider_name(storeBill.getProvider(storeBill.getId_provider()).getLast_name());
           // billList.setRest(Integer.parseInt(storeBill.getProvider(storeBill.getId_provider()).getCreditor()));
            //this for loop is for get total
            for (StoreBillProduct storeBillProduct : billListProduct) {
                if (storeBillProduct.getId_stor_bill() == storeBill.getId()) {
                    total += storeBillProduct.getPrice() * storeBillProduct.getProduct_quantity();
                }
            }
            billList.setTotal(total);
            billList.setRest(total-billList.getPaid_up());
            list.add(billList);
        }

        return list;
    }

    private void chargeCredtorProviderTable() {
        col_last_name.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        col_phone_number.setCellValueFactory(new PropertyValueFactory<>("phone_number"));
        col_job.setCellValueFactory(new PropertyValueFactory<>("job"));
        col_creditor.setCellValueFactory(new PropertyValueFactory<>("creditor"));
        list_Provider = providerOperation.getCredetorProvider();
        dataTableProvider.setAll(list_Provider);
        providerTable.setItems(dataTableProvider);
    }

    private void chargeProductFinishedTable() {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        unit.setCellValueFactory(new PropertyValueFactory<>("storage_Unit"));
        list_Products = productOperation.getProductFinished();
        dataTableProduct.setAll(list_Products);
        productFinishedTable.setItems(dataTableProduct);
    }
}
