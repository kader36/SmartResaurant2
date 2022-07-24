package BddPackage;

import Models.Work_hours;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Work_hoursOpertion extends BDD<Work_hours>{
    @Override
    public boolean insert(Work_hours o) {
        conn=connect();
        boolean ins = false;
        String query = "INSERT INTO `Work_hours`(`ID_employer`, `Date_work`, `Attendance`,Pay,valus) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);

            preparedStmt.setInt(1, o.getID_employer());
            preparedStmt.setString(2, o.getDate_work());
            preparedStmt.setString(3, o.getAttendance());
            preparedStmt.setString(4, o.getPay());
            preparedStmt.setInt(5, o.getValuer());
            int insert = preparedStmt.executeUpdate();
            if (insert != -1) ins = true;
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ins;
    }

    @Override
    public boolean update(Work_hours o1, Work_hours o2) {
        conn=connect();
        boolean upd = false;
        String query = "UPDATE `Work_hours` SET `Attendance`=? WHERE `ID_employer`=? and `Date_work`=?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, o1.getAttendance());
            preparedStmt.setInt(2, o2.getID_employer());
            preparedStmt.setString(3, o2.getDate_work());
            int update = preparedStmt.executeUpdate();
            if (update != -1) upd = true;
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upd;
    }
    public boolean update(Work_hours o1) {
        conn=connect();
        boolean upd = false;
        String query = "UPDATE `Work_hours` SET `Pay`=? ,valus=? WHERE `ID_employer`=? and `Date_work`=?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, o1.getPayNow());
            preparedStmt.setInt(2, o1.getValuer());
            preparedStmt.setInt(3, o1.getID_employer());
            preparedStmt.setString(4, o1.getDate_work());
            int update = preparedStmt.executeUpdate();
            if (update != -1) upd = true;
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upd;
    }

    @Override
    public boolean delete(Work_hours o) {
        return false;
    }

    @Override
    public boolean isExist(Work_hours o) {
        conn=connect();
        boolean exist=false;
        String query = "SELECT * FROM `Work_hours` WHERE `ID_employer`=? and`Date_work`=?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o.getID_employer());
            preparedStmt.setString(2,o.getDate_work());
            ResultSet resultSet = preparedStmt.executeQuery();
            if (resultSet.next()) exist = true;
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exist;
    }

    public Work_hours getwork_hours(Work_hours o) {
        conn=connect();
        boolean exist=false;
        Work_hours work_hours=new Work_hours();
        String query = "SELECT * FROM `Work_hours` WHERE `ID_employer`=? and`Date_work`=?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o.getID_employer());
            preparedStmt.setString(2,o.getDate_work());
            ResultSet resultSet = preparedStmt.executeQuery();

            while (resultSet.next()) {

                work_hours.setID_employer(resultSet.getInt("ID_employer"));
                work_hours.setDate_work(resultSet.getString("Date_work"));
                work_hours.setAttendance(resultSet.getString("Attendance"));
                work_hours.setPay(resultSet.getString("Pay"));
                work_hours.setPayNow(resultSet.getString("Pay"));


            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return work_hours;
    }

    @Override
    public ArrayList<Work_hours> getAll() {
        conn=connect();
        boolean exist=false;
        ArrayList<Work_hours>list=new ArrayList<>();
        String query = "SELECT * FROM `Work_hours` ORDER BY `Work_hours`.`Date_work` DESC ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();

            while (resultSet.next()) {
                Work_hours work_hours=new Work_hours();
                work_hours.setID_employer(resultSet.getInt("ID_employer"));
                work_hours.setDate_work(resultSet.getString("Date_work"));
                work_hours.setValuer(resultSet.getInt("valus"));
                work_hours.setPay(resultSet.getString("Pay"));
                work_hours.setPayNow(resultSet.getString("Pay"));


            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public ArrayList<Work_hours> getAllWork(Work_hours o) {
        conn=connect();
        boolean exist=false;
        ArrayList<Work_hours>list=new ArrayList<>();
        String query = "SELECT * FROM `Work_hours` WHERE `ID_employer`=? ORDER BY `Work_hours`.`Date_work` DESC";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o.getID_employer());
            ResultSet resultSet = preparedStmt.executeQuery();

            while (resultSet.next()) {
                Work_hours work_hours=new Work_hours();
                work_hours.setID_employer(resultSet.getInt("ID_employer"));
                work_hours.setDate_work(resultSet.getString("Date_work"));
                work_hours.setAttendance(resultSet.getString("Attendance"));
                work_hours.setValuer(resultSet.getInt("valus"));
                work_hours.setPay(resultSet.getString("Pay"));
                work_hours.setPayNow(resultSet.getString("Pay"));

                list.add(work_hours);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public ArrayList<Work_hours> getAllWorkdate(String date1, String date2, int id) {
        conn=connect();
        boolean exist=false;
        ArrayList<Work_hours>list=new ArrayList<>();
        String query = "SELECT * FROM `Work_hours` WHERE `Date_work`BETWEEN ? and ? and `ID_employer`=? ORDER BY `Work_hours`.`Date_work` DESC;";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1,date1);
            preparedStmt.setString(2,date2);
            preparedStmt.setInt(3,id);
            ResultSet resultSet = preparedStmt.executeQuery();

            while (resultSet.next()) {
                Work_hours work_hours=new Work_hours();
                work_hours.setID_employer(resultSet.getInt("ID_employer"));
                work_hours.setDate_work(resultSet.getString("Date_work"));
                work_hours.setAttendance(resultSet.getString("Attendance"));
                work_hours.setValuer(resultSet.getInt("valus"));
                work_hours.setPay(resultSet.getString("Pay"));
                work_hours.setPayNow(resultSet.getString("Pay"));

                list.add(work_hours);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
