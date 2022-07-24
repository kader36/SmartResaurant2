package Controllers.Tables;

import BddPackage.*;
import Models.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class DashbordControlleur implements Initializable {
    @FXML
    private ImageView gif;

    @FXML
    private Label totalAmount;

    @FXML
    private Label income;

    @FXML
    private Label profits;

    @FXML
    private Label numberachat;

    @FXML
    private AreaChart<?, ?> areachart;
    @FXML
    private CategoryAxis Xaxis;
    @FXML
    private NumberAxis Yaxis;
    @FXML
    private BarChart<?, ?> barchar;
    @FXML
    private CategoryAxis X;
    @FXML
    private NumberAxis Y;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AreaChartDay();
        barcharDay();
        numberachatCalculation("day");
        IncomeCalculation("day");
        expensesDay("day");
        totalAmountCalculation();
    }



    void ProfitCalculation(String dat){

        double priceproduct=0.0;
        double PriceFood=0;
        double Profit=0.0;
        double price=0;
        int i,j,k,m;
        ArrayList<Orders> list = new ArrayList<>();
        ArrayList<FoodOrder> listFood = new ArrayList<>();
        ArrayList<IngredientsFood> listProduit = new ArrayList<>();
        OrdersOperation ordersOperation=new OrdersOperation();
        if(dat.equals("day"))
                list=ordersOperation.getelementDay();
        else{
            if(dat.equals("month"))
                list=ordersOperation.getelementMonth();
            else
                list=ordersOperation.getelementYear();
        }
        FoodOrderOperation foodOrderOperation=new FoodOrderOperation();
        Food food=new Food();
        FoodOperation foodOperation=new FoodOperation();
        IngredientsFoodOperation ingredientsFoodOperation=new IngredientsFoodOperation();
        Product product =new Product();
        ProductOperation productOperation=new ProductOperation();
        StoreBillProductOperation storeBillProductOperation=new StoreBillProductOperation();
        for ( i=0;i<list.size();i++){
            listFood=foodOrderOperation.getElement(list.get(i));
            for ( j=0;j<listFood.size();j++){
                food=foodOperation.getFoodByID(listFood.get(j).getId_food());
                PriceFood+=food.getPrice()*listFood.get(j).getQuantity();
                listProduit=ingredientsFoodOperation.getIngredientsFood(food.getId());
                for ( k=0;k<listProduit.size();k++){
                    product=productOperation.GetProduct(listProduit.get(k).getId_product());
                     price=ordersOperation.priceproduct(product.getId(),food.getId());
                    priceproduct+= price;

                }

                for ( m=0;m<listFood.get(j).getQuantity();m++){
                    PriceFood=PriceFood-priceproduct;
                }
                priceproduct=0.0;
            }
            Profit+=PriceFood;
            PriceFood=0;
        }
        int reselt= (int) Profit;
        profits.setText(String.valueOf(reselt));

    }
    void IncomeCalculation(String dat){
        Double Income=0.0;
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
        income.setText(String.valueOf(Income));

    }
    void numberachatCalculation(String dat){
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
        numberachat.setText(String.valueOf(list.size()));


    }
    void totalAmountCalculation(){
        Double Income=0.0;

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


        totalAmount.setText(String.valueOf(Income));

    }
    @FXML
    void Day(ActionEvent event){
        numberachatCalculation("day");
        IncomeCalculation("day");
        //ProfitCalculation("day");
        expensesDay("day");

        AreaChartDay();
        barcharDay();

    }
    @FXML
    void Month(ActionEvent event){

        numberachatCalculation("month");
        IncomeCalculation("month");
        //ProfitCalculation("month");
        expensesDay("month");

        AreaChartMonth();
        barcharMonth();

    }
    @FXML
    void Year(ActionEvent event){

        numberachatCalculation("year");
        IncomeCalculation("year");
        //ProfitCalculation("year");
        expensesDay("year");
        AreaChartYear();
        barcharYear();

    }

     void AreaChartDay(){
         Xaxis.setLabel("الايام ");
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
         p2.setName("المستخراجات");
         for ( j=1;j<32;j++){
             price=moneyWithdrawalOperationsBDD.getMonyDay(j);
             p2.getData().add(new XYChart.Data (""+j,price));

         }
         areachart.getData().addAll(p1,p2);

    }
    void AreaChartMonth(){
        Xaxis.setLabel("الاشهر ");
        areachart.getData().clear();
        areachart.getData().removeAll();
        MoneyWithdrawalOperationsBDD moneyWithdrawalOperationsBDD=new MoneyWithdrawalOperationsBDD();
        OrdersOperation ordersOperation1=new OrdersOperation();
        XYChart.Series p1=new XYChart.Series();
        p1.setName("المدخيل");
        int price=0;
        int j=0;
        for ( j=1;j<13;j++){
            price=ordersOperation1.getelementMonth(j);
            p1.getData().add(new XYChart.Data (""+j,price));

        }
        XYChart.Series p2=new XYChart.Series();
        p2.setName("المستخراجات");
        for ( j=1;j<13;j++){
            price=moneyWithdrawalOperationsBDD.getMonyMonth(j);
            p2.getData().add(new XYChart.Data (""+j,price));

        }
        areachart.getData().addAll(p1,p2);



    }
    void AreaChartYear(){
        Xaxis.setLabel("السنوات");
        areachart.getData().clear();
        areachart.getData().removeAll();
        MoneyWithdrawalOperationsBDD moneyWithdrawalOperationsBDD=new MoneyWithdrawalOperationsBDD();
        OrdersOperation ordersOperation1=new OrdersOperation();
        XYChart.Series p1=new XYChart.Series();
        p1.setName("المدخيل");
        int price=0;
        int j=0;

        DateFormat dateYear = new SimpleDateFormat("yyyy");
        Date date = new Date();
        int i=Integer.parseInt(dateYear.format(date))-5;
        for ( j=i;j<=Integer.parseInt(dateYear.format(date));j++){
            price=ordersOperation1.getelementYear(j);
            p1.getData().add(new XYChart.Data (""+j,price));

        }
        XYChart.Series p2=new XYChart.Series();
        p2.setName("المستخراجات");
        for ( j=i;j<=Integer.parseInt(dateYear.format(date));j++){
            price=moneyWithdrawalOperationsBDD.getMonyYear(j);
            System.out.println("mony="+price);
            p2.getData().add(new XYChart.Data (""+j,price));

        }

        areachart.getData().addAll(p1,p2);


    }
    void barcharDay(){
        barchar.getData().clear();
        barchar.getData().removeAll();
        XYChart.Series p2=new XYChart.Series();
        p2.setName("المبيعات");
        ArrayList<Orders> list = new ArrayList<>();
        OrdersOperation ordersOperation=new OrdersOperation();
        for ( int j=1;j<32;j++){
            list=ordersOperation.getOrderDay(j);
            p2.getData().add(new XYChart.Data (""+j,list.size()));

        }
        barchar.getData().addAll(p2);

    }
    void barcharMonth(){
        barchar.getData().clear();
        barchar.getData().removeAll();
        XYChart.Series p2=new XYChart.Series();
        p2.setName("المبيعات");
        ArrayList<Orders> list = new ArrayList<>();
        OrdersOperation ordersOperation=new OrdersOperation();
        for ( int j=1;j<13;j++){
            list=ordersOperation.getOrderMonth(j);
            p2.getData().add(new XYChart.Data (""+j,list.size()));

        }
        barchar.getData().addAll(p2);

    }
    void barcharYear(){
        barchar.getData().clear();
        barchar.getData().removeAll();
        XYChart.Series p2=new XYChart.Series();
        p2.setName("المبيعات");
        ArrayList<Orders> list = new ArrayList<>();
        OrdersOperation ordersOperation=new OrdersOperation();
        DateFormat dateYear = new SimpleDateFormat("yyyy");
        Date date = new Date();
        int i=Integer.parseInt(dateYear.format(date))-5;
        for ( int j=i;j<=Integer.parseInt(dateYear.format(date));j++){
            list=ordersOperation.getOrderYear(j);
            p2.getData().add(new XYChart.Data (""+j,list.size()));

        }
        barchar.getData().addAll(p2);

    }
    void expensesDay(String dat){
        int prix=0;
        MoneyWithdrawalOperationsBDD mony=new MoneyWithdrawalOperationsBDD();
        DateFormat dateDay = new SimpleDateFormat("dd");
        DateFormat dateMonth = new SimpleDateFormat("MM");
        DateFormat dateYear = new SimpleDateFormat("yyyy");
        Date date = new Date();
        if(dat.equals("day"))
            prix=mony.getMonyDay(Integer.parseInt(dateDay.format(date)));
        else{
            if(dat.equals("month"))
                prix=mony.getMonyMonth(Integer.parseInt(dateMonth.format(date)));
            else
                prix=mony.getMonyYear(Integer.parseInt(dateYear.format(date)));
        }

        profits.setText(String.valueOf(prix));

    }


}
