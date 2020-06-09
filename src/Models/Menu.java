package Models;

import java.util.Date;

public class Menu {

    private int id;
    private Date date;

    public Menu() {
    }

    public Menu(int id, Date date) {
        this.id = id;
        this.date = date;
    }



    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
