package Controllers.newKitchenChef;

import BddPackage.FoodProductComposeOperation;
import Models.Drinks;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class DrinksManagmentControlleur implements Initializable {

    // fxml elements.
    @FXML
    private Button exportExcelButton;

    @FXML
    private Button exportPDFButton;

    @FXML
    private Button printButton;

    @FXML
    private Button closeButton;

    @FXML
    private Button saveButton;

    @FXML
    private Label drinkNumberLabel;

    @FXML
    private ComboBox<String> orderComboBox;

    @FXML
    private TextField searchTextField;

    @FXML
    private Button addNewDrinkButton;

    @FXML
    private TableView<Drinks> drinksTabel;

    @FXML
    private TableColumn<Drinks, Double> drinkPriceColumn;

    @FXML
    private TableColumn<Drinks, String> drinkDescriptionColumn;

    @FXML
    private TableColumn<Drinks, Integer> drinkNumberColumn;

    @FXML
    private TableColumn<Drinks, String> drinkNameColumn;

    @FXML
    private TableColumn<Drinks, ImageView> drinkImageColumn;

    @FXML
    private TableColumn<Drinks, CheckBox> drinkCheckBoxTableColumn;

    @FXML
    private MenuButton moreButton;

    @FXML
    private MenuItem updateMenuItem;

    @FXML
    private MenuItem deleteMenuItem;



    // variables.
    ObservableList<Drinks> originalDrinksList =  FXCollections.observableArrayList();
    ObservableList<Drinks> temporaryDrinkList = FXCollections.observableArrayList();
    ObservableList<Drinks> tableViewDrinkList = FXCollections.observableArrayList();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // set the buttons action methods.
        addNewDrinkButton.setOnAction(actionEvent -> {
            try {
                addNewDrink();
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        /*closeButton.setOnAction(actionEvent -> {
            cancelChanges();
        });*/

        exportExcelButton.setOnAction(actionEvent -> {
            try {
                exportExcel();
            } catch (IOException e) {
                e.printStackTrace();
            }
            PDFReportGenerators.showNotification(
                    "تخراج EXCEL",
                    "تم استخراج ملف ال EXCEL",
                    2,
                    false);
        });

        exportPDFButton.setOnAction(actionEvent -> {
            try {
                PDFReportGenerators.exportDrinkItemsPDF(addNewDrinkButton, tableViewDrinkList);
                PDFReportGenerators.showNotification(
                        "تخراج PDF",
                        "تم استخراج ملف ال PDF",
                        2,
                        false);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        });

        printButton.setOnAction(actionEvent -> {
            try {
                PDFReportGenerators.printDrinkItemsReport(addNewDrinkButton, tableViewDrinkList);
                PDFReportGenerators.showNotification(
                        "طباعة PDF",
                        "تم عرض الملف من أجل الطباعة ",
                        2,
                        false);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        });


        // set the search textField on change method.
        searchTextField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            searchDrinksByName(newValue);
        });

        // add the order condition to the order comboBox.
        List<String> orderStringItems = new ArrayList<>();
        orderStringItems.add("الثمن");
        orderStringItems.add("الاسم");
        orderStringItems.add("الكل");
        ObservableList orderItems = FXCollections.observableList(orderStringItems);
        orderComboBox.setItems(orderItems);
        orderComboBox.setOnAction(actionEvent -> {
            if (orderComboBox.getValue().equals("الثمن")){
                //System.out.println(orderComboBox.getValue());
                showDrinsBasedOnPriceOrder();
            }else{
                if (orderComboBox.getValue().equals("الاسم")){
                    // System.out.println(orderComboBox.getValue());
                    showDrinksBasedOnNameOrder();
                }else{
                    showDrinksBasedOnOriginalOrder();
                }
            }
        });

        // set the more button items.
        updateMenuItem.setOnAction(actionEvent -> {
            try {
                updateDrinkData();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        deleteMenuItem.setOnAction(actionEvent -> {
            removeButton();
        });


        // load the food data.
        loadData();
    }

    // method that loads the data.
    void loadData(){

        // get the data from database.
        FoodProductComposeOperation databaseConenctor = new FoodProductComposeOperation();


        // set teh table view.
        drinkPriceColumn.setCellValueFactory( new PropertyValueFactory<Drinks,Double>("price"));
        drinkNameColumn.setCellValueFactory( new PropertyValueFactory<Drinks,String>("name"));
        drinkImageColumn.setCellValueFactory( new PropertyValueFactory<Drinks,ImageView>("image"));
        drinkDescriptionColumn.setCellValueFactory( new PropertyValueFactory<Drinks,String>("description"));
        drinkNumberColumn.setCellValueFactory( new PropertyValueFactory<Drinks,Integer>("id"));
        drinkCheckBoxTableColumn.setCellValueFactory( new PropertyValueFactory<Drinks,CheckBox>("drinkSelectedCheckBox"));

        drinksTabel.setItems(originalDrinksList);

        // set the number of found food items label.
        //int foundFoodItems = databaseConenctor.getCountDrink();
       // drinkNumberLabel.setText(String.valueOf(foundFoodItems));

    }

    // method to search by name.
    void searchDrinksByName(String searchedValue){
        // if search empty revert to original data.
        if (searchedValue.isEmpty()){
            tableViewDrinkList.clear();
            for (int foodIndex = 0; foodIndex < originalDrinksList.size(); foodIndex++) {
                tableViewDrinkList.add(originalDrinksList.get(foodIndex));
            }
            for (int foodIndex = 0; foodIndex < temporaryDrinkList.size(); foodIndex++) {
                tableViewDrinkList.add(temporaryDrinkList.get(foodIndex));
            }
            drinksTabel.setItems(tableViewDrinkList);
        }else{
            // search all the items in teh table view and remove the ones that do not start by that name.
            tableViewDrinkList.clear();
            for (int foodIndex = 0; foodIndex < originalDrinksList.size(); foodIndex++) {
                if (originalDrinksList.get(foodIndex).getName().startsWith(searchedValue)){

                    tableViewDrinkList.add(originalDrinksList.get(foodIndex));
                }
            }
            drinksTabel.setItems(tableViewDrinkList);
        }

    }

    // method to show food based on name order.
    void showDrinksBasedOnNameOrder(){

        // reorder what is in teh table view list based on the name ordering.
        List<Drinks> tmpList = tableViewDrinkList
                .stream()
                .sorted((o1, o2) -> o1.getName().compareTo(o2.getName()) > 0 ? 1 : -1)
                .collect(Collectors.toList());
        tableViewDrinkList.clear();
        for (int index = 0; index < tmpList.size(); index++) {
            tableViewDrinkList.add(tmpList.get(index));
        }
        // assign the list to the table.
        drinksTabel.setItems(tableViewDrinkList);
        // assign the list to the table.
    }

    void showDrinksBasedOnOriginalOrder(){

        // go back to the original order.
        tableViewDrinkList.clear();
        for (int foodIndex = 0; foodIndex < originalDrinksList.size(); foodIndex++) {
            tableViewDrinkList.add(originalDrinksList.get(foodIndex));
        }
        for (int foodIndex = 0; foodIndex < temporaryDrinkList.size(); foodIndex++) {
            tableViewDrinkList.add(temporaryDrinkList.get(foodIndex));
        }
    }

    // method to show food based on price order.
    void showDrinsBasedOnPriceOrder(){

        // reorder what is in teh table view list based on the price ordering.
        List<Drinks> tmpList = tableViewDrinkList
                .stream()
                .sorted(Comparator.comparingInt(Drinks::getPrice))
                .collect(Collectors.toList());
        tableViewDrinkList.clear();
        for (int index = 0; index < tmpList.size(); index++) {
            tableViewDrinkList.add(tmpList.get(index));
        }
        // assign the list to the table.
        drinksTabel.setItems(tableViewDrinkList);
    }

    // method to add a food.
    void addNewDrink() throws IOException {

        // open the new add window.
        AnchorPane root = FXMLLoader.load(getClass().getResource("/Views/KitchenChef/newDrink.fxml"));
        Stage newDrinkStage = new Stage();
        newDrinkStage.setScene(new Scene(root));
        newDrinkStage.setTitle("اضافة مشروب");
        newDrinkStage.requestFocus();
        newDrinkStage.initModality(Modality.APPLICATION_MODAL);
        newDrinkStage.show();
    }

    // method to update the food.
    void updateDrinkData() throws IOException {

        // set the selected food in the update window class.
        boolean noDrinkIsSelected = true;
        for (int foodIndex = 0; foodIndex < tableViewDrinkList.size(); foodIndex++) {
            if (tableViewDrinkList.get(foodIndex).getDrinkSelectedCheckBox().isSelected() == true){
                UpdateDrinkControlleur.selectedDrinkData = tableViewDrinkList.get(foodIndex);
                noDrinkIsSelected =  false;
            }
        }
        if(noDrinkIsSelected == true){
            PDFReportGenerators.showErrorNotification(
                    "لم يتم اختيار أي عنصر",
                    "الرجاء اختيار عنصر لاجراء التعديل عليه",
                    1,
                    false
            );
        }else{
            // open the update food window.
            AnchorPane root = FXMLLoader.load(getClass().getResource("/Views/KitchenChef/updateDrink.fxml"));
            Stage newFoodStage = new Stage();
            newFoodStage.setScene(new Scene(root));
            newFoodStage.setTitle("تحديث المعلومات");
            newFoodStage.requestFocus();
            newFoodStage.initModality(Modality.APPLICATION_MODAL);
            newFoodStage.show();
        }
    }

    // method to remove the selected food.
    void removeButton(){

        // create the alert msg before deleting.
        Alert deletionAlert = new Alert(Alert.AlertType.CONFIRMATION);
        deletionAlert.setTitle("حدف العناصر");
        deletionAlert.setContentText("سيتم حدف العناصر التي تم اختيارها بصفة دائمة ادا قمت بتأكيد هدا الرسالة");
        deletionAlert.setHeaderText("تأكيد حدف العناصر");
        //deletionAlert.setWidth(600);
        ButtonType cancelButton = new ButtonType("الغاء", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType confirmationButton = new ButtonType("حدف", ButtonBar.ButtonData.YES);
        deletionAlert.getDialogPane().getButtonTypes().clear();
        deletionAlert.getDialogPane().getButtonTypes().add(cancelButton);
        deletionAlert.getDialogPane().getButtonTypes().add(confirmationButton);
        Optional<ButtonType> alertresult =  deletionAlert.showAndWait();

        if (alertresult.get().getText().equals("حدف")){
            // add warnings.
            FoodProductComposeOperation databseConnector  = new FoodProductComposeOperation();
            for (int foodIndex = 0; foodIndex < tableViewDrinkList.size(); foodIndex++) {
                if (tableViewDrinkList.get(foodIndex).getDrinkSelectedCheckBox().isSelected() == true){
                    // remove from database.
                    //databseConnector.delete(tableViewDrinkList.get(foodIndex));
                    // remove from the other list.
                    originalDrinksList.remove(tableViewDrinkList.get(foodIndex));
                    // remove from the temporary list.
                    temporaryDrinkList.remove(tableViewDrinkList.get(foodIndex));
                    // remove from teh view.
                    tableViewDrinkList.remove(tableViewDrinkList.get(foodIndex));
                }
            }
            PDFReportGenerators.showNotification(
                    "حدف العناصر",
                    "تم حدف العناصر بنجاح",
                    1,
                    false
            );
        }

    }

    // method to export excel file
    void exportExcel() throws IOException {
        // export what is in the table view as an excel file.
        // show place picker.
        String filePath = "";
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export Excel File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"));


        // create a .csv file and write to it.
        File excelFile = new File(filePath);
        FileWriter fileWriter =  new FileWriter(filePath);
        // the excel titles.
        fileWriter.write("رقم الطعام,الاسم,الثمن,وصف\n");
        // add all the data from teh table view.
        for (int foodIndex = 0; foodIndex < tableViewDrinkList.size(); foodIndex++) {
            fileWriter.write(
                    tableViewDrinkList.get(foodIndex).getId() + ","+
                            tableViewDrinkList.get(foodIndex).getName() + "," +
                            tableViewDrinkList.get(foodIndex).getPrice() + "," +
                            tableViewDrinkList.get(foodIndex).getDescription() + "\n"
            );
        }
        fileWriter.close();
    }

    // method export PDF file.
    String exportPDF() throws IOException, DocumentException {

        // show place picker.
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(addNewDrinkButton.getScene().getWindow());

        String path = selectedDirectory.getAbsolutePath();
        Document document = new Document();
        String newPath = path+"/report"+new Date().getTime()+".pdf";
        PdfWriter.getInstance(document,new FileOutputStream(newPath));
        document.open();

        com.itextpdf.text.Font font1 = new com.itextpdf.text.Font();
        font1.setColor(BaseColor.LIGHT_GRAY);
        font1.setSize(30);
        font1.setStyle("BOLD");
        font1.setFamily("TIMES_ROMAN");

        Paragraph title = new Paragraph("List Des Produits",font1);
        title.setAlignment(Element.ALIGN_CENTER);

        document.add(title);
        document.add(new Paragraph("  "));
        document.add(new Paragraph("  "));

        com.itextpdf.text.Font font2 = new com.itextpdf.text.Font();
        font2.setColor(BaseColor.BLACK);
        font2.setSize(14);
        font2.setStyle("BOLD");
        font2.setFamily("TIMES_ROMAN");
        LocalDateTime curentDate = LocalDateTime.now();
        document.add(new Paragraph("Date : " + curentDate,font2));
        document.add(new Paragraph("Nombre de Produit : " + tableViewDrinkList.size(),font2));
        document.add(new Paragraph("  "));
        document.add(new Paragraph("  "));
        document.add(new Paragraph("  "));


        // create the table.
        com.itextpdf.text.Font font3 = new com.itextpdf.text.Font();
        font3.setColor(BaseColor.WHITE);
        font3.setSize(16);
        font3.setStyle("BOLD");
        font3.setFamily("TIMES_ROMAN");


        PdfPTable pdfPTable = new PdfPTable(4);

        PdfPCell numberCell = new PdfPCell(new Paragraph("ID",font3));
        numberCell.setBorder(0);
        numberCell.setBackgroundColor(BaseColor.LIGHT_GRAY);

        PdfPCell nameCell = new PdfPCell(new Paragraph("nom",font3));
        nameCell.setBorder(0);
        nameCell.setBackgroundColor(BaseColor.LIGHT_GRAY);

        PdfPCell priceCell = new PdfPCell(new Paragraph("prix",font3));
        priceCell.setBorder(0);
        priceCell.setBackgroundColor(BaseColor.LIGHT_GRAY);

        PdfPCell descriptionCell = new PdfPCell(new Paragraph("description",font3));
        descriptionCell.setBorder(0);
        descriptionCell.setBackgroundColor(BaseColor.LIGHT_GRAY);

        pdfPTable.addCell(numberCell);
        pdfPTable.addCell(nameCell);
        pdfPTable.addCell(priceCell);
        pdfPTable.addCell(descriptionCell);

        // add the data.
        boolean isRawColored = false;
        for (int drinkIndex = 0; drinkIndex < tableViewDrinkList.size(); drinkIndex++) {

            PdfPCell foodNumberCell = new PdfPCell(new Paragraph(String.valueOf(tableViewDrinkList.get(drinkIndex).getId())));
            foodNumberCell.setBorder(0);
            PdfPCell foodNameCell = new PdfPCell(new Paragraph(tableViewDrinkList.get(drinkIndex).getName()));
            foodNameCell.setBorder(0);
            PdfPCell foodPriceCell = new PdfPCell(new Paragraph(String.valueOf(tableViewDrinkList.get(drinkIndex).getPrice())));
            foodPriceCell.setBorder(0);
            PdfPCell foodDescriptionCell = new PdfPCell(new Paragraph(tableViewDrinkList.get(drinkIndex).getDescription()));
            foodDescriptionCell.setBorder(0);

            if (isRawColored == true){
                foodNumberCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                foodNameCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                foodPriceCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                foodDescriptionCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            }

            pdfPTable.addCell(foodNumberCell);
            pdfPTable.addCell(foodNameCell);
            pdfPTable.addCell(foodPriceCell);
            pdfPTable.addCell(foodDescriptionCell);

            isRawColored = ! isRawColored;

        }

        // add the object.
        document.add(pdfPTable);
        document.close();
        return newPath;
    }

    // method to print file.
    void printFile() throws IOException, DocumentException {

        // get the generated file path.
        String filePath = exportPDF();

        // show the pdf.
        File file = new File(filePath);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Desktop.getDesktop().open(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
