package Controllers.stroreKeeper;

import BddPackage.ProviderJobOperation;
import BddPackage.ProviderOperation;
import Controllers.ValidateController;
import Models.Provider;
import Models.ProviderJob;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
/**
 * provider controller operation;
 *
 * */
public class ProviderController implements Initializable {
    @FXML
    private BorderPane mainPane;
    @FXML
    private TableView<Provider> providerTable;
    @FXML
    private TableColumn<Provider, String> col_name;

    @FXML
    private TableColumn<Provider, String> col_last_name;

    @FXML
    private TableColumn<Provider, String> col_job;

    @FXML
    private TableColumn<Provider, String> col_adress;

    @FXML
    private TableColumn<Provider, Integer> col_phone_number;

    @FXML
    private TableColumn<Provider, Integer> col_creditor;

    @FXML
    private TableColumn<Provider, Integer> col_creditor_to;

    @FXML
    private TableColumn<Provider, Integer> col_id;

    @FXML
    private TextField txt__name;

    @FXML
    private TextField txt_last_name;

    @FXML
    private ComboBox<String> JobCombo;

    @FXML
    private TextField txt_adress;

    @FXML
    private TextField txt_phone_number;

    @FXML
    private Label lbl_provider_number;

    @FXML
    private ComboBox<String> listedbyCombo;

    @FXML
    private TextField txt_search;

    @FXML
    private TextField txt_name_upd;

    @FXML
    private TextField txt_last_name_upd;

    @FXML
    private TextField txt_adress_upd;

    @FXML
    private TextField txt_phone_number_upd;

    @FXML
    private Button insertButton;

    @FXML
    private Button updateButton;

    @FXML
    private VBox vboxOption;

    @FXML
    private Label lbl_info_provider;

    private ObservableList<Provider> dataTable = FXCollections.observableArrayList();
    private ArrayList<Provider> list_Providers = new ArrayList<>();
    private ValidateController validateController = new ValidateController();
    private ProviderJobOperation providerJobOperation = new ProviderJobOperation();

    private Provider provider;
    private ProviderOperation providerOperation = new ProviderOperation();

    private ObservableList<String> dataCombo = FXCollections.observableArrayList();
    private boolean visible = false;

