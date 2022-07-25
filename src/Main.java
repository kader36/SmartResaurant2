import BddPackage.SerialOperation;
import Controllers.Key;
import Controllers.Tables.OrdersServer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main extends Application {
    Parent root;
    double xOffset, yOffset;

    @Override
    public void start(Stage primaryStage) throws Exception {
        SerialOperation serialOperation=new SerialOperation();
       if(serialOperation.checking()==-1){
           try {
               File myObj = new File("C:/Users/" + System.getProperty("user.name") + "/Desktop/codeAdress.txt");
               if (myObj.createNewFile()) {
                   System.out.println("File created: " + myObj.getName());
                   FileWriter myWriter = new FileWriter("C:/Users/" + System.getProperty("user.name") + "/Desktop/codeAdress.txt");
                   myWriter.write(Key.printCpu());
                   myWriter.close();
               } else {
                   System.out.println("File already exists.");
               }

           } catch (IOException e) {
               throw new RuntimeException(e);
           }
       }else {
           try {
               root = FXMLLoader.load(getClass().getClassLoader().getResource("Views/login.fxml"));
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

           } catch (Exception e) {
               e.printStackTrace();
           }
           primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
               @Override
               public void handle(WindowEvent e) {

                   try {
                       OrdersServer.serverSocket.close();
                       System.out.println("socket close");
                   } catch (IOException ex) {
                       ex.printStackTrace();
                   }
                   Platform.exit();
                   System.exit(0);
               }
           });
       }

        // lunch the serve thread to listen to new orders from the tablet application.

    }
        public static void main(String[] args)
        {

            launch(args);


        }

}
