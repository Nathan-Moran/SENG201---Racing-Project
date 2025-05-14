package seng201.team0;

public class RaceCalculations {

    public static double calculateEffectiveSpeed(Car car, Route route) {
        RouteAttributes attr = route.getAttributes();

        double speed = car.getSpeed();
        double handling = car.getHandling();

        double adjustedSpeed = speed * attr.getSpeedAdvantage();
        double adjustedHandling = handling * attr.getHandlingAdvantage();

        return adjustedSpeed + adjustedHandling;
    }


    public static double calculateFuelConsumptionRate(Car car) {
        return 1.0 / car.getFuelEconomy();
    }
}

