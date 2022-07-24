package BddPackage;

import Models.MoneyWithdrawal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MoneyWithdrawalOperationsBDD extends BDD<MoneyWithdrawal> {


    @Override
    public boolean insert(MoneyWithdrawal o) {
        conn=connect();
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
            conn.close();
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
        conn=connect();
        System.out.println("removed id is " + o.getDatabaseID());
        boolean queryResult = false;
        String query = "DELETE FROM `money_withdrawal_operations` WHERE `ID` = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, o.getDatabaseID());
            int delete = preparedStmt.executeUpdate();
            if (delete != -1) queryResult = true;
            conn.close();
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
        conn=connect();
        ArrayList<MoneyWithdrawal> operationsList = new ArrayList<>();
        String query;
        query = "SELECT * FROM `money_withdrawal_operations`";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();

            while (resultSet.next()) {
               MoneyWithdrawal moneyWithdrawal=new MoneyWithdrawal();
               moneyWithdrawal.setDatabaseID(resultSet.getInt("ID"));
                moneyWithdrawal.setUserName(resultSet.getString("USER_NAME"));
                moneyWithdrawal.setNote(resultSet.getString("NOTE"));
                moneyWithdrawal.setMoneyWithdrawn(resultSet.getDouble("MONEY_WITHDRAWN"));
                moneyWithdrawal.setDate(resultSet.getDate("DATE"));
                operationsList.add(moneyWithdrawal);
            }
        conn.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return operationsList;
    }
    public int getMonyDay(int day) {
        int mony=0;
        conn=connect();
        DateFormat dateFormat = new SimpleDateFormat("dd");
        DateFormat dateMonth = new SimpleDateFormat("MM");
        DateFormat dateYear = new SimpleDateFormat("yyyy");
        Date date = new Date();
        ArrayList<MoneyWithdrawal> list = new ArrayList<>();
        String query = "SELECT SUM(MONEY_WITHDRAWN) as 'MONEY'FROM money_withdrawal_operations WHERE DAY(`DATE`)=? and MONTH(`DATE`)=? and YEAR(`DATE`)=?";

        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, day);
            preparedStmt.setInt(2, Integer.parseInt(dateMonth.format(date)));
            preparedStmt.setInt(3, Integer.parseInt(dateYear.format(date)));
            ResultSet resultSet = preparedStmt.executeQuery();

            while (resultSet.next()) {
                mony=resultSet.getInt("MONEY");

            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mony;

    }
    public int getMonyMonth(int month) {
        int mony=0;
        conn=connect();
        DateFormat dateYear = new SimpleDateFormat("yyyy");
        Date date = new Date();

        String query = "SELECT SUM(MONEY_WITHDRAWN) as 'MONEY'FROM money_withdrawal_operations WHERE  MONTH(`DATE`)=? and YEAR(`DATE`)=?";

        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, month);
            preparedStmt.setInt(2, Integer.parseInt(dateYear.format(date)));
            ResultSet resultSet = preparedStmt.executeQuery();

            while (resultSet.next()) {
                mony=resultSet.getInt("MONEY");

            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mony;

    }
    public int getMonyYear(int year) {
        int mony=0;
        conn=connect();
        DateFormat dateYear = new SimpleDateFormat("yyyy");
        Date date = new Date();

        String query = "SELECT SUM(MONEY_WITHDRAWN) as 'MONEY'FROM money_withdrawal_operations WHERE  YEAR(`DATE`)=?";

        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, year);
            ResultSet resultSet = preparedStmt.executeQuery();

            while (resultSet.next()) {
                mony=resultSet.getInt("MONEY");

            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mony;

    }
}
