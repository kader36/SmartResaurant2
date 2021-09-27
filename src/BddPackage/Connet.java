package BddPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connet {
    Connection conn=null;
    public Connection connection()  {

        // db parameters
        // localhost:3306/ResturantDB?useSSL=false
        String url = "jdbc:mysql://localhost/resaturentdb?useUnicode=yes&characterEncoding=UTF-8";
        String user = "root";
        String password = "";
        String unicode= "?useUnicode=yes&characterEncoding=UTF-8";
        try {
            conn=null;
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
    public void close(){
       try { conn.close(); } catch (SQLException ignore) {}
    }

}
