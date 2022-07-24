package BddPackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Connet {
    Connection conn=null;
    public Connection connection()  {
        String database="";
        String root="";
        String modepasse="";

        database=readfile(new File(System.getProperty("user.dir") + "/BD/Database.txt"));
        root=readfile(new File(System.getProperty("user.dir") + "/BD/root.txt"));
        modepasse=readfile(new File(System.getProperty("user.dir") + "/BD/modepasse.txt"));


        String url = database;
        String user = root;
        String password = modepasse;
        String unicode= "?useUnicode=yes&characterEncoding=UTF-8";
        try {
            conn=null;
            conn = DriverManager.getConnection(url, user, password);

        } catch (SQLException throwables) {
            System.out.println("An error occurred. Maybe user/password is invalid");
            throwables.printStackTrace();
        }



        return conn;

    }
    public String readfile(File fileReader){
        String txt="";
        try { Scanner scanner = new Scanner( fileReader);
            String text = scanner.useDelimiter(";").next();
            txt=text;
            System.out.println(text);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return txt;
    }
    public void close(){
       try { conn.close(); } catch (SQLException ignore) {}
    }

}
