package Models;

public class CurrentUser {
    private static int id;
    private static int id_emloyer;
    private static String Emloyer_name;
    private static String UserName;
    private static String Type;


    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        CurrentUser.id = id;
    }

    public static int getId_emloyer() {
        return id_emloyer;
    }

    public static void setId_emloyer(int id_emloyer) {
        CurrentUser.id_emloyer = id_emloyer;
    }

    public static String getEmloyer_name() {
        return Emloyer_name;
    }

    public static void setEmloyer_name(String emloyer_name) {
        Emloyer_name = emloyer_name;
    }

    public static String getUserName() {
        return UserName;
    }

    public static void setUserName(String userName) {
        UserName = userName;
    }

    public static String getType() {
        return Type;
    }

    public static void setType(String type) {
        Type = type;
    }
}
