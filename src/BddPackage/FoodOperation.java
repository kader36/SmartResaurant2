package BddPackage;

import Models.Food;
import javafx.scene.image.Image;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FoodOperation extends BDD<Food> {

    public boolean changeAvailability(Food o){
        boolean ins = false;
        String query = "UPDATE `food` SET `AVAILABLE` = ? WHERE `ID_FOOD` = ?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setBoolean(1, o.isAvailabale());
            preparedStmt.setInt(2, o.getId());
            int insert = preparedStmt.executeUpdate();
            if (insert != -1) ins = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ins;
    }

    @Override
    public boolean insert(Food o) {
        boolean ins = false;
        String query = "INSERT INTO `food`(`ID_Food_Category`, `FOOD_NAME`, `DESCRIPTION`, `FOOD_PRICE`, `FOOD_IMAGE`, `ID_FOOD`) \n" +
                "VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, o.getId_category());
            preparedStmt.setString(2, o.getName());
            preparedStmt.setString(3, o.getDescription());
            preparedStmt.setInt(4, o.getPrice());
            preparedStmt.setString(5, o.getImage_path());
            preparedStmt.setInt(6, o.getId());
            int insert = preparedStmt.executeUpdate();
            if (insert != -1) ins = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ins;

    }

    @Override
    public boolean update(Food o1, Food o2) {
        boolean upd = false;
        String query = "UPDATE `food` SET `ID_Food_Category`= ?,`FOOD_NAME`= ?,`DESCRIPTION`= ?,`FOOD_PRICE`= ?,`FOOD_IMAGE`= ? WHERE `ID_FOOD`= ?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, o2.getId_category());
            preparedStmt.setString(2, o2.getName());
            preparedStmt.setString(3, o2.getDescription());
            preparedStmt.setInt(4, o2.getPrice());
            preparedStmt.setString(5, o2.getImage_path());
            preparedStmt.setInt(6, o1.getId());
            int update = preparedStmt.executeUpdate();
            if (update != -1) upd = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upd;

    }
    public ArrayList<Food> getAllBy(String orderBY) {
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
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        ResultSet resultSet = preparedStmt.executeQuery();
        while (resultSet.next()) {
            Food food = new Food();
            food.setId(resultSet.getInt("ID_FOOD"));
            food.setId_category(resultSet.getInt("ID_FOOD_CATEGORY"));
            food.setName(resultSet.getString("FOOD_NAME"));
            food.setDescription(resultSet.getString("DESCRIPTION"));
            food.setPrice(resultSet.getInt("FOOD_PRICE"));
            food.setImage_path(resultSet.getString("FOOD_IMAGE"));
            list.add(food);
        }
        return list;
    }

    @Override
    public boolean delete(Food o) {
        boolean del = false;
        String query = "DELETE FROM `food` WHERE `ID_FOOD` = ?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, o.getId());
            int delete = preparedStmt.executeUpdate();
            if (delete != -1) del = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return del;
    }

    @Override
    public boolean isExist(Food o) {
        String query = "SELECT * FROM `food`";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
                if (o.getName().equals(resultSet.getString("FOOD_NAME")))
                    return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public ArrayList<Food> getAll() {
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
                food.setImage_path(resultSet.getString("FOOD_IMAGE"));
                food.setImageByPath(new Image("file:"+food.getImage_path()));
                food.setAvailabale(resultSet.getBoolean("AVAILABLE"));
                food.setCategory(food.getId_category());

                list.add(food);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int lastID() {
        int idlastFood = 0;
        String query = "select `ID_FOOD` from `food` ORDER BY `ID_FOOD` DESC LIMIT 1 ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
                idlastFood = resultSet.getInt("ID_FOOD");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idlastFood;
    }

    public Food getFoodByID(int idFood) {
        Food food = new Food();
        String query = "select  * from `food` WHERE `ID_FOOD` = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, idFood);

            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
                food.setId(resultSet.getInt("ID_FOOD"));
                food.setId_category(resultSet.getInt("ID_Food_Category"));
                food.setName(resultSet.getString("FOOD_NAME"));
                food.setDescription(resultSet.getString("DESCRIPTION"));
                food.setPrice(resultSet.getInt("FOOD_PRICE"));
                food.setImage_path(resultSet.getString("FOOD_IMAGE"));
                food.setRating(resultSet.getInt("RATING"));
                food.setAvailabale(resultSet.getBoolean("AVAILABLE"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return food;
    }

    public int getIdFood(Food food) {
        int i=0;
        String query = "select  * from food ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
                if (food.getName().equals(resultSet.getString("FOOD_NAME")) && food.getImage_path().equals(resultSet.getString("FOOD_IMAGE")) && food.getPrice() == resultSet.getInt("FOOD_PRICE")) {
                    i= resultSet.getInt("ID_FOOD");
                }
                System.out.println("values of data : " + resultSet.getInt("ID_PROVIDER_OPERATION") + " " + resultSet.getInt("ID_USER_OPERATION") + " " + resultSet.getInt("PAID_UP"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    public int getCountFood() {
        int total = 0;
        String query = "SELECT COUNT(*) AS total FROM food";
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
