package Controllers;

import BddPackage.UserOperation;
import Models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UsersController implements Initializable {
    @FXML
    private Button cancelButtonCategory;
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
    private boolean visibleUser= false;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vboxOptionCategory.setVisible(false);
        visibleUser= false;
        chargelistUsers();
        col_username.setCellValueFactory(new PropertyValueFactory<>("UserName"));
        col_type.setCellValueFactory(new PropertyValueFactory<>("Type"));
        col_employe.setCellValueFactory(new PropertyValueFactory<>("Emloyer_name"));
        dataTable.setAll(listUsers);
        userTableView.setItems(dataTable);

    }
    private void chargelistUsers() {
        listUsers = userOperation.getAll();
    }
    @FXML
    void showHideUserOperation(ActionEvent event) {
        userTableView.setDisable(true);
        visibleUser = showHideListOperation(vboxOptionCategory,visibleUser);
    }
    private boolean showHideListOperation(VBox vBox, boolean visibles) {

        if (!visibles) {
            vBox.setVisible(true);
            visibles = true;
        } else {
            vBox.setVisible(false);
            visibles = false;

        }
        return visibles;
    }
    private void closeDialog(Button btn) {
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }
    @FXML
    void clickedMouse(MouseEvent event) {
        closeDialog(cancelButtonCategory);
    }
    @FXML
    void insertUser(ActionEvent event) {
        try {
            vboxOptionCategory.setVisible(false);
            visibleUser = false;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/AddUser.fxml"));
            DialogPane temp = loader.load();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(temp);
            dialog.initStyle(StageStyle.UNDECORATED);
            dialog.showAndWait();
            refresh();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void refresh() {
        chargelistUsers();
        dataTable.setAll(listUsers);
        userTableView.setItems(dataTable);
    }
    @FXML
    void ShowUserTable(MouseEvent event) {
        userTableView.setDisable(false);
    }

}
