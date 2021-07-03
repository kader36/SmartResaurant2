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

public class NewDrinkAddControlleur implements Initializable {

    // fxml elements.
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
    private Button addDrinkButton;


    // variables.
    ArrayList<DrinksCategory> categoryiesList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // set the buttons action methods.
        addDrinkButton.setOnAction(actionEvent -> {
            addNewDrinkToDataBase();
            PDFReportGenerators.showNotification(
                    "أضافة مشروب جديد",
                    "تم اضافة مشروب جديد الى قائمة المشروبات المتوفرة ",
                    2,
                    false);
        });

        addPictureButton.setOnAction(actionEvent -> {
            selectDrinkPicture();
        });

        // load the category data.
        loadData();

    }

    //  get the necessary data from database.
    void loadData(){
        // get the list of categories.
        DrinksCategoryOperation databaseConenctor = new DrinksCategoryOperation();
        categoryiesList = databaseConenctor.getAll();
        // set the categories comboBox.
        ArrayList<String> categoriesNames = new ArrayList<>();
        for (int categpryIndex = 0; categpryIndex < categoryiesList.size(); categpryIndex++) {
            categoriesNames.add(categoryiesList.get(categpryIndex).getName());
        }
        drinkCategpryComboBox.getItems().addAll(categoriesNames);
    }

    // method to select the food picture.
    void selectDrinkPicture(){

        // open the files picker.
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(addDrinkButton.getScene().getWindow());
        // save the chosen file path.
        if (file != null){
            picturePathLabel.setText(file.getPath());
        }
        // add a notifier of the new added food.

    }

    // method to confirm the add.
    void addNewDrinkToDataBase(){

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
            DrinksOperation databaseConnector = new DrinksOperation();
            Drinks newDrink = new Drinks();
            // get the selected category id
            int selectedCategoryID = 0 ;
            for (int categoryIndex = 0; categoryIndex < categoryiesList.size(); categoryIndex++) {
                if (drinkCategpryComboBox.getValue().equals(categoryiesList.get(categoryIndex).getName())){
                    selectedCategoryID = categoryiesList.get(categoryIndex).getId();
                }
            }
            // set the food data.
            newDrink.setName(drinkNameTextField.getText());
            newDrink.setPrice(Integer.parseInt(drinkPriceTextField.getText()));
            newDrink.setImage_path(picturePathLabel.getText());
            newDrink.setDescription(drinkDescriptionTextArea.getText());
            newDrink.setId_category(selectedCategoryID);
            newDrink.setAvailable(true);

            databaseConnector.insert(newDrink);
        }

    }

}
