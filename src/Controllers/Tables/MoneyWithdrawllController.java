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
    // temporary data that will be add to table view but not to data base.
    ObservableList<MoneyWithdrawal> temporaryMoneyWithdrawalOperations = FXCollections.observableArrayList();

    // original data that we get from data base.
    ObservableList<MoneyWithdrawal> originalMoneyWithdrawalOperations = FXCollections.observableArrayList();

    // data that is curently in the table.
    ObservableList<MoneyWithdrawal> tableMoneyWithdrawalOperations = FXCollections.observableArrayList();

    long millis=System.currentTimeMillis();


    // test data.
    //ObservableList<MoneyWithdrawal> moneyWithdrawlOperations = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        txtValidate();
        //set the button on action methods.
        saveButton.setOnAction(actionEvent -> {
            saveOperationsToDatabase();
        });

        quitButton.setOnAction(actionEvent -> {
            clearData();
        });

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
                Alert alertWarning = new Alert(Alert.AlertType.INFORMATION);
                alertWarning.setHeaderText("تاكيد العملية ");
                alertWarning.setContentText("تم اضافة العملية بنجاح ");
                Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                okButton.setText("حسنا");
                alertWarning.showAndWait();
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

        for (int index = 0; index < operationsList.size(); index++) {
            originalMoneyWithdrawalOperations.add(operationsList.get(index));
            tableMoneyWithdrawalOperations.add(operationsList.get(index));
        }

        // set the data that we got to the tabel.
        //checkColumn.setCellValueFactory( new PropertyValueFactory<MoneyWithdrawal,CheckBox>("activeCheckBox"));
        userColumn.setCellValueFactory( new PropertyValueFactory<MoneyWithdrawal,String>("userName"));
        withdrawedMoneyColumn.setCellValueFactory( new PropertyValueFactory<MoneyWithdrawal,String>("moneyWithdrawnDA"));
        noteColumn.setCellValueFactory( new PropertyValueFactory<MoneyWithdrawal,String>("note"));
        dateColumn.setCellValueFactory( new PropertyValueFactory<MoneyWithdrawal,Date>("date"));
        monywithdrawlOperationsTableView.setItems(originalMoneyWithdrawalOperations);
        
    }


    // saveData method.

    void saveOperationsToDatabase(){

        // add the new operations to the database.

        // clear the list to not do a duplication later.
        temporaryMoneyWithdrawalOperations.clear();
        // set the new original data.
        monywithdrawlOperationsTableView.setItems(originalMoneyWithdrawalOperations);

    }


    // quit data method.
    void clearData(){
        temporaryMoneyWithdrawalOperations.clear();
        tableMoneyWithdrawalOperations.clear();
        // delete the temp from the table view.
        for (int index = 0; index < temporaryMoneyWithdrawalOperations.size(); index++) {
            tableMoneyWithdrawalOperations.remove(temporaryMoneyWithdrawalOperations.get(index));
        }
        for (int index = 0; index < originalMoneyWithdrawalOperations.size(); index++) {
            tableMoneyWithdrawalOperations.add(originalMoneyWithdrawalOperations.get(index));
        }
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
        temporaryMoneyWithdrawalOperations.add(
                new MoneyWithdrawal( CurrentUser.getEmloyer_name(),moneySum,new java.sql.Date(millis) ,note)
        );
        // add it to the table
        tableMoneyWithdrawalOperations.add(
                new MoneyWithdrawal(CurrentUser.getEmloyer_name(),moneySum,new java.sql.Date(millis) ,note)
        );

        monywithdrawlOperationsTableView.setItems(tableMoneyWithdrawalOperations);
    }



    // remove a money withdrawal operation.
    void removeOperation(){

        MoneyWithdrawalOperationsBDD databaseConnector = new MoneyWithdrawalOperationsBDD();
        for (int index = 0; index < tableMoneyWithdrawalOperations.size(); index++) {
            if (tableMoneyWithdrawalOperations.get(index).getActiveCheckBox().isSelected() == true){
                // delete in the database.
                databaseConnector.delete(tableMoneyWithdrawalOperations.get(index));
                // delete in the lists.
                originalMoneyWithdrawalOperations.remove(
                        tableMoneyWithdrawalOperations.get(index)
                );
                temporaryMoneyWithdrawalOperations.remove(
                        tableMoneyWithdrawalOperations.get(index)
                );
                tableMoneyWithdrawalOperations.remove(
                        tableMoneyWithdrawalOperations.get(index)
                );

            }
        }
    }

    private void txtValidate() {
        ValidateController validateController=new ValidateController();
        validateController.inputNumberValue(withdrawedMoneyTextField);

    }

}
