package BddPackage;

import Models.OrderPrint;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderPrintOpertion extends BDD <OrderPrint>{
    public static int orderprint;
    @Override
    public boolean insert(OrderPrint o) {
        conn=connect();
        boolean ins = false;
        String query = "INSERT INTO `ordersprint` (`ID_ORDER`, `ID_TABLE_ORDER`,  `ORDER_PRICE`, `Order_Active`) VALUES (?, ?, ?, ?);";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o.getId());
            preparedStmt.setInt(2,o.getId_table());
            preparedStmt.setInt(3,(int)o.getPrice());
            preparedStmt.setInt(4,1);
            int insert = preparedStmt.executeUpdate();
            if(insert != -1) ins = true;

            // set teh last inserted column id;
            String lastIDQuery = "SELECT  ID_ORDER FROM `ordersprint` ORDER BY ORDER_TIME DESC LIMIT 1";
            PreparedStatement lastIDPreparedStmt = conn.prepareStatement(lastIDQuery);
            ResultSet resultSet = lastIDPreparedStmt.executeQuery();
            while (resultSet.next()) {
                orderprint =  resultSet.getInt("ID_ORDER");
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ins;
    }

    @Override
    public boolean update(OrderPrint o1, OrderPrint o2) {
        return false;
    }

    @Override
    public boolean delete(OrderPrint o) {
        return false;
    }

    @Override
    public boolean isExist(OrderPrint o) {
        return false;
    }

    @Override
    public ArrayList<OrderPrint> getAll() {
        return null;
    }
}
