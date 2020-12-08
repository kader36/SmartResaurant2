import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.lang.reflect.Field;
import java.nio.charset.Charset;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
     /*   System.setProperty("prism.text","t2k");
        System.setProperty("prism.lcdtext","false");
      */  //System.setProperty("file.encoding", "UTF-8");

        Parent root = FXMLLoader.load(getClass().getResource("Views/MainScreen.fxml"));
        primaryStage.setTitle("Hello World");
        root.getStylesheets().add("Style.css");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
