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

public class UpdateFoodControlleur implements Initializable {

    //fxml elements.
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
    private Button updateFoodButton;


    // varaibles.
    static Food selectedFoodData;
    ArrayList<FoodCategory> categoryiesList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // set the selected food to update data.
        updateFoodButton.setOnAction(actionEvent -> {
            updateFoodData();
        });

        addPictureButton.setOnAction(actionEvent -> {
            selectFoodPicture();
        });

        // intilize the data.
        loadSelectedFoodData();
    }


    // method to load the selected food data.
    void loadSelectedFoodData(){

        // load the categories.
        FoodCategoryOperation databaseConenctor = new FoodCategoryOperation();
        categoryiesList = databaseConenctor.getAll();

        // set the categories comboBox.
        ArrayList<String> categoriesNames = new ArrayList<>();
        for (int categpryIndex = 0; categpryIndex < categoryiesList.size(); categpryIndex++) {
            categoriesNames.add(categoryiesList.get(categpryIndex).getName());
        }
        foodCategpryComboBox.getItems().addAll(categoriesNames);
        // set the selected category.
        String selectedCategoryValue = "" ;
        for (int categoryIndex = 0; categoryIndex < categoryiesList.size(); categoryIndex++) {
            if (selectedFoodData.getId_category() == categoryiesList.get(categoryIndex).getId()){
                selectedCategoryValue = categoryiesList.get(categoryIndex).getName();
            }
        }
        foodCategpryComboBox.setValue(selectedCategoryValue);

        // load the other data.
        foodNameTextField.setText(selectedFoodData.getName());
        foodDescriptionTextArea.setText(selectedFoodData.getDescription());
        foodPriceTextField.setText(String.valueOf(selectedFoodData.getPrice()));
        picturePathLabel.setText(selectedFoodData.getImage_path());
    }

    // method to change the food image.
    void selectFoodPicture(){

        // open the files picker.
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(updateFoodButton.getScene().getWindow());
        // save the chosen file path.
        if (file != null){
            picturePathLabel.setText(file.getPath());
        }

    }

    // method to confirm the update.
    void updateFoodData(){

        FoodOperation databaseConnector = new FoodOperation();
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
            Food newFood = new Food();
            // get the selected category id
            int selectedCategoryID = 0 ;
            for (int categoryIndex = 0; categoryIndex < categoryiesList.size(); categoryIndex++) {
                if (foodCategpryComboBox.getValue().equals(categoryiesList.get(categoryIndex).getName())){
                    selectedCategoryID = categoryiesList.get(categoryIndex).getId();
                }
            }
            // set the food data.
            newFood.setId(selectedFoodData.getId());
            newFood.setName(foodNameTextField.getText());
            newFood.setPrice(Integer.parseInt(foodPriceTextField.getText()));
            newFood.setImage_path(picturePathLabel.getText());
            newFood.setDescription(foodDescriptionTextArea.getText());
            newFood.setId_category(selectedCategoryID);
            newFood.setAvailabale(selectedFoodData.isAvailabale());
            databaseConnector.update(newFood,newFood);

            PDFReportGenerators.showNotification(
                    "تحديث المعلومات",
                    "تم تحديث المعلومات بنجاح",
                    1,
                    false
            );
        }


    }
}
