package BddPackage;

import Models.StoreBillProduct;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class StoreBillProductOperation extends BDD<StoreBillProduct> {
    @Override
    public boolean insert(StoreBillProduct o) {
        boolean ins = false;
        String query = "INSERT INTO `STORE_BILL_PRODUCT`(`ID_STORE_BILL`, `ID_PRODUCT`, `PRICE`, `PRODUCT_QUANTITY`) VALUES (?,?,?,?)";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(   1,o.getId_stor_bill());
            preparedStmt.setInt(   2,o.getId_product());
            preparedStmt.setInt(   3,o.getPrice());
            preparedStmt.setInt(   4,o.getProduct_quantity());
            int insert = preparedStmt.executeUpdate();
            if(insert != -1) ins = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ins;
    }

    @Override
    public boolean update(StoreBillProduct o1, StoreBillProduct o2) {
        boolean upd = false;
        String query = "UPDATE `STORE_BILL_PRODUCT` SET `ID_PRODUCT`= ?,`PRICE`= ?,`PRODUCT_QUANTITY`= ? WHERE `ID_STORE_BILL`= ?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o1.getId_product());
            preparedStmt.setInt(2,o1.getPrice());
            preparedStmt.setInt(3,o1.getProduct_quantity());
            preparedStmt.setInt(4,o2.getId_stor_bill());
            int update = preparedStmt.executeUpdate();
            if(update != -1) upd = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upd;
    }

    @Override
    public boolean delete(StoreBillProduct o) {
        return false;
    }

    @Override
    public boolean isExist(StoreBillProduct o) {
        return false;
    }

    @Override
    public ArrayList<StoreBillProduct> getAll() {
        return null;
    }
}
