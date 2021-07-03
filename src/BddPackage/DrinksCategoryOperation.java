package BddPackage;

import Models.DrinksCategory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DrinksCategoryOperation extends BDD<DrinksCategory> {

    @Override
    public boolean insert(DrinksCategory o) {
        boolean ins = false;
        String query = "INSERT INTO `drinks_category`(`DRINKS_CATEGORY_NAME`) VALUES (?)";
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
    public boolean update(DrinksCategory o1, DrinksCategory o2) {
        boolean upd = false;
        String query = "UPDATE `drinks_category` SET `DRINKS_CATEGORY_NAME`=? WHERE `ID_DRINKS_CATEGORY` = ?";
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
    public boolean delete(DrinksCategory o) {
        boolean del = false;
        String query = "DELETE FROM `drinks_category` WHERE ID_DRINKS_CATEGORY = ? ";
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
    public boolean isExist(DrinksCategory o) {
        return false;
    }

    @Override
    public ArrayList<DrinksCategory> getAll() {
        ArrayList<DrinksCategory> list = new ArrayList<>();
        String query = "SELECT * FROM `drinks_category`";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){
                DrinksCategory drinksCategory = new DrinksCategory();
                drinksCategory.setId(resultSet.getInt("ID_DRINKS_CATEGORY"));
                drinksCategory.setName(resultSet.getString("DRINKS_CATEGORY_NAME"));
                drinksCategory.setColor(resultSet.getString("COLOR"));
                list.add(drinksCategory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
