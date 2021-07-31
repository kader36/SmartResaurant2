package Models;

public class IngredientsFoodProductCompose {
    private int id_productCopmpose;
    private int id_food;
    private double quantity;

    public IngredientsFoodProductCompose() {
    }

    public IngredientsFoodProductCompose(int id_drink, int id_product, int quantity) {
        this.id_productCopmpose = id_drink;
        this.id_food = id_product;
        this.quantity = quantity;
    }

    public int getId_productCopmpose() {
        return id_productCopmpose;
    }

    public void setId_productCopmpose(int id_productCopmpose) {
        this.id_productCopmpose = id_productCopmpose;
    }

    public int getId_food() {
        return id_food;
    }

    public void setId_food(int id_food) {
        this.id_food = id_food;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
