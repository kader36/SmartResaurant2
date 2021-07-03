package BddPackage;

import Controllers.ValuesStatic;
import Models.Provider;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProviderOperation extends BDD<Provider> {
    @Override
    public boolean insert(Provider o) {
        boolean ins = false;
        String query = "INSERT INTO `provider`( `PROVIDER_FIRST_NAME`, `PROVIDER_LAST_NAME`, `PROVIDER_PHONE_NUMBER`,\n" +
                "`PROVIDER_JOB`,`PROVIDER_ADRESS`) \n" +
                "VALUES (?,?,?,?,?)";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, o.getFirst_name());
            preparedStmt.setString(2, o.getLast_name());
            preparedStmt.setString(3, o.getPhone_number());
            preparedStmt.setString(4, o.getJob());
            preparedStmt.setString(5, o.getAdress());
            int insert = preparedStmt.executeUpdate();
            if (insert != -1) ins = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ins;
    }

    @Override
    public boolean update(Provider o1, Provider o2) {
        boolean upd = false;
        String query = "UPDATE `provider` SET `PROVIDER_FIRST_NAME`= ?,`PROVIDER_LAST_NAME`= ?,\n" +
                "    `PROVIDER_JOB`=? ,`PROVIDER_ADRESS`= ? WHERE ID_PROVIDER = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, o1.getFirst_name());
            preparedStmt.setString(2, o1.getLast_name());
            preparedStmt.setString(3, o1.getJob());
            preparedStmt.setString(4, o1.getAdress());
            preparedStmt.setInt(5, o2.getId());
            int update = preparedStmt.executeUpdate();
            if (update != -1) upd = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upd;
    }
    public boolean update(Provider o1) {
        boolean upd = false;
        String query = "UPDATE `provider` SET `PROVIDER_CREDITOR`= ? WHERE `ID_PROVIDER`= ?" ;

        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setDouble(1, o1.getCreditor());
            preparedStmt.setInt(2 ,o1.getId());
            int update = preparedStmt.executeUpdate();
            if (update != -1) upd = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upd;
    }

    @Override
    public boolean delete(Provider o) {
        boolean del = false;
        String query = "DELETE FROM `provider` WHERE ID_PROVIDER = ? ";
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
    public boolean isExist(Provider o) {
        return false;
    }

    @Override
    public ArrayList<Provider> getAll() {

        ArrayList<Provider> list = new ArrayList<>();
        String query = "SELECT * FROM `provider`";
        try {
            chargeData(list, query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<Provider> getAllBy(String orderBY) {
        ArrayList<Provider> list = new ArrayList<>();
        String query = "SELECT * FROM `provider` ORDER BY " + orderBY + " ASC ";
        try {

            chargeData(list, query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<Provider> getCredetorProvider() {
        ArrayList<Provider> list = new ArrayList<>();
        String query = "SELECT * FROM `provider` WHERE PROVIDER_CREDITOR > 0";
        try {

            chargeData(list, query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    private ArrayList<Provider> chargeData(ArrayList<Provider> list, String query) throws SQLException {
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        ResultSet resultSet = preparedStmt.executeQuery();
        ValuesStatic.totCreditor = 0;
        ValuesStatic.credetorToday = 0;
        while (resultSet.next()) {
            Provider provider = new Provider();
            provider.setId(resultSet.getInt("ID_PROVIDER"));
            provider.setFirst_name(resultSet.getString("PROVIDER_FIRST_NAME"));
            provider.setLast_name(resultSet.getString("PROVIDER_LAST_NAME"));
            provider.setPhone_number(resultSet.getString("PROVIDER_PHONE_NUMBER"));
            provider.setAdress(resultSet.getString("PROVIDER_ADRESS"));
            provider.setJob(resultSet.getString("PROVIDER_JOB"));
            provider.setCreditor(resultSet.getDouble("PROVIDER_CREDITOR"));
            provider.setCreditor_to(resultSet.getString("PROVIDER_CREDITOR_TO"));
            list.add(provider);
            ValuesStatic.totCreditor += provider.getCreditor();
        }
        return list;
    }


    public Provider getProviderById(int idProvider) {
        ArrayList<Provider> list = new ArrayList<>();
        String query = "SELECT * FROM `provider` where ID_PROVIDER = " + idProvider;
        try {
            chargeData(list, query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list.get(0);
    }

    public int getCountProvider() {
        int total = 0;
        String query = "SELECT COUNT(*) AS total FROM provider";
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