    private SequentialTransition lblTransition = new SequentialTransition();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void Init(BorderPane mainPane) {
        this.mainPane = mainPane;
        mainPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                vboxOption.setVisible(false);
                visible = false;

            }
        });
        chargeListProvider();
        vboxOption.setVisible(false);
        lbl_info_provider.setVisible(false);
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        col_last_name.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        col_phone_number.setCellValueFactory(new PropertyValueFactory<>("phone_number"));
        col_adress.setCellValueFactory(new PropertyValueFactory<>("adress"));
        col_job.setCellValueFactory(new PropertyValueFactory<>("job"));
        col_creditor.setCellValueFactory(new PropertyValueFactory<>("creditor"));
        col_creditor_to.setCellValueFactory(new PropertyValueFactory<>("creditor_to"));

        listedbyCombo.getItems().addAll("الاسم","اللقب","الوطيفة","العنوان");
        /*
        * sort table provider by type of job
        * */
        listedbyCombo.valueProperty().addListener((new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                switch (newValue){
                    case "الاسم":
                        dataTable.setAll(providerOperation.getAllBy("PROVIDER.PROVIDER_FIRST_NAME"));
                        providerTable.setItems(dataTable);
                        System.out.println("misaoooooooooooooor");
                        break;
                    case "اللقب" :
                        dataTable.setAll(providerOperation.getAllBy("PROVIDER.PROVIDER_LAST_NAME"));
                        providerTable.setItems(dataTable);
                        break;
                    case "الوطيفة" :
                        dataTable.setAll(providerOperation.getAllBy("PROVIDER.PROVIDER_JOB"));
                        providerTable.setItems(dataTable);
                        break;
                    case "العنوان":
                        dataTable.setAll(providerOperation.getAllBy("PROVIDER_ADRESS"));
                        providerTable.setItems(dataTable);
                        break;
                }

            }
        }));
        refresh();
    }

    private void chargeListProviderJob() {
        ProviderJobOperation providerJobOperation = new ProviderJobOperation();
        ArrayList<ProviderJob> list_Provider_Job = providerJobOperation.getAll();
        for (ProviderJob aList_Provider_Job : list_Provider_Job) {
            dataCombo.add(aList_Provider_Job.getName());
        }
        JobCombo.setItems(dataCombo);
    }
    /**
     * pop up add_provider code
     * */
    @FXML
    void addNewProvider(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/addProvider.fxml"));
            DialogPane temp = loader.load();
            ProviderController providerController = loader.getController();
            providerController.chargeListProviderJob();
            providerController.txtValidate();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(temp);
            dialog.initStyle(StageStyle.UNDECORATED);
            dialog.showAndWait();
            refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*
    * insert provider BDD
    * */
    @FXML
    void insertProviderAction(ActionEvent event) {
     String job = JobCombo.getSelectionModel().getSelectedItem();
     if (!txt__name.getText().isEmpty() && !txt_last_name.getText().isEmpty() && !job.equals("")
         && !txt_adress.getText().isEmpty() && !txt_phone_number.getText().isEmpty() )
     {
      Provider provider = new Provider();
      provider.setFirst_name(txt__name.getText());
      provider.setLast_name(txt_last_name.getText());
      provider.setAdress(txt_adress.getText());
      provider.setPhone_number(txt_phone_number.getText());
      provider.setJob(job);
      providerOperation.insert(provider);
      clearTxt();
     }
    }
    @FXML
    void updateProvider(ActionEvent event) {
        vboxOption.setVisible(false);
        visible = false;
        Provider provider = providerTable.getSelectionModel().getSelectedItem();
        if (provider != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/updateProvider.fxml"));
                DialogPane temp = loader.load();
                ProviderController providerController = loader.getController();
                providerController.chargeListProviderJob();
                providerController.chargeTxt(provider);
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setDialogPane(temp);
                dialog.initStyle(StageStyle.UNDECORATED);
                dialog.showAndWait();
                refresh();
                fadeoutlbl("تم التعديل بنجاح");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("تحذير ");
            alertWarning.setContentText("يرجى اختيار المورد المراد تعديله");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton( ButtonType.OK );
            okButton.setText("حسنا");
            alertWarning.showAndWait();        }

    }

    private void chargeTxt(Provider oldprovider) {
        txt_name_upd.setText(oldprovider.getFirst_name());
        txt_last_name_upd.setText(oldprovider.getLast_name());
        txt_adress_upd.setText(oldprovider.getAdress());
        txt_phone_number_upd.setText(oldprovider.getPhone_number());
        JobCombo.getSelectionModel().select(oldprovider.getJob());
        provider = oldprovider;
    }

    @FXML
    void updateProviderAction(ActionEvent event) {
     if (!txt_name_upd.getText().isEmpty() && !txt_last_name_upd.getText().isEmpty() && !txt_adress_upd.getText().isEmpty()
     && !txt_phone_number_upd.getText().isEmpty()){
         Provider newProvider = new Provider();
         newProvider.setFirst_name(txt_name_upd.getText());
         newProvider.setLast_name(txt_last_name_upd.getText());
         newProvider.setPhone_number(txt_phone_number_upd.getText());
         newProvider.setJob(JobCombo.getSelectionModel().getSelectedItem());
         newProvider.setAdress(txt_adress_upd.getText());
         providerOperation.update(newProvider,provider);
         closeDialog(updateButton);
     }
    }

    @FXML
    void deleteProvider(ActionEvent event) {
        vboxOption.setVisible(false);
        visible = false;
        Provider provider = providerTable.getSelectionModel().getSelectedItem();
        if (provider != null) {
            Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
            alertConfirmation.setHeaderText("تأكيد الحذف");
            alertConfirmation.setContentText("هل انت متأكد من حذف المورد  ");
            Button okButton = (Button) alertConfirmation.getDialogPane().lookupButton( ButtonType.OK );
            okButton.setText("موافق");

            Button cancel = (Button) alertConfirmation.getDialogPane().lookupButton( ButtonType.CANCEL );
            cancel.setText("الغاء");

            alertConfirmation.showAndWait().ifPresent(response -> {
                if (response == ButtonType.CANCEL) {
                    alertConfirmation.close();
                }
                else if (response == ButtonType.OK)
                {
                    providerOperation.delete(provider);
                    fadeoutlbl("تم الحذف بنجاح");
                    refresh();
                }
            });
        }else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("تحذير ");
            alertWarning.setContentText("يرجى اختيار المورد المراد حذقه");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton( ButtonType.OK );
            okButton.setText("حسنا");
        }
    }



    @FXML
    void showListJob(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/jobs.fxml"));
            DialogPane temp = loader.load();
        /*    ProviderController providerController = loader.getController();
            providerController.hide();
        */
            ProviderJobController providerJobController = loader.getController();
            providerJobController.InitJobList();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(temp);
            dialog.initStyle(StageStyle.UNDECORATED);
            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void chargeListProvider(){
    }

    @FXML
    void close(MouseEvent event) {
       closeDialog(insertButton);
    }


    @FXML
    void showHideProviderOperation(ActionEvent event) {
     visible = showHideListOperation(vboxOption,visible);
    }

    private boolean showHideListOperation(VBox vBox ,boolean visibles){
        if (!visibles){
            vBox.setVisible(true);
            visibles = true;
        }
        else{ vBox.setVisible(false);
            visibles = false;

        }
        return visibles;
    }

    private void closeDialog(Button btn){
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }

    private void clearTxt(){
        txt__name.clear();
        txt_last_name.clear();
        txt_phone_number.clear();
        txt_adress.clear();

    }
    private void refresh(){
        list_Providers = providerOperation.getAll();
        dataTable.setAll(list_Providers);
        providerTable.setItems(dataTable);
        lbl_provider_number.setText(""+(list_Providers.size()-1));

    }
    @FXML
    void txt_phone_Pressed(KeyEvent event) {

    }
    /**
     * label validation .....
     * */
    private void fadeoutlbl(String info){
        lbl_info_provider.setText(info);
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(2),lbl_info_provider);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.setCycleCount(1);
        fadeIn.setAutoReverse(true);
        lbl_info_provider.setVisible(true);
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), lbl_info_provider);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        lblTransition.getChildren().addAll(fadeIn,fadeOut);
        lblTransition.play();

    }
    private void txtValidate(){
        validateController.inputTextValueType(txt__name);
        validateController.inputTextValueType(txt_adress);
        validateController.inputTextValueType(txt_last_name);
        //problem
        //validation number
        /*txt_phone_number.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("[0-9]{10}")) return;
            txt_phone_number.setText(oldValue);
        });*/
    }

}
