package BddPackage;

import Models.Orders;
import Models.TableGainedMoney;
import Models.Tables;

import java.sql.Connection;
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

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ins;
    }

    @Override
    public boolean update(Orders o1, Orders o2) {
        boolean upd = false;
        String query = "UPDATE `orders` SET `ORDER_PRICE`=? " +
                "WHERE `ID_ORDER` = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,Integer.parseInt(String.valueOf(o1.getPrice())));
            preparedStmt.setInt(2,o2.getId());
            int update = preparedStmt.executeUpdate();
            if(update != -1) upd = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upd;
    }
    public boolean update(Orders o) {
        boolean upd = false;
        String query = "UPDATE `orders` SET `Order_Active`=?  " +
                "WHERE ID_ORDER = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,0);
            preparedStmt.setInt(2,o.getId());
            int update = preparedStmt.executeUpdate();
            if(update != -1) upd = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upd;
    }

    @Override
    public boolean delete(Orders o) {
        boolean del = false;
        String query = "DELETE FROM `orders` WHERE `ID_ORDER` = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o.getId());
            int delete = preparedStmt.executeUpdate();
            if(delete != -1) del = true;
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

        Connet connet=new Connet();
        Connection con =connet.connect();
        ArrayList<Orders> list = new ArrayList<>();
        String query = "SELECT * FROM `orders` WHERE `Order_Active`=1";
        try {
            PreparedStatement preparedStmt = con.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();

            while (resultSet.next()) {
                Orders orders = new Orders();
                orders.setId(resultSet.getInt("ID_ORDER"));
                orders.setId_table(resultSet.getInt("ID_TABLE_ORDER"));
                orders.setPrice(resultSet.getInt("ORDER_PRICE"));
                orders.setTime(resultSet.getTime("ORDER_TIME"));


                list.add(orders);

            }
            connet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    public ArrayList<Orders> getAllOrder() {

        Connet connet=new Connet();
        Connection con =connet.connect();
        ArrayList<Orders> list = new ArrayList<>();
        String query = "SELECT * FROM `orders`";
        try {
            PreparedStatement preparedStmt = con.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();

            while (resultSet.next()) {
                Orders orders = new Orders();
                orders.setId(resultSet.getInt("ID_ORDER"));
                orders.setId_table(resultSet.getInt("ID_TABLE_ORDER"));
                orders.setPrice(resultSet.getInt("ORDER_PRICE"));
                orders.setTime(resultSet.getTime("ORDER_TIME"));


                list.add(orders);

            }
            connet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    public TableGainedMoney getElement(Tables o){
        Connet connet=new Connet();
        Connection con =connet.connect();
        DateFormat dateFormat = new SimpleDateFormat("dd");
        Date date = new Date();
        System.out.println("la date ="+Integer.parseInt(dateFormat.format(date)));
        Models.TableGainedMoney tableGainedMoney=new TableGainedMoney();
        String query = "SELECT sum(`ORDER_PRICE`) FROM orders WHERE DAY(`ORDER_TIME`)=? and `ID_TABLE_ORDER`=?";
        try {
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, Integer.parseInt(dateFormat.format(date)));
            preparedStmt.setInt(2,o.getId());
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){

                tableGainedMoney.setGainedMoney(resultSet.getDouble(1));
                tableGainedMoney.setTableId(o.getId());

            }
            connet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableGainedMoney;
    }

    public ArrayList<Orders> getelementDay() {

        Connet connet=new Connet();
        Connection con =connet.connect();
        DateFormat dateFormat = new SimpleDateFormat("dd");
        DateFormat dateMonth = new SimpleDateFormat("MM");
        DateFormat dateYear = new SimpleDateFormat("yyyy");
        Date date = new Date();
        ArrayList<Orders> list = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE DAY(`ORDER_TIME`)=? and MONTH(`ORDER_TIME`)=? and YEAR(ORDER_TIME)=?";
        try {
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, Integer.parseInt(dateFormat.format(date)));
            preparedStmt.setInt(2, Integer.parseInt(dateMonth.format(date)));
            preparedStmt.setInt(3, Integer.parseInt(dateYear.format(date)));
            ResultSet resultSet = preparedStmt.executeQuery();

            while (resultSet.next()) {
                Orders orders = new Orders();
                orders.setId(resultSet.getInt("ID_ORDER"));
                orders.setId_table(resultSet.getInt("ID_TABLE_ORDER"));
                orders.setPrice(resultSet.getInt("ORDER_PRICE"));
                orders.setTime(resultSet.getTime("ORDER_TIME"));


                list.add(orders);

            }
            connet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    public ArrayList<Orders> getelementMonth() {

        Connet connet=new Connet();
        Connection con =connet.connect();
        DateFormat dateMonth = new SimpleDateFormat("MM");
        DateFormat dateYear = new SimpleDateFormat("yyyy");
        Date date = new Date();
        ArrayList<Orders> list = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE MONTH(`ORDER_TIME`)=? and YEAR(ORDER_TIME)=?";
        try {
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, Integer.parseInt(dateMonth.format(date)));
            preparedStmt.setInt(2, Integer.parseInt(dateYear.format(date)));
            ResultSet resultSet = preparedStmt.executeQuery();

            while (resultSet.next()) {
                Orders orders = new Orders();
                orders.setId(resultSet.getInt("ID_ORDER"));
                orders.setId_table(resultSet.getInt("ID_TABLE_ORDER"));
                orders.setPrice(resultSet.getInt("ORDER_PRICE"));
                orders.setTime(resultSet.getTime("ORDER_TIME"));


                list.add(orders);

            }
            connet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    public ArrayList<Orders> getelementYear() {

        Connet connet=new Connet();
        Connection con =connet.connect();
        DateFormat dateMonth = new SimpleDateFormat("MM");
        DateFormat dateYear = new SimpleDateFormat("yyyy");
        Date date = new Date();
        ArrayList<Orders> list = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE YEAR(ORDER_TIME)=?";
        try {
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, Integer.parseInt(dateYear.format(date)));
            ResultSet resultSet = preparedStmt.executeQuery();

            while (resultSet.next()) {
                Orders orders = new Orders();
                orders.setId(resultSet.getInt("ID_ORDER"));
                orders.setId_table(resultSet.getInt("ID_TABLE_ORDER"));
                orders.setPrice(resultSet.getInt("ORDER_PRICE"));
                orders.setTime(resultSet.getTime("ORDER_TIME"));


                list.add(orders);

            }
            connet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }


}
