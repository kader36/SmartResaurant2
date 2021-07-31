package BddPackage;

import Models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserOperation extends BDD<User>{
    @Override
    public boolean insert(User o) {
        conn=connect();
        boolean ins = false;
        String query = "INSERT INTO `users`( `ID_EMPLOYER`, `USERNAME`, `PASSWORD`, `TYPE`)  \n" +
                "VALUES (?,?,?,?)";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);

            preparedStmt.setInt(1, o.getId_emloyer());
            preparedStmt.setString(2, o.getUserName());
            preparedStmt.setString(3, o.getPassWord());
            preparedStmt.setString(4, o.getType());
            int insert = preparedStmt.executeUpdate();
            if (insert != -1) ins = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ins;
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
        conn=connect();
        boolean exist=false;
        String query = "SELECT * FROM `users` WHERE `USERNAME`=?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1,o.getUserName());
            ResultSet resultSet = preparedStmt.executeQuery();
            if (resultSet.next()) exist = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exist;
    }

    @Override
    public ArrayList<User> getAll() {
        conn=connect();
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
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;

    }
    public boolean getUser(User o){
         conn=connect();
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
        }finally {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return exist;
    }
    public User getUserParUser(String username){
        conn=connect();
        User user=new User();
        String query = " SELECT * FROM `users` WHERE `USERNAME`=? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1,username);
            ResultSet resultSet = preparedStmt.executeQuery();


            while (resultSet.next()) {
                user.setId(resultSet.getInt("ID_USER"));
                user.setUserName(resultSet.getString("USERNAME"));
                user.setType(resultSet.getString("TYPE"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return user;
    }
}
