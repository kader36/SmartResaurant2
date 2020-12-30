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
            preparedStmt.setInt(1, o.getId_user());
            preparedStmt.setInt(2, o.getId_provider());
            preparedStmt.setInt(3, o.getPaid_up());
            int insert = preparedStmt.executeUpdate();
            if (insert != -1) ins = true;
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
            preparedStmt.setInt(1, o1.getId_user());
            preparedStmt.setInt(2, o1.getId_provider());
            preparedStmt.setInt(3, o1.getPaid_up());
            preparedStmt.setInt(4, o2.getId());
            int update = preparedStmt.executeUpdate();
            if (update != -1) upd = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upd;
    }

    @Override
    public boolean delete(StoreBill o) {
        boolean del = false;
        String query = "DELETE FROM `STORE_BILL` WHERE ID_STORE_BILL = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, o.getId());
            int delete = preparedStmt.executeUpdate();
            if (delete != -1) del = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("value of del : " + del);
        return del;
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
            while (resultSet.next()) {
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


    public int getIdStorBill(int idProvider, int idUser, int paid) {
        String query = "SELECT * FROM `STORE_BILL`";
        //SELECT `ID_STORE_BILL`, `STORE_BILL_DATE`, `ID_USER_OPERATION`, `ID_PROVIDER_OPERATION`,
        // `PAID_UP` FROM `STORE_BILL` WHERE 1
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
                System.out.println("values of parametre : " + idProvider + " " + idUser + " " + paid);
                System.out.println("values of data : " + resultSet.getInt("ID_PROVIDER_OPERATION") + " " + resultSet.getInt("ID_USER_OPERATION") + " " + resultSet.getInt("PAID_UP"));
                if (idProvider == resultSet.getInt("ID_PROVIDER_OPERATION") && idUser == resultSet.getInt("ID_USER_OPERATION") && paid == resultSet.getInt("PAID_UP")) {
                    System.out.println("values of data : " + resultSet.getInt("ID_PROVIDER_OPERATION") + " " + resultSet.getInt("ID_USER_OPERATION") + " " + resultSet.getInt("PAID_UP"));
                    return resultSet.getInt("ID_STORE_BILL");
                }


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getCountStoreBill(){
        int total = 0;
        String query = "SELECT COUNT(*) AS total FROM STORE_BILL";
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
