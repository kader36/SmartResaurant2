package Controllers.Tables;

import BddPackage.MoneyWithdrawalOperationsBDD;
import Controllers.ValidateController;
import Models.CurrentUser;
import Models.MoneyWithdrawal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MoneyWithdrawllController implements Initializable {


    //fxml elements.
    @FXML
    private Button removeButton;

    @FXML
    private TextField withdrawedMoneyTextField;

    @FXML
    private Button withdrawlMoneybutton;

    @FXML
    private TextField noteTextField;

    @FXML
    private Button quitButton;

    @FXML
    private Button saveButton;

    @FXML
    private TableView<MoneyWithdrawal> monywithdrawlOperationsTableView;

    @FXML
    private TableColumn<MoneyWithdrawal, String> id;

    @FXML
    private TableColumn<MoneyWithdrawal, CheckBox> checkColumn;

    @FXML
    private TableColumn<MoneyWithdrawal, String> userColumn;

    @FXML
    private TableColumn<MoneyWithdrawal, String> withdrawedMoneyColumn;

    @FXML
    private TableColumn<MoneyWithdrawal, String> noteColumn;

    @FXML
    private TableColumn<MoneyWithdrawal, Date> dateColumn;


    //variables.


    // original data that we get from data base.
    ObservableList<MoneyWithdrawal> originalMoneyWithdrawalOperations = FXCollections.observableArrayList();


    long millis=System.currentTimeMillis();


    // test data.
    //ObservableList<MoneyWithdrawal> moneyWithdrawlOperations = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        txtValidate();
        //set the button on action methods.




        removeButton.setOnAction(actionEvent -> {
            removeOperation();
        });

        withdrawlMoneybutton.setOnAction(actionEvent -> {
            if(!withdrawedMoneyTextField.getText().isEmpty() && !noteTextField.getText().isEmpty()){
                addMoenyWithdrawlOperation(
                        Double.parseDouble(withdrawedMoneyTextField.getText()),
                        noteTextField.getText(),
                        "user"
                );
            }else{
                Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                alertWarning.setHeaderText("تحذير ");
                alertWarning.setContentText("يرجى ملئ جميع الحقول");
                Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                okButton.setText("حسنا");
                alertWarning.showAndWait();
            }

            withdrawedMoneyTextField.clear();
            noteTextField.clear();
        });


        // get the table view data.
        // get the data from database and add it to the original data.
        MoneyWithdrawalOperationsBDD databaseConenctor = new MoneyWithdrawalOperationsBDD();
        ArrayList<MoneyWithdrawal> operationsList = databaseConenctor.getAll();
            originalMoneyWithdrawalOperations.addAll(operationsList);


        // set the data that we got to the tabel.
        //checkColumn.setCellValueFactory( new PropertyValueFactory<MoneyWithdrawal,CheckBox>("activeCheckBox"));
        userColumn.setCellValueFactory( new PropertyValueFactory<MoneyWithdrawal,String>("userName"));
        withdrawedMoneyColumn.setCellValueFactory( new PropertyValueFactory<MoneyWithdrawal,String>("moneyWithdrawnDA"));
        noteColumn.setCellValueFactory( new PropertyValueFactory<MoneyWithdrawal,String>("note"));
        dateColumn.setCellValueFactory( new PropertyValueFactory<MoneyWithdrawal,Date>("date"));
        id.setCellValueFactory( new PropertyValueFactory<MoneyWithdrawal,String>("databaseID"));
        monywithdrawlOperationsTableView.setItems(originalMoneyWithdrawalOperations);
        
    }





    // quit data method.
    void clearData(){
        ArrayList list=new ArrayList();
        MoneyWithdrawalOperationsBDD moneyWithdrawalOperationsBDD=new MoneyWithdrawalOperationsBDD();
        list=moneyWithdrawalOperationsBDD.getAll();
        originalMoneyWithdrawalOperations.setAll(list);
        monywithdrawlOperationsTableView.setItems(originalMoneyWithdrawalOperations);

    }


    // withdrawal money method.
    void addMoenyWithdrawlOperation(double moneySum, String note, String user ){

        // add it to the temporary.
        MoneyWithdrawalOperationsBDD databaseConenctor = new MoneyWithdrawalOperationsBDD();
        MoneyWithdrawal moneyWithdrawal=new MoneyWithdrawal();
        moneyWithdrawal.setMoneyWithdrawn(moneySum);
        moneyWithdrawal.setDate(new java.sql.Date(millis));
        moneyWithdrawal.setUserName(CurrentUser.getEmloyer_name());
        moneyWithdrawal.setNote(note);
        databaseConenctor.insert(moneyWithdrawal);

        clearData();
    }



    // remove a money withdrawal operation.
    void removeOperation(){
        Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirmation.setHeaderText("تأكيد الحفظ");
        alertConfirmation.setContentText("هل انت متأكد من عملية حفظ الوجبة");
        Button okButton = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setText("موافق");

        Button cancel = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.CANCEL);
        cancel.setText("الغاء");

        alertConfirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.CANCEL) {
                alertConfirmation.close();
            } else if (response == ButtonType.OK) {
                MoneyWithdrawal moneyWithdrawal = monywithdrawlOperationsTableView.getSelectionModel().getSelectedItem();
                MoneyWithdrawalOperationsBDD databaseConnector = new MoneyWithdrawalOperationsBDD();
                databaseConnector.delete(moneyWithdrawal);
                // delete in the lists.
            }});
        clearData();

    }

    private void txtValidate() {
        ValidateController validateController=new ValidateController();
        validateController.inputNumberValue(withdrawedMoneyTextField);

    }

}
