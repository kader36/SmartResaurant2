package BddPackage;

import Models.Unity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UnityOperation extends BDD<Unity>{
    @Override
    public boolean insert(Unity o) {
        conn=connect();
        boolean ins = false;
        String query = "INSERT INTO `Unitys`( `name`) VALUES (?) ;";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(   1,o.getName());

            int insert = preparedStmt.executeUpdate();
            if(insert != -1) ins = true;
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ins;
    }

    @Override
    public boolean update(Unity o1, Unity o2) {
        return false;
    }

    @Override
    public boolean delete(Unity o) {
        conn=connect();
        boolean del = false;
        String query = "DELETE FROM `Unitys` WHERE `id`=?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(   1,o.getId());
            int delete = preparedStmt.executeUpdate();
            if(delete != -1) del = true;
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return del;
    }

    @Override
    public boolean isExist(Unity o) {
        return false;
    }

    @Override
    public ArrayList<Unity> getAll() {
        conn=connect();
        ArrayList<Unity> list = new ArrayList<>();
        String query = "SELECT * FROM `Unitys`";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){
               Unity unity=new Unity();
               unity.setId(resultSet.getInt(1));
               unity.setName(resultSet.getString(2));
               list.add(unity);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
