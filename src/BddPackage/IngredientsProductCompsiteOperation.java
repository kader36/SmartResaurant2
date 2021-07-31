package BddPackage;

import Models.IngredientsProductCompsite;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class IngredientsProductCompsiteOperation extends BDD<IngredientsProductCompsite>{
    @Override
    public boolean insert(IngredientsProductCompsite o) {
        conn=connect();
        boolean ins = false;
        String query = "INSERT INTO `ingredients_productcompsite`(`id_product`, `id_producComposite`, `quantity`)VALUES (?,?,?)";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, o.getId_product());
            preparedStmt.setInt(2, o.getId_productCompsite());
            preparedStmt.setInt(3, o.getQuantity());
            int insert = preparedStmt.executeUpdate();
            if (insert != -1) ins = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ins;

    }

    @Override
    public boolean update(IngredientsProductCompsite o1, IngredientsProductCompsite o2) {
        return false;
    }

    @Override
    public boolean delete(IngredientsProductCompsite o) {
        return false;
    }

    @Override
    public boolean isExist(IngredientsProductCompsite o) {
        return false;
    }

    @Override
    public ArrayList<IngredientsProductCompsite> getAll() {
        return null;
    }

}
