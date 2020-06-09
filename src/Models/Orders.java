package Models;

import java.util.Date;

public class Orders {

    private int id;
    private int id_table;
    private Date time;
    private int price;

    public Orders() {
    }

    public Orders(int id, int id_table, Date time, int price) {
        this.id = id;
        this.id_table = id_table;
        this.time = time;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_table() {
        return id_table;
    }

    public void setId_table(int id_table) {
        this.id_table = id_table;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
