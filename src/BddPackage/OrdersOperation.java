package BddPackage;

import Models.Orders;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrdersOperation extends BDD <Orders> {
    @Override
    public boolean insert(Orders o) {
        boolean ins = false;
        String query = "INSERT INTO `ORDERS`( `ID_TABLE_ORDER`,`ORDER_PRICE`) VALUES (?,?)";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o.getId_table());
            preparedStmt.setInt(2,o.getPrice());
            int insert = preparedStmt.executeUpdate();
            if(insert != -1) ins = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ins;
    }

    @Override
    public boolean update(Orders o1, Orders o2) {
        boolean upd = false;
        String query = "UPDATE `ORDERS` SET `ORDER_PRICE`=? " +
                "WHERE `ID_ORDER` = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o1.getPrice());
            preparedStmt.setInt(2,o2.getId());
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
        String query = "DELETE FROM `ORDERS` WHERE `ID_ORDER` = ? ";
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
        return null;
    }
}
