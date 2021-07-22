package Controllers.Tables;

import BddPackage.TabelsOperation;
import Models.Tables;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class TablesListController implements Initializable {

    // fxml elements.
    @FXML
    private Button removeButton;

    @FXML
    private TextField tableNameTextField;

    @FXML
    private Button addTableButton;

    @FXML
    private Button quitDataButton;

    @FXML
    private Button saveDataButton;

    @FXML
    private TableView<Tables> tablesTableView;

    @FXML
    private TableColumn<Tables, CheckBox> tableViewCheckColumn;

    @FXML
    private TableColumn<Tables, String> tableViewNumberColumn;

    @FXML
    private TableColumn<Tables, String> tableViewNameColumn;

    // variables.
    // temporary data that will be add to table view but not to data base.
    ObservableList<Tables> temporarytableList = FXCollections.observableArrayList();

    // original data that we get from data base.
    ObservableList<Tables> originaltableList = FXCollections.observableArrayList();

    // data that is curently in the table.
    ObservableList<Tables> tablewViewTableList = FXCollections.observableArrayList();


    // testing data.
   // ObservableList<Tables> tableList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // set the button on action.
        addTableButton.setOnAction(actionEvent -> {
            addTable(Integer.parseInt(tableNameTextField.getText()));
                    tableNameTextField.clear();
        }
        );

        saveDataButton.setOnAction(actionEvent -> {
            saveTablesToDatabase();
        });

        quitDataButton.setOnAction(actionEvent -> {
            clearData();
        });

        removeButton.setOnAction(actionEvent -> {
            removeOperation();
        });


        // get the data.
        TabelsOperation databseConnector = new  TabelsOperation();
        ArrayList<Tables> tablesFromDatabase = databseConnector.getAll();

        for (int index = 0; index < tablesFromDatabase.size(); index++) {
            originaltableList.add(tablesFromDatabase.get(index));
            tablewViewTableList.add(tablesFromDatabase.get(index));
        }

        // setting the table view.
        tableViewCheckColumn.setCellValueFactory( new PropertyValueFactory<Tables,CheckBox>("activeCheckBox"));
        tableViewNumberColumn.setCellValueFactory( new PropertyValueFactory<Tables,String>("id"));
        tableViewNameColumn.setCellValueFactory( new PropertyValueFactory<Tables,String>("numberTab"));

        tablesTableView.setItems(originaltableList);


    }

    // a method to save the table.
    void saveTablesToDatabase(){

        // add the new operations to the database.

        // clear the list to not do a duplication later.
        temporarytableList.clear();
        // set the new original data.

        Alert alertWarning = new Alert(Alert.AlertType.INFORMATION);
        alertWarning.setHeaderText("تمت العملية ");
        alertWarning.setContentText("تم حفظ المعلومات بنجاح");
        Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setText("حسنا");
        alertWarning.showAndWait();

    }

    // a method to quit the method.
    void clearData(){

        temporarytableList.clear();
        tablewViewTableList.clear();
        // delete the temp from the table view.
        for (int index = 0; index < temporarytableList.size(); index++) {
            tablewViewTableList.remove(temporarytableList.get(index));
        }
        for (int index = 0; index < originaltableList.size(); index++) {
            tablewViewTableList.add(originaltableList.get(index));
        };
        tablesTableView.setItems(originaltableList);
    }


    // a method to add a table to table view.
    void addTable(int tableNumber){

        // add it to the temporary.
        TabelsOperation tabelsOperation=new TabelsOperation();
        Tables tables=new Tables();
        tables.setNumber(tableNumber);
        boolean bool=tabelsOperation.isExist(tables);
        if(!bool){
            temporarytableList.add(
                    new Tables(tableNumber,tableNumber,String.valueOf(false))
            );
            // add it to the table
            tablewViewTableList.add(
                    new Tables(tableNumber,tableNumber,String.valueOf(false))
            );

            tablesTableView.setItems(tablewViewTableList);
            TabelsOperation databaseConenctor = new TabelsOperation();
            for (int index = 0; index < temporarytableList.size(); index++) {
                databaseConenctor.insert(temporarytableList.get(index));
                originaltableList.add(temporarytableList.get(index));
            }
        }else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("تحذير ");
            alertWarning.setContentText("هذه الطاولة موجودة ");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("حسنا");
            alertWarning.showAndWait();
        }

    }



    // remove a money withdrawal operation.
    void removeOperation(){

        TabelsOperation databaseConnector = new TabelsOperation();
        for (int index = 0; index < tablewViewTableList.size(); index++) {
            if (tablewViewTableList.get(index).getActiveCheckBox().isSelected() == true){
                // delete in the database.
                databaseConnector.delete(tablewViewTableList.get(index));
                // delete in the lists.
                originaltableList.remove(
                        tablewViewTableList.get(index)
                );
                temporarytableList.remove(
                        tablewViewTableList.get(index)
                );
                tablewViewTableList.remove(
                        tablewViewTableList.get(index)
                );

            }
        }
    }




}
