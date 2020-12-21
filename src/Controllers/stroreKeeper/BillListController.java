package Controllers.stroreKeeper;

import Models.BillList;
import Models.Provider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class BillListController implements Initializable {
    @FXML
    private BorderPane mainPane;


    @FXML
    private TableView<BillList> billTable;

    @FXML
    private TableColumn<?, ?> col_Bill_number;

    @FXML
    private TableColumn<?, ?> col_Date;

    @FXML
    private TableColumn<?, ?> col_Provider;

    @FXML
    private TableColumn<?, ?> col_Paid;

    @FXML
    private TableColumn<?, ?> col_rest;

    @FXML
    private TableColumn<?, ?> col_Total;

    @FXML
    private VBox vboxBillOption;

    @FXML
    private TextField txtSearchField;

    FilteredList<BillList> filteredData;


    private ArrayList<BillList> billLists = new ArrayList<>();
    private ObservableList<BillList> dataTable = FXCollections.observableArrayList();
    private boolean visible = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chargeListBill();
        vboxBillOption.setVisible(false);
        col_Bill_number.setCellValueFactory(new PropertyValueFactory<>("number"));
        col_Date.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_Provider.setCellValueFactory(new PropertyValueFactory<>("provider_name"));
        col_Paid.setCellValueFactory(new PropertyValueFactory<>("Paid_up"));
        col_rest.setCellValueFactory(new PropertyValueFactory<>("rest"));
        col_Total.setCellValueFactory(new PropertyValueFactory<>("total"));
        dataTable.setAll(billLists);
        billTable.setItems(dataTable);

    }

    public void Init(BorderPane mainPane) {
        this.mainPane = mainPane;
        vboxBillOption.setVisible(false);
        visible = false;
        mainPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                vboxBillOption.setVisible(false);
                visible = false;
            }
        });
    }

    @FXML
    void addNewBill(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/AddFactoryBuy.fxml"));
            BorderPane temp = loader.load();
            AddBillController addBillController = loader.getController();
            addBillController.Init(temp);
            mainPane.getChildren().setAll(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void chargeListBill() {
        for (int i = 1; i < 10; i++) {
            BillList billList = new BillList();
            billList.setNumber(i);
            billList.setProvider_name("selmani");
            billList.setDate((i * 2 + 1) + "/" + i + "/2020");
            billList.setPaid_up(i * 1255);
            billList.setTotal(i * 1525);
            billList.setRest(billList.getTotal() - billList.getPaid_up());
            billLists.add(billList);
        }
    }

    @FXML
    void slideShowHide(ActionEvent event) {
        if (!visible) {
            vboxBillOption.setVisible(true);
            visible = true;
        } else {
            vboxBillOption.setVisible(false);
            visible = false;

        }
    }

    @FXML
    void editBill(ActionEvent event) {
        if (verificationBillSelected("edit")){

        }else {
            return;
        }
    }

    @FXML
    void deleteBill(ActionEvent event) {
        if (verificationBillSelected("delete")){

        }else {
            return;
        }
    }


    boolean verificationBillSelected(String message){
        vboxBillOption.setVisible(false);
        visible = false;
        BillList billList = billTable.getSelectionModel().getSelectedItem();
        if (billList != null){
            return true;
        } else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("تحذير ");
            if (message.equals("delete"))
                alertWarning.setContentText("يرجى اختيار الفاتورة المراد حذفها");
            else
                alertWarning.setContentText("يرجى اختيار الفاتورة المراد تعديلها");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("حسنا");
            alertWarning.showAndWait();
            return false;
        }
    }

    @FXML
    void exportCsvListBill(ActionEvent event) {

    }

    @FXML
    void exportPdfLstBill(ActionEvent event) {

    }

    @FXML
    void reportListBill(ActionEvent event) {

    }

    @FXML
    void txtSearchBill(ActionEvent event) {
        // filtrer les données
        ObservableList<BillList> dataListBill = billTable.getItems();
        filteredData = new FilteredList<>(dataListBill, e -> true);
        txtSearchField.setOnKeyReleased(e -> {
            txtSearchField.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filteredData.setPredicate((Predicate<? super BillList>) billList -> {
                    if (newValue == null || newValue.isEmpty()) {
                        //loadDataInTable();
                        return true;
                    } else if (String.valueOf(billList.getNumber()).contains(newValue.toLowerCase())) {
                        return true;
                    } else if (billList.getProvider_name().toLowerCase().contains(newValue.toLowerCase())) {
                        return true;
                    }
                    return false;
                });
            });

            SortedList<BillList> sortedList = new SortedList<BillList>(filteredData);
            sortedList.comparatorProperty().bind(billTable.comparatorProperty());
            billTable.setItems(sortedList);
        });
    }

//    private void refresh() {
//        billLists = providerOperation.getAll();
//        dataTable.setAll(list_Providers);
//        providerTable.setItems(dataTable);
//        lbl_provider_number.setText("" + (list_Providers.size() - 1));
//
//    }
}
