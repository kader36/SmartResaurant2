package Models;

public class Product {
    private int id;
    private String name;
    private int  id_category;
    private String category_name;
    private String purchase_Unit;
    private String storage_Unit;
    private String Recipe_Unit;
    private int quantity;

    public Product() {
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public Product(int id, String name, int id_category, String purchase_Unit, String storage_Unit, String recipe_Unit, int quantity) {
        this.id = id;
        this.name = name;
        this.id_category = id_category;
        this.purchase_Unit = purchase_Unit;
        this.storage_Unit = storage_Unit;
        Recipe_Unit = recipe_Unit;
        this.quantity = quantity;
    }

    public int getId_category() {
        return id_category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPurchase_Unit() {
        return purchase_Unit;
    }

    public void setPurchase_Unit(String purchase_Unit) {
        this.purchase_Unit = purchase_Unit;
    }

    public String getStorage_Unit() {
        return storage_Unit;
    }

    public void setStorage_Unit(String storage_Unit) {
        this.storage_Unit = storage_Unit;
    }

    public String getRecipe_Unit() {
        return Recipe_Unit;
    }

    public void setRecipe_Unit(String recipe_Unit) {
        Recipe_Unit = recipe_Unit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
