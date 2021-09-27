package BddPackage;

import Models.ProductCategory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductCategoryOperation extends BDD<ProductCategory> {
    @Override
    public boolean insert(ProductCategory o) {
        conn=connect();
        boolean ins = false;
        String query = "INSERT INTO `product_category`(`CATEGORY_NAME`) VALUES (?)";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1,o.getName());
            int insert = preparedStmt.executeUpdate();
            if(insert != -1) ins = true;
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ins;
    }

    @Override
    public boolean update(ProductCategory o1, ProductCategory o2) {
        conn=connect();
        boolean upd = false;
        String query = "UPDATE `product_category` SET `CATEGORY_NAME`=? WHERE `ID_CATEGORY` = ?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1,o1.getName());
            preparedStmt.setInt(2, o2.getId());
            int update = preparedStmt.executeUpdate();
            if(update != -1) upd = true;
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return upd;    }

    @Override
    public boolean delete(ProductCategory o) {
        conn=connect();
        boolean del = false;
        String query = "DELETE FROM `product_category` WHERE  `ID_CATEGORY` = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o.getId());
            int delete = preparedStmt.executeUpdate();
            if(delete != -1) del = true;
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return del;    }

    @Override
    public boolean isExist(ProductCategory o) {
        return false;
    }

    @Override
    public ArrayList<ProductCategory> getAll() {
        conn=connect();
        ArrayList<ProductCategory> list = new ArrayList<>();
        String query = "SELECT * FROM `product_category`";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){
                ProductCategory productCategory = new ProductCategory();
                productCategory.setId(resultSet.getInt("ID_CATEGORY"));
                productCategory.setName(resultSet.getString("CATEGORY_NAME"));
                list.add(productCategory);

            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;     }
}
