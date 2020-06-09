package Controllers;

import BddPackage.TabelsOperation;
import Models.Tables;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public void AddTable() {
        Tables tables = new Tables();
        tables.setNumber(5);//table number
        TabelsOperation tabelsOperation = new TabelsOperation();//calss ta3 les fonction
        if(!tabelsOperation.isExist(tables))  tabelsOperation.insert(tables);//example insert

    }
}
