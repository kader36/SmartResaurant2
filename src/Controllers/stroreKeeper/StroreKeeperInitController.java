package Controllers.stroreKeeper;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StroreKeeperInitController implements Initializable {
    public AnchorPane mainPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void ProviderPage(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/storekeeper/ProviderManagment.fxml"));
            AnchorPane temp = loader.load();
            ProviderOperationController controller = loader.getController();
            controller.Init(mainPane);
            mainPane.getChildren().setAll(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void CategoryProductPage(ActionEvent actionEvent)  {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/storekeeper/CategoryProductMangement.fxml"));
            AnchorPane temp = loader.load();
            CategoryProductController controller = loader.getController();
            controller.Init(mainPane);
            mainPane.getChildren().setAll(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void ProductPage(ActionEvent actionEvent){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/storekeeper/ProductManagemnt.fxml"));
            AnchorPane temp = loader.load();
            ProductOperationController controller = loader.getController();
            controller.Init(mainPane);
            mainPane.getChildren().setAll(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
