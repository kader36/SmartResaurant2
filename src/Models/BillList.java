package Models;

public class BillList  {
    private int number;
    private String date;
    private String provider_name;
    private int Paid_up;
    private int rest;
    private int total;

    public BillList() {
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProvider_name() {
        return provider_name;
    }

    public void setProvider_name(String provider_name) {
        this.provider_name = provider_name;
    }

    public int getPaid_up() {
        return Paid_up;
    }

    public void setPaid_up(int paid_up) {
        Paid_up = paid_up;
    }

    public int getRest() {
        return rest;
    }

    public void setRest(int rest) {
        this.rest = rest;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
