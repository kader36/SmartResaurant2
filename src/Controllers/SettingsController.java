package Controllers;

import BddPackage.SettingsOperation;
import Controllers.newKitchenChef.PDFReportGenerators;
import Models.Settings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
    @FXML
    private BorderPane mainPane;

    @FXML
    private TextField txt_name;

    @FXML
    private TextField txt_phone1;

    @FXML
    private TextField txt_phone2;

    @FXML
    private TextField txt_adress;

    @FXML
    private ImageView txt_logo;

    private String pathImage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SettingsOperation settingsOperation=new SettingsOperation();
        Settings settings=settingsOperation.get();
        txt_name.setText(settings.getName());
        txt_phone1.setText(settings.getPhonenamber1());
        txt_phone2.setText(settings.getPhonenamber2());
        txt_adress.setText(settings.getAdress());
        pathImage=settings.getPathImage();
        if(!pathImage.isEmpty()){
            Image image=new Image(pathImage);
            txt_logo.setImage(image);
        }


    }
    @FXML
    void addPicLogo(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("اختر صورة");
        Stage stage = (Stage) mainPane.getScene().getWindow();
        fileChooser.getExtensionFilters().addAll(
                //new FileChooser.ExtensionFilter()
                new FileChooser.ExtensionFilter("JPG", "*.jpg", "*.png", "*.svg")
                //new FileChooser.ExtensionFilter("PNG", "*.png")
        );

        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            pathImage = file.toURI().toString();
            // snapshot the rounded image.
            txt_logo.setImage(image);
        } else {
            txt_logo.setImage(null);
        }
    }
    @FXML
    void Save(ActionEvent event) {
        Settings settings=new Settings();
        SettingsOperation settingsOperation=new SettingsOperation();
        settings.setName(txt_name.getText());
        settings.setPhonenamber1(txt_phone1.getText());
        settings.setPhonenamber2(txt_phone2.getText());
        settings.setAdress(txt_adress.getText());
        settings.setPathImage(pathImage);
        boolean bool=settingsOperation.update(settings,settings);
        if(bool){
            PDFReportGenerators.showNotification(
                    " تحديث المعلومات",
                    "تم تحديث المعلومات بنجاح",
                    1,
                    false);
        }
    }
}
