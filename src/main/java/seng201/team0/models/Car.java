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
    private final int baseFuelEconomy;
    /**
     * The currently installed handling upgrade tuning part. Null if no upgrade is installed.
     */
    private TuningPart handlingUpgrade;
    /**
     * The currently installed speed upgrade tuning part. Null if no upgrade is installed.
     */
    private TuningPart speedUpgrade;

    /**
     * A custom name given to the car by the player. Null if no custom name is set.
     */
    private String customName;

    /**
     * Constructs a new Car instance with specified base statistics and price.
     *
     * @param name         The name of the car model.
     * @param speed        The base speed of the car.
     * @param handling     The base handling characteristic of the car.
     * @param reliability  The base reliability percentage of the car.
     * @param fuelEconomy  The base fuel economy (max distance) of the car.
     * @param price        The purchase price of the car.
     */
    public Car(String name, double speed, double handling, double reliability, int fuelEconomy, int price) {
        super(name, price);
        this.baseSpeed = speed;
        this.baseHandling = handling;
        this.baseReliability = reliability;
        this.baseFuelEconomy = fuelEconomy;
    }

    /**
     * Constructs a new Car instance as a copy of an existing Car object.
     * This is useful for creating new instances without carrying over installed upgrades or custom names.
     *
     * @param other The {@link Car} object to copy.
     */
    public Car(Car other) {
        super(other.getName(), other.getPrice());
        this.baseSpeed = other.baseSpeed;
        this.baseHandling = other.baseHandling;
        this.baseReliability = other.baseReliability;
        this.baseFuelEconomy = other.baseFuelEconomy;
        this.speedUpgrade = null; // Copy constructor should typically not copy transient state like upgrades
        this.handlingUpgrade = null; // Copy constructor should typically not copy transient state like upgrades
        this.customName = null; // Custom name is not copied
    }

    /**
     * Gets the reliability of the car formatted as a percentage string.
     * @return A string representing the reliability (e.g., "70%").
     */
    public String getReliabilityPercent() {
        return String.format("%.0f%%", getReliability() * 100);
    }

    /**
     * Gets the current speed of the car formatted as a string, scaled for display.
     * @return A string representing the scaled speed.
     */
    public String getSpeedString() {
        return String.format("%.0f", getSpeed() * 350);
    }

    /**
     * Gets the current handling of the car formatted as a percentage string.
     * @return A string representing the handling (e.g., "80%").
     */
    public String getHandlingPercent() {
        return String.format("%.0f%%", getHandling() * 100);
    }

    /**
     * Gets the current fuel economy of the car formatted as a string, scaled for display.
     * @return A string representing the scaled fuel economy.
     */
    public String getFuelEconomyString() {
        return String.format("%.0f", getFuelEconomy() * 30);
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

    /**
     * Gets the custom name of the car.
     * @return The custom name, or null if no custom name has been set.
     */
    public String getCustomName() {
        return customName;
    }

    /**
     * Sets a custom name for the car.
     * @param name The new custom name for the car.
     */
    public void setCustomName(String name) {
        this.customName = name;
    }
}
