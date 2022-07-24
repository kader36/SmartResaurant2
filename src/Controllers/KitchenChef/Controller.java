package Controllers.KitchenChef;

import Controllers.Tables.OrdersServer;
import Controllers.stroreKeeper.BillListController;
import Models.CurrentUser;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private AnchorPane mainPane;

    @FXML
    private ImageView logo,userVector;
    @FXML
    private Label userName;
    @FXML
    private Label Type;
    @FXML
    private Button btn_clos;
    Parent root = null;
    double xOffset, yOffset;

    /**
     * load screen controller
     * */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userName.setText(CurrentUser.getEmloyer_name());
        Type.setText(CurrentUser.getType());
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/foods.fxml"));
            BorderPane temp = loader.load();
            temp.setMaxHeight(mainPane.getMaxHeight());
            temp.setMaxWidth(mainPane.getMaxWidth());
            FoodsController foodsController = loader.getController();
            foodsController.Init(temp);
            mainPane.getChildren().setAll(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void LaunchProviderScreen(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/AddFactoryBuy.fxml"));
            BorderPane temp = loader.load();
            mainPane.getChildren().setAll(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void goToFirst(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/FirstScreen.fxml"));
            AnchorPane temp = loader.load();
            temp.setMaxHeight(mainPane.getMaxHeight());
            mainPane.getChildren().setAll(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void loadBillScreen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Factories.fxml"));

            BorderPane temp = loader.load();
            BillListController billController = loader.getController();
            billController.Init(temp);
            mainPane.getChildren().setAll(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void loadAvailableItemScreen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/KitchenChef/availableFoods.fxml"));
            BorderPane temp = loader.load();
            temp.setMaxHeight(mainPane.getMaxHeight());
            temp.setMaxWidth(mainPane.getMaxWidth());
            mainPane.getChildren().setAll(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void loadFoodScreen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/foods.fxml"));
            BorderPane temp = loader.load();
            FoodsController foodsController = loader.getController();
            foodsController.Init(temp);
            temp.setMaxHeight(mainPane.getMaxHeight());
            temp.setMaxWidth(mainPane.getMaxWidth());
            mainPane.getChildren().setAll(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void ProductCompositeScrenn(ActionEvent event){
            try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/KitchenChef/ProductComposite.fxml"));
        BorderPane temp = loader.load();
                temp.setMaxHeight(mainPane.getMaxHeight());
                temp.setMaxWidth(mainPane.getMaxWidth());
        mainPane.getChildren().setAll(temp);
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    @FXML
    void OrderScreen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/KitchenChef/kitchenOrdersView.fxml"));
            BorderPane temp = loader.load();
            temp.setMaxHeight(mainPane.getMaxHeight());
            temp.setMaxWidth(mainPane.getMaxWidth());
            mainPane.getChildren().setAll(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void close(ActionEvent event) {
        Stage stage = (Stage) btn_clos.getScene().getWindow();
        stage.close();
        try {
            OrdersServer.serverSocket.close();
            System.out.println("socket close");
        } catch (IOException ex) {
            ex.printStackTrace();
        }


        Stage primaryStage =new Stage();
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("Views/login.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image image =new Image("/Images/logo.png");
        primaryStage.getIcons().add(image);
        Scene scene = new Scene(root);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();
        scene.setFill(Color.TRANSPARENT);
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
    @FXML
    void home(MouseEvent event) {
        Stage stage = (Stage) btn_clos.getScene().getWindow();
        stage.close();
        Stage primaryStage =new Stage();
        Parent root = null;
        try {
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
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    @FXML
    void min(ActionEvent event) {
        Stage stage = (Stage) btn_clos.getScene().getWindow();
        stage.toBack();
    }

}
