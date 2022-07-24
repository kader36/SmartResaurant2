package BddPackage;

import Models.Settings;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SettingsOperation extends BDD<Settings> {
    @Override
    public boolean insert(Settings o) {
        return false;
    }

    @Override
    public boolean update(Settings o1, Settings o2) {
        conn=connect();
        boolean upd = false;
        String query = "UPDATE `settings` SET `Name`=?,`PhoneNambe1`=?,`PhoneNambe2`=?,`Adress`=?,`Logo`=? WHERE `Id`=1";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, o1.getName());
            System.out.println(o1.getName());
            preparedStmt.setString(2, o1.getPhonenamber1());
            preparedStmt.setString(3, o1.getPhonenamber2());
            preparedStmt.setString(4, o1.getAdress());
            preparedStmt.setString(5, o1.getPathImage());
            int update = preparedStmt.executeUpdate();
            if (update != -1) upd = true;
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upd;
    }

    @Override
    public boolean delete(Settings o) {
        return false;
    }

    @Override
    public boolean isExist(Settings o) {
        return false;
    }

    @Override
    public ArrayList getAll() {
        return null;
    }
    public Settings get() {
        Settings settings=new Settings();
        String query = "SELECT * FROM `settings` where Id=1";
        conn=connect();
        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {

                settings.setName(resultSet.getString("Name"));
                settings.setPhonenamber1(resultSet.getString("PhoneNambe1"));
                settings.setPhonenamber2(resultSet.getString("PhoneNambe2"));
                settings.setAdress(resultSet.getString("Adress"));
                settings.setPathImage(resultSet.getString("Logo"));
            }
            conn.close();
        } catch (SQLException e) {
                e.printStackTrace();
        }

        return settings;
    }
}
