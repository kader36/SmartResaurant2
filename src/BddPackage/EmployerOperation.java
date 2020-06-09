package BddPackage;

import Models.Employer;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployerOperation extends BDD<Employer> {

    @Override
    public boolean insert(Employer o) {
        boolean ins = false;
        String query = "INSERT INTO `EMPLOYER`( `EMPLOYER_NAME`, `EMPLOYER_LAST_NAME`, `EMPLOYER_PHONE_NUMBER`,\n" +
                "`EMPLOYER_EMAIL`,`EMPLOYER_ADRESS`, `SALARY`) \n" +
                "VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1,o.getFirst_name());
            preparedStmt.setString(2,o.getLast_name());
            preparedStmt.setInt(   3,o.getPhone_number());
            preparedStmt.setString(4,o.getEmail());
            preparedStmt.setString(5,o.getAdress());
            preparedStmt.setInt(   6,o.getSalary());
            int insert = preparedStmt.executeUpdate();
            if(insert != -1) ins = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ins;
    }

    @Override
    public boolean update(Employer o1, Employer o2) {
        boolean upd;
        String query = "";
        return false;
    }

    @Override
    public boolean delete(Employer o) {
        return false;
    }

    @Override
    public boolean isExist(Employer o) {
        return false;
    }

    @Override
    public ArrayList<Employer> getAll() {
        return null;
    }
}
