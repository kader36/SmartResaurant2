package BddPackage;

import Models.FoodOrder;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class FoodOrderPrintOperation extends BDD<FoodOrder>{
    @Override
    public boolean insert(FoodOrder o) {
        conn=connect();
        boolean ins = false;
        String query = "INSERT INTO `food_order_print`(`ID_ORDER`, `ID_FOOD`,`ORDER_QUANTITY`) VALUES (?,?,?)";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o.getId_order());
            preparedStmt.setInt(2,o.getId_food());
            preparedStmt.setInt(3,o.getQuantity());
            int insert = preparedStmt.executeUpdate();
            if(insert != -1) ins = true;
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ins;
    }

    @Override
    public boolean update(FoodOrder o1, FoodOrder o2) {
        return false;
    }

    @Override
    public boolean delete(FoodOrder o) {
        return false;
    }

    @Override
    public boolean isExist(FoodOrder o) {
        return false;
    }

    @Override
    public ArrayList<FoodOrder> getAll() {
        return null;
    }
}
