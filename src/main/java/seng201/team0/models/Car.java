package seng201.team0.models;

/**
 * Represents a car in the game, which is a type of {@link Purchasable} item.
 * Cars have base statistics like speed, handling, reliability, and fuel economy.
 * These stats can be modified by installing {@link TuningPart} upgrades for speed and handling.
 * @author Nathan Moran
 */
public class Car extends Purchasable {
    /**
     * The base speed of the car before any upgrades.
     */
    private final double baseSpeed;
    /**
     * The base handling of the car, influencing control, before any upgrades.
     */
    private final double baseHandling;
    /**
     * The base reliability of the car as a percentage. Higher values mean less chance of breakdown.
     */
    private final double baseReliability;
    /**
     * The base fuel economy of the car, representing max distance (km) on a full tank.
     */
    private final double baseFuelEconomy;
    /**
     * The currently installed handling upgrade tuning part. Null if no upgrade is installed.
     */
    private TuningPart handlingUpgrade;
    /**
     * The currently installed speed upgrade tuning part. Null if no upgrade is installed.
     */
    private TuningPart speedUpgrade;

    private String customName;

    /**
     * Constructs a new Car instance.
     *
     * @param name         The name of the car model.
     * @param speed        The base speed of the car.
     * @param handling     The base handling characteristic of the car.
     * @param reliability  The base reliability percentage of the car.
     * @param fuelEconomy  The base fuel economy (max distance) of the car.
     * @param price        The purchase price of the car.
     */
    public Car(String name, double speed, double handling, double reliability, double fuelEconomy, int price) {
        super(name, price);
        this.baseSpeed = speed;
        this.baseHandling = handling;
        this.baseReliability = reliability;
        this.baseFuelEconomy = fuelEconomy;
    }

    public String getReliabilityPercent() {
        return String.format("%.0f%%", baseReliability * 100);
    }

    public String getSpeedString() {
        return String.format("%.2fkm/h", baseSpeed * 300);
    }

    public String getHandlingPercent() {
        return String.format("%.2f%%", baseHandling * 100);
    }

    public String getFuelEconomyString() {
        return String.format("%.2f%%", baseFuelEconomy * 300);
    }


    /**
     * Gets the current speed of the car, including any boost from an installed speed upgrade.
     *
     * @return The effective speed of the car.
     */
    public double getSpeed() {
        if (speedUpgrade != null) {
            double speedBoost = speedUpgrade.getBoost();
            return baseSpeed * speedBoost;
        } else {
            return baseSpeed;
        }
    }

    /**
     * Gets the current handling of the car, including any boost from an installed handling upgrade.
     *
     * @return The effective handling of the car.
     */
    public double getHandling() {
        if (handlingUpgrade != null) {
            double handlingBoost = handlingUpgrade.getBoost();
            return baseHandling * handlingBoost;
        } else {
            return baseHandling;
        }
    }

    /**
     * Gets the base reliability of the car.
     *
     * @return The reliability percentage.
     */
    public double getReliability() {
        return baseReliability;
    }

    /**
     * Gets the base fuel economy of the car.
     *
     * @return The maximum distance achievable on a full tank.
     */
    public double getFuelEconomy() {
        return baseFuelEconomy;
    }

    /**
     * Adds a speed upgrade to the car if no speed upgrade is currently installed.
     *
     * @param part The {@link TuningPart} to be installed as a speed upgrade.
     */
    public void addSpeedUpgrade(TuningPart part) {
        if (speedUpgrade == null) {
            this.speedUpgrade = part;
        }
    }

    /**
     * Adds a handling upgrade to the car if no handling upgrade is currently installed.
     *
     * @param part The {@link TuningPart} to be installed as a handling upgrade.
     */
    public void addHandlingUpgrade(TuningPart part) {
        if (handlingUpgrade == null) {
            handlingUpgrade = part;
        }
    }

    /**
     * Removes the currently installed speed upgrade from the car.
     */
    public void removeSpeedUpgrade() {
        if (speedUpgrade != null) {
            speedUpgrade = null;
        }
    }

    /**
     * Removes the currently installed handling upgrade from the car.
     */
    public void removeHandlingUpgrade() {
        if (handlingUpgrade != null) {
            handlingUpgrade = null;
        }
    }

    /**
     * Gets the currently installed speed upgrade.
     *
     * @return The installed speed {@link TuningPart}, or null if no speed upgrade is installed.
     */
    public TuningPart getSpeedUpgrade() {
            return speedUpgrade;
    }

    /**
     * Gets the currently installed handling upgrade.
     *
     * @return The installed handling {@link TuningPart}, or null if no handling upgrade is installed.
     */
    public TuningPart getHandlingUpgrade() {
            return handlingUpgrade;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String name) {
        this.customName = name;
    }
}
