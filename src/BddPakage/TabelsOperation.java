package BddPakage;

import Models.Tables;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TabelsOperation extends BDD<Tables> {
    @Override
    public boolean insert(Tables o) {

            boolean ins = false;
            String query = "INSERT INTO `TABLES` (`TABLE_NUMBER`) VALUES (?);";
            try {
                PreparedStatement preparedStmt = conn.prepareStatement(query);
                preparedStmt.setInt(   1,o.getNumber());
                int insert = preparedStmt.executeUpdate();
                if(insert != -1) ins = true;
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return ins;
    }

    @Override
    public boolean update(Tables o1, Tables o2) {
        boolean upd = false;
        String query = "UPDATE `TABLES` SET `TABLE_NUMBER`=?  WHERE `ID_TABLE` = ?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(   1,o1.getNumber());
            preparedStmt.setInt(   2,o2.getId());
            int update = preparedStmt.executeUpdate();
            if(update != -1) upd = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return upd;
    }

    @Override
    public boolean delete(Tables o) {
        boolean del = false;
        String query = "DELETE FROM `TABLES` WHERE ID_TABLE = ?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(   1,o.getId());
            int delete = preparedStmt.executeUpdate();
            if(delete != -1) del = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return del;
    }

    @Override
    public boolean isExist(Tables o) {
     boolean exist = false;
     String query = "SELECT * FROM `TABLES` WHERE `ID_TABLE` = ?";

        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o.getNumber());
            ResultSet resultSet = preparedStmt.executeQuery();
            if (resultSet.next()) exist = true;
            System.out.println(exist);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exist;
    }

    @Override
    public ArrayList<Tables> getAll() {
        ArrayList<Tables> list = new ArrayList<>();
        String query = "SELECT * FROM `TABLES`";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){
               Tables tables = new Tables(resultSet.getInt(1),resultSet.getInt(2),resultSet.getString(3));
                list.add(tables);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
