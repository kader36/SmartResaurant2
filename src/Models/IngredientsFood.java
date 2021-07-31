package Models;

public class IngredientsFood {
    private int id_food;
    private int id_product;
    private String Product_name;
    private int quantity;
    private String unity;
    private int type;


    public IngredientsFood() {
    }

    public IngredientsFood(int id_food, int id_product, int quantity) {
        this.id_food = id_food;
        this.id_product = id_product;
        this.quantity = quantity;
    }



    public IngredientsFood(String product_name, int quantity) {
        Product_name = product_name;
        this.quantity = quantity;
    }

    public String getProduct_name() {
        return Product_name;
    }

    public void setProduct_name(String product_name) {
        Product_name = product_name;
    }

    public int getId_food() {
        return id_food;
    }

    public void setId_food(int id_food) {
        this.id_food = id_food;
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

    public String getUnity() {
        return unity;
    }

    public void setUnity(String unity) {
        this.unity = unity;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
