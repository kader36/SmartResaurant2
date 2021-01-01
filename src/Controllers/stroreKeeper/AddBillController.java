package Controllers.stroreKeeper;

import BddPackage.ProductOperation;
import BddPackage.ProviderOperation;
import BddPackage.StorBilleOperation;
import BddPackage.StoreBillProductOperation;
import Controllers.ValidateController;
import Models.*;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXListView;
import com.mysql.jdbc.Connection;
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
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class AddBillController implements Initializable {


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
    Button reportTableBillBtn;

    @FXML
    Button exportCsvTableBillBtn;

    @FXML
    Button exportPdfTableBillBtn;

    @FXML
    private JFXCheckBox saveCheck;

    @FXML
    private TextField searchTxtProduct;

    @FXML
    private Button insertProductBillBtn;

    @FXML
    private Button saveBtn;

    @FXML
    private Label nbFact;


    private ObservableList<String> dataCombo;

    private StoreBillProductOperation billProductOperation = new StoreBillProductOperation();
    private StorBilleOperation storBilleOperation = new StorBilleOperation();
    private ValidateController validateController = new ValidateController();
    private static int idStorBill;
    private static String provider;

    @FXML
    private Button closeButton;

    @FXML
    private VBox vboxBillOption;

    private ObservableList<Bill> dataTable = FXCollections.observableArrayList();
    private ObservableList<String> dataTableView = FXCollections.observableArrayList();
    private ArrayList<Bill> list_Bill = new ArrayList<>();
    private ArrayList<String> list_Product = new ArrayList<>();
    private int total_bill_Price = 0;
    private ArrayList<Product> list_ProductsObject = new ArrayList<>();
    ProviderOperation providerOperation = new ProviderOperation();
    private ArrayList<Provider> listProviderObject = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // txt_Provider_Name.setText("naily maz");
    }

    public void Init(BorderPane mainPane) {
        setDisableReports();
        this.mainPane = mainPane;
        chargeListProduct();
        chargeListProvider();
        col_Product_Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_Unit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        col_Quantity.setCellValueFactory(new PropertyValueFactory<>("quant"));
        col_Price.setCellValueFactory(new PropertyValueFactory<>("price"));
        col_Total_Price.setCellValueFactory(new PropertyValueFactory<>("total"));
        dataTableView.setAll(list_Product);
        listViewProduct.setItems(dataTableView);
        lbl_bill_total.setText(total_bill_Price + ".00");
        lbl_date.setText(LocalDate.now().toString());
        validateController.inputNumberValue(txt_Paid);
        nbFact.setText(String.valueOf(storBilleOperation.getCountStoreBill() + 1));
//        listViewProduct.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//
//            }
//        });
    }

