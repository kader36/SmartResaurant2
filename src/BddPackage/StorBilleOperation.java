package BddPackage;

import Models.StoreBill;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StorBilleOperation extends BDD<StoreBill> {
    @Override
    public boolean insert(StoreBill o) {
        boolean ins = false;
        String query = "INSERT INTO `STORE_BILL`( `ID_USER_OPERATION`, `ID_PROVIDER_OPERATION`, `PAID_UP`)\n" +
                "VALUES (?,?,?)";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(   1,o.getId());
            preparedStmt.setInt(   2,o.getId_provider());
            preparedStmt.setInt(   3,o.getPaid_up());
            int insert = preparedStmt.executeUpdate();
            if(insert != -1) ins = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ins;
    }

    @Override
    public boolean update(StoreBill o1, StoreBill o2) {
        boolean upd = false;
        String query = "UPDATE `STORE_BILL` SET `ID_USER_OPERATION`=?,`ID_PROVIDER_OPERATION`=?,`PAID_UP`=? WHERE `ID_STORE_BILL` = ?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o1.getId_user());
            preparedStmt.setInt(2,o1.getId_provider());
            preparedStmt.setInt(3,o1.getPaid_up());
            preparedStmt.setInt(4,o2.getId());
            int update = preparedStmt.executeUpdate();
            if(update != -1) upd = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upd;
    }

    @Override
    public boolean delete(StoreBill o) {
        return false;
    }

    @Override
    public boolean isExist(StoreBill o) {
        return false;
    }

    @Override
    public ArrayList<StoreBill> getAll() {
        ArrayList<StoreBill> list = new ArrayList<>();
        String query = "SELECT * FROM `STORE_BILL`";
        //SELECT `ID_STORE_BILL`, `STORE_BILL_DATE`, `ID_USER_OPERATION`, `ID_PROVIDER_OPERATION`,
        // `PAID_UP` FROM `STORE_BILL` WHERE 1
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){
                StoreBill storeBill = new StoreBill();
                storeBill.setId(resultSet.getInt("ID_STORE_BILL"));
                storeBill.setDate(resultSet.getDate("STORE_BILL_DATE"));
                storeBill.setId_user(resultSet.getInt("ID_USER_OPERATION"));
                storeBill.setId_provider(resultSet.getInt("ID_PROVIDER_OPERATION"));
                storeBill.setPaid_up(resultSet.getInt("PAID_UP"));
                list.add(storeBill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
