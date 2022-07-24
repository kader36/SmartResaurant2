package BddPackage;
import Models.Room;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoomOpertion extends BDD<Room> {
    @Override
    public boolean insert(Room o) {
        conn=connect();
        boolean ins = false;
        String query = "INSERT INTO `Room`(`Id`, `room`) VALUES (?,?)";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, o.getID());
            preparedStmt.setString(2, o.getName());

            int insert = preparedStmt.executeUpdate();
            if (insert != -1) ins = true;
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ins;
    }

    @Override
    public boolean update(Room o1, Room o2) {
        return false;
    }

    @Override
    public boolean delete(Room o) {
        conn=connect();
        boolean del = false;
        String query = "DELETE FROM `Room` WHERE `Id`= ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, o.getID());
            int delete = preparedStmt.executeUpdate();
            if (delete != -1) del = true;
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return del;
    }

    @Override
    public boolean isExist(Room o) {
        return false;
    }

    @Override
    public ArrayList<Room> getAll() {
        ArrayList<Room> list = new ArrayList<>();
        String query = "SELECT * FROM `Room`";
        try {

            chargeData(list, query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    private ArrayList<Room> chargeData(ArrayList<Room> list, String query) throws SQLException {
        conn=connect();
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        ResultSet resultSet = preparedStmt.executeQuery();
        while (resultSet.next()) {
            Room room = new Room();
            room.setID(resultSet.getInt("Id"));
            room.setName(resultSet.getString("room"));
            list.add(room);
        }
        conn.close();
        return list;
    }
}
