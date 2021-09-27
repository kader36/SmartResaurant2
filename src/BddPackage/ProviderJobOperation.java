package BddPackage;

import Models.ProviderJob;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProviderJobOperation extends BDD<ProviderJob> {
    @Override
    public boolean insert(ProviderJob o) {
        conn=connect();
        boolean ins = false;
        String query = "INSERT INTO `provider_job`(`TYPE`) VALUES (?)";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1,o.getName());
            int insert = preparedStmt.executeUpdate();
            if(insert != -1) ins = true;
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ins;
    }

    @Override
    public boolean update(ProviderJob o1, ProviderJob o2) {
        conn=connect();
        boolean upd = false;
        String query = "UPDATE `provider_job` SET `TYPE`=? WHERE `ID` = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1,o1.getName());
            preparedStmt.setInt(   2,o2.getId());
            int update = preparedStmt.executeUpdate();
            if(update != -1) upd = true;
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upd;    }

    @Override
    public boolean delete(ProviderJob o) {
        conn=connect();
        boolean del = false;
        String query = "DELETE FROM `provider_job` WHERE ID = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o.getId());
            int delete = preparedStmt.executeUpdate();
            if(delete != -1) del = true;
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return del;
    }

    @Override
    public boolean isExist(ProviderJob o) {
        return false;
    }

    @Override
    public ArrayList<ProviderJob> getAll() {
        conn=connect();
        ArrayList<ProviderJob> list = new ArrayList<>();
        String query = "SELECT * FROM `provider_job`";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){
                ProviderJob providerJob = new ProviderJob();
                providerJob.setId(resultSet.getInt("ID"));
                providerJob.setName(resultSet.getString("TYPE"));
                list.add(providerJob);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;

    }
}
