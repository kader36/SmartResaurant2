import Controllers.Tables.OrdersServer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class Main extends Application {
    Parent root;
    double xOffset, yOffset;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // lunch the serve thread to listen to new orders from the tablet application.
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd ");
        Date date = new Date(System.currentTimeMillis());
        GregorianCalendar ge=new GregorianCalendar();
        long millis=System.currentTimeMillis();

        System.out.println("1...."+dateFormat.format(date));
        System.out.println("date...."+date.getMonth()+1);
        System.out.println("date...."+ge.getTime());
        Thread serverThread = new Thread(() -> {
            OrdersServer.startListeningToOrders();
        });
        serverThread.start();
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("Views/login.fxml"));
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
    }
        public static void main(String[] args)
        {
            launch(args);


        }

}
