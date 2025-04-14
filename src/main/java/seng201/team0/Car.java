package seng201.team0;

public class Car extends Purchasable {
    private double basespeed;
    private double basehandling; // influences how well the car can navigate turns and maintain control
    private int basereliability; // a percentage. Higher reliability means the car will be less likely to breakdown during a race (random event)
    private int basefuelEconomy; // max distance in km achievable with full tank of fuel
    private TuningPart[] upgrades = new TuningPart[2];

    public Car(String name, double speed, double handling, int reliability, int fuelEconomy, int price) {
        super(name, price);
        this.basespeed = speed;     // Can be changed using tuning parts
        this.basehandling = handling;   // Can be changed using tuning parts
        this.basereliability = reliability; // Dependent on Car model
        this.basefuelEconomy = fuelEconomy; // Dependent on Car model
    }

    public double getSpeed() {
        if (upgrades[0] != null) {
            double speedBoost = upgrades[0].getBoost();
            return basespeed * speedBoost;
        } else {
            return basespeed;
        }
    }

    public double getHandling() {
        if (upgrades[1] != null) {
            double handlingBoost = upgrades[1].getBoost();
            return basehandling * handlingBoost;
        } else {
            return basehandling;
        }
    }

    public int getReliability() {
        return basereliability;
    }

    public int getFuelEconomy() {
        return basefuelEconomy;
    }

    public TuningPart[] getUpgrades() {
        return upgrades;
    }

    public void addSpeedUpgrade(TuningPart part) {
        upgrades[0] = part;
    }

    public void addHandlingUpgrade(TuningPart part) {
        upgrades[1] = part;
    }

    public TuningPart getSpeedUpgrade() {
        return upgrades[0];
    }

    public TuningPart getHandlingUpgrade() {
        return upgrades[1];
    }
}
