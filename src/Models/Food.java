package Models;

import BddPackage.FoodCategoryOperation;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.CheckBox;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.File;

public class Food {

    private int id;
    private int id_category;
    private String name;
    private String description;
    private int price;
    private int pricefee;
    private String image_path;
    private ImageView image = new ImageView();
    //private VBox imageVBox;
    private int rating;
    private String category_name;
    private CheckBox foodSelectedCheckBox;
    private byte[] IMAGE;


    private boolean availabale;


    public Food() {
        image.setFitHeight(80);
        image.setFitWidth(200);
        this.foodSelectedCheckBox = new CheckBox();
        this.foodSelectedCheckBox.setSelected(false);
        //imageVBox = new VBox(image);
        //imageVBox.setMargin(image, new Insets(10, 10, 10, 10));
       // imageVBox.setStyle("-fx-padding: 10;");
        //imageVBox.setAlignment();
  /*      FoodCategoryOperation foodCategoryOperation=new FoodCategoryOperation();
        FoodCategory  foodCategory=new FoodCategory();
        ArrayList<FoodCategory> list = new ArrayList<>();
        list=foodCategoryOperation.getAll();
        FoodCategory foodCategory1=new FoodCategory();
        for(int i=0; i<list.size();i++){
            foodCategory1=list.get(i);
            if(foodCategory1.getId()==1){
                this.category="1000000000000";
            }
        }
        this.category= "1000000000000";
*/

    }

    public int getPricefee() {
        return pricefee;
    }

    public void setPricefee(int pricefee) {
        this.pricefee = pricefee;
    }

    public Food(int id, int id_category, String name, String description,
                int price, String image_path, int rating, boolean availabale) {
        this.id = id;
        this.id_category = id_category;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image_path = image_path;
        this.rating = rating;
        this.availabale = availabale;
        File file = new File(image_path);
        Image image = new Image(file.toURI().toString());
        this.image.setImage(image);
        this.image.setFitHeight(80);
        this.image.setFitWidth(220);
        // change the image margin.
        this.foodSelectedCheckBox = new CheckBox();
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
    public String getCategory_name() {
        return category_name;
    }
    public void setCategory(int id_category_name) {
        String category="";
        FoodCategoryOperation foodCategoryOperation=new FoodCategoryOperation();
        FoodCategory foodCategory=new FoodCategory();
        foodCategory=foodCategoryOperation.getCategory(id_category_name);


        this.category_name=foodCategory.getName();
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
        File file=new File(image_path);
         ImageView image=new ImageView(file.toURI().toString());
         image.setFitHeight(80);
         image.setFitWidth(140);
        Rectangle clip = new Rectangle();
        clip.setWidth(140);
        clip.setHeight(80);

        clip.setArcHeight(25);
        clip.setArcWidth(25);
        clip.setStroke(Color.BLACK);
        image.setClip(clip);

        // snapshot the rounded image.
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        //WritableImage myimage = image.snapshot(parameters, null);

        // remove the rounding clip so that our effect can show through.
        image.setClip(null);
        image.setEffect(new DropShadow(3, Color.BLACK));
        //image.setImage(myimage);
         return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public void setImageByPath(Image image) {
        this.image.setImage(image);
    }

    public int getRating() { return rating; }

    public void setRating(int rating) { this.rating = rating; }

    public boolean isAvailabale() { return availabale; }

    public void setAvailabale(boolean availabale) { this.availabale = availabale; }

    public CheckBox getFoodSelectedCheckBox() { return foodSelectedCheckBox; }

    public void setFoodSelectedCheckBox(CheckBox foodSelectedCheckBox) { this.foodSelectedCheckBox = foodSelectedCheckBox; }

    public void setCheckBoxState(boolean state) { this.foodSelectedCheckBox.setSelected(state); }

    public byte[] getIMAGE() {
        return IMAGE;
    }

    public void setIMAGE(byte[] IMAGE) {
        this.IMAGE = IMAGE;
    }
}
