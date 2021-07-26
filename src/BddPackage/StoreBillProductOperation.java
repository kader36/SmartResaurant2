package BddPackage;

import Models.Product;
import Models.StoreBillProduct;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StoreBillProductOperation extends BDD<StoreBillProduct> {
    @Override
    public boolean insert(StoreBillProduct o) {
        boolean ins = false;
        String query = "INSERT INTO `store_bill_product`(`ID_STORE_BILL`, `ID_PRODUCT`, `PRICE`, `PRODUCT_QUANTITY`) VALUES (?,?,?,?)";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o.getId_stor_bill());
            preparedStmt.setInt(2, o.getId_product());
            preparedStmt.setInt(3, o.getPrice());
            preparedStmt.setInt(4, o.getProduct_quantity());
            System.out.println(o.getId_stor_bill());
            System.out.println(o.getId_product());
            System.out.println(o.getPrice());
            System.out.println(o.getProduct_quantity());

            int insert = preparedStmt.executeUpdate();
            if (insert != -1) ins = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ins;
    }

//    public boolean insert(Bill o, String providerName) {
//        boolean ins = false;
//        String query = "INSERT INTO `STORE_BILL_PRODUCT`(`ID_STORE_BILL`, `ID_PRODUCT`, `PRICE`, `PRODUCT_QUANTITY`) VALUES (?,?,?,?)";
//        try {
//            PreparedStatement preparedStmt = conn.prepareStatement(query);
//            preparedStmt.setInt(   1,o.getId_stor_bill());
//            preparedStmt.setInt(   2,o.getId_product());
//            preparedStmt.setInt(   3,o.getPrice());
//            preparedStmt.setInt(   4,o.getProduct_quantity());
//            int insert = preparedStmt.executeUpdate();
//            if(insert != -1) ins = true;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return ins;
//    }

    @Override
    public boolean update(StoreBillProduct o1, StoreBillProduct o2) {
        boolean upd = false;
        String query = "UPDATE `store_bill_product` SET `ID_PRODUCT`= ?,`PRICE`= ?,`PRODUCT_QUANTITY`= ? WHERE `ID_STORE_BILL`= ?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, o1.getId_product());
            preparedStmt.setInt(2, o1.getPrice());
            preparedStmt.setInt(3, o1.getProduct_quantity());
            preparedStmt.setInt(4, o2.getId_stor_bill());
            int update = preparedStmt.executeUpdate();
            if (update != -1) upd = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upd;
    }



    @Override
    public boolean delete(StoreBillProduct o) {
        return false;
    }


    public boolean delete(int o) {
        boolean del = false;
        String query = "DELETE FROM `store_bill_product` WHERE ID_STORE_BILL = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, o);
            int delete = preparedStmt.executeUpdate();
            if (delete != -1) del = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("value of del : " + del);
        return del;
    }

    @Override
    public boolean isExist(StoreBillProduct o) {
        return false;
    }

    @Override
    public ArrayList<StoreBillProduct> getAll() {
        conn=connect();
        ArrayList<StoreBillProduct> list = new ArrayList<>();
        String query = "SELECT * FROM `store_bill_product`";
        try {
            chargeData(list, query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;

    }

    private ArrayList<StoreBillProduct> chargeData(ArrayList<StoreBillProduct> list, String query) throws SQLException {
        conn=connect();
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        ResultSet resultSet = preparedStmt.executeQuery();
        while (resultSet.next()){
            StoreBillProduct storeBillProduct = new StoreBillProduct();
            storeBillProduct.setId_stor_bill(resultSet.getInt(           "ID_STORE_BILL"));
            storeBillProduct.setId_product(resultSet.getInt("ID_PRODUCT"));
            storeBillProduct.setPrice(resultSet.getInt( "PRICE"));
            storeBillProduct.setProduct_quantity(resultSet.getInt( "PRODUCT_QUANTITY"));
            list.add(storeBillProduct);
        }
        return list;
    }
    public ArrayList<StoreBillProduct> getParProduite(Product product) {
        ArrayList<StoreBillProduct> list = new ArrayList<>();
        String query = "SELECT * FROM `store_bill_product` where ID_PRODUCT=?";
        try {

            PreparedStatement preparedStmt = null;
            preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, product.getId());
            ResultSet resultSet = preparedStmt.executeQuery();
        while (resultSet.next()){
            StoreBillProduct storeBillProduct = new StoreBillProduct();
            storeBillProduct.setId_stor_bill(resultSet.getInt(           "ID_STORE_BILL"));
            storeBillProduct.setId_product(resultSet.getInt("ID_PRODUCT"));
            storeBillProduct.setPrice(resultSet.getInt( "PRICE"));
            storeBillProduct.setProduct_quantity(resultSet.getInt( "PRODUCT_QUANTITY"));
            list.add(storeBillProduct);
        }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;

    }
    public int  getMouyenPrice(Product product) {
        int price =0;
        String query = "SELECT avg(`PRICE`) as 'PRICE' FROM `store_bill_product` WHERE `ID_PRODUCT`=?";
        try {
            conn=connect();
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, product.getId());
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){
                price=resultSet.getInt( "PRICE");
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return price;
    }
}
