package BddPackage;

import Models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserOperation extends BDD<User>{
    @Override
    public boolean insert(User o) {
        return false;
    }

    @Override
    public boolean update(User o1, User o2) {
        return false;
    }

    @Override
    public boolean delete(User o) {
        return false;
    }

    @Override
    public boolean isExist(User o) {
        return false;
    }

    @Override
    public ArrayList<User> getAll() {
        ArrayList<User> list = new ArrayList<>();
        String query = "SELECT * FROM `users`,employer WHERE users.ID_EMPLOYER=employer.ID_EMPLOYER";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
                User user=new User();

                user.setUserName(resultSet.getString("USERNAME"));
                user.setEmloyer_name(resultSet.getString("EMPLOYER_NAME")+" "+resultSet.getString("EMPLOYER_LAST_NAME"));
                user.setType(resultSet.getString("TYPE"));

                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;

    }
    public boolean getUser(User o){
       boolean exist=false;
        String query = " SELECT * FROM `users` WHERE `USERNAME`=? and `PASSWORD`=?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1,o.getUserName());
            preparedStmt.setString(2,o.getPassWord());
            ResultSet resultSet = preparedStmt.executeQuery();
            if (resultSet.next()) exist = true;
            System.out.println(exist);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exist;
    }
}
