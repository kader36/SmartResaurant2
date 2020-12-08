package Controllers.stroreKeeper;

import Models.BillList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BillListController implements Initializable {
    @FXML
    private BorderPane mainPane;


    @FXML
    private TableView<?> billTable;

    @FXML
    private TableColumn<?, ?> col_Bill_number;

    @FXML
    private TableColumn<?, ?> col_Date;

    @FXML
    private TableColumn<?, ?> col_Provider;

    @FXML
    private TableColumn<?, ?> col_Paid;

    @FXML
    private TableColumn<?, ?> col_rest;

    @FXML
    private TableColumn<?, ?> col_Total;

    @FXML
    private VBox vboxBillOption;



    private ArrayList<BillList> billLists = new ArrayList<>();
    private ObservableList<BillList> dataTable = FXCollections.observableArrayList();
    private boolean visible = false;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chargeListBill();
        vboxBillOption.setVisible(false);
        col_Bill_number.setCellValueFactory(new PropertyValueFactory<>("number"));
        col_Date.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_Provider.setCellValueFactory(new PropertyValueFactory<>("provider_name"));
        col_Paid.setCellValueFactory(new PropertyValueFactory<>("Paid_up"));
        col_rest.setCellValueFactory(new PropertyValueFactory<>("rest"));
        col_Total.setCellValueFactory(new PropertyValueFactory<>("total"));
        dataTable.setAll(billLists);
     //   billTable.setItems(dataTable);

    }

    public void Init(BorderPane mainPane) {
        this.mainPane = mainPane;
    }

    @FXML
    void addNewBill(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/AddFactoryBuy.fxml"));
            BorderPane temp = loader.load();
            BillController billController = loader.getController();
            billController.Init(temp);
            mainPane.getChildren().setAll(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void chargeListBill(){
        for (int i = 1; i < 10; i++) {
            BillList billList = new BillList();
            billList.setNumber(i);
            billList.setProvider_name("selmani");
            billList.setDate((i*2+1)+"/"+i+"/2020");
            billList.setPaid_up(i*1255);
            billList.setTotal(i*1525);
            billList.setRest(billList.getTotal()-billList.getPaid_up());
            billLists.add(billList);
        }
    }
    @FXML
    void slideShowHide(ActionEvent event) {
        if (!visible){
            vboxBillOption.setVisible(true);
            visible = true;
        }
        else{ vboxBillOption.setVisible(false);
            visible = false;

        }
    }

}
