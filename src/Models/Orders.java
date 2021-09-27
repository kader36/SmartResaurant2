package Models;

import java.util.ArrayList;
import java.util.Date;

public class Orders {

    private int id;
    private int id_table;
    private Date time;
    private double price;
    private ArrayList<FoodOrder> foodsList;



    public Orders() {
    }

    public Orders(int id, int id_table, Date time, double price, ArrayList<FoodOrder> foodsList, ArrayList<DrinksOrder> drinksList) {
        this.id = id;
        this.id_table = id_table;
        this.time = time;
        this.price = price;
        this.foodsList = foodsList;

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ArrayList<FoodOrder> getFoodsList() { return foodsList; }

    public void setFoodsList(ArrayList<FoodOrder> foodsList) { this.foodsList = foodsList; }


}
