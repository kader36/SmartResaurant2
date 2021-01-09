package Controllers.KitchenChef;

import BddPackage.FoodOperation;
import BddPackage.IngredientsFoodOperation;
import BddPackage.ProductOperation;
import Controllers.ValidateController;
import Controllers.ValuesStatic;
import Models.Food;
import Models.IngredientsFood;
import Models.Product;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Predicate;

public class EditFoodController {
    @FXML
    private BorderPane mainPane;

    @FXML
    private TextField foodName;

    @FXML
    private Label lbl_nb_food;

    @FXML
    private TextField foodPrice;

    @FXML
    private ImageView picFood;

    @FXML
    private TableView<IngredientsFood> table_food;

    @FXML
    private TableColumn<?, ?> col_Product_Name;

    @FXML
    private TableColumn<?, ?> col_Unit;

    @FXML
    private TableColumn<?, ?> col_Quantity;


    @FXML
    private Button saveTableProductsBtn;

    @FXML
    private ImageView cancelTableProduct;

    @FXML
    private Button reportTableProductsBtn;

    @FXML
    private Button exportCsvTableProductsBtn;

    @FXML
    private Button exportPdfTableProductsBtn;

    @FXML
    private JFXListView<String> listViewProduct;

    @FXML
    private TextField txt_search;

    @FXML
    private TextField txt_quantity;

    @FXML
    private Button addPicBtn;

    private ObservableList<String> dataTableView = FXCollections.observableArrayList();
    private ArrayList<String> list_Product = new ArrayList<>();
    private ObservableList<String> dataCombo;
    private ArrayList<Product> list_ProductsObject = new ArrayList<>();
    private ArrayList<IngredientsFood> list_ingredientsFoods = new ArrayList<>();
    private ObservableList<IngredientsFood> dataTable = FXCollections.observableArrayList();
    private ValidateController validateController = new ValidateController();
    private String pathImage;
    private FoodOperation foodOperation = new FoodOperation();
    private IngredientsFoodOperation ingredientsFoodOperation = new IngredientsFoodOperation();
    private static int idFood;

    public void Init(BorderPane mainPane) {
        setDisableReports();
        this.mainPane = mainPane;
        chargeListProduct();
        col_Product_Name.setCellValueFactory(new PropertyValueFactory<>("Product_name"));
        col_Unit.setCellValueFactory(new PropertyValueFactory<>("unity"));
        col_Quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
//        col_Price.setCellValueFactory(new PropertyValueFactory<>("price"));
//        col_Total_Price.setCellValueFactory(new PropertyValueFactory<>("total"));
        dataTableView.setAll(list_Product);
        listViewProduct.setItems(dataTableView);
        txtValidate();
        lbl_nb_food.setText(String.valueOf(ValuesStatic.currentFood.getId()));
        foodName.setText(ValuesStatic.currentFood.getName());
        foodPrice.setText(String.valueOf(ValuesStatic.currentFood.getPrice()));
        Image image = new Image(ValuesStatic.currentFood.getImage_path());
        picFood.setImage(image);
        chargeTableIngredients();
    }

    private void chargeTableIngredients() {
        list_ingredientsFoods = new ArrayList<>();
        for (IngredientsFood ingredientsFood : ingredientsFoodOperation.getAll()) {
            if (ingredientsFood.getId_food() == ValuesStatic.currentFood.getId()) {
                ingredientsFood.setProduct_name(getNameProductById(ingredientsFood.getId_product()));
                ingredientsFood.setUnity(getUnitProductByCobo(ingredientsFood.getProduct_name()));
                list_ingredientsFoods.add(ingredientsFood);
            }
        }
        dataTable.setAll(list_ingredientsFoods);
        table_food.setItems(dataTable);
    }


    private void chargeListProduct() {
        dataCombo = FXCollections.observableArrayList();
        ProductOperation productOperation = new ProductOperation();
        ArrayList<Product> listProducts = productOperation.getAll();
        for (Product listProductFromBD : listProducts) {
            list_Product.add(listProductFromBD.getName());
            list_ProductsObject.add(listProductFromBD);
        }
    }

    String getUnitProductByCobo(String comboChose) {
        for (Product listProductFromDB : list_ProductsObject) {
            String product = listProductFromDB.getName().replace("[", "");
            product = product.replace("]", "");
            if (comboChose.equals(product)) {
                if (listProductFromDB.getStorage_Unit().contains("g"))
                    return "g";
                else if (listProductFromDB.getStorage_Unit().contains("l"))
                    return "cl";
                else if (listProductFromDB.getStorage_Unit().contains("ل"))
                    return "سل";
                else if (listProductFromDB.getStorage_Unit().contains("غ"))
                    return "غ";
            }

        }

        return "";
    }

