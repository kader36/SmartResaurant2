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
import javafx.fxml.FXMLLoader;
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
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UpdateFoodController implements Initializable {
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
    private ProductOperation productOperation;
    private FoodCategoryOperation foodCategoryOperation;
    private ArrayList<Product> listProduct;
    private ObservableList<String> data_list= FXCollections.observableArrayList();
    private ObservableList<String> data_Combo= FXCollections.observableArrayList();
    private ArrayList<IngredientsFood> listIngredients;
    private ArrayList<FoodCategory> listFoodCategory;
    private ObservableList<IngredientsFood> dataIngredients= FXCollections.observableArrayList();
    private String imagePath = "/home/nail/IdeaProjects/SmartResaurant/src/FoodImage";
    private  int idFood;
    private Food oldfood;
    private IngredientsFood oldIngredientsFood;
    private boolean change = false;




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ValidateController validateController = new ValidateController();
        validateController.inputTextValueType(txt_food_name);
        validateController.inputNumberValueType(txt_food_Price);
        validateController.inputNumberValueType(txt_quantity_Product);
        foodOperation = new FoodOperation();
        ingredientsFoodOperation = new IngredientsFoodOperation();
        productOperation = new ProductOperation();
        foodCategoryOperation = new FoodCategoryOperation();
        listFoodCategory = foodCategoryOperation.getAll();
        listProduct = productOperation.getAll();

        ChargeComboCategoryFood();
        chargeListViewProduvt();
        col_Product_name.setCellValueFactory(new PropertyValueFactory<>("Product_name"));
        col_Product_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tableFoodProduct.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    oldIngredientsFood = tableFoodProduct.getSelectionModel().getSelectedItem();
                    oldIngredientsFood.setId_food(idFood);
                    listViewProduct.getSelectionModel().select(oldIngredientsFood.getProduct_name());
                    txt_quantity_Product.setText(String.valueOf(oldIngredientsFood.getQuantity()));
                }
            }
        });


    }


    private void ChargeTxt() {
        txt_food_name.setText(oldfood.getName());
        txt_description.setText(oldfood.getDescription());
        txt_food_Price.setText(String.valueOf(oldfood.getPrice()));

    }

    public void Init(AnchorPane mainPane,int idFood){
        this.mainPane = mainPane;
        this.idFood = idFood;
        oldfood = foodOperation.getFoodByID(idFood);
        ChargeTxt();
        loadImage(oldfood.getImage_path());
        refresh();

    }

    private void loadImage(String imagePath) {
        File file = new File(imagePath);
        try {
            image = ImageIO.read(file);
            img_food.setImage(SwingFXUtils.toFXImage(image, null));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void chargeListViewProduvt() {
        for (int i = 0; i < listProduct.size(); i++) {
            data_list.add(listProduct.get(i).getName());

        }
        listViewProduct.setItems(data_list);

    }




    @FXML
    void insertProduct(ActionEvent event) {
        String productName  = listViewProduct.getSelectionModel().getSelectedItem();
        if (!txt_quantity_Product.getText().equals("") && productName != null){
            IngredientsFood ingredientsFood = new IngredientsFood();
            ingredientsFood.setId_food(idFood);
            ingredientsFood.setId_product(getProductID(productName));
            ingredientsFood.setQuantity(Integer.parseInt(txt_quantity_Product.getText()));
            ingredientsFoodOperation.insert(ingredientsFood);
            refresh();
        }
    }

    @FXML
    void updateProduct(ActionEvent event) {
        if (oldIngredientsFood != null &&!ComboCategoryFood.getValue().isEmpty() && !txt_quantity_Product.getText().isEmpty()){
            IngredientsFood newIngredientsFood = new IngredientsFood();
            newIngredientsFood.setId_product(getProductID(ComboCategoryFood.getValue()));
            newIngredientsFood.setQuantity(Integer.parseInt(txt_quantity_Product.getText()));
            ingredientsFoodOperation.update(newIngredientsFood,oldIngredientsFood);
            oldIngredientsFood = null;
            refresh();
        }
    }

    @FXML
    void deleteProduct(ActionEvent event) {
        IngredientsFood ingredientsFood = tableFoodProduct.getSelectionModel().getSelectedItem();
        if (ingredientsFood != null){
            ingredientsFoodOperation.delete(ingredientsFood);
            refresh();
        }
    }
    @FXML
    void saveFoodUpdate(ActionEvent event) {
        if (!ComboCategoryFood.getValue().isEmpty()) {
            if (!txt_description.getText().equals("") && !txt_food_name.getText().equals("") && !txt_food_Price.getText().equals("") && img_food != null
            && !tableFoodProduct.getItems().isEmpty())
            {
                Food newfood = new Food();
                newfood.setId_category(getCategoryFoodID(ComboCategoryFood.getValue()));
                newfood.setName(txt_food_name.getText());
                newfood.setDescription(txt_description.getText());
                newfood.setPrice(Integer.valueOf(txt_food_Price.getText()));
                if (change){
                    File file = new File(oldfood.getImage_path());
                    file.delete();
                    newfood.setImage_path(imagePath+txt_food_name.getText() +".jpg");
                    saveToFile(txt_food_name.getText());
                }else {
                    newfood.setImage_path(oldfood.getImage_path());
                }
                byte[]photo=null;
                try {
                    String url=oldfood.getImage_path();
                    url = url.substring(5, url.length());
                    BufferedImage bImage = ImageIO.read(new File(url));
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    ImageIO.write(bImage, "jpg", bos );
                    photo=bos.toByteArray();

                } catch (IOException e) {
                    System.err.println("file reading error");
                }
                newfood.setIMAGE(photo);
               boolean bool= foodOperation.update(oldfood,newfood);
               if(bool==true)
                homePage();
            }
        }
    }






    private void refresh() {
        listIngredients = ingredientsFoodOperation.getIngredientsFood(idFood);
        dataIngredients.setAll(listIngredients);
        tableFoodProduct.setItems(dataIngredients);
    }
    private void ChargeComboCategoryFood() {
        for (int i = 0; i < listFoodCategory.size(); i++) {
            data_Combo.add(listFoodCategory.get(i).getName());
        }
        ComboCategoryFood.setValue(listFoodCategory.get(0).getName());

        ComboCategoryFood.setItems(data_Combo);
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
                change = true;

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
        for (int i = 0; i < listFoodCategory.size(); i++) {
            if (listFoodCategory.get(i).getName().equals(category)){
                idCategory = listFoodCategory.get(i).getId();
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

    private void homePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/KitchenChef/FoodMainView.fxml"));
            AnchorPane temp = loader.load();
            FoodMainController controller = loader.getController();

            controller.Init(mainPane);
            mainPane.getChildren().setAll(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
