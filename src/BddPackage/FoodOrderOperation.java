package BddPackage;

import Models.FoodOrder;
import Models.Orders;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FoodOrderOperation extends BDD<FoodOrder> {
    @Override
    public boolean insert(FoodOrder o) {
        boolean ins = false;
        String query = "INSERT INTO `food_order`(`ID_ORDER`, `ID_FOOD`,`ORDER_QUANTITY`) VALUES (?,?,?)";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,OrdersOperation.lastId);
            preparedStmt.setInt(2,o.getId_food());
            preparedStmt.setInt(3,o.getQuantity());
            int insert = preparedStmt.executeUpdate();
            if(insert != -1) ins = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ins;

    }

    @Override
    public boolean update(FoodOrder o1, FoodOrder o2) {
        boolean upd = false;
        String query = "UPDATE `food_order` SET `ID_FOOD`=? ,`ORDER_QUANTITY`= ? " +
                "WHERE ID_ORDER = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o1.getId_food());
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
    public boolean delete(FoodOrder o) {
        boolean del = false;
        String query = "DELETE FROM `food_order` WHERE `ID_ORDER` = ? ";
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
    public boolean isExist(FoodOrder o) {

        return false;    }

    @Override
    public ArrayList<FoodOrder> getAll() {
        return null;
    }
    public ArrayList<FoodOrder> getElement(Orders orders)
    {
        ArrayList<FoodOrder> list = new ArrayList<>();
        String query = "SELECT * FROM `food_order` WHERE `ID_ORDER`=?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,orders.getId());
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
                FoodOrder food = new FoodOrder();
                food.setId_food(resultSet.getInt("ID_FOOD"));
                food.setId_order(resultSet.getInt("ID_ORDER"));
                food.setQuantity(resultSet.getInt("ORDER_QUANTITY"));

                list.add(food);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
