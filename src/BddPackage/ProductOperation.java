package BddPackage;

import Models.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductOperation extends BDD<Product> {
    @Override
    public boolean insert(Product o) {
        boolean ins = false;
        String query = "INSERT INTO `PRODUCT`(`ID_PRODUCT`, `ID_PRODUCT_CATEGORY`, `PRODUCT_NAME`, `QUANTITY`, `STORAGE_UNIT`, `LESS_QUANTITY`)\n" +
                "VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, o.getId());
            preparedStmt.setInt(2, o.getId_category());
            preparedStmt.setString(3, o.getName());
            preparedStmt.setInt(4, o.getTot_quantity());
            preparedStmt.setString(5, o.getStorage_Unit());
            preparedStmt.setInt(6, o.getLess_quantity());
            int insert = preparedStmt.executeUpdate();
            if (insert != -1) ins = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ins;
    }

    @Override
    public boolean update(Product o1, Product o2) {
        boolean upd = false;
        String query = "UPDATE `PRODUCT` SET `ID_PRODUCT_CATEGORY`=?,`PRODUCT_NAME`=?,`QUANTITY`=?,`STORAGE_UNIT`=?,`LESS_QUANTITY`=? WHERE `ID_PRODUCT` = ?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, o1.getId_category());
            preparedStmt.setString(2, o1.getName());
            preparedStmt.setInt(3, o1.getTot_quantity());
            preparedStmt.setString(4, o1.getStorage_Unit());
            preparedStmt.setInt(5, o1.getLess_quantity());
            preparedStmt.setInt(6, o2.getId());
            int update = preparedStmt.executeUpdate();
            if (update != -1) upd = true;
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
            preparedStmt.setInt(1, o.getId());
            int delete = preparedStmt.executeUpdate();
            if (delete != -1) del = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return del;
    }

    @Override
    public boolean isExist(Product o) {
        return false;
    }

    @Override
    public ArrayList<Product> getAll() {
        ArrayList<Product> list = new ArrayList<>();
        String query = "SELECT `ID_PRODUCT`, `ID_PRODUCT_CATEGORY`, `CATEGORY_NAME`, `PRODUCT_NAME`, `STORAGE_UNIT`, `QUANTITY`, `LESS_QUANTITY` FROM `PRODUCT`,`PRODUCT_CATEGORY` WHERE PRODUCT.ID_PRODUCT_CATEGORY = PRODUCT_CATEGORY.ID_CATEGORY";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("ID_PRODUCT"));
                product.setId_category(resultSet.getInt("ID_PRODUCT_CATEGORY"));
                product.setCategory_name(resultSet.getString("CATEGORY_NAME"));
                product.setName(resultSet.getString("PRODUCT_NAME"));
                product.setStorage_Unit(resultSet.getString("STORAGE_UNIT"));
                product.setTot_quantity(resultSet.getInt("QUANTITY"));
                product.setLess_quantity(resultSet.getInt("LESS_QUANTITY"));
                list.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<Product> getAllBy(String orderBY) {
        ArrayList<Product> list = new ArrayList<>();
        String query = "SELECT * FROM `PRODUCT` ORDER BY " + orderBY + " ASC ";
        try {

            chargeData(list, query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private ArrayList<Product> chargeData(ArrayList<Product> list, String query) throws SQLException {
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        ResultSet resultSet = preparedStmt.executeQuery();
        while (resultSet.next()) {
            Product product = new Product();
            product.setId(resultSet.getInt("ID_PRODUCT"));
            product.setId_category(resultSet.getInt("ID_PRODUCT_CATEGORY"));
            product.setName(resultSet.getString("PRODUCT_NAME"));
            product.setTot_quantity(resultSet.getInt("QUANTITY"));
            product.setStorage_Unit(resultSet.getString("STORAGE_UNIT"));
            product.setLess_quantity(resultSet.getInt("LESS_QUANTITY"));
            list.add(product);
        }
        return list;
    }

    public int getNbDeadProduct() {
        int emptyQuantity = 0;
        String query = "SELECT `ID_PRODUCT`, `ID_PRODUCT_CATEGORY`, `CATEGORY_NAME`, `PRODUCT_NAME`, `STORAGE_UNIT`, `QUANTITY`, `LESS_QUANTITY` FROM `PRODUCT`,`PRODUCT_CATEGORY` WHERE PRODUCT.ID_PRODUCT_CATEGORY = PRODUCT_CATEGORY.ID_CATEGORY";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getInt("QUANTITY") == 0) {
                    emptyQuantity++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emptyQuantity;
    }

    public int getNbLessQuantity() {
        int emptyQuantity = 0;
        String query = "SELECT `ID_PRODUCT`, `ID_PRODUCT_CATEGORY`, `CATEGORY_NAME`, `PRODUCT_NAME`, `STORAGE_UNIT`, `QUANTITY`, `LESS_QUANTITY` FROM `PRODUCT`,`PRODUCT_CATEGORY` WHERE PRODUCT.ID_PRODUCT_CATEGORY = PRODUCT_CATEGORY.ID_CATEGORY";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getInt("QUANTITY") < resultSet.getInt("LESS_QUANTITY")) {
                    emptyQuantity++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emptyQuantity;
    }

    public int getCountProduct(){
        int total = 0;
        String query = "SELECT COUNT(*) AS total FROM PRODUCT";
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
