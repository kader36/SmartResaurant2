package Controllers.Tables;

import BddPackage.*;
import Models.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FoodBuyController implements Initializable {
    @FXML
    private GridPane itemsGridView;
    @FXML
    private GridPane goodItemsGrid;
    @FXML
    private  GridPane facturItemsGrid;
    @FXML
    private Text txt_total;
    @FXML
    private Button payMoneyButton;
    public static BooleanProperty loadDataFood = new SimpleBooleanProperty();
    public static BooleanProperty addfood = new SimpleBooleanProperty();
    public static BooleanProperty deletFood = new SimpleBooleanProperty();
    private ArrayList<FacturItem> listfactur = new ArrayList<>();
     ArrayList<Food> foodsList = new ArrayList<>();
     ArrayList<Food> temporaryFoodsList = new ArrayList<>();
     public static int ID,idfood ,qentity;
    private int columnF = 1;
    private int rowF = 1;
    private int Total ;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDataCategory();
        loaddefaultDataFood();
        loadDataFood.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                loadDataFood();
            }
        });
        addfood.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                FoodOperation foodOperation = new FoodOperation();
                Food food = foodOperation.getFoodByID(idfood);
                FacturItem facturItem = new FacturItem();
                facturItem.setFood(food);
                facturItem.setQentity(qentity);
                listfactur.add(facturItem);
                Addfood(qentity, food);
                int total = 0;
                for (int i = 0; i < listfactur.size(); i++) {
                    total += listfactur.get(i).getQentity() * listfactur.get(i).getFood().getPrice();
                }
                txt_total.setText(String.valueOf(total+",00"));
                Total = total;
            }
        });
        deletFood.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                facturItemsGrid.getChildren().clear();
                int total = 0;
                for (int i = 0; i < listfactur.size(); i++) {
                    if (listfactur.get(i).getFood().getId() == idfood)
                        listfactur.remove(i);
                }
                for (int i = 0; i < listfactur.size(); i++) {
                    total += listfactur.get(i).getQentity() * listfactur.get(i).getFood().getPrice();
                }
                addfood(listfactur);
                txt_total.setText(String.valueOf(total+",00"));

            }
        });
         payMoneyButton.setOnAction(actionEvent -> {
             Orders orders=new Orders();
             ArrayList<Orders> list = new ArrayList<>();
             ArrayList<FoodOrder> listfood =new ArrayList<>();
             OrdersOperation ordersOperation=new OrdersOperation();
             list=ordersOperation.getAllOrder();
             orders.setId(list.get(list.size()-1).getId()+1);
             orders.setId_table(99);
             orders.setPrice(Total);
             for (int i = 0; i < listfactur.size(); i++) {
                 FoodOrder foodOrder=new FoodOrder();
                 foodOrder.setId_food(listfactur.get(i).getFood().getId());
                 foodOrder.setQuantity(listfactur.get(i).getQentity());
                 foodOrder.setId_order(orders.getId());
                 listfood.add(foodOrder);
             }
             orders.setFoodsList(listfood);
             addOrder(orders);
             facturItemsGrid.getChildren().clear();
             goodItemsGrid.getChildren().clear();
             loaddefaultDataFood();
             txt_total.setText("00");
             Total=0;
             listfactur.clear();
             Connet connet = new Connet();
             Connection con = connet.connection();
             try {
                 String report = System.getProperty("user.dir") + "/SmartResaurant/src/Report/print.jrxml";
                 JasperDesign jasperDesign = JRXmlLoader.load(report);
                 String sqlCmd = "SELECT orders.ORDER_TIME,orders.ORDER_PRICE,food_order.ORDER_QUANTITY,food.FOOD_NAME,food.FOOD_PRICE,orders.ID_TABLE_ORDER ,settings.Name,settings.PhoneNambe1,settings.PhoneNambe2,settings.Adress\n" +
                         "FROM `orders`,food_order,food,settings\n" +
                         "WHERE orders.ID_ORDER=food_order.ID_ORDER and food_order.ID_FOOD=food.ID_FOOD and orders.ID_ORDER="+orders.getId();
                 JRDesignQuery jrDesignQuery = new JRDesignQuery();
                 jrDesignQuery.setText(sqlCmd);
                 jasperDesign.setQuery(jrDesignQuery);
                 JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
                 Connection connection = null;

                 JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, con);

                 JasperPrintManager.printReport(jasperPrint,false);

             } catch (JRException e) {
                 e.printStackTrace();
             }
             Alert alertWarning = new Alert(Alert.AlertType.INFORMATION);
             alertWarning.setContentText("تم اضافة الطلب بنجاح");
             Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
             okButton.setText("حسنا");
             alertWarning.showAndWait();
       });
    }




    void loadDataCategory(){
        int column = 1;
        int row = 1;
        FoodCategoryOperation foodCategoryOperation=new FoodCategoryOperation();
        ArrayList<FoodCategory> list=foodCategoryOperation.getAll();
        for(int i=0;i!=list.size();i++){
            FoodCategory foodCategory=list.get(i);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Views/TablesViews/CategoryItem.fxml"));
            AnchorPane anchorPane = null;
            try {
                anchorPane = loader.load();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            CategoryItemController categoryItemController = loader.getController();
            // search for the price and title and change them here.
            categoryItemController.setDetailsItemdData(
                    foodCategory.getId(),
                    foodCategory.getName()
            );
            anchorPane.setEffect(new DropShadow(20, Color.grayRgb(190)));
            itemsGridView.add(anchorPane,column,row);
            row++;
            itemsGridView.setMargin(anchorPane,new Insets(5));

        }
    }
    void loadDataFood(){
        goodItemsGrid.getChildren().clear();
        // get food data from database.
        if(ID!=0) {
            FoodOperation databaseConnector = new FoodOperation();
            foodsList = databaseConnector.getFoodByCategory(ID);
            temporaryFoodsList = databaseConnector.getAll();

            // set the gridItems.
            int tableOrderColumn = 0;
            int tableOrderRow = 1;

            for (int foodIndex = 0; foodIndex < foodsList.size(); foodIndex++) {

                if (tableOrderColumn == 3) {
                    tableOrderColumn = 0;
                    tableOrderRow = tableOrderRow + 1;
                }

                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/Views/TablesViews/foodItem.fxml"));
                    AnchorPane anchorPane2 = loader.load();
                    FoodItemController foodItemController = loader.getController();
                    foodItemController.setItemData(
                            foodsList.get(foodIndex).getName(),
                            foodsList.get(foodIndex).getImage_path(),
                            foodsList.get(foodIndex).getId()
                    );


                    goodItemsGrid.add(anchorPane2, tableOrderColumn++, tableOrderRow);
                    //column = column + 1;
                    // set the width.
                    goodItemsGrid.setMinWidth(Region.USE_COMPUTED_SIZE);
                    goodItemsGrid.setMaxWidth(Region.USE_PREF_SIZE);
                    goodItemsGrid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                    // set the height.
                    goodItemsGrid.setMinHeight(Region.USE_COMPUTED_SIZE);
                    goodItemsGrid.setMaxHeight(Region.USE_PREF_SIZE);
                    goodItemsGrid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                    Rectangle clip = new Rectangle();
                    clip.setWidth(135);
                    clip.setHeight(198);

                    clip.setArcHeight(25);
                    clip.setArcWidth(25);
                    clip.setStroke(Color.BLACK);
                    goodItemsGrid.setClip(clip);

                    // snapshot the rounded image.
                    SnapshotParameters parameters = new SnapshotParameters();
                    parameters.setFill(Color.TRANSPARENT);


                    // remove the rounding clip so that our effect can show through.
                    goodItemsGrid.setClip(null);
                    goodItemsGrid.setEffect(new DropShadow(1, Color.BLACK));


                    GridPane.setMargin(anchorPane2, new Insets(20));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    void loaddefaultDataFood(){
        goodItemsGrid.getChildren().clear();
        // get food data from database.

            FoodOperation databaseConnector = new FoodOperation();
            foodsList = databaseConnector.getAllActive();
            temporaryFoodsList = databaseConnector.getAll();

            // set the gridItems.
            int tableOrderColumn = 0;
            int tableOrderRow = 1;

            for (int foodIndex = 0; foodIndex < foodsList.size(); foodIndex++) {

                if (tableOrderColumn == 3) {
                    tableOrderColumn = 0;
                    tableOrderRow = tableOrderRow + 1;
                }

                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/Views/TablesViews/foodItem.fxml"));
                    AnchorPane anchorPane2 = loader.load();
                    FoodItemController foodItemController = loader.getController();
                    foodItemController.setItemData(
                            foodsList.get(foodIndex).getName(),
                            foodsList.get(foodIndex).getImage_path(),
                            foodsList.get(foodIndex).getId()
                    );


                    goodItemsGrid.add(anchorPane2, tableOrderColumn++, tableOrderRow);
                    //column = column + 1;
                    // set the width.
                    goodItemsGrid.setMinWidth(Region.USE_COMPUTED_SIZE);
                    goodItemsGrid.setMaxWidth(Region.USE_PREF_SIZE);
                    goodItemsGrid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                    // set the height.
                    goodItemsGrid.setMinHeight(Region.USE_COMPUTED_SIZE);
                    goodItemsGrid.setMaxHeight(Region.USE_PREF_SIZE);
                    goodItemsGrid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                    Rectangle clip = new Rectangle();
                    clip.setWidth(135);
                    clip.setHeight(198);

                    clip.setArcHeight(25);
                    clip.setArcWidth(25);
                    clip.setStroke(Color.BLACK);
                    goodItemsGrid.setClip(clip);

                    // snapshot the rounded image.
                    SnapshotParameters parameters = new SnapshotParameters();
                    parameters.setFill(Color.TRANSPARENT);


                    // remove the rounding clip so that our effect can show through.
                    goodItemsGrid.setClip(null);
                    goodItemsGrid.setEffect(new DropShadow(1, Color.BLACK));


                    GridPane.setMargin(anchorPane2, new Insets(20));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

    }

    private void Addfood(int id,Food food){

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FoodBuyController.class.getResource("/Views/TablesViews/FacturItem.fxml"));
        AnchorPane anchorPane = null;
        try {
            anchorPane = loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        FacturItemController  facturItemController = loader.getController();
        // search for the price and title and change them here.
        facturItemController.setItemData(
                id,
                food
        );
        anchorPane.setEffect(new DropShadow(20, Color.grayRgb(190)));
        facturItemsGrid.add(anchorPane,columnF,rowF);
        rowF++;
        facturItemsGrid.setMargin(anchorPane,new Insets(5));

    }

    void addfood(ArrayList list)
    {
        for (int i=0;i!=list.size();i++) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(FoodBuyController.class.getResource("/Views/TablesViews/FacturItem.fxml"));
            AnchorPane anchorPane = null;
            try {
                anchorPane = loader.load();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            FacturItemController facturItemController = loader.getController();
            // search for the price and title and change them here.
            facturItemController.setItemData(
                    listfactur.get(i).getQentity(),
                    listfactur.get(i).getFood()
            );
            anchorPane.setEffect(new DropShadow(20, Color.grayRgb(190)));
            facturItemsGrid.add(anchorPane, columnF, rowF);
            rowF++;
            facturItemsGrid.setMargin(anchorPane, new Insets(5));


        }
    }
    void addOrder(Orders orders){
        OrdersOperation orderDatabaseConnector = new OrdersOperation();
        orderDatabaseConnector.insertnew(orders);
        // add the food order.
        FoodOrderOperation foodDatabaseConnector = new FoodOrderOperation();
        for (int foodIndex = 0; foodIndex < orders.getFoodsList().size(); foodIndex++) {
            foodDatabaseConnector.insert(orders.getFoodsList().get(foodIndex));
            ArrayList<IngredientsFood> list = new ArrayList<>();
            ArrayList<IngredientsFoodProductCompose> listproductcompose = new ArrayList<>();
            IngredientsFoodOperation ingredientsFoodOperation=new IngredientsFoodOperation();
            list=ingredientsFoodOperation.getIngredientsFood(orders.getFoodsList().get(foodIndex).getId_food());
            FoodProductComposeOperation foodProductComposeOperation=new FoodProductComposeOperation();
            listproductcompose=foodProductComposeOperation.getIngredientsFood(orders.getFoodsList().get(foodIndex).getId_food());
            for (int productIndex = 0; productIndex < list.size(); productIndex++) {
                Product product=new Product();
                ProductOperation productOperation=new ProductOperation();
                product=productOperation.GetProduct(list.get(productIndex).getId_product());
                double x=list.get(productIndex).getQuantity();
                double y=product.getCoefficient();
                double foodQuntity=orders.getFoodsList().get(foodIndex).getQuantity();
                double resul=x/y*foodQuntity;
                double productQuntity=product.getTot_quantity();
                double quntity2=productQuntity-resul;
                DecimalFormat df =new DecimalFormat("0.00");
                product.setTot_quantity(Double.parseDouble(df.format(quntity2)));
                productOperation.update(product);
            }
            for (int productComIndex = 0; productComIndex < listproductcompose.size(); productComIndex++) {
                ProductComposite productComposite=new ProductComposite();
                ProductCompositeOperation productCompositeOperation=new ProductCompositeOperation();
                productComposite=productCompositeOperation.GetProductComposite(listproductcompose.get(productComIndex).getId_productCopmpose());
                double x=listproductcompose.get(productComIndex).getQuantity();
                double y=productComposite.getCoefficient();
                double foodQuntity=orders.getFoodsList().get(foodIndex).getQuantity();
                double resul=x/y*foodQuntity;
                double productQuntity=productComposite.getQuantity();
                double quntity2=productQuntity-resul;

                DecimalFormat df =new DecimalFormat("0.00");
                productComposite.setQuantity(Double.parseDouble(df.format(quntity2)));
                productCompositeOperation.update(productComposite);
            }
        }
    }
}
