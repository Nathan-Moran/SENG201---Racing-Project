package seng201.team0.models;

public class Car extends Purchasable {
    private double basespeed;
    private double basehandling; // influences how well the car can navigate turns and maintain control
    private int basereliability; // a percentage. Higher reliability means the car will be less likely to breakdown during a race (random event)
    private int basefuelEconomy; // max distance in km achievable with full tank of fuel
    private TuningPart handlingUpgrade;
    private TuningPart speedUpgrade;

    public Car(String name, double speed, double handling, int reliability, int fuelEconomy, int price) {
        super(name, price);
        this.basespeed = speed;     // Can be changed using tuning parts
        this.basehandling = handling;   // Can be changed using tuning parts
        this.basereliability = reliability; // Dependent on Car model
        this.basefuelEconomy = fuelEconomy; // Dependent on Car model
    }

    public double getSpeed() {
        if (speedUpgrade != null) {
            double speedBoost = speedUpgrade.getBoost();
            return basespeed * speedBoost;
        } else {
            return basespeed;
        }
    }

    public double getHandling() {
        if (handlingUpgrade != null) {
            double handlingBoost = handlingUpgrade.getBoost();
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

    public void addSpeedUpgrade(TuningPart part) {
        if (speedUpgrade == null) {
            this.speedUpgrade = part;
        }
    }

    public void addHandlingUpgrade(TuningPart part) {
        if (handlingUpgrade == null) {
            handlingUpgrade = part;
        }
    }

    public void removeSpeedUpgrade(TuningPart part) {
        if (speedUpgrade != null) {
            speedUpgrade = null;
        }
    }

    public void removeHandlingUpgrade(TuningPart part) {
        if (handlingUpgrade != null) {
            handlingUpgrade = null;
        }
    }


    public TuningPart getSpeedUpgrade() {
            return speedUpgrade;
    }

    public TuningPart getHandlingUpgrade() {
            return handlingUpgrade;

    }
}
