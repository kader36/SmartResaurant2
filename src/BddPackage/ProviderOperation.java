package BddPackage;

import Models.Provider;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProviderOperation extends BDD<Provider> {
    @Override
    public boolean insert(Provider o) {
        boolean ins = false;
        String query = "INSERT INTO `PROVIDER`( `PROVIDER_FIRST_NAME`, `PROVIDER_LAST_NAME`, `PROVIDER_PHONE_NUMBER`,\n" +
                "`PROVIDER_JOB`,`PROVIDER_ADRESS`) \n" +
                "VALUES (?,?,?,?,?)";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1,o.getFirst_name());
            preparedStmt.setString(2,o.getLast_name());
            preparedStmt.setString(   3,o.getPhone_number());
            preparedStmt.setString(   4,o.getJob());
            preparedStmt.setString(5,o.getAdress());
            int insert = preparedStmt.executeUpdate();
            if(insert != -1) ins = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ins;
    }

    @Override
    public boolean update(Provider o1, Provider o2) {
        boolean upd = false;
        String query = "UPDATE `PROVIDER` SET `PROVIDER_FIRST_NAME`= ?,`PROVIDER_LAST_NAME`= ?,\n" +
                "    `PROVIDER_JOB`=? ,`PROVIDER_ADRESS`= ? WHERE ID_PROVIDER = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1,o1.getFirst_name());
            preparedStmt.setString(2,o1.getLast_name());
            preparedStmt.setString(   3,o1.getJob());
            preparedStmt.setString(4,o1.getAdress());
            preparedStmt.setInt(   5,o2.getId());
            int update = preparedStmt.executeUpdate();
            if(update != -1) upd = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upd;
    }

    @Override
    public boolean delete(Provider o) {
        boolean del = false;
        String query = "DELETE FROM `PROVIDER` WHERE ID_PROVIDER = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o.getId());
            int delete = preparedStmt.executeUpdate();
            if(delete != -1) del = true;
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
        String query = "SELECT * FROM `PROVIDER`";
        try {
            chargeData(list, query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<Provider> getAllBy(String orderBY){
        ArrayList<Provider> list = new ArrayList<>();
        String query = "SELECT * FROM `PROVIDER` ORDER BY "+orderBY+" ASC ";
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
        while (resultSet.next()){
            Provider provider = new Provider();
            provider.setId(resultSet.getInt(           "ID_PROVIDER"));
            provider.setFirst_name(resultSet.getString("PROVIDER_FIRST_NAME"));
            provider.setLast_name(resultSet.getString( "PROVIDER_LAST_NAME"));
            provider.setPhone_number(resultSet.getString( "PROVIDER_PHONE_NUMBER"));
            provider.setAdress(resultSet.getString(    "PROVIDER_ADRESS"));
            provider.setJob(resultSet.getString("PROVIDER_JOB"));
            provider.setCreditor(resultSet.getString("PROVIDER_CREDITOR"));
            provider.setCreditor_to(resultSet.getString("PROVIDER_CREDITOR_TO"));
            list.add(provider);
        }
        return list;
    }


}
