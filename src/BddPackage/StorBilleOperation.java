package BddPackage;

import Controllers.ValuesStatic;
import Models.StoreBill;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class StorBilleOperation extends BDD<StoreBill> {
    @Override
    public boolean insert(StoreBill o) {
        conn=connect();
        boolean ins = false;
        String query = "INSERT INTO `store_bill`( `ID_USER_OPERATION`, `ID_PROVIDER_OPERATION`, `PAID_UP`)\n" +
                "VALUES (?,?,?)";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, o.getId_user());
            preparedStmt.setInt(2, o.getId_provider());
            preparedStmt.setInt(3, o.getPaid_up());
            int insert = preparedStmt.executeUpdate();
            if (insert != -1) ins = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ins;
    }

    @Override
    public boolean update(StoreBill o1, StoreBill o2) {
        conn=connect();
        boolean upd = false;
        String query = "UPDATE `store_bill` SET `ID_USER_OPERATION`=?,`ID_PROVIDER_OPERATION`=?,`PAID_UP`=? WHERE `ID_STORE_BILL` = ?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, o1.getId_user());
            preparedStmt.setInt(2, o1.getId_provider());
            preparedStmt.setInt(3, o1.getPaid_up());
            preparedStmt.setInt(4, o2.getId());
            int update = preparedStmt.executeUpdate();
            if (update != -1) upd = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upd;
    }

    @Override
    public boolean delete(StoreBill o) {
        conn=connect();
        boolean del = false;
        String query = "DELETE FROM `store_bill` WHERE ID_STORE_BILL = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, o.getId());
            int delete = preparedStmt.executeUpdate();
            if (delete != -1) del = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("value of del : " + del);
        return del;
    }

    @Override
    public boolean isExist(StoreBill o) {
        return false;
    }

    @Override
    public ArrayList<StoreBill> getAll() {
        conn=connect();
        ArrayList<StoreBill> list = new ArrayList<>();
        String query = "SELECT * FROM `store_bill`";
        //SELECT `ID_STORE_BILL`, `STORE_BILL_DATE`, `ID_USER_OPERATION`, `ID_PROVIDER_OPERATION`,
        // `PAID_UP` FROM `STORE_BILL` WHERE 1
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            ValuesStatic.factToday = 0;
            ValuesStatic.factWeek = 0;
            ValuesStatic.factMonth = 0;
            ValuesStatic.credetorToday = 0;
            ValuesStatic.credetorWeek = 0;
            ValuesStatic.credetorMonth = 0;

            while (resultSet.next()) {
                StoreBill storeBill = new StoreBill();
                storeBill.setId(resultSet.getInt("ID_STORE_BILL"));
                storeBill.setDate(resultSet.getDate("STORE_BILL_DATE"));
                storeBill.setId_user(resultSet.getInt("ID_USER_OPERATION"));
                storeBill.setId_provider(resultSet.getInt("ID_PROVIDER_OPERATION"));
                storeBill.setPaid_up(resultSet.getInt("PAID_UP"));
               // storeBill.setTotal(resultSet.getInt("TOTAL"));
                list.add(storeBill);
                if (storeBill.getDate().toString().equals(LocalDate.now().toString())) {
                    ValuesStatic.factToday++;
                    ValuesStatic.credetorToday += storeBill.getTotal() - storeBill.getPaid_up();
                }
//                System.out.println("date today : " + ValuesStatic.inMonth());
//                System.out.println("date of fact : " + storeBill.getDate().toString());
                if (storeBill.getDate().toString().contains(ValuesStatic.inMonth())) {
                    ValuesStatic.factMonth++;
                    ValuesStatic.credetorMonth += storeBill.getTotal() - storeBill.getPaid_up();
                }
                LocalDate date = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth());
                int weekOfYear = date.get(WeekFields.of(Locale.FRANCE).weekOfYear());
                date = LocalDate.of(Integer.parseInt(ValuesStatic.getYear(storeBill.getDate().toString())), Integer.parseInt(ValuesStatic.getMonth(storeBill.getDate().toString())), Integer.parseInt(ValuesStatic.getDay(storeBill.getDate().toString())));
                System.out.println("date is : " + Integer.parseInt(ValuesStatic.getYear(storeBill.getDate().toString())) + " " + Integer.parseInt(ValuesStatic.getMonth(storeBill.getDate().toString())) + " " + Integer.parseInt(ValuesStatic.getDay(storeBill.getDate().toString())));
                int weekOfFact = date.get(WeekFields.of(Locale.FRANCE).weekOfYear());
                if (weekOfYear == weekOfFact && LocalDate.now().getYear() == Integer.parseInt(ValuesStatic.getYear(storeBill.getDate().toString()))) {
                    ValuesStatic.factWeek++;
                    ValuesStatic.credetorWeek += storeBill.getTotal() - storeBill.getPaid_up();
                }
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public int getIdStorBill(int idProvider, int idUser, int paid) {
        conn=connect();
        String query = "SELECT * FROM `store_bill`";
        //SELECT `ID_STORE_BILL`, `STORE_BILL_DATE`, `ID_USER_OPERATION`, `ID_PROVIDER_OPERATION`,
        // `PAID_UP` FROM `STORE_BILL` WHERE 1
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
//                System.out.println("values of parametre : " + idProvider + " " + idUser + " " + paid);
//                System.out.println("values of data : " + resultSet.getInt("ID_PROVIDER_OPERATION") + " " + resultSet.getInt("ID_USER_OPERATION") + " " + resultSet.getInt("PAID_UP"));
                if (idProvider == resultSet.getInt("ID_PROVIDER_OPERATION") && idUser == resultSet.getInt("ID_USER_OPERATION") && paid == resultSet.getInt("PAID_UP")) {
                    //System.out.println("values of data : " + resultSet.getInt("ID_PROVIDER_OPERATION") + " " + resultSet.getInt("ID_USER_OPERATION") + " " + resultSet.getInt("PAID_UP"));
                    return resultSet.getInt("ID_STORE_BILL");
                }


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getCountStoreBill() {
        conn=connect();
        int total = 0;
        String query = "SELECT COUNT(*) AS total FROM store_bill";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next())
                total = resultSet.getInt("total");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    public ArrayList<StoreBill> getAllBy(String orderBY) {
        ArrayList<StoreBill> list = new ArrayList<>();
        String query = "SELECT * FROM `store_bill` ORDER BY " + orderBY + " ASC ";
        try {

            chargeData(list, query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private ArrayList<StoreBill> chargeData(ArrayList<StoreBill> list, String query) throws SQLException {
        conn=connect();
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        ResultSet resultSet = preparedStmt.executeQuery();
        while (resultSet.next()) {
            StoreBill storeBill = new StoreBill();
            storeBill.setId(resultSet.getInt("ID_STORE_BILL"));
            storeBill.setDate(resultSet.getDate("STORE_BILL_DATE"));
            storeBill.setId_user(resultSet.getInt("ID_USER_OPERATION"));
            storeBill.setId_provider(resultSet.getInt("ID_PROVIDER_OPERATION"));
            storeBill.setPaid_up(resultSet.getInt("PAID_UP"));
            list.add(storeBill);
        }
        return list;
    }
    public int getBillParDy(int day) {
        int Paid=0;
        conn=connect();
        DateFormat dateFormat = new SimpleDateFormat("dd");
        DateFormat dateMonth = new SimpleDateFormat("MM");
        DateFormat dateYear = new SimpleDateFormat("yyyy");
        Date date = new Date();
        ArrayList<StoreBill> list = new ArrayList<>();
        String query = "SELECT SUM(`PAID_UP`) as 'PAID'\n" +
                "FROM `store_bill` \n" +
                "WHERE DAY(`STORE_BILL_DATE`)=? \n" +
                "and MONTH(`STORE_BILL_DATE`)=?\n" +
                "and YEAR(`STORE_BILL_DATE`)=?";

        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, day);
            preparedStmt.setInt(2, Integer.parseInt(dateMonth.format(date)));
            preparedStmt.setInt(3, Integer.parseInt(dateYear.format(date)));
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
                Paid=resultSet.getInt("PAID");
                }
            conn.close();
            } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return Paid;
    }
    public int getBillParMonth(int month) {
        int Paid=0;
        conn=connect();
        DateFormat dateFormat = new SimpleDateFormat("dd");
        DateFormat dateMonth = new SimpleDateFormat("MM");
        DateFormat dateYear = new SimpleDateFormat("yyyy");
        Date date = new Date();
        ArrayList<StoreBill> list = new ArrayList<>();
        String query = "SELECT SUM(`PAID_UP`) as 'PAID'\n" +
                "FROM `store_bill` \n" +
                "WHERE  MONTH(`STORE_BILL_DATE`)=?\n" +
                "and YEAR(`STORE_BILL_DATE`)=?";

        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);

            preparedStmt.setInt(1, month);
            preparedStmt.setInt(2, Integer.parseInt(dateYear.format(date)));
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
                Paid=resultSet.getInt("PAID");
            }
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return Paid;
    }
    public int getBillParYear(int year) {
        int Paid=0;
        conn=connect();
        DateFormat dateFormat = new SimpleDateFormat("dd");
        DateFormat dateMonth = new SimpleDateFormat("MM");
        DateFormat dateYear = new SimpleDateFormat("yyyy");
        Date date = new Date();
        ArrayList<StoreBill> list = new ArrayList<>();
        String query = "SELECT SUM(`PAID_UP`) as 'PAID'\n" +
                "FROM `store_bill` \n" +
                "WHERE  YEAR(`STORE_BILL_DATE`)=?";

        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, year);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
                Paid=resultSet.getInt("PAID");
            }
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return Paid;
    }
}
