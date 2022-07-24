package Models;

import BddPackage.*;
import Controllers.Tables.OrdersServer;
import Controllers.Tables.TabelActiveController;
import Controllers.Tables.TablesController;
import Controllers.newKitchenChef.KitchenOrdersControlleur;
import Controllers.newKitchenChef.PDFReportGenerators;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

public class ClientSocket implements Runnable{
    private Socket socket;
    private DataInputStream in;
    private  BufferedWriter out;
    static String foodsString = "";
    static String categoriesString = "";
    // variables.
    public static ArrayList<Orders> ordersList = new ArrayList<Orders>();


    static int orderCounter = 0;

    // add a mechanism to know which order has been seen and which has not.
    static ArrayList<Boolean> orderSeenState = new ArrayList<>();
    public ClientSocket(Socket socket) throws IOException {
        this.socket=socket;
        in=new DataInputStream(new BufferedInputStream(this.socket.getInputStream()));
        out=new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
    }

    @Override
    public void run() {
        String str;
        try {

            String msg = in.readLine();
            System.out.println("message is " + msg);


            switch(msg) {
                case "sendData": {
                    //address = socket.getInetAddress();
                    System.out.println("From :" + socket.getInetAddress());
                    System.out.println("message is " + msg);
                    // get the data from database and send it to the client.

                    if (foodsString == "") {
                        foodsString = getFoodDataFromDataBase();
                    }
                    out.write(foodsString);
                    out.flush();
                    out.close();

                }
                break;
                case "getCategories": {
                    // send the categories.
                    if (categoriesString == "") {
                        categoriesString = getCategoriesFromDAtaBase();
                    }
                    out.write(categoriesString);
                    out.flush();
                    out.close();
                }
                break;
                case "localhost": {
                    OrdersServer.ListClient.add(this);
                    out.write("update");
                    out.newLine();
                    out.flush();

                }
                break;
                case "confirmOrder": {
                    ArrayList<ClientSocket> liste = OrdersServer.ListClient;
                    for (ClientSocket clientSocket : liste) {
                        System.out.println("AllclientSocket=" + clientSocket);
                        if (!clientSocket.socket.isClosed()) {
                            clientSocket.out.write("11111111111");
                            clientSocket.out.newLine();
                            clientSocket.out.flush();
                            System.out.println("clientSocket=" + clientSocket);
                        }

                    }
                }
                break;
                case "getAvailiableTables": {
                    // case of sending table data and reciving and table reservatoion.
                    TabelsOperation databaseConnector = new TabelsOperation();

                    // send table :
                    // get list of table from database.
                    ArrayList<Tables> tablesList = databaseConnector.getAll();
                    // create the availiable tables msg tableID/tableID....
                    String availiableTablesMSG = "";
                    for (int index = 0; index < tablesList.size(); index++) {
                        if ( tablesList.get(index).getId() != 99) {
                            availiableTablesMSG = availiableTablesMSG
                                    + tablesList.get(index).getId()
                                    + "/";
                        }
                    }
                    // remove the last /
                    availiableTablesMSG = availiableTablesMSG.substring(0, availiableTablesMSG.length() - 1);
                    // send the msg.
                    out.write(availiableTablesMSG);
                    out.flush();
                    out.close();

                }
                break;
                default: {
                    //check if its a order or a table reservation.
                    if (msg.startsWith("table")) {
                        TabelsOperation databaseConnector = new TabelsOperation();
                        // receive a msg of reservation:
                        // decipher the data.
                        String reservedTableID = msg.split(":")[1];
                        // update the database.
                        // get thebale where the table number eqauk to what was recieved
                        Tables reservedTableData = databaseConnector.getTable(Integer.parseInt(reservedTableID));
                        // update the table status.
                        if (reservedTableData != null) {
                            Tables newTableState = new Tables(
                                    reservedTableData.getId(),
                                    reservedTableData.getNumber(),
                                    String.valueOf(true)
                            );
                            databaseConnector.update(reservedTableData, newTableState);
                        }
                    } else {
                        System.out.println("Order From :" + socket.getInetAddress());
                        System.out.println("message is " + msg);
                        // add the orders to the orders list.

                        addOrder(msg);

                        // add the order state to teh orderSeenState.
                        orderSeenState.add(false);
                        // change the variable new Order in the tablesController.
                        //TablesController.newOrder.setValue(! TablesController.newOrder.getValue());
                        ArrayList<ClientSocket> liste = OrdersServer.ListClient;
                        for (ClientSocket clientSocket : liste) {
                            System.out.println("AllclientSocket=" + clientSocket);
                            if (!clientSocket.socket.isClosed()) {
                                clientSocket.out.write("11111111111");
                                clientSocket.out.newLine();
                                clientSocket.out.flush();
                                System.out.println("clientSocket=" + clientSocket);
                            }

                        }

                    }
                }
            }
} catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static <list> void addOrder(String orderData){

        // add the indication if its a drink or a food.
        // the data will be like : idTable@price@food1=2,food2=2,foodX=X@drink1=6,drink2=5,drinkX=X
        // 3@3500@food1=2,food2=2@+drink1=6,drink2=5
        String[] data = orderData.split("@");
        String id = data[0];
        String price = data[1];
        String[] foods = data[2].split(",");
        String[] drinks;
        if (data.length == 4){
            drinks = data[3].split(",");
        }else{
            drinks = new String[0];
        }



        Orders newOrder = new Orders();
        // construct the food list.
        ArrayList<FoodOrder> foodsList = new ArrayList<>();
        for (int foodIndex = 0; foodIndex < foods.length; foodIndex++) {
            String[] foodData = foods[foodIndex].split("=");
            String foodId = foodData[0];
            String foodQuantity = foodData[1];

            foodsList.add(
                    new FoodOrder(
                            foodIndex,
                            Integer.parseInt(foodId),
                            Integer.parseInt(foodQuantity)
                    )
            );
        }

        // construct the drinks list.
        ArrayList<DrinksOrder> drinksList = new ArrayList<>();
        for (int drinksIndex = 0; drinksIndex < drinks.length; drinksIndex++) {
            String[] drinksData = drinks[drinksIndex].split("=");
            String foodId = drinksData[0];
            String foodQuantity = drinksData[1];

            drinksList.add(
                    new DrinksOrder(
                            drinksIndex,
                            Integer.parseInt(foodId),
                            Integer.parseInt(foodQuantity)
                    )
            );
        }

        // construct the order.
        newOrder.setId(orderCounter);
        newOrder.setId_table(Integer.parseInt(id));
        newOrder.setPrice(Double.parseDouble(price));
        newOrder.setFoodsList(foodsList);
        newOrder.setTime(new Date());

        orderCounter++;

        // add the order.
        ordersList.add(newOrder);
        // add the order state.
        orderSeenState.add(false);

        // notify the tables order view.
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // add the order to the kitchen.
                    KitchenOrdersControlleur.ordersIdsQueue.add(String.valueOf(newOrder.getId()));
                    KitchenOrdersControlleur.newOrder.setValue(! KitchenOrdersControlleur.newOrder.getValue());
                    // show the notification.
                    PDFReportGenerators.showNotification(
                            "طلب جديد",
                            "تم استلام طلب جديد ",
                            1,
                            false);
                    Orders orders=new Orders();
                    OrderPrint orderPrint=new OrderPrint();
                    ArrayList<Orders> list = new ArrayList<>();
                    OrdersOperation ordersOperation=new OrdersOperation();
                    orderPrint.setId_table(newOrder.getId_table());
                    orderPrint.setPrice(newOrder.getPrice());
                    orderPrint.setFoodsList(newOrder.getFoodsList());

                    list=ordersOperation.getAllOrder();
                    if(list.size()>0)
                    orders.setId(list.get(list.size()-1).getId()+1);
                    else orders.setId(1);
                    orders.setId_table(newOrder.getId_table());
                    orders.setPrice(newOrder.getPrice());
                    orders.setFoodsList(newOrder.getFoodsList());
                    AjouteOrder(orders,orderPrint);
                    System.out.println("131");
                    String path=System.getProperty("user.dir") +"/Sound/notificationSound.mp3";
                    System.out.println(path);
                    print(orders);

                    File f = new File(path);
                    Media hit = new Media(f.toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(hit);
                    mediaPlayer.play();
                }catch(Exception e){
                    e.printStackTrace();
                }
                TabelActiveController.newOrder.setValue(!TabelActiveController.newOrder.getValue());
                TablesController.newOrder.setValue(! TablesController.newOrder.getValue());

            }
        });

        // add the sound notification.




    }

    private static void print(Orders orders){
        Connet connet = new Connet();
        Connection con = connet.connection();
        try {
            String report =  System.getProperty("user.dir") +"/Report/print.jrxml";
            JasperDesign jasperDesign = JRXmlLoader.load(report);
            String sqlCmd = "SELECT ordersprint.ORDER_TIME,ordersprint.ORDER_PRICE,food_order_print.ORDER_QUANTITY,food.FOOD_NAME,food.FOOD_PRICE,ordersprint.ID_TABLE_ORDER ,settings.Name,settings.PhoneNambe1,settings.PhoneNambe2,settings.Adress\n" +
                    "FROM `ordersprint`,food_order_print,food,settings\n" +
                    "WHERE ordersprint.ID_ORDER=food_order_print.ID_ORDER and food_order_print.ID_FOOD=food.ID_FOOD and ordersprint.ID_ORDER="+OrderPrintOpertion.orderprint;
            JRDesignQuery jrDesignQuery = new JRDesignQuery();
            jrDesignQuery.setText(sqlCmd);
            jasperDesign.setQuery(jrDesignQuery);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            Connection connection = null;

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, con);
            //JasperViewer.viewReport(jasperPrint,false);
            JasperPrintManager.printReport(jasperPrint,false);

        } catch (JRException e) {
            e.printStackTrace();
        }
    }


    public static String getFoodDataFromDataBase() throws FileNotFoundException {

        // get all the foods list.
        String allFoodString = "";
        FoodOperation databaseConnector = new FoodOperation();
        ArrayList<Food> allFoods =  databaseConnector.getAllActive();

        for (int foodIndex = 0; foodIndex < allFoods.size(); foodIndex++) {
            //string will be like
            // id/category/name/desc/price/foodRating/imagebytes - nextimageData
            String foodData;
            foodData = String.valueOf(allFoods.get(foodIndex).getId()) + "@"
                    + String.valueOf(allFoods.get(foodIndex).getId_category()) + "@"
                    + String.valueOf(allFoods.get(foodIndex).getName()) + "@"
                    + String.valueOf(allFoods.get(foodIndex).getDescription()) + "@"
                    + String.valueOf(allFoods.get(foodIndex).getPrice()) + "@"
                    + String.valueOf(allFoods.get(foodIndex).getRating()) + "@";

            // transfer the image into bytes.
            String base64String = null;
/*
            //===================
            try {
                String url=allFoods.get(foodIndex).getImage_path();
                url = url.substring(5, url.length());
                System.out.println(url);
                BufferedImage bImage = ImageIO.read(new File(url));
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ImageIO.write(bImage, "jpg", bos );
                byte [] data = bos.toByteArray();
                 base64String= Base64.encode(data);

            } catch (IOException e) {
                System.err.println("file reading error");
            }*/
            byte [] data;
            data=allFoods.get(foodIndex).getIMAGE();
            base64String= Base64.encode(data);
            foodData = foodData+base64String+"-";
            System.out.println(foodData);
            allFoodString = allFoodString + foodData;


        }

        allFoodString = allFoodString.substring(0, allFoodString.length() - 1 );
        System.out.println(allFoodString);
        return allFoodString ;

    }

    public static String getCategoriesFromDAtaBase(){

        // get the food categories.
        String categoriesString = "";
        // category will be like : id/name/color.
        FoodCategoryOperation dataBaseConnector = new FoodCategoryOperation();
        ArrayList<FoodCategory> categories = dataBaseConnector.getAll();

        for (int categoryIndex = 0; categoryIndex < categories.size(); categoryIndex++) {
            categoriesString = categoriesString + categories.get(categoryIndex).getId() + "/";
            categoriesString = categoriesString + categories.get(categoryIndex).getName() + "/";
            categoriesString = categoriesString + categories.get(categoryIndex).getColor() + "/";
            categoriesString = categoriesString + "1-";
        }

        // get the drinks categories.
        categoriesString = categoriesString.substring(0, categoriesString.length() - 1 );
        System.out.println(categoriesString);

        return categoriesString;
    }
    static void AjouteOrder(Orders orders,OrderPrint orderPrint){
        FoodOrder foodOrder=new FoodOrder();
        FoodOrderPrint foodOrderPrint=new FoodOrderPrint();

        TabelsOperation tabelsOperation1=new TabelsOperation();
        String up=tabelsOperation1.Tabel(orders.getId_table());
        OrdersOperation orderDatabaseConnector = new OrdersOperation();
        OrderPrintOpertion orderPrintOpertion=new OrderPrintOpertion();
        orderPrintOpertion.insert(orderPrint);
        if(up.equals("true")){
            orderDatabaseConnector.insert(orders);
            Tables tables=new Tables();
            tables.setId(orders.getId_table());
            tables.setOrderId(orders.getId());
            tabelsOperation1.update(tables);
        }else{
            Tables tables=new Tables();
            Orders ordersnew=new Orders();
            tables.setId(orders.getId_table());
            orders.setId(tabelsOperation1.getIdOrder(tables));
            System.out.println(orders.getId()+"      ttttttttttttttttttttttttttttttttttttttt");
            ordersnew=orderDatabaseConnector.getOrder(orders);
            ordersnew.setPrice(ordersnew.getPrice()+orders.getPrice());
            orderDatabaseConnector.update(ordersnew,orders);


        }

        // add the food order.
        FoodOrderOperation foodDatabaseConnector = new FoodOrderOperation();
        FoodOrderPrintOperation foodOrderPrintOperation=new FoodOrderPrintOperation();
        for (int foodIndex = 0; foodIndex < orders.getFoodsList().size(); foodIndex++) {
            foodOrder=orders.getFoodsList().get(foodIndex);
            foodOrder.setId_order(orders.getId());
            foodDatabaseConnector.insert(foodOrder);
            foodOrder.setId_order(OrderPrintOpertion.orderprint);
            foodOrderPrintOperation.insert(foodOrder);
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
        TabelsOperation tabelsOperation=new TabelsOperation();
        Tables table=new Tables();
        table.setId(orders.getId_table());
        tabelsOperation.desActivTabel(table);
    }

}
