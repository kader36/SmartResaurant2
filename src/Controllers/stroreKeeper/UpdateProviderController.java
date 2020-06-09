package Controllers.stroreKeeper;


import BddPackage.ProviderOperation;

import Controllers.ValidateController;
import Models.Provider;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class UpdateProviderController implements Initializable {
    @FXML
    private JFXButton closeUpdateButton;

    @FXML
    private JFXTextField txt_first_name;

    @FXML
    private JFXTextField txt_last_name;

    @FXML
    private JFXTextField txt_phone_number;

    @FXML
    private JFXTextField txt_email;

    @FXML
    private JFXTextField txt_adress;

    @FXML
    public AnchorPane pane;

    private Provider oldProvider;
    public AnchorPane mainPane;




    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ValidateController validateController = new ValidateController();
        validateController.inputNumberValueType(txt_phone_number);
        validateController.inputTextValueType(txt_first_name);
        validateController.inputTextValueType(txt_last_name);


    }


    public void Init(Provider provider){
        this.oldProvider = provider;
        txt_first_name.setText(oldProvider.getFirst_name());
        txt_last_name.setText(oldProvider.getLast_name());
        txt_phone_number.setText(oldProvider.getPhone_number());
        txt_email.setText(oldProvider.getEmail());
        txt_adress.setText(oldProvider.getAdress());
    }
    public void close(ActionEvent actionEvent) {
        Stage stage = (Stage) closeUpdateButton.getScene().getWindow();
        stage.close();

       /* ProviderOperationController controller = new ProviderOperationController();
        controller.test();*/
    }
    public void updateProvider(){
        ProviderOperation providerOperation = new ProviderOperation();
        if (!txt_first_name.equals("") && !txt_last_name.equals("") && !txt_phone_number.equals("") && !txt_email.equals("") && !txt_adress.equals("")){
            if (validatEmail(txt_email.getText()) && validatePhoneNumber(txt_phone_number.getText())) {
                Provider newprovider = new Provider();
                oldProvider.setId(oldProvider.getId()); // id provider for change
                newprovider.setFirst_name(txt_first_name.getText());
                newprovider.setLast_name(txt_last_name.getText());
                newprovider.setPhone_number(txt_phone_number.getText());
                newprovider.setEmail(txt_email.getText());
                newprovider.setAdress(txt_adress.getText());
                providerOperation.update(newprovider,oldProvider);
                txtVide();
                Stage stage = (Stage) closeUpdateButton.getScene().getWindow();
                stage.close();


            }
        }
    }
    private void txtVide() {
        ProductOperationController.Vide(txt_adress, txt_email, txt_first_name, txt_last_name, txt_phone_number);
    }
    private boolean validatEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@gmail.com";
        Pattern pat = Pattern.compile(emailRegex);
        return pat.matcher(email).matches();
    }
    private boolean validatePhoneNumber(String number){
        String numberRegex = "(0)?[5-7][0-9]{8}";
        Pattern pat = Pattern.compile(numberRegex);
        return (pat.matcher(number).matches());
    }


}
