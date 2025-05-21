package seng201.team0.models;

/**
 * Represents an AI-controlled opponent car in a race.
 * Opponent cars have a constant speed and track their current distance traveled.
 */
public class OpponentCar {
    /**
     * The constant speed of the opponent car in kilometers per tick (or unit of time).
     */
    private final double speed;
    /**
     * The current distance accumulated by the opponent car during the race, in kilometers.
     * This value increases with each {@code advanceTick()} call.
     */
    private double currentDistance = 0;

    /**
     * Constructs a new OpponentCar with a specified speed.
     * @param speed The constant speed at which this opponent car travels.
     */
    public OpponentCar(double speed) {
        this.speed = speed;
    }

    /**
     * Gets the constant speed of the opponent car.
     * @return The speed of the car.
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Gets the current total distance traveled by the opponent car.
     * @return The current distance in kilometers.
     */
    public double getCurrentDistance() {
        return currentDistance;
    }

    /**
     * Advances the opponent car's distance by its speed for one tick (unit of time).
     * This simulates the car moving forward.
     */
    public void advanceTick() {
        currentDistance += speed;
    }
}