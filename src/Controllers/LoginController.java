package Controllers;

import BddPackage.UserOperation;
import Models.CurrentUser;
import Models.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class LoginController {
    @FXML
    private JFXButton btnClose;
    @FXML
    private JFXButton btnSignIn;
    @FXML
    private JFXTextField inpUser;
    @FXML
    private JFXPasswordField inpPassword;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private Label error;

    @FXML
    void Login(ActionEvent event){
        error.setVisible(false);
        User user=new User();
        user.setUserName(inpUser.getText());
        user.setPassWord(inpPassword.getText());
        UserOperation userOperation=new UserOperation();
        boolean userexist=userOperation.getUser(user);
        ArrayList<User> list=userOperation.getAll();
        if(userexist==true){
            Stage primaryStage =new Stage();
            Parent root = null;
            for(int i=0;i< list.size();i++){
                if(list.get(i).getUserName().equals(user.getUserName())){
                    CurrentUser.setUserName(list.get(i).getUserName());
                    CurrentUser.setEmloyer_name(list.get(i).getEmloyer_name());
                    CurrentUser.setType(list.get(i).getType());
                }
            }
            UserOperation userOperation1=new UserOperation();
            User user1=new User();
            user=userOperation1.getUserParUser(CurrentUser.getUserName());
            CurrentUser.setId(user.getId());


            try {
                root = FXMLLoader.load(getClass().getResource("../Views/MainScreen.fxml"));
                primaryStage.setTitle("برنامج ادارة المطاعم");
                root.getStylesheets().add("Style.css");
                primaryStage.setScene(new Scene(root));
                primaryStage.setMaximized(true);
                primaryStage.show();
                CurrentUser.getUserName();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage) btnClose.getScene().getWindow();
            stage.close();

        }else
        {
            error.setVisible(true);
        }
    }

    @FXML
    void close(ActionEvent event){
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();

    }



}
