package Models;

import BddPackage.Work_hoursOpertion;
import Controllers.EmployerController;

import java.util.ArrayList;
import java.util.Date;

public class Employer {
    private int id;
    private String first_name;
    private String last_name;
    private String last_nameANDfirst_name;
    private String  phone_number;
    private String job;
    private String work_strat;
    private Date work_end;
    private String adress;
    private int salary;
    private int SalaryDay;
    private String Present;
    private ArrayList<Work_hours> work_hours_ArrayList;


    public Employer() {

    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        Work_hours work_hours=new Work_hours();
        work_hours.setID_employer(this.id);
        work_hours.setDate_work(EmployerController.txtdatetime);
        Work_hoursOpertion work_hoursOpertion=new Work_hoursOpertion();
        Work_hours work_hours1=new Work_hours();
        work_hours1=work_hoursOpertion.getwork_hours(work_hours);
        String at=work_hours1.getAttendance();
        Present=at;
        work_hours_ArrayList=work_hoursOpertion.getAllWork(work_hours);

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
        this.last_nameANDfirst_name=this.first_name+" "+last_name;
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

    public void setJob(String email) {
        this.job = email;
    }

    public String getWork_strat() {
        return work_strat;
    }

    public void setWork_strat(String work_strat) {
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

    public String getLast_nameANDfirst_name() {
        return last_nameANDfirst_name;
    }

    public int getSalaryDay() {
        return SalaryDay;
    }

    public void setSalaryDay(int salaryDay) {
        SalaryDay = salaryDay;
    }

    public String getPresent() {
        return Present;
    }

    public void setPresent(String present) {
        Present = present;
    }

    public ArrayList<Work_hours> getWork_hours_ArrayList() {
        return work_hours_ArrayList;
    }

    public void setWork_hours_ArrayList(ArrayList<Work_hours> work_hours_ArrayList) {
        this.work_hours_ArrayList = work_hours_ArrayList;
    }
}
