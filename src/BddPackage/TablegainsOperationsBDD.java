package BddPackage;

import Models.TableGainedMoney;
import Models.Tables;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
    public TableGainedMoney getElement(Tables o){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd ");
        Date date = new Date();
        TableGainedMoney tableGainedMoney=new TableGainedMoney();
        String query = "SELECT sum(GAINED_MONEY) FROM `table_gaines` WHERE `DATE`=? and `ID_TABLE`=?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setDate(1,new java.sql.Date(millis));
            System.out.println(new java.sql.Date(millis));
            System.out.println(date);
            preparedStmt.setInt(2,o.getId());
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){

                tableGainedMoney.setGainedMoney(resultSet.getDouble(1));
                tableGainedMoney.setTableId(o.getId());

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableGainedMoney;
    }
}
