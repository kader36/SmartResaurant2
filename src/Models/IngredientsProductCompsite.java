package Models;

public class IngredientsProductCompsite {
    private int Id_productCompsite;
    private int id_product;
    private String Product_name;
    private String ProductCompsite_name;
    private String unity;
    private int quantity;

    public IngredientsProductCompsite() {
    }

    public int getId_productCompsite() {
        return Id_productCompsite;
    }

    public void setId_productCompsite(int id_productCompsite) {
        Id_productCompsite = id_productCompsite;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public String getProduct_name() {
        return Product_name;
    }

    public void setProduct_name(String product_name) {
        Product_name = product_name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductCompsite_name() {
        return ProductCompsite_name;
    }

    public void setProductCompsite_name(String productCompsite_name) {
        ProductCompsite_name = productCompsite_name;
    }

    public String getUnity() {
        return unity;
    }

    public void setUnity(String unity) {
        this.unity = unity;
    }
}
