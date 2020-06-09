package Models;

public class IngredientsDrink {
    private int id_drink;
    private int id_product;
    private int quantity;

    public IngredientsDrink() {
    }

    public IngredientsDrink(int id_drink, int id_product, int quantity) {
        this.id_drink = id_drink;
        this.id_product = id_product;
        this.quantity = quantity;
    }

    public int getId_drink() {
        return id_drink;
    }

    public void setId_drink(int id_drink) {
        this.id_drink = id_drink;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
