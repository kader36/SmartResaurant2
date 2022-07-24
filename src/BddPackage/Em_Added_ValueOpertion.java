package BddPackage;

import Models.Employer_Added_Value;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Em_Added_ValueOpertion extends BDD<Employer_Added_Value> {
    @Override
    public boolean insert(Employer_Added_Value o) {
            conn=connect();
            boolean ins = false;
            String query = "INSERT INTO `Employer_Added_Value`(`ID_employer`, `Date_added`, `value_added`) VALUES  (?,?,?)";
            try {
                PreparedStatement preparedStmt = conn.prepareStatement(query);

                preparedStmt.setInt(1, o.getId_employer());
                preparedStmt.setString(2, o.getDate_Added());
                preparedStmt.setInt(3, o.getValue_added());
                int insert = preparedStmt.executeUpdate();
                if (insert != -1) ins = true;
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return ins;
    }

    @Override
    public boolean update(Employer_Added_Value o1, Employer_Added_Value o2) {
        return false;
    }

    @Override
    public boolean delete(Employer_Added_Value o) {
        return false;
    }

    @Override
    public boolean isExist(Employer_Added_Value o) {
        return false;
    }

    @Override
    public ArrayList<Employer_Added_Value> getAll() {
        return null;
    }
    public ArrayList<Employer_Added_Value> getAll(Employer_Added_Value o) {
        conn=connect();
        ArrayList<Employer_Added_Value> list=new ArrayList<>();
        String query = "SELECT * FROM `Employer_Added_Value` WHERE `ID_employer`=?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, o.getId_employer());
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
                Employer_Added_Value employer_added_value=new Employer_Added_Value();
                employer_added_value.setId_employer(resultSet.getInt("ID_employer"));
                employer_added_value.setDate_Added(resultSet.getString("Date_added"));
                employer_added_value.setValue_added(resultSet.getInt("value_added"));
                list.add(employer_added_value);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public ArrayList<Employer_Added_Value> getAdded_Value(Employer_Added_Value o){
        conn=connect();
        ArrayList<Employer_Added_Value> list=new ArrayList<>();
        String query = "SELECT * FROM `Employer_Added_Value` WHERE `ID_employer`=? and `Date_added`=? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, o.getId_employer());
            preparedStmt.setString(2, o.getDate_Added());
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
                Employer_Added_Value employer_added_value=new Employer_Added_Value();
                employer_added_value.setId_employer(resultSet.getInt("ID_employer"));
                employer_added_value.setDate_Added(resultSet.getString("Date_added"));
                employer_added_value.setValue_added(resultSet.getInt("value_added"));
                list.add(employer_added_value);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
