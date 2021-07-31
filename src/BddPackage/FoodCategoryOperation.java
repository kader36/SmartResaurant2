package BddPackage;

import Models.FoodCategory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FoodCategoryOperation extends BDD<FoodCategory> {
    @Override
    public boolean insert(FoodCategory o) {
        conn=connect();
        boolean ins = false;
        String query = "INSERT INTO `food_category`(`Food_Category_NAME`,COLOR) VALUES (?,?)";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1,o.getName());
            preparedStmt.setString(2,o.getColor());
            int insert = preparedStmt.executeUpdate();
            if(insert != -1) ins = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ins;
    }

    @Override
    public boolean update(FoodCategory o1, FoodCategory o2) {
        conn=connect();
        boolean upd = false;
        String query = "UPDATE `food_category` SET `Food_Category_NAME`=? WHERE `ID_Food_Category` = ?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1,o1.getName());
            preparedStmt.setInt(2, o2.getId());
            int update = preparedStmt.executeUpdate();
            if(update != -1) upd = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return upd;
    }

    @Override
    public boolean delete(FoodCategory o) {
        conn=connect();
        boolean del = false;
        String query = "DELETE FROM `food_category` WHERE `ID_Food_Category` = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o.getId());
            int delete = preparedStmt.executeUpdate();
            if(delete != -1) del = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return del;
    }

    @Override
    public boolean isExist(FoodCategory o) {
        return false;
    }

    @Override
    public ArrayList<FoodCategory> getAll() {
        conn=connect();
        ArrayList<FoodCategory> list = new ArrayList<>();
        String query = "SELECT * FROM `food_category`";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){
                FoodCategory foodCategory = new FoodCategory();
                foodCategory.setId(resultSet.getInt("ID_Food_Category"));
                foodCategory.setName(resultSet.getString("Food_Category_NAME"));
                foodCategory.setColor(resultSet.getString("COLOR"));
                list.add(foodCategory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public FoodCategory getCategory(int o) {
        conn=connect();
        String query = "SELECT * FROM `food_category` WHERE `ID_Food_Category`=?";
        FoodCategory foodCategory = new FoodCategory();
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
                foodCategory.setId(resultSet.getInt("ID_Food_Category"));
                foodCategory.setName(resultSet.getString("Food_Category_NAME"));
                foodCategory.setColor(resultSet.getString("COLOR"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foodCategory;
    }




}
