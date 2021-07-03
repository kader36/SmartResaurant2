package BddPackage;

import Models.Drinks;
import javafx.scene.image.Image;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DrinksOperation extends BDD<Drinks> {

    public boolean changeAvailability(Drinks o){
        boolean ins = false;
        String query = "UPDATE `drinks` SET `AVAILABLE` = ? WHERE `ID_DRINKS` = ?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setBoolean(1, o.isAvailable());
            preparedStmt.setInt(2, o.getId());
            int insert = preparedStmt.executeUpdate();
            if (insert != -1) ins = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ins;
    }


    @Override
    public boolean insert(Drinks o) {
        boolean ins = false;
        String query = "INSERT INTO `drinks`(`ID_DRINKS_CATEGORY`, `DRINKS_NAME`, `DRINKS_DESCRIPTION`, `DRINKS_PRICE`, `DRINKS_IMAGE`) \n" +
                "VALUES (?,?,?,?,?)";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(   1,o.getId_category());
            preparedStmt.setString(2,o.getName());
            preparedStmt.setString(3,o.getDescription());
            preparedStmt.setInt(   4,o.getPrice());
            preparedStmt.setString(5,o.getImage_path());
            int insert = preparedStmt.executeUpdate();
            if(insert != -1) ins = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ins;
    }

    @Override
    public boolean update(Drinks o1, Drinks o2) {
        boolean upd = false;
        String query = "UPDATE `drinks` SET `ID_DRINKS_CATEGORY`=?,`DRINKS_NAME`=?,\n" +
                "    `DRINKS_DESCRIPTION`=?,`DRINKS_PRICE`=?,`DRINKS_IMAGE`=? WHERE ID_DRINKS = ?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(   1,o2.getId_category());
            preparedStmt.setString(2,o2.getName());
            preparedStmt.setString(3,o2.getDescription());
            preparedStmt.setInt(   4,o2.getPrice());
            preparedStmt.setString(5,o2.getImage_path());
            preparedStmt.setInt(   6,o1.getId());
            int update = preparedStmt.executeUpdate();
            if(update != -1) upd = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upd;
    }

    @Override
    public boolean delete(Drinks o) {
        boolean del = false;
        String query = "DELETE FROM `drinks` WHERE `ID_DRINKS` = ?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o.getId());
            int delete = preparedStmt.executeUpdate();
            if (delete != -1) del = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return del;
    }

    @Override
    public boolean isExist(Drinks o) {
        return false;
    }

    @Override
    public ArrayList<Drinks> getAll() {
        ArrayList<Drinks> list = new ArrayList<>();
        String query = "SELECT * FROM `drinks`";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){
                Drinks drinks = new Drinks();
                drinks.setId(resultSet.getInt(           1));
                drinks.setId_category(resultSet.getInt(  2));
                drinks.setName(resultSet.getString(      3));
                drinks.setDescription(resultSet.getString(4));
                drinks.setPrice(resultSet.getInt(        5));
                drinks.setImage_path(resultSet.getString(6));
                drinks.setImageByPath(new Image("file:"+drinks.getImage_path()));
                drinks.setRating(resultSet.getInt(7));
                drinks.setAvailable(resultSet.getBoolean(8));
                list.add(drinks);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Drinks getDrinkByID(int idFood) {
        Drinks drink = new Drinks();
        String query = "select  * from `drinks` WHERE `ID_DRINK` = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, idFood);

            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
                drink.setId(resultSet.getInt("ID_FOOD"));
                drink.setId_category(resultSet.getInt("ID_Food_Category"));
                drink.setName(resultSet.getString("FOOD_NAME"));
                drink.setDescription(resultSet.getString("DESCRIPTION"));
                drink.setPrice(resultSet.getInt("FOOD_PRICE"));
                drink.setImage_path(resultSet.getString("FOOD_IMAGE"));
                drink.setRating(resultSet.getInt("RATING"));
                drink.setAvailable(resultSet.getBoolean("AVAILABLE"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return drink;
    }

    public int getCountDrink() {
        int total = 0;
        String query = "SELECT COUNT(*) AS total FROM drinks";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next())
                total = resultSet.getInt("total");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }
}
