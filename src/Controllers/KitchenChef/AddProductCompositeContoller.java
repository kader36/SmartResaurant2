package Controllers.KitchenChef;

import BddPackage.IngredientsProductCompsiteOperation;
import BddPackage.ProductCompositeOperation;
import BddPackage.ProductOperation;
import Models.IngredientsProductCompsite;
import Models.Product;
import Models.ProductComposite;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddProductCompositeContoller implements Initializable {
    @FXML
    private BorderPane mainPane;
    @FXML
    private Label Unity;
    @FXML
    private TextField txt_quantity;
    @FXML
    private JFXListView<String> listViewProduct;
    @FXML
    private TableView<IngredientsProductCompsite> table_food;

    @FXML
    private TableColumn<?, ?> col_Product_Name;

    @FXML
    private TableColumn<?, ?> col_Unit;

    @FXML
    private TableColumn<?, ?> col_Quantity;
    @FXML
    private ComboBox IdProduteComposet;
    @FXML
    private TextField foodName;


    private ArrayList<Product> list_ProductsObject = new ArrayList<>();
    private ArrayList<String> list_Product = new ArrayList<>();
    private ObservableList<String> dataTableView = FXCollections.observableArrayList();
    private ObservableList<String> dataCombo;
    private ArrayList<IngredientsProductCompsite> listProducts = new ArrayList<>();
    private ObservableList<IngredientsProductCompsite> dataTable = FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(listViewProduct.isMouseTransparent())
        this.Unity.setText(ShowUnity());
        chargeListProduct();
        chargeListProducteComposite();
        col_Product_Name.setCellValueFactory(new PropertyValueFactory<>("Product_name"));
        col_Unit.setCellValueFactory(new PropertyValueFactory<>("unity"));
        col_Quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));


        dataTableView.setAll(list_Product);
        listViewProduct.setItems(dataTableView);

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
    private String ShowUnity(){
        String unity="";
        Product product=new Product();

       unity= listViewProduct.getSelectionModel().getSelectedItems().toString();

        return unity;
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
            IngredientsProductCompsite ingredientsFood = new IngredientsProductCompsite();
            product = product.replace("[", "");
            product = product.replace("]", "");
            ingredientsFood.setProduct_name(product);
            ingredientsFood.setUnity("G");
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
    private void refresh() {
        dataTable.setAll(listProducts);
        table_food.setItems(dataTable);
    }
    private void chargeListProducteComposite() {
        dataCombo = FXCollections.observableArrayList();

        ProductCompositeOperation productCompositeOperation=new ProductCompositeOperation();
        ArrayList<ProductComposite> foodCategory= productCompositeOperation.getAll();
        for (ProductComposite listProviderFromDB : foodCategory) {
            dataCombo.add(listProviderFromDB.getName());

        }
        IdProduteComposet.setItems(dataCombo);

    }
    @FXML
    void deleteProductFood(ActionEvent event) {
        IngredientsProductCompsite ingredientsFood = table_food.getSelectionModel().getSelectedItem();
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
                if (IdProduteComposet.getSelectionModel().isEmpty()) {
                    Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                    alertWarning.setHeaderText("تحذير ");
                    alertWarning.setContentText("الرجاء حدد المنتوج المركب المراد صنعه قبل الحفظ");
                    Button okkButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                    okkButton.setText("حسنا");
                    alertWarning.showAndWait();
                    return;
                }else {

                    if  (foodName.getText().isEmpty()) {
                        Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                        alertWarning.setHeaderText("تحذير ");
                        alertWarning.setContentText("الرجاء ادخل  الكمية قبل الحفظ");
                        Button okkButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                        okkButton.setText("حسنا");
                        alertWarning.showAndWait();
                        return;
                    }else{


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
                                ProductComposite productComposite1=new ProductComposite();
                                ProductComposite productComposite2=new ProductComposite();
                                ProductCompositeOperation productCompositeOperation1=new ProductCompositeOperation();
                                // insert ingredients
                                IngredientsProductCompsiteOperation ingredientsProductCompsiteOperation=new IngredientsProductCompsiteOperation();
                                for (IngredientsProductCompsite ingredientsProductCompsite : dataTable){
                                    ProductOperation productOperation=new ProductOperation();
                                    ProductCompositeOperation productCompositeOperation=new ProductCompositeOperation();
                                    Product product=new Product();
                                    ProductComposite productComposite=new ProductComposite();
                                    product=productOperation.GetProduct(ingredientsProductCompsite.getProduct_name());
                                    double quantity=product.getTot_quantity()-ingredientsProductCompsite.getQuantity()/product.getCoefficient();
                                    product.setTot_quantity(quantity);
                                    productOperation.update(product);
                                    productComposite=productCompositeOperation.GetProductComposite(IdProduteComposet.getSelectionModel().getSelectedItem().toString());
                                    ingredientsProductCompsite.setId_productCompsite(productComposite.getId());
                                    ingredientsProductCompsite.setId_product(product.getId());
                                    ingredientsProductCompsite.setQuantity(ingredientsProductCompsite.getQuantity());
                                    ingredientsProductCompsiteOperation.insert(ingredientsProductCompsite);
                                }
                                productComposite1=productCompositeOperation1.GetProductComposite(IdProduteComposet.getSelectionModel().getSelectedItem().toString());
                                productComposite2.setId(productComposite1.getId());
                                productComposite2.setQuantity(productComposite1.getQuantity()+Float.valueOf(foodName.getText()));
                                boolean up=productCompositeOperation1.update(productComposite2);
                               System.out.println(up+"  nnnnnnnnnnnnnnnnnnnnnnnnkkkkkkkkkkkkkk");
                                Alert alertWarning = new Alert(Alert.AlertType.INFORMATION);
                                alertWarning.setHeaderText("تأكيدالحفظ");
                                alertWarning.setContentText("تم صنع المنتوج بنجاح");
                                Button okkButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                                okkButton.setText("حسنا");
                                alertWarning.showAndWait();


                            }
                        });
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/KitchenChef/ProductComposite.fxml"));
                            BorderPane temp = loader.load();
                            mainPane.getChildren().setAll(temp);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

    }
