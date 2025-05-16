package seng201.team0.models;

public class TuningPart extends Purchasable {
    String stat;
    double boost;

    public TuningPart(String name, int price, String stat, double boost) {
        super(name, price);
        this.stat = stat;
        this.boost = boost;
    }

    public String getStat() {
        return stat;
    }

    public double getBoost() {
        return boost;
    }

    public String getDescription() {
        return this.getStat() + " +" + (1 - this.getBoost()) * 100 + "%";
    }
}
