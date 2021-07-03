package Models;

import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class Drinks {
    private int id;
    private int id_category;
    private String name;
    private String decription;
    private int price;
    private String image_path;
    private int rating;
    private boolean available;
    private CheckBox drinkSelectedCheckBox;
    private ImageView image = new ImageView();

    public Drinks() {
        image.setFitHeight(96);
        image.setFitWidth(227);
        this.drinkSelectedCheckBox = new CheckBox();
        this.drinkSelectedCheckBox.setSelected(false);
    }

    public Drinks(int id, int id_category, String name, String description, int price, String image_path, int rating, boolean available) {
        this.id = id;
        this.id_category = id_category;
        this.name = name;
        this.decription = description;
        this.price = price;
        this.image_path = image_path;
        this.rating =  rating;
        this.available = available;
        File file = new File(image_path);
        Image image = new Image(file.toURI().toString());
        this.image.setImage(image);
        this.image.setFitHeight(80);
        this.image.setFitWidth(220);
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

    public int getRating() { return rating; }

    public void setRating(int rating) { this.rating = rating; }

    public boolean isAvailable() { return available; }

    public void setAvailable(boolean available) { this.available = available; }

    public void setCheckBoxState(boolean state) { this.drinkSelectedCheckBox.setSelected(state); }

    public CheckBox getDrinkSelectedCheckBox() { return drinkSelectedCheckBox; }

    public void setImageByPath(Image image) {
        this.image.setImage(image);
    }

    public ImageView getImage() {
        return image;
    }

}
