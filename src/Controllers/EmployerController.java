package Controllers;

import BddPackage.EmployerOperation;
import Models.Employer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EmployerController implements Initializable {
    @FXML
    private TableView<Employer> employerTableView;
    @FXML
    private TableColumn<Employer, String> col_name;
    @FXML
    private TableColumn<Employer, String> col_last_name;
    @FXML
    private TableColumn<Employer, String> col_phone_number;
    @FXML
    private TableColumn<Employer, String> col_job;
    @FXML
    private TableColumn<Employer, String> col_work_strat;
    @FXML
    private TableColumn<Employer, String> col_work_end;
    @FXML
    private TableColumn<Employer, String> col_salary;
    @FXML
    private TableColumn<Employer, Integer> id;
    @FXML
    private TextField txt_name;
    @FXML
    private TextField txt_last_name;
    @FXML
    private TextField txt_phone_number;
    @FXML
    private TextField txt_job;
    @FXML
    private TextField txt_salary;
    @FXML
    private VBox vboxOption;

    private boolean visible;
    private ObservableList<Employer> dataTable = FXCollections.observableArrayList();
    private ArrayList<Employer> list_Elployer = new ArrayList<>();
    private EmployerOperation employerOperation = new EmployerOperation();
    private ObservableList<String> dataCombo;
    private ValidateController validateController = new ValidateController();
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        vboxOption.setVisible(false);
        txtValidate();
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        col_last_name.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        col_phone_number.setCellValueFactory(new PropertyValueFactory<>("phone_number"));
        col_job.setCellValueFactory(new PropertyValueFactory<>("job"));
        col_salary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        col_work_strat.setCellValueFactory(new PropertyValueFactory<>("work_strat"));
        col_work_end.setCellValueFactory(new PropertyValueFactory<>("work_end"));
        list_Elployer=employerOperation.getAll();
        dataTable.setAll(list_Elployer);
        employerTableView.setItems(dataTable);

    }
    @FXML
    void insertProduct(ActionEvent event) {
        if (!txt_name.getText().isEmpty() && !txt_last_name.getText().isEmpty()
                && !txt_phone_number.getText().isEmpty() && !txt_job.getText().isEmpty()) {

            Employer employer = new Employer();
            employer.setFirst_name(txt_name.getText());
            employer.setLast_name(txt_last_name.getText());
            employer.setPhone_number(txt_phone_number.getText());
            employer.setJob(txt_job.getText());
            employer.setSalary(Integer.valueOf(txt_salary.getText()));
            employerOperation.insert(employer);

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
    private void refresh() {
        list_Elployer = employerOperation.getAll();
        dataTable.setAll(list_Elployer);
        employerTableView.setItems(dataTable);
    }
    private void clearTxt() {
        txt_last_name.clear();
        txt_phone_number.clear();
        txt_job.clear();
        txt_name.clear();
        txt_salary.clear();
    }
    private void txtValidate() {
        validateController.inputTextValueType(txt_name);
        validateController.inputNumberValueType(txt_phone_number);
        validateController.inputTextValueType(txt_last_name);
        validateController.inputTextValueType(txt_job);
        validateController.inputNumberValue(txt_salary);
    }
    @FXML
    void showListUsers(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Users.fxml"));
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
    void deleteProduct(ActionEvent event) {
        vboxOption.setVisible(false);
        visible = false;
        Employer employer = employerTableView.getSelectionModel().getSelectedItem();
        if (employer != null) {
            Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
            alertConfirmation.setHeaderText("تأكيد الحذف");
            alertConfirmation.setContentText("هل انت متأكد من حذف هذا الموظف   ");
            Button okButton = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("موافق");
            Button cancel = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.CANCEL);
            cancel.setText("الغاء");

            alertConfirmation.showAndWait().ifPresent(response -> {
                if (response == ButtonType.CANCEL) {
                    alertConfirmation.close();
                } else if (response == ButtonType.OK) {
                    EmployerOperation employerOperation=new EmployerOperation();
                    employerOperation.delete(employer);
                    Alert alertWarning = new Alert(Alert.AlertType.INFORMATION);
                    alertWarning.setHeaderText("تأكيد ");
                    alertWarning.setContentText("تم حذف الموظف بنجاح");
                    Button OKButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                    OKButton.setText("حسنا");
                    alertWarning.showAndWait();
                    refresh();
                }
            });
        } else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("تحذير ");
            alertWarning.setContentText("يرجى اختيار الموظف المراد حذفه");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("حسنا");
            alertWarning.showAndWait();
        }
    }
    @FXML
    void updateProduct(ActionEvent event) {
        vboxOption.setVisible(false);
        visible = false;
        Employer employer = employerTableView.getSelectionModel().getSelectedItem();
        if (employer != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/UpdateEmploy.fxml"));
            DialogPane temp = null;
            try {
                temp = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            UpdateEmployerController updateEmployerController = loader.getController();
            updateEmployerController.chargeTxt(employer);
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

}
