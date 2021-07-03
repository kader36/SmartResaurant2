package Controllers.newKitchenChef;

import BddPackage.FoodOperation;
import Models.Food;
import com.itextpdf.text.DocumentException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;


public class FoodManagmentControlleur implements Initializable {

    // fxml elements.
    @FXML
    private Button exportExcelButton;

    @FXML
    private Button exportPDFButton;

    @FXML
    private Button printButton;

    @FXML
    private Button closeButton;

    @FXML
    private Button saveButton;

    @FXML
    private TableView<Food> foodsTabel;

    @FXML
    private TableColumn<Food, Double> foodPriceColumn;

    @FXML
    private TableColumn<Food, String> foodDescriptionColumn;

    @FXML
    private TableColumn<Food, String> foodNameColumn;

    @FXML
    private TableColumn<Food, ImageView> foodImageColumn;

    @FXML
    private TableColumn<Food, Integer> foodNumberColumn;

    @FXML
    private TableColumn<Food, CheckBox> foodCheckBoxTableColumn;

    @FXML
    private Label foodNumberLabel;

    @FXML
    private ComboBox<String> orderComboBox;

    @FXML
    private TextField searchTextField;

    @FXML
    private Button addNewFoodButton;

    @FXML
    private MenuButton moreButton;

    @FXML
    private MenuItem updateMenuItem;

    @FXML
    private MenuItem deleteMenuItem;


