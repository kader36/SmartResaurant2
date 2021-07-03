package Models;

import java.util.Date;

public class Employer {
    private int id;
    private String first_name;
    private String last_name;
    private String  phone_number;
    private String job;
    private Date work_strat;
    private Date work_end;
    private String adress;
    private int salary;
    private String numberphone;


    public Employer() {
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
        this.numberphone="0";
    }

    public String getJob() {
        return job;
    }

    public void setJob(String email) {
        this.job = email;
    }

    public Date getWork_strat() {
        return work_strat;
    }

    public void setWork_strat(Date work_strat) {
        this.work_strat = work_strat;
    }

    public Date getWork_end() {
        return work_end;
    }

    public void setWork_end(Date work_end) {
        this.work_end = work_end;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
