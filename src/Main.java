import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        System.setProperty("prism.text","t2k");
        System.setProperty("prism.lcdtext","false");
        //Views/storekeeper/StoreManagement.fxml this for add product and product category
        //Views/KitchenChef/FoodMainController.fxml this for add food and food product
        Parent root = FXMLLoader.load(getClass().getResource("Views/KitchenChef/FoodMainController.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
