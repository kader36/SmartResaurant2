package Models;

import BddPackage.ProviderOperation;

import java.util.ArrayList;
import java.util.Date;

public class StoreBill {
    private int id;
    private Date date;
    private int id_user;
    private int id_provider;
    private int paid_up;

    public StoreBill() { }

    public StoreBill(int id, Date date, int id_user, int id_provider, int paid_up) {
        this.id = id;
        this.date = date;
        this.id_user = id_user;
        this.id_provider = id_provider;
        this.paid_up = paid_up;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_provider() {
        return id_provider;
    }

    public void setId_provider(int id_provider) {
        this.id_provider = id_provider;
    }

    public int getPaid_up() {
        return paid_up;
    }

    public void setPaid_up(int paid_up) {
        this.paid_up = paid_up;
    }

    public Provider getProvider(int idProvider){
        ProviderOperation providerOperation = new ProviderOperation();
        ArrayList<Provider> listProvider = providerOperation.getAll();
        for (Provider listProviderFromDB : listProvider) {
            if (listProviderFromDB.getId() == idProvider)
                return listProviderFromDB;
        }

        return null;
    }
}
