package BddPackage;

import Models.DrinksOrder;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class DrinkOrderOperation extends BDD<DrinksOrder> {
    @Override
    public boolean insert(DrinksOrder o) {
        boolean ins = false;
        String query = "INSERT INTO `drinks_order`(`ID_ORDER`, `ID_Drinks`,`ORDER_QUANTITY`) VALUES (?,?,?)";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,OrdersOperation.lastId);
            preparedStmt.setInt(2,o.getId_drink());
            preparedStmt.setInt(3,o.getQuantity());
            int insert = preparedStmt.executeUpdate();
            if(insert != -1) ins = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ins;
    }

    @Override
    public boolean update(DrinksOrder o1, DrinksOrder o2) {
        boolean upd = false;
        String query = "UPDATE `drinks_order` SET `ID_DRINKS`=? ,`ORDER_QUANTITY`= ? " +
                "WHERE ID_ORDER = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o1.getId_drink());
            preparedStmt.setInt(2,o1.getQuantity());
            preparedStmt.setInt(3,o2.getId_order());
            int update = preparedStmt.executeUpdate();
            if(update != -1) upd = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upd;
    }

    @Override
    public boolean delete(DrinksOrder o) {
        boolean del = false;
        String query = "DELETE FROM `drinks_order` WHERE `ID_ORDER` = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o.getId_order());
            int delete = preparedStmt.executeUpdate();
            if(delete != -1) del = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return del;
    }

    @Override
    public boolean isExist(DrinksOrder o) {
        return false;
    }

    @Override
    public ArrayList<DrinksOrder> getAll() {
        return null;
    }
}
