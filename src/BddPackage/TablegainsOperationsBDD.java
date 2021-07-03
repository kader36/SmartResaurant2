package BddPackage;

import Models.MoneyWithdrawal;
import Models.ProductCategory;
import Models.TableGainedMoney;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TablegainsOperationsBDD  extends BDD<TableGainedMoney>{


    long millis=System.currentTimeMillis();


    //@Override
    public boolean insertNewGain(TableGainedMoney o, double money) {

        boolean ins = false;
        String query = "INSERT INTO `table_gaines` (`ID_TABLE`, `DATE`, `GAINED_MONEY`) VALUES (?,?,?)";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o.getTableId());
            preparedStmt.setDate(2,new java.sql.Date(millis));
            preparedStmt.setDouble(3,money);
            int insert = preparedStmt.executeUpdate();
            if(insert != -1) ins = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ins;
    }


    @Override
    public boolean insert(TableGainedMoney o) {
        return false;
    }

    @Override
    public boolean update(TableGainedMoney o1, TableGainedMoney o2) {



        return false;
    }

    @Override
    public boolean delete(TableGainedMoney o) {
        return false;
    }

    @Override
    public boolean isExist(TableGainedMoney o) {
        return false;
    }

    @Override
    public ArrayList<TableGainedMoney> getAll() {
        return null;
    }
}
