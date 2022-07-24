package Models;

public class ProductComposite {
    private int id;
    private String name;
    private double quantity;
    private String storage_Unit;
    private String Unity_Food;
    private int Coefficient;
    private int LESS_QUANTITY;

    public ProductComposite() {
    }

    public ProductComposite(int id, String name, float quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
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

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public int getLESS_QUANTITY() {
        return LESS_QUANTITY;
    }

    public void setLESS_QUANTITY(int LESS_QUANTITY) {
        this.LESS_QUANTITY = LESS_QUANTITY;
    }

    public String getStorage_Unit() {
        return storage_Unit;
    }

    public void setStorage_Unit(String storage_Unit) {
        this.storage_Unit = storage_Unit;
    }

    public String getUnity_Food() {
        return Unity_Food;
    }

    public void setUnity_Food(String unity_Food) {
        Unity_Food = unity_Food;
    }

    public int getCoefficient() {
        return Coefficient;
    }

    public void setCoefficient(int coefficient) {
        Coefficient = coefficient;
    }
}
