package Controllers.stroreKeeper;

import BddPackage.ProviderOperation;
import Models.Provider;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProviderOperationController implements Initializable {

    @FXML
    private AnchorPane mainpane;

    @FXML
    public TableView<Provider> table;


    @FXML
    public TableColumn<Provider, Integer> col_id;

    @FXML
    public TableColumn<Provider, String> col_first_name;

    @FXML
    public TableColumn<Provider, String> col_last_name;

    @FXML
    public TableColumn<Provider, Integer> col_phone_number;

    @FXML
    public TableColumn<Provider, String> col_email;

    @FXML
    public TableColumn<Provider, String> col_adress;

    @FXML
    private JFXButton insertbutton;

    @FXML
    private JFXButton updatebutton;

    @FXML
    private JFXButton deletebutton;


    private ObservableList<Provider> data_Table= FXCollections.observableArrayList();
    private Provider provider;
    private ProviderOperation providerOperation ;
    private ArrayList<Provider> list_Provider;
    /*
     * private int id;
    private String first_name;
    private String last_name;
    private int  phone_number;
    private String email;
    private String adress;*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        providerOperation  = new ProviderOperation();
        list_Provider = providerOperation.getAll();
        data_Table.setAll(list_Provider);
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_first_name.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        col_last_name.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        col_phone_number.setCellValueFactory(new PropertyValueFactory<>("phone_number"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_adress.setCellValueFactory(new PropertyValueFactory<>("adress"));
        table.setItems(data_Table);
       refrech();

    }
    public void Init(AnchorPane mainPane){
        this.mainpane = mainPane;
        refrech();

    }

    public void close(ActionEvent actionEvent) {
    }



    public void insertAction(ActionEvent actionEvent) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Views/storekeeper/InsertProvider.fxml"));
            Stage primaryStage = new Stage();
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateAction(ActionEvent actionEvent) {
        Provider provider  ;
        provider = table.getSelectionModel().getSelectedItem();
        System.out.println(provider.getAdress());
        if (null == provider) System.out.println("null");
        else{
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Views/storekeeper/updateProvider.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.getMessage();
            }
            UpdateProviderController controller = loader.getController();
            controller.Init(provider);
            Parent parent = loader.getRoot();
            stage.setScene(new Scene(parent));
            stage.show();
        }

    }

    public void deleteAction(ActionEvent actionEvent) {
     provider = table.getSelectionModel().getSelectedItem();
        if (provider != null) {
            providerOperation.delete(provider);
            refrech();
        }
    }
    private void refrech(){
        ArrayList<Provider> list_Provider = providerOperation.getAll();
        data_Table.setAll(list_Provider);
        table.setItems(data_Table);
    }

    public void test() {
        refrech();
    }


}
