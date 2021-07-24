package BddPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

abstract class BDD<Object> {

    Connection conn=null;

    BDD() {
        connect();
    }

    public Connection connect(){

        // db parameters
        // localhost:3306/ResturantDB?useSSL=false
        String url = "jdbc:mysql://localhost/resaturentdb";
        String user = "root";
        String password = "";
        String unicode= "?useUnicode=yes&characterEncoding=UTF-8";
        try {

            conn = DriverManager.getConnection(url, user, password);
            if (conn != null) {
                System.out.println("Connected to the database");
            }
        } catch (SQLException throwables) {
            System.out.println("An error occurred. Maybe user/password is invalid");
            throwables.printStackTrace();
        }

        return conn;

    }
    void close(){
        if (conn != null) try { conn.close(); } catch (SQLException ignore) {}
    }

    abstract public boolean insert(Object o);

    abstract public boolean update(Object o1,Object o2);

    abstract public boolean delete(Object o);

    abstract public boolean isExist(Object o);

    abstract public ArrayList<Object> getAll();
}
