package Controllers.stroreKeeper;

import BddPackage.ProductCategoryOperation;
import BddPackage.ProductOperation;
import Controllers.ValidateController;
import Models.Product;
import Models.ProductCategory;
import Models.Provider;
import com.mysql.jdbc.Connection;
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
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

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
    private ComboBox<String> comboListCategory;

    @FXML
    private TextField txt_search;

    @FXML
    private Label totProducts;

    FilteredList<Product> filteredData;

    @FXML
    private VBox vboxOption;
    private boolean visible;
    private ObservableList<Product> dataTable = FXCollections.observableArrayList();
    private ArrayList<Product> list_Products = new ArrayList<>();
    private ProductOperation productOperation = new ProductOperation();
    private ObservableList<String> dataCombo;
    static ArrayList<ProductCategory> listCategory;
    private ValidateController validateController = new ValidateController();
    private Product product;

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
        txtValidate();
        //categoryCombo.getItems().addAll("fofof", "hhh");
        chargeListCategory();
        comboListCategory.getItems().addAll("اسم السلعة", "الصنف", "الكمية الإجمالية", "الحد الأدنى", "الوحدة");
        sortComboByCategory();
        lbl_less_quantity.setText(String.valueOf(productOperation.getNbLessQuantity()));
        lbl_dead_product.setText(String.valueOf(productOperation.getNbDeadProduct()));
        totProducts.setText(String.valueOf(productOperation.getCountProduct()));
    }

    private void sortComboByCategory() {
        comboListCategory.valueProperty().addListener((new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                switch (newValue) {
                    case "اسم السلعة":
                        dataTable.setAll(productOperation.getAllBy("PRODUCT.PRODUCT_NAME"));
                        productTable.setItems(dataTable);
                        break;
                    case "الصنف":
                        dataTable.setAll(productOperation.getAllBy("PRODUCT.ID_PRODUCT_CATEGORY"));
                        productTable.setItems(dataTable);
                        break;
                    case "الكمية الإجمالية":
                        dataTable.setAll(productOperation.getAllBy("PRODUCT.QUANTITY"));
                        productTable.setItems(dataTable);
                        break;
                    case "الحد الأدنى":
                        dataTable.setAll(productOperation.getAllBy("PRODUCT.LESS_QUANTITY"));
                        productTable.setItems(dataTable);
                        break;

                    case "الوحدة":
                        dataTable.setAll(productOperation.getAllBy("PRODUCT.STORAGE_UNIT"));
                        productTable.setItems(dataTable);
                        break;
                }

            }
        }));
    }

    private void txtValidate() {
        validateController.inputTextValueType(txt_name);
        validateController.inputTextValueType(txt_unite);
        validateController.inputNumberValue(txt_tot_quantity);
        validateController.inputNumberValue(txt_less_quantity);
    }

    private void txtValidateUpdate() {
        validateController.inputTextValueType(txt_name_upd);
        validateController.inputTextValueType(txt_unite_upd);
        validateController.inputNumberValue(txt_tot_quantity_upd);
        validateController.inputNumberValue(txt_less_quantity_upd);
    }

    private void refresh() {
        list_Products = productOperation.getAll();
        dataTable.setAll(list_Products);
        productTable.setItems(dataTable);
        lbl_less_quantity.setText(String.valueOf(productOperation.getNbLessQuantity()));
        lbl_dead_product.setText(String.valueOf(productOperation.getNbDeadProduct()));
        totProducts.setText(String.valueOf(productOperation.getCountProduct()));
    }

    void chargeListCategory() {
        dataCombo = FXCollections.observableArrayList();
        ProductCategoryOperation categoryOperation = new ProductCategoryOperation();
        listCategory = categoryOperation.getAll();
        for (ProductCategory listProductCategoryFromDB : listCategory)
            dataCombo.add(listProductCategoryFromDB.getName());
        categoryCombo.setItems(dataCombo);
    }

    static int getIdCategoryByCobo(String comboChose) {
        for (ProductCategory listProductCategoryFromDB : listCategory)
            if (comboChose.equals(listProductCategoryFromDB.getName()))
                return listProductCategoryFromDB.getId();
        return -1;
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
        if (!txt_name.getText().isEmpty() && !txt_tot_quantity.getText().isEmpty()
                && !txt_less_quantity.getText().isEmpty() && !txt_unite.getText().isEmpty() && !categoryCombo.getSelectionModel().isEmpty()) {
            String category = categoryCombo.getSelectionModel().getSelectedItem();
            Product product = new Product();
            product.setName(txt_name.getText());
            //product.setId_category(idCategory(category));
            product.setId_category(getIdCategoryByCobo(category));
            product.setTot_quantity(Integer.valueOf(txt_tot_quantity.getText()));
            product.setStorage_Unit(txt_unite.getText());
            product.setLess_quantity(Integer.valueOf(txt_less_quantity.getText()));
            productOperation.insert(product);
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
        txt_unite.clear();
        txt_less_quantity.clear();
        txt_tot_quantity.clear();
        txt_name.clear();
    }

    @FXML
    void updateProduct(ActionEvent event) {
//        String category = categoryCombo.getSelectionModel().getSelectedItem();
//        System.out.println(idCategory(category));
        vboxOption.setVisible(false);
        visible = false;
        Product product = productTable.getSelectionModel().getSelectedItem();
        if (product != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/updateProduct.fxml"));
            DialogPane temp = null;
            try {
                temp = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ProductController productController = loader.getController();
            productController.chargeListCategory();
            productController.chargeTxt(product);
            productController.txtValidateUpdate();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(temp);
            dialog.initStyle(StageStyle.UNDECORATED);
            dialog.showAndWait();
            refresh();
        } else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("تحذير ");
            alertWarning.setContentText("يرجى اختيار السلعة المراد تعديلها");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("حسنا");
            alertWarning.showAndWait();
        }
    }

    private void chargeTxt(Product oldProduct) {
        txt_name_upd.setText(oldProduct.getName());
        txt_less_quantity_upd.setText(String.valueOf(oldProduct.getLess_quantity()));
        txt_tot_quantity_upd.setText(String.valueOf(oldProduct.getTot_quantity()));
        txt_unite_upd.setText(oldProduct.getStorage_Unit());
        categoryCombo.setValue(oldProduct.getCategory_name());
        this.product = oldProduct;
    }

    @FXML
    void deleteProduct(ActionEvent event) {
        vboxOption.setVisible(false);
        visible = false;
        Product product = productTable.getSelectionModel().getSelectedItem();
        if (product != null) {
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
                    productOperation.delete(product);
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
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("تحذير ");
            alertWarning.setContentText("يرجى اختيار السلعة المراد حذفها");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("حسنا");
            alertWarning.showAndWait();
        }
    }

    @FXML
    void showListCategory(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/storekeeper/Categories.fxml"));
            DialogPane temp = loader.load();
            CategoriesController categoriesController = loader.getController();
            categoriesController.InitCategoryList();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(temp);
            dialog.initStyle(StageStyle.UNDECORATED);
            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private int idCategory(String category) {
        int i = 0;
        for (Product list_Product : list_Products) {
            if (list_Product.getCategory_name().equals(category)) {
                System.out.println("op : " + i);
                return list_Product.getId_category();
            }
            i++;
        }
        return -1;
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
    void searchProduct(ActionEvent event) {
        // filtrer les données
        ObservableList<Product> dataProvider = productTable.getItems();
        filteredData = new FilteredList<>(dataProvider, e -> true);
        txt_search.setOnKeyReleased(e -> {
            txt_search.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filteredData.setPredicate((Predicate<? super Product>) provider -> {
                    if (newValue == null || newValue.isEmpty()) {
                        //loadDataInTable();
                        return true;
                    } else if (String.valueOf(provider.getId()).contains(newValue.toLowerCase())) {
                        return true;
                    } else if (provider.getName().toLowerCase().contains(newValue.toLowerCase())) {
                        return true;
                    } else if (provider.getCategory_name().toLowerCase().contains(newValue.toLowerCase())) {
                        return true;
                    } else if (String.valueOf(provider.getTot_quantity()).contains(newValue)) {
                        return true;
                    } else if (provider.getCategory_name().contains(newValue)) {
                        return true;
                    }
                    return false;
                });
            });

            SortedList<Product> sortedList = new SortedList<>(filteredData);
            sortedList.comparatorProperty().bind(productTable.comparatorProperty());
            productTable.setItems(sortedList);
        });
    }

    @FXML
    void exportCsvTableProduct(ActionEvent event) {
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
            sb.append("اسم السلعة");
            sb.append(",");
            sb.append("الصنف");
            sb.append(",");
            sb.append("الوحدة");
            sb.append(",");
            sb.append("الكمية الإجمالية");
            sb.append(",");
            sb.append("الحد الأدنى");
            sb.append("\r\n");
            list_Products = productOperation.getAll();
            for (Product product : list_Products) {
                sb.append(product.getName());
                sb.append(",");
                sb.append(product.getCategory_name());
                sb.append(",");
                sb.append(product.getStorage_Unit());
                sb.append(",");
                sb.append(product.getTot_quantity());
                sb.append(",");
                sb.append(product.getLess_quantity());
                sb.append("\r\n");
            }
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
    }

    @FXML
    void exportPdfTableProduct(ActionEvent event) {
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
            String report = "E:\\SmartResaurant\\src\\Views\\storekeeper\\reportProductList.jrxml";
            JasperDesign jasperDesign = JRXmlLoader.load(report);
            String sqlCmd = "select * from product";
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
    }

    @FXML
    void reportTableProduct(ActionEvent event) {
        // TODO must change report path
        try {
            String report = "E:\\SmartResaurant\\src\\Views\\storekeeper\\reportProductList.jrxml";
            JasperDesign jasperDesign = JRXmlLoader.load(report);
            String sqlCmd = "select * from product order by id_product";
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
    }

    // --- this attribuites and methodes are of update Product

    @FXML
    private TextField txt_name_upd;

    @FXML
    private TextField txt_unite_upd;

    @FXML
    private TextField txt_tot_quantity_upd;

    @FXML
    private TextField txt_less_quantity_upd;

    @FXML
    private Label errUpdateProduct;

    @FXML
    private Button updateButton;


    @FXML
    void closeUpdateProduct(MouseEvent event) {
        closeDialog(updateButton);
    }

    @FXML
    void updateProductAction(ActionEvent event) {
        if (!txt_name_upd.getText().isEmpty() && !txt_tot_quantity_upd.getText().isEmpty()
                && !txt_less_quantity_upd.getText().isEmpty() && !txt_unite_upd.getText().isEmpty() && !categoryCombo.getSelectionModel().isEmpty()) {
            String category = categoryCombo.getSelectionModel().getSelectedItem();
            Product updProduct = new Product();
            updProduct.setId_category(getIdCategoryByCobo(category));
            updProduct.setName(txt_name_upd.getText());
            updProduct.setTot_quantity(Integer.parseInt(txt_tot_quantity_upd.getText()));
            updProduct.setStorage_Unit(txt_unite_upd.getText());
            updProduct.setLess_quantity(Integer.parseInt(txt_less_quantity_upd.getText()));
            productOperation.update(updProduct, product);
            Alert alertWarning = new Alert(Alert.AlertType.INFORMATION);
            alertWarning.setHeaderText("تأكيد ");
            alertWarning.setContentText("تم تعديل السلعة بنجاح");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("حسنا");
            alertWarning.showAndWait();
            closeDialog(updateButton);
        } else {
            errUpdateProduct.setText("الرجاء ملأ جميع الحقول");
        }
        //productOperation = new ProductOperation();
    }

    private void closeDialog(Button btn) {
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }


}

