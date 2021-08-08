package Controllers.KitchenChef;

import BddPackage.*;
import Controllers.ValidateController;
import Controllers.ValuesStatic;
import Controllers.newKitchenChef.PrductComposeItemControlleur;
import Models.*;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXListView;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import static Controllers.newKitchenChef.PrductComposeItemControlleur.addProduct;

public class EditFoodController implements Initializable {
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
    private TableColumn<?, ?> col_Type;

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
    @FXML
    private JFXDrawer drawerSlider;
    @FXML
    private TextArea Des;
    @FXML
    private Label FoodCategory;

    private ObservableList<String> dataTableView = FXCollections.observableArrayList();
    private ArrayList<String> list_Product = new ArrayList<>();
    public  ArrayList<String> list_ProductCompose = new ArrayList<>();
    private ObservableList<String> dataCombo;
    private ArrayList<Product> list_ProductsObject = new ArrayList<>();
    private ArrayList<ProductComposite> list_ProductsComposeObject = new ArrayList<>();
    public static ArrayList<IngredientsFood> list_ingredientsFoods = new ArrayList<>();
    private ObservableList<IngredientsFood> dataTable = FXCollections.observableArrayList();
    private ValidateController validateController = new ValidateController();
    private String pathImage;
    private FoodOperation foodOperation = new FoodOperation();
    private IngredientsFoodOperation ingredientsFoodOperation = new IngredientsFoodOperation();
    private FoodProductComposeOperation foodProductComposeOperation = new FoodProductComposeOperation();
    private static int idFood;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addProduct.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                refresh();
            }
        });

    }
    public void Init(BorderPane mainPane) {
        drawerSlider.setVisible(false);
        setDisableReports();
        this.mainPane = mainPane;
        chargeListProduct();
        chargeListProductCompose();
        col_Product_Name.setCellValueFactory(new PropertyValueFactory<>("Product_name"));
        col_Unit.setCellValueFactory(new PropertyValueFactory<>("unity"));
        col_Quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        col_Type.setCellValueFactory(new PropertyValueFactory<>("type"));
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
        pathImage=ValuesStatic.currentFood.getImage_path();
        Des.setText(ValuesStatic.currentFood.getDescription());
        FoodCategory.setText(ValuesStatic.currentFood.getCategory_name());
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
        for (IngredientsFoodProductCompose ingredientsFoodProductCompose : foodProductComposeOperation.getIngredientsFood(ValuesStatic.currentFood.getId())) {
            IngredientsFood ingredientsFood=new IngredientsFood();
            ingredientsFood.setType(1);
            ingredientsFood.setProduct_name(getNameProductComposeById(ingredientsFoodProductCompose.getId_productCopmpose()));
            ingredientsFood.setUnity(getUnitProductComposeByCobo(ingredientsFoodProductCompose.getId_productCopmpose()));
            ingredientsFood.setQuantity((int) ingredientsFoodProductCompose.getQuantity());
            list_ingredientsFoods.add(ingredientsFood);
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
    private void chargeListProductCompose() {
        dataCombo = FXCollections.observableArrayList();
        ProductCompositeOperation productOperation = new ProductCompositeOperation();
        ArrayList<ProductComposite> listProducts = productOperation.getAll();
        for (ProductComposite listProductFromBD : listProducts) {
            list_ProductCompose.add(listProductFromBD.getName());
            list_ProductsComposeObject.add(listProductFromBD);
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


    int getIdProductByCobo(String comboChose,int type) {
        if(type==0){
            ProductOperation productOperation=new ProductOperation();
            Product product=productOperation.GetProduct(comboChose);
            System.out.println("id product ="+product.getId());
            return product.getId();

        }else {
            ProductCompositeOperation productCompositeOperation=new ProductCompositeOperation();
            ProductComposite productComposite=productCompositeOperation.GetProductComposite(comboChose);
            System.out.println("id product compose ="+productComposite.getId());
            return productComposite.getId();
        }

    }

    String getNameProductById(int idProduct) {
        for (Product listProductFromDB : list_ProductsObject) {
            if (idProduct == listProductFromDB.getId())
                return listProductFromDB.getName();
        }
        return "";
    }
    String getNameProductComposeById(int idProduct) {
        ProductCompositeOperation productCompositeOperation=new ProductCompositeOperation();
        ProductComposite productComposite=productCompositeOperation.GetProductComposite(idProduct);
        return productComposite.getName();
    }
    String getUnitProductComposeByCobo(int idProduct) {
        ProductCompositeOperation productCompositeOperation=new ProductCompositeOperation();
        ProductComposite productComposite=productCompositeOperation.GetProductComposite(idProduct);
        return productComposite.getUnity_Food();
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
                food.setDescription(Des.getText());
                food.setImage_path(pathImage);
                food.setPrice(Integer.parseInt(foodPrice.getText()));
                // TODO must to change food of category
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
                for(IngredientsFoodProductCompose ingredientsFoodProductCompose:foodProductComposeOperation.getIngredientsFood(idFood)){
                    foodProductComposeOperation.delete(ingredientsFoodProductCompose);
                }

                // insert new ingredients
                for (IngredientsFood ingredientsFood : dataTable) {
                    ingredientsFood.setId_food(idFood);
                    if(ingredientsFood.getType()==0) {
                        ingredientsFood.setId_product(getIdProductByCobo(ingredientsFood.getProduct_name(), 0));
                        ingredientsFoodOperation.insert(ingredientsFood);
                    }else {
                        IngredientsFoodProductCompose ingredientsFoodProductCompose=new IngredientsFoodProductCompose();
                        ingredientsFoodProductCompose.setId_productCopmpose(getIdProductByCobo(ingredientsFood.getProduct_name(),1));
                        ingredientsFoodProductCompose.setId_food(idFood);
                        ingredientsFoodProductCompose.setQuantity(ingredientsFood.getQuantity());
                        foodProductComposeOperation.insert(ingredientsFoodProductCompose);
                    }
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
    @FXML
    void AfficheProductCompose(ActionEvent event){
        PrductComposeItemControlleur.listProduct=list_ProductCompose;
        drawerSlider.setVisible(true);
        if (drawerSlider.isShown()){
            drawerSlider.close();
            drawerSlider.setVisible(false);
        }else {
            try {
                FXMLLoader loader = new FXMLLoader();
                AnchorPane box=loader.load(getClass().getClassLoader().getResource("Views/KitchenChef/ProducteComposeItem.fxml"));
                box.setMaxHeight(Screen.getPrimary().getVisualBounds().getHeight());
                box.setMaxWidth(Screen.getPrimary().getVisualBounds().getWidth());

                drawerSlider.setSidePane(box);
                drawerSlider.open();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
