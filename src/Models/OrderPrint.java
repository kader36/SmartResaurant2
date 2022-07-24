package Models;

import java.util.ArrayList;
import java.util.Date;

public class OrderPrint {
    private int id;
    private int id_table;
    private Date time;
    private double price;
    private ArrayList<FoodOrder> foodsList;

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ArrayList<FoodOrder> getFoodsList() { return foodsList; }

    public void setFoodsList(ArrayList<FoodOrder> foodsList) { this.foodsList = foodsList; }


}
