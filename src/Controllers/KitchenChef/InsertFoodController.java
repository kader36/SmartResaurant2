package Controllers.KitchenChef;

import BddPackage.FoodCategoryOperation;
import BddPackage.FoodOperation;
import BddPackage.IngredientsFoodOperation;
import BddPackage.ProductOperation;
import Controllers.ValidateController;
import Models.Food;
import Models.FoodCategory;
import Models.IngredientsFood;
import Models.Product;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class InsertFoodController implements Initializable {
    @FXML
    private JFXComboBox<String> ComboCategoryFood;
    @FXML
    private AnchorPane mainPane;

    @FXML
    private JFXButton loadImagebtn;

    @FXML
    private ListView<String> listViewProduct;

    @FXML
    private TableView<IngredientsFood> tableFoodProduct;

    @FXML
    private TableColumn<IngredientsFood, Integer> col_Product_quantity;

    @FXML
    private TableColumn<IngredientsFood, String> col_Product_name;

    @FXML
    private JFXTextField txt_quantity_Product;

    @FXML
    private JFXTextField txt_search_product;

    @FXML
    private ImageView img_food;

    @FXML
    private JFXTextField txt_food_name;

    @FXML
    private JFXTextField txt_food_Price;

    @FXML
    private JFXTextArea txt_description;

    private BufferedImage image;
    private IngredientsFoodOperation ingredientsFoodOperation;
    private FoodOperation foodOperation;
    private FoodCategoryOperation foodCategoryOperation;
    private ProductOperation productOperation;
    private ArrayList<Product> listProduct;
    private ArrayList<FoodCategory> listCategoryFood;
    private ObservableList<String> data_list= FXCollections.observableArrayList();
    private ObservableList<String> data_combo= FXCollections.observableArrayList();
    private ArrayList<IngredientsFood> listIngredients;
    private ObservableList<IngredientsFood> dataIngredients= FXCollections.observableArrayList();
    private String imagePath = "/home/nail/IdeaProjects/SmartResaurant/src/FoodImage";





    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ValidateController validateController = new ValidateController();
        validateController.inputTextValueType(txt_food_name);
        validateController.inputNumberValueType(txt_food_Price);
        validateController.inputNumberValueType(txt_quantity_Product);
        foodOperation = new FoodOperation();
        ingredientsFoodOperation = new IngredientsFoodOperation();
        listIngredients = new ArrayList<>();
        productOperation = new ProductOperation();
        listProduct = productOperation.getAll();
        foodCategoryOperation = new FoodCategoryOperation();
        listCategoryFood = foodCategoryOperation.getAll();
        chargeListViewProduvt();
        ChargeComboCategoryFood();
        col_Product_name.setCellValueFactory(new PropertyValueFactory<>("Product_name"));
        col_Product_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tableFoodProduct.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    IngredientsFood oldIngredientsFood = tableFoodProduct.getSelectionModel().getSelectedItem();
                    listViewProduct.getSelectionModel().select(oldIngredientsFood.getProduct_name());
                    txt_quantity_Product.setText(String.valueOf(oldIngredientsFood.getQuantity()));
                }
            }
        });

    }



    public void Init(AnchorPane mainPane){
        this.mainPane = mainPane;
    }

    private void chargeListViewProduvt() {
        for (int i = 0; i < listProduct.size(); i++) {
            data_list.add(listProduct.get(i).getName());

        }
        listViewProduct.setItems(data_list);

    }
    private void ChargeComboCategoryFood() {
        for (int i = 0; i < listCategoryFood.size(); i++) {
            data_combo.add(listCategoryFood.get(i).getName());
        }
        ComboCategoryFood.setItems(data_combo);
    }

    @FXML
    void AddFood(ActionEvent event) {
        if (!ComboCategoryFood.getValue().isEmpty()) {
            System.out.println(ComboCategoryFood.getValue());
            if (!txt_description.getText().equals("") && !txt_food_name.getText().equals("") && !txt_food_Price.getText().equals("") && img_food != null
               && !tableFoodProduct.getItems().isEmpty()) {
                Food food = new Food();
                food.setId_category(getCategoryFoodID(ComboCategoryFood.getValue()));
                food.setName(txt_food_name.getText());
                food.setDescription(txt_description.getText());
                food.setPrice(Integer.valueOf(txt_food_Price.getText()));
                food.setImage_path(imagePath + txt_food_name.getText() + ".jpg");
                foodOperation.insert(food);
                chargeIngredientFoodTable(foodOperation.lastID());
                saveToFile(txt_food_name.getText());
                txtVide();
            }
        }
    }

    private void chargeIngredientFoodTable(int idFood) {
     IngredientsFood ingredientsFood ;
        for (int i = 0; i < listIngredients.size(); i++) {
            ingredientsFood = new IngredientsFood();
            ingredientsFood.setId_food(idFood);
            ingredientsFood.setId_product(getProductID(listIngredients.get(i).getProduct_name()));
            ingredientsFood.setQuantity(listIngredients.get(i).getQuantity());
            ingredientsFoodOperation.insert(ingredientsFood);
        }

    }

    @FXML
    void insertProduct(ActionEvent event) {
        String productName  = listViewProduct.getSelectionModel().getSelectedItem();
        if (!txt_quantity_Product.getText().equals("") && productName != null){
            IngredientsFood ingredientsFood = new IngredientsFood(productName,Integer.parseInt(txt_quantity_Product.getText()));
            listIngredients.add(ingredientsFood);
            refresh();
        }
    }
    @FXML
    void updateProduct(ActionEvent event) {
     String productName = listViewProduct.getSelectionModel().getSelectedItem();
     int index;
     if (productName !=null && !txt_quantity_Product.getText().isEmpty()){
         index = indexIngredientFoodList(productName);
         listIngredients.get(index).setQuantity(Integer.parseInt(txt_quantity_Product.getText()));
         listIngredients.get(index).setProduct_name(productName);
         refresh();
     }
    }

    @FXML
    void deleteProduct(ActionEvent event) {
        IngredientsFood ingredientsFood = tableFoodProduct.getSelectionModel().getSelectedItem();
        if (ingredientsFood != null){
            for (int i = 0; i < listIngredients.size(); i++) {
                if (listIngredients.get(i).getProduct_name().equals(ingredientsFood.getProduct_name())){
                    listIngredients.remove(i);
                    refresh();
                    break;
                }
            }
        }
    }
    private int indexIngredientFoodList(String productName){
        int index = 0;
        for (int i = 0; i < listIngredients.size(); i++) {
            if (listIngredients.get(i).getProduct_name().equals(productName)){
                index = i;
                break;
            }
        }
        return index;
    }

    private void refresh() {
        dataIngredients.setAll(listIngredients);
        tableFoodProduct.setItems(dataIngredients);
    }
    private void txtVide(){
        tableFoodProduct.setItems(null);
        txt_quantity_Product.setText("");
        txt_food_name.setText("");
        txt_description.setText("");
        txt_food_Price.setText("");
        image = null;
        img_food.setImage(null);

    }


    @FXML
    void uploadImage(ActionEvent event) {

        FileChooser fileChooser=new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All","*.png","*.jpg"));
        File file=fileChooser.showOpenDialog(null);
        if (file !=  null) {
            try {
                image = ImageIO.read(file);
                img_food.setImage(SwingFXUtils.toFXImage(image, null));
                System.out.println(file.getPath());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
    private int getProductID(String productName){
        int idProduct = 0;

        for (Product aListProduct : listProduct) {
            if (aListProduct.getName().equals(productName)) {
                idProduct = aListProduct.getId();
                break;
            }
        }
        return idProduct;
    }
    private int getCategoryFoodID(String category){
        int idCategory = 0;
        for (int i = 0; i < listCategoryFood.size(); i++) {
            if (listCategoryFood.get(i).getName().equals(category)){
                idCategory = listCategoryFood.get(i).getId();
                break;
            }
        }
        return idCategory;
    }
    private   void saveToFile(String imageFoodName) {
        // path of the folder to save image in it .
        File outputFile = new File(imagePath+imageFoodName+".jpg");
        try {
            ImageIO.write(image,"jpg",outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