    int getIdProductByCobo(String comboChose) {
        for (Product listProductFromDB : list_ProductsObject) {
            String product = listProductFromDB.getName().replace("[", "");
            product = product.replace("]", "");
            if (comboChose.equals(product))
                return listProductFromDB.getId();
        }

        return -1;
    }

    String getNameProductById(int idProduct) {
        for (Product listProductFromDB : list_ProductsObject) {
            if (idProduct == listProductFromDB.getId())
                return listProductFromDB.getName();
        }
        return "";
    }

    private void txtValidate() {
        validateController.inputTextValueType(foodName);
        validateController.inputNumberValue(foodPrice);
        validateController.inputNumberValue(txt_quantity);
    }

    @FXML
    void searchProduct(ActionEvent event) {
        // filtrer les données
        ObservableList<String> dataProduct = listViewProduct.getItems();
        FilteredList<String> filteredData = new FilteredList<>(dataProduct, e -> true);
        txt_search.setOnKeyReleased(e -> {
            txt_search.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filteredData.setPredicate((Predicate<? super String>) product -> {
                    if (newValue == null || newValue.isEmpty()) {
                        //loadDataInTable();
                        return true;
                    } else if (product.contains(newValue.toLowerCase())) {
                        return true;
                    }
                    return false;
                });
            });

            SortedList<String> sortedList = new SortedList<>(filteredData);
            //sortedList.comparatorProperty().bind((ObservableValue<? extends Comparator<? super String>>) listViewProduct);
            listViewProduct.setItems(sortedList);//exe
        });
    }

    @FXML
    void addPicFood(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("اختر صورة");
        Stage stage = (Stage) mainPane.getScene().getWindow();
        fileChooser.getExtensionFilters().addAll(
                //new FileChooser.ExtensionFilter()
                new FileChooser.ExtensionFilter("JPG", "*.jpg", "*.png", "*.svg")
                //new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            pathImage = file.toURI().toString();
            picFood.setImage(image);
        } else {
            picFood.setImage(null);
        }
    }

    @FXML
    void deletPicFood(ActionEvent event) {
        picFood.setImage(null);
    }

    @FXML
    void deleteProductFood(ActionEvent event) {
        IngredientsFood ingredientsFood = table_food.getSelectionModel().getSelectedItem();
        if (ingredientsFood != null) {
            getIndex(ingredientsFood.getProduct_name());
            refresh();
        } else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("تحذير ");
            alertWarning.setContentText("يرجى تحديد السلعة المراد حذفها");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("حسنا");
            alertWarning.showAndWait();
        }
    }

    private void getIndex(String productName) {
        for (int i = 0; i < list_ingredientsFoods.size(); i++) {
            if (list_ingredientsFoods.get(i).getProduct_name().equals(productName)) {
                list_ingredientsFoods.remove(i);
                break;
            }
        }
    }

    @FXML
    void insertProductFood(ActionEvent event) {
        if (listViewProduct.getSelectionModel().getSelectedItem() == null) {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("تحذير ");
            alertWarning.setContentText("يرجى اختيار نوع السلعة");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("حسنا");
            alertWarning.showAndWait();
            return;
        }
        if (listViewProduct.getSelectionModel().getSelectedItems() != null && !txt_quantity.getText().isEmpty()) {
            //txt_quantity
            if (!txt_quantity.getText().matches("[0-9]+")) {
                Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                alertWarning.setHeaderText("تحذير ");
                alertWarning.setContentText("يرجى ادخال ارقام");
                Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                okButton.setText("حسنا");
                alertWarning.showAndWait();
                return;
            }
            String product = String.valueOf(listViewProduct.getSelectionModel().getSelectedItems());
            IngredientsFood ingredientsFood = new IngredientsFood();
            product = product.replace("[", "");
            product = product.replace("]", "");
            ingredientsFood.setProduct_name(product);
            ingredientsFood.setUnity(getUnitProductByCobo(product));
            ingredientsFood.setQuantity(Integer.valueOf(txt_quantity.getText()));
            list_ingredientsFoods.add(ingredientsFood);
            txt_quantity.setText("");
            refresh();


        } else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("تحذير ");
            alertWarning.setContentText("يرجى ملأ الخانات اللازمة");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("حسنا");
            alertWarning.showAndWait();
        }
    }

    private void refresh() {
        dataTable.setAll(list_ingredientsFoods);
        table_food.setItems(dataTable);
        saveTableProductsBtn.setDisable(false);
        setDisableReports();
    }

    void setDisableReports() {
        reportTableProductsBtn.setDisable(true);
        exportCsvTableProductsBtn.setDisable(true);
        exportPdfTableProductsBtn.setDisable(true);
    }

    void setEnableReports() {
        reportTableProductsBtn.setDisable(false);
        exportCsvTableProductsBtn.setDisable(false);
        exportPdfTableProductsBtn.setDisable(false);
    }

    @FXML
    void cancel(ActionEvent event) {

        Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirmation.setHeaderText("الغاء اضافة الوجبة");
        alertConfirmation.setContentText("هل انت متأكد من عملية الغاء اضافة الوجبة");
        Button okButton = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setText("موافق");

        Button cancel = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.CANCEL);
        cancel.setText("الغاء");

        alertConfirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.CANCEL) {
                alertConfirmation.close();
            } else if (response == ButtonType.OK) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/foods.fxml"));
                    BorderPane temp = loader.load();
                    FoodsController foodsController = loader.getController();
                    foodsController.Init(temp);
                    mainPane.getChildren().setAll(temp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @FXML
    void saveTableProducts(ActionEvent event) { // Update*
        dataTable = FXCollections.observableArrayList();
        dataTable.setAll(table_food.getItems());
        if (table_food.getItems().size() == 0) {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("تحذير ");
            alertWarning.setContentText("الرجاء ملأ مكونات الوجبة قبل الحفظ");
            Button okkButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okkButton.setText("حسنا");
            alertWarning.showAndWait();
            return;
        }
        if (foodPrice.getText().equals("")) {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("تحذير ");
            alertWarning.setContentText("الرجاء ادخال سعر الوجبة قبل الحفظ");
            Button okkButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okkButton.setText("حسنا");
            alertWarning.showAndWait();
            return;
        }

        if (foodName.getText().equals("")) {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("تحذير ");
            alertWarning.setContentText("الرجاء ادخال اسم الوجبة قبل الحفظ");
            Button okkButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okkButton.setText("حسنا");
            alertWarning.showAndWait();
            return;
        }

        if (picFood == null) {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("تحذير ");
            alertWarning.setContentText("الرجاء ادخال صورة الوجبة قبل الحفظ");
            Button okkButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okkButton.setText("حسنا");
            alertWarning.showAndWait();
            return;
        }
        if (!ValuesStatic.currentFood.getName().equals(foodName.getText())) {
            Food f = new Food();
            f.setName(foodName.getText());
            if (foodOperation.isExist(f)) {
                Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                alertWarning.setHeaderText("تحذير");
                alertWarning.setContentText("هذه الوجبة موجودة");
                Button okkButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                okkButton.setText("حسنا");
                alertWarning.showAndWait();
                return;
            }
        }

        Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirmation.setHeaderText("تأكيد الحفظ");
        alertConfirmation.setContentText("هل انت متأكد من عملية حفظ الوجبة");
        Button okButton = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setText("موافق");

        Button cancel = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.CANCEL);
        cancel.setText("الغاء");

        alertConfirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.CANCEL) {
                alertConfirmation.close();
            } else if (response == ButtonType.OK) {
                // update food
                Food food = new Food();
                food.setName(foodName.getText());
                food.setDescription(foodName.getText() + " " + foodPrice.getText() + ".00 DA");
                food.setImage_path(pathImage);
                food.setPrice(Integer.parseInt(foodPrice.getText()));
                // TODO must to change food of category
                food.setId_category(5);
                foodOperation.update(ValuesStatic.currentFood, food);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                idFood = foodOperation.getIdFood(food);
                // delete old ingredients
                for (IngredientsFood ingredientsFood : ingredientsFoodOperation.getAll()) {
                    ingredientsFoodOperation.delete(ingredientsFood);
                }
                // insert new ingredients
                for (IngredientsFood ingredientsFood : dataTable) {
                    ingredientsFood.setId_food(idFood);
                    ingredientsFood.setId_product(getIdProductByCobo(ingredientsFood.getProduct_name()));
                    ingredientsFoodOperation.insert(ingredientsFood);
                }
                Alert alertWarning = new Alert(Alert.AlertType.INFORMATION);
                alertWarning.setHeaderText("تأكيدالحفظ");
                alertWarning.setContentText("تم تعديل الوجبة بنجاح");
                Button okkButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                okkButton.setText("حسنا");
                alertWarning.showAndWait();
                setEnableReports();
                setTxtFieldsEmpty();
            }
        });
    }

    void setTxtFieldsEmpty() {
        foodName.setText("");
        foodPrice.setText("");
        txt_quantity.setText("");
        saveTableProductsBtn.setDisable(true);
    }

    @FXML
    void exportCsvTableProducts(ActionEvent event) {

    }

    @FXML
    void exportPdfTableProducts(ActionEvent event) {

    }

    @FXML
    void reportTableProducts(ActionEvent event) {

    }
}
