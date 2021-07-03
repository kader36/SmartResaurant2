package BddPackage;

import Models.ProductComposite;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductCompositeOperation extends BDD<ProductComposite>{
    @Override
    public boolean insert(ProductComposite o) {
        boolean ins = false;
        String query = "INSERT INTO `product_composite`( `name`, `QUANTITY`, `LESS_QUANTITY`, `storage_Unit`, `Unity_Food`, `Coefficient`) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1,o.getName());
            preparedStmt.setFloat(2,o.getQuantity());
            preparedStmt.setInt(3,o.getLESS_QUANTITY());
            preparedStmt.setString(4,o.getStorage_Unit());
            preparedStmt.setString(5,o.getUnity_Food());
            preparedStmt.setInt(6,o.getCoefficient());
            int insert = preparedStmt.executeUpdate();
            if(insert != -1) ins = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ins;
    }

    @Override
    public boolean update(ProductComposite o1, ProductComposite o2) {
        return false;
    }
    public boolean update(ProductComposite o1) {
        boolean upd = false;
        String query = "UPDATE `product_composite` SET `QUANTITY`=? WHERE `id`=?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setFloat(1, o1.getQuantity());

            preparedStmt.setInt(2, o1.getId());
            int update = preparedStmt.executeUpdate();
            if (update != -1) upd = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upd;
    }

    @Override
    public boolean delete(ProductComposite o) {
        return false;
    }

    @Override
    public boolean isExist(ProductComposite o) {
        return false;
    }

    @Override
    public ArrayList<ProductComposite> getAll() {

        ArrayList<ProductComposite> list = new ArrayList<>();
        String query = "SELECT * FROM `product_composite`";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
                ProductComposite productComposite = new ProductComposite();
                productComposite.setName(resultSet.getString("name"));
                productComposite.setQuantity(resultSet.getFloat("QUANTITY"));
                productComposite.setStorage_Unit(resultSet.getString("storage_Unit"));
                productComposite.setUnity_Food(resultSet.getString("Unity_Food"));
                productComposite.setCoefficient(resultSet.getInt("Coefficient"));
                productComposite.setLESS_QUANTITY(resultSet.getInt("LESS_QUANTITY"));


                list.add(productComposite);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ProductComposite GetProductComposite(String  nameProduct){
        String query = "SELECT * FROM `product_composite` WHERE `name`=?";
        ProductComposite productComposite=new ProductComposite();
        try {

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, nameProduct);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
                productComposite.setId(resultSet.getInt("id"));
                productComposite.setName(resultSet.getString("name"));
                productComposite.setQuantity(resultSet.getInt("QUANTITY"));
                productComposite.setStorage_Unit(resultSet.getString("STORAGE_UNIT"));
                productComposite.setLESS_QUANTITY(resultSet.getInt("LESS_QUANTITY"));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return productComposite;

    }
}
