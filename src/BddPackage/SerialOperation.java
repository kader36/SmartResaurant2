package BddPackage;

import Controllers.Key;
import Models.Serial;

import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SerialOperation extends BDD<Serial>{
    @Override
    public boolean insert(Serial o) {
        return false;
    }

    @Override
    public boolean update(Serial o1, Serial o2) {
        return false;
    }

    @Override
    public boolean delete(Serial o) {
        return false;
    }

    @Override
    public boolean isExist(Serial o) {
        return false;
    }

    @Override
    public ArrayList<Serial> getAll() {
        return null;
    }
    public boolean update(Serial o1) {
        conn=connect();
        boolean upd = false;
        String query = "UPDATE `serial` SET `serialpc`=? WHERE id=1;";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, o1.getSerial());
            int update = preparedStmt.executeUpdate();
            if (update != -1) upd = true;
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upd;
    }
    public int checking()throws IOException {
        Key key=new Key();
        String macAdress=key.macAdress();

        try (
                Connection con = connect();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT  IFNULL(serialPc,''),`trialBegin`,`trial` From serial")) {
            if (rs.next()) {
                // cheking  if pass change go to creation
                if(rs.getString(1).equals(macAdress)){
                    //lunch nrml
                    return 0;
                }else{
                    // incorrect Pc
                    Date date=new Date();Date dateBeg=rs.getDate(2);Date Fin=rs.getDate(3);
                    if(date.compareTo(dateBeg)>=0 && date.compareTo(Fin)<0){
                        st.executeUpdate("UPDATE `serial` SET `TrialBegin`=CURRENT_DATE() WHERE id=1;");
                        return  1;
                    }else{
                        DateFormat dateYear = new SimpleDateFormat("yyyy-MM-dd");
                        if(Fin.toString().equals(dateYear.format(date).toString())){
                            st.executeUpdate("UPDATE `serial` SET `TrialBegin`=CURRENT_DATE() WHERE id=1;");
                        }


                        return -1;
                    }

                }
            }else{
                // creation serial page
                st.executeUpdate("INSERT INTO `serial`(`id`, `trialBegin`,`trial`) VALUES (1, CURRENT_DATE(),  CURRENT_DATE() + INTERVAL 7 DAY)");
                //securePWS();
                return 1;
            }


        } catch (SQLException e) {

            System.err.println(e);
            System.exit(1);
        }
        return 0;
    }
}
