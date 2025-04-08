import java.util.ArrayList;

public class Car extends Purchasable {
    private int speed;
    private int handling; // influences how well the car can navigate turns and maintain control
    private int reliability; // a percentage. Higher reliability means the car will be less likely to breakdown during a race (random event)
    private int fuelEconomy; // max distance in km achievable with full tank of fuel
    private ArrayList<TuningParts> upgrades = new ArrayList<>();

    public Car(int speed, int handling, int reliability, int fuelEconomy, int price, String name, String description) {
        super(name, price, description);
        this.speed = speed;
        this.handling = handling;
        this.reliability = reliability;
        this.fuelEconomy = fuelEconomy;
    }

    public int getSpeed() {
        return speed;
    }

    public int getHandling() {
        return handling;
    }

    public int getReliability() {
        return reliability;
    }

    public int getFuelEconomy() {
        return fuelEconomy;
    }

    public ArrayList<TuningParts> getUpgrades() {
        if (upgrades.isEmpty()) {
            // figure out what to say
        }
        return upgrades;
    }

    public void addUpgrade(TuningParts parts) {
        upgrades.add(parts);
    }

    public void removeUpgrade(TuningParts parts) {
        upgrades.remove(parts);
    }



//    public void setUpgrades(ArrayList<TuningParts> upgrades) {
//        this.upgrades = upgrades;
//    }

}
