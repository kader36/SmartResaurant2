package Controllers.stroreKeeper;

import BddPackage.ProviderJobOperation;
import Controllers.ValidateController;
import Models.Provider;
import Models.ProviderJob;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProviderJobController implements Initializable {

    @FXML
    private VBox jobPane;

    @FXML
    private DialogPane mainPane;

    @FXML
    private TextField txt_job_upt;

    @FXML
    private Button updateJobButton;

    @FXML
    private TableView<ProviderJob> providerJobTable;

    @FXML
    private TableColumn<ProviderJob, Integer> col_job_number;
    @FXML
    private TableColumn<ProviderJob, String> col_job_name;

    @FXML
    private VBox vboxOptionJob;
    @FXML
    private TextField txt_job;

    @FXML
    private Button insertJobButton;

    @FXML
    private Button cancelButtonJob;

    @FXML
    private Label lbl_valide;

    @FXML
    private Label errAddJob;

    @FXML
    private Label errUpdateJob;



    private ProviderJob providerJob = new ProviderJob();
    private ProviderJobOperation providerJobOperation = new ProviderJobOperation();

    private ObservableList<ProviderJob> dataTable = FXCollections.observableArrayList();
    private ArrayList<ProviderJob> listProviderJob = new ArrayList<>();
    private ValidateController validateController = new ValidateController();

    private FadeTransition fadeIn = new FadeTransition(
            Duration.millis(2000)
    );

    private boolean visibleJob = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void Init(ProviderJob Job) {
        txt_job_upt.setText(Job.getName());
        providerJob = Job;
    }

    public void InitJobList() {
        vboxOptionJob.setVisible(false);
        chargelistJobProvider();
        col_job_number.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_job_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        dataTable.setAll(listProviderJob);
        providerJobTable.setItems(dataTable);

    }

    private void chargelistJobProvider() {
        listProviderJob = providerJobOperation.getAll();
    }

    @FXML
    private void clickedMouse() {
        vboxOptionJob.setVisible(false);
        visibleJob = false;
        System.out.println("clicked VBOX");
    }

    @FXML
    void insertJob(ActionEvent event) {
        try {
            vboxOptionJob.setVisible(false);
            visibleJob = false;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/addJob.fxml"));
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
    void AddJobAction(ActionEvent event) {
        if (!txt_job.getText().isEmpty()) {
            providerJobOperation.insert(new ProviderJob(txt_job.getText()));
            txt_job.setText("");
            fadeIn.setNode(lbl_valide);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.setCycleCount(1);
            fadeIn.setAutoReverse(true);
            lbl_valide.setVisible(true);
            fadeIn.playFromStart();
            //  lbl_valide.setVisible(false);
        } else {
            errAddJob.setText("ادخل اسم الوظيفة الجديدة");
        }
    }

    @FXML
    void updateJob(ActionEvent event) {
        vboxOptionJob.setVisible(false);
        visibleJob = false;
        ProviderJob providerJob = providerJobTable.getSelectionModel().getSelectedItem();
        if (providerJob == null) {
            System.out.println("it's null");
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("تحذير ");
            alertWarning.setContentText("يرجى اختيار الوظيفة المراد تعديلها");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("حسنا");
            alertWarning.showAndWait();
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/updateJob.fxml"));
                DialogPane temp = loader.load();
                ProviderJobController providerJobController = loader.getController();
                providerJobController.Init(providerJob);
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
    void UpdateJobAction(ActionEvent event) {
        if (!txt_job_upt.getText().isEmpty() && !providerJob.getName().equals(txt_job_upt.getText())) {
            providerJobOperation.update(new ProviderJob(txt_job_upt.getText()), providerJob);
            close(updateJobButton);
        }else {
            errUpdateJob.setText("ادخل اسم الوظيفة الجديدة");
        }
    }

    @FXML
    void cancel(ActionEvent event) {
        close(cancelButtonJob);

    }

    @FXML
    void deleteJob(ActionEvent event) {
        vboxOptionJob.setVisible(false);
        visibleJob = false;
        providerJob = providerJobTable.getSelectionModel().getSelectedItem();
        if (providerJob != null) {
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
                    providerJobOperation.delete(providerJob);
                    refresh();
                }
            });
        } else {
            System.out.println("it's null");
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("تحذير ");
            alertWarning.setContentText("يرجى اختيار الوظيفة المراد حذفهاها");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("حسنا");
            alertWarning.showAndWait();
        }
    }

    @FXML
    void closeInsertJobDialog(MouseEvent event) {
        close(insertJobButton);
    }

    @FXML
    void closeUpdateJobDialog(MouseEvent event) {
        close(updateJobButton);
    }

    private void close(Button btn) {
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void showHideJobOperation(ActionEvent event) {
        visibleJob = showHideListOperation(vboxOptionJob, visibleJob);

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
    void txt_job_Pressed(KeyEvent event) {
        validateController.inputTextValueType(txt_job);
    }

    private void refresh() {
        chargelistJobProvider();
        dataTable.setAll(listProviderJob);
        providerJobTable.setItems(dataTable);
    }


}
