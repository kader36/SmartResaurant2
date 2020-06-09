package Models;

public class StoreBillProduct {
    private int id_stor_bill;
    private int id_product;
    private int price;
    private int product_quantity;

    public StoreBillProduct() {
    }

    public StoreBillProduct(int id_stor_bill, int id_product, int price, int product_quantity) {
        this.id_stor_bill = id_stor_bill;
        this.id_product = id_product;
        this.price = price;
        this.product_quantity = product_quantity;
    }

    public int getId_stor_bill() {
        return id_stor_bill;
    }

    public void setId_stor_bill(int id_stor_bill) {
        this.id_stor_bill = id_stor_bill;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(int product_quantity) {
        this.product_quantity = product_quantity;
    }
}
