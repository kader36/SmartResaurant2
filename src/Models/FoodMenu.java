package Models;

public class FoodMenu {


    private int id_menu;
    private int id_food;

    public FoodMenu() {
    }

    public FoodMenu( int id_menu,int id_food) {
        this.id_food = id_food;
        this.id_menu = id_menu;
    }

    public int getId_food() {
        return id_food;
    }

    public void setId_food(int id_food) {
        this.id_food = id_food;
    }

    public int getId_menu() {
        return id_menu;
    }

    public void setId_menu(int id_menu) {
        this.id_menu = id_menu;
    }
}
