package Controllers.KitchenChef;

import BddPackage.*;
import Controllers.ValidateController;
import Models.*;
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

public class AddFoodController {

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
    private TextArea Des;

    @FXML
    private Button addPicBtn;
    @FXML
    private ComboBox IdFoodCategory;


    private ObservableList<String> dataTableView = FXCollections.observableArrayList();
    private ArrayList<String> list_Product = new ArrayList<>();
    private ObservableList<String> dataCombo;
    private ArrayList<Product> list_ProductsObject = new ArrayList<>();
    private ArrayList<FoodCategory> list_Category = new ArrayList<>();
    private ArrayList<IngredientsFood> listProducts = new ArrayList<>();
    private ObservableList<IngredientsFood> dataTable = FXCollections.observableArrayList();
    private ValidateController validateController = new ValidateController();
    private String pathImage;
    private FoodOperation foodOperation = new FoodOperation();
    private IngredientsFoodOperation ingredientsFoodOperation = new IngredientsFoodOperation();
    private  int idFood;


    public void Init(BorderPane mainPane) {

        setDisableReports();
        this.mainPane = mainPane;
        chargeListProduct();
        chargeListCategory();
        col_Product_Name.setCellValueFactory(new PropertyValueFactory<>("Product_name"));
        col_Unit.setCellValueFactory(new PropertyValueFactory<>("unity"));
        col_Quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
  //      col_Price.setCellValueFactory(new PropertyValueFactory<>("price"));
 //       col_Total_Price.setCellValueFactory(new PropertyValueFactory<>("total"));
        dataTableView.setAll(list_Product);
        listViewProduct.setItems(dataTableView);
        picFood.setImage(null);
        txtValidate();
        lbl_nb_food.setText(String.valueOf(foodOperation.getCountFood() + 1));
        idFood=foodOperation.getCountFood() + 1;
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

            this.picFood.setFitHeight(96);
            this. picFood.setFitWidth(250);
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
        for (int i = 0; i < listProducts.size(); i++) {
            if (listProducts.get(i).getProduct_name().equals(productName)) {
                listProducts.remove(i);
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
            listProducts.add(ingredientsFood);
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
        dataTable.setAll(listProducts);
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
    void saveTableProducts(ActionEvent event) {
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
        }else {
        if (foodPrice.getText().equals("")) {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("تحذير ");
            alertWarning.setContentText("الرجاء ادخال سعر الوجبة قبل الحفظ");
            Button okkButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okkButton.setText("حسنا");
            alertWarning.showAndWait();
            return;
        }else{
            if (foodName.getText().equals("")) {
                Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                alertWarning.setHeaderText("تحذير ");
                alertWarning.setContentText("الرجاء ادخال اسم الوجبة قبل الحفظ");
                Button okkButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                okkButton.setText("حسنا");
                alertWarning.showAndWait();
                return;
            }else {

                if  (picFood.getImage() == null) {
                    Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                    alertWarning.setHeaderText("تحذير ");
                    alertWarning.setContentText("الرجاء ادخال صورة الوجبة قبل الحفظ");
                    Button okkButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                    okkButton.setText("حسنا");
                    alertWarning.showAndWait();
                    return;
                }else{
                    Food f = new Food();
                    f.setName(foodName.getText());
                    if (foodOperation.isExist(f)){
                        Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                        alertWarning.setHeaderText("تحذير");
                        alertWarning.setContentText("هذه الوجبة موجودة");
                        Button okkButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                        okkButton.setText("حسنا");
                        alertWarning.showAndWait();
                        return;
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
                            // inert food
                            Food food = new Food();
                            int idCategory=getIdCategory(IdFoodCategory.getSelectionModel().getSelectedItem().toString());
                            food.setId(getIdFood());
                            food.setName(foodName.getText());
                            food.setDescription(Des.getText());
                            food.setImage_path(pathImage);
                            food.setPrice(Integer.parseInt(foodPrice.getText()));
                            // TODO must to change food of category
                            food.setId_category(idCategory);
                            foodOperation.insert(food);
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            // insert ingredients
                            for (IngredientsFood ingredientsFood : dataTable) {
                                ingredientsFood.setId_food(idFood);
                                ingredientsFood.setId_product(getIdProductByCobo(ingredientsFood.getProduct_name()));
                                ingredientsFoodOperation.insert(ingredientsFood);
                            }
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/foods.fxml"));
                                BorderPane temp = loader.load();
                                FoodsController foodsController = loader.getController();
                                foodsController.Init(temp);
                                mainPane.getChildren().setAll(temp);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Alert alertWarning = new Alert(Alert.AlertType.INFORMATION);
                            alertWarning.setHeaderText("تأكيدالحفظ");
                            alertWarning.setContentText("تم اضافة الوجبة بنجاح");
                            Button okkButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                            okkButton.setText("حسنا");
                            alertWarning.showAndWait();
                            setEnableReports();
                            setTxtFieldsEmpty();

                        }
                    });
                }
            }
         }
        }




    }
    private void chargeListCategory() {
        dataCombo = FXCollections.observableArrayList();

       FoodCategoryOperation foodCategoryOperation=new FoodCategoryOperation();
        ArrayList<FoodCategory> foodCategory= foodCategoryOperation.getAll();
        for (FoodCategory listProviderFromDB : foodCategory) {
            dataCombo.add(listProviderFromDB.getName());

        }
        IdFoodCategory.setItems(dataCombo);

    }

    void setTxtFieldsEmpty() {
        foodName.setText("");
        foodPrice.setText("");
        txt_quantity.setText("");
        saveTableProductsBtn.setDisable(true);
    }
    private int getIdCategory(String s){
        int  Idcatecory=0;
        FoodCategoryOperation foodCategoryOperation=new FoodCategoryOperation();
        ArrayList<FoodCategory> foodCategory= foodCategoryOperation.getAll();
        for (FoodCategory listProviderFromDB : foodCategory) {
            if(listProviderFromDB.getName().equals(s))
                Idcatecory= listProviderFromDB.getId();

        }
        return Idcatecory;

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
    private int getIdFood(){
        int id=0;
        ArrayList<Food> list=new ArrayList<>();
        list=foodOperation.getAll();
        System.out.println(list);
        if(list.size()==0)
            id=1;
        else{
            Food food=new Food();
            food=list.get(list.size()-1);
            id=food.getId()+1;
        }




        return id;
    }
}
