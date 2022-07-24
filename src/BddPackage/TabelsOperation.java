package BddPackage;

import Models.Tables;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TabelsOperation extends BDD<Tables> {
    @Override
    public boolean insert(Tables o) {
            conn=connect();
            boolean ins = false;
            String query = "INSERT INTO `tables` (ID_TABLE,`TABLE_NUMBER`,`ID_room`) VALUES (?,?,?);";
            try {
                PreparedStatement preparedStmt = conn.prepareStatement(query);
                preparedStmt.setInt(   1,o.getNumber());
                preparedStmt.setInt(   2,o.getNumber());
                preparedStmt.setInt(   3,1);
                int insert = preparedStmt.executeUpdate();
                if(insert != -1) ins = true;
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return ins;
    }

    @Override
    public boolean update(Tables o1, Tables o2) {
        conn=connect();
        boolean upd = false;
        String query = "UPDATE `tables` SET `TABLE_NUMBER`=?  WHERE `ID_TABLE` = ?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(   1,o1.getNumber());
            preparedStmt.setInt(   2,o2.getId());
            int update = preparedStmt.executeUpdate();
            if(update != -1) upd = true;
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return upd;
    }
    public boolean update(Tables o1) {
        conn=connect();
        boolean upd = false;
        String query = "UPDATE `tables` SET `ID_Order`=? WHERE `ID_TABLE`=?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(   1,o1.getOrderId());
            preparedStmt.setInt(   2,o1.getId());
            int update = preparedStmt.executeUpdate();
            if(update != -1) upd = true;
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return upd;
    }

    @Override
    public boolean delete(Tables o) {
        conn=connect();
        boolean del = false;
        String query = "DELETE FROM `tables` WHERE ID_TABLE = ?";
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
    public boolean isExist(Tables o) {
        conn=connect();
     boolean exist = false;
     String query = "SELECT * FROM `tables` WHERE `TABLE_NUMBER` = ?";

        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o.getNumber());
            ResultSet resultSet = preparedStmt.executeQuery();
            if (resultSet.next()) exist = true;
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exist;
    }

    @Override
    public ArrayList<Tables> getAll() {
        conn=connect();
        ArrayList<Tables> list = new ArrayList<>();
        String query = "SELECT * FROM `tables`";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){
               Tables tables = new Tables(resultSet.getInt(1),resultSet.getInt(2),resultSet.getString(4));
                tables.setBloquer(resultSet.getString(3));
                list.add(tables);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public Tables getTable(int id){
        conn=connect();
        Tables tables=new Tables();
        String query = "SELECT * FROM `tables`WHERE `ID_TABLE`=?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,id);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){
                 tables.setId(resultSet.getInt(1));
                tables.setNumber(resultSet.getInt(2));
                tables.setBloquer(resultSet.getString(3));
                tables.setActive(resultSet.getString(4));


            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tables;
    }
    public boolean ActivTabel(Tables o){
        conn=connect();
        boolean upd = false;
        String query = "UPDATE `tables` SET `TABLE_ACTIVE`=?  WHERE `ID_TABLE` = ?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(   1,"true");
            preparedStmt.setInt(   2,o.getId());
            int update = preparedStmt.executeUpdate();
            if(update != -1) upd = true;
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return upd;
    }
    public boolean desActivTabel(Tables o){
        conn=connect();
        boolean upd = false;
        String query = "UPDATE `tables` SET `TABLE_ACTIVE`=?  WHERE `ID_TABLE` = ?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(   1,"false");
            preparedStmt.setInt(   2,o.getId());
            int update = preparedStmt.executeUpdate();
            if(update != -1) upd = true;
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return upd;
    }
    public boolean ActivOrderTabel(Tables o){
        conn=connect();
        boolean upd = false;
        String query = "UPDATE `tables` SET `Order_Active`=?  WHERE `ID_TABLE` = ?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(   1,"true");
            preparedStmt.setInt(   2,o.getId());
            int update = preparedStmt.executeUpdate();
            if(update != -1) upd = true;
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return upd;
    }
    public boolean desActivOrderTabel(Tables o){
        conn=connect();
        boolean upd = false;
        String query = "UPDATE `tables` SET `Order_Active`=?  WHERE `ID_TABLE` = ?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(   1,"false");
            preparedStmt.setInt(   2,o.getId());
            int update = preparedStmt.executeUpdate();
            if(update != -1) upd = true;
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return upd;
    }
    public ArrayList<Tables> getAllActive() {
        conn=connect();
        ArrayList<Tables> list = new ArrayList<>();
        String query = "SELECT * FROM `tables` WHERE `TABLE_ACTIVE`=true";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){
                Tables tables = new Tables(resultSet.getInt(1),resultSet.getInt(2),resultSet.getString(4));
                tables.setBloquer(resultSet.getString(3));
                tables.setOrderActive(resultSet.getString(5));
                list.add(tables);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public String Tabel(int o){
        conn=connect();
        String upd = "";
        String query = "SELECT `TABLE_ACTIVE` FROM `tables` WHERE `ID_TABLE` = ?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(   1,o);
            ResultSet resultSet = preparedStmt.executeQuery();
            Tables tables=new Tables();
            while (resultSet.next()){
              tables.setActive(resultSet.getString(1));
            }
            upd=tables.getActive();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return upd;
    }
    public int getIdOrder(Tables o){
        conn=connect();
        int  upd = 0;
        String query = "SELECT ID_Order FROM `tables` WHERE `ID_TABLE` = ?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(   1,o.getId());
            ResultSet resultSet = preparedStmt.executeQuery();
            Tables tables=new Tables();
            while (resultSet.next()){
                tables.setOrderId(resultSet.getInt(1));
            }
            upd=tables.getOrderId();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return upd;
    }
}
