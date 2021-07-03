package Controllers.KitchenChef;

import BddPackage.FoodOperation;
import Controllers.ValuesStatic;
import Models.Food;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class FoodsController implements Initializable {

    @FXML
    private AnchorPane mainPain;
    @FXML
    private BorderPane mainPane;

    @FXML
    private TableView<Food> foodTable;

    @FXML
    private TableColumn<?, ?> col_id;

    @FXML
    private TableColumn<?, ?> col_img;

    @FXML
    private TableColumn<?, ?> col_name;

    @FXML
    private TableColumn<?, ?> col_category;

    @FXML
    private TableColumn<?, ?> col_description;

    @FXML
    private TableColumn<?, ?> col_price;

    @FXML
    private Label totalFood;

    @FXML

    private VBox vboxOption;

    @FXML
    private TextField txtSearch;

    @FXML
    private ComboBox<String> sortedCombo;

    private ArrayList<Food> list_Food = new ArrayList<>();
    private FoodOperation foodOperation = new FoodOperation();
    private ObservableList<Food> dataTable = FXCollections.observableArrayList();
    FilteredList<Food> filteredData;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void Init(BorderPane temp) {
        mainPane = temp;
        vboxOption.setVisible(false);
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_img.setCellValueFactory(new PropertyValueFactory<>("image"));
        col_category.setCellValueFactory(new PropertyValueFactory<>("category_name"));
        col_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
      //  sortedCombo.getItems().addAll("رقم الوجبة","اسم الوجبة","سعر الوجبة","صورة الوجبة","وصف الوجبة");
        sortedByCombo();
        list_Food = foodOperation.getAll();
        dataTable.setAll(list_Food);
        foodTable.setItems(dataTable);
        totalFood.setText(String.valueOf(foodOperation.getCountFood()));
    }


    @FXML
    void addNewFood(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/addFood.fxml"));
            BorderPane temp = loader.load();
            AddFoodController addFoodController = loader.getController();
            addFoodController.Init(temp);
            mainPane.getChildren().setAll(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void ShowListeCategory(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/KitchenChef/FoodCategories.fxml"));
            DialogPane temp = loader.load();
            CategoryFoodController categoryFoodController = loader.getController();
            categoryFoodController.InitCategoryList();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(temp);
            dialog.initStyle(StageStyle.UNDECORATED);
            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void deleteFood(ActionEvent event) {
        vboxOption.setVisible(false);
        Food food = foodTable.getSelectionModel().getSelectedItem();
        if (food == null) {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("تحذير ");
            alertWarning.setContentText("يرجى اختيار الوجبة المراد حذفها");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("حسنا");
            alertWarning.showAndWait();
            return;
        }

        Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirmation.setHeaderText("تأكيد الحذف");
        alertConfirmation.setContentText("هل انت متأكد من حذف الوجبة");
        Button okButton = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setText("موافق");

        Button cancel = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.CANCEL);
        cancel.setText("الغاء");

        alertConfirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.CANCEL) {
                alertConfirmation.close();
            } else if (response == ButtonType.OK) {
                foodOperation.delete(food);
                Alert alertWarning = new Alert(Alert.AlertType.INFORMATION);
                alertWarning.setHeaderText("تأكيد الحذف");
                alertWarning.setContentText("تم حذف الوجبة بنجاح");
                Button ookButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                ookButton.setText("حسنا");
                alertWarning.showAndWait();
                refresh();
            }
        });
    }

    @FXML
    void editFood(ActionEvent event) {
        Food food = foodTable.getSelectionModel().getSelectedItem();
        if (food == null) {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("تحذير ");
            alertWarning.setContentText("يرجى اختيار الوجبة المراد تعديلها");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("حسنا");
            alertWarning.showAndWait();
            return;
        }
        ValuesStatic.currentFood = food;
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/UpdateFood.fxml"));
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/UpdateFood.fxml"));
            BorderPane temp = loader.load();
            EditFoodController editFoodController = loader.getController();
            editFoodController.Init(temp);
            mainPane.getChildren().setAll(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void searchFood(ActionEvent event) {
        // filtrer les données
        ObservableList<Food> dataProvider = foodTable.getItems();
        filteredData = new FilteredList<>(dataProvider, e -> true);
        txtSearch.setOnKeyReleased(e -> {
            txtSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filteredData.setPredicate((Predicate<? super Food>) food -> {
                    if (newValue == null || newValue.isEmpty()) {
                        //loadDataInTable();
                        return true;
                    } else if (String.valueOf(food.getId()).contains(newValue.toLowerCase())) {
                        return true;
                    } else if (food.getName().toLowerCase().contains(newValue.toLowerCase())) {
                        return true;
                    } else if (String.valueOf(food.getPrice()).toLowerCase().contains(newValue.toLowerCase())) {
                        return true;
                    } else if (food.getDescription().contains(newValue)) {
                        return true;
                    }
                    return false;
                });
            });

            SortedList<Food> sortedList = new SortedList<>(filteredData);
            sortedList.comparatorProperty().bind(foodTable.comparatorProperty());
            foodTable.setItems(sortedList);
        });
    }

    private void refresh() {
        list_Food = foodOperation.getAll();
        dataTable.setAll(list_Food);
        foodTable.setItems(dataTable);
        totalFood.setText(String.valueOf(foodOperation.getCountFood()));


    }

    @FXML
    void showHideFoodOperation(ActionEvent event) {
        vboxOption.setVisible(true);
    }

    @FXML
    void reportFoodTable(ActionEvent event) {

    }

    @FXML
    void exportCsvFood(ActionEvent event) {

    }


    @FXML
    void exportPdfFood(ActionEvent event) {

    }


    void sortedByCombo() {
        sortedCombo.valueProperty().addListener((new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                switch (newValue) {
                    case "رقم الوجبة":
                        dataTable.setAll(foodOperation.getAllBy("FOOD.ID_FOOD"));
                        foodTable.setItems(dataTable);
                        break;
                    case "اسم الوجبة":
                        dataTable.setAll(foodOperation.getAllBy("FOOD.FOOD_NAME"));
                        foodTable.setItems(dataTable);
                        break;
                    case "سعر الوجبة":
                        dataTable.setAll(foodOperation.getAllBy("FOOD.FOOD_PRICE"));
                        foodTable.setItems(dataTable);
                        break;
                    case "وصف الوجبة":
                        dataTable.setAll(foodOperation.getAllBy("FOOD.DESCRIPTION"));
                        foodTable.setItems(dataTable);
                        break;

                }

            }
        }));

    }


}
