package Controllers.stroreKeeper;

import BddPackage.ProductCategoryOperation;
import Controllers.ValidateController;
import Models.ProductCategory;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CategoriesController implements Initializable {

    @FXML
    private VBox jobPane;

    @FXML
    private VBox vboxOptionCategory;

    @FXML
    private Button cancelButtonCategory;

    @FXML
    private TableView<ProductCategory> productCategoryTable;


    @FXML
    private TableColumn<ProductCategory, Integer> col_category_number;

    @FXML
    private TableColumn<ProductCategory, String> col_category_name;

    private ProductCategory productCategory = new ProductCategory();
    private ProductCategoryOperation productCategoryOperation = new ProductCategoryOperation();
    private ObservableList<ProductCategory> dataTable = FXCollections.observableArrayList();
    private ArrayList<ProductCategory> listPoductCategory = new ArrayList<>();
    private ValidateController validateController = new ValidateController();

    private FadeTransition fadeIn = new FadeTransition(
            Duration.millis(2000)
    );

    private boolean visibleCategory = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void Init(ProductCategory productCategory) {
        txt_category_upt.setText(productCategory.getName());
        this.productCategory = productCategory;
    }

    public void InitCategoryList() {
        vboxOptionCategory.setVisible(false);
        chargelistCategoryProduct();
        col_category_number.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_category_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        dataTable.setAll(listPoductCategory);
        productCategoryTable.setItems(dataTable);
    }

    private void chargelistCategoryProduct() {
        listPoductCategory = productCategoryOperation.getAll();
    }

    @FXML
    private void cancel(Button btn) {
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void clickedMouse(MouseEvent event) {
        closeDialog(cancelButtonCategory);
    }

    @FXML
    void deleteCategory(ActionEvent event) {
        vboxOptionCategory.setVisible(false);
        visibleCategory = false;
        productCategory = productCategoryTable.getSelectionModel().getSelectedItem();
        if (productCategory != null) {
            Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
            alertConfirmation.setHeaderText("تأكيد الحذف");
            alertConfirmation.setContentText("هل انت متأكد من حذف الصنف ");
            Button okButton = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("موافق");

            Button cancel = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.CANCEL);
            cancel.setText("الغاء");

            alertConfirmation.showAndWait().ifPresent(response -> {
                if (response == ButtonType.CANCEL) {
                    alertConfirmation.close();
                } else if (response == ButtonType.OK) {
                    productCategoryOperation.delete(productCategory);
                    refresh();
                }
            });
        } else {
            System.out.println("it's null");
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("تحذير ");
            alertWarning.setContentText("يرجى اختيار الصنف المراد حذفه");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("حسنا");
            alertWarning.showAndWait();
        }
    }

    @FXML
    void insertCategory(ActionEvent event) {
        try {
            vboxOptionCategory.setVisible(false);
            visibleCategory = false;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/storekeeper/AddCategory.fxml"));
            DialogPane temp = loader.load();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(temp);
            dialog.initStyle(StageStyle.UNDECORATED);
            dialog.showAndWait();
            refresh();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void refresh() {
        chargelistCategoryProduct();
        dataTable.setAll(listPoductCategory);
        productCategoryTable.setItems(dataTable);
    }

    @FXML
    void showHideCategoryOperation(ActionEvent event) {
        productCategoryTable.setDisable(true);
        visibleCategory = showHideListOperation(vboxOptionCategory,visibleCategory);
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
    void updateCategory(ActionEvent event) {
        vboxOptionCategory.setVisible(false);
        visibleCategory = false;
        ProductCategory productCategory = productCategoryTable.getSelectionModel().getSelectedItem();
        if (productCategory == null) {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("تحذير ");
            alertWarning.setContentText("يرجى اختيار الصنف المراد تعديله");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("حسنا");
            alertWarning.showAndWait();
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/storekeeper/UpdateCategory.fxml"));
                DialogPane temp = loader.load();
                CategoriesController categoriesController = loader.getController();
                categoriesController.Init(productCategory);
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setDialogPane(temp);
                dialog.initStyle(StageStyle.UNDECORATED);
                dialog.showAndWait();
                refresh();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    // ----- controller of addCategory

    @FXML
    private ImageView closebtn;

    @FXML
    private TextField txt_category;

    @FXML
    private Button insertCategoryButton;

    @FXML
    private Label errAddCategory;

    @FXML
    private Label lbl_valide;

    @FXML
    void AddCategoryAction(ActionEvent event) {
        if (!txt_category.getText().isEmpty()) {
            productCategoryOperation.insert(new ProductCategory(txt_category.getText()));
            txt_category.setText("");
            fadeIn.setNode(lbl_valide);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.setCycleCount(1);
            fadeIn.setAutoReverse(true);
            lbl_valide.setVisible(true);
            fadeIn.playFromStart();
            /////////////////////////////////////////////////////////////////////

        } else {
            errAddCategory.setText("ادخل اسم الصنف الجديد");
        }
    }
    @FXML
    void ShowCategoryTable(MouseEvent event) {
        productCategoryTable.setDisable(false);
    }

    @FXML
    void closeInsertJobDialog(MouseEvent event) {
        close(insertCategoryButton);
    }

    @FXML
    void txt_category_Pressed(KeyEvent event) {


    }
    private void closeDialog(Button btn) {
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }


    // controller of update

    @FXML
    private DialogPane mainPane;

    @FXML
    private TextField txt_category_upt;

    @FXML
    private Button updateCategoryButton;

    @FXML
    private Label errProductCategory;

    @FXML
    void UpdateCategoryAction(ActionEvent event) {
        if (!txt_category_upt.getText().isEmpty() && !productCategory.getName().equals(txt_category_upt.getText())) {
            productCategoryOperation.update(new ProductCategory(txt_category_upt.getText()), productCategory);
            close(updateCategoryButton);
        } else {
            errProductCategory.setText("ادخل اسم الوظيفة الجديدة");
        }
    }

    private void close(Button btn) {
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void closeUpdateJobDialog(MouseEvent event) {
        close(updateCategoryButton);
    }


}
