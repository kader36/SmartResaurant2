package Controllers;

import Models.BillList;
import Models.Food;

import java.time.LocalDate;

public class ValuesStatic {
    public static BillList billList;
    public static int totCreditor;
    public static int factToday;
    public static int credetorToday;
    public static int factMonth;
    public static int credetorMonth;
    public static int factWeek;
    public static int credetorWeek;
    public static Food currentFood;
    public static String inMonth(){
        String tab [] = LocalDate.now().toString().split("-");
        return tab[0] + "-" + tab[1];
    }

    public static String getDay(String date){
        String tab [] = date.split("-");
        return tab[2];
    }

    public static String getMonth(String date){
        String tab [] = date.split("-");
        return tab[1];
    }

    public static String getYear(String date){
        String tab [] = date.split("-");
        return tab[0];
    }
}
