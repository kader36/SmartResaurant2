package Models;

public class DrinksOrder {
    private int id_order;
    private int id_drink;
    private int quantity;

    public DrinksOrder() {
    }

    public DrinksOrder(int id_order, int id_drink, int quantity) {
        this.id_order = id_order;
        this.id_drink = id_drink;
        this.quantity = quantity;
    }

    public int getId_order() {
        return id_order;
    }

    public void setId_order(int id_order) {
        this.id_order = id_order;
    }

    public int getId_drink() {
        return id_drink;
    }

    public void setId_drink(int id_drink) {
        this.id_drink = id_drink;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
