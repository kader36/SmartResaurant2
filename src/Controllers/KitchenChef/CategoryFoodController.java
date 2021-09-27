package Controllers.KitchenChef;

import BddPackage.FoodCategoryOperation;
import Models.FoodCategory;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CategoryFoodController implements Initializable {

    @FXML
    private AnchorPane mainPane;

    @FXML
    private VBox vboxOptionCategory;

    @FXML
    private Button cancelButtonCategory;

    @FXML
    private TableView<FoodCategory> tableCatrgoryFood;

    @FXML
    private TableColumn<FoodCategory, String> col_category_name;

    @FXML
    private TableColumn<FoodCategory, Integer> col_idcategory;

    @FXML
    private JFXButton insertbutton;

    @FXML
    private JFXButton updatebutton;

    @FXML
    private JFXButton deletebutton;

    @FXML
    private JFXTextField txt_food_name;

    @FXML
    private JFXButton updatecategoryButton;

    @FXML
    private TextField txt_category_upt;
    @FXML
    private TextField txt_category;
    @FXML
    private Label lbl_valide;
    @FXML
    private Label errAddCategory;
    @FXML
    private Button insertCategoryButton;
    @FXML
    private Button updateCategoryButton;
    @FXML
    private Label errProductCategory;

    private boolean visibleCategory = false;
    @FXML
    private HBox hbox;

    @FXML
    private JFXCheckBox green;

    @FXML
    private JFXCheckBox red;

    @FXML
    private JFXCheckBox blue;

    @FXML
    private JFXCheckBox Purple;
    @FXML
    private JFXCheckBox brouwn;

    public  BooleanProperty change = new SimpleBooleanProperty();
    private ArrayList<FoodCategory> listCategory;
    private ObservableList<FoodCategory> data_Table= FXCollections.observableArrayList();
    private FoodCategory foodCategory;
    private FoodCategoryOperation foodCategoryOperation=new FoodCategoryOperation();
    private FadeTransition fadeIn = new FadeTransition(
            Duration.millis(2000)
    );
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FoodCategory foodCategory=new FoodCategory();
        FoodCategoryOperation foodCategoryOperation=new FoodCategoryOperation();
        foodCategory=foodCategoryOperation.getCategory();
        System.out.println(foodCategory.getName()+" "+foodCategory.getColor());
        change.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                red.setOnAction((event) -> {
                    deselection(green,blue,Purple,brouwn);

                });
                green.setOnAction((event) -> {
                    deselection(red,blue,Purple,brouwn);

                });
                blue.setOnAction((event) -> {
                    deselection(red,green,Purple,brouwn);

                });
                Purple.setOnAction((event) -> {
                    deselection(red,blue,green,brouwn);

                });
                brouwn.setOnAction((event) -> {
                    deselection(red,blue,Purple,green);

                });
            }
        });


    }
    public void Init(AnchorPane mainPain) {
        this.mainPane = mainPain;
    }
    private void Inite(FoodCategory productCategory) {
        txt_category_upt.setText(productCategory.getName());
        this.foodCategory = productCategory;

    }
    public void InitCategoryList() {
        vboxOptionCategory.setVisible(false);
        ArrayList<FoodCategory> ListCategory=new ArrayList<FoodCategory>();
        FoodCategoryOperation foodCategoryOperation=new FoodCategoryOperation();
        ListCategory=foodCategoryOperation.getAll();
        listCategory = foodCategoryOperation.getAll();
        col_idcategory.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_category_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        data_Table.setAll(ListCategory);
        tableCatrgoryFood.setItems(data_Table);
    }
    private void chargelistCategoryProduct() {

    }


    @FXML
    void deleteCategoryFood(ActionEvent event) {
       FoodCategory foodCategory = tableCatrgoryFood.getSelectionModel().getSelectedItem();
        if(foodCategory != null){
            foodCategoryOperation.delete(foodCategory);
            refresh();
        }
    }

    @FXML
    void insertCategoryFood(ActionEvent event) {
        if (!txt_food_name.equals("")){
            FoodCategory foodCategory = new FoodCategory();
            foodCategory.setName(txt_food_name.getText());
            foodCategoryOperation.insert(foodCategory);
            refresh();
        }
    }

    @FXML
    void updateAction(ActionEvent event) {
         foodCategory = tableCatrgoryFood.getSelectionModel().getSelectedItem();
        if(foodCategory != null){
            updatecategoryButton.setDisable(false);
            txt_food_name.setText(foodCategory.getName());
        }
    }

    @FXML
    void updateCategoryFood(ActionEvent event) {
        if (!txt_food_name.equals("")){
            FoodCategory newprodCategory = new FoodCategory();
            newprodCategory.setName(txt_food_name.getText());
            foodCategoryOperation.update(newprodCategory,foodCategory);
            updatecategoryButton.setDisable(true);
            txt_food_name.setText("");
            refresh();
        }
    }

    private void refresh() {
        listCategory = foodCategoryOperation.getAll();
        data_Table.setAll(listCategory);
        tableCatrgoryFood.setItems(data_Table);
    }
    @FXML
    void ShowCategoryTable(MouseEvent event) {
        tableCatrgoryFood.setDisable(false);
    }
    @FXML
    void clickedMouse(MouseEvent event) {
        closeDialog(cancelButtonCategory);
    }
    private void closeDialog(Button btn) {
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }

    void showHideCategoryOperation(ActionEvent event) {
        tableCatrgoryFood.setDisable(true);
        visibleCategory = showHideListOperation(vboxOptionCategory,visibleCategory);
    }
    @FXML
    void showCategoryOperation(ActionEvent event){
        tableCatrgoryFood.setDisable(true);
        visibleCategory = showHideListOperation(vboxOptionCategory,visibleCategory);
    }

    private boolean showHideListOperation(VBox vBox, boolean visibles) {

        if (!visibles) {
            vBox.setVisible(true);
            visibles = true;
        } else {
            vBox.setVisible(false);
            visibles = false;

        }
        return visibles;
    }
    @FXML
    void insertCategory(ActionEvent event) {
        try {
            vboxOptionCategory.setVisible(false);
            visibleCategory = false;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/KitchenChef/AddFoodCategory.fxml"));
            DialogPane temp = loader.load();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(temp);
            dialog.initStyle(StageStyle.UNDECORATED);
            dialog.showAndWait();


            refresh();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void updateCategory(ActionEvent event) {
        vboxOptionCategory.setVisible(false);
        visibleCategory = false;
        FoodCategory foodCategory =new FoodCategory();
        foodCategory = tableCatrgoryFood.getSelectionModel().getSelectedItem();
        if (foodCategory == null) {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("تحذير ");
            alertWarning.setContentText("يرجى اختيار الصنف المراد تعديله");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("حسنا");
            alertWarning.showAndWait();
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/KitchenChef/UpdateCategoryFood.fxml"));
                DialogPane temp = loader.load();
                CategoryFoodController categoriesController = loader.getController();
                categoriesController.Inite(foodCategory);
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setDialogPane(temp);
                dialog.initStyle(StageStyle.UNDECORATED);
                dialog.showAndWait();
                refresh();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void AddCategoryAction(ActionEvent event) {
        String color="";
        if (!txt_category.getText().isEmpty()) {
            FoodCategoryOperation foodCategoryOperation=new FoodCategoryOperation();
            FoodCategory foodCategory1=new FoodCategory();
            foodCategory1=foodCategoryOperation.getCategory();
            System.out.println(foodCategory1.getColor());
            if(foodCategory1.getColor()!=null) {
                if (foodCategory1.getColor().equals("800000")) {
                    color = "FF0000";
                } else {
                    if (foodCategory1.getColor().equals("FF0000")) {
                        color = "008000";
                    } else {
                        if (foodCategory1.getColor().equals("008000")) {
                            color = "0000FF";
                        } else {
                            if (foodCategory1.getColor().equals("0000FF")) {
                                color = "800080";
                            } else {
                                color = "800000";
                            }
                        }
                    }
                }
            }else{
                color ="28E80E";
            }
            FoodCategory foodCategory=new FoodCategory();
            foodCategory.setName(txt_category.getText());
            foodCategory.setColor(color);
            boolean k=foodCategoryOperation.insert(foodCategory);
            System.out.println(k);
            txt_category.setText("");
            fadeIn.setNode(lbl_valide);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.setCycleCount(1);
            fadeIn.setAutoReverse(true);
            lbl_valide.setVisible(true);
            fadeIn.playFromStart();

            /////////////////////////////////////////////////////////////////////

        } else {
            errAddCategory.setText("ادخل اسم الصنف الجديد");
        }
    }
    @FXML
    void closeInsertJobDialog(MouseEvent event) {
        close(insertCategoryButton);
    }
    @FXML
    void txt_category_Pressed(KeyEvent event) { }
    private void close(Button btn) {
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }
    @FXML
    void deleteCategory(ActionEvent event) {
        vboxOptionCategory.setVisible(false);
        visibleCategory = false;
        foodCategory = tableCatrgoryFood.getSelectionModel().getSelectedItem();
        if (foodCategory != null) {
            Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
            alertConfirmation.setHeaderText("تأكيد الحذف");
            alertConfirmation.setContentText("هل انت متأكد من حذف الصنف ");
            Button okButton = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("موافق");

            Button cancel = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.CANCEL);
            cancel.setText("الغاء");

            alertConfirmation.showAndWait().ifPresent(response -> {
                if (response == ButtonType.CANCEL) {
                    alertConfirmation.close();
                } else if (response == ButtonType.OK) {
                    foodCategoryOperation.delete(foodCategory);
                    refresh();
                }
            });
        } else {
            System.out.println("it's null");
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("تحذير ");
            alertWarning.setContentText("يرجى اختيار الصنف المراد حذفه");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("حسنا");
            alertWarning.showAndWait();
        }
    }

    @FXML
    void closeUpdateJobDialog(MouseEvent event) {
        close(updateCategoryButton);
    }
    @FXML
    void UpdateCategoryAction(ActionEvent event) {
        FoodCategory foodCategory1=new FoodCategory();
        foodCategory1.setName(txt_category_upt.getText());
        if (!txt_category_upt.getText().isEmpty() && !foodCategory.getName().equals(txt_category_upt.getText())) {
            foodCategoryOperation.update(foodCategory1, foodCategory);
            close(updateCategoryButton);
        } else {
            errProductCategory.setText("ادخل اسم الوظيفة الجديدة");
        }
    }

    void deselection(JFXCheckBox a,JFXCheckBox b,JFXCheckBox c,JFXCheckBox d){
        a.setSelected(false);
        c.setSelected(false);
        d.setSelected(false);
        b.setSelected(false);
        change.setValue(!change.getValue());
    }
    void selection(){
        if(red.isSelected()){
            change.setValue(!change.getValue());
        }
    }
}
