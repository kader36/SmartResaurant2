package Controllers.newKitchenChef;

import Models.Drinks;
import Models.Food;
import com.github.plushaze.traynotification.animations.Animations;
import com.github.plushaze.traynotification.notification.Notification;
import com.github.plushaze.traynotification.notification.TrayNotification;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfDiv;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.stage.DirectoryChooser;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;

public class PDFReportGenerators {

    static String exportFoodItemsPDF(Button sceneButton, ObservableList<Food> tableViewFoodList) throws IOException, DocumentException {

        // show place picker.
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(sceneButton.getScene().getWindow());

        String path = selectedDirectory.getAbsolutePath();
        Document document = new Document(PageSize.A4);
        String newPath = path+"/report"+new Date().getTime()+".pdf";
        PdfWriter.getInstance(document,new FileOutputStream(newPath));
        document.open();


        com.itextpdf.text.Font font1 = new com.itextpdf.text.Font();
        font1.setColor(BaseColor.WHITE);
        font1.setSize(30);
        font1.setStyle("BOLD");
        font1.setFamily("TIMES_ROMAN");

        Paragraph title = new Paragraph("List Des Produits",font1);
        title.setAlignment(Element.ALIGN_LEFT);


        PdfDiv pdfDiv = new PdfDiv();
        pdfDiv.setTextAlignment(Element.ALIGN_CENTER);
        pdfDiv.setBackgroundColor(new BaseColor(238,61,72));
        pdfDiv.setWidth(520f);
        pdfDiv.setSpacingAfter(0f);
        pdfDiv.setSpacingBefore(0f);
        pdfDiv.setPaddingLeft(15f);
        pdfDiv.setPaddingRight(15f);
        pdfDiv.setPaddingTop(10f);
        pdfDiv.setPaddingBottom(10f);


        pdfDiv.addElement(title);
        pdfDiv.addElement(new Paragraph("  "));


        com.itextpdf.text.Font font2 = new com.itextpdf.text.Font();
        font2.setColor(BaseColor.WHITE);
        font2.setSize(12);
        font2.setStyle("BOLD");
        font2.setFamily("TIMES_ROMAN");

        LocalDateTime curentDate = LocalDateTime.now();
        pdfDiv.addElement(new Paragraph("Date : " + curentDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)),font2));
        pdfDiv.addElement(new Paragraph("Nombre de Produit : " + tableViewFoodList.size(),font2));
        pdfDiv.addElement(new Paragraph("  "));

        document.add(pdfDiv);

        document.add(new Paragraph("  "));
        document.add(new Paragraph("  "));

        // create the table.
        com.itextpdf.text.Font font3 = new com.itextpdf.text.Font();
        font3.setColor(BaseColor.WHITE);
        font3.setSize(16);
        font3.setStyle("BOLD");
        font3.setFamily("TIMES_ROMAN");

        com.itextpdf.text.Font font3Small = new com.itextpdf.text.Font();
        font3Small.setColor(BaseColor.WHITE);
        font3Small.setSize(12);
        font3Small.setStyle("BOLD");
        font3Small.setFamily("TIMES_ROMAN");

        com.itextpdf.text.Font font3SmallGrey = new com.itextpdf.text.Font();
        font3SmallGrey.setColor(BaseColor.GRAY);
        font3SmallGrey.setSize(12);
        font3SmallGrey.setStyle("BOLD");
        font3SmallGrey.setFamily("TIMES_ROMAN");


        PdfPTable pdfPTable = new PdfPTable(4);

        PdfPCell numberCell = new PdfPCell(new Paragraph("ID",font3));
        numberCell.setBorder(0);
        numberCell.setBackgroundColor(new BaseColor(61,170,187));

        PdfPCell nameCell = new PdfPCell(new Paragraph("Nom",font3));
        nameCell.setBorder(0);
        nameCell.setBackgroundColor(new BaseColor(61,170,187));

        PdfPCell priceCell = new PdfPCell(new Paragraph("Prix",font3));
        priceCell.setBorder(0);
        priceCell.setBackgroundColor(new BaseColor(61,170,187));

        PdfPCell descriptionCell = new PdfPCell(new Paragraph("Description",font3));
        descriptionCell.setBorder(0);
        descriptionCell.setBackgroundColor(new BaseColor(61,170,187));

        pdfPTable.addCell(numberCell);
        pdfPTable.addCell(nameCell);
        pdfPTable.addCell(priceCell);
        pdfPTable.addCell(descriptionCell);

        // add the data.
        boolean isRawColored = false;
        for (int foodIndex = 0; foodIndex < tableViewFoodList.size(); foodIndex++) {

            PdfPCell foodNumberCell;
            PdfPCell foodNameCell;
            PdfPCell foodPriceCell;
            PdfPCell foodDescriptionCell;


            if (isRawColored == true){
                 foodNumberCell = new PdfPCell(new Paragraph(String.valueOf(tableViewFoodList.get(foodIndex).getId()),font3Small));
                foodNumberCell.setBorder(0);
                 foodNameCell = new PdfPCell(new Paragraph(tableViewFoodList.get(foodIndex).getName(),font3Small));
                foodNameCell.setBorder(0);
                 foodPriceCell = new PdfPCell(new Paragraph(String.valueOf(tableViewFoodList.get(foodIndex).getPrice()),font3Small));
                foodPriceCell.setBorder(0);
                 foodDescriptionCell = new PdfPCell(new Paragraph(tableViewFoodList.get(foodIndex).getDescription(),font3Small));
                foodDescriptionCell.setBorder(0);

                foodNumberCell.setBackgroundColor(new BaseColor(61,170,187));
                foodNameCell.setBackgroundColor(new BaseColor(61,170,187));
                foodPriceCell.setBackgroundColor(new BaseColor(61,170,187));
                foodDescriptionCell.setBackgroundColor(new BaseColor(61,170,187));
            }else{
                 foodNumberCell = new PdfPCell(new Paragraph(String.valueOf(tableViewFoodList.get(foodIndex).getId()),font3SmallGrey));
                foodNumberCell.setBorder(0);
                 foodNameCell = new PdfPCell(new Paragraph(tableViewFoodList.get(foodIndex).getName(),font3SmallGrey));
                foodNameCell.setBorder(0);
                 foodPriceCell = new PdfPCell(new Paragraph(String.valueOf(tableViewFoodList.get(foodIndex).getPrice()),font3SmallGrey));
                foodPriceCell.setBorder(0);
                 foodDescriptionCell = new PdfPCell(new Paragraph(tableViewFoodList.get(foodIndex).getDescription(),font3SmallGrey));
                foodDescriptionCell.setBorder(0);

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

    static String exportDrinkItemsPDF(Button sceneButton, ObservableList<Drinks> tableViewFoodList) throws IOException, DocumentException {

        // show place picker.
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(sceneButton.getScene().getWindow());

        String path = selectedDirectory.getAbsolutePath();
        Document document = new Document(PageSize.A4);
        String newPath = path+"/report"+new Date().getTime()+".pdf";
        PdfWriter.getInstance(document,new FileOutputStream(newPath));
        document.open();


        com.itextpdf.text.Font font1 = new com.itextpdf.text.Font();
        font1.setColor(BaseColor.WHITE);
        font1.setSize(30);
        font1.setStyle("BOLD");
        font1.setFamily("TIMES_ROMAN");

        Paragraph title = new Paragraph("List Des Produits",font1);
        title.setAlignment(Element.ALIGN_LEFT);


        PdfDiv pdfDiv = new PdfDiv();
        pdfDiv.setBackgroundColor(new BaseColor(238,61,72));
        pdfDiv.setWidth(520f);
        pdfDiv.setSpacingAfter(0f);
        pdfDiv.setSpacingBefore(0f);
        pdfDiv.setPaddingLeft(15f);
        pdfDiv.setPaddingRight(15f);
        pdfDiv.setPaddingTop(10f);
        pdfDiv.setPaddingBottom(10f);


        pdfDiv.addElement(title);
        pdfDiv.addElement(new Paragraph("  "));


        com.itextpdf.text.Font font2 = new com.itextpdf.text.Font();
        font2.setColor(BaseColor.WHITE);
        font2.setSize(12);
        font2.setStyle("BOLD");
        font2.setFamily("TIMES_ROMAN");

        LocalDateTime curentDate = LocalDateTime.now();
        pdfDiv.addElement(new Paragraph("Date : " + curentDate,font2));
        pdfDiv.addElement(new Paragraph("Nombre de Produit : " + tableViewFoodList.size(),font2));
        pdfDiv.addElement(new Paragraph("  "));

        document.add(pdfDiv);

        document.add(new Paragraph("  "));
        document.add(new Paragraph("  "));

        // create the table.
        com.itextpdf.text.Font font3 = new com.itextpdf.text.Font();
        font3.setColor(BaseColor.WHITE);
        font3.setSize(16);
        font3.setStyle("BOLD");
        font3.setFamily("TIMES_ROMAN");

        com.itextpdf.text.Font font3Small = new com.itextpdf.text.Font();
        font3Small.setColor(BaseColor.WHITE);
        font3Small.setSize(12);
        font3Small.setStyle("BOLD");
        font3Small.setFamily("TIMES_ROMAN");

        com.itextpdf.text.Font font3SmallGrey = new com.itextpdf.text.Font();
        font3SmallGrey.setColor(BaseColor.GRAY);
        font3SmallGrey.setSize(12);
        font3SmallGrey.setStyle("BOLD");
        font3SmallGrey.setFamily("TIMES_ROMAN");


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
        for (int foodIndex = 0; foodIndex < tableViewFoodList.size(); foodIndex++) {

            PdfPCell foodNumberCell;
            PdfPCell foodNameCell;
            PdfPCell foodPriceCell;
            PdfPCell foodDescriptionCell;


            if (isRawColored == true){
                foodNumberCell = new PdfPCell(new Paragraph(String.valueOf(tableViewFoodList.get(foodIndex).getId()),font3Small));
                foodNumberCell.setBorder(0);
                foodNameCell = new PdfPCell(new Paragraph(tableViewFoodList.get(foodIndex).getName(),font3Small));
                foodNameCell.setBorder(0);
                foodPriceCell = new PdfPCell(new Paragraph(String.valueOf(tableViewFoodList.get(foodIndex).getPrice()),font3Small));
                foodPriceCell.setBorder(0);
                foodDescriptionCell = new PdfPCell(new Paragraph(tableViewFoodList.get(foodIndex).getDescription(),font3Small));
                foodDescriptionCell.setBorder(0);

                foodNumberCell.setBackgroundColor(new BaseColor(61,170,187));
                foodNameCell.setBackgroundColor(new BaseColor(61,170,187));
                foodPriceCell.setBackgroundColor(new BaseColor(61,170,187));
                foodDescriptionCell.setBackgroundColor(new BaseColor(61,170,187));
            }else{
                foodNumberCell = new PdfPCell(new Paragraph(String.valueOf(tableViewFoodList.get(foodIndex).getId()),font3SmallGrey));
                foodNumberCell.setBorder(0);
                foodNameCell = new PdfPCell(new Paragraph(tableViewFoodList.get(foodIndex).getName(),font3SmallGrey));
                foodNameCell.setBorder(0);
                foodPriceCell = new PdfPCell(new Paragraph(String.valueOf(tableViewFoodList.get(foodIndex).getPrice()),font3SmallGrey));
                foodPriceCell.setBorder(0);
                foodDescriptionCell = new PdfPCell(new Paragraph(tableViewFoodList.get(foodIndex).getDescription(),font3SmallGrey));
                foodDescriptionCell.setBorder(0);

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
    static void printFoodItemsReport(Button sceneButton, ObservableList<Food> tableViewFoodList) throws IOException, DocumentException {

        // get the generated file path.
        String filePath = exportFoodItemsPDF(sceneButton,tableViewFoodList);

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

    // method to print file.
    static void printDrinkItemsReport(Button sceneButton, ObservableList<Drinks> tableViewFoodList) throws IOException, DocumentException {

        // get the generated file path.
        String filePath = exportDrinkItemsPDF(sceneButton,tableViewFoodList);

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

    static void makeClientBill(Button sceneButton) throws FileNotFoundException, DocumentException {

        // create the file.
        // add in the parameters the place where to save the bills.
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(sceneButton.getScene().getWindow());

        String path = selectedDirectory.getAbsolutePath();
        Document document = new Document(PageSize.LETTER);
        String newPath = path+"/report"+new Date().getTime()+".pdf";
        PdfWriter.getInstance(document,new FileOutputStream(newPath));
        document.open();
        // title
        com.itextpdf.text.Font font1 = new com.itextpdf.text.Font();
        font1.setColor(BaseColor.LIGHT_GRAY);
        font1.setSize(10);
        font1.setStyle("BOLD");
        font1.setFamily("TIMES_ROMAN");

        Paragraph title = new Paragraph("Facture",font1);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);


        // shop name.
        com.itextpdf.text.Font font2 = new com.itextpdf.text.Font();
        font2.setColor(BaseColor.LIGHT_GRAY);
        font2.setSize(6);
        font2.setStyle("BOLD");
        font2.setFamily("TIMES_ROMAN");

        Paragraph shopName = new Paragraph("Shop name",font2);
        title.setAlignment(Element.ALIGN_LEFT);
        document.add(shopName);

        // date.
        LocalDateTime curentDate = LocalDateTime.now();
        Paragraph date = new Paragraph(String.valueOf(curentDate),font2);
        title.setAlignment(Element.ALIGN_LEFT);
        document.add(date);

        // items.
        com.itextpdf.text.Font font3 = new com.itextpdf.text.Font();
        font3.setColor(BaseColor.LIGHT_GRAY);
        font3.setSize(6);
        font3.setStyle("BOLD");
        font3.setFamily("TIMES_ROMAN");

        PdfPTable pdfPTable = new PdfPTable(4);

        for (int index = 0; index < 10; index++) {

            PdfPCell itemName = new PdfPCell(new Paragraph("Item",font3));
            itemName.setBorder(0);
            itemName.setBackgroundColor(BaseColor.LIGHT_GRAY);

            PdfPCell quantitySign = new PdfPCell(new Paragraph("X",font3));
            quantitySign.setBorder(0);
            quantitySign.setBackgroundColor(BaseColor.LIGHT_GRAY);

            PdfPCell itemQuantity = new PdfPCell(new Paragraph("2",font3));
            itemQuantity.setBorder(0);
            itemQuantity.setBackgroundColor(BaseColor.LIGHT_GRAY);

            PdfPCell itemPrice = new PdfPCell(new Paragraph("2500",font3));
            itemPrice.setBorder(0);
            itemPrice.setBackgroundColor(BaseColor.LIGHT_GRAY);

            pdfPTable.addCell(itemName);
            pdfPTable.addCell(quantitySign);
            pdfPTable.addCell(itemQuantity);
            pdfPTable.addCell(itemPrice);
        }

        document.add(pdfPTable);

        // total.

        Paragraph totalPrice = new Paragraph("52366",font2);
        title.setAlignment(Element.ALIGN_RIGHT);

        document.add(totalPrice);
        document.close();

        // show the file.
        File file = new File(newPath);
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

    // method to show the notifications.
    public static void showNotification(String title, String notificationMsg, int showTimeInSecods, boolean isInfinit){

        Notifications notifications;
        if (isInfinit == true){
            Notification notification = com.github.plushaze.traynotification.notification.Notifications.SUCCESS;
            TrayNotification tray = new TrayNotification();
            tray.setTitle(title);
            tray.setMessage(notificationMsg);
            tray.setNotification(notification);
            tray.setAnimation(Animations.SLIDE);
            tray.showAndDismiss(Duration.INDEFINITE);
        }else{
            Notification notification = com.github.plushaze.traynotification.notification.Notifications.SUCCESS;
            TrayNotification tray = new TrayNotification();
            tray.setTitle(title);
            tray.setMessage(notificationMsg);
            tray.setNotification(notification);
            tray.setAnimation(Animations.SLIDE);
            tray.showAndDismiss(Duration.seconds(showTimeInSecods));
        }
    }


    // method to show the notifications.
    public static void showErrorNotification(String title, String notificationMsg, int showTimeInSecods, boolean isInfinit){

        Notifications notifications;
        if (isInfinit == true){
            Notification notification = com.github.plushaze.traynotification.notification.Notifications.ERROR;
            TrayNotification tray = new TrayNotification();
            tray.setTitle(title);
            tray.setMessage(notificationMsg);
            tray.setNotification(notification);
            tray.setAnimation(Animations.SLIDE);
            tray.showAndDismiss(Duration.INDEFINITE);
        }else{
            Notification notification = com.github.plushaze.traynotification.notification.Notifications.ERROR;
            TrayNotification tray = new TrayNotification();
            tray.setTitle(title);
            tray.setMessage(notificationMsg);
            tray.setNotification(notification);
            tray.setAnimation(Animations.SLIDE);
            tray.showAndDismiss(Duration.seconds(showTimeInSecods));
        }
    }
}
