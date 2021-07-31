package BddPackage;

import Models.IngredientsFoodProductCompose;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FoodProductComposeOperation extends BDD<IngredientsFoodProductCompose> {

    public boolean changeAvailability(IngredientsFoodProductCompose o){
        boolean ins = false;

        return ins;
    }


    @Override
    public boolean insert(IngredientsFoodProductCompose o) {
        conn=connect();
        boolean ins = false;
        String query = "INSERT INTO `food_productcompsite`(`ID_FOOD`, `ID_PRODUCT`, `QUANTITY`)  VALUES (?,?,?)" ;

        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(   1,o.getId_food());
            preparedStmt.setInt(2,o.getId_productCopmpose());
            preparedStmt.setDouble(3,o.getQuantity());

            int insert = preparedStmt.executeUpdate();
            if(insert != -1) ins = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ins;
    }

    @Override
    public boolean update(IngredientsFoodProductCompose o1, IngredientsFoodProductCompose o2) {
        conn=connect();
        boolean upd = false;

        return upd;
    }

    @Override
    public boolean delete(IngredientsFoodProductCompose o) {
        conn=connect();
        boolean del = false;

        return del;
    }

    @Override
    public boolean isExist(IngredientsFoodProductCompose o) {
        return false;
    }

    @Override
    public ArrayList<IngredientsFoodProductCompose> getAll() {

        return null;
    }
    public ArrayList<IngredientsFoodProductCompose> getIngredientsFood(int idFood) {
        conn=connect();
        ArrayList<IngredientsFoodProductCompose> list = new ArrayList<>();
        String query = "SELECT `ID_FOOD`, `ID_PRODUCT`, `QUANTITY` FROM `food_productcompsite` WHERE `ID_FOOD`=?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, idFood);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
                IngredientsFoodProductCompose ingredientsFood = new IngredientsFoodProductCompose();
                ingredientsFood.setId_food(resultSet.getInt("ID_FOOD"));
                ingredientsFood.setId_productCopmpose(resultSet.getInt("ID_PRODUCT"));
                ingredientsFood.setQuantity(resultSet.getDouble("QUANTITY"));

                list.add(ingredientsFood);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


}
