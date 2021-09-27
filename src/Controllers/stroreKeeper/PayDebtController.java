package Controllers.stroreKeeper;

import BddPackage.ProductOperation;
import BddPackage.ProviderOperation;
import BddPackage.StorBilleOperation;
import BddPackage.StoreBillProductOperation;
import Models.*;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PayDebtController implements Initializable {
    @FXML
    private JFXButton cancelButtonUser;
    @FXML
    private Label number;

    @FXML
    private TableView<Bill> Tableproduct;
    @FXML
    private TableColumn<Bill, String> col_Product_Name;
    @FXML
    private TableColumn<Bill, String> col_Price;
    @FXML
    private TableColumn<Bill, String> col_Total_Price;
    @FXML
    private TableColumn<Bill, String> col_Quantity;

    @FXML
    private Label Total;


    @FXML
    private Label rest;

    @FXML
    private TextField paid;
    private BillList billList;
    private ObservableList<Bill> dataTable = FXCollections.observableArrayList();
    private ArrayList<Bill> list_Bill = new ArrayList<>();
    public void initialize(URL location, ResourceBundle resources) {
        cancelButtonUser.setOnAction(actionEvent -> {
            closeDialog(cancelButtonUser);
        });
    }
    private void closeDialog(Button btn) {
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }
    public void init(BillList billList){
        this.billList=billList;
        number.setText(String.valueOf(billList.getNumber()));
        Total.setText(String.valueOf(billList.getTotal()));
        rest.setText(String.valueOf(billList.getRest()));
        ChargeProduct();
       col_Product_Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_Quantity.setCellValueFactory(new PropertyValueFactory<>("quant"));
        col_Price.setCellValueFactory(new PropertyValueFactory<>("price"));
        col_Total_Price.setCellValueFactory(new PropertyValueFactory<>("total"));
    }
    private void ChargeProduct(){
        StoreBillProductOperation storeBillProductOperation=new StoreBillProductOperation();
        StorBilleOperation storBilleOperation=new StorBilleOperation();
        ArrayList<StoreBillProduct> list = new ArrayList<>();
        list=storeBillProductOperation.getBill(Integer.parseInt(number.getText()));

        for(int i=0;i<list.size() ;i++) {
            ProductOperation productOperation=new ProductOperation();
            Product product = productOperation.GetProduct(list.get(i).getId_product());
            Bill bill = new Bill();
            bill.setName(product.getName());
            bill.setQuant(list.get(i).getProduct_quantity());
            bill.setPrice(list.get(i).getPrice());
            bill.setTotal(list.get(i).getPrice() * list.get(i).getProduct_quantity());
            list_Bill.add(bill);
        }
        dataTable.setAll(list_Bill);
        Tableproduct.setItems(dataTable);
    }
    @FXML
    void pay(ActionEvent event) {
        int intpaid= Integer.parseInt(paid.getText());
        if(intpaid>Integer.parseInt(rest.getText())){
            Alert alertWarning = new Alert(Alert.AlertType.ERROR);
            alertWarning.setHeaderText("تحذير ");
            alertWarning.setContentText("مبلع المدخل اكبر من المبلغ المستحق ");
            Button okkButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okkButton.setText("حسنا");
            alertWarning.showAndWait();
        }else {
            StorBilleOperation  storBilleOperation=new StorBilleOperation();
            StoreBill storeBill=storBilleOperation.getStorBill(billList.getNumber());
            storeBill.setPaid_up(storeBill.getPaid_up()+intpaid);
            storBilleOperation.update(storeBill,storeBill);
            ProviderOperation providerOperation=new ProviderOperation();
            Provider provider=providerOperation.getProviderById(storeBill.getId_provider());
            provider.setCreditor(provider.getCreditor()-intpaid);
            providerOperation.update(provider);
            closeDialog(cancelButtonUser);
            BillListController.Refech.setValue(!BillListController.Refech.getValue());
        }
    }
}
