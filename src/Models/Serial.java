package Models;

public class Serial {
    public int Id;
    public String trialBegin;
    public String trialFin;
    public String serial;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTrialBegin() {
        return trialBegin;
    }

    public void setTrialBegin(String trialBegin) {
        this.trialBegin = trialBegin;
    }

    public String getTrialFin() {
        return trialFin;
    }

    public void setTrialFin(String trialFin) {
        this.trialFin = trialFin;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }
}
