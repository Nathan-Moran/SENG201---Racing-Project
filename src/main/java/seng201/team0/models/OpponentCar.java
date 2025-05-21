package seng201.team0.models;

public class OpponentCar {
    private final double speed;  // Constant speed for the opponent (in km/h)
    private double currentDistance = 0;  // Distance traveled by the opponent (in kilometers)

    public OpponentCar(double speed) {
        this.speed = speed;
    }

    public double getSpeed() {
        return speed;
    }

    public double getCurrentDistance() {
        return currentDistance;
    }

    public void advanceTick() {
        currentDistance += speed;
    }
}