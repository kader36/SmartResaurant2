package Controllers.newKitchenChef;

import BddPackage.ProductCompositeOperation;
import Models.ProductComposite;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class UpdateProductComposeController {
    @FXML
    private Button updateButton;
    @FXML
    private TextField txt_name_upd;

    @FXML
    private TextField txt_tot_quantity_upd;

    @FXML
    private TextField txt_unityStory_upd;

    @FXML
    private TextField txt_unityFood_upd;

    @FXML
    private TextField txt_Coefficient_upd;
    @FXML
    private TextField txt_less_quantity_upd;
    @FXML
    private Label errUpdateProduct;
    private ProductComposite productComposite;
    @FXML
    void closeUpdateProduct(MouseEvent event) {
        closeDialog(updateButton);
    }
    private void closeDialog(Button btn) {
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }
    public void chargeTxt(ProductComposite oldProduct) {
        txt_name_upd.setText(oldProduct.getName());
        txt_less_quantity_upd.setText(String.valueOf(oldProduct.getLESS_QUANTITY()));
        txt_tot_quantity_upd.setText(String.valueOf(oldProduct.getQuantity()));
        txt_unityStory_upd.setText(oldProduct.getStorage_Unit());
        txt_unityFood_upd.setText(oldProduct.getUnity_Food());
        txt_Coefficient_upd.setText(String.valueOf(oldProduct.getCoefficient()));
        this.productComposite=oldProduct;

    }

    @FXML
    void updateProductAction(ActionEvent event) {
        if (!txt_name_upd.getText().isEmpty() && !txt_tot_quantity_upd.getText().isEmpty() && !txt_unityFood_upd.getText().isEmpty()
                && !txt_less_quantity_upd.getText().isEmpty() && !txt_unityStory_upd.getText().isEmpty() && !txt_Coefficient_upd.getText().isEmpty()) {
            ProductCompositeOperation productCompositeOperation=new ProductCompositeOperation();
            ProductComposite updProduct = new ProductComposite();
            updProduct.setName(txt_name_upd.getText());
            updProduct.setQuantity(Double.valueOf(txt_tot_quantity_upd.getText()));
            updProduct.setUnity_Food(txt_unityFood_upd.getText());
            updProduct.setLESS_QUANTITY(Integer.parseInt(txt_less_quantity_upd.getText()));
            updProduct.setStorage_Unit(txt_unityStory_upd.getText());
            updProduct.setCoefficient(Integer.parseInt(txt_Coefficient_upd.getText()));
            productCompositeOperation.update(updProduct, productComposite);
            Alert alertWarning = new Alert(Alert.AlertType.INFORMATION);
            alertWarning.setHeaderText("تأكيد ");
            alertWarning.setContentText("تم تعديل السلعة بنجاح");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("حسنا");
            alertWarning.showAndWait();
            closeDialog(updateButton);
        } else {
            errUpdateProduct.setText("الرجاء ملأ جميع الحقول");
        }
        //productOperation = new ProductOperation();
    }
}
