package Controllers.stroreKeeper;

import BddPackage.ProviderOperation;
import BddPackage.StorBilleOperation;
import BddPackage.StoreBillProductOperation;
import Controllers.ValuesStatic;
import Models.BillList;
import Models.Provider;
import Models.StoreBill;
import Models.StoreBillProduct;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    private TableColumn<BillList, Integer> col_Bill_number;

    @FXML
    private TableColumn<BillList, String> col_Date;

    @FXML
    private TableColumn<BillList, String> col_Provider;

    @FXML
    private TableColumn<BillList, Integer> col_Paid;

    @FXML
    private TableColumn<BillList, Integer> col_rest;

    @FXML
    private TableColumn<BillList, Integer> col_Total;

    @FXML
    private VBox vboxBillOption;

    @FXML
    private TextField txtSearchField;

    @FXML
    private Label nbTotFact;

    @FXML
    private ComboBox<String> listedByCombo;

    FilteredList<BillList> filteredData;


    private ArrayList<BillList> billLists = new ArrayList<>();
    private ArrayList<StoreBill> storBillLists = new ArrayList<>();
    private ArrayList<Provider> providerList = new ArrayList<>();
    private ArrayList<StoreBillProduct> billListProduct = new ArrayList<>();
    private ObservableList<BillList> dataTable = FXCollections.observableArrayList();
    private StorBilleOperation storBilleOperation = new StorBilleOperation();
    private ProviderOperation providerOperation = new ProviderOperation();
    private StoreBillProductOperation storeBillProductOperation = new StoreBillProductOperation();
    private boolean visible = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void Init(BorderPane mainPane) {
      //  vboxBillOption.setVisible(false);
        col_Bill_number.setCellValueFactory(new PropertyValueFactory<>("number"));
        col_Date.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_Provider.setCellValueFactory(new PropertyValueFactory<>("provider_name"));
        col_Paid.setCellValueFactory(new PropertyValueFactory<>("Paid_up"));
        col_rest.setCellValueFactory(new PropertyValueFactory<>("rest"));
        col_Total.setCellValueFactory(new PropertyValueFactory<>("total"));
        billLists = chargeListBill();
        dataTable.setAll(billLists);
        billTable.setItems(dataTable);
        nbTotFact.setText(String.valueOf(storBilleOperation.getCountStoreBill()));
        listedByCombo.getItems().addAll("رقم الفاتورة","اسم المورد","المدفوع");
        listedByCombo.valueProperty().addListener((new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                switch (newValue) {
                    case "رقم الفاتورة":
                        dataTable.setAll(chargeListBill("STORE_BILL.ID_STORE_BILL"));
                        billTable.setItems(dataTable);
                        System.out.println("misaoooooooooooooor");
                        break;
                    case "اسم المورد":
                        dataTable.setAll(chargeListBill("STORE_BILL.ID_PROVIDER_OPERATION"));
                        billTable.setItems(dataTable);
                        break;
                    case "المدفوع":
                        dataTable.setAll(chargeListBill("STORE_BILL.PAID_UP"));
                        billTable.setItems(dataTable);
                        break;
                }

            }
        }));
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

    private ArrayList<BillList> chargeListBill() {
//
        ArrayList<BillList> list = new ArrayList<>();
        storBillLists = storBilleOperation.getAll();
        providerList = providerOperation.getAll();
        billListProduct = storeBillProductOperation.getAll();


        for (StoreBill storeBill : storBillLists) {
            int total = 0;
            BillList billList = new BillList();
            billList.setNumber(storeBill.getId());
            billList.setDate(storeBill.getDate().toString());
            billList.setPaid_up(storeBill.getPaid_up());
            billList.setProvider_name(storeBill.getProvider(storeBill.getId_provider()).getLast_name());
            //this for loop is for get total
            for (StoreBillProduct storeBillProduct : billListProduct) {
                if (storeBillProduct.getId_stor_bill() == storeBill.getId()) {
                    total = total+storeBillProduct.getPrice() * storeBillProduct.getProduct_quantity();
                }

            }
            billList.setTotal(total);
            billList.setRest(total-billList.getPaid_up());
            list.add(billList);



        }
        return list;
    }


    private ArrayList<BillList> chargeListBill(String orderBy) {
//
        ArrayList<BillList> list = new ArrayList<>();
        storBillLists = storBilleOperation.getAllBy(orderBy);
        providerList = providerOperation.getAll();
        billListProduct = storeBillProductOperation.getAll();


        for (StoreBill storeBill : storBillLists) {
            int total = 0;
            BillList billList = new BillList();
            billList.setNumber(storeBill.getId());
            billList.setDate(storeBill.getDate().toString());
            billList.setPaid_up(storeBill.getPaid_up());
            billList.setProvider_name(storeBill.getProvider(storeBill.getId_provider()).getLast_name());

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
        if (verificationBillSelected("edit")) {
            ValuesStatic.billList = billTable.getSelectionModel().getSelectedItem();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/UpdateFactoryBuy.fxml"));
                BorderPane temp = loader.load();
                UpdateBillController updateBillController = loader.getController();
                updateBillController.Init(temp);
                mainPane.getChildren().setAll(temp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            return;
        }
    }

    @FXML
    void deleteBill(ActionEvent event) {
        if (verificationBillSelected("delete")) {
            ValuesStatic.billList = billTable.getSelectionModel().getSelectedItem();
            StoreBill storeBill = new StoreBill();
            storeBill.setId(billTable.getSelectionModel().getSelectedItem().getNumber());
            Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
            alertConfirmation.setHeaderText("تأكيد الحذف");
            alertConfirmation.setContentText("هل انت متأكد من حذف السلعة  ");
            Button okButton = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("موافق");
            Button cancel = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.CANCEL);
            cancel.setText("الغاء");

            alertConfirmation.showAndWait().ifPresent(response -> {
                if (response == ButtonType.CANCEL) {
                    alertConfirmation.close();
                } else if (response == ButtonType.OK) {
                    storBilleOperation.delete(storeBill);
                    Alert alertWarning = new Alert(Alert.AlertType.INFORMATION);
                    alertWarning.setHeaderText("تأكيد ");
                    alertWarning.setContentText("تم حذف السلعة بنجاح");
                    Button OKButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                    OKButton.setText("حسنا");
                    alertWarning.showAndWait();
                    refresh();
                }
            });
        } else {
            return;
        }
    }


    void refresh() {
        billLists = chargeListBill();
        dataTable.setAll(billLists);
        billTable.setItems(dataTable);
        nbTotFact.setText(String.valueOf(storBilleOperation.getCountStoreBill()));
    }

    boolean verificationBillSelected(String message) {
        vboxBillOption.setVisible(false);
        visible = false;
        BillList billList = billTable.getSelectionModel().getSelectedItem();
        if (billList != null) {
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
