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
}
