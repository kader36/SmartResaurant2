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
                "`PROVIDER_EMAIL`,`PROVIDER_ADRESS`) \n" +
                "VALUES (?,?,?,?,?)";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1,o.getFirst_name());
            preparedStmt.setString(2,o.getLast_name());
            preparedStmt.setString(   3,o.getPhone_number());
            preparedStmt.setString(4,o.getEmail());
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
        String query = "UPDATE `PROVIDER` SET `PROVIDER_FIRST_NAME`= ?,`PROVIDER_LAST_NAME`=?,\n" +
                "    `PROVIDER_PHONE_NUMBER`=?,`PROVIDER_EMAIL`= ?,`PROVIDER_ADRESS`=? WHERE ID_PROVIDER = ?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1,o1.getFirst_name());
            preparedStmt.setString(2,o1.getLast_name());
            preparedStmt.setString(   3,o1.getPhone_number());
            preparedStmt.setString(4,o1.getEmail());
            preparedStmt.setString(5,o1.getAdress());
            preparedStmt.setInt(   6,o2.getId());
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
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){
                //SELECT `ID_PROVIDER`, `PROVIDER_FIRST_NAME`, `PROVIDER_LAST_NAME`, `PROVIDER_PHONE_NUMBER`,
                // `PROVIDER_EMAIL`, `PROVIDER_ADRESS` FROM `PROVIDER` WHERE 1
              Provider provider = new Provider();
              provider.setId(resultSet.getInt(           "ID_PROVIDER"));
              provider.setFirst_name(resultSet.getString("PROVIDER_FIRST_NAME"));
              provider.setLast_name(resultSet.getString( "PROVIDER_LAST_NAME"));
              provider.setPhone_number(resultSet.getString( "PROVIDER_PHONE_NUMBER"));
              provider.setEmail(resultSet.getString(     "PROVIDER_EMAIL"));
              provider.setAdress(resultSet.getString(    "PROVIDER_ADRESS"));
                list.add(provider);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;

    }
}
