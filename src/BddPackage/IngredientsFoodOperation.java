package BddPackage;

import Models.IngredientsFood;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class IngredientsFoodOperation extends BDD<IngredientsFood> {
    @Override
    public boolean insert(IngredientsFood o) {

        boolean ins = false;
        String query = "INSERT INTO `INGREDIENTS_FOOD`(`ID_FOOD_INGREDIENT`, `ID_PRODUCT_INGREDIENT`,`INGREDIENT_QUANTITY`) VALUES (?,?,?)";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o.getId_food());
            preparedStmt.setInt(2,o.getId_product());
            preparedStmt.setInt(3,o.getQuantity());
            int insert = preparedStmt.executeUpdate();
            if(insert != -1) ins = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ins;    }

    @Override
    public boolean update(IngredientsFood o1, IngredientsFood o2)
    {
        boolean upd = false;
        String query = "UPDATE `INGREDIENTS_FOOD` SET `ID_PRODUCT_INGREDIENT`=?,`INGREDIENT_QUANTITY`=? WHERE `ID_FOOD_INGREDIENT`= ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o1.getId_product());
            preparedStmt.setInt(2,o1.getQuantity());
            preparedStmt.setInt(3,o2.getId_food());
            int update = preparedStmt.executeUpdate();
            if(update != -1) upd = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upd;
    }

    @Override
    public boolean delete(IngredientsFood o) {
        return false;
    }

    @Override
    public boolean isExist(IngredientsFood o) {
        return false;
    }

    @Override
    public ArrayList<IngredientsFood> getAll() {
        return null;
    }
}
