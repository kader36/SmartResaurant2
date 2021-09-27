package Controllers.stroreKeeper;


import BddPackage.*;
import Models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    private Label totalefact;

    @FXML
    private BorderPane mainPane;

    @FXML
    private Label factToday;

    @FXML
    private Label factWeek;

    @FXML
    private Label factMonth;

    @FXML
    private Label credetorToday;

    @FXML
    private Label credetorWeek;

    @FXML
    private Label CredetorMonth;

    @FXML
    private Label totFact;

    @FXML
    private Label totProvider;

    @FXML
    private Label totCreditor;

    @FXML
    private TableView<Product> productFinishedTable;

    @FXML
    private TableColumn<?, ?> name;

    @FXML
    private TableColumn<?, ?> unit;

    @FXML
    private TableView<Provider> providerTable;

    @FXML
    private TableColumn<?, ?> col_last_name;

    @FXML
    private TableColumn<?, ?> col_job;

    @FXML
    private TableColumn<?, ?> col_phone_number;

    @FXML
    private TableColumn<?, ?> col_creditor;

    @FXML
    private TableView<BillList> factTable;

    @FXML
    private TableColumn<?, ?> col_Bill_number;

    @FXML
    private TableColumn<?, ?> col_Date;

    @FXML
    private TableColumn<?, ?> col_Provider;

    @FXML
    private TableColumn<?, ?> col_Paid;

    @FXML
    private TableColumn<?, ?> col_Total;
    @FXML
    private PieChart piechart;
    @FXML
    private AreaChart<?, ?> areachart;
    @FXML
    private Label Incomee;


    StorBilleOperation storBilleOperation = new StorBilleOperation();
    ProviderOperation providerOperation = new ProviderOperation();
    ProductOperation productOperation = new ProductOperation();
    private ArrayList<Product> list_Products;
    private ArrayList<Provider> list_Provider;
    private ArrayList<BillList> list_Fact;
    private ObservableList<Product> dataTableProduct = FXCollections.observableArrayList();
    private ObservableList<Provider> dataTableProvider = FXCollections.observableArrayList();
    private ObservableList<BillList> dataTableFact = FXCollections.observableArrayList();
    private StoreBillProductOperation storeBillProductOperation = new StoreBillProductOperation();
    private ObservableList<PieChart.Data> datapiechart ;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        totalAmountCalculation();
        IncomeCalculation("day");
        masarif("day");
        AreaChartDay();
        credetCalcule();
        ProductFinished();
        chargepiechart();

    }

    public void Init(BorderPane mainPane){
        this.mainPane = mainPane;
        storBilleOperation.getAll();
        totFact.setText(String.valueOf(storBilleOperation.getCountStoreBill()));
        totProvider.setText(String.valueOf(providerOperation.getCountProvider()));



        //chargeProductFinishedTable();
        chargeCredtorProviderTable();

    }


    private ArrayList<BillList> chargeListBill() {
        ArrayList<BillList> list = new ArrayList<>();
        ArrayList<StoreBill> storBillLists = storBilleOperation.getAll();
        //ArrayList<Provider> providerList = providerOperation.getAll();
        ArrayList<StoreBillProduct> billListProduct = storeBillProductOperation.getAll();


        for (StoreBill storeBill : storBillLists) {
            BillList billList = new BillList();
            int total = 0;
            billList.setNumber(storeBill.getId());
            billList.setDate(storeBill.getDate().toString());
            billList.setPaid_up(storeBill.getPaid_up());
            billList.setProvider_name(storeBill.getProvider(storeBill.getId_provider()).getLast_name());
           // billList.setRest(Integer.parseInt(storeBill.getProvider(storeBill.getId_provider()).getCreditor()));
            //this for loop is for get total
            for (StoreBillProduct storeBillProduct : billListProduct) {
                if (storeBillProduct.getId_stor_bill() == storeBill.getId()) {
                    total += storeBillProduct.getPrice() * storeBillProduct.getProduct_quantity();
                }
            }
            billList.setTotal(total);
            billList.setRest(total-billList.getPaid_up());
            list.add(billList);
        }

        return list;
    }

    private void chargeCredtorProviderTable() {
        col_last_name.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        col_phone_number.setCellValueFactory(new PropertyValueFactory<>("phone_number"));
        col_job.setCellValueFactory(new PropertyValueFactory<>("job"));
        col_creditor.setCellValueFactory(new PropertyValueFactory<>("creditor"));
        list_Provider = providerOperation.getCredetorProvider();
        dataTableProvider.setAll(list_Provider);
        providerTable.setItems(dataTableProvider);
    }

    private void chargeProductFinishedTable() {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        unit.setCellValueFactory(new PropertyValueFactory<>("storage_Unit"));
        list_Products = productOperation.getProductFinished();
        dataTableProduct.setAll(list_Products);
        productFinishedTable.setItems(dataTableProduct);
    }
    private void chargepiechart() {
        ArrayList<FoodOrder> list = new ArrayList<>();
        FoodOrderOperation food=new FoodOrderOperation();
        int max1 =0,max2 =0,max3=0;
        int number=0,index1=0,index2=0,index3=0;
        list=food.getAll();
        if(list.size()!=0){

            for ( int j=1;j<=list.get(list.size()-1).getId_food();j++){
                number=food.getTopFood(j);
                if(number > max1){
                    max3=max2;
                    index3=index2;
                    max2=max1;
                    index2=index1;
                    max1=number;
                    index1=j;
                    System.out.println("max1="+max1);
                    System.out.println("max2="+max2);
                    System.out.println("max2="+max3);
                    System.out.println("===========");
                }else {
                    if(number > max2){
                        max3=max2;
                        index3=index2;
                        max2=number;
                        index2=j;
                        System.out.println("max1="+max2);
                        System.out.println("max2="+max3);
                        System.out.println("===========");
                    }else {
                        if(number > max3){
                            max3=number;
                            index3=j;
                            System.out.println("max2="+max2);
                            System.out.println("max3="+max3);
                            System.out.println("===========");
                        }

                    }
                }
            }
            Food food1 =new Food();
            Food food2 =new Food();
            Food food3 =new Food();
            FoodOperation foodOperation =new FoodOperation();
            food1=foodOperation.getFoodByID(index1);
            food2=foodOperation.getFoodByID(index2);
            food3=foodOperation.getFoodByID(index3);

                datapiechart = FXCollections.observableArrayList();
                datapiechart.add(new PieChart.Data(food1.getName(), max1));
                datapiechart.add(new PieChart.Data(food2.getName(), max2));
                datapiechart.add(new PieChart.Data(food3.getName(), max3));
                piechart.setData(datapiechart);

        }


    }

    void AreaChartDay(){
        areachart.getData().clear();
        areachart.getData().removeAll();

        OrdersOperation ordersOperation1=new OrdersOperation();
        MoneyWithdrawalOperationsBDD moneyWithdrawalOperationsBDD=new MoneyWithdrawalOperationsBDD();
        XYChart.Series p1=new XYChart.Series();
        p1.setName("المدخيل");
        int price=0;
        int j=0;

        DateFormat dateFormat = new SimpleDateFormat("dd");
        Date date = new Date();
        for ( j=1;j<32;j++){
            price=ordersOperation1.getelementDay(j);
            p1.getData().add(new XYChart.Data (""+j,price));

        }
        XYChart.Series p2=new XYChart.Series();
        p2.setName("المصاريف");
        StorBilleOperation storBilleOperation=new StorBilleOperation();
        for ( j=1;j<32;j++){
            price=storBilleOperation.getBillParDy(j);
            p2.getData().add(new XYChart.Data (""+j,price));

        }

        XYChart.Series p3=new XYChart.Series();
        p3.setName("المال المستخرج ");
        for ( j=1;j<32;j++){
            price=moneyWithdrawalOperationsBDD.getMonyDay(j);
            p3.getData().add(new XYChart.Data (""+j,price));

        }
        areachart.getData().addAll(p1,p2,p3);

    }
    void AreaChartMonth(){
        areachart.getData().clear();
        areachart.getData().removeAll();

        OrdersOperation ordersOperation1=new OrdersOperation();
        MoneyWithdrawalOperationsBDD moneyWithdrawalOperationsBDD=new MoneyWithdrawalOperationsBDD();
        XYChart.Series p1=new XYChart.Series();
        p1.setName("المدخيل");
        int price=0;
        int j=0;

        DateFormat dateFormat = new SimpleDateFormat("dd");
        Date date = new Date();
        for ( j=1;j<13;j++){
            price=ordersOperation1.getelementMonth(j);
            p1.getData().add(new XYChart.Data (""+j,price));

        }
        XYChart.Series p2=new XYChart.Series();
        p2.setName("المصاريف");
        StorBilleOperation storBilleOperation=new StorBilleOperation();
        for ( j=1;j<13;j++){
            price=storBilleOperation.getBillParMonth(j);
            p2.getData().add(new XYChart.Data (""+j,price));

        }

        XYChart.Series p3=new XYChart.Series();
        p3.setName("المال المستخرج ");
        for ( j=1;j<13;j++){
            price=moneyWithdrawalOperationsBDD.getMonyMonth(j);
            p3.getData().add(new XYChart.Data (""+j,price));

        }
        areachart.getData().addAll(p1,p2,p3);

    }
    void AreaChartYear(){
        areachart.getData().clear();
        areachart.getData().removeAll();

        OrdersOperation ordersOperation1=new OrdersOperation();
        MoneyWithdrawalOperationsBDD moneyWithdrawalOperationsBDD=new MoneyWithdrawalOperationsBDD();
        XYChart.Series p1=new XYChart.Series();
        p1.setName("المدخيل");
        int price=0;
        int j=0;

        DateFormat dateYear = new SimpleDateFormat("yyyy");
        Date date = new Date();
        int i=Integer.parseInt(dateYear.format(date))-5;
        for ( j=i;j<i+6;j++){
            price=ordersOperation1.getelementYear(j);
            p1.getData().add(new XYChart.Data (""+j,price));

        }
        XYChart.Series p2=new XYChart.Series();
        p2.setName("المصاريف");
        StorBilleOperation storBilleOperation=new StorBilleOperation();
        for ( j=i;j<i+6;j++){
            price=storBilleOperation.getBillParYear(j);
            p2.getData().add(new XYChart.Data (""+j,price));

        }

        XYChart.Series p3=new XYChart.Series();
        p3.setName("المال المستخرج ");
        for ( j=i;j<i+6;j++){
            price=moneyWithdrawalOperationsBDD.getMonyYear(j);
            p3.getData().add(new XYChart.Data (""+j,price));

        }
        areachart.getData().addAll(p1,p2,p3);

    }
    void totalAmountCalculation(){
        double Income=0.0;

        ArrayList<Orders> list = new ArrayList<>();
        OrdersOperation ordersOperation=new OrdersOperation();
        list=ordersOperation.getAllOrder();
        for (int i=0;i<list.size();i++){
            Income=Income+list.get(i).getPrice();
        }
        ArrayList<MoneyWithdrawal> listmony = new ArrayList<>();
        MoneyWithdrawalOperationsBDD mony =new MoneyWithdrawalOperationsBDD();
        listmony=mony.getAll();
        for (int j=0;j<listmony.size();j++){
            Income=Income-listmony.get(j).getMoneyWithdrawn();
        }

        int da= (int) Income;
        totalefact.setText(String.valueOf(da+",00"));

    }
    void IncomeCalculation(String dat){
        double Income=0.0;
        ArrayList<Orders> list = new ArrayList<>();
        OrdersOperation ordersOperation=new OrdersOperation();
        if(dat.equals("day"))
            list=ordersOperation.getelementDay();
        else{
            if(dat.equals("month"))
                list=ordersOperation.getelementMonth();
            else
                list=ordersOperation.getelementYear();
        }
        for (int i=0;i<list.size();i++){
            Income+=list.get(i).getPrice();
        }
        int da=(int)Income;
        Incomee.setText(String.valueOf(da+",00"));


    }
    void masarif(String dat){
        int price=0;
        StorBilleOperation storBilleOperation=new StorBilleOperation();
        DateFormat dateFormat = new SimpleDateFormat("dd");
        DateFormat dateMonth = new SimpleDateFormat("MM");
        DateFormat dateYear = new SimpleDateFormat("yyyy");
        Date date = new Date();
        if(dat.equals("day"))
            price+=storBilleOperation.getBillParDy(Integer.parseInt(dateFormat.format(date)));
        else {
            if(dat.equals("month"))
                price+=storBilleOperation.getBillParMonth(Integer.parseInt(dateMonth.format(date)));
            else
                price+=storBilleOperation.getBillParYear(Integer.parseInt(dateYear.format(date)));
        }
        CredetorMonth.setText(String.valueOf(price)+",00");

    }
    void credetCalcule(){
        ProviderOperation providerOperation=new ProviderOperation();
        ArrayList<Provider> list = new ArrayList<>();
        list=providerOperation.getAll();
        int prix=0;
        for (int i=0;i<list.size();i++){
            prix+=list.get(i).getCreditor();
        }
        credetorToday.setText(String.valueOf(prix)+",00");
    }
    void ProductFinished(){
        ArrayList<Product> list = new ArrayList<>();
        ProductOperation productOperation=new ProductOperation();
        list=productOperation.getProductFinished();
        totCreditor.setText(String.valueOf(list.size()));
    }
    @FXML
    void Day(ActionEvent event){
        IncomeCalculation("day");
        masarif("day");
        AreaChartDay();
    }
    @FXML
    void Month(ActionEvent event){
        IncomeCalculation("month");
        masarif("month");
        AreaChartMonth();
    }
    @FXML
    void Year(ActionEvent event){
        IncomeCalculation("year");
        masarif("yeaa");
        AreaChartYear();
    }

}
