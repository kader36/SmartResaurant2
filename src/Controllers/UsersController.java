package Controllers;

import BddPackage.UserOperation;
import Models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UsersController implements Initializable {
    @FXML
    private VBox vboxOptionCategory;
    @FXML
    private TableView<User> userTableView;
    @FXML
    private TableColumn<User, Integer> col_username;
    @FXML
    private TableColumn<User, String> col_type;
    @FXML
    private TableColumn<User, String> col_employe;
    private User user = new User();
    private UserOperation userOperation = new UserOperation();
    private ObservableList<User> dataTable = FXCollections.observableArrayList();
    private ArrayList<User> listUsers = new ArrayList<>();
    private ValidateController validateController = new ValidateController();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vboxOptionCategory.setVisible(false);
        chargelistCategoryProduct();
        col_username.setCellValueFactory(new PropertyValueFactory<>("UserName"));
        col_type.setCellValueFactory(new PropertyValueFactory<>("Type"));
        col_employe.setCellValueFactory(new PropertyValueFactory<>("Emloyer_name"));
        dataTable.setAll(listUsers);
        userTableView.setItems(dataTable);

    }
    private void chargelistCategoryProduct() {
        listUsers = userOperation.getAll();
    }
    @FXML
    void showHideCategoryOperation(ActionEvent event) {
        userTableView.setDisable(true);
       // visibleCategory = showHideListOperation(vboxOptionCategory,visibleCategory);
    }
}
