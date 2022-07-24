package Models;

import javafx.scene.control.CheckBox;

import java.sql.Date;


public class MoneyWithdrawal {

    private int databaseID;
    private String userName;
    private Double moneyWithdrawn;
    private String moneyWithdrawnDA;
    private Date date;
    private String note;
    private CheckBox activeCheckBox;

    public MoneyWithdrawal() {
    }

    public MoneyWithdrawal(String userName, Double moneyWithdrawled, Date date, String note) {


        this.userName = userName;
        this.moneyWithdrawn = moneyWithdrawled;
        this.date = date;
        this.note = note;
        this.activeCheckBox = new CheckBox();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Double getMoneyWithdrawn() {
        return moneyWithdrawn;
    }

    public void setMoneyWithdrawn(Double moneyWithdrawn) {
        this.moneyWithdrawn = moneyWithdrawn;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public CheckBox getActiveCheckBox() {
        return activeCheckBox;
    }

    public void setActiveCheckBox(CheckBox activeCheckBox) {
        this.activeCheckBox = activeCheckBox;
    }

    public int getDatabaseID() { return databaseID; }

    public void setDatabaseID(int databaseID) { this.databaseID = databaseID; }


    public String getMoneyWithdrawnDA() {
        return moneyWithdrawn+" DA";
    }

    public void setMoneyWithdrawnDA(String moneyWithdrawnDA) {
        this.moneyWithdrawnDA = moneyWithdrawn+" DA";
    }
}
