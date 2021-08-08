package Models;

public class Settings {
    private String Name;
    private String Phonenamber1;
    private String Phonenamber2;
    private String Adress;
    private String pathImage;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhonenamber1() {
        return Phonenamber1;
    }

    public void setPhonenamber1(String phonenamber1) {
        Phonenamber1 = phonenamber1;
    }

    public String getPhonenamber2() {
        return Phonenamber2;
    }

    public void setPhonenamber2(String phonenamber2) {
        Phonenamber2 = phonenamber2;
    }

    public String getAdress() {
        return Adress;
    }

    public void setAdress(String adress) {
        Adress = adress;
    }

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }
}
