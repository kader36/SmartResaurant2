package Controllers.KitchenChef;

import BddPackage.*;
import Controllers.ValidateController;
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
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import static Controllers.newKitchenChef.PrductComposeItemControlleur.addProduct;

public class AddFoodController implements Initializable {

    @FXML
    private BorderPane mainPane;

    @FXML
    private Pane panefood;


    @FXML
    private TextField foodName;

    @FXML
    private Label lbl_nb_food;

    @FXML
    private TextField foodPrice;

    @FXML
    private ImageView picFood;

    @FXML
    private  TableView<IngredientsFood> table_food;

    @FXML
    private TableColumn<?, ?> col_Product_Name;

    @FXML
    private TableColumn<?, ?> col_Unit;

    @FXML
    private TableColumn<?, ?> col_Quantity;

    @FXML
    private TableColumn<?, ?> col_Type;


    @FXML
    private  Button saveTableProductsBtn;

    @FXML
    private ImageView cancelTableProduct;

    @FXML
    private  Button reportTableProductsBtn;

    @FXML
    private  Button exportCsvTableProductsBtn;

    @FXML
    private  Button exportPdfTableProductsBtn;

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
    @FXML
    private JFXDrawer drawerSlider;
    @FXML
    private JFXListView<String> listViewProductCompose;


    private ObservableList<String> dataTableView = FXCollections.observableArrayList();
    private ArrayList<String> list_Product = new ArrayList<>();
    public  ArrayList<String> list_ProductCompose = new ArrayList<>();
    private ObservableList<String> dataCombo;
    private ArrayList<Product> list_ProductsObject = new ArrayList<>();
    private ArrayList<ProductComposite> list_ProductsComposeObject = new ArrayList<>();
    private ArrayList<FoodCategory> list_Category = new ArrayList<>();
    public static ArrayList<IngredientsFood> listProducts = new ArrayList<>();
    public static ObservableList<IngredientsFood> dataTable = FXCollections.observableArrayList();
    private ValidateController validateController = new ValidateController();
    private String pathImage;
    private FoodOperation foodOperation = new FoodOperation();
    private IngredientsFoodOperation ingredientsFoodOperation = new IngredientsFoodOperation();
    private FoodProductComposeOperation ingredientsProductCompsiteOperation = new FoodProductComposeOperation();
    private int idFood;

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
        //setDisableReports();
        this.mainPane = mainPane;
        chargeListProduct();
        chargeListProductCompose();
        chargeListCategory();
        col_Product_Name.setCellValueFactory(new PropertyValueFactory<>("Product_name"));
        col_Unit.setCellValueFactory(new PropertyValueFactory<>("unity"));
        col_Quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        col_Type.setCellValueFactory(new PropertyValueFactory<>("type"));
        //       col_Total_Price.setCellValueFactory(new PropertyValueFactory<>("total"));
        dataTableView.setAll(list_Product);
        listViewProduct.setItems(dataTableView);


        picFood.setImage(null);
        txtValidate();
        lbl_nb_food.setText(String.valueOf(foodOperation.getCountFood() + 1));
        idFood = foodOperation.getCountFood() + 1;
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
        ProductOperation productOperation=new ProductOperation();
        Product product=productOperation.GetProduct(comboChose);

        return product.getUnity_Food();
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


    private void txtValidate() {

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
        panefood.setVisible(false);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("اختر صورة");
        Stage stage = (Stage) addPicBtn.getScene().getWindow();
        fileChooser.getExtensionFilters().addAll(
                //new FileChooser.ExtensionFilter()
                new FileChooser.ExtensionFilter("JPG", "*.jpg", "*.png", "*.svg","*.jpeg")
                //new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        picFood.setFitHeight(100);
        picFood.setFitWidth(200);
        File file = fileChooser.showOpenDialog(stage.getScene().getWindow());
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            pathImage = file.toURI().toString();
            Rectangle clip = new Rectangle();
            clip.setWidth(200);
            clip.setHeight(100);
            clip.setArcHeight(10);
            clip.setArcWidth(10);
            //clip.setStroke(Color.BLACK);
            picFood.setClip(clip);

            // snapshot the rounded image.
            SnapshotParameters parameters = new SnapshotParameters();
            parameters.setFill(Color.TRANSPARENT);
            WritableImage myimage = picFood.snapshot(parameters, null);

            // remove the rounding clip so that our effect can show through.
            picFood.setEffect(new DropShadow(3, Color.BLACK));
            picFood.setImage(myimage);
            picFood.setImage(image);
            System.out.println("setWidth" + picFood.getFitWidth());
            System.out.println("setHeight" + picFood.getFitHeight());
        } else {
            picFood.setImage(null);
        }
    }

