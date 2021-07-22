package Controllers;

import BddPackage.EmployerOperation;
import BddPackage.UserOperation;
import Models.Employer;
import Models.User;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdduserController implements Initializable {
    @FXML
    private JFXButton cancelButtonUser;
    @FXML
    private TextField txt_username;
    @FXML
    private PasswordField txt_password;
    @FXML
    private PasswordField txt_passwordconfirm;
    @FXML
    private ComboBox txt_type;
    @FXML
    private ComboBox txt_employe;
    private javafx.collections.ObservableList<String> dataCombo;
    private int idemployer;

    public void initialize(URL location, ResourceBundle resources) {
        cancelButtonUser.setOnAction(actionEvent -> {
            closeDialog(cancelButtonUser);
        });

        txt_type.getItems().addAll("مدير","محاسب","طباخ");
        chargeListEmployer();


    }
    private void closeDialog(Button btn) {
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }
    @FXML
    void clickedMouse(MouseEvent event) {
        closeDialog(cancelButtonUser);
    }
    @FXML
    void insertUser(ActionEvent event) {
        if(!txt_username.getText().matches("[a-zA-Z0-9]{4}")){
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("تحذير ");
            alertWarning.setContentText("يرجى إختيار اسم مستخدم صالح");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("حسنا");
            alertWarning.showAndWait();
        }else{
            if(txt_password.getText().isEmpty()){
                Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                alertWarning.setHeaderText("تحذير ");
                alertWarning.setContentText("يرجى ملئ حقل كلمة المرور");
                Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                okButton.setText("حسنا");
                alertWarning.showAndWait();
            }else{
                if(!txt_password.getText().equals(txt_passwordconfirm.getText())){
                    Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                    alertWarning.setHeaderText("تحذير ");
                    alertWarning.setContentText("كلمة المرور غير متطابقتين");
                    Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                    okButton.setText("حسنا");
                    alertWarning.showAndWait();
                }else{
                    if(txt_type.getSelectionModel().isEmpty()){
                        Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                        alertWarning.setHeaderText("تحذير ");
                        alertWarning.setContentText("يرجى تحديد دور المستخدم");
                        Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                        okButton.setText("حسنا");
                        alertWarning.showAndWait();
                    }else {
                        if(txt_employe.getSelectionModel().isEmpty()){
                            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                            alertWarning.setHeaderText("تحذير ");
                            alertWarning.setContentText("يرجى تحديد اسم الموظف المراد تحدده");
                            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                            okButton.setText("حسنا");
                            alertWarning.showAndWait();
                        }else{
                            idemployer=getIdemployer(txt_employe.getSelectionModel().getSelectedItem().toString());
                            UserOperation userOperation=new UserOperation();
                            User user=new User();
                            user.setId_emloyer(idemployer);
                            user.setUserName(txt_username.getText());
                            user.setPassWord(txt_password.getText());
                            user.setType(txt_type.getSelectionModel().getSelectedItem().toString());
                            if(!userOperation.isExist(user)){
                                boolean bool=userOperation.insert(user);
                                if(bool)
                                    closeDialog(cancelButtonUser);
                                else
                                    System.out.println("eroooooooooooooooooor");
                            }else{
                                Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                                alertWarning.setHeaderText("تحذير ");
                                alertWarning.setContentText("الاسم المستخدم هذا موجود");
                                Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                                okButton.setText("حسنا");
                                alertWarning.showAndWait();
                            }
                        }

                    }
                }
            }
        }

    }
    private void chargeListEmployer() {
        dataCombo = FXCollections.observableArrayList();

        EmployerOperation employerOperation=new EmployerOperation();
        ArrayList<Employer> listEmployer= employerOperation.getAll();
        for (Employer listEmployerFromDB : listEmployer) {
            dataCombo.add(listEmployerFromDB.getLast_nameANDfirst_name());

        }
        txt_employe.setItems(dataCombo);

    }
    private int getIdemployer(String employer){
        int id=-1;
        EmployerOperation employerOperation=new EmployerOperation();
        ArrayList<Employer> listEmployer= employerOperation.getAll();
        for (int i=0;i<listEmployer.size();i++) {
           if(listEmployer.get(i).getLast_nameANDfirst_name().equals(employer)){
               id=listEmployer.get(i).getId();
               break;
           }
        }
        System.out.println("id="+id);
        return id;
    }



}
