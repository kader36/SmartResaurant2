package BddPackage;

import Models.MoneyWithdrawal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MoneyWithdrawalOperationsBDD extends BDD<MoneyWithdrawal> {


    @Override
    public boolean insert(MoneyWithdrawal o) {
        boolean operationResult  = false;
        String query = "INSERT INTO `money_withdrawal_operations`( `USER_NAME`,`MONEY_WITHDRAWN`,`DATE`,`NOTE`)" +
                " VALUES (?,?,?,?)";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1,o.getUserName());
            preparedStmt.setDouble(2,o.getMoneyWithdrawn());
            preparedStmt.setDate(3,o.getDate());
            preparedStmt.setString(4,o.getNote());
            int insert = preparedStmt.executeUpdate();
            if(insert != -1) operationResult = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(MoneyWithdrawal o1, MoneyWithdrawal o2) {
        return false;
    }

    @Override
    public boolean delete(MoneyWithdrawal o) {

        System.out.println("removed id is " + o.getDatabaseID());
        boolean queryResult = false;
        String query = "DELETE FROM `money_withdrawal_operations` WHERE `ID` = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, o.getDatabaseID());
            int delete = preparedStmt.executeUpdate();
            if (delete != -1) queryResult = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return queryResult;
    }

    @Override
    public boolean isExist(MoneyWithdrawal o) {
        return false;
    }

    @Override
    public ArrayList<MoneyWithdrawal> getAll() {

        ArrayList<MoneyWithdrawal> operationsList = new ArrayList<>();
        String query;
        query = "SELECT * FROM `money_withdrawal_operations`";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();

            while (resultSet.next()) {
                operationsList.add(
                        new MoneyWithdrawal(

                                resultSet.getString("USER_NAME"),
                                resultSet.getDouble("MONEY_WITHDRAWN"),
                                resultSet.getDate("DATE"),
                                resultSet.getString("NOTE")
                        )
                );
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return operationsList;
    }
}
