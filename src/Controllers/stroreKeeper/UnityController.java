package Controllers.stroreKeeper;

import BddPackage.UnityOperation;
import Controllers.ValidateController;
import Models.ProductCategory;
import Models.Unity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UnityController implements Initializable {
    @FXML
    private ImageView closebtn;

    @FXML
    private TextField txt_unity;

    @FXML
    private Button insertCategoryButton;

    @FXML
    private Label errAddCategory;

    @FXML
    private Label lbl_valide;
    @FXML
    private VBox jobPane;

    @FXML
    private VBox vboxOptionCategory;

    @FXML
    private Button cancelButtonCategory;

    @FXML
    private TableView<Unity> productCategoryTable;


    @FXML
    private TableColumn<Unity, Integer> col_category_number;

    @FXML
    private TableColumn<Unity, String> col_category_name;
    private boolean visibleCategory = false;
    private ProductCategory unitys = new ProductCategory();
    private UnityOperation unityopertion = new UnityOperation();
    private ObservableList<Unity> dataTable = FXCollections.observableArrayList();
    private ArrayList<Unity> listunity= new ArrayList<>();
    private ValidateController validateController = new ValidateController();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public void Init(){
        vboxOptionCategory.setVisible(false);
        chargelistUnity();
        col_category_number.setCellValueFactory(new PropertyValueFactory<>("Id"));
        col_category_name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        dataTable.setAll(listunity);
        productCategoryTable.setItems(dataTable);
    }
    private void chargelistUnity() {
        listunity = unityopertion.getAll();
    }
    @FXML
    void ShowCategoryTable(MouseEvent event) {
        productCategoryTable.setDisable(false);
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
    void clickedMouse(MouseEvent event) {
        closeDialog(cancelButtonCategory);
    }
    private void closeDialog(Button btn) {
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }
    @FXML
    void AddUnityAction(ActionEvent event) {
        if (!txt_unity.getText().isEmpty()) {
            Unity unity=new Unity();
            unity.setName(txt_unity.getText());
            unityopertion.insert(unity);
            txt_unity.setText("");
            closeDialog(insertCategoryButton);
            refresh();
        }else
            errAddCategory.setText("الرجاء ادخل اسم الوحدة الجديدة");

    }

    @FXML
    void closeInsertJobDialog(MouseEvent event) {
        closeDialog(insertCategoryButton);
    }
    @FXML
    void insertUnity(ActionEvent event) {
        try {
            vboxOptionCategory.setVisible(false);
            visibleCategory = false;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/storekeeper/AddUnity.fxml"));
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
        chargelistUnity();
        dataTable.setAll(listunity);
        productCategoryTable.setItems(dataTable);
    }
    @FXML
    void deleteUnity(ActionEvent event) {
        vboxOptionCategory.setVisible(false);
        visibleCategory = false;
        Unity unity=new Unity();
        unity = productCategoryTable.getSelectionModel().getSelectedItem();
        if (unity != null) {
            Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
            alertConfirmation.setHeaderText("تأكيد الحذف");
            alertConfirmation.setContentText("هل انت متأكد من حذف الوحدة ");
            Button okButton = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("موافق");

            Button cancel = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.CANCEL);
            cancel.setText("الغاء");

            Unity finalUnity1 = unity;
            alertConfirmation.showAndWait().ifPresent(response -> {
                if (response == ButtonType.CANCEL) {
                    alertConfirmation.close();
                } else if (response == ButtonType.OK) {
                    unityopertion.delete(finalUnity1);
                    refresh();
                }
            });
        } else {
            System.out.println("it's null");
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("تحذير ");
            alertWarning.setContentText("يرجى اختيار الوجدة المراد حذفه");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("حسنا");
            alertWarning.showAndWait();
        }

    }
}
