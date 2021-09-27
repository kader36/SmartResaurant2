package BddPackage;

import Models.Orders;
import Models.TableGainedMoney;
import Models.Tables;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OrdersOperation extends BDD <Orders> {
    long millis=System.currentTimeMillis();
    static int lastId;
    @Override
    public boolean insert(Orders o) {
        conn=connect();
        boolean ins = false;
        String query = "INSERT INTO `orders`( `ID_TABLE_ORDER`,`ORDER_PRICE`,`Order_Active`) VALUES (?,?,?)";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o.getId_table());
            preparedStmt.setInt(2,(int)o.getPrice());
            preparedStmt.setInt(3,1);
            int insert = preparedStmt.executeUpdate();
            if(insert != -1) ins = true;

            // set teh last inserted column id;
            String lastIDQuery = "SELECT  ID_ORDER FROM `orders` ORDER BY ORDER_TIME DESC LIMIT 1";
            PreparedStatement lastIDPreparedStmt = conn.prepareStatement(lastIDQuery);
            ResultSet resultSet = lastIDPreparedStmt.executeQuery();
            while (resultSet.next()) {
                lastId =  resultSet.getInt("ID_ORDER");
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ins;
    }

    @Override
    public boolean update(Orders o1, Orders o2) {
        conn=connect();
        boolean upd = false;
        String query = "UPDATE `orders` SET `ORDER_PRICE`=? " +
                "WHERE `ID_ORDER` = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,Integer.parseInt(String.valueOf(o1.getPrice())));
            preparedStmt.setInt(2,o2.getId());
            int update = preparedStmt.executeUpdate();
            if(update != -1) upd = true;
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upd;
    }
    public boolean update(Orders o) {
        conn=connect();
        boolean upd = false;
        String query = "UPDATE `orders` SET `Order_Active`=?  " +
                "WHERE ID_ORDER = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,0);
            preparedStmt.setInt(2,o.getId());
            int update = preparedStmt.executeUpdate();
            if(update != -1) upd = true;
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upd;
    }

    @Override
    public boolean delete(Orders o) {
        conn=connect();
        boolean del = false;
        String query = "DELETE FROM `orders` WHERE `ID_ORDER` = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o.getId());
            int delete = preparedStmt.executeUpdate();
            if(delete != -1) del = true;
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return del;
    }

    @Override
    public boolean isExist(Orders o) {
        return false;
    }

    @Override
    public ArrayList<Orders> getAll() {

        conn=connect();
        ArrayList<Orders> list = new ArrayList<>();
        String query = "SELECT * FROM `orders` WHERE `Order_Active`=1";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();

            while (resultSet.next()) {
                Orders orders = new Orders();
                orders.setId(resultSet.getInt("ID_ORDER"));
                orders.setId_table(resultSet.getInt("ID_TABLE_ORDER"));
                orders.setPrice(resultSet.getInt("ORDER_PRICE"));
                orders.setTime(resultSet.getTime("ORDER_TIME"));


                list.add(orders);

            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    public ArrayList<Orders> getAllOrder() {

        conn=connect();
        ArrayList<Orders> list = new ArrayList<>();
        String query = "SELECT * FROM `orders`";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();

            while (resultSet.next()) {
                Orders orders = new Orders();
                orders.setId(resultSet.getInt("ID_ORDER"));
                orders.setId_table(resultSet.getInt("ID_TABLE_ORDER"));
                orders.setPrice(resultSet.getInt("ORDER_PRICE"));
                orders.setTime(resultSet.getDate("ORDER_TIME"));


                list.add(orders);

            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    public TableGainedMoney getElement(Tables o){
        conn=connect();
        DateFormat dateFormat = new SimpleDateFormat("dd");
        Date date = new Date();
        System.out.println("la date ="+Integer.parseInt(dateFormat.format(date)));
        Models.TableGainedMoney tableGainedMoney=new TableGainedMoney();
        String query = "SELECT sum(`ORDER_PRICE`) FROM orders WHERE DAY(`ORDER_TIME`)=? and `ID_TABLE_ORDER`=?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, Integer.parseInt(dateFormat.format(date)));
            preparedStmt.setInt(2,o.getId());
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){

                tableGainedMoney.setGainedMoney(resultSet.getDouble(1));
                tableGainedMoney.setTableId(o.getId());

            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableGainedMoney;
    }

    public ArrayList<Orders> getelementDay() {

        conn=connect();
        DateFormat dateFormat = new SimpleDateFormat("dd");
        DateFormat dateMonth = new SimpleDateFormat("MM");
        DateFormat dateYear = new SimpleDateFormat("yyyy");
        Date date = new Date();
        ArrayList<Orders> list = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE DAY(`ORDER_TIME`)=? and MONTH(`ORDER_TIME`)=? and YEAR(ORDER_TIME)=?";

        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, Integer.parseInt(dateFormat.format(date)));
            preparedStmt.setInt(2, Integer.parseInt(dateMonth.format(date)));
            preparedStmt.setInt(3, Integer.parseInt(dateYear.format(date)));
            ResultSet resultSet = preparedStmt.executeQuery();

            while (resultSet.next()) {
                Orders orders = new Orders();
                orders.setId(resultSet.getInt("ID_ORDER"));
                orders.setId_table(resultSet.getInt("ID_TABLE_ORDER"));
                orders.setPrice(resultSet.getInt("ORDER_PRICE"));
                orders.setTime(resultSet.getDate("ORDER_TIME"));


                list.add(orders);

            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    public ArrayList<Orders> getelementMonth() {


        DateFormat dateMonth = new SimpleDateFormat("MM");
        DateFormat dateYear = new SimpleDateFormat("yyyy");
        Date date = new Date();
        ArrayList<Orders> list = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE MONTH(`ORDER_TIME`)=? and YEAR(ORDER_TIME)=?";
        try {
            conn=connect();
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, Integer.parseInt(dateMonth.format(date)));
            preparedStmt.setInt(2, Integer.parseInt(dateYear.format(date)));
            ResultSet resultSet = preparedStmt.executeQuery();

            while (resultSet.next()) {
                Orders orders = new Orders();
                orders.setId(resultSet.getInt("ID_ORDER"));
                orders.setId_table(resultSet.getInt("ID_TABLE_ORDER"));
                orders.setPrice(resultSet.getInt("ORDER_PRICE"));
                orders.setTime(resultSet.getDate("ORDER_TIME"));


                list.add(orders);

            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    public ArrayList<Orders> getelementYear() {

        conn=connect();
        DateFormat dateMonth = new SimpleDateFormat("MM");
        DateFormat dateYear = new SimpleDateFormat("yyyy");
        Date date = new Date();
        ArrayList<Orders> list = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE YEAR(ORDER_TIME)=?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, Integer.parseInt(dateYear.format(date)));
            ResultSet resultSet = preparedStmt.executeQuery();

            while (resultSet.next()) {
                Orders orders = new Orders();
                orders.setId(resultSet.getInt("ID_ORDER"));
                orders.setId_table(resultSet.getInt("ID_TABLE_ORDER"));
                orders.setPrice(resultSet.getInt("ORDER_PRICE"));
                orders.setTime(resultSet.getDate("ORDER_TIME"));


                list.add(orders);

            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    public int getelementDay(int day) {
        int price=0;
        conn=connect();
        DateFormat dateMonth = new SimpleDateFormat("MM");
        DateFormat dateYear = new SimpleDateFormat("yyyy");
        Date date = new Date();
        String query = "SELECT sum(`ORDER_PRICE`) as 'ORDER_PRICE' FROM orders WHERE DAY(`ORDER_TIME`)=? and MONTH(`ORDER_TIME`)=? and YEAR(ORDER_TIME)=?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, day);
            preparedStmt.setInt(2, Integer.parseInt(dateMonth.format(date)));
            preparedStmt.setInt(3, Integer.parseInt(dateYear.format(date)));
            ResultSet resultSet = preparedStmt.executeQuery();

            while (resultSet.next()) {

                price=resultSet.getInt("ORDER_PRICE");

            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return price;
    }
    public int getelementMonth(int  month) {
        int price=0;
        conn=connect();
        DateFormat dateYear = new SimpleDateFormat("yyyy");
        Date date = new Date();
        String query = "SELECT sum(`ORDER_PRICE`) as 'ORDER_PRICE' FROM orders WHERE  MONTH(`ORDER_TIME`)=? and YEAR(ORDER_TIME)=?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, month);
            preparedStmt.setInt(2, Integer.parseInt(dateYear.format(date)));
            ResultSet resultSet = preparedStmt.executeQuery();

            while (resultSet.next()) {
                price=resultSet.getInt("ORDER_PRICE");

            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return price;
    }
    public int getelementYear(int year ) {
        int price=0;
        conn=connect();
        String query = "SELECT sum(`ORDER_PRICE`) as 'ORDER_PRICE' FROM orders WHERE  YEAR(ORDER_TIME)=?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, year);
            ResultSet resultSet = preparedStmt.executeQuery();

            while (resultSet.next()) {
                price=resultSet.getInt("ORDER_PRICE");
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return price;
    }

    public ArrayList<Orders> getOrderDay(int day) {

        conn=connect();
        DateFormat dateFormat = new SimpleDateFormat("dd");
        DateFormat dateMonth = new SimpleDateFormat("MM");
        DateFormat dateYear = new SimpleDateFormat("yyyy");
        Date date = new Date();
        ArrayList<Orders> list = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE DAY(`ORDER_TIME`)=? and MONTH(`ORDER_TIME`)=? and YEAR(ORDER_TIME)=?";

        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, day);
            preparedStmt.setInt(2, Integer.parseInt(dateMonth.format(date)));
            preparedStmt.setInt(3, Integer.parseInt(dateYear.format(date)));
            ResultSet resultSet = preparedStmt.executeQuery();

            while (resultSet.next()) {
                Orders orders = new Orders();
                orders.setId(resultSet.getInt("ID_ORDER"));
                orders.setId_table(resultSet.getInt("ID_TABLE_ORDER"));
                orders.setPrice(resultSet.getInt("ORDER_PRICE"));
                orders.setTime(resultSet.getDate("ORDER_TIME"));


                list.add(orders);

            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    public ArrayList<Orders> getOrderMonth(int month) {

        conn=connect();
        DateFormat dateYear = new SimpleDateFormat("yyyy");
        Date date = new Date();
        ArrayList<Orders> list = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE  MONTH(`ORDER_TIME`)=? and YEAR(ORDER_TIME)=?";

        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);

            preparedStmt.setInt(1, month);
            preparedStmt.setInt(2, Integer.parseInt(dateYear.format(date)));
            ResultSet resultSet = preparedStmt.executeQuery();

            while (resultSet.next()) {
                Orders orders = new Orders();
                orders.setId(resultSet.getInt("ID_ORDER"));
                orders.setId_table(resultSet.getInt("ID_TABLE_ORDER"));
                orders.setPrice(resultSet.getInt("ORDER_PRICE"));
                orders.setTime(resultSet.getDate("ORDER_TIME"));


                list.add(orders);

            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    public ArrayList<Orders> getOrderYear(int year) {

        conn=connect();
        DateFormat dateYear = new SimpleDateFormat("yyyy");
        Date date = new Date();
        ArrayList<Orders> list = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE  YEAR(ORDER_TIME)=?";

        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);


            preparedStmt.setInt(1, year);
            ResultSet resultSet = preparedStmt.executeQuery();

            while (resultSet.next()) {
                Orders orders = new Orders();
                orders.setId(resultSet.getInt("ID_ORDER"));
                orders.setId_table(resultSet.getInt("ID_TABLE_ORDER"));
                orders.setPrice(resultSet.getInt("ORDER_PRICE"));
                orders.setTime(resultSet.getDate("ORDER_TIME"));


                list.add(orders);

            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public int priceproduct(int product,int food) {
        int price=0;
        conn=connect();

        String query = "SELECT AVG(store_bill_product.PRICE)*ingredients_food.INGREDIENT_QUANTITY/product.coefficient as 'price' FROM product,ingredients_food,store_bill_product WHERE product.ID_PRODUCT=ingredients_food.ID_PRODUCT_INGREDIENT and ingredients_food.ID_PRODUCT_INGREDIENT=store_bill_product.ID_PRODUCT and product.ID_PRODUCT=store_bill_product.ID_PRODUCT and product.ID_PRODUCT=? and ingredients_food.ID_FOOD_INGREDIENT=?";

        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, product);
            preparedStmt.setInt(2, food);
            ResultSet resultSet = preparedStmt.executeQuery();

            while (resultSet.next()) {
                price=resultSet.getInt("price");
                System.out.println("price="+price);

            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return price;
    }

    public boolean insertnew(Orders o) {
        conn=connect();
        boolean ins = false;
        String query = "INSERT INTO `orders`( `ID_TABLE_ORDER`,`ORDER_PRICE`,`Order_Active`) VALUES (?,?,?)";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o.getId_table());
            preparedStmt.setInt(2,(int)o.getPrice());
            preparedStmt.setInt(3,0);
            int insert = preparedStmt.executeUpdate();
            if(insert != -1) ins = true;

            // set teh last inserted column id;
            String lastIDQuery = "SELECT  ID_ORDER FROM `orders` ORDER BY ORDER_TIME DESC LIMIT 1";
            PreparedStatement lastIDPreparedStmt = conn.prepareStatement(lastIDQuery);
            ResultSet resultSet = lastIDPreparedStmt.executeQuery();
            while (resultSet.next()) {
                lastId =  resultSet.getInt("ID_ORDER");
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ins;
    }
}