//    @FXML
//    void clickedListView(MouseEvent event) {
//        System.out.println("gggg");
//        insertProductBillBtn.setDisable(false);
//    }


    private void chargeListProvider() {
        dataCombo = FXCollections.observableArrayList();
        ProviderOperation providerOperation = new ProviderOperation();
        ArrayList<Provider> listProvider = providerOperation.getAll();
        for (Provider listProviderFromDB : listProvider) {
            dataCombo.add(listProviderFromDB.getLast_name() + "   " + listProviderFromDB.getPhone_number());
        }
        comboProvider.setItems(dataCombo);
    }

    private void chargeListProduct() {
//        for (int i = 0; i < 2; i++) {
//
//            list_Product.add("طماطم معلبة");
//            list_Product.add("بطاطا");
//            list_Product.add("فلفل");
//            list_Product.add("طماطم");
//            list_Product.add("مايوناز");
//            list_Product.add("هريسة");
//            list_Product.add("زيت");
//            list_Product.add("كاتشاب");
//            list_Product.add("زيتون");
//            list_Product.add("طماطم");
//
//        }

        dataCombo = FXCollections.observableArrayList();
        ProductOperation productOperation = new ProductOperation();
        ArrayList<Product> listProducts = productOperation.getAll();
        for (Product listProductFromBD : listProducts) {
            list_Product.add(listProductFromBD.getName());
            list_ProductsObject.add(listProductFromBD);
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
        Stage stage = (Stage) saveBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void insertProductBill(ActionEvent event) {
        if (listViewProduct.getSelectionModel().getSelectedItem() == null) {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("تحذير ");
            alertWarning.setContentText("يرجى اختيار نوع السلعة");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("حسنا");
            alertWarning.showAndWait();
            return;
        }
        if (listViewProduct.getSelectionModel().getSelectedItems() != null && !txt_price.getText().isEmpty() && !txt_quantity.getText().isEmpty()) {
            //txt_quantity
            if (!txt_quantity.getText().matches("[0-9]+")
                    || !txt_price.getText().matches("[0-9]+")
            ) {
                Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                alertWarning.setHeaderText("تحذير ");
                alertWarning.setContentText("يرجى ادخال ارقام");
                Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                okButton.setText("حسنا");
                alertWarning.showAndWait();
                return;
            }
            String product = String.valueOf(listViewProduct.getSelectionModel().getSelectedItems());
            Bill bill = new Bill();
            product = product.replace("[", "");
            product = product.replace("]", "");
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


        } else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("تحذير ");
            alertWarning.setContentText("يرجى ملأ الخانات اللازمة");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("حسنا");
            alertWarning.showAndWait();
        }
    }

    @FXML
    void deleteProductBill(ActionEvent event) {
        Bill bill = table_Bill.getSelectionModel().getSelectedItem();
        if (bill != null) {
            getIndex(bill.getName());
            refresh();
        } else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("تحذير ");
            alertWarning.setContentText("يرجى تحديد السلعة المراد حذفها");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("حسنا");
            alertWarning.showAndWait();
        }
    }

    private void getIndex(String productName) {
        for (int i = 0; i < list_Bill.size(); i++) {
            if (list_Bill.get(i).getName().equals(productName)) {
                total_bill_Price = total_bill_Price - list_Bill.get(i).getTotal();
                list_Bill.remove(i);
                break;
            }
        }
    }

    private void refresh() {
        dataTable.setAll(list_Bill);
        table_Bill.setItems(dataTable);
        lbl_bill_total.setText(total_bill_Price + ".00");
        setDisableReports();
    }

    @FXML
    void saveTableBill(ActionEvent event) {
        //Bill bill = table_Bill.getSelectionModel().getSelectedItem();
        dataTable = FXCollections.observableArrayList();
        dataTable.setAll(table_Bill.getItems());
        if (table_Bill.getItems().size() == 0) {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("تحذير ");
            alertWarning.setContentText("الرجاء ملأ الفاتورة قبل الحفظ");
            Button okkButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okkButton.setText("حسنا");
            alertWarning.showAndWait();
            return;
        }
        if (dataTable != null && !comboProvider.getSelectionModel().isEmpty()) {
            Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
            alertConfirmation.setHeaderText("تأكيد الحفظ");
            alertConfirmation.setContentText("هل انت متأكد من عملية حفظ الفاتورة");
            Button okButton = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("موافق");

            Button cancel = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.CANCEL);
            cancel.setText("الغاء");

            alertConfirmation.showAndWait().ifPresent(response -> {
                if (response == ButtonType.CANCEL) {
                    alertConfirmation.close();
                } else if (response == ButtonType.OK) {
                    provider = comboProvider.getSelectionModel().getSelectedItem();
                    String[] tab = comboProvider.getSelectionModel().getSelectedItem().split(" ");
                    String s = tab[tab.length - 1];
                    int idProvider = Integer.parseInt(s);
                    int paid;
                    if (txt_Paid.getText().equals(""))
                        paid = 0;
                    else
                        paid = Integer.parseInt(txt_Paid.getText());
                    // update provider
                    // update provider
                    Provider provider = getProviderById(idProvider);
                    provider.setCreditor(String.valueOf(Integer.parseInt(lbl_bill_total.getText()) - Integer.parseInt(txt_Paid.getText())));
                    providerOperation.update(provider,provider);
                    // insert into store bill
                    StoreBill storeBill = new StoreBill();
                    storeBill.setId_provider(idProvider);
                    storeBill.setId_user(1);
                    storeBill.setPaid_up(paid);
                    storBilleOperation.insert(storeBill);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    idStorBill = storBilleOperation.getIdStorBill(idProvider, 1, paid);
                    for (Bill bill : dataTable) {
                        // insert into store bill product
                        StoreBillProduct storeBillProduct = new StoreBillProduct();
                        storeBillProduct.setId_stor_bill(idStorBill);
                        storeBillProduct.setId_product(getIdProductByCobo(bill.getName()));
                        storeBillProduct.setPrice(bill.getPrice());
                        storeBillProduct.setProduct_quantity(bill.getQuant());
                        billProductOperation.insert(storeBillProduct);
                    }
                    refresh();
                    // go to Factories Secreen
                    Alert alertWarning = new Alert(Alert.AlertType.INFORMATION);
                    alertWarning.setHeaderText("تأكيد ");
                    alertWarning.setContentText("تم اضافة الفاتورة بنجاح");
                    Button okkButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                    okkButton.setText("حسنا");
                    alertWarning.showAndWait();
                }
                setEnableReports();
            });
        } else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("تحذير ");
            alertWarning.setContentText("يرجى تحديد اسم المورد");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("حسنا");
            alertWarning.showAndWait();
        }
    }

    private Provider getProviderById(int idProvider) {
        listProviderObject = providerOperation.getAll();
        for (Provider provider : listProviderObject){
            if (provider.getId() == idProvider)
                return provider;
        }

        return null;
    }

    @FXML
    void cancelTableBill(ActionEvent event) {
        Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirmation.setHeaderText("تأكيد الالغاء");
        alertConfirmation.setContentText("هل انت متأكد من الغاء اجراء العمليات ؟");
        Button okButton = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setText("موافق");

        Button cancel = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.CANCEL);
        cancel.setText("الغاء");

        alertConfirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.CANCEL) {
                alertConfirmation.close();
            } else if (response == ButtonType.OK) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Factories.fxml"));
                    BorderPane temp = loader.load();
                    BillListController billController = loader.getController();
                    billController.Init(temp);
                    mainPane.getChildren().setAll(temp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    int getIdProductByCobo(String comboChose) {
        for (Product listProductFromDB : list_ProductsObject) {
            String product = listProductFromDB.getName().replace("[", "");
            product = product.replace("]", "");
            if (comboChose.equals(product))
                return listProductFromDB.getId();
        }

        return -1;
    }


    @FXML
    void searchProduct(ActionEvent event) {
        // filtrer les données
        ObservableList<String> dataProduct = listViewProduct.getItems();
        FilteredList<String> filteredData = new FilteredList<>(dataProduct, e -> true);
        searchTxtProduct.setOnKeyReleased(e -> {
            searchTxtProduct.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filteredData.setPredicate((Predicate<? super String>) product -> {
                    if (newValue == null || newValue.isEmpty()) {
                        //loadDataInTable();
                        return true;
                    } else if (product.contains(newValue.toLowerCase())) {
                        return true;
                    }
                    return false;
                });
            });

            SortedList<String> sortedList = new SortedList<>(filteredData);
            //sortedList.comparatorProperty().bind((ObservableValue<? extends Comparator<? super String>>) listViewProduct);
            listViewProduct.setItems(sortedList);//exe
        });
    }

    @FXML
    void exportCsvTableBill(ActionEvent event) {
        if (verificationBeforeReport()) {
            try {
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                // choose directory of CSV
                DirectoryChooser directoryChooser = new DirectoryChooser();
                File selectedDirectory = directoryChooser.showDialog(stage);
                String workspace;
                if (selectedDirectory == null)
                    return;
                workspace = selectedDirectory.getAbsolutePath();
                // Export to CSV.
                PrintWriter pw = new PrintWriter(workspace + "/list of products.csv");
                StringBuilder sb = new StringBuilder();

                sb.append("المورد : ");
                sb.append(",");
                sb.append(provider);
                sb.append("\r\n");
                sb.append("\r\n");
                sb.append("رقم الفاتورة");
                sb.append(",");
                sb.append("اسم السلعة");
                sb.append(",");
                sb.append("السعر");
                sb.append(",");
                sb.append("الكمية الإجمالية");
                sb.append("\r\n");
                ArrayList<StoreBillProduct> billLists = billProductOperation.getAll();
                ProductOperation productOperation = new ProductOperation();
                ArrayList<Product> listProducts = productOperation.getAll();
                String nameProduct = null;
                for (StoreBillProduct storeBillProduct : billLists) {
                    if (storeBillProduct.getId_stor_bill() == idStorBill) {
                        sb.append(storeBillProduct.getId_stor_bill());
                        sb.append(",");
                        for (Product listProductFromBD : listProducts) {
                            if (listProductFromBD.getId() == storeBillProduct.getId_product())
                                nameProduct = listProductFromBD.getName();
                        }
                        sb.append(nameProduct);
                        sb.append(",");
                        sb.append(storeBillProduct.getPrice());
                        sb.append(",");
                        sb.append(storeBillProduct.getProduct_quantity());
                        sb.append("\r\n");
                    }
                }
                sb.append("\r\n");
                sb.append("\r\n");
                sb.append("\r\n");

                sb.append("الإجمالي :");
                sb.append(",");
                sb.append(total_bill_Price);
                sb.append("\r\n");
                pw.write(sb.toString());
                pw.close();
                Alert alertWarning = new Alert(Alert.AlertType.INFORMATION);
                alertWarning.setHeaderText("تأكيد");
                alertWarning.setContentText("تم استخراج المعلومات بواسطة Excel بنجاح");
                Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                okButton.setText("حسنا");
                alertWarning.showAndWait();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else
            return;
    }

    @FXML
    void exportPdfTableBill(ActionEvent event) {
        if (verificationBeforeReport()) {
            try {
                // TODO must change report path
                // choose directory of PDF
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();

                DirectoryChooser directoryChooser = new DirectoryChooser();
                File selectedDirectory = directoryChooser.showDialog(stage);
                String workspace;
                if (selectedDirectory == null)
                    return;
                workspace = selectedDirectory.getAbsolutePath();
                //
                String report = "E:\\SmartResaurant\\src\\Views\\storekeeper\\reportStoreBillProduct.jrxml";
                JasperDesign jasperDesign = JRXmlLoader.load(report);
                String sqlCmd = "select * from store_bill_product where id_store_bill = " + idStorBill;
                JRDesignQuery jrDesignQuery = new JRDesignQuery();
                jrDesignQuery.setText(sqlCmd);
                jasperDesign.setQuery(jrDesignQuery);
                JasperReport jasperReport = null;
                jasperReport = JasperCompileManager.compileReport(jasperDesign);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null);
                // Export to PDF.
                JasperExportManager.exportReportToPdfFile(jasperPrint, workspace + "/list of products.pdf");
            } catch (JRException e) {
                e.printStackTrace();
            }
        } else
            return;
    }

    @FXML
    void reportTableBill(ActionEvent event) {
        if (verificationBeforeReport()) {
            // TODO must change report path
            try {
                String report = "E:\\SmartResaurant\\src\\Views\\storekeeper\\reportStoreBillProduct.jrxml";
                JasperDesign jasperDesign = JRXmlLoader.load(report);
                String sqlCmd = "select * from store_bill_product where id_store_bill = " + idStorBill;
                JRDesignQuery jrDesignQuery = new JRDesignQuery();
                jrDesignQuery.setText(sqlCmd);
                jasperDesign.setQuery(jrDesignQuery);
                JasperReport jasperReport = null;
                jasperReport = JasperCompileManager.compileReport(jasperDesign);
                Connection connection = null;
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, connection);
                JasperViewer.viewReport(jasperPrint);
            } catch (JRException e) {
                e.printStackTrace();
            }
        } else
            return;
    }

    boolean verificationBeforeReport() {
        if (dataTable != null && !comboProvider.getSelectionModel().isEmpty()) {
            return true;
        } else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("تحذير ");
            alertWarning.setContentText("يرجى تحديد اسم المورد");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("حسنا");
            alertWarning.showAndWait();
            return false;
        }
    }

    void setDisableReports() {
        reportTableBillBtn.setDisable(true);
        exportCsvTableBillBtn.setDisable(true);
        exportPdfTableBillBtn.setDisable(true);
    }

    void setEnableReports() {
        reportTableBillBtn.setDisable(false);
        exportCsvTableBillBtn.setDisable(false);
        exportPdfTableBillBtn.setDisable(false);
    }
}
