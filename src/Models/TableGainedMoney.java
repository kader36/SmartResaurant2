package Models;

public class TableGainedMoney {

    private int tableId;
    private double gainedMoney;

    public TableGainedMoney(int tableId, double gainedMoney) {
        this.tableId = tableId;
        this.gainedMoney = gainedMoney;
    }

    public int getTableId() { return tableId; }

    public void setTableId(int tableId) { this.tableId = tableId; }

    public double getGainedMoney() { return gainedMoney; }

    public void setGainedMoney(double gainedMoney) { this.gainedMoney = gainedMoney; }
}