    @FXML
    void deletPicFood(ActionEvent event) {
        panefood.setVisible(true);
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
            ingredientsFood.setType(0);
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

    public  void refresh() {
        dataTable.setAll(listProducts);
        table_food.setItems(dataTable);
        saveTableProductsBtn.setDisable(false);
        //setDisableReports();
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
        } else {
            if (foodPrice.getText().equals("")) {
                Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                alertWarning.setHeaderText("تحذير ");
                alertWarning.setContentText("الرجاء ادخال سعر الوجبة قبل الحفظ");
                Button okkButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                okkButton.setText("حسنا");
                alertWarning.showAndWait();
                return;
            } else {
                if (foodName.getText().equals("")) {
                    Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                    alertWarning.setHeaderText("تحذير ");
                    alertWarning.setContentText("الرجاء ادخال اسم الوجبة قبل الحفظ");
                    Button okkButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                    okkButton.setText("حسنا");
                    alertWarning.showAndWait();
                    return;
                } else {

                    if (picFood.getImage() == null) {
                        Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                        alertWarning.setHeaderText("تحذير ");
                        alertWarning.setContentText("الرجاء ادخال صورة الوجبة قبل الحفظ");
                        Button okkButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                        okkButton.setText("حسنا");
                        alertWarning.showAndWait();
                        return;
                    } else {
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
                                byte[]photo=null;
                                try {

                                    String url=pathImage;
                                    url = url.substring(5, url.length());
                                    BufferedImage bImage = ImageIO.read(new File(url));
                                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                    ImageIO.write(bImage, "jpg", bos );
                                    photo=bos.toByteArray();

                                } catch (IOException e) {
                                    System.err.println("file reading error");
                                }
                                Food food = new Food();
                                int idCategory = getIdCategory(IdFoodCategory.getSelectionModel().getSelectedItem().toString());
                                food.setId(getIdFood());
                                food.setName(foodName.getText());
                                food.setDescription(Des.getText());
                                food.setImage_path(pathImage);
                                food.setPrice(Integer.parseInt(foodPrice.getText()));
                                food.setIMAGE(photo);
                                // TODO must to change food of category
                                food.setId_category(idCategory);
                                boolean bool=foodOperation.insert(food);
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                // insert ingredients
                                if(bool==true) {
                                    for (IngredientsFood ingredientsFood : dataTable) {
                                        IngredientsFoodProductCompose ingredientsFoodProductCompose = new IngredientsFoodProductCompose();
                                        ingredientsFoodProductCompose.setQuantity(ingredientsFood.getQuantity());
                                        ingredientsFoodProductCompose.setId_food(food.getId());
                                        ingredientsFood.setId_food(food.getId());

                                        if (ingredientsFood.getType() == 0) {
                                            ingredientsFood.setId_product(getIdProductByCobo(ingredientsFood.getProduct_name(), 0));
                                            ingredientsFoodOperation.insert(ingredientsFood);
                                            System.out.println("**");
                                        } else {
                                            ingredientsFoodProductCompose.setId_productCopmpose(getIdProductByCobo(ingredientsFood.getProduct_name(), 1));
                                            ingredientsProductCompsiteOperation.insert(ingredientsFoodProductCompose);
                                            System.out.println("***");
                                        }


                                    }
                                    dataTable.clear();
                                    listProducts.clear();
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
                }
            }
        }


    }

    private void chargeListCategory() {
        dataCombo = FXCollections.observableArrayList();

        FoodCategoryOperation foodCategoryOperation = new FoodCategoryOperation();
        ArrayList<FoodCategory> foodCategory = foodCategoryOperation.getAll();
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

    private int getIdCategory(String s) {
        int Idcatecory = 0;
        FoodCategoryOperation foodCategoryOperation = new FoodCategoryOperation();
        ArrayList<FoodCategory> foodCategory = foodCategoryOperation.getAll();
        for (FoodCategory listProviderFromDB : foodCategory) {
            if (listProviderFromDB.getName().equals(s))
                Idcatecory = listProviderFromDB.getId();

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

    private int getIdFood() {
        int id = 0;
        ArrayList<Food> list = new ArrayList<>();
        list = foodOperation.getAll();
        System.out.println(list);
        if (list.size() == 0)
            id = 1;
        else {
            Food food = new Food();
            food = list.get(list.size() - 1);
            id = food.getId() + 1;
        }



        return id;
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
