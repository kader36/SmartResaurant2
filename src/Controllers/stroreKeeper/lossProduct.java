package Controllers.stroreKeeper;

import BddPackage.ProductOperation;
import Controllers.ValidateController;
import Models.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class lossProduct {


    @FXML
    private TextField txt_lossQentity;

    @FXML
    private Button cancelButtonCategory;

    @FXML
    private Label errAddCategory;

    @FXML
    private Label lbl_valide;
    private Product newproduct;
    ValidateController validateController=new ValidateController();
    public void Init(Product product){
        this.newproduct=product;
    }


    private void closeDialog(Button btn) {
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }
    @FXML
    void clickedMouse(MouseEvent event) {
        closeDialog(cancelButtonCategory);
    }


    @FXML
    private void lossQentity(ActionEvent event) {
        try {
            Product product2=new Product();
            ProductOperation productOperation=new ProductOperation();
            product2.setId(newproduct.getId());
            double qentit=newproduct.getTot_quantity()-Double.parseDouble(txt_lossQentity.getText());
            product2.setTot_quantity(qentit);
            productOperation.update(product2);
            closeDialog(cancelButtonCategory);
            ProductController.clear.setValue(!ProductController.clear.getValue());

        }catch (Exception e){
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("تحذير");
            alertWarning.setContentText("يرجى ادخال قيمة صحيحة");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("حسنا");
            alertWarning.showAndWait();
            txt_lossQentity.clear();
        }
    }
}
