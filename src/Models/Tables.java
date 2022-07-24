package Models;

import javafx.scene.control.CheckBox;

public class Tables {

    private int id;
    private int number;
    private String numberTab;
    private String Bloquer;
    private String active;
    private String OrderActive;
    private CheckBox activeCheckBox;
    private int OrderId;
    // add total sum.

    public int getOrderId() {
      return OrderId;
    }

    public void setOrderId(int orderId) {
        this.OrderId = orderId;
    }

    public Tables() {
    }

    public Tables(int id, int number, String active) {
        this.id = id;
        this.number = number;
        this.active = active;
        activeCheckBox = new CheckBox();
        this.numberTab=" الطاولة رقم "+number;

    }

    public Tables(int id, int number, String active,String Bloquer) {
        this.id = id;
        this.number = number;
        this.active = active;
        activeCheckBox = new CheckBox();
        this.numberTab=" الطاولة رقم "+number;
        this.Bloquer=Bloquer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
        this.numberTab="الطاولة رقم" ;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public CheckBox getActiveCheckBox() {
        return activeCheckBox;
    }

    public void setActiveCheckBox(CheckBox activeCheckBox) {
        this.activeCheckBox = activeCheckBox;
    }

    public String getNumberTab() {
        return numberTab;
    }

    public void setNumberTab() {
        this.numberTab =  this.number+"الطاولة رقم";
    }

    public String getBloquer() {
        return Bloquer;
    }

    public void setBloquer(String bloquer) {
        Bloquer = bloquer;
    }

    public String getOrderActive() {
        return OrderActive;
    }

    public void setOrderActive(String orderActive) {
        OrderActive = orderActive;
    }
}
