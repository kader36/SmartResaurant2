package Controllers;

import BddPackage.UserOperation;
import Controllers.Tables.OrdersServer;
import Models.CurrentUser;
import Models.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
    double xOffset, yOffset;

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
                if(CurrentUser.getType().equals("مدير")){
                    root = FXMLLoader.load(getClass().getClassLoader().getResource("Views/MainScreen.fxml"));
                    primaryStage.setTitle("برنامج ادارة المطاعم");
                    root.getStylesheets().add("Style.css");
                    Image image =new Image("/Images/logo.png");
                    primaryStage.getIcons().add(image);
                    primaryStage.setScene(new Scene(root));
                    primaryStage.resizableProperty().setValue(false);
                    primaryStage.setMaximized(true);
                    primaryStage.initStyle(StageStyle.TRANSPARENT);
                    primaryStage.show();
                    root.setOnMousePressed(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            xOffset = event.getSceneX();
                            yOffset = event.getSceneY();
                        }
                    });
                    root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            primaryStage.setX(event.getScreenX() - xOffset);
                            primaryStage.setY(event.getScreenY() - yOffset);
                        }
                    });
                }else {if(CurrentUser.getType().equals("محاسب")){
                    root = FXMLLoader.load(getClass().getClassLoader().getResource("Views/MainScreenAccountant.fxml"));
                    primaryStage.setTitle("برنامج ادارة المطاعم");
                    root.getStylesheets().add("Style.css");
                    Image image =new Image("/Images/logo.png");
                    primaryStage.getIcons().add(image);
                    primaryStage.setScene(new Scene(root));
                    primaryStage.resizableProperty().setValue(false);
                    primaryStage.setMaximized(true);
                    primaryStage.initStyle(StageStyle.TRANSPARENT);
                    primaryStage.show();
                    root.setOnMousePressed(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            xOffset = event.getSceneX();
                            yOffset = event.getSceneY();
                        }
                    });
                    root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            primaryStage.setX(event.getScreenX() - xOffset);
                            primaryStage.setY(event.getScreenY() - yOffset);
                        }
                    });

                }else {
                    root = FXMLLoader.load(getClass().getClassLoader().getResource("Views/MainScreenKitchenChef.fxml"));
                    primaryStage.setTitle("برنامج ادارة المطاعم");
                    root.getStylesheets().add("Style.css");
                    Image image =new Image("/Images/logo.png");
                    primaryStage.getIcons().add(image);
                    primaryStage.setScene(new Scene(root));
                    primaryStage.resizableProperty().setValue(false);
                    primaryStage.setMaximized(true);
                    primaryStage.initStyle(StageStyle.TRANSPARENT);
                    primaryStage.show();
                    root.setOnMousePressed(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            xOffset = event.getSceneX();
                            yOffset = event.getSceneY();
                        }
                    });
                    root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            primaryStage.setX(event.getScreenX() - xOffset);
                            primaryStage.setY(event.getScreenY() - yOffset);
                        }
                    });

                }
                }
                Thread serverThread = new Thread(() -> {
                    OrdersServer.startListeningToOrders();

                });
                serverThread.start();

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
