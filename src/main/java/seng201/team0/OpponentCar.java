package seng201.team0;

public class OpponentCar {
    private double speed;  // Constant speed for the opponent (in km/h)
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

    public void updateDistance(double timeElapsed) {
        // The opponent moves at a constant speed regardless of other factors
        this.currentDistance += speed * timeElapsed;  // Update distance based on speed and time
    }
}