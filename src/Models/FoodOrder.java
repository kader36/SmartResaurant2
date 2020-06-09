package Models;

public class FoodOrder {

    private int id_order;
    private int id_food;
    private int quantity;

    public FoodOrder() {
    }

    public FoodOrder(int id_order, int id_food, int quantity) {
        this.id_order = id_order;
        this.id_food = id_food;
        this.quantity = quantity;
    }

    public int getId_food() {
        return id_food;
    }

    public void setId_food(int id_food) {
        this.id_food = id_food;
    }

    public int getId_order() {
        return id_order;
    }

    public void setId_order(int id_order) {
        this.id_order = id_order;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
