package Models;

public class AvailableItem {

    private String itemName;
    private boolean itemAvailable;
    private String imagePath;


    public AvailableItem(String itemName, boolean itemAvailable, String imagePath) {
        this.itemName = itemName;
        this.itemAvailable = itemAvailable;
        this.imagePath = imagePath;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public boolean isItemAvailable() {
        return itemAvailable;
    }

    public void setItemAvailable(boolean itemAvailable) {
        this.itemAvailable = itemAvailable;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }


}

