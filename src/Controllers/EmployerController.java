package Controllers;

import BddPackage.EmployerOperation;
import BddPackage.Work_hoursOpertion;
import Models.Employer;
import Models.Work_hours;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EmployerController implements Initializable {

    @FXML
    private  TableView<Employer> employerTableView;
    @FXML
    private TableColumn<Employer,String> col_salary;

    @FXML
    private TableColumn<Employer,String> col_salary_day;

    @FXML
    private TableColumn<Employer, String> col_work_strat;

    @FXML
    private TableColumn<Employer, String> Present;
    @FXML
    private TableColumn<Employer, String> col_name;
    @FXML
    private TableColumn<Employer, String> col_last_name;
    @FXML
    private TableColumn<Employer, String> col_phone_number;
    @FXML
    private TableColumn<Employer, String> col_job;
    @FXML
    private TableColumn<Employer, String> col_work_end;
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
    private TextField txt_salaryDay;
    @FXML
    private DatePicker dateJob;
    @FXML
    private DatePicker dateTime;

    @FXML
    private Label txt_dateTime;
    @FXML
    private VBox vboxOption;
    public  static String txtdatetime;
    double xOffset, yOffset;

    private boolean visible;
    private  ObservableList<Employer> dataTable = FXCollections.observableArrayList();
    private  ArrayList<Employer> list_Elployer = new ArrayList<>();
    private  EmployerOperation employerOperation = new EmployerOperation();
    private ObservableList<String> dataCombo;
    private ValidateController validateController = new ValidateController();
    public static BooleanProperty confirm = new SimpleBooleanProperty();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        confirm.addListener((observableValue, aBoolean, t1) -> {
            refresh();
        });
        vboxOption.setVisible(false);
        txtValidate();
        java.util.Date date = new java.util.Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        txt_dateTime.setText(dateFormat.format(date));
        txtdatetime=txt_dateTime.getText();
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        col_last_name.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        col_phone_number.setCellValueFactory(new PropertyValueFactory<>("phone_number"));
        col_job.setCellValueFactory(new PropertyValueFactory<>("job"));
        col_salary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        col_work_strat.setCellValueFactory(new PropertyValueFactory<>("work_strat"));
        Present.setCellValueFactory(new PropertyValueFactory<>("Present"));
        col_salary_day.setCellValueFactory(new PropertyValueFactory<>("SalaryDay"));
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
            employer.setSalaryDay(Integer.parseInt(txt_salaryDay.getText()));
            employer.setWork_strat(String.valueOf(dateJob.getValue()));
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
    private  void refresh() {
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
        txt_salaryDay.clear();
        dateJob.setValue(null);
    }
    private void txtValidate() {
        validateController.inputTextValueType(txt_name);
        validateController.inputNumberValueType(txt_phone_number);
        validateController.inputTextValueType(txt_last_name);
        validateController.inputTextValueType(txt_job);
        validateController.inputNumberValue(txt_salary);
        validateController.inputNumberValue(txt_salaryDay);
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
            alertWarning.setContentText("يرجى اختيار الموظف المراد تعديل بياناته");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("حسنا");
            alertWarning.showAndWait();
        }
    }
    @FXML
    void Present(ActionEvent event) {
        String at;
        Employer employer=new Employer();
        employer=employerTableView.getSelectionModel().getSelectedItem();
        if (employer != null) {
            Work_hours work_hours=new Work_hours();
            Work_hoursOpertion work_hoursOpertion=new Work_hoursOpertion();
            work_hours.setID_employer(employer.getId());
            work_hours.setDate_work(txt_dateTime.getText());
            work_hours.setAttendance("حاضر");
            if(!work_hoursOpertion.isExist(work_hours)){
                work_hoursOpertion.insert(work_hours);
                Alert alertWarning = new Alert(Alert.AlertType.INFORMATION);
                alertWarning.setHeaderText("تمت العملية بنجاح ");
                alertWarning.setContentText(" "+employer.getLast_name()+" "+employer.getFirst_name()+" تم تسجيل حضوره بنجاح في :"+txt_dateTime.getText());
                Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                okButton.setText("حسنا");
                alertWarning.showAndWait();
                refresh();
            }
            else {
                Work_hours work_hours2=new Work_hours();
                work_hours2=work_hoursOpertion.getwork_hours(work_hours);
                if(work_hours2.getAttendance().equals("حاضر"))
                {
                    at="حضور";
                    Alert alertWarning = new Alert(Alert.AlertType.INFORMATION);
                    alertWarning.setHeaderText("هذا الموطف تم تسجيله اليوم "+txt_dateTime.getText());
                    alertWarning.setContentText(" "+employer.getLast_name()+" "+employer.getFirst_name()+"تم تسجيل حضوره اليوم ");
                    Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                    okButton.setText("حسنا");
                    alertWarning.showAndWait();
                }
                else{
                    at="غياب";
                    Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
                    alertConfirmation.setHeaderText(" لقد تم تسجيل "+at+" "+employer.getFirst_name()+" "+employer.getLast_name() +" "+txt_dateTime.getText()+" اليوم");
                    alertConfirmation.setContentText("هل انت متاكد تريد تسجيل حضور هذا الموظف");
                    Button okButton1 = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.CANCEL);
                    okButton1.setText("الغاء");
                    Button okButtonj = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.OK);
                    okButtonj.setText("موافق");

                    Work_hours finalWork_hours = work_hours2;
                    Employer finalEmployer = employer;
                    alertConfirmation.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.CANCEL) {
                            alertConfirmation.close();
                        }else if (response == ButtonType.OK){
                            work_hoursOpertion.update(work_hours,finalWork_hours);
                            Alert alertWarning = new Alert(Alert.AlertType.INFORMATION);
                            alertWarning.setHeaderText("تمت العملية بنجاح ");
                            alertWarning.setContentText(" "+ finalEmployer.getLast_name()+" "+ finalEmployer.getFirst_name()+"تم تسجيل حضوره بنجاح ");
                            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                            okButton.setText("حسنا");
                            alertWarning.showAndWait();
                            refresh();
                        }
                    });
                }

            }


        } else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("تحذير ");
            alertWarning.setContentText("يرجى اختيار الموظف المراد تسجيل حضوره");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("حسنا");
            alertWarning.showAndWait();
        }
    }

    @FXML
    void absent(ActionEvent event) {
        String at;
        Employer employer=new Employer();
        employer=employerTableView.getSelectionModel().getSelectedItem();
        if (employer != null) {
            Work_hours work_hours=new Work_hours();
            Work_hoursOpertion work_hoursOpertion=new Work_hoursOpertion();
            work_hours.setID_employer(employer.getId());
            work_hours.setDate_work(txt_dateTime.getText());
            work_hours.setAttendance("غائب");
            if(!work_hoursOpertion.isExist(work_hours)){
                work_hoursOpertion.insert(work_hours);
                Alert alertWarning = new Alert(Alert.AlertType.INFORMATION);
                alertWarning.setHeaderText("تمت العملية بنجاح ");
                alertWarning.setContentText(" "+employer.getLast_name()+" "+employer.getFirst_name()+" تم تسجيل غياب بنجاح في :"+txt_dateTime.getText());
                Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                okButton.setText("حسنا");
                alertWarning.showAndWait();
                refresh();
            }
            else {
                Work_hours work_hours2=new Work_hours();
                work_hours2=work_hoursOpertion.getwork_hours(work_hours);
                if(!work_hours2.getAttendance().equals("حاضر"))
                {
                    at="غياب";

                    Alert alertWarning = new Alert(Alert.AlertType.INFORMATION);
                    alertWarning.setHeaderText("هذا الموطف تم تسجيله يوم "+txt_dateTime.getText());
                    alertWarning.setContentText(" "+employer.getLast_name()+" "+employer.getFirst_name()+" تم تسجيل غيابه اليوم ");
                    Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                    okButton.setText("حسنا");
                    alertWarning.showAndWait();
                }
                else{
                    at="حضور";
                    Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
                    alertConfirmation.setHeaderText(" لقد تم تسجيل "+at+" "+employer.getFirst_name()+" "+employer.getLast_name() +" يوم"+txt_dateTime.getText());
                    alertConfirmation.setContentText("هل انت متاكد تريد تسجيل غياب هذا الموظف");
                    Button okButton1 = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.CANCEL);
                    okButton1.setText("الغاء");
                    Button okButtonj = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.OK);
                    okButtonj.setText("موافق");

                    Work_hours finalWork_hours = work_hours2;
                    Employer finalEmployer = employer;
                    alertConfirmation.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.CANCEL) {
                            alertConfirmation.close();
                        }else if (response == ButtonType.OK){
                            work_hoursOpertion.update(work_hours,finalWork_hours);
                            Alert alertWarning = new Alert(Alert.AlertType.INFORMATION);
                            alertWarning.setHeaderText("تمت العملية بنجاح ");
                            alertWarning.setContentText(" "+ finalEmployer.getLast_name()+" "+ finalEmployer.getFirst_name()+"تم تسجيل غيابه بنجاح ");
                            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                            okButton.setText("حسنا");
                            alertWarning.showAndWait();
                            refresh();
                        }
                    });
                }

            }


        } else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("تحذير ");
            alertWarning.setContentText("يرجى اختيار الموظف المراد تسجيل حضوره");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("حسنا");
            alertWarning.showAndWait();
        }

    }
    @FXML
    void dateAction(ActionEvent event) {
        txt_dateTime.setText(String.valueOf(dateTime.getValue()));
        txtdatetime=txt_dateTime.getText();
        refresh();
    }
    @FXML
    void payMonth(ActionEvent event) {
        Employer employer=new Employer();
        employer=employerTableView.getSelectionModel().getSelectedItem();
        if (employer != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/PayEmployer.fxml"));
                DialogPane temp = loader.load();
                payMonthController payDebtController = loader.getController();
                payDebtController.Init(employer);
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setDialogPane(temp);
                dialog.initStyle(StageStyle.UNDECORATED);
                dialog.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("تحذير ");
            alertWarning.setContentText("يرجى اختيار الموظف المراد صب راتبه");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("حسنا");
            alertWarning.showAndWait();
        }

    }

    @FXML
    void printRaport(ActionEvent event) {

    }
    @FXML
    void Added_Value(ActionEvent event) {
        Employer employer=new Employer();
        employer=employerTableView.getSelectionModel().getSelectedItem();
        if (employer != null) {
            Work_hoursOpertion work_hoursOpertion=new Work_hoursOpertion();
            Work_hours work_hours=new Work_hours();
            work_hours.setID_employer(employer.getId());
            work_hours.setDate_work(txtdatetime);
            if(work_hoursOpertion.isExist(work_hours))
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Employer_Added_Value.fxml"));
                    DialogPane temp = loader.load();
                    Employer_Added_ValueController employer_added_valueController = loader.getController();
                    employer_added_valueController.Init(employer);
                    Dialog<ButtonType> dialog = new Dialog<>();
                    dialog.setDialogPane(temp);
                    dialog.initStyle(StageStyle.UNDECORATED);
                    dialog.showAndWait();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            else {
                Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                alertWarning.setHeaderText("تحذير ");
                alertWarning.setContentText("يرجى تسجيل حضور او غياب هذا الموظف اولا");
                Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                okButton.setText("حسنا");
                alertWarning.showAndWait();
            }

        }else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("تحذير ");
            alertWarning.setContentText("يرجى اختيار الموظف المراد صب راتبه");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("حسنا");
            alertWarning.showAndWait();
        }


    }



}
