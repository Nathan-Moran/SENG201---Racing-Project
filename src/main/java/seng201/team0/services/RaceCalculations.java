package seng201.team0.services;

import seng201.team0.models.*;

import java.util.List;

public class RaceCalculations {
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


    public void updateRace(int timeElapsed) {
        // Update the distance for each opponent
        for (OpponentCar opponent : opponents) {
            opponent.updateDistance(timeElapsed);
        }
    }
}

