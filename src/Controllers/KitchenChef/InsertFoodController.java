package Controllers.KitchenChef;

import BddPackage.FoodOperation;
import BddPackage.IngredientsFoodOperation;
import BddPackage.ProductOperation;
import Controllers.ValidateController;
import Models.Food;
import Models.IngredientsFood;
import Models.Product;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
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
    private ProductOperation productOperation;
    private ArrayList<Product> listProduct;
    private ObservableList<String> data_list= FXCollections.observableArrayList();
    private ArrayList<IngredientsFood> listIngredients;
    private ObservableList<IngredientsFood> dataIngredients= FXCollections.observableArrayList();
    private String imagePath = "/home/nail/IdeaProjects/RestaurantTest/src/FoodImage/";




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
        chargeListViewProduvt();
        col_Product_name.setCellValueFactory(new PropertyValueFactory<>("Product_name"));
        col_Product_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

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

    @FXML
    void AddFood(ActionEvent event) {
        if (!txt_description.getText().equals("") && !txt_food_name.getText().equals("") && !txt_food_Price.getText().equals("") && img_food != null){
            Food food = new Food();
            food.setId_category(1);
            food.setName(txt_food_name.getText());
            food.setDescription(txt_description.getText());
            food.setPrice(Integer.valueOf(txt_food_Price.getText()));
            food.setImage_path(imagePath+txt_food_name.getText()+".jpg");
            foodOperation.insert(food);
            chargeIngredientFoodTable(foodOperation.lastID());
            saveToFile(txt_food_name.getText());
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
    void deleteProduct(ActionEvent event) {
        int index = 0;
        IngredientsFood ingredientsFood = tableFoodProduct.getSelectionModel().getSelectedItem();
        if (ingredientsFood != null){
            for (int i = 0; i < listIngredients.size(); i++) {
                if (listIngredients.get(i).getProduct_name().equals(ingredientsFood.getProduct_name())){
                    listIngredients.remove(i);
                    refresh();
                    break;
                }
                index++;
            }
        }
        System.out.println(index);
    }



    @FXML
    void insertProduct(ActionEvent event) {
        String productName  = listViewProduct.getSelectionModel().getSelectedItem();
        if (!txt_quantity_Product.equals("") && productName != null){
            IngredientsFood ingredientsFood = new IngredientsFood(productName,Integer.parseInt(txt_quantity_Product.getText()));
            listIngredients.add(ingredientsFood);
            refresh();
        }
    }

    private void refresh() {
        dataIngredients.setAll(listIngredients);
        tableFoodProduct.setItems(dataIngredients);
    }

    @FXML
    void updateProduct(ActionEvent event) {
        String productName  = listViewProduct.getSelectionModel().getSelectedItem();

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
            if (aListProduct.getName() == productName) {
                idProduct = aListProduct.getId();
                break;
            }
        }
        return idProduct;
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

