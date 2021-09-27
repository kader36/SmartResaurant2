package BddPackage;

import Models.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductOperation extends BDD<Product> {
    @Override
    public boolean insert(Product o) {
        conn=connect();
        boolean ins = false;
        String query = "INSERT INTO `product`(`ID_PRODUCT`, `ID_PRODUCT_CATEGORY`, `PRODUCT_NAME`, `QUANTITY`, `STORAGE_UNIT`, `LESS_QUANTITY`, `Unity_Food`, `coefficient`)\n" +
                "VALUES (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, o.getId());
            preparedStmt.setInt(2, o.getId_category());
            preparedStmt.setString(3, o.getName());
            preparedStmt.setDouble(4, o.getTot_quantity());
            preparedStmt.setString(5, o.getStorage_Unit());
            preparedStmt.setInt(6, o.getLess_quantity());
            preparedStmt.setString(7, o.getUnity_Food());
            preparedStmt.setInt(8, o.getCoefficient());

            int insert = preparedStmt.executeUpdate();
            if (insert != -1) ins = true;
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ins;
    }

    @Override
    public boolean update(Product o1, Product o2) {
        conn=connect();
        boolean upd = false;
        String query = "UPDATE `product` SET `ID_PRODUCT_CATEGORY`=?,`PRODUCT_NAME`=?,`QUANTITY`=?,`STORAGE_UNIT`=?,`LESS_QUANTITY`=? WHERE `ID_PRODUCT` = ?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, o1.getId_category());
            preparedStmt.setString(2, o1.getName());
            preparedStmt.setDouble(3, o1.getTot_quantity());
            preparedStmt.setString(4, o1.getStorage_Unit());
            preparedStmt.setInt(5, o1.getLess_quantity());
            preparedStmt.setInt(6, o2.getId());
            int update = preparedStmt.executeUpdate();
            if (update != -1) upd = true;
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upd;
    }
    public boolean update(Product o1) {
        conn=connect();
        boolean upd = false;
        String query = "UPDATE `product` SET `QUANTITY`=? WHERE `ID_PRODUCT`=?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);

            preparedStmt.setDouble(1, o1.getTot_quantity());
            preparedStmt.setInt(2, o1.getId());
            int update = preparedStmt.executeUpdate();
            if (update != -1) upd = true;
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upd;
    }

    @Override
    public boolean delete(Product o) {
        conn=connect();
        boolean del = false;
        String query = "DELETE FROM `product` WHERE  `ID_PRODUCT` = ? ";
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
    public boolean isExist(Product o) {
        return false;
    }

    @Override
    public ArrayList<Product> getAll() {
        conn=connect();
        ArrayList<Product> list = new ArrayList<>();
        String query = "SELECT * FROM `product`,`product_category` WHERE product.ID_PRODUCT_CATEGORY = product_category.ID_CATEGORY";
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
                product.setUnity_Food(resultSet.getString("Unity_Food"));
                product.setCoefficient(resultSet.getInt("coefficient"));
                product.setLess_quantity(resultSet.getInt("LESS_QUANTITY"));
                product.setTot_quantity(resultSet.getDouble("QUANTITY"));
                //product.setLess_quantity(resultSet.getInt("LESS_QUANTITY"));
                list.add(product);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<Product> getAllBy(String orderBY) {
        ArrayList<Product> list = new ArrayList<>();
        String query = "SELECT * FROM `product`,`product_category` WHERE product.ID_PRODUCT_CATEGORY = product_category.ID_CATEGORY ORDER BY " + orderBY + " ASC ";
        try {

            chargeData(list, query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<Product> getProductFinished() {
        ArrayList<Product> list = new ArrayList<>();
        String query = "SELECT * FROM `product` WHERE QUANTITY <= `LESS_QUANTITY`;";
        try {

            chargeData(list, query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private ArrayList<Product> chargeData(ArrayList<Product> list, String query) throws SQLException {
        conn=connect();
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        ResultSet resultSet = preparedStmt.executeQuery();
        while (resultSet.next()) {
            Product product = new Product();
            product.setId(resultSet.getInt("ID_PRODUCT"));
            product.setId_category(resultSet.getInt("ID_PRODUCT_CATEGORY"));
            product.setName(resultSet.getString("PRODUCT_NAME"));
            product.setStorage_Unit(resultSet.getString("STORAGE_UNIT"));
            product.setUnity_Food(resultSet.getString("Unity_Food"));
            product.setCoefficient(resultSet.getInt("coefficient"));
            product.setLess_quantity(resultSet.getInt("LESS_QUANTITY"));
            product.setTot_quantity(resultSet.getDouble("QUANTITY"));
            //product.setLess_quantity(resultSet.getInt("LESS_QUANTITY"));
            list.add(product);
        }
        conn.close();
        return list;
    }

    public int getNbDeadProduct() {
        conn=connect();
        int emptyQuantity = 0;
        String query = "SELECT `ID_PRODUCT`, `ID_PRODUCT_CATEGORY`, `CATEGORY_NAME`, `PRODUCT_NAME`, `STORAGE_UNIT`, `QUANTITY` FROM `product`,`product_category` WHERE product.ID_PRODUCT_CATEGORY = product_category.ID_CATEGORY";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getInt("QUANTITY") == 0) {
                    emptyQuantity++;
                }
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emptyQuantity;
    }

    public int getNbLessQuantity() {
        conn=connect();
        int emptyQuantity = 0;
        String query = "SELECT `ID_PRODUCT`, `ID_PRODUCT_CATEGORY`, `CATEGORY_NAME`, `PRODUCT_NAME`, `STORAGE_UNIT`, `QUANTITY` FROM `product`,`product_category` WHERE product.ID_PRODUCT_CATEGORY = product_category.ID_CATEGORY";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getInt("QUANTITY") < resultSet.getInt("LESS_QUANTITY")) {
                    emptyQuantity++;
                }
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emptyQuantity;
    }

    public int getCountProduct(){
        conn=connect();
        int total = 0;
        String query = "SELECT COUNT(*) AS total FROM product";
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
    public  Product GetProduct(int  idProduct){
        conn=connect();
        String query = "SELECT * FROM `product` WHERE `ID_PRODUCT` =?";
        Product product=new Product();
        try {

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, idProduct);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
                product.setId(resultSet.getInt("ID_PRODUCT"));
                product.setId_category(resultSet.getInt("ID_PRODUCT_CATEGORY"));
                product.setName(resultSet.getString("PRODUCT_NAME"));
                product.setTot_quantity(resultSet.getDouble("QUANTITY"));
                product.setStorage_Unit(resultSet.getString("STORAGE_UNIT"));
                product.setLess_quantity(resultSet.getInt("LESS_QUANTITY"));
                product.setCoefficient(resultSet.getInt("coefficient"));
            }
            conn.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return product;

    }
    public  Product GetProduct(String  nameProduct){
        conn=connect();
        String query = "SELECT * FROM `product` WHERE `PRODUCT_NAME` =?";
        Product product=new Product();
        try {

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, nameProduct);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
                product.setId(resultSet.getInt("ID_PRODUCT"));
                product.setId_category(resultSet.getInt("ID_PRODUCT_CATEGORY"));
                product.setName(resultSet.getString("PRODUCT_NAME"));
                product.setTot_quantity(resultSet.getDouble("QUANTITY"));
                product.setStorage_Unit(resultSet.getString("STORAGE_UNIT"));
                product.setUnity_Food(resultSet.getString("Unity_Food"));
                product.setLess_quantity(resultSet.getInt("LESS_QUANTITY"));
                product.setCoefficient(resultSet.getInt("coefficient"));
            }
            conn.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return product;

    }
}
