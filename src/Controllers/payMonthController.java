package Controllers;

import BddPackage.EmployerOperation;
import BddPackage.MoneyWithdrawalOperationsBDD;
import BddPackage.Work_hoursOpertion;
import Models.CurrentUser;
import Models.Employer;
import Models.MoneyWithdrawal;
import Models.Work_hours;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class payMonthController implements Initializable {
    long millis=System.currentTimeMillis();
    @FXML
    private AnchorPane mainPane;
    @FXML
    private DatePicker date1;

    @FXML
    private DatePicker date2;
    @FXML
    private Label number;

    @FXML
    private TableView<Work_hours> Tableproduct;

    @FXML
    private TableColumn<Work_hours, String> col_Date;

    @FXML
    private TableColumn<Work_hours, String> col_Prsent;

    @FXML
    private TableColumn<Work_hours, Integer> col_Valuse;

    @FXML
    private TableColumn<Work_hours, Integer> col_payMonth;

    @FXML
    private Label Total;

    @FXML
    private Label rest;

    @FXML
    private TextField paid;
    @FXML
    private Label num_present;

    @FXML
    private Label num_dayF;
    @FXML
    private Label toto;
    private Employer employer;
    int num=0,hader = 0,ghaib = 0,total;
    String s;


    @FXML
    private JFXButton cancelButtonUser;
    private ObservableList<Work_hours> dataTable = FXCollections.observableArrayList();
    ArrayList<Work_hours> list=new ArrayList<>();

    public void initialize(URL location, ResourceBundle resources) {
        cancelButtonUser.setOnAction(actionEvent -> {
            closeDialog(cancelButtonUser);
        });
    }
    public void Init(Employer employer){
        this.employer=employer;
        charge();
        number.setText(employer.getFirst_name()+" "+employer.getLast_name());
        dataTable.setAll(list);
        Tableproduct.setItems(dataTable);
        col_Date.setCellValueFactory(new PropertyValueFactory<>("Date_work"));
        col_Prsent.setCellValueFactory(new PropertyValueFactory<>("Attendance"));
        col_Valuse.setCellValueFactory(new PropertyValueFactory<>("Added_Value"));
        col_payMonth.setCellValueFactory(new PropertyValueFactory<>("Pay"));
        for(int i=0;i<list.size();i++)
        {
            num+=list.get(i).getAdded_Value();
           s=list.get(i).getAttendance();
           if(s.equals("حاضر"))
               hader++;
           else if (s.equals("غائب"))
               ghaib++;
           
        }
        total=employer.getSalaryDay()*hader-num;

        Total.setText(String.valueOf(num));
        num_present.setText(String.valueOf(hader));
        num_dayF.setText(String.valueOf(ghaib));
        rest.setText(String.valueOf(employer.getSalaryDay()*hader));
        toto.setText(String.valueOf(total));
    }
    void charge(){
        for(int i=0;i<employer.getWork_hours_ArrayList().size();i++){
            System.out.println(employer.getWork_hours_ArrayList().get(i).getPayNow());
            if(employer.getWork_hours_ArrayList().get(i).getPayNow()==null){
                list.add(employer.getWork_hours_ArrayList().get(i));
            }else {
                EmployerOperation employerOperation=new EmployerOperation();
                break;
            }
        }

    }

    private void closeDialog(Button btn) {
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void pay(ActionEvent event) {
        Work_hours work_hours=new Work_hours();
        Work_hoursOpertion work_hoursOpertion=new Work_hoursOpertion();
        work_hours.setPayNow("true");
        work_hours.setValuer(total);
        work_hours.setID_employer(employer.getId());
        work_hours.setDate_work(EmployerController.txtdatetime);
        Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirmation.setHeaderText("المبلغ الاجمالي لرتب :"+String.valueOf(total)+" دج");
        alertConfirmation.setContentText("هل انت متاكد من صب راتب هذا الموظف  ");
        Button okButton1 = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.CANCEL);
        okButton1.setText("الغاء");
        Button okButtonj = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.OK);
        okButtonj.setText("موافق");

        Employer finalEmployer = employer;
        alertConfirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.CANCEL) {
                alertConfirmation.close();
            }else if (response == ButtonType.OK){
                work_hoursOpertion.update(work_hours);
                list.clear();
               EmployerController.confirm.setValue(!EmployerController.confirm.getValue());
                closeDialog(cancelButtonUser);
            }
        });
        MoneyWithdrawalOperationsBDD moneyWithdrawalOperationsBDD=new MoneyWithdrawalOperationsBDD();
        MoneyWithdrawal moneyWithdrawal=new MoneyWithdrawal();
        moneyWithdrawal.setMoneyWithdrawnDA(String.valueOf(total));
        moneyWithdrawal.setMoneyWithdrawn(Double.valueOf(total));
        moneyWithdrawal.setUserName(CurrentUser.getEmloyer_name());
        moneyWithdrawal.setDate(new java.sql.Date(millis));
        moneyWithdrawal.setNote("راتب الموظف "+employer.getLast_name()+" "+employer.getFirst_name()+" ");
        moneyWithdrawalOperationsBDD.insert(moneyWithdrawal);
    }
    @FXML
    void dateAction(ActionEvent event) {
        Work_hoursOpertion work_hoursOpertion=new Work_hoursOpertion();
        String dat1=String.valueOf(date1.getValue());
        System.out.println(dat1);
        String dat2=String.valueOf(date2.getValue());
        System.out.println(dat2);
        if(!dat1.isEmpty()||!dat2.isEmpty()){
            list.clear();
            list=work_hoursOpertion.getAllWorkdate(dat1,dat2,employer.getId());
            clear();
        }
        else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("تحذير ");
            alertWarning.setContentText(" يرجئ تديد التاريخين ");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("حسنا");
            alertWarning.showAndWait();

        }

    }
    void clear(){
        charge();
        dataTable.setAll(list);
        Tableproduct.setItems(dataTable);
    }
}
