package BddPackage;

import Models.Employer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployerOperation extends BDD<Employer> {

    @Override
    public boolean insert(Employer o) {
        conn=connect();
        boolean ins = false;
        String query = "INSERT INTO `employer`( `EMPLOYER_NAME`, `EMPLOYER_LAST_NAME`, `EMPLOYER_PHONE_NUMBER`,\n" +
                "`EMPLOYER_JOB`, `SALARY`) \n" +
                "VALUES (?,?,?,?,?)";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1,o.getFirst_name());
            preparedStmt.setString(2,o.getLast_name());
            preparedStmt.setString(   3,o.getPhone_number());
            preparedStmt.setString(4,o.getJob());
            preparedStmt.setInt(   5,o.getSalary());
            int insert = preparedStmt.executeUpdate();
            if(insert != -1) ins = true;
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ins;
    }

    @Override
    public boolean update(Employer o1, Employer o2) {
        conn=connect();
        boolean upd = false;
        String query = "UPDATE `employer` SET`EMPLOYER_NAME`=?,`EMPLOYER_LAST_NAME`=?,`EMPLOYER_PHONE_NUMBER`=?,`EMPLOYER_JOB`=?,`SALARY`=? \n" +
                "WHERE `ID_EMPLOYER`=?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, o1.getFirst_name());
            preparedStmt.setString(2, o1.getLast_name());
            preparedStmt.setString(3, o1.getPhone_number());
            preparedStmt.setString(4, o1.getJob());
            preparedStmt.setInt(5, o1.getSalary());
            preparedStmt.setInt(6, o2.getId());
            int update = preparedStmt.executeUpdate();
            if (update != -1) upd = true;
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upd;
    }

    @Override
    public boolean delete(Employer o) {
        conn=connect();
        boolean del = false;
        String query = "DELETE FROM `employer` WHERE `ID_EMPLOYER`=?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, o.getId());
            int delete = preparedStmt.executeUpdate();
            if (delete != -1) del = true;
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return del;
    }

    @Override
    public boolean isExist(Employer o) {
        return false;
    }

    @Override
    public ArrayList<Employer> getAll() {
        conn=connect();
        ArrayList<Employer> list = new ArrayList<>();
        String query = "SELECT * FROM `employer`";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
               Employer employer=new Employer();
                employer.setId(resultSet.getInt("ID_EMPLOYER"));
                employer.setFirst_name(resultSet.getString("EMPLOYER_NAME"));
                employer.setLast_name(resultSet.getString("EMPLOYER_LAST_NAME"));
                employer.setPhone_number(resultSet.getString("EMPLOYER_PHONE_NUMBER"));
                employer.setJob(resultSet.getString("EMPLOYER_JOB"));
                employer.setSalary(resultSet.getInt("SALARY"));
                employer.setWork_strat(resultSet.getDate("EMPLOYER_WORK_START"));
                employer.setWork_end(resultSet.getDate("EMPLOYER_WORK_END"));

                list.add(employer);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
