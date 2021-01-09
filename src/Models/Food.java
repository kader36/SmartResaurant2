package Models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Food {

    private int id;
    private int id_category;
    private String name;
    private String description;
    private int price;
    private String image_path;
    private ImageView image = new ImageView();


    public Food() {
        image.setFitHeight(96);
        image.setFitWidth(227);
    }

    public Food(int id, int id_category, String name, String description, int price, String image_path) {
        this.id = id;
        this.id_category = id_category;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image_path = image_path;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getId_category() {
        return id_category;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public void setImageByPath(Image image) {
        this.image.setImage(image);
    }
}
