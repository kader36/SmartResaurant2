package Controllers.newKitchenChef;

import BddPackage.FoodCategoryOperation;
import BddPackage.FoodOperation;
import Models.Food;
import Models.FoodCategory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class NewFoodAddControlleur implements Initializable {

    // fxml elements.
    @FXML
    private ComboBox<String> foodCategpryComboBox;

    @FXML
    private TextField foodNameTextField;

    @FXML
    private TextField foodPriceTextField;

    @FXML
    private TextArea foodDescriptionTextArea;

    @FXML
    private Label picturePathLabel;

    @FXML
    private Button addPictureButton;

    @FXML
    private Button addFoodButton;

    // variables.
    ArrayList<FoodCategory> categoryiesList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // set the buttons action methods.
        addFoodButton.setOnAction(actionEvent -> {
            addNewFoodToDataBase();
            PDFReportGenerators.showNotification(
                    "أضافة طعام جديد",
                    "تم اضافة طعام جديد الى قائمة الاطعمة المتوفرة ",
                    2,
                    false);
        });

        addPictureButton.setOnAction(actionEvent -> {
            selectFoodPicture();
        });

        // load the category data.
        loadData();
    }


    //  get the necessary data from database.
    void loadData(){
        // get the list of categories.
        FoodCategoryOperation databaseConenctor = new FoodCategoryOperation();
        categoryiesList = databaseConenctor.getAll();
        // set the categories comboBox.
        ArrayList<String> categoriesNames = new ArrayList<>();
        for (int categpryIndex = 0; categpryIndex < categoryiesList.size(); categpryIndex++) {
            categoriesNames.add(categoryiesList.get(categpryIndex).getName());
        }
        foodCategpryComboBox.getItems().addAll(categoriesNames);
    }

    // method to select the food picture.
    void selectFoodPicture(){

        // open the files picker.
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(addFoodButton.getScene().getWindow());
        // save the chosen file path.
        if (file != null){
            picturePathLabel.setText(file.getPath());
        }
        // add a notifier of the new added food.

    }

    // method to confirm the add.
    void addNewFoodToDataBase(){

        if (
                foodNameTextField.getText().isEmpty() == true ||
                foodDescriptionTextArea.getText().isEmpty() == true ||
                foodPriceTextField.getText().isEmpty() == true ||
                picturePathLabel.getText().isEmpty() == true ){

            // set the error message maybe an snack bar alert message.
            PDFReportGenerators.showErrorNotification(
                    "بعض العناصر ناقصة",
                    "الرجاء ماء جميع المعلومات",
                    1,
                    false
            );

        }else{
            FoodOperation databaseConnector = new FoodOperation();
            Food newFood = new Food();
            // get the selected category id
            int selectedCategoryID = 0 ;
            for (int categoryIndex = 0; categoryIndex < categoryiesList.size(); categoryIndex++) {
                if (foodCategpryComboBox.getValue().equals(categoryiesList.get(categoryIndex).getName())){
                    selectedCategoryID = categoryiesList.get(categoryIndex).getId();
                }
            }
            // set the food data.
            newFood.setName(foodNameTextField.getText());
            newFood.setPrice(Integer.parseInt(foodPriceTextField.getText()));
            newFood.setImage_path(picturePathLabel.getText());
            newFood.setDescription(foodDescriptionTextArea.getText());
            newFood.setId_category(selectedCategoryID);
            newFood.setAvailabale(true);

            databaseConnector.insert(newFood);
        }

    }

}
