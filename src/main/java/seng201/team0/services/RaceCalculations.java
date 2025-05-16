package seng201.team0.services;

import seng201.team0.models.*;

import java.util.ArrayList;
import java.util.List;

public final class RaceCalculations {
    private List<OpponentCar> opponents;


    public RaceCalculations(List<OpponentCar> opponents) {
        this.opponents = opponents;
    }

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

    public static List<OpponentCar> getFinishedOpponents(Race race) {
        return race.getOpponents().stream()
                .filter(o -> o.getCurrentDistance() >= race.getRoute().getLength())
                .toList();
    }

    public static List<Double> calculateFuelStopDistances(double length, int numStops) {
        List<Double> stops = new ArrayList<>();
        for (int i = 1; i <= numStops; i++) {
            stops.add(length * i / (numStops + 1));
        }
        return stops;
    }

    public static double calculateEffectiveReliability(Car car, Route route) {
        RouteAttributes attr = route.getAttributes();
        return car.getReliability() * attr.getReliabilityAdvantage();
    }

    public static double calculateEventTriggerDistance(double length) {
        return (double) (length * 0.4);
    }

    public static double calculateBreakdownTriggerDistance(double length) {
        return (double) (length * 0.7);
    }
}

