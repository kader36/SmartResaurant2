package BddPackage;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

abstract class BDD<Object> {

    Connection conn;


    public Connection connect(){
        conn=null;
        String database="";
        String root="";
        String modepasse="";

        database=readfile(new File(System.getProperty("user.dir") + "/BD/Database.txt"));
        root=readfile(new File(System.getProperty("user.dir") + "/BD/root.txt"));
        modepasse=readfile(new File(System.getProperty("user.dir") + "/BD/modepasse.txt"));


        String url =database;
        String user = root;
        String password = modepasse;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return conn;

    }
    void close(){
        if (conn != null) try { conn.close(); } catch (SQLException ignore) {}
    }
    public String readfile(File fileReader){
        String txt="";
        try { Scanner scanner = new Scanner( fileReader);
            String text = scanner.useDelimiter(";").next();
            txt=text;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return txt;
    }

    abstract public boolean insert(Object o);

    abstract public boolean update(Object o1,Object o2);

    abstract public boolean delete(Object o);

    abstract public boolean isExist(Object o);

    abstract public ArrayList<Object> getAll();
}
