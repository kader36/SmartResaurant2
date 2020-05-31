package Models;

public class Tables {

    private int id;
    private int number;
    private String active;

    public Tables() {
    }

    public Tables(int id, int number, String active) {
        this.id = id;
        this.number = number;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}
