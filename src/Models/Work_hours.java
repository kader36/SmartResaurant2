package Models;

import BddPackage.Em_Added_ValueOpertion;

import java.util.ArrayList;

public class Work_hours {
    private int ID_employer;
    private String Date_work;
    private String Attendance;
    private int Valuer;
    private String Pay;
    private int Added_Value=0;
    private String PayNow;

    public int getID_employer() {
        return ID_employer;
    }

    public void setID_employer(int ID_employer) {
        this.ID_employer = ID_employer;
    }

    public String getDate_work() {
        return Date_work;
    }

    public void setDate_work(String date_work) {
        Date_work = date_work;
    }

    public String getAttendance() {
        return Attendance;

    }

    public void setAttendance(String attendance) {
       this.Attendance=attendance;
    }

    public int getValuer() {
        return Valuer;
    }

    public void setValuer(int valuer) {
        Valuer = valuer;
    }

    public String getPay() {
        return Pay;
    }

    public void setPay(String pay) {
        if(this.Valuer>0)
        Pay = String.valueOf(getValuer());
        else Pay="--";
        ArrayList<Employer_Added_Value> list=new ArrayList<>();
        Employer_Added_Value em=new Employer_Added_Value();
        em.setId_employer(ID_employer);
        em.setDate_Added(Date_work);
        Em_Added_ValueOpertion em_added_valueOpertion=new Em_Added_ValueOpertion();
        list=em_added_valueOpertion.getAdded_Value(em);
        for (int i=0;i< list.size();i++)
            Added_Value+=list.get(i).getValue_added();
    }

    public int getAdded_Value() {
        return Added_Value;
    }

    public void setAdded_Value(int added_Value) {
        Added_Value = added_Value;
    }

    public String getPayNow() {
        return PayNow;
    }

    public void setPayNow(String payNow) {
        PayNow = payNow;
    }
}