    // variables.
    ObservableList<Food> originalFoodsList =  FXCollections.observableArrayList();
    ObservableList<Food> temporaryFoodList = FXCollections.observableArrayList();
    ObservableList<Food> tableViewFoodList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // set the buttons action methods.
        addNewFoodButton.setOnAction(actionEvent -> {
            try {
                addNewFood();
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        exportExcelButton.setOnAction(actionEvent -> {
            try {
                exportExcel();
                PDFReportGenerators.showNotification(
                        "تخراج EXCEL",
                        "تم استخراج ملف ال EXCEL",
                        2,
                        false);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        exportPDFButton.setOnAction(actionEvent -> {
            try {
                PDFReportGenerators.exportFoodItemsPDF(addNewFoodButton,tableViewFoodList);
                PDFReportGenerators.showNotification(
                        "تخراج PDF",
                        "تم استخراج ملف ال PDF",
                        2,
                        false);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        printButton.setOnAction(actionEvent -> {
            try {
                PDFReportGenerators.printFoodItemsReport(addNewFoodButton, tableViewFoodList);
                PDFReportGenerators.showNotification(
                        "طباعة PDF",
                        "تم عرض الملف من أجل الطباعة ",
                        2,
                        false);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        });


        // set the search textField on change method.
        searchTextField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            searchFoodByName(newValue);
        });

        // add the order condition to the order comboBox.
        List<String> orderStringItems = new ArrayList<>();
        orderStringItems.add("الثمن");
        orderStringItems.add("الاسم");
        orderStringItems.add("الكل");
        ObservableList orderItems = FXCollections.observableList(orderStringItems);
        orderComboBox.setItems(orderItems);
        orderComboBox.setOnAction(actionEvent -> {
            if (orderComboBox.getValue().equals("الثمن")){
                //System.out.println(orderComboBox.getValue());
                showFoodBasedOnPriceOrder();
            }else{
                if (orderComboBox.getValue().equals("الاسم")){
                   // System.out.println(orderComboBox.getValue());
                    showFoodBasedOnNameOrder();
                }else{
                    showFoodBasedOnOriginalOrder();
                }
            }
        });

        // set the more button items.
        updateMenuItem.setOnAction(actionEvent -> {
            try {
                updateFoodData();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        deleteMenuItem.setOnAction(actionEvent -> {
            removeButton();
        });


        // load the food data.
        loadData();
    }

    // method that loads the data.
    void loadData(){

        // get the data from database.
        FoodOperation databaseConenctor = new FoodOperation();
        ArrayList<Food> dataBaseFood = databaseConenctor.getAll();
        for (int foodIndex = 0; foodIndex < dataBaseFood.size(); foodIndex++) {
            originalFoodsList.add(dataBaseFood.get(foodIndex));
            tableViewFoodList.add(dataBaseFood.get(foodIndex));
        }

        // set teh table view.
        foodPriceColumn.setCellValueFactory( new PropertyValueFactory<Food,Double>("price"));
        foodNameColumn.setCellValueFactory( new PropertyValueFactory<Food,String>("name"));
        foodImageColumn.setCellValueFactory( new PropertyValueFactory<Food,ImageView>("image"));
        foodDescriptionColumn.setCellValueFactory( new PropertyValueFactory<Food,String>("description"));
        foodNumberColumn.setCellValueFactory( new PropertyValueFactory<Food,Integer>("id"));
        foodCheckBoxTableColumn.setCellValueFactory( new PropertyValueFactory<Food,CheckBox>("foodSelectedCheckBox"));

        foodsTabel.setItems(originalFoodsList);

        // set the number of found food items label.
        int foundFoodItems = databaseConenctor.getCountFood();
        foodNumberLabel.setText(String.valueOf(foundFoodItems));

    }

    // method to save the changes.
    void saveData(){

        // save to database
        FoodOperation databaseConnector = new FoodOperation();
        for (int foodIndex = 0; foodIndex < temporaryFoodList.size(); foodIndex++) {
            // insert the new items to the data base
            databaseConnector.insert(temporaryFoodList.get(foodIndex));
            // synchronize the data.
            originalFoodsList.add(temporaryFoodList.get(foodIndex));
        }

    }

    // method to cancel the changes.
    void cancelChanges(){

        System.out.println("===================================================");
        // clear the temporary data.
        temporaryFoodList.clear();
        tableViewFoodList.clear();
       // revert to the original.
        for (int foodIndex = 0; foodIndex < originalFoodsList.size(); foodIndex++) {
            originalFoodsList.get(foodIndex).setCheckBoxState(false);
            tableViewFoodList.add(originalFoodsList.get(foodIndex));
        }

        // set the items to the table.
        foodsTabel.setItems(tableViewFoodList);
    }


    // method to search by name.
    void searchFoodByName(String searchedValue){
        // if search empty revert to original data.
        if (searchedValue.isEmpty()){
            tableViewFoodList.clear();
            for (int foodIndex = 0; foodIndex < originalFoodsList.size(); foodIndex++) {
                tableViewFoodList.add(originalFoodsList.get(foodIndex));
            }
            for (int foodIndex = 0; foodIndex < temporaryFoodList.size(); foodIndex++) {
                tableViewFoodList.add(temporaryFoodList.get(foodIndex));
            }
            foodsTabel.setItems(tableViewFoodList);
        }else{
            // search all the items in teh table view and remove the ones that do not start by that name.
            tableViewFoodList.clear();
            for (int foodIndex = 0; foodIndex < originalFoodsList.size(); foodIndex++) {
                if (originalFoodsList.get(foodIndex).getName().startsWith(searchedValue)){

                    tableViewFoodList.add(originalFoodsList.get(foodIndex));
                }
            }
            foodsTabel.setItems(tableViewFoodList);
        }

    }

    // method to show food based on name order.
    void showFoodBasedOnNameOrder(){

        // reorder what is in teh table view list based on the name ordering.
        List<Food> tmpList = tableViewFoodList
                .stream()
                .sorted((o1, o2) -> o1.getName().compareTo(o2.getName()) > 0 ? 1 : -1)
                .collect(Collectors.toList());
        tableViewFoodList.clear();
        for (int index = 0; index < tmpList.size(); index++) {
            tableViewFoodList.add(tmpList.get(index));
        }
        // assign the list to the table.
        foodsTabel.setItems(tableViewFoodList);
        // assign the list to the table.
    }

    void showFoodBasedOnOriginalOrder(){

        // go back to the original order.
        tableViewFoodList.clear();
        for (int foodIndex = 0; foodIndex < originalFoodsList.size(); foodIndex++) {
            tableViewFoodList.add(originalFoodsList.get(foodIndex));
        }
        for (int foodIndex = 0; foodIndex < temporaryFoodList.size(); foodIndex++) {
            tableViewFoodList.add(temporaryFoodList.get(foodIndex));
        }
    }


    // method to show food based on price order.
    void showFoodBasedOnPriceOrder(){

        // reorder what is in teh table view list based on the price ordering.
        List<Food> tmpList = tableViewFoodList
                .stream()
                .sorted(Comparator.comparingInt(Food::getPrice))
                .collect(Collectors.toList());
        tableViewFoodList.clear();
        for (int index = 0; index < tmpList.size(); index++) {
            tableViewFoodList.add(tmpList.get(index));
        }
        // assign the list to the table.
        foodsTabel.setItems(tableViewFoodList);
    }

    // method to add a food.
    void addNewFood() throws IOException {

        // open the new add window.
        AnchorPane root = FXMLLoader.load(getClass().getResource("/Views/KitchenChef/newFood.fxml"));
        Stage newFoodStage = new Stage();
        newFoodStage.setScene(new Scene(root));
        newFoodStage.setTitle("اضافة طعام");
        newFoodStage.requestFocus();
        newFoodStage.initModality(Modality.APPLICATION_MODAL);
        newFoodStage.show();
    }

    // method that will refresh  the order.
    /*void refreshFoodItemsOrder(){
        // get the selected order index from the comboBox.
        // change the order condition and re show in teh table view.
    }*/

    // more functions method.
    void moreButton(){
        // show the delete/update option.
        // add the alert messages.
    }

    // method to update the food.
    void updateFoodData() throws IOException {

        // set the selected food in the update window class.
        boolean noFoodIsSelected = true;
        for (int foodIndex = 0; foodIndex < tableViewFoodList.size(); foodIndex++) {
            if (tableViewFoodList.get(foodIndex).getFoodSelectedCheckBox().isSelected() == true){
                UpdateFoodControlleur.selectedFoodData = tableViewFoodList.get(foodIndex);
                noFoodIsSelected = false;
            }
        }

        if (noFoodIsSelected == true){
            PDFReportGenerators.showErrorNotification(
                    "لم يتم اختيار أي عنصر",
                    "الرجاء اختيار عنصر لاجراء التعديل عليه",
                    1,
                    false
            );
        }else{
            // open the update food window.
            AnchorPane root = FXMLLoader.load(getClass().getResource("/Views/KitchenChef/updateFood.fxml"));
            Stage newFoodStage = new Stage();
            newFoodStage.setScene(new Scene(root));
            newFoodStage.setTitle("تحديث المعلومات");
            newFoodStage.requestFocus();
            newFoodStage.initModality(Modality.APPLICATION_MODAL);
            newFoodStage.show();
        }
    }

    // method to remove the selected food.
    void removeButton(){

        // create the alert msg before deleting.
        Alert deletionAlert = new Alert(Alert.AlertType.CONFIRMATION);
        deletionAlert.setTitle("حدف العناصر");
        deletionAlert.setContentText("سيتم حدف العناصر التي تم اختيارها بصفة دائمة ادا قمت بتأكيد هدا الرسالة");
        deletionAlert.setHeaderText("تأكيد حدف العناصر");
        //deletionAlert.setWidth(600);
        ButtonType cancelButton = new ButtonType("الغاء", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType confirmationButton = new ButtonType("حدف", ButtonBar.ButtonData.YES);
        deletionAlert.getDialogPane().getButtonTypes().clear();
        deletionAlert.getDialogPane().getButtonTypes().add(cancelButton);
        deletionAlert.getDialogPane().getButtonTypes().add(confirmationButton);
        Optional<ButtonType> alertresult =  deletionAlert.showAndWait();

        if (alertresult.get().getText().equals("حدف")){
            // add warnings.
            FoodOperation databseConnector  = new FoodOperation();
            for (int foodIndex = 0; foodIndex < tableViewFoodList.size(); foodIndex++) {
                if (tableViewFoodList.get(foodIndex).getFoodSelectedCheckBox().isSelected() == true){
                    // remove from database.
                    databseConnector.delete(tableViewFoodList.get(foodIndex));
                    // remove from the other list.
                    originalFoodsList.remove(tableViewFoodList.get(foodIndex));
                    // remove from the temporary list.
                    temporaryFoodList.remove(tableViewFoodList.get(foodIndex));
                    // remove from teh view.
                    tableViewFoodList.remove(tableViewFoodList.get(foodIndex));
                }
            }
            PDFReportGenerators.showNotification(
                    "حدف العناصر",
                    "تم حدف العناصر بنجاح",
                    1,
                    false
            );
        }
    }


    // method to export excel file
    void exportExcel() throws IOException {

        // export what is in the table view as an excel file.
        // show place picker.
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(addNewFoodButton.getScene().getWindow());
        System.out.println(selectedDirectory.getAbsolutePath());

        // create a .csv file and write to it.
        File excelFile = new File(selectedDirectory.getAbsolutePath()+"/excelFile"+new Date().getDate()+".csv");
        FileWriter fileWriter =  new FileWriter(excelFile);
        // the excel titles.
        fileWriter.write("رقم الطعام,الاسم,الثمن,وصف\n");
        // add all the data from teh table view.
        for (int foodIndex = 0; foodIndex < tableViewFoodList.size(); foodIndex++) {
            fileWriter.write(
                    tableViewFoodList.get(foodIndex).getId() + ","+
                            tableViewFoodList.get(foodIndex).getName() + "," +
                            tableViewFoodList.get(foodIndex).getPrice() + "," +
                            tableViewFoodList.get(foodIndex).getDescription() + "\n"
            );
        }
        fileWriter.close();
    }



}
