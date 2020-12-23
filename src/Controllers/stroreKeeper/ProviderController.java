package Controllers.stroreKeeper;

import BddPackage.ProviderJobOperation;
import BddPackage.ProviderOperation;
import Controllers.ValidateController;
import Models.Product;
import Models.Provider;
import Models.ProviderJob;
import com.mysql.jdbc.Connection;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;

/**
 * provider controller operation;
 */
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
    private Button moreOptionsProvider;

    @FXML
    private Button addProvider;

    @FXML
    private VBox vboxOption;

    @FXML
    private Label lbl_info_provider;

    @FXML
    private Label errAddProvider;

    @FXML
    private Label errUpdateProvider;

    private static boolean updateProviderSucc = false;
    private static boolean addProviderSucc = false;

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

        listedbyCombo.getItems().addAll("الاسم", "اللقب", "الوطيفة", "العنوان");
        /*
         * sort table provider by type of job
         * */
        listedbyCombo.valueProperty().addListener((new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                switch (newValue) {
                    case "الاسم":
                        dataTable.setAll(providerOperation.getAllBy("PROVIDER.PROVIDER_FIRST_NAME"));
                        providerTable.setItems(dataTable);
                        System.out.println("misaoooooooooooooor");
                        break;
                    case "اللقب":
                        dataTable.setAll(providerOperation.getAllBy("PROVIDER.PROVIDER_LAST_NAME"));
                        providerTable.setItems(dataTable);
                        break;
                    case "الوطيفة":
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
     */
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
            if (addProviderSucc) {
                fadeoutlbl("  تمت الاضافة بنجاح");
                addProviderSucc = false;
            }

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

        if (!txt__name.getText().isEmpty() && !txt_last_name.getText().isEmpty()
                && !txt_adress.getText().isEmpty() && !txt_phone_number.getText().isEmpty()) {
            try {
                if (!job.equals("")) ;
            } catch (Exception e) {
                errAddProvider.setText("املأ جميع الحقول");
                return;
            }
            if (!txt_phone_number.getText().matches("[0-9]+") || txt_phone_number.getText().length() != 10) {
                errAddProvider.setText("ادخل رقم هاتف صحيح");
                return;
            }
            Provider provider = new Provider();
            provider.setFirst_name(txt__name.getText());
            provider.setLast_name(txt_last_name.getText());
            provider.setAdress(txt_adress.getText());
            provider.setPhone_number(txt_phone_number.getText());
            provider.setJob(job);
            providerOperation.insert(provider);
            clearTxt();
            addProviderSucc = true;
            closeDialog(insertButton);
        } else {
            errAddProvider.setText("املأ جميع الحقول");
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
                providerController.txtValidateUpdate();
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setDialogPane(temp);
                dialog.initStyle(StageStyle.UNDECORATED);
                dialog.showAndWait();
                refresh();
                if (updateProviderSucc) {
                    fadeoutlbl("  تم التعديل بنجاح");
                    updateProviderSucc = false;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("تحذير ");
            alertWarning.setContentText("يرجى اختيار المورد المراد تعديله");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("حسنا");
            alertWarning.showAndWait();
        }

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
                && !txt_phone_number_upd.getText().isEmpty()) {
            if (txt_name_upd.getText().matches("[0-9]+") || txt_last_name_upd.getText().matches("[0-9]+")) {
                errUpdateProvider.setText("ادخل معلومات صحيحة");
                return;
            }
            if (!txt_phone_number_upd.getText().matches("[0-9]+") || txt_phone_number_upd.getText().length() != 10) {
                errUpdateProvider.setText("ادخل رقم هاتف صحيح");
                return;
            }
            Provider newProvider = new Provider();
            newProvider.setFirst_name(txt_name_upd.getText());
            newProvider.setLast_name(txt_last_name_upd.getText());
            newProvider.setPhone_number(txt_phone_number_upd.getText());
            newProvider.setJob(JobCombo.getSelectionModel().getSelectedItem());
            newProvider.setAdress(txt_adress_upd.getText());
            providerOperation.update(newProvider, provider);
            updateProviderSucc = true;
            closeDialog(updateButton);
        } else {
            errUpdateProvider.setText("الرجاء ملأ جميع الحقول");
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
            Button okButton = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("موافق");

            Button cancel = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.CANCEL);
            cancel.setText("الغاء");

            alertConfirmation.showAndWait().ifPresent(response -> {
                if (response == ButtonType.CANCEL) {
                    alertConfirmation.close();
                } else if (response == ButtonType.OK) {
                    providerOperation.delete(provider);
                    fadeoutlbl("تم الحذف بنجاح");
                    refresh();
                }
            });
        } else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("تحذير ");
            alertWarning.setContentText("يرجى اختيار المورد المراد حذفه");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("حسنا");
            alertWarning.showAndWait();
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


    private void chargeListProvider() {
        refresh();
    }

    @FXML
    void close(MouseEvent event) {
        closeDialog(insertButton);
    }

    @FXML
    void closeUpdateProvider(MouseEvent mouseEvent) {
        closeDialog(updateButton);
    }

    @FXML
    void showHideProviderOperation(ActionEvent event) {
        visible = showHideListOperation(vboxOption, visible);
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

    private void closeDialog(Button btn) {
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }

    private void clearTxt() {
        txt__name.clear();
        txt_last_name.clear();
        txt_phone_number.clear();
        txt_adress.clear();

    }

    private void refresh() {
        list_Providers = providerOperation.getAll();
        dataTable.setAll(list_Providers);
        providerTable.setItems(dataTable);
        lbl_provider_number.setText("" + (list_Providers.size() - 1));

    }

    @FXML
    void txt_phone_Pressed(KeyEvent event) {

    }

    /**
     * label validation .....
     */
    private void fadeoutlbl(String info) {
        this.lbl_info_provider.setText(info);
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), this.lbl_info_provider);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.setCycleCount(1);
        fadeIn.setAutoReverse(true);
        this.lbl_info_provider.setVisible(true);
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), this.lbl_info_provider);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        this.lblTransition.getChildren().addAll(fadeIn, fadeOut);
        this.lblTransition.play();

    }

    private void txtValidate() {
        validateController.inputTextValueType(txt__name);
        //validateController.inputTextValueType(txt_adress);
        validateController.inputTextValueType(txt_last_name);
        //problem
        //validation number
        /*txt_phone_number.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("[0-9]{10}")) return;
            txt_phone_number.setText(oldValue);
        });*/
    }

    private void txtValidateUpdate() {
        validateController.inputTextValueType(txt_name_upd);
        validateController.inputTextValueType(txt_last_name_upd);
    }


    // TODO do it in 16/12/2020

    @FXML
    private ImageView closeImg;
    FilteredList<Provider> filteredData;

    @FXML
    void searchProvider(ActionEvent event) {

        // filtrer les données
        ObservableList<Provider> dataProvider = providerTable.getItems();
        filteredData = new FilteredList<>(dataProvider, e -> true);
        txt_search.setOnKeyReleased(e -> {
            txt_search.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filteredData.setPredicate((Predicate<? super Provider>) provider -> {
                    if (newValue == null || newValue.isEmpty()) {
                        //loadDataInTable();
                        return true;
                    } else if (String.valueOf(provider.getId()).contains(newValue.toLowerCase())) {
                        return true;
                    } else if (provider.getFirst_name().toLowerCase().contains(newValue.toLowerCase())) {
                        return true;
                    } else if (provider.getLast_name().toLowerCase().contains(newValue.toLowerCase())) {
                        return true;
                    } else if (provider.getJob().contains(newValue)) {
                        return true;
                    } else if (provider.getAdress().contains(newValue)) {
                        return true;
                    } else if (provider.getPhone_number().contains(newValue)) {
                        return true;
                    }
                    return false;
                });
            });

            SortedList<Provider> sortedList = new SortedList<>(filteredData);
            sortedList.comparatorProperty().bind(providerTable.comparatorProperty());
            providerTable.setItems(sortedList);
        });

    }

    @FXML
    void reportProvidersList(ActionEvent event) {
        // TODO must change report path
        try {
            String report = "E:\\SmartResaurant\\src\\Views\\storekeeper\\reportProvider.jrxml";
            JasperDesign jasperDesign = JRXmlLoader.load(report);
            String sqlCmd = "select * from provider order by id_provider";
            JRDesignQuery jrDesignQuery = new JRDesignQuery();
            jrDesignQuery.setText(sqlCmd);
            jasperDesign.setQuery(jrDesignQuery);
            JasperReport jasperReport = null;
            jasperReport = JasperCompileManager.compileReport(jasperDesign);
            Connection connection = null;
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, connection);
            JasperViewer.viewReport(jasperPrint);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void exportPDFOfProviderList(ActionEvent event) {
        try {
            // TODO must change report path
            // choose directory of PDF
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();

            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(stage);
            String workspace;
            if (selectedDirectory == null)
                return;
            workspace = selectedDirectory.getAbsolutePath();
            //
            String report = "E:\\SmartResaurant\\src\\Views\\storekeeper\\reportProvider.jrxml";
            JasperDesign jasperDesign = JRXmlLoader.load(report);
            String sqlCmd = "select * from provider";
            JRDesignQuery jrDesignQuery = new JRDesignQuery();
            jrDesignQuery.setText(sqlCmd);
            jasperDesign.setQuery(jrDesignQuery);
            JasperReport jasperReport = null;
            jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null);
            // Export to PDF.
            JasperExportManager.exportReportToPdfFile(jasperPrint, workspace + "/list of providers.pdf");
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void exportCsvProvider(ActionEvent event) {
        try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            // choose directory of CSV
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(stage);
            String workspace;
            if (selectedDirectory == null)
                return;
            workspace = selectedDirectory.getAbsolutePath();
            // Export to CSV.
            PrintWriter pw = new PrintWriter(workspace + "/list of provider.csv");
            StringBuilder sb = new StringBuilder();
            sb.append("رقم التسجيل");
            sb.append(",");
            sb.append("الإسم");
            sb.append(",");
            sb.append("اللقب");
            sb.append(",");
            sb.append("الوظيفة");
            sb.append(",");
            sb.append("العنوان");
            sb.append(",");
            sb.append("رقم الهاتف");
            sb.append(",");
            sb.append("دائن");
            sb.append(",");
            sb.append("مدان بـ");
            sb.append("\r\n");
            list_Providers = providerOperation.getAll();
            for (Provider provider : list_Providers) {
                sb.append(provider.getId());
                sb.append(",");
                sb.append(provider.getFirst_name());
                sb.append(",");
                sb.append(provider.getLast_name());
                sb.append(",");
                sb.append(provider.getJob());
                sb.append(",");
                sb.append(provider.getAdress());
                sb.append(",");
                sb.append(provider.getPhone_number());
                sb.append(",");
                sb.append(provider.getCreditor_to());
                sb.append(",");
                sb.append(provider.getCreditor());
                sb.append("\r\n");
            }
            pw.write(sb.toString());
            pw.close();
            Alert alertWarning = new Alert(Alert.AlertType.INFORMATION);
            alertWarning.setHeaderText("تأكيد");
            alertWarning.setContentText("تم استخراج المعلومات بواسطة Excel بنجاح");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("حسنا");
            alertWarning.showAndWait();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
