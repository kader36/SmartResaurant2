package Controllers.newKitchenChef;

import BddPackage.DrinksCategoryOperation;
import BddPackage.DrinksOperation;
import Models.Drinks;
import Models.DrinksCategory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UpdateDrinkControlleur implements Initializable {

    //fxml elements.
    @FXML
    private ComboBox<String> drinkCategpryComboBox;

    @FXML
    private TextField drinkNameTextField;

    @FXML
    private TextField drinkPriceTextField;

    @FXML
    private TextArea drinkDescriptionTextArea;

    @FXML
    private Label picturePathLabel;

    @FXML
    private Button addPictureButton;

    @FXML
    private Button updateDrinkButton;


    // varaibles.
    static Drinks selectedDrinkData;
    ArrayList<DrinksCategory> categoryiesList = new ArrayList<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // set the selected food to update data.
        updateDrinkButton.setOnAction(actionEvent -> {
            updateDrinkData();
        });

        addPictureButton.setOnAction(actionEvent -> {
            selectDrinkPicture();
        });

        // intilize the data.
        loadSelectedDrinkData();
    }

    // method to load the selected food data.
    void loadSelectedDrinkData(){

        // load the categories.
        DrinksCategoryOperation databaseConenctor = new DrinksCategoryOperation();
        categoryiesList = databaseConenctor.getAll();

        // set the categories comboBox.
        ArrayList<String> categoriesNames = new ArrayList<>();
        for (int categpryIndex = 0; categpryIndex < categoryiesList.size(); categpryIndex++) {
            categoriesNames.add(categoryiesList.get(categpryIndex).getName());
        }
        drinkCategpryComboBox.getItems().addAll(categoriesNames);
        // set the selected category.
        String selectedCategoryValue = "" ;
        for (int categoryIndex = 0; categoryIndex < categoryiesList.size(); categoryIndex++) {
            if (selectedDrinkData.getId_category() == categoryiesList.get(categoryIndex).getId()){
                selectedCategoryValue = categoryiesList.get(categoryIndex).getName();
            }
        }
        drinkCategpryComboBox.setValue(selectedCategoryValue);

        // load the other data.
        drinkNameTextField.setText(selectedDrinkData.getName());
        drinkDescriptionTextArea.setText(selectedDrinkData.getDescription());
        drinkPriceTextField.setText(String.valueOf(selectedDrinkData.getPrice()));
        picturePathLabel.setText(selectedDrinkData.getImage_path());
    }

    // method to change the food image.
    void selectDrinkPicture(){

        // open the files picker.
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(updateDrinkButton.getScene().getWindow());
        // save the chosen file path.
        if (file != null){
            picturePathLabel.setText(file.getPath());
        }

    }

    // method to confirm the update.
    void updateDrinkData(){

        DrinksOperation databaseConnector = new DrinksOperation();
        if (
                drinkNameTextField.getText().isEmpty() == true ||
                        drinkDescriptionTextArea.getText().isEmpty() == true ||
                        drinkPriceTextField.getText().isEmpty() == true ||
                        picturePathLabel.getText().isEmpty() == true ){

            // set the error message maybe an snack bar alert message.
            PDFReportGenerators.showErrorNotification(
                    "بعض العناصر ناقصة",
                    "الرجاء ماء جميع المعلومات",
                    1,
                    false
            );


        }else{
            Drinks newDrink = new Drinks();
            // get the selected category id
            int selectedCategoryID = 0 ;
            for (int categoryIndex = 0; categoryIndex < categoryiesList.size(); categoryIndex++) {
                if (drinkCategpryComboBox.getValue().equals(categoryiesList.get(categoryIndex).getName())){
                    selectedCategoryID = categoryiesList.get(categoryIndex).getId();
                }
            }
            // set the food data.
            newDrink.setId(selectedDrinkData.getId());
            newDrink.setName(drinkNameTextField.getText());
            newDrink.setPrice(Integer.parseInt(drinkPriceTextField.getText()));
            newDrink.setImage_path(picturePathLabel.getText());
            newDrink.setDescription(drinkDescriptionTextArea.getText());
            newDrink.setId_category(selectedCategoryID);
            newDrink.setAvailable(selectedDrinkData.isAvailable());
            databaseConnector.update(newDrink,newDrink);

            PDFReportGenerators.showNotification(
                    "تحديث المعلومات",
                    "تم تحديث المعلومات بنجاح",
                    1,
                    false
            );
        }

    }


}
