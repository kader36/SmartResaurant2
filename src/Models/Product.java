package Models;

public class Product {
    private int id;
    private String name;
    private int id_category;
    private String category_name;
    private String storage_Unit;
    private int less_quantity;
    private int tot_quantity;

    public Product() {
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

    public int getId_category() {
        return id_category;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getStorage_Unit() {
        return storage_Unit;
    }

    public void setStorage_Unit(String storage_Unit) {
        this.storage_Unit = storage_Unit;
    }

    public int getLess_quantity() {
        return less_quantity;
    }

    public void setLess_quantity(int less_quantity) {
        this.less_quantity = less_quantity;
    }

    public int getTot_quantity() {
        return tot_quantity;
    }

    public void setTot_quantity(int tot_quantity) {
        this.tot_quantity = tot_quantity;
    }
}
