package Controllers;

import BddPackage.Em_Added_ValueOpertion;
import BddPackage.MoneyWithdrawalOperationsBDD;
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
import java.util.ResourceBundle;

public class Employer_Added_ValueController implements Initializable {
    long millis=System.currentTimeMillis();
    @FXML
    private Label number;

    @FXML
    private TableView<Employer_Added_Value> Tableproduct;

    @FXML
    private TableColumn<Employer_Added_Value, String> col_Date;

    @FXML
    private TableColumn<Employer_Added_Value, Integer> col_Valuse;

    @FXML
    private TextField paid;

    @FXML
    private JFXButton cancelButtonUser;
    private Employer employer;
    private ObservableList<Employer_Added_Value> dataTable = FXCollections.observableArrayList();
    public void initialize(URL location, ResourceBundle resources) {
        cancelButtonUser.setOnAction(actionEvent -> {
            closeDialog(cancelButtonUser);
        });
    }
    void chargeDATA(){
        Employer_Added_Value value=new Employer_Added_Value();
        value.setId_employer(employer.getId());
        Em_Added_ValueOpertion em_added_valueOpertion=new Em_Added_ValueOpertion();
        dataTable.setAll(em_added_valueOpertion.getAll(value));
        Tableproduct.setItems(dataTable);

    }
    public void Init(Employer employer){
        this.employer=employer;
        chargeDATA();
        col_Date.setCellValueFactory(new PropertyValueFactory<>("Date_Added"));
        col_Valuse.setCellValueFactory(new PropertyValueFactory<>("value_added"));
    }

    @FXML
    void pay(ActionEvent event) {
        Employer_Added_Value employer_added_value=new Employer_Added_Value();
        Em_Added_ValueOpertion em_added_valueOpertion=new Em_Added_ValueOpertion();
        employer_added_value.setId_employer(employer.getId());
        employer_added_value.setDate_Added(EmployerController.txtdatetime);
        employer_added_value.setValue_added(Integer.parseInt(paid.getText()));
        em_added_valueOpertion.insert(employer_added_value);
        closeDialog(cancelButtonUser);
        EmployerController.confirm.setValue(!EmployerController.confirm.getValue());
        Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirmation.setHeaderText(" تم دفع قيمة مسبقة لـ  "+employer.getFirst_name()+" "+employer.getLast_name() +" يوم"+EmployerController.txtdatetime);
        alertConfirmation.setContentText("هل تريد قص هذه القيمة من الكيس");
        Button okButton1 = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.CANCEL);
        okButton1.setText("الغاء");
        Button okButtonj = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.OK);
        okButtonj.setText("موافق");
        Employer finalEmployer = employer;
        alertConfirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.CANCEL) {
                alertConfirmation.close();
            }else if (response == ButtonType.OK){
                MoneyWithdrawalOperationsBDD moneyWithdrawalOperationsBDD=new MoneyWithdrawalOperationsBDD();
                MoneyWithdrawal moneyWithdrawal=new MoneyWithdrawal();
                moneyWithdrawal.setMoneyWithdrawnDA(paid.getText());
                moneyWithdrawal.setMoneyWithdrawn(Double.valueOf(paid.getText()));
                moneyWithdrawal.setUserName(CurrentUser.getEmloyer_name());
                moneyWithdrawal.setDate(new java.sql.Date(millis));
                moneyWithdrawal.setNote("اخذ الموظف "+employer.getLast_name()+" "+employer.getFirst_name()+"قيمة مسبقة من الراتب ");
                moneyWithdrawalOperationsBDD.insert(moneyWithdrawal);

            }});


    }
    private void closeDialog(Button btn) {
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }

}
