package BddPackage;

import Models.Food;
import javafx.scene.image.Image;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FoodOperation extends BDD<Food> {

    public boolean changeAvailability(Food o){
        conn=connect();
        boolean ins = false;
        String query = "UPDATE `food` SET `AVAILABLE` = ? WHERE `ID_FOOD` = ?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setBoolean(1, o.isAvailabale());
            preparedStmt.setInt(2, o.getId());
            int insert = preparedStmt.executeUpdate();
            if (insert != -1) ins = true;
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ins;
    }

    @Override
    public boolean insert(Food o) {
        conn=connect();
        boolean ins = false;
        String query = "INSERT INTO `food`(`ID_Food_Category`, `FOOD_NAME`, `DESCRIPTION`, `FOOD_PRICE`, `FOOD_IMAGE`, `ID_FOOD`,`IMAGE`,FOOD_PRICE_FEE) \n" +
                "VALUES (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, o.getId_category());
            preparedStmt.setString(2, o.getName());
            preparedStmt.setString(3, o.getDescription());
            preparedStmt.setInt(4, o.getPrice());
            preparedStmt.setString(5, o.getImage_path());
            preparedStmt.setInt(6, o.getId());
            preparedStmt.setBytes(7, o.getIMAGE());
            preparedStmt.setInt(8, o.getPricefee());
            int insert = preparedStmt.executeUpdate();
            if (insert != -1) ins = true;
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ins;

    }

    @Override
    public boolean update(Food o1, Food o2) {
        conn=connect();
        boolean upd = false;
        String query = "UPDATE `food` SET`FOOD_NAME`= ?,`DESCRIPTION`= ?,`FOOD_PRICE`= ?,`FOOD_IMAGE`= ?,`IMAGE`=?,`FOOD_PRICE_FEE`= ? WHERE `ID_FOOD`= ?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, o2.getName());
            preparedStmt.setString(2, o2.getDescription());
            preparedStmt.setInt(3, o2.getPrice());
            preparedStmt.setString(4, o2.getImage_path());
            preparedStmt.setBytes(5, o2.getIMAGE());
            preparedStmt.setInt(6, o2.getPricefee());
            preparedStmt.setInt(7, o1.getId());
            int update = preparedStmt.executeUpdate();
            if (update != -1) upd = true;
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upd;

    }
    public ArrayList<Food> getAllBy(String orderBY) {
        conn=connect();
        ArrayList<Food> list = new ArrayList<>();
        String query = "SELECT * FROM `food` ORDER BY " + orderBY + " ASC ";
        try {

            chargeData(list, query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private ArrayList<Food> chargeData(ArrayList<Food> list, String query) throws SQLException{
        conn=connect();
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        ResultSet resultSet = preparedStmt.executeQuery();
        while (resultSet.next()) {
            Food food = new Food();
            food.setId(resultSet.getInt("ID_FOOD"));
            food.setId_category(resultSet.getInt("ID_FOOD_CATEGORY"));
            food.setName(resultSet.getString("FOOD_NAME"));
            food.setDescription(resultSet.getString("DESCRIPTION"));
            food.setPrice(resultSet.getInt("FOOD_PRICE"));
            food.setPricefee(resultSet.getInt("FOOD_PRICE_FEE"));
            food.setImage_path(resultSet.getString("FOOD_IMAGE"));
            list.add(food);
        }
        conn.close();
        return list;
    }

    @Override
    public boolean delete(Food o) {
        conn=connect();
        boolean del = false;
        String query = "DELETE FROM `food` WHERE `ID_FOOD` = ?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, o.getId());
            int delete = preparedStmt.executeUpdate();
            if (delete != -1) del = true;
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return del;
    }

    @Override
    public boolean isExist(Food o) {
        conn=connect();
        boolean bool=false;
        String query = "SELECT * FROM `food` WHERE `FOOD_NAME`=?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, o.getName());
            ResultSet resultSet = preparedStmt.executeQuery();
            if (resultSet.next())
                    bool= true;
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bool;
    }

    @Override
    public ArrayList<Food> getAll() {
        conn=connect();
        ArrayList<Food> list = new ArrayList<>();
        String query = "SELECT * FROM `food`";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
                Food food = new Food();
                food.setId(resultSet.getInt("ID_FOOD"));
                food.setId_category(resultSet.getInt("ID_Food_Category"));
                food.setName(resultSet.getString("FOOD_NAME"));
                food.setDescription(resultSet.getString("DESCRIPTION"));
                food.setPrice(resultSet.getInt("FOOD_PRICE"));
                food.setPricefee(resultSet.getInt("FOOD_PRICE_FEE"));
                food.setImage_path(resultSet.getString("FOOD_IMAGE"));
                File file=new File(food.getImage_path());
                food.setImageByPath(new Image(file.toURI().toString()));
                food.setAvailabale(resultSet.getBoolean("AVAILABLE"));
                food.setCategory(food.getId_category());

                list.add(food);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public ArrayList<Food> getAllActive() {
        conn=connect();
        ArrayList<Food> list = new ArrayList<>();
        String query = "SELECT * FROM `food`  where AVAILABLE=1 ORDER BY `ID_Food_Category`";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
                Food food = new Food();
                food.setId(resultSet.getInt("ID_FOOD"));
                food.setId_category(resultSet.getInt("ID_Food_Category"));
                food.setName(resultSet.getString("FOOD_NAME"));
                food.setDescription(resultSet.getString("DESCRIPTION"));
                food.setPrice(resultSet.getInt("FOOD_PRICE"));
                food.setPricefee(resultSet.getInt("FOOD_PRICE_FEE"));
                food.setImage_path(resultSet.getString("FOOD_IMAGE"));
                File file=new File(food.getImage_path());
                food.setImageByPath(new Image(file.toURI().toString()));
                food.setAvailabale(resultSet.getBoolean("AVAILABLE"));
                food.setIMAGE(resultSet.getBytes("IMAGE"));
                food.setRating(resultSet.getInt("RATING"));
                food.setCategory(food.getId_category());

                list.add(food);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int lastID() {
        conn=connect();
        int idlastFood = 0;
        String query = "select `ID_FOOD` from `food` ORDER BY `ID_FOOD` DESC LIMIT 1 ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
                idlastFood = resultSet.getInt("ID_FOOD");
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idlastFood;
    }

    public Food getFoodByID(int idFood) {
        conn=connect();
        Food food = new Food();
        String query = "select  * from `food` WHERE `ID_FOOD` = ? ";
        try {
            conn=connect();
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, idFood);

            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
                food.setId(resultSet.getInt("ID_FOOD"));
                food.setId_category(resultSet.getInt("ID_Food_Category"));
                food.setName(resultSet.getString("FOOD_NAME"));
                food.setDescription(resultSet.getString("DESCRIPTION"));
                food.setPrice(resultSet.getInt("FOOD_PRICE"));
                food.setPricefee(resultSet.getInt("FOOD_PRICE_FEE"));
                food.setImage_path(resultSet.getString("FOOD_IMAGE"));
                food.setRating(resultSet.getInt("RATING"));
                food.setAvailabale(resultSet.getBoolean("AVAILABLE"));
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return food;
    }
    public ArrayList<Food> getFoodByCategory(int idCategory) {
        conn=connect();
        ArrayList<Food> list = new ArrayList();
        String query = "select  * from `food` WHERE `ID_Food_Category` = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,idCategory);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
                Food food = new Food();
                food.setId(resultSet.getInt("ID_FOOD"));
                food.setId_category(resultSet.getInt("ID_Food_Category"));
                food.setName(resultSet.getString("FOOD_NAME"));
                food.setDescription(resultSet.getString("DESCRIPTION"));
                food.setPrice(resultSet.getInt("FOOD_PRICE"));
                food.setPricefee(resultSet.getInt("FOOD_PRICE_FEE"));
                food.setImage_path(resultSet.getString("FOOD_IMAGE"));
                File file=new File(food.getImage_path());
                food.setImageByPath(new Image(file.toURI().toString()));
                food.setAvailabale(resultSet.getBoolean("AVAILABLE"));
                food.setCategory(food.getId_category());

                list.add(food);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getIdFood(Food food) {
        conn=connect();
        int i=0;
        String query = "select  * from food where FOOD_NAME=?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, food.getName());
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {

                    i= resultSet.getInt("ID_FOOD");


            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    public int getCountFood() {
        conn=connect();
        int total = 0;
        String query = "SELECT COUNT(*) AS total FROM food";
        try {

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next())
                total = resultSet.getInt("total");
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }
    public int getBenefitFood(int  o){
        conn=connect();
        int total = 0;
        String query = "SELECT `FOOD_PRICE`-`FOOD_PRICE_FEE` as 'total' FROM food WHERE `ID_FOOD`=?";
        try {

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, o);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next())
                total = resultSet.getInt("total");
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;


    }

}
