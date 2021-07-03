package Models;

public class Provider {
    private int id;
    private String first_name;
    private String last_name;
    private String  phone_number;
    private String job;
    private String adress;
    private double creditor;
    private String creditor_to;

    public Provider() {
    }

    public Provider(int id, String first_name, String last_name, String phone_number, String job, String adress, double creditor, String creditor_to) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone_number = phone_number;
        this.job = job;
        this.adress = adress;
        this.creditor = creditor;
        this.creditor_to = creditor_to;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public double getCreditor() {
        return creditor;
    }

    public void setCreditor(double creditor) {
        this.creditor = creditor;
    }

    public String getCreditor_to() {
        return creditor_to;
    }

    public void setCreditor_to(String creditor_to) {
        this.creditor_to = creditor_to;
    }
}
