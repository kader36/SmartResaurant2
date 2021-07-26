package BddPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

abstract class BDD<Object> {

    Connection conn;


    public Connection connect(){
        conn=null;
        // db parameters
        // localhost:3306/ResturantDB?useSSL=false
        String url = "jdbc:mysql://localhost/resaturentdb";
        String user = "root";
        String password = "";
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
            if (conn != null) {
                System.out.println("Connected to the database");
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
