package Models;

public class Employer_Added_Value {
    private int Id_employer;
    private String Date_Added;
    private int value_added;

    public int getId_employer() {
        return Id_employer;
    }

    public void setId_employer(int id_employer) {
        Id_employer = id_employer;
    }

    public String getDate_Added() {
        return Date_Added;
    }

    public void setDate_Added(String date_Added) {
        Date_Added = date_Added;
    }

    public int getValue_added() {
        return value_added;
    }

    public void setValue_added(int value_added) {
        this.value_added = value_added;
    }
}
