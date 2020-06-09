package BddPackage;

import Models.FoodCategory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FoodCategoryOperation extends BDD<FoodCategory> {
    @Override
    public boolean insert(FoodCategory o) {
        boolean ins = false;
        String query = "INSERT INTO `FOOD_CATEGORY`(`Food_Category_NAME`) VALUES (?)";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1,o.getName());
            int insert = preparedStmt.executeUpdate();
            if(insert != -1) ins = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ins;
    }

    @Override
    public boolean update(FoodCategory o1, FoodCategory o2) {
        boolean upd = false;
        String query = "UPDATE `FOOD_CATEGORY` SET `Food_Category_NAME`=? WHERE `ID_Food_Category` = ?";
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
        boolean del = false;
        String query = "DELETE FROM `FOOD_CATEGORY` WHERE `ID_Food_Category` = ? ";
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
        ArrayList<FoodCategory> list = new ArrayList<>();
        String query = "SELECT * FROM `FOOD_CATEGORY`";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){
                FoodCategory foodCategory = new FoodCategory();
                foodCategory.setId(resultSet.getInt("ID_Food_Category"));
                foodCategory.setName(resultSet.getString("Food_Category_NAME"));
                list.add(foodCategory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}
