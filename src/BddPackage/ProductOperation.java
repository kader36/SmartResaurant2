package BddPackage;

import Models.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public  class ProductOperation extends BDD<Product> {
    @Override
    public boolean insert(Product o) {
        boolean ins = false;
        String query = "INSERT INTO `PRODUCT`( `ID_PRODUCT_CATEGORY`, `PRODUCT_NAME`, `PURCHASE_UNIT`, `STORAGE_UNIT`, `RECIPE_UNIT`, `QUANTITY`)\n" +
                "VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(      1   ,o.getId_category());
            preparedStmt.setString(   2,o.getName());
            preparedStmt.setString(   3,o.getPurchase_Unit());
            preparedStmt.setString(   4,o.getStorage_Unit());
            preparedStmt.setString(   5,o.getRecipe_Unit());
            preparedStmt.setInt(      6,o.getQuantity());
            int insert = preparedStmt.executeUpdate();
            if (insert != -1) ins = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ins;
    }

    @Override
    public boolean update(Product o1, Product o2){
        boolean upd = false;
        String query =  "UPDATE `PRODUCT` SET `ID_PRODUCT_CATEGORY`=?,`PRODUCT_NAME`=?,`PURCHASE_UNIT`=?,`STORAGE_UNIT`=?,`RECIPE_UNIT`=?,`QUANTITY`=? WHERE `ID_PRODUCT` = ?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(   1,o1.getId_category());
            preparedStmt.setString(2,o1.getName());
            preparedStmt.setString(   3,o1.getPurchase_Unit());
            preparedStmt.setString(   4,o1.getStorage_Unit());
            preparedStmt.setString(   5,o1.getRecipe_Unit());
            preparedStmt.setInt(   6,o1.getQuantity());
            preparedStmt.setInt(   7,o2.getId());
            int update = preparedStmt.executeUpdate();
            if(update != -1 ) upd = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upd;
    }

    @Override
    public boolean delete(Product o) {
        boolean del = false;
        String query = "DELETE FROM `PRODUCT` WHERE  `ID_PRODUCT` = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o.getId());
            int delete = preparedStmt.executeUpdate();
            if(delete != -1) del = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return del;    }

    @Override
    public boolean isExist(Product o) {
        return false;
    }

    @Override
    public ArrayList<Product> getAll() {
        ArrayList<Product> list = new ArrayList<>();
        String query = "SELECT `ID_PRODUCT`, `ID_PRODUCT_CATEGORY`, `CATEGORY_NAME`, `PRODUCT_NAME`, `PURCHASE_UNIT`, `STORAGE_UNIT`, `RECIPE_UNIT`, `QUANTITY` FROM `PRODUCT`,`PRODUCT_CATEGORY` WHERE PRODUCT.ID_PRODUCT_CATEGORY = PRODUCT_CATEGORY.ID_CATEGORY";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt(         "ID_PRODUCT"));
                product.setId_category(resultSet.getInt("ID_PRODUCT_CATEGORY"));
                product.setCategory_name(resultSet.getString(    "CATEGORY_NAME"));
                product.setName(resultSet.getString(    "PRODUCT_NAME"));
                product.setPurchase_Unit(resultSet.getString("PURCHASE_UNIT"));
                product.setStorage_Unit(resultSet.getString("STORAGE_UNIT"));
                product.setRecipe_Unit(resultSet.getString("RECIPE_UNIT"));
                product.setQuantity(resultSet.getInt(   "QUANTITY"));
                list.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}
