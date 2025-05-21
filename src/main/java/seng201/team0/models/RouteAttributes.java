package seng201.team0.models;

/**
 * Defines the specific advantages or disadvantages a route provides to different
 * car statistics: speed, handling, and reliability. These multipliers are used
 * to modify a car's effective performance on a given route.
 */
public class RouteAttributes {
    /**
     * A multiplier applied to a car's speed stat on this route.
     * A value greater than 1 indicates an advantage, less than 1 a disadvantage.
     */
    private final double speedAdvantage;
    /**
     * A multiplier applied to a car's handling stat on this route.
     * A value greater than 1 indicates an advantage, less than 1 a disadvantage.
     */
    private final double handlingAdvantage;
    /**
     * A multiplier applied to a car's reliability stat on this route.
     * A value greater than 1 indicates an advantage, less than 1 a disadvantage.
     */
    private final double reliabilityAdvantage;

    /**
     * Constructs new RouteAttributes with specified advantages for car stats.
     * @param speedAdvantage The multiplier for speed.
     * @param handlingAdvantage The multiplier for handling.
     * @param reliabilityAdvantage The multiplier for reliability.
     */
    public RouteAttributes(double speedAdvantage, double handlingAdvantage, double reliabilityAdvantage) {
        this.speedAdvantage = speedAdvantage;
        this.handlingAdvantage = handlingAdvantage;
        this.reliabilityAdvantage = reliabilityAdvantage;
    }

    /**
     * Gets the speed advantage multiplier for this route.
     * @return The speed advantage multiplier.
     */
    public double getSpeedAdvantage() {
        return speedAdvantage;
    }

    /**
     * Gets the handling advantage multiplier for this route.
     * @return The handling advantage multiplier.
     */
    public double getHandlingAdvantage() {
        return handlingAdvantage;
    }

    /**
     * Gets the reliability advantage multiplier for this route.
     * @return The reliability advantage multiplier.
     */
    public double getReliabilityAdvantage() {
        return reliabilityAdvantage;
    }
}