package Models;

public class Drinks {
    private int id;
    private int id_category;
    private String name;
    private String decription;
    private int price;
    private String image_path;

    public Drinks() {
    }

    public Drinks(int id, int id_category, String name, String description, int price, String image_path) {
        this.id = id;
        this.id_category = id_category;
        this.name = name;
        this.decription = description;
        this.price = price;
        this.image_path = image_path;
    }

    public Drinks(int id_category, String name, String decription, int price, String image_path) {
        this.id_category = id_category;
        this.name = name;
        this.decription = decription;
        this.price = price;
        this.image_path = image_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_category() {
        return id_category;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return decription;
    }

    public void setDescription(String decription) {
        this.decription = decription;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }
}
