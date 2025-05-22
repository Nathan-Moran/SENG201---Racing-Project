package seng201.team0.models;

/**
 * Represents a tuning part that can be purchased and installed on a car to enhance its performance.
 * It extends {@link Purchasable} to include properties like the stat it affects and its boost multiplier.
 */
public class TuningPart extends Purchasable {
    /**
     * The stat that this tuning part affects (e.g., "⚡" for speed, "🎮" for handling).
     */
    String stat;
    /**
     * The multiplier by which this tuning part boosts the affected stat.
     * For example, 1.1 means a 10% increase.
     */
    double boost;

    /**
     * Constructs a new TuningPart instance.
     *
     * @param name The name of the tuning part.
     * @param price The price of the tuning part.
     * @param stat The string identifier for the stat this part boosts (e.g., an emoji like "⚡" or "🎮").
     * @param boost The multiplier for the stat boost.
     */
    public TuningPart(String name, int price, String stat, double boost) {
        super(name, price);
        this.stat = stat;
        this.boost = boost;
    }

    /**
     * Gets the stat that this tuning part affects.
     * @return The string identifier for the affected stat.
     */
    public String getStat() {
        return stat;
    }

    /**
     * Gets the boost multiplier provided by this tuning part.
     * @return The boost multiplier.
     */
    public double getBoost() {
        return boost;
    }
}