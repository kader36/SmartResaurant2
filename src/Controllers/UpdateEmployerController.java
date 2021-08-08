package Controllers;

import BddPackage.EmployerOperation;
import Models.Employer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class UpdateEmployerController {
    @FXML
    private TextField txt_name_upd;

    @FXML
    private TextField txt_prenom_upd;

    @FXML
    private TextField txt_phoneNumber_upd;

    @FXML
    private TextField txt_job_upd;

    @FXML
    private TextField txt_ratib_upd;
    @FXML
    private Label errUpdateEmploy;


    @FXML
    private Button updateButton;
    private  Employer employer;
    public void chargeTxt(Employer oldEmployer) {
        txt_name_upd.setText(oldEmployer.getFirst_name());
        txt_prenom_upd.setText(oldEmployer.getLast_name());
        txt_phoneNumber_upd.setText(oldEmployer.getPhone_number());
        txt_job_upd.setText(oldEmployer.getJob());
        txt_ratib_upd.setText(String.valueOf(oldEmployer.getSalary()));
        this.employer = oldEmployer;
    }
    @FXML
    void closeUpdateProduct(MouseEvent event) {
        closeDialog(updateButton);
    }
    private void closeDialog(Button btn) {
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }
    @FXML
    void updateProductAction(ActionEvent event) {
         if (!txt_name_upd.getText().isEmpty() && !txt_prenom_upd.getText().isEmpty()
             && !txt_phoneNumber_upd.getText().isEmpty() && !txt_job_upd.getText().isEmpty() && !txt_ratib_upd.getText().isEmpty()) {

         Employer updEmployer = new Employer();

         updEmployer.setFirst_name(txt_name_upd.getText());
         updEmployer.setLast_name(txt_prenom_upd.getText());
         updEmployer.setPhone_number(txt_phoneNumber_upd.getText());
         updEmployer.setJob(txt_job_upd.getText());
         updEmployer.setSalary(Integer.parseInt(txt_ratib_upd.getText()));
             EmployerOperation employerOperation=new EmployerOperation();
             employerOperation.update(updEmployer, employer);
        Alert alertWarning = new Alert(Alert.AlertType.INFORMATION);
        alertWarning.setHeaderText("تأكيد ");
        alertWarning.setContentText("تم تعديل السلعة بنجاح");
        Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setText("حسنا");
        alertWarning.showAndWait();
        closeDialog(updateButton);
         } else {
         errUpdateEmploy.setText("الرجاء ملأ جميع الحقول");
         }
    }
}
