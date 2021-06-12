package BddPackage;

import Models.MoneyWithdrawal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MoneyWithdrawlBDD extends BDD<MoneyWithdrawal> {


    @Override
    public boolean insert(MoneyWithdrawal o) {
        boolean operationResult  = false;
        String query = "INSERT INTO `money_withdrawal_operations`(`ID_ORDER`, `USER_NAME`,`MONEY_WITHDRAWN`,`DATE`,`NOTE`)" +
                " VALUES (?,?,?,?,?)";
        String id = "5";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1,id);
            preparedStmt.setString(2,o.getUserName());
            preparedStmt.setDouble(3,Double.parseDouble(o.getMoneyWithdrawn()));
            preparedStmt.setDate(4,o.getDate());
            preparedStmt.setString(5,o.getNote());
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
        return false;
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
                                resultSet.getString("MONEY_WITHDRAWN"),
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
